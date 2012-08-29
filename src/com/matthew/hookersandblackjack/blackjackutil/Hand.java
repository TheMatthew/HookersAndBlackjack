/*
 * Hand.java
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

public class Hand {
	ArrayList<Card> cards = new ArrayList<Card>();

	public int getValue() {
		int value = 0;
		for (Card c : cards) {
			if (value + 11 > 21 && c.value == 11)
				value++;
			else
				value += c.value;

		}
		return value;
	}

	public void hit(Card c) {
		cards.add(c);
		Collections.sort(cards);

	}

	public boolean isBusted() {
		return (getValue() > 21);
	}

	public Card peek() {
		return cards.get(0);
	}

	@Override
	public String toString() {
		String s = cards.get(0).toString();

		for (int i = 1; i < cards.size(); i++) {
			s += ", " + cards.get(i).toString();
		}
		return s;
	}
}

