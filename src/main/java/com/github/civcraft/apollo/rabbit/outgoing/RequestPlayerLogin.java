package com.github.civcraft.apollo.rabbit.outgoing;

import java.net.InetAddress;
import java.util.UUID;

import org.json.JSONObject;

import com.github.civcraft.zeus.rabbit.RabbitMessage;
import com.github.civcraft.zeus.rabbit.incoming.apollo.PlayerLoginRequest;

public class RequestPlayerLogin extends RabbitMessage {

	private UUID player;
	private InetAddress ip;
	
	public RequestPlayerLogin(String transactionID, UUID player, InetAddress ip) {
		super(transactionID);
		this.player = player;
		this.ip = ip;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player);
		json.put("ip", ip.toString());
	}

	@Override
	public String getIdentifier() {
		return PlayerLoginRequest.ID;
	}

}
