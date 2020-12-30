package com.github.maxopoly.apollo.rabbit.outgoing;

import java.net.InetAddress;
import java.util.UUID;

import org.json.JSONObject;

import com.github.maxopoly.zeus.rabbit.RabbitMessage;
import com.github.maxopoly.zeus.rabbit.incoming.apollo.PlayerLoginRequest;

public class RequestPlayerLogin extends RabbitMessage {

	private UUID player;
	private InetAddress ip;
	private String name;
	
	public RequestPlayerLogin(String transactionID, UUID player, InetAddress ip, String name) {
		super(transactionID);
		this.player = player;
		this.ip = ip;
		this.name = name;
	}

	@Override
	protected void enrichJson(JSONObject json) {
		json.put("player", player);
		json.put("ip", ip.getHostAddress());
		json.put("name", name);
	}

	@Override
	public String getIdentifier() {
		return PlayerLoginRequest.ID;
	}

}
