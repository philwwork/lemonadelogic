package com.game.main;

import java.util.List;


public interface Effect
{

	public enum EffectType {SUPPLY,SELL}
	
	
	
	public EffectType getType();
	
	public void doEffect(Player player, List<String> log);
	public String getOwnerName();
	

}

