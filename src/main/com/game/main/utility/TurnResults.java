package com.game.main.utility;

import java.util.List;

public class TurnResults {

	private List<String> log;

	public void setLog(List<String> log) {
		this.log=log;
	}
	
	public List<String> getLog()
	{
		return log;
	}
	
	public int getLemons() {
		return lemons;
	}
	public void setLemons(int lemons) {
		this.lemons = lemons;
	}
	public int getIce() {
		return ice;
	}
	public void setIce(int ice) {
		this.ice = ice;
	}
	public int getWater() {
		return water;
	}
	public void setWater(int water) {
		this.water = water;
	}
	public int getSugar() {
		return sugar;
	}
	public void setSugar(int sugar) {
		this.sugar = sugar;
	}
	public int getCups() {
		return cups;
	}
	public void setCups(int cups) {
		this.cups = cups;
	}
	private int lemons;
	private int ice;
	private int water;
	private int sugar;
	private int cups;
	private int money;
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	private boolean win=false;

	public void setWin(boolean win) {
		this.win=win;
	}
	public boolean getWin()
	{
		return win;
	}
	
	public String toString()
	{
		String summary = "Lemons %d Ice %d Water %d Sugar %d Cups %d money %d";
		return String.format(summary, lemons, ice, water, sugar, cups, money);
	}
}
