package com.game.main;

import java.util.List;

public class SugarLessEffect extends Effect {


		private final float PERCENT_LESS =.82f;
		

		public void doEffect(Player player, List<String> log) {
			
			int percentLessCount= (int) ( ((float)player.getSugar()) * PERCENT_LESS );
			
			int lessSugar = Math.min(percentLessCount, player.getSugar());
			

			player.setSugar(player.getSugar()-lessSugar);
			log.add(String.format("Your rival %s paid a homeless guy to take your sugar! You have %d less sugar !", getOwnerName(),lessSugar));
			
		}


		public EffectType getType() {
			return EffectType.SUPPLY;
		}




	}
