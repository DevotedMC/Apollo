package com.github.civcraft.apollo.rabbit.outgoing;

import java.util.UUID;

import org.json.JSONObject;

import com.github.civcraft.zeus.rabbit.RabbitMessage;
import com.github.civcraft.zeus.rabbit.incoming.apollo.PlayerLoginRequest;

public class RequestPlayerLogin extends RabbitMessage {

	private UUID player;
	
	public RequestPlayerLogin(String transactionID, UUID player) {
		super(transactionID);
		this.player = player;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player);
	}

	@Override
	public String getIdentifier() {
		return PlayerLoginRequest.ID;
	}

}
