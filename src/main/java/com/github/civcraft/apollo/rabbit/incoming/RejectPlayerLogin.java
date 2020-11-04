package com.github.civcraft.apollo.rabbit.incoming;

import org.json.JSONObject;

import com.github.civcraft.apollo.rabbit.PlayerLoginSession;
import com.github.civcraft.zeus.rabbit.incoming.InteractiveRabbitCommand;
import com.github.civcraft.zeus.rabbit.outgoing.artemis.RejectPlayerDataRequest;
import com.github.civcraft.zeus.rabbit.outgoing.artemis.RejectPlayerInitialLogin;
import com.github.civcraft.zeus.servers.ConnectedServer;

public class RejectPlayerLogin extends InteractiveRabbitCommand<PlayerLoginSession> {

	@Override
	public boolean handleRequest(PlayerLoginSession connState, ConnectedServer sendingServer, JSONObject data) {
		String reason = data.getString("reason");
		connState.handleReply(false, reason, null);
		return false;
	}

	@Override
	public String getIdentifier() {
		return RejectPlayerInitialLogin.ID;
	}

	@Override
	public boolean createSession() {
		return false;
	}
	

}
