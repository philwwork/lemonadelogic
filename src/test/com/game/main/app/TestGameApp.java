package com.game.main.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.game.main.utility.GameInfo;

import com.game.main.utility.Turn;
import com.game.main.utility.TurnResults;
import com.game.main.utility.UserInfo;

public class TestGameApp {

	/**
	 * This test class simulates a browser application playing the game.
	 * 
	 */

	@Test
	public void testCreateLogin() {
		String email = "phil@gmail.com";
		String password = "fakepass";
		String username = "Phil";

		GameApp myApp = new GameApp();

		UserInfo result = myApp.createLogin(username, email, password);
		assertTrue(result.getName().equals(username));
		assertTrue(result.getEmail().equals(email));
		assertTrue(result.getToken() != null);

		
		myApp.createLogin("Fred" , "fred@gmail.com",  "fredpassword");
		myApp.createLogin("abe",  "abe@gmail.com", "abepassword");
		
		UserInfo otherResult = myApp.createLogin("john", "john@gmail.com", "johnpassword");
		assertTrue(otherResult.getName().equals("john"));
		assertTrue(otherResult.getEmail().equals("john@gmail.com"));
		assertTrue(otherResult.getToken() !=null);
		assertTrue(otherResult.getToken() !=result.getToken());
	
	}

	
	private GameApp afterCreateUsers()
	{
		
		String email = "phil@gmail.com";
		String password = "fakepass";
		String username = "Phil";

		GameApp myApp = new GameApp();

		myApp.createLogin(username, email, password);
		myApp.createLogin("Fred" , "fred@gmail.com",  "fredpassword");
		myApp.createLogin("abe",  "abe@gmail.com", "abepassword");
		myApp.createLogin("john", "john@gmail.com", "johnpassword");
		return myApp;
	}
	
	@Test
	public void testDoLogin() {

		GameApp myApp = afterCreateUsers();

		// UserInfo is what will be sent back to the browser.
		UserInfo myLogin = myApp.doLogin("phil@gmail.com", "fakepass");

		assertNull(myLogin.getMessage());
		assertTrue(myLogin.getEmail().equals("phil@gmail.com"));
		assertTrue(myLogin.getName().equals("Phil"));
		assertNotNull(myLogin.getToken());
		
		
		myApp.doLogin("fred@gmail.com", "fredpassword");
		myApp.doLogin("john@gmail.com", "johnpassword");
		
		UserInfo otherLogin =myApp.doLogin("abe@gmail.com", "abepassword");
		assertNotNull(otherLogin.getToken());
		assertTrue(otherLogin!=myLogin);
	}
	
	@Test 
	public void testCreateGame() {
		
		GameApp myApp = afterCreateUsers();

		UserInfo myLogin = myApp.doLogin("phil@gmail.com", "fakepass");
		myApp.createGame("whatever", myLogin.getToken());
		
		
	}
	
	
	@Test
	public void testJoinGame()
	{
		
		GameApp myApp = afterCreateUsers();

		UserInfo phil = myApp.doLogin("phil@gmail.com", "fakepass");
		myApp.createGame("whatever", phil.getToken());
		
		UserInfo fred = myApp.doLogin("fred@gmail.com",  "fredpassword");
		UserInfo john = myApp.doLogin("john@gmail.com", "johnpassword");
		UserInfo abe = myApp.doLogin("abe@gmail.com", "abepassword");
		
		myApp.joinGame("whatever", fred.getToken());
		myApp.joinGame("whatever", abe.getToken());
		myApp.joinGame("whatever", john.getToken());
		
		GameInfo gameInfo = myApp.sendGameInfo("whatever");
				
		assertTrue(gameInfo.getStatus()==GameInfo.StatusType.WAITING);
		assertTrue(gameInfo.getName().equals("whatever"));
		
	
		assertNotNull(gameInfo.getPlayerInfo(fred.getName()));
		assertNotNull(gameInfo.getPlayerInfo(phil.getName()));
		assertNotNull(gameInfo.getPlayerInfo(abe.getName()));
		assertNotNull(gameInfo.getPlayerInfo(john.getName()));
		
	}
	
	
	private UserInfo createGamePhil(GameApp myApp)
	{
		UserInfo phil = myApp.doLogin("phil@gmail.com", "fakepass");
		myApp.createGame("whatever", phil.getToken());
		return phil;
	}

	private UserInfo joinGameFred(GameApp myApp)
	{
		UserInfo fred = myApp.doLogin("fred@gmail.com",  "fredpassword");
		myApp.joinGame("whatever", fred.getToken());
		return fred;
	}
	
	private UserInfo joinGameAbe(GameApp myApp)
	{
		UserInfo abe = myApp.doLogin("abe@gmail.com", "abepassword");
		myApp.joinGame("whatever", abe.getToken());
		return abe;
	}
	
	
	private UserInfo joinGameJohn(GameApp myApp)
	{
		UserInfo john = myApp.doLogin("john@gmail.com", "johnpassword");
		myApp.joinGame("whatever", john.getToken());
		return john;
	}
	
	@Test
	public void testDoTurn()
	{
		GameApp myApp = afterCreateUsers();
		UserInfo phil = createGamePhil(myApp);
		UserInfo john = joinGameJohn(myApp);
		UserInfo abe = joinGameAbe(myApp);
		UserInfo fred = joinGameFred(myApp);
		
		
		GameInfo gameInfo = myApp.startGame("whatever", phil.getToken());
		
		assertTrue(gameInfo.getStatus()==GameInfo.StatusType.STARTED);
	
		
		for (int i=0; i < 2; i++)
		{
			Turn philsTurn = new Turn(gameInfo.getName());
			
			philsTurn.setLemonToBuy(7);
			philsTurn.setIceToBuy(8);
			philsTurn.setWaterToBuy(9);
			philsTurn.setSugarToBuy(10);
			philsTurn.setCupsToBuy(7);
			philsTurn.setEffectName("cupless");
			philsTurn.setAgainstName("Fred");
			philsTurn.setUserToken(phil.getToken());
			
			TurnResults results = myApp.doTurn(philsTurn);
			
			log(results.getLog());
			System.out.println(results.toString());
		}
		
		for (int i=0; i < 7; i++)
		{
			Turn fredsTurn = new Turn(gameInfo.getName());
		
			fredsTurn.setLemonToBuy(1);
			fredsTurn.setIceToBuy(2);
			fredsTurn.setWaterToBuy(3);
			fredsTurn.setSugarToBuy(4);
			fredsTurn.setCupsToBuy(5);
			fredsTurn.setEffectName("sugarless");
			fredsTurn.setAgainstName("Phil");
			fredsTurn.setUserToken(fred.getToken());
			
			TurnResults results = myApp.doTurn(fredsTurn);
			
			log(results.getLog());
			System.out.println(results.toString());
		}
		
		for (int i=0; i <5; i++)
		{
			Turn philsTurn = new Turn(gameInfo.getName());
			
			philsTurn.setLemonToBuy(7);
			philsTurn.setIceToBuy(8);
			philsTurn.setWaterToBuy(9);
			philsTurn.setSugarToBuy(10);
			philsTurn.setCupsToBuy(7);
			philsTurn.setEffectName("sugarless");
			philsTurn.setAgainstName("Fred");
			philsTurn.setUserToken(phil.getToken());
			
			TurnResults results = myApp.doTurn(philsTurn);
			
			log(results.getLog());
			System.out.println(results.toString());
		}
		
		
	}

	private void log(List<String> log)
	{
		System.out.println();
		for (String s: log)
		{
			System.out.println(s);
			
		}
	}
}
