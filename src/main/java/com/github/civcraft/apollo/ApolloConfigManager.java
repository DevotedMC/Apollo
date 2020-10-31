package com.github.civcraft.apollo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;
import com.rabbitmq.client.ConnectionFactory;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ApolloConfigManager {

	private static final String CONFIG_FILE_NAME = "config.yml";

	private ApolloMain apollo;
	private File configFile;
	private Configuration config;
	
	private ConnectionFactory connectionFactory;
	private String ownIdentifier;
	private boolean rabbitDebug;

	public ApolloConfigManager(ApolloMain apollo) {
		this.apollo = apollo;
	}
	
	public boolean parse() {
		connectionFactory = parseRabbitConfig();
		ownIdentifier = config.getString("own_identifier");
		return true;
	}
	
	private ConnectionFactory parseRabbitConfig() {
		ConnectionFactory connFac = new ConnectionFactory();
		String user = config.getString("rabbitmq.user", null);
		if (user != null) {
			connFac.setUsername(user);
		}
		String password = config.getString("rabbitmq.password", null);
		if (password != null) {
			connFac.setPassword(password);
		}
		String host = config.getString("rabbitmq.host", null);
		if (host != null) {
			connFac.setHost(host);
		}
		int port = config.getInt("rabbitmq.port", -1);
		if (port != -1) {
			connFac.setPort(port);
		}
		rabbitDebug = config.getBoolean("rabbitmq.debug", true);
		return connFac;
	}
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	
	public String getOwnIdentifier() {
		return ownIdentifier;
	}
	
	public boolean debugRabbit() {
		return rabbitDebug;
	}
 
	public boolean reload() {
		apollo.getDataFolder().mkdirs();
		configFile = new File(apollo.getDataFolder(), CONFIG_FILE_NAME);
		ConfigurationProvider configMan = ConfigurationProvider.getProvider(YamlConfiguration.class);
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
				try (InputStream is = apollo.getResourceAsStream("config.yml");
						OutputStream os = new FileOutputStream(configFile)) {
					ByteStreams.copy(is, os);
				}
			} catch (IOException e) {
				apollo.getLogger().severe("Unable to create configuration file: " + e.toString());
				return false;
			}
		}
		try {
			config = configMan.load(configFile);
		} catch (IOException e) {
			config = null;
			return false;
		}
		return true;
	}

}
