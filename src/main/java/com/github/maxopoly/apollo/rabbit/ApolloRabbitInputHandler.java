package com.github.maxopoly.apollo.rabbit;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.apollo.rabbit.incoming.ConfirmPlayerLogin;
import com.github.maxopoly.apollo.rabbit.incoming.LimboPlayerHandler;
import com.github.maxopoly.apollo.rabbit.incoming.RejectPlayerLogin;
import com.github.maxopoly.apollo.rabbit.incoming.SendPlayerHandler;
import com.github.maxopoly.zeus.model.TransactionIdManager;
import com.github.maxopoly.zeus.rabbit.abstr.AbstractRabbitInputHandler;

public class ApolloRabbitInputHandler extends AbstractRabbitInputHandler {

	public ApolloRabbitInputHandler(TransactionIdManager transactionManager) {
		super(transactionManager);
	}

	@Override
	protected void registerCommands() {
		registerCommand(new LimboPlayerHandler());
		registerCommand(new SendPlayerHandler());
		registerCommand(new ConfirmPlayerLogin());
		registerCommand(new RejectPlayerLogin());
	}

	@Override
	protected void logError(String msg) {
		ApolloMain.getInstance().getLogger().severe(msg);
	}

}
