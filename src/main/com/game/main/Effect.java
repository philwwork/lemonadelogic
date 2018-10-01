package com.game.main;

import java.util.HashMap;
import java.util.List;


public abstract class Effect
{
	
	private static HashMap<String, Class<? extends Effect>> effectClasses;
	
	static 
	{
		
			effectClasses = new HashMap<String, Class<? extends Effect>>();
			effectClasses.put("cupless",  CupLessEffect.class);
			effectClasses.put("sugarless",  SugarLessEffect.class);
	}
	
	
	
	
	public static Effect getNewEffect(String name)
	{
		
		Class<? extends Effect> someEffectClass = effectClasses.get(name);

		
		Effect anEffect = null;
		try {
			anEffect = (Effect)someEffectClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return anEffect;
	}
		
	public enum EffectType {SUPPLY,SELL}

	
	private String ownerName;	
	
	
	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public abstract EffectType getType();
	
	public abstract void doEffect(Player player, List<String> log);

	

}

