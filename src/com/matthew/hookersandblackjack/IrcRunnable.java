/*
 * IrcRunnable.java - The IRC thread
 *
 * Copyright (C) 2012 Matthew Khouzam
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.matthew.hookersandblackjack;

import org.jibble.pircbot.PircBot;

public class IrcRunnable implements Runnable {

	PircBot bot;

	public PircBot getBot() {
		return bot;
	}

	public void setBot(PircBot bot) {
		this.bot = bot;
	}


	String channel = "#dorsal-test";

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	

	String hostname = "irc.oftc.net";
	

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public void run() {
		bot.setVerbose(true);
		try {
			bot.connect(hostname);
			bot.joinChannel(channel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
