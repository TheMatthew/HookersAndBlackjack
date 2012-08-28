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
			playerDB = new HMDB("temp.txt");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		localbb.setPlayerDB(playerDB);
		localhb.setPlayerDB(playerDB);
		hookerRunnable.setBot(localhb);
		blackjackRunnable.setBot(localbb);
		hookerThread.start();
		while (!localhb.isConnected())
			Thread.sleep(1000);
		blackjackThread.start();
		blackjackThread.setName("BlackJack");
		hookerThread.setName("Hooker");
		blackjackThread.join();
		hookerThread.join();

	}
}
