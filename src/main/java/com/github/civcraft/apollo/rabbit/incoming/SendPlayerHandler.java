package com.github.civcraft.apollo.rabbit.incoming;

import java.util.UUID;

import org.json.JSONObject;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.zeus.rabbit.incoming.StaticRabbitCommand;
import com.github.civcraft.zeus.servers.ConnectedServer;

public class SendPlayerHandler extends StaticRabbitCommand {

	@Override
	public void handleRequest(ConnectedServer sendingServer, JSONObject data) {
		UUID uuid = UUID.fromString(data.getString("uuid"));
		String targetServer = data.getString("target");
		ApolloMain.getInstance().getPlayerServerManager().sendPlayer(uuid, targetServer);
	}

	@Override
	public String getIdentifier() {
		return "send_player";
	}

}
