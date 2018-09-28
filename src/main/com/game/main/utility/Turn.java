package com.game.main.utility;

public class Turn {
	
	private String gameName;
	private String userToken;
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Turn(String gameName)
	{
		this.gameName=gameName;
	}

	public int getLemonsToBuy() {
		return lemonToBuy;
	}

	public void setLemonToBuy(int numLemons) {
		this.lemonToBuy = numLemons;
	}

	public int getIceToBuy() {
		return iceToBuy;
	}

	public void setIceToBuy(int numIce) {
		this.iceToBuy = numIce;
	}

	public int getWaterToBuy() {
		return waterToBuy;
	}

	public void setWaterToBuy(int numWater) {
		this.waterToBuy = numWater;
	}

	public int getSugarToBuy() {
		return sugarToBuy;
	}

	public void setSugarToBuy(int numSugar) {
		this.sugarToBuy = numSugar;
	}

	public int getCupsToBuy() {
		return cupsToBuy;
	}

	public void setCupsToBuy(int numCups) {
		this.cupsToBuy = numCups;
	}

	private int lemonToBuy;
	private int iceToBuy;
	private int waterToBuy;
	private int sugarToBuy;
	private int cupsToBuy;
	
	public String getEffectName() {
		return effectName;
	}

	public void setEffectName(String effectName) {
		this.effectName = effectName;
	}

	String effectName, againstName;

	public String getAgainstName() {
		return againstName;
	}

	public void setAgainstName(String againstPlayer) {
		this.againstName = againstPlayer;
	}

	public void setUserToken(String token) {
		userToken=token;
		
	}
	public String getUserToken()
	{
		return userToken;
	}

	public boolean hasEffect() {

		return effectName!=null;
	}

}

