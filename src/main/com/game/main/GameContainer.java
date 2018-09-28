package com.game.main;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.game.main.utility.GameInfo;
import com.game.main.utility.Prices;
import com.game.main.utility.Turn;
import com.game.main.utility.TurnResults;
import com.game.main.utility.UserInfo;

public class GameContainer {

	private Hashtable<String, Game> games = new Hashtable<String, Game>();

	// token key for User object
	private Hashtable<String, User> loggedInUsers = new Hashtable<String, User>();


	private Hashtable<String,String> systemPasswords = new Hashtable<String,String>();
	
	private Hashtable<String, User> systemUsers = new Hashtable<String, User>();

	public void createGame(String title, String token) {
		
		
		User user = getUserByToken(token);
		
		if (user==null)
		{
			throw new IllegalStateException("Invalid user token.");
		}
		createGameWithUser(title, user);

	}

	public Game createGameWithUser(String title, User user) throws IllegalStateException {

		if (hasGame(title)) {
			throw new IllegalStateException("Game with that name already exists.");
		}
		Game aGame = new Game(title, this);
		aGame.addPlayer(user);

		games.put(title, aGame);
		return aGame;
	}

	public UserInfo createLogin(String username, String email, String password) {

		String token = createUser(username, email, password);
		return new UserInfo(username, email, token);
	}

	public String createUser(String username, String email, String password) {

		User user = new User(username, email);

		systemPasswords.put(email, hashFunction(password));
		systemUsers.put(email,  user);
		
		// Create a token for user
		String token = generateToken();
		loggedInUsers.put(token, user);

		return token;
	}

	private String hashFunction(String password) {
	
		// TODO fake it till you make it
		return ""+password.hashCode();
	}

	public void deleteGame(String title) {
		Iterator<Game> i = games.values().iterator();

		while (i.hasNext()) {
			Game g = i.next();
			if (g.getName().equals(title)) {
				i.remove();
			}
		}
	}

	
	
	
		
	
	private void removeTokenFromUser(User user)
	{
		// O(n) otherwise you're maintaining two Hashtables.
		// Remove previous token if any
		Set<Entry<String, User>> tempSet = loggedInUsers.entrySet();
		for (Entry<String, User> temp: tempSet)
		{
			if (temp.getValue().equals(user))
			{
				tempSet.remove(temp);
				return;
			}
		}
	}
	
	public UserInfo doLogin(String email, String password) {

		User user = systemUsers.get(email);
		if (user == null) {
			return new UserInfo("Invalid username or password.");
		}

		
		removeTokenFromUser(user);
		
		String token = generateToken();

		// Create a token
		loggedInUsers.put(token, user);

		return new UserInfo(user.getName(), email, token);
	}

	private String generateToken() {
		return UUID.randomUUID().toString();

	}

	private Game getGame(String title) {
		for (Game g : games.values()) {
			if (g.getName().equals(title)) {
				return g;
			}
		}
		return null;
	}

	public Game getGame(String title, User user) {
		Game game = games.get(title);
		if (game == null || game.hasPlayer(user))
			return game;
		else
			throw new IllegalStateException("You don't have access to this game.");
	}

	public List<Game> getGames() {
		List<Game> myGames = new LinkedList<Game>();
		myGames.addAll(games.values());
		return myGames;
	}

	public User getUserByName(String name) {

		for (User user : systemUsers.values()) {
			if (user.getName().equals(name))
				return user;
		}
		return null;
	}

	User getUserByToken(String token) {
		return loggedInUsers.get(token);
	}

	public boolean hasGame(Game game) {
		for (Game s : games.values()) {
			if (s.equals(game))
				return true;
		}
		return false;
	}

	public boolean hasGame(String name) {
		for (Game s : games.values()) {
			if (s.getName().equals(name))
				return true;
		}
		return false;
	}

	public boolean hasUser(String name) {

		return getUserByName(name) != null;
	}

	public boolean hasUsername(String username) {

		
		for (String s: systemUsers.keySet())
		{
			if (s.equals(username))
				return true;
		}
		return false;
	}

	public void joinGame(String title, User user) {
		Game myGame = getGame(title);
		myGame.addPlayer(user);
		games.put(title, myGame);
	}

	public void removeGame(Game game) {

		games.remove(game);

	}

	public GameInfo joinGame(String title, String token) {
		User user = getUserByToken(token);
		Game game = getGame(title);
		game.addPlayer(user);
		return game.getInfo();
	}

	public GameInfo getGameInfo(String title) {

		return games.get(title).getInfo();
		
		
	}

	public GameInfo startGame(String title, String token) {
		
		Game game = getGame(title);
		if (game==null)
			throw new IllegalStateException("No such game.");
		
		User user = getUserByToken(token);
		if (user==null)
			throw new IllegalStateException("No such user.");
		
		if (!game.hasPlayer(user))
			throw new IllegalStateException("Player not in that game.");

		if (!game.isOwner(user))
			throw new IllegalStateException("Only the owner can start the game.");

		game.start();
		return game.getInfo();
	}

	public TurnResults doTurn(Turn turn) {

		// TODO: How do you determine the game so that it can't be faked?
		User user = getUserByToken(turn.getUserToken());
		
		Game game = getGame(turn.getGameName());
				
		return game.doTurn(user,turn);
	}

	public Prices getPrices() {
		// TODO: not implemented
		return new Prices();
	}

}
