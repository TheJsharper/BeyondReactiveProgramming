package com.jsharper.utils;

import com.github.javafaker.Country;
import com.github.javafaker.Faker;

public class CountryLocal extends Country {

	public  CountryLocal(Faker faker) {
		super(faker);
	}

	@Override
	public String toString() {
		return "CountryLocal [flag()=" + flag() + ", countryCode2()=" + countryCode2() + ", countryCode3()="
				+ countryCode3() + ", capital()=" + capital() + ", currency()=" + currency() + ", currencyCode()="
				+ currencyCode() + ", name()=" + name() + "]";
	}

}
