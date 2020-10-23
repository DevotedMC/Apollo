package com.github.civcraft.apollo.rabbit;

import com.github.civcraft.apollo.ApolloMain;
import com.github.civcraft.apollo.rabbit.incoming.LimboPlayerHandler;
import com.github.civcraft.apollo.rabbit.incoming.SendPlayerHandler;
import com.github.civcraft.zeus.model.TransactionIdManager;
import com.github.civcraft.zeus.rabbit.abstr.AbstractRabbitInputHandler;

public class ApolloRabbitInputHandler extends AbstractRabbitInputHandler {

	public ApolloRabbitInputHandler(TransactionIdManager transactionManager) {
		super(transactionManager);
	}

	@Override
	protected void registerCommands() {
		registerCommand(new LimboPlayerHandler());
		registerCommand(new SendPlayerHandler());
	}

	@Override
	protected void logError(String msg) {
		ApolloMain.getInstance().getLogger().severe(msg);
	}

}
