package com.github.maxopoly.apollo.rabbit.incoming;

import org.json.JSONObject;

import com.github.maxopoly.apollo.rabbit.session.PlayerLoginSession;
import com.github.maxopoly.zeus.rabbit.incoming.InteractiveRabbitCommand;
import com.github.maxopoly.zeus.rabbit.outgoing.apollo.ConfirmInitialPlayerLogin;
import com.github.maxopoly.zeus.servers.ConnectedServer;

public class ConfirmPlayerLogin extends InteractiveRabbitCommand<PlayerLoginSession> {

	@Override
	public boolean handleRequest(PlayerLoginSession connState, ConnectedServer sendingServer, JSONObject data) {
		String targetServer = data.getString("target");
		String playerName = data.getString("name");
		connState.setName(playerName);
		connState.handleReply(true, null, targetServer);
		return false;
	}

	@Override
	public String getIdentifier() {
		return ConfirmInitialPlayerLogin.ID;
	}

	@Override
	public boolean createSession() {
		return false;
	}

}
