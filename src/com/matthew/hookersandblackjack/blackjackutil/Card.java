/*
 * Card.java
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


public class Card implements Comparable<Card> {
	final static String suites[] = { "Hearts", "Spades", "Clubs", "Diamonds" };

	final static String numbers[] = { "Ace", "Two", "Three", "Four", "Five",
			"Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };

	final String suite;

	final int number;;

	final int id;
	final int value;

	public Card(int pos) {
		id = pos;
		number = pos % 13;
		int su = pos % 4;
		suite = suites[su];
		if ((number + 1) > 10)
			value = 10;
		else if (number == 0)
			value = 11;
		else
			value = number + 1;
	}

	@Override
	public int compareTo(Card o) {
		if (value < o.value)
			return -1;
		if (value > o.value)
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return numbers[number] + " of " + suite;
	}
}
