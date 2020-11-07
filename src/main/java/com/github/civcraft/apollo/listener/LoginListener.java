package com.github.civcraft.apollo.listener;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.apollo.rabbit.outgoing.RequestPlayerLogin;
import com.github.civcraft.apollo.rabbit.session.PlayerLoginSession;

import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

	@EventHandler
	public void onLogin(LoginEvent event) {
		event.setCancelled(true);
		ApolloMain apollo = ApolloMain.getInstance();
		String ticket = apollo.getTransactionIdManager().pullNewTicket();
		PlayerLoginSession session = new PlayerLoginSession(apollo.getZeus(), ticket,
				event.getConnection().getUniqueId(), event.getConnection().getName());
		session.setWaitingEvent(event);
		apollo.getTransactionIdManager().putSession(session);
		ApolloMain.getInstance().getRabbitHandler().sendMessage(new RequestPlayerLogin(ticket,
				event.getConnection().getUniqueId(), event.getConnection().getAddress().getAddress(), event.getConnection().getName()));
		synchronized (event) {
			while (event.isCancelled()) {
				try {
					event.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (event.getCancelReasonComponents().length > 0) {
			event.setCancelled(true);
			return;
		}
		// allow
		String name = session.getName(); //TODO update player name
		event.setCancelled(false);
		
	}

	@EventHandler
	public void onServerConnect(ServerConnectEvent event) {
		switch (event.getReason()) {
		case SERVER_DOWN_REDIRECT:
		case LOBBY_FALLBACK:
		case KICK_REDIRECT:
			event.getPlayer().disconnect("Server down");
			return;
		case PLUGIN:
		case COMMAND:
		case PLUGIN_MESSAGE:
		case UNKNOWN:
			return;
		case JOIN_PROXY:
			String target = ApolloMain.getInstance().getPlayerServerManager().consumeConnectionTarget(event.getPlayer().getUniqueId());
			if (target == null) {
				event.getPlayer().disconnect("Failed to find target server");
				return;
			}
			ServerInfo server = ApolloMain.getInstance().getProxy().getServerInfo(target);
			if (server == null) {
				event.getPlayer().disconnect("Target server went offline mid connection attempt");
				return;
			}
			event.setTarget(server);
			break;
		default:
			event.getPlayer().disconnect("No");
			return;
		}
		// only join proxy, meaning initial connect at this point

	}
	
	private String extractRawMsg(BaseComponent [] comps) {
		StringBuilder sb = new StringBuilder();
		for(BaseComponent comp : comps) {
			sb.append(comp.toPlainText());
		}
		return sb.toString();
	}

}
