package com.github.maxopoly.apollo;

import java.util.concurrent.TimeUnit;

import com.github.maxopoly.apollo.commands.LimboPlayerCommand;
import com.github.maxopoly.apollo.commands.LobbyCommand;
import com.github.maxopoly.apollo.commands.WorldSpawnCommand;
import com.github.maxopoly.apollo.listener.LoginListener;
import com.github.maxopoly.apollo.listener.LogoffListener;
import com.github.maxopoly.apollo.rabbit.RabbitHandler;
import com.github.maxopoly.zeus.model.TransactionIdManager;
import com.github.maxopoly.zeus.servers.ZeusServer;

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
		transactionIdManager = new TransactionIdManager(configManager.getOwnIdentifier(), getLogger()::info);
		rabbitHandler = new RabbitHandler(configManager.getConnectionFactory(), configManager.getOwnIdentifier(),
				transactionIdManager, getLogger(), new ZeusServer());
		if (!rabbitHandler.setup()) {
			getProxy().stop("Failed to load rabbit");
			return;
		}
		rabbitHandler.beginAsyncListen();
		getProxy().getScheduler().schedule(this, transactionIdManager::updateTimeouts, 10, 10, TimeUnit.MILLISECONDS);
		getProxy().getPluginManager().registerListener(this, new LoginListener());
		getProxy().getPluginManager().registerListener(this, new LogoffListener());
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		rabbitHandler.shutdown();
	}

	private void registerCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LimboPlayerCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new LobbyCommand());
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new WorldSpawnCommand());
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
