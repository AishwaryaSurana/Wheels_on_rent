package com.as.travela;

import android.util.Log;

public class City 
{
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
	
	/*@Override
	public int hashCode() {
		int hashcode=1;
		hashcode=hashcode*37+this.city_name.hashCode();
		return hashcode;
	}*/
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof City))
		{
			return false;
		}
		
		City c=(City)obj; 
		Log.e(c.city_name+" City",this.city_name+" City java");
		return this.city_name.equals(c.city_name);
		
		
	}
	
	
}
