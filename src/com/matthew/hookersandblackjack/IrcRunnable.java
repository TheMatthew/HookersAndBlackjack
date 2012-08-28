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

	@Override
	public void run() {
		bot.setVerbose(true);
		try {
			bot.connect("irc.oftc.net");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bot.joinChannel("#dorsal-fun");
	}

}
