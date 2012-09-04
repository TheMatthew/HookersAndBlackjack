/*
 * BlackJackGame.java
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

package com.matthew.hookersandblackjack.blackjackutil;

import java.util.ArrayList;

import com.matthew.hookersandblackjack.BlackJackBot;
import com.matthew.hookersandblackjack.HMDB;
import com.matthew.hookersandblackjack.blackjackutil.Player.status;

public class BlackjackGame {
	private Hand dealerHand = new Hand();
	private ArrayList<Player> players = new ArrayList<Player>();
	private HMDB playerDB;

	public enum status {
		notStarted, started, bet, dealt, hit, stand
	}

	public void register(Player player) {
		players.add(player);
		player.setGame(this);
	}

	public void unregister(Player player) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(player)) {
				players.remove(i);
				i--;
			}
		}
	}

	public void update() {
		boolean allwait = true;
		for (Player p : players) {
			allwait &= p.isWaiting();
		}
		if (allwait == true) {
			playDealer();
		}
		for (Player p : players) {
			for (Hand h : p.getHands()) {
				if (!h.isBusted()) {
					if (dealerHand.isBusted()) {

					} else if (dealerHand.getValue() < h.getValue()) {
						playerDB.put(p.getName(),
								playerDB.get(p.getName()) + h.win());
					}
				}
			}
		}

	}

	private void playDealer() {
		while( dealerHand.getValue() < 17){
			dealerHand.hit(BlackJackBot.getDeck().Deal());
		}
	}

	public boolean canBet(Player player) {
		return !player.hasBet();
	}

	public void deal(){

		reply(sender, "Dealer has " + dealerHand.peek().toString());
		reply(sender, "You have " + playerHand.toString());
		if (playerHand.getValue() == 21) {
			if (dealerHand.getValue() == 21) {
				reply(sender, "It's a blackjack push!");
				playerDB.put(sender,
						player.bet.getValue() + playerDB.get(sender));
			} else {
				reply(sender, "Dealer has blackjack!");
				player.reset();
			}
		} else if (dealerHand.getValue() == 21) {
			reply(sender, "Player has blackjack!");
			playerDB.put(
					sender,
					(playerDB.get(sender) + (long) (player.bet.getValue() * 2.5)));
			player.reset();

		} else {
			
			player.setStatus(status.dealt);
		}


	}
}