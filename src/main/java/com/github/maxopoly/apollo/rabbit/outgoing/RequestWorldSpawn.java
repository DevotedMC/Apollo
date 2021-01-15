package com.github.maxopoly.apollo.rabbit.outgoing;

import java.util.UUID;

import org.json.JSONObject;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.apollo.rabbit.ApolloStandardRequest;
import com.github.maxopoly.zeus.rabbit.RabbitMessage;
import com.github.maxopoly.zeus.rabbit.StandardRequest;
import com.github.maxopoly.zeus.rabbit.incoming.apollo.WorldSpawnRequestHandler;

public class RequestWorldSpawn extends ApolloStandardRequest {
	
	private UUID player;

	public RequestWorldSpawn(UUID player) {
		this.player = player;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player);
	}

	@Override
	public String getIdentifier() {
		return WorldSpawnRequestHandler.REQUEST_ID;
	}

	@Override
	public void handleReply(JSONObject reply) {
		String server = reply.getString("target_server");
		ApolloMain.getInstance().getPlayerServerManager().sendPlayer(player, server);
	}

}
