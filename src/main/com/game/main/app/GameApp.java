package com.game.main.app;

import com.game.main.Game;
import com.game.main.GameContainer;
import com.game.main.Player;
import com.game.main.utility.GameInfo;
import com.game.main.utility.Turn;
import com.game.main.utility.TurnResults;
import com.game.main.utility.UserInfo;

public class GameApp {

	private GameContainer container = new GameContainer();
	
	public UserInfo doLogin(String email, String password) {
		UserInfo login = container.doLogin(email,password);
		return login;
	}

	public UserInfo createLogin(String username, String email, String password) {
		
		
		if (container.hasUsername(username))
		{
			UserInfo aLogin = new UserInfo("Username already exists.");
			return aLogin;
		}
		
		
		
		UserInfo login = container.createLogin(username,email, password);
		
		
		return login;
		
		
	}

	public void createGame(String title, String token) {
		
		
		container.createGame(title,token);
			


		}
		
		
	

	public void showGames()
	{
		// Return a list of games back to the browser.
		for (Game game: container.getGames())
		{
		
				log(game.getName());
				
				String userList = "";
				for (Player player: game.getPlayers())
				{
					userList += player.getName()+"\t";
				}
				log(userList);
		}
	}
	
	
	public void log(String text)
	{
		System.out.println(text);
	}

	public GameInfo joinGame(String title, String token) {
	

		return container.joinGame(title, token);
	}

	public GameInfo sendGameInfo(String title) {
		
		
		return container.getGameInfo(title);
		
	}

	public GameInfo startGame(String title, String token) {
		
		return container.startGame(title, token);
		
	}

	public TurnResults doTurn(Turn philsTurn) {
		return container.doTurn(philsTurn);
		
	}
}

