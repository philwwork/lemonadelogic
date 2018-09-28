package com.game.main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.game.main.utility.GameInfo;
import com.game.main.utility.PlayerInfo;
import com.game.main.utility.Prices;
import com.game.main.utility.Turn;
import com.game.main.utility.TurnResults;

public class Game {

	private static final int WINNING_MONEY = 10000;
	public static int MAX_PLAYERS = 7;
	public static int MIN_PLAYERS = 3;
	public static int TURNS_PER_DAY = 7;
	private GameContainer container;

	boolean finished = false;

	private String name;

	Player owner;

	List<Player> players = new LinkedList<Player>();

	boolean started = false;

	public Game(String name, GameContainer container) {
		this.name = name;
		this.container = container;

	}

	public void addPlayer(User user) throws IllegalStateException {

		if (hasPlayer(user)) {
			throw new IllegalStateException("Same player added twice.");
		}

		if (players.size() + 1 > Game.MAX_PLAYERS) {
			throw new IllegalStateException("Greater than max players.");
		}

		Player player = new Player(user);
		if (players.size() == 0)
			setOwner(player);

		player.setTurns(Game.TURNS_PER_DAY);

		players.add(player);
	}

	private Prices getPrices() {
		Prices prices = container.getPrices();
		return prices;
	}

	public TurnResults doTurn(User user, Turn turn) throws IllegalStateException {

		if (!this.isStarted()) {
			throw new IllegalStateException("Playing turn before game started.");
		}

		if (this.isFinished()) {
			throw new IllegalStateException("Playing turn after game finished.");
		}

		Player turnPlayer = getPlayer(user);

		if (turnPlayer.getTurns() == 0) {
			throw new IllegalStateException("Playing turn without turns left.");
		}

		List<String> log = new ArrayList<String>();
		log.add(String.format("Player %s, %d turns remaining.", turnPlayer.getName(), turnPlayer.getTurns()));
		Prices prices = getPrices();

		turnPlayer.doBuy(turn, prices, log);

		if (turn.hasEffect()) {

			Effect effect = new SampleEffect(turnPlayer.getName());

			Player againstPlayer = getPlayer(turn.getAgainstName());
			againstPlayer.addEffect(effect);
		}

		// Need to process player's effect after the buy.
		// Effects affect supplies, which changes how much you can sell.

		ArrayList<String> playerNames = new ArrayList<String>();

		for (Player p : getPlayers()) {
			playerNames.add(p.getName());
		}

		log.add(String.format("You estimate that you can make %d cups of lemonade to sell.",
				turnPlayer.getSellableCount()));

		turnPlayer.processEffects(playerNames, log);
		log.add("You begin turning your supplies into lemonade.");

		turnPlayer.doSell(getPrices(), log);
		TurnResults finishedTurn = turnPlayer.getTurnResults(log);
		finishedTurn.setLog(log);
		turnPlayer.turnsMinusMinus();

		if (turnPlayer.getMoney() >= Game.WINNING_MONEY) {
			log.add("The game is over, you have won.");
			this.finish();
			finishedTurn.setWin(true);
			// TODO: Message to other players.
		}

		return finishedTurn;
	}

	public void finish() {
		if (!this.isStarted()) {
			throw new IllegalStateException("Finishing game before it is started.");
		}

		finished = true;
	}

	public String getName() {
		return name;
	}

	public Player getOwner() {
		return owner;
	}

	public Player getPlayer(User user) {

		for (Player aPlayer : players) {
			if (aPlayer.isUser(user))

				return aPlayer;
		}
		return null;
	}

	private Player getPlayer(String username) {
		for (Player aPlayer : players) {
			if (aPlayer.getName().equals(username))
				
			return aPlayer;
		}
		return null;
	}

	public int getPlayerCount() {
		return players.size();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public boolean isOwner(User user) {

		return getOwner().isUser(user);

	}

	public boolean hasPlayer(User user) {

		return getPlayer(user) != null;

	}



	public boolean isFinished() {
		return finished;
	}

	public boolean isStarted() {

		return started;
	}

	public void remove(User user) {

		Player player = getPlayer(user);
		players.remove(player);
		if (players.size() == 0) {
			container.removeGame(this);
			return;
		}

		if (getOwner().isUser(user)) {
			setOwner(players.get(0));
		}

	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void start() throws IllegalStateException {

		if (players.size() < MIN_PLAYERS) {
			throw new IllegalStateException("Illegal game state, starting game without minimum required players.");
		}



		started = true;

	}

	public GameInfo getInfo() {

		GameInfo gameInfo = new GameInfo();
		gameInfo.setName(this.getName());

		if (!isStarted()) {
			gameInfo.setStatus(GameInfo.StatusType.WAITING);
		}

		if (isStarted()) {
			gameInfo.setStatus(GameInfo.StatusType.STARTED);
		}

		if (isFinished()) {
			gameInfo.setStatus(GameInfo.StatusType.FINISHED);
		}

		PlayerInfo[] playerInfoArray = new PlayerInfo[this.getPlayers().size()];

		for (int i = 0; i < this.getPlayers().size(); i++) {
			playerInfoArray[i] = this.getPlayers().get(i).getPlayerInfo();
		}

		gameInfo.setPlayers(playerInfoArray);
		return gameInfo;
	}

}
