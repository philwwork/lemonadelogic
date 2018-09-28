package com.game.main;

import java.util.List;

public class SampleEffect implements Effect
{

	private String ownerName;
	
	
	public SampleEffect(String ownerName) {
		this.ownerName = ownerName;
	}

	
	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	
	public void doEffect(Player player, List<String> log) {
		
		int lessCups = Math.min(7, player.getCups());
		
		player.setCups(player.getCups()-lessCups);
		log.add(String.format("You have %d less cups due to an effect from %s!", lessCups,ownerName));
		
	}

	@Override
	public EffectType getType() {
		return EffectType.SUPPLY;
	}




}