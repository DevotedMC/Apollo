package com.github.maxopoly.apollo.rabbit;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.zeus.rabbit.RabbitMessage;

public abstract class ApolloRabbitMessage extends RabbitMessage {

	public ApolloRabbitMessage() {
		super(ApolloMain.getInstance().getTransactionIdManager().pullNewTicket());
	}

}
