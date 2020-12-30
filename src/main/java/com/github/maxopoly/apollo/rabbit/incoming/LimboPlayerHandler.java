package com.github.maxopoly.apollo.rabbit.incoming;

import java.util.UUID;

import org.json.JSONObject;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.zeus.rabbit.incoming.StaticRabbitCommand;
import com.github.maxopoly.zeus.servers.ConnectedServer;

public class LimboPlayerHandler extends StaticRabbitCommand {

	@Override
	public void handleRequest(ConnectedServer sendingServer, JSONObject data) {
		UUID uuid = UUID.fromString(data.getString("uuid"));
		ApolloMain.getInstance().getLimboManager().limboPlayer(uuid);
	}

	@Override
	public String getIdentifier() {
		return "limbo_player";
	}
	

}
