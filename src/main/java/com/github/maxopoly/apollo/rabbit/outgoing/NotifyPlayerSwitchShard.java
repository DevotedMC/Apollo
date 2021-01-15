package com.github.maxopoly.apollo.rabbit.outgoing;

import java.util.UUID;

import org.json.JSONObject;

import com.github.maxopoly.apollo.rabbit.ApolloRabbitMessage;
import com.github.maxopoly.zeus.rabbit.RabbitMessage;
import com.github.maxopoly.zeus.rabbit.incoming.apollo.NotifyPlayerSwitchShardHandler;

public class NotifyPlayerSwitchShard extends ApolloRabbitMessage {

	private UUID player;
	private String newShard;
	
	public NotifyPlayerSwitchShard(UUID player, String newShard) {
		this.player = player;
		this.newShard = newShard;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player.toString());
		json.put("server", newShard);
	}

	@Override
	public String getIdentifier() {
		return NotifyPlayerSwitchShardHandler.ID;
	}

}
