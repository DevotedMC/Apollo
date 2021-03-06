package com.github.maxopoly.apollo.rabbit.outgoing;

import java.util.UUID;

import org.json.JSONObject;

import com.github.maxopoly.apollo.rabbit.ApolloRabbitMessage;
import com.github.maxopoly.zeus.rabbit.RabbitMessage;
import com.github.maxopoly.zeus.rabbit.incoming.apollo.PlayerDisconnectHandler;

public class NotifyPlayerLogoff extends ApolloRabbitMessage {

	private UUID player;
	
	public NotifyPlayerLogoff(UUID player) {
		this.player = player;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player.toString());
	}

	@Override
	public String getIdentifier() {
		return PlayerDisconnectHandler.ID;
	}

}
