package com.github.maxopoly.apollo.commands;

import com.github.maxopoly.apollo.ApolloMain;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCommand extends Command {

	public LobbyCommand() {
		super("lobby");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ApolloMain.getInstance().getPlayerServerManager().sendPlayer((ProxiedPlayer) sender, "lobby");
	}

}
