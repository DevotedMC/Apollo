package com.github.civcraft.apollo.rabbit.incoming;

import java.util.UUID;

import org.json.JSONObject;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.zeus.rabbit.incoming.StaticRabbitCommand;
import com.github.civcraft.zeus.rabbit.outgoing.apollo.TransferPlayerToServer;
import com.github.civcraft.zeus.servers.ConnectedServer;

public class SendPlayerHandler extends StaticRabbitCommand {

	@Override
	public void handleRequest(ConnectedServer sendingServer, JSONObject data) {
		UUID uuid = UUID.fromString(data.getString("player"));
		String targetServer = data.getString("target_server");
		ApolloMain.getInstance().getPlayerServerManager().sendPlayer(uuid, targetServer);
	}

	@Override
	public String getIdentifier() {
		return TransferPlayerToServer.ID;
	}

}
