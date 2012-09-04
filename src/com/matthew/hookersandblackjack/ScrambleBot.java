package com.matthew.hookersandblackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.jibble.pircbot.PircBot;

public class ScrambleBot extends PircBot {
	String currentQuestion;
	String currentPlayer;
	String currentWord;
	String scrambledWord;
	HMDB playerDB;
	Timer announcer;
	String currentChannel = "#dorsal-fun";

	public ScrambleBot(HMDB playerDB) {
		this.setName("ScrambleBot");
		reset();
	}

	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		super.onMessage(channel, sender, login, hostname, message);
		if (currentWord != null) {
			if (!sender.equalsIgnoreCase(currentPlayer)) {
				if (message.toLowerCase().equals(currentWord)) {
					sendMessage(
							channel,
							sender
									+ " answered "
									+ currentWord
									+ " which is correct and will now receive 10 LTTng dollars! ");
					playerDB.put(sender, playerDB.get(sender) + 10L);
				}
			} else {
				sendMessage(channel, sender + " disabled the game");
			}
			reset();
		}
	}

	private void reset() {
		currentWord = null;
		scrambledWord = null;
		currentQuestion = null;
		currentPlayer = null;
		announcer = null;
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		super.onPrivateMessage(sender, login, hostname, message);
		String[] msg = message.split(" ");
		String command = msg[0].toLowerCase();
		String data = message.substring(message.indexOf(' ') + 1);
		switch (command) {
		case "!question":
			if (currentWord == null) {
				currentQuestion = data;
				currentPlayer = sender;
			}
			break;
		case "!answer":
			if (currentWord == null && currentQuestion != null
					&& currentPlayer.equalsIgnoreCase(sender)) {
				currentWord = data;
				ArrayList<Character> shuffle = new ArrayList<>();
				for (int i = 0; i < data.length(); i++) {
					char charAt = data.charAt(i);
					if (charAt != 0)
						shuffle.add(charAt);
				}
				Collections.shuffle(shuffle);
				StringBuilder sb = new StringBuilder();
				scrambledWord = new String();
				while (!shuffle.isEmpty())
					sb.append(shuffle.remove(0));
				scrambledWord = sb.toString();
				announcer = new Timer();
				announcer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						sendMessage(currentChannel,
								"Scramble game is started: Question : "
										+ currentQuestion);
						sendMessage(currentChannel, "Hint : " + scrambledWord);
					}
				}, 0, 180 * 1000);
			}
			break;
		}

	}

}
