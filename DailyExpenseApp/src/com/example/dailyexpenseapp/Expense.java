package com.example.dailyexpenseapp;

public class Expense {

	int expense_id;
	String item;
	float amount;
	String date;
	String bill;
	int user_id;
	
	public Expense() 
	{
	
	}

	public Expense(int expense_id, String item, float amount, String date,
			String bill, int user_id) {
		super();
		this.expense_id = expense_id;
		this.item = item;
		this.amount = amount;
		this.date = date;
		this.bill = bill;
		this.user_id = user_id;
	}

	public int getExpense_id() {
		return expense_id;
	}

	public void setExpense_id(int expense_id) {
		this.expense_id = expense_id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return item+","+amount+","+date;
	}
}
