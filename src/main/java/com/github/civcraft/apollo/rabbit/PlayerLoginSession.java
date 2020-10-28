package com.github.civcraft.apollo.rabbit;

import java.util.UUID;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.zeus.rabbit.PlayerSpecificPacketSession;
import com.github.civcraft.zeus.servers.ConnectedServer;

import net.md_5.bungee.api.event.LoginEvent;

public class PlayerLoginSession extends PlayerSpecificPacketSession {

	private LoginEvent eventWaiting;

	public PlayerLoginSession(ConnectedServer source, String transactionID, UUID player) {
		super(source, transactionID, player);
	}

	public void setWaitingEvent(LoginEvent event) {
		this.eventWaiting = event;
	}

	public void handleReply(boolean allow, String reason, String targetServer) {
		if (allow && targetServer != null) {
			eventWaiting.setCancelReason("");
			ApolloMain.getInstance().getPlayerServerManager().putConnectionTarget(getPlayer(), targetServer);
		} else {
			if (reason == null || reason.isEmpty()) {
				reason = "Login rejected";
			}
			eventWaiting.setCancelReason(reason);
		}
		eventWaiting.setCancelled(false);
		eventWaiting.notifyAll();
	}

	@Override
	public void handleTimeout() {
		// TODO Auto-generated method stub

	}

}
