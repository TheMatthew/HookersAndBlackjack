/*
 * main.java
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
/**
 * 
 */
package com.matthew.hookersandblackjack;

import java.io.IOException;

/**
 * @author Matthew
 * 
 */
public class Main {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		HookerBot localhb;
		BlackJackBot localbb;
		HMDB playerDB = null;
		IrcRunnable hookerRunnable = new IrcRunnable();
		IrcRunnable blackjackRunnable = new IrcRunnable();
		Thread hookerThread = new Thread(hookerRunnable);
		Thread blackjackThread = new Thread(blackjackRunnable);
		localhb = new HookerBot();
		localbb = new BlackJackBot();

		try {
			playerDB = new HMDB("bank");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		localbb.setPlayerDB(playerDB);
		localhb.setPlayerDB(playerDB);
		hookerRunnable.setBot(localhb);
		hookerRunnable.setChannel("#dorsal-fun");
		blackjackRunnable.setChannel("#dorsal-fun");
		blackjackRunnable.setBot(localbb);
		blackjackThread.start();
		while (!localbb.isConnected())
			Thread.sleep(1000);
		hookerThread.start();
		blackjackThread.setName("BlackJack");
		hookerThread.setName("Hooker");
		blackjackThread.join();
		hookerThread.join();

	}
}
