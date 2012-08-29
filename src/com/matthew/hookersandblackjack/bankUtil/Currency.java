package com.matthew.hookersandblackjack.bankUtil;

public class Currency {
	public Currency(Long value_){
		value = value_;
	}
	long value;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value + " LTTng dollars";
	}
	
}
