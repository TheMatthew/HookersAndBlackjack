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

import java.util.ArrayList;

import com.matthew.hookersandblackjack.BlackJackBot;
import com.matthew.hookersandblackjack.HMDB;
import com.matthew.hookersandblackjack.bankUtil.Currency;
import com.matthew.hookersandblackjack.blackjackutil.BlackjackGame.status;

public class Player {

	final String name;
	Long money;
	Currency masterBet = null;
	HMDB playerDB;
	
	int currentHand;
	ArrayList<Hand> hands;

	public Player(String playerName, Long money) {
		this.money = money;
		name = playerName;
		currentHand = 0;
		stat = status.notplaying;
		
	}

	public HMDB getPlayerDB() {
		return playerDB;
	}

	public void setPlayerDB(HMDB playerDB) {
		this.playerDB = playerDB;
	}

	public BlackjackGame getGame() {
		return game;
	}

	public void setGame(BlackjackGame game) {
		this.game = game;
	}

	enum status {
		playing, notplaying,waiting
	};

	private status stat = status.notplaying;

	boolean persistBet = false;

	public BlackjackGame game;

	public status getStatus() {
		return stat;
	}

	public void setStatus(status stat) {
		this.stat = stat;
		if (stat == status.notplaying) {
			game.unregister(this);
			game = null;
		}
		game.update();
	}

	public void reset() {
		setStatus(status.notplaying);
		if (!persistBet) {

		}
	}

	public void doubleDown() {
		hit();
		stand();

	}

	public void bet(Long val ){
		masterBet = new Currency(val);
	}
	
	public void deal(){
		doBet();
		hands.add( new Hand() );
		hit();
		hit();
		game.deal();		
	}

	private void doBet() {
		long value = masterBet.getValue();
		getCurrentHand().pot = new Currency(value);
		playerDB.put(name, playerDB.get(name) - value);
	}
	
	public void hit() {
		getCurrentHand().hit( BlackJackBot.getDeck().Deal());
	}

	private Hand getCurrentHand() {
		return hands.get(currentHand);
	}

	public void stand() {
		if(hasMoreHands())
			currentHand++;
			else
				setStatus(status.waiting);
	}
	
	public void split(){
		if( canBet()){
			hands.add(getCurrentHand().split());
			doBet();
		}
	}

	private boolean canBet() {
		return playerDB.get(name) > masterBet.getValue();
	}

	private boolean hasMoreHands() {
		return currentHand < hands.size();
	}

	public boolean isWaiting() {
		return this.getStatus().equals(status.waiting);
	}

	public ArrayList<Hand> getHands() {
		return hands;
	}

	public String getName() {
		return name;
	}

	public void forfeit() {
		for(Hand h : hands){
			h.pot = new Currency( 0L );
		}
		setStatus(status.notplaying);
		
	}

	public boolean hasBet() {
		return this.stat.equals(status.notplaying);
	}

};
