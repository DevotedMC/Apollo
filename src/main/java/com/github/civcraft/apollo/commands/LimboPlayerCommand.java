package com.github.civcraft.apollo.commands;

import com.github.civcraft.apollo.ApolloMain;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LimboPlayerCommand extends Command {

	public LimboPlayerCommand() {
		super("limbo");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage("Command only accepts name of a player");
			return;
		}
		ProxiedPlayer player = ApolloMain.getInstance().getProxy().getPlayer(args [0]);
		if (player == null) {
			sender.sendMessage("The player "+  args [0] + " does not exist");
			return;
		}
		ApolloMain.getInstance().getLimboManager().limboPlayer(player.getUniqueId());
	}

}