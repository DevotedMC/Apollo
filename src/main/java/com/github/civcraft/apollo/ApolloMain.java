package com.github.civcraft.apollo;

import com.github.civcraft.apollo.commands.LimboPlayerCommand;
import com.github.civcraft.apollo.listener.LoginListener;
import com.github.civcraft.apollo.rabbit.RabbitHandler;
import com.github.civcraft.zeus.model.TransactionIdManager;
import com.github.civcraft.zeus.servers.ZeusServer;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class ApolloMain extends Plugin {

	private static ApolloMain instance;

	public static ApolloMain getInstance() {
		return instance;
	}

	private RabbitHandler rabbitHandler;
	private ApolloConfigManager configManager;
	private TransactionIdManager transactionIdManager;
	private LimboManager limboManager;
	private PlayerServerManager serverManager;
	private ZeusServer zeus;

	@Override
	public void onEnable() {
		instance = this;
		configManager = new ApolloConfigManager(this);
		if (!(configManager.reload() && configManager.parse())) {
			getProxy().stop("Failed to load config");
			return;
		}
		zeus = new ZeusServer();
		serverManager = new PlayerServerManager(this);
		limboManager = new LimboManager(this);
		transactionIdManager = new TransactionIdManager(configManager.getOwnIdentifier());
		getProxy().getPluginManager().registerListener(this, new LoginListener());
		rabbitHandler = new RabbitHandler(configManager.getConnectionFactory(), configManager.getOwnIdentifier(),
				transactionIdManager, getLogger(), new ZeusServer());
		registerCommands();
	}

	private void registerCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LimboPlayerCommand());
	}

	public LimboManager getLimboManager() {
		return limboManager;
	}
	
	public ZeusServer getZeus() {
		return zeus;
	}

	public RabbitHandler getRabbitHandler() {
		return rabbitHandler;
	}

	public ApolloConfigManager getConfigManager() {
		return configManager;
	}

	public TransactionIdManager getTransactionIdManager() {
		return transactionIdManager;
	}

	public PlayerServerManager getPlayerServerManager() {
		return serverManager;
	}

}
