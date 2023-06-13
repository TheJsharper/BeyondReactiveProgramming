package com.jsharper.utils;

public enum COUNT {
	TEN(10), HUNDRED(100), TWO_HUNDRED(200), THREE_HUNDRED(300), FOUR_HUNDRED(400), FIVE_HUNDRED(500), TAUSEND(1000);

	private final int value;

	COUNT(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}