package com.github.maxopoly.apollo.commands;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.apollo.rabbit.outgoing.RequestWorldSpawn;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WorldSpawnCommand extends Command {

	public WorldSpawnCommand() {
		super("worldspawn");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			return;
		}
		ApolloMain.getInstance().getRabbitHandler().sendMessage(new RequestWorldSpawn(((ProxiedPlayer) sender).getUniqueId()));
	}

}
