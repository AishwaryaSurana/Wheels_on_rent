package com.example.wheelsonrent;

public class State {
	String state_name;
	State()
	{
		
	}
	State(String state_name)
	{
		state_name=this.state_name;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return state_name;
	}
	

}
