package com.github.civcraft.apollo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.protocol.Protocol;
import net.md_5.bungee.protocol.packet.Kick;

public class FakeChannelWrapper extends ChannelWrapper {

	public FakeChannelWrapper(ChannelWrapper old) {
		super(null);
	}
	
	
	public void setProtocol(Protocol protocol) {
	}

	public void setVersion(int protocol) {
	}

	public void write(Object packet) {
	}

	public void close(Object packet) {
		
	}

	public void delayedClose(final Kick kick) {
		
	}

	public void addBefore(String baseName, String name, ChannelHandler handler) {

	}

	public Channel getHandle() {
		return null;
	}

	public void setCompressionThreshold(int compressionThreshold) {
		
	}

}
