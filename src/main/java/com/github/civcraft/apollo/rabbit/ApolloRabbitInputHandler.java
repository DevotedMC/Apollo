package com.github.civcraft.apollo.rabbit;

import com.github.civcraft.zeus.model.TransactionIdManager;
import com.github.civcraft.zeus.rabbit.abstr.AbstractRabbitInputHandler;

public class ApolloRabbitInputHandler extends AbstractRabbitInputHandler {

	public ApolloRabbitInputHandler(TransactionIdManager transactionManager) {
		super(transactionManager);
	}

	@Override
	protected void registerCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void logError(String msg) {
		// TODO Auto-generated method stub
		
	}

}
