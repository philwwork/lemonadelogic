package com.game.main.utility;

public class GameInfo {

	public enum StatusType {WAITING,STARTED,FINISHED,ERROR};
	
	public StatusType getStatus() {
		return status;
	}
	public void setStatus(StatusType status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PlayerInfo[] getPlayers() {
		return players;
	}
	public void setPlayers(PlayerInfo[] players) {
		this.players = players;
	}
	
	
	public String getTopScore() {
		return topScore;
	}
	public void setTopScore(String topScore) {
		this.topScore = topScore;
	}
	public PlayerInfo getTopPlayer() {
		return topPlayer;
	}
	public void setTopPlayer(PlayerInfo topPlayer) {
		this.topPlayer = topPlayer;
	}

	public PlayerInfo getPlayerInfo(String name)
	{
		for (PlayerInfo p: getPlayers())
		{
			if (p.getName().equals(name))
			{
				return p;
			}
		}
		return null;
		
	}

	private StatusType status;
	
	private String name;
	private PlayerInfo[] players;
	
	private String topScore;
	private PlayerInfo topPlayer;
	
	
}
