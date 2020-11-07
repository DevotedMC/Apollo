package com.github.civcraft.apollo.rabbit.incoming;

import org.json.JSONObject;

import com.github.civcraft.apollo.rabbit.session.PlayerLoginSession;
import com.github.civcraft.zeus.rabbit.incoming.InteractiveRabbitCommand;
import com.github.civcraft.zeus.rabbit.outgoing.artemis.ConfirmInitialPlayerLogin;
import com.github.civcraft.zeus.servers.ConnectedServer;

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
