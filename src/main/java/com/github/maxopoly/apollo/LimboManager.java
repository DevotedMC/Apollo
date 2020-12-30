package com.github.maxopoly.apollo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.Respawn;

public class LimboManager {
	
	private Set<UUID> limboPlayers;
	private ApolloMain apollo;
	
	public LimboManager(ApolloMain apollo) {
		this.limboPlayers = new HashSet<>();
		this.apollo = apollo;
	}
	
	public void limboPlayer(UUID uuid){
		ProxiedPlayer player = apollo.getProxy().getPlayer(uuid);
		sendIntoLimboPacket(player);
		limboPlayers.add(uuid);
	}
	
	public void removeFromLimbo(UUID uuid) {
		this.limboPlayers.remove(uuid);
	}

	private void sendIntoLimboPacket(ProxiedPlayer player) {
		/*
		CompoundTag dimension = new CompoundTag();
		dimension.add(new NamedTag("name", new StringTag("minecraft:the_end")));
		dimension.add(new NamedTag("natural", new ByteTag(0)));
		dimension.add(new NamedTag("ambient_light", new ByteTag(0)));
		dimension.add(new NamedTag("bed_works", new ByteTag(0)));
		dimension.add(new NamedTag("has_skylight", new ByteTag(0)));
		*/
		//dimension.(new StringTag("minecraft:the_end"));
		//dimension.set
		//dimension.set(1, new IntTag(3));
		Respawn respawnPacket = new Respawn("minecraft:the_end", "limbo", -1L, (short) 1, (short) 1, (short) 1, null, false, true,
				true);
		player.unsafe().sendPacket(respawnPacket);
	}

}
