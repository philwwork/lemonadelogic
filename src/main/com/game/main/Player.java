package com.game.main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.game.main.utility.PlayerInfo;
import com.game.main.utility.Prices;
import com.game.main.utility.Turn;
import com.game.main.utility.TurnResults;

public class Player {

	private Queue<Effect> effectQueue = new LinkedList<Effect>();

	private int turns;
	private int lemons = 70;
	private int ice = 70;
	private int water = 70;
	private int sugar = 70;
	private int cups = 70;
	private int money = 1000;

	private User user;
	private Effect effect;
	
	
	public Player(User aUser) {
		this.user = aUser;
	}

	public String getName() {
		return user.getName();
	}

	public int getTurns() {
		return turns;
	}

	public boolean isUser(User aUser) {
		return user.equals(aUser);
	}

	public void setTurns(int turns) {
		this.turns = turns;

	}

	public void turnsMinusMinus() {
		turns--;

	}

	public PlayerInfo getPlayerInfo() {

		PlayerInfo playerInfo = new PlayerInfo();

		playerInfo.setName(this.getName());
		playerInfo.setScore("" + this.getMoney());
		return playerInfo;
	}

	private int buyLemons(int count, int price) {
		int cost = count * price;
		money -= cost;
		lemons += count;
		return count;
	}

	private int buyIce(int count, int price) {
		int cost = count * price;
		money -= cost;
		ice += count;
		return count;
	}

	private int buyWater(int count, int price) {
		int cost = count * price;
		money -= cost;
		water += count;
		return count;
	}

	private int buySugar(int count, int price) {
		int cost = count * price;
		money -= cost;
		sugar += count;
		return count;
	}

	private int buyCups(int count, int price) {
		int cost = count * price;
		money -= cost;
		cups += count;
		return count;
	}

	public int doSell(Prices p, List<String> log) {
		int toSell = getSellableCount();

		if (toSell > 0) {
			setLemons(getLemons() - toSell);
			setIce(getIce() - toSell);
			setWater(getWater() - toSell);
			setSugar(getSugar() - toSell);
			setCups(getCups() - toSell);

			setMoney(money + p.getLemonadePrice() * toSell);
		}

		log.add(String.format("Sold %d cups of Lemonade today, good jorb.", toSell));
		return toSell;
	}

	public int getSellableCount() {
		int temp = Math.min(getLemons(), getIce());
		temp = Math.min(temp, getWater());
		temp = Math.min(temp, getCups());
		temp = Math.min(temp, getSugar());
		return temp;
	}

	public void doBuy(Turn turn, Prices prices, List<String> log) {

		// Perform buy, throw exception if request invalid.
		int boughtLemons = buyLemons(turn.getLemonsToBuy(), prices.getLemonsPrice());
		log.add(String.format("Bought %d lemons for %d.", boughtLemons, boughtLemons * prices.getLemonsPrice()));

		int boughtIce = buyIce(turn.getIceToBuy(), prices.getLemonsPrice());
		log.add(String.format("Bought %d ice for %d.", boughtIce, boughtIce * prices.getIcePrice()));

		int boughtWater = buyWater(turn.getWaterToBuy(), prices.getLemonsPrice());
		log.add(String.format("Bought %d water for %d.", boughtWater, boughtWater * prices.getWaterPrice()));

		int boughtCups = buyCups(turn.getCupsToBuy(), prices.getLemonsPrice());
		log.add(String.format("Bought %d cups for %d.", boughtCups, boughtCups * prices.getCupsPrice()));

		int boughtSugar = buySugar(turn.getSugarToBuy(), prices.getLemonsPrice());
		log.add(String.format("Bought %d sugar for %d.", boughtSugar, boughtSugar * prices.getSugarPrice()));
	
		buyEffect(turn.getEffectName(), turn.getAgainstName(), prices.getEffectPrice());
		log.add(String.format("Bought %s effect for %d against %s.", turn.getEffectName(), prices.getEffectPrice(), turn.getAgainstName()));
	}

	
	private void buyEffect(String effectName, String againstName, int price)
	{
		int cost = price;
		setMoney(money - cost);
		
		readyEffect(new SampleEffect(againstName));
		
	}

	private void readyEffect(SampleEffect effect) {
		this.effect=effect;
		
	}

	public boolean hasEffect()
	{
		return effect!=null;
	}
	
	
	public Effect getEffect()
	{
		return effect;
	}
	
	
	
	public TurnResults getTurnResults(List<String> log) {
		TurnResults finishedTurn = new TurnResults();

		finishedTurn.setLemons(getLemons());
		finishedTurn.setIce(getIce());
		finishedTurn.setWater(getWater());
		finishedTurn.setSugar(getSugar());
		finishedTurn.setCups(getCups());
		finishedTurn.setMoney(getMoney());
		finishedTurn.setLog(log);
		return finishedTurn;
	}

	// Up to one from each player, per turn.
	public void processEffects(List<String> playersWithUnusedEffect, List<String> log) {

		
		Iterator<Effect> i = effectQueue.iterator();
		while (i.hasNext()) {
			
			Effect e = i.next();
			if (playersWithUnusedEffect.contains(e.getOwnerName()))
			{
				e.doEffect(this,log);
				i.remove();
				playersWithUnusedEffect.remove(e.getOwnerName());
				if (playersWithUnusedEffect.size()==0)
					break;
			}
		}
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;

		if (money < 0) {
			throw new IllegalArgumentException("Negative money.");
		}
	}

	public void addEffect(Effect effect) {
		effectQueue.add(effect);
	}

}
