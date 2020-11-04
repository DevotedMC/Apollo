package com.github.civcraft.apollo.listener;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.apollo.rabbit.PlayerLoginSession;
import com.github.civcraft.apollo.rabbit.outgoing.RequestPlayerLogin;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

	@EventHandler
	public void onLogin(LoginEvent event) {
		event.setCancelled(true);
		ApolloMain apollo = ApolloMain.getInstance();
		String ticket = apollo.getTransactionIdManager().pullNewTicket();
		PlayerLoginSession session = new PlayerLoginSession(apollo.getZeus(), ticket,
				event.getConnection().getUniqueId());
		session.setWaitingEvent(event);
		apollo.getTransactionIdManager().putSession(session);
		ApolloMain.getInstance().getRabbitHandler()
				.sendMessage(new RequestPlayerLogin(ticket, event.getConnection().getUniqueId()));
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
			break;
		default:
			event.getPlayer().disconnect("No");
			return;
		}
		// only join proxy, meaning initial connect at this point

	}

}
