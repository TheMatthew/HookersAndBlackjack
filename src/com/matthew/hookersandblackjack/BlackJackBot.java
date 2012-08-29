/*
 * BlackJackBot.java
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

import java.util.HashMap;
import java.util.Random;

import org.jibble.pircbot.PircBot;

import com.matthew.hookersandblackjack.blackjackutil.Card;
import com.matthew.hookersandblackjack.blackjackutil.Deck;
import com.matthew.hookersandblackjack.blackjackutil.Hand;
import com.matthew.hookersandblackjack.blackjackutil.Player;
import com.matthew.hookersandblackjack.blackjackutil.Player.status;

public class BlackJackBot extends PircBot {
	
	Deck d = new Deck();

	HMDB playerDB;

	HashMap<String, Player> playerMap = new HashMap<String, Player>();

	Random rnd = new Random(System.nanoTime());

	public BlackJackBot() {
		this.setName("BlackJackBot-TEST"+(System.nanoTime()%1000));
	}

	private void balance(String sender) {
		{
			Long money = playerDB.get(sender);
			sendMessage(sender, "You have " + money + " LTTng dolars");
		}
	}

	private void bet(String sender, Player player, String msg) {
		{
			Long money = playerDB.get(sender);
			Long bet = Long.decode(msg.split(" ")[1]);

			if (player.stat.equals(status.notStarted)
					|| player.stat.equals(status.started)) {
				if (money <= 0)
					reply(sender, "please bet a positive integer");
				else if (bet <= money) {
					playerDB.put(sender, money - bet);
					player.bet = bet;
					player.stat = status.bet;
				} else {
					reply(sender, "You don't have that kind of money");
				}
			}
		}
	}

	private void deal(String sender, Player player) {
		if (!player.stat.equals(status.bet)) {
			reply(sender, "You are either playing or didn't bet yet.");
		} else {

			player.dealerHand = new Hand();
			player.playerHand = new Hand();
			Hand dealerHand = player.dealerHand;
			Hand playerHand = player.playerHand;
			dealerHand.hit(d.Deal());
			dealerHand.hit(d.Deal());
			playerHand.hit(d.Deal());
			playerHand.hit(d.Deal());
			if (dealerHand.getValue() == 21) {
				if (playerHand.getValue() == 21) {
					reply(sender, "It's a blackjack push!");
					playerDB.put(sender, player.bet + playerDB.get(sender));
				} else {
					reply(sender, "Dealer has blackjack!");
					player.reset();
				}
			} else if (playerHand.getValue() == 21) {
				reply(sender, "Player has blackjack!");
				playerDB.put(sender,
						(playerDB.get(sender) + (long) (player.bet * 2.5)));
				player.reset();

			} else {
				reply(sender, "Dealer has " + dealerHand.peek().toString());
				reply(sender, "You have " + playerHand.toString());
				player.stat = status.dealt;
			}
		}
	}

	private void hit(String sender, Player player) {
		{
			if (!player.stat.equals(status.dealt)) {
				sendAction(sender, "/me hits " + sender);
			} else {
				Card c = d.Deal();
				player.playerHand.hit(c);
				reply(sender,
						"Given a " + c + " total: "
								+ player.playerHand.getValue());

				if (player.playerHand.getValue() > 21) {
					reply(sender, "Busted");
					player.reset();
				}
			}
		}
	}

	private void mainGame(String sender, String message) {
		Player player = playerMap.get(sender);
		if (player == null) {
			player = new Player();
			playerMap.put(sender, player);
		}

		String msg = message.toLowerCase();
		if (msg.startsWith("!play"))
			playStart(sender, player);
		else if (msg.startsWith("!bet"))
			bet(sender, player, msg);
		else if (msg.startsWith("!deal"))
			deal(sender, player);
		else if (msg.startsWith("!hit"))
			hit(sender, player);
		else if (msg.startsWith("!stand"))
			stand(sender, player);
		else if (msg.startsWith("!balance"))
			balance(sender);
	}

	@Override
	protected void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		super.onMessage(channel, sender, login, hostname, message);
		mainGame(sender, message);
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		super.onPrivateMessage(sender, login, hostname, message);
		mainGame(sender, message);
	}

	private void playStart(String sender, final Player player) {
		{
			if (player.stat.equals(status.notStarted)) {
				this.reply(sender,
						"Blackjack will start, place a bet (!bet <Amount>)");
				Long money = playerDB.get(sender);
				if (money == null) {
					money = 50L;
					playerDB.put(sender, money);
				}
				player.stat = status.started;
				this.reply(sender, "You currently have " + money.toString()
						+ " Tracer Dollars.");
			} else {
				this.reply(sender, "You're already playing a game.");
			}
		}
	}

	public void setPlayerDB(HMDB playerDB2) {
		this.playerDB = playerDB2;
	}

	private void stand(String sender, Player player) {
		{
			int playerVal = player.playerHand.getValue();
			int dealerValue = player.dealerHand.getValue();
			reply(sender, "Dealer has " + player.dealerHand);
			while (dealerValue <= 16 && dealerValue <= playerVal) {
				Card c = d.Deal();
				reply(sender, "Dealer hits " + c);
				player.dealerHand.hit(c);
				dealerValue = player.dealerHand.getValue();
			}
			Long money = playerDB.get(sender);
			int playerValue = player.playerHand.getValue();
			if (dealerValue > 21) {
				money += 2 * player.bet;
				reply(sender, "Dealer is bust! " + sender + " has won "
						+ player.bet + " LTTng Dolars for a total of " + money);
				playerDB.put(sender, money);
				player.reset();
			} else if (dealerValue < playerValue) {
				money += 2 * player.bet;
				reply(sender, "Dealer is bust! " + sender + " has won "
						+ player.bet + " LTTng Dolars for a total of " + money);
				playerDB.put(sender, money);
				player.reset();
			} else if (dealerValue == playerValue) {
				money += player.bet;
				reply(sender, "It's a push");
				playerDB.put(sender, money);
				player.reset();
			} else {
				reply(sender, "Dealer wins");
				player.reset();
			}

		}
	}

	public void reply(String sender, String message) {
		sendMessage(this.getChannels()[0], sender + ": " + message);
	}

}
