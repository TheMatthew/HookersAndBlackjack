/*
 * Player.java
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

public class Player {

	public enum status {
		notStarted, started, bet, dealt, stand
	}

	public status stat;

	public long bet;

	public Hand dealerHand;
	public status getStat() {
		return stat;
	}

	public Hand getDealerHand() {
		return dealerHand;
	}

	public Hand getPlayerHand() {
		return playerHand;
	}

	public Hand playerHand;

	public Player() {
		reset();
	}

	public void reset() {
		stat = status.notStarted;
		bet = 0;
	}

};
