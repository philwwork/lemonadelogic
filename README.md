# A Lemonade stand type game
# This is an unfinished demo of how the logic of the game should work. 

My idea for a Lemonade stand game is that of a competitive multiplayer, mostly text game, play when you want game.

You are in a competition to raise $10,000 for charity, and the first kid to do so wins.

You buy supplies, and sell lemonade to reach your goal. The difference between this and the cannonical lemonade stand games is that there is a competitive aspect. The players join a game, and can attempt to sabotage each others' progress by purchasing "effects." These effects will slow down the financial success of the other players by destroying supplies, or otherwise causing mischief. 

Examples of effects include paying people to destroy or steal the other player's supplies, spreading silly rumors about the other players' cheap purchasing habits, etc. Perhaps improving yourself with bonuses to luck or efficiency could be an effect. In any event, there is a cost vs gain in terms of money, and in terms of the attention of the other players. As well as inter-player effects, a separate queue of natural effects should be in place. These effects should change how much people buy or randomly affect supplies.

Regarding prices, right now the prices fluctuate randomly. I would like to try to make more sense of the pricing scheme. Of note, all players will get the same prices. The prices will change randomly but will be fixed for each set of turns for the day. This should help reward good decision making.

The classic game choice between building yourself up, and tearing your competition down will reign in this game. As well, with the multi-player aspect, temporary truces and bitter rivalries will add to the excitement. And it's all in fun. The game at a minimum will require 3 players.

The idea was inspired by my own experiences with multi-player board games, and classic text only games like Lemonade stand.

The game is asynchronous, which of course means the players won't have to be playing at the same time. The game uses a turn based "queue" system to queue up effects and news (i.e. status) to the other players. As a player you will only have so many turns per day, and each of those turns correspond to some other player's turn, even if they don't happen until later.

If you don't use your turns for that day, that's too bad. The other players will make more money and have more chances to sabotage you. The effects that they queue up, however, are independent of your delinquency; they will just wait until you use your next turns to be applied. Nothing goes to waste*, except your own turns. Of note, you will process one effect per each opposing player per turn. *If you miss your turns, another player can queue up more effects on you than will be used by the game's end, but the ones that are queued will never be dropped. For now, a player may purchase only one effect per turn, use it wisely.

Design decisions such as how much granularity to put into the buying system, (how many ounces, lemons, etc) have been decided by using a unit system. Each "unit" of the supplies is enough to make 1 cup of lemonade. You're going to have to suspend disbelief (if you haven't already). I will probably improve on this concept later.

Currently the game uses "cents" as currency for the simplicity of building a demo. However I would like to switch to dollars and cents because it's not much more effort and it's just better for quality. I tried to think of cents as being like coins in a role playing game, but it probably should just be dollars and cents. For now I would rather focus on making a more functional UI however.

Unimplemented, but important to the game is the concept of demand, and its factors such as weather. Ideally, on a hot day people will buy more and/or pay more per cup. On a cool/windy/rainy day they will buy less. Currently you will automatically sell as much as can be made with your supplies. It follows that the players' buying choices should be influenced by demand so it needs to happen before buying supplies. As well the player should be able to choose how much lemonade to make (and hence be able to hoard their supplies for better days), which is unimplemented. 

The ambient demand change can be implemented as an effect type queue. It should be the same for all players per turn each day (since they presumably live in the same city). The post buy/pre-sale natural effects should be different (random) per player, as they are unpredictable and have more to do with dumb luck.

The sequence of the turn is as follows:

	(unimplemented) automatic: Report on demand factors (weather etc) for this 	turn.

	player: Player makes supply purchasing/effect purchasing decisions.

	player: Player ends the turn. 

	automatic: The purchase happens. The quantity of lemonade that can be sold is estimated. This is what you *should* sell without regard to effects.

	automatic: Effects are processed. These affects may change the amount of supplies you have before the sale happens, affecting the quantity of your sale. 

	automatic: The sale happens, supply amounts and money are updated.



After each turn a status summary page should have recent info on the amount of cash (score) per player. Supply quantities will be private.







 



