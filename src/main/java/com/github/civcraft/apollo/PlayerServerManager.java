package com.github.civcraft.apollo;

import java.util.UUID;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerServerManager {
	
	private ApolloMain apollo;
	
	public PlayerServerManager(ApolloMain apollo) {
		this.apollo = apollo;
	}
	
	public boolean sendPlayer(ProxiedPlayer player, String server) {
		ServerInfo serverInfo =apollo.getProxy().getServerInfo(server);
		if (serverInfo == null) {
			return false;
		}
		player.connect(serverInfo);
		return true;
	}
	
	public boolean sendPlayer(UUID uuid, String server) {
		ProxiedPlayer player = apollo.getProxy().getPlayer(uuid);
		if (player == null) {
			return false;
		}
		return sendPlayer(player, server);
	}

}
