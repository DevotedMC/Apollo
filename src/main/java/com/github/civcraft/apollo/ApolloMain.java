package com.github.civcraft.apollo;

import com.github.civcraft.apollo.rabbit.RabbitHandler;
import com.github.civcraft.zeus.servers.ZeusServer;

import net.md_5.bungee.api.plugin.Plugin;

public class ApolloMain extends Plugin {
	
	private static ApolloMain instance;
	
	public static ApolloMain getInstance() {
		return instance;
	}
	
	private RabbitHandler rabbitHandler;
	
	@Override
	public void onEnable() {
		instance = this;
		rabbitHandler = new RabbitHandler(connFac, incomingQueue, outgoingQueue, ownName, getLogger(), new ZeusServer());
	}

}
