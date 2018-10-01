package com.game.main;

import java.util.List;

public class CupLessEffect extends Effect
{

	private final int LESS = 7;
	private final float PERCENT_LESS =.22f;
	

	public void doEffect(Player player, List<String> log) {
		
		// LESS or PERCENT_LESS, whichever is more.
		// i.e. 7 cups or 22% of cups (truncated), whichever is more.
		int percentLessCount= (int) ( ((float)player.getCups()) * PERCENT_LESS );
		int lessCups = Math.max(LESS, percentLessCount);
		
		lessCups = Math.min(lessCups, player.getCups());
		
		player.setCups(player.getCups()-lessCups);
		log.add(String.format("Neighborhood bully stomps on %d cups. He says '%s sends his regards'", lessCups,getOwnerName()));
		
	}


	public EffectType getType() {
		return EffectType.SUPPLY;
	}




}