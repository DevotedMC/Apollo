package com.github.maxopoly.apollo.rabbit.incoming;

import org.json.JSONObject;

import com.github.maxopoly.apollo.rabbit.session.PlayerLoginSession;
import com.github.maxopoly.zeus.rabbit.incoming.InteractiveRabbitCommand;
import com.github.maxopoly.zeus.rabbit.outgoing.apollo.RejectPlayerInitialLogin;
import com.github.maxopoly.zeus.servers.ConnectedServer;

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
