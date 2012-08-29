/*
 * Deck.java
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
import java.util.Collections;

public class Deck {
	ArrayList<Card> cards = new ArrayList<Card>();

	public Deck() {
		for (int j = 0; j < 5; j++)
			addDeck();
	}

	public void addDeck() {
		for (int i = 0; i < 52; i++) {
			cards.add(new Card(i));
		}
		Collections.shuffle(cards);
	}

	public Card Deal() {
		Card ret = cards.remove(cards.size() - 1);
		if (remain() < 52 * 2) {
			addDeck();
		}
		return ret;
	}

	public int remain() {
		return cards.size();
	}
}
