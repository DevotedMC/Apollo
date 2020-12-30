package com.github.maxopoly.apollo.rabbit.session;

import java.util.UUID;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.zeus.rabbit.PlayerSpecificPacketSession;
import com.github.maxopoly.zeus.servers.ConnectedServer;

import net.md_5.bungee.api.event.LoginEvent;

public class PlayerLoginSession extends PlayerSpecificPacketSession {

	private LoginEvent eventWaiting;
	private String name;

	public PlayerLoginSession(ConnectedServer source, String transactionID, UUID player, String name) {
		super(source, transactionID, player);
		this.name = name;
	}

	public void setWaitingEvent(LoginEvent event) {
		this.eventWaiting = event;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void handleReply(boolean allow, String reason, String targetServer) {
		if (allow && targetServer != null) {
			eventWaiting.setCancelReason();
			ApolloMain.getInstance().getPlayerServerManager().putConnectionTarget(getPlayer(), targetServer);
		} else {
			if (reason == null || reason.isEmpty()) {
				reason = "Login rejected";
			}
			eventWaiting.setCancelReason(reason);
		}
		eventWaiting.setCancelled(false);
		synchronized (eventWaiting) {
			eventWaiting.notifyAll();
		}
	}

	@Override
	public void handleTimeout() {
		// TODO Auto-generated method stub

	}

}
