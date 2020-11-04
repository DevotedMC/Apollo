package com.github.civcraft.apollo.rabbit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.zeus.model.TransactionIdManager;
import com.github.civcraft.zeus.rabbit.RabbitMessage;
import com.github.civcraft.zeus.rabbit.ZeusRabbitGateway;
import com.github.civcraft.zeus.rabbit.incoming.InteractiveRabbitCommand;
import com.github.civcraft.zeus.servers.ZeusServer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import net.md_5.bungee.api.plugin.Plugin;

public class RabbitHandler {

	private ConnectionFactory connectionFactory;
	private String incomingQueue;
	private String outgoingQueue;
	private Logger logger;
	private Connection conn;
	private Channel incomingChannel;
	private Channel outgoingChannel;
	private ApolloRabbitInputHandler inputProcessor;
	private ZeusServer zeus;
	private String consumerTag;

	public RabbitHandler(ConnectionFactory connFac, String ownIdentifier, TransactionIdManager transactionManager,
			Logger logger, ZeusServer zeus) {
		this.connectionFactory = connFac;
		this.incomingQueue = ZeusRabbitGateway.getChannelFromZeus(ownIdentifier);
		this.outgoingQueue = ZeusRabbitGateway.getChannelToZeus(ownIdentifier);
		this.logger = logger;
		inputProcessor = new ApolloRabbitInputHandler(transactionManager);
		this.zeus = zeus;
	}

	public boolean setup() {
		InteractiveRabbitCommand.setSendingLambda((s,p) -> sendMessage(p));	
		try {
			conn = connectionFactory.newConnection();
			incomingChannel = conn.createChannel();
			outgoingChannel = conn.createChannel();
			incomingChannel.queueDeclare(incomingQueue, false, false, false, null);
			outgoingChannel.queueDeclare(outgoingQueue, false, false, false, null);
			return true;
		} catch (IOException | TimeoutException e) {
			logger.severe("Failed to setup rabbit connection: " + e.toString());
			return false;
		}
	}

	public void beginAsyncListen() {
		Plugin plugin = ApolloMain.getInstance();
		ApolloMain.getInstance().getProxy().getScheduler().runAsync(plugin, () -> {
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				try {
					String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
					if (ApolloMain.getInstance().getConfigManager().debugRabbit()) {
						logger.info("[X] R_IN: " + message);
					}
					inputProcessor.handle(zeus, message);
				} catch (Exception e) {
					logger.log(Level.SEVERE, "Exception in rabbit handling: ", e);
				}
			};
			try {
				consumerTag = incomingChannel.basicConsume(incomingQueue, true, deliverCallback, consumerTag -> {
				});
			} catch (IOException e) {
				logger.severe("Error in rabbit listener: " + e.toString());
			}
		});
	}

	public void shutdown() {
		try {
			incomingChannel.basicCancel(consumerTag);
			incomingChannel.close();
			outgoingChannel.close();
			conn.close();
		} catch (IOException | TimeoutException e) {
			logger.log(Level.SEVERE, "Failed to close rabbit connection: ", e);
		}
	}

	public boolean sendMessage(RabbitMessage message) {
		try {
			String strMsg = message.getJSON().toString();
			if (ApolloMain.getInstance().getConfigManager().debugRabbit()) {
				logger.info("[X] R_OUT: " + strMsg);
			}
			outgoingChannel.basicPublish("", outgoingQueue, null, strMsg.getBytes(StandardCharsets.UTF_8));
			return true;
		} catch (IOException e) {
			logger.severe("Failed to send rabbit message: " + e);
			return false;
		}
	}
}
