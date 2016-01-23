package com.example.wheelsonrent;

public class City {
	String city_name;

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public City(String city_name) {
		super();
		this.city_name = city_name;
	}

	public City() {
		super();
	}

	@Override
	public String toString() {
		return city_name;
	}
	
	
}
