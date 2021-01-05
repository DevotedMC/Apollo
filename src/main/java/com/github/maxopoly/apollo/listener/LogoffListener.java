package com.github.maxopoly.apollo.listener;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.apollo.rabbit.outgoing.NotifyPlayerLogoff;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LogoffListener implements Listener {
	
	@EventHandler
	public void onLogoff(PlayerDisconnectEvent event) {
		String transId = ApolloMain.getInstance().getTransactionIdManager().pullNewTicket();
		ApolloMain.getInstance().getRabbitHandler().sendMessage(new NotifyPlayerLogoff(transId, event.getPlayer().getUniqueId()));
	}

}
