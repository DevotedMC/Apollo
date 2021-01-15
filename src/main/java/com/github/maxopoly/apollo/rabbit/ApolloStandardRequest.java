package com.github.maxopoly.apollo.rabbit;

import org.json.JSONObject;

import com.github.maxopoly.apollo.ApolloMain;
import com.github.maxopoly.zeus.model.TransactionIdManager;
import com.github.maxopoly.zeus.rabbit.StandardRequest;
import com.github.maxopoly.zeus.servers.ConnectedServer;

public abstract class ApolloStandardRequest extends StandardRequest {

	public ApolloStandardRequest() {
		super(ApolloMain.getInstance().getTransactionIdManager(), ApolloMain.getInstance().getZeus());
	}

}
