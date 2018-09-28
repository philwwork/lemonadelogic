package com.game.main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.game.main.utility.Turn;

public class TestGame {

	private static GameContainer testContainer;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void setUpBeforeClass() {

		testContainer = new GameContainer();
	}

	private Game createGameWithUser(String title, User user) {

			return testContainer.createGameWithUser(title, user);
	}

	private User createUser(String name, String email) {
		
		if (testContainer.hasUser(name))
			return testContainer.getUserByName(name);
			
		else
		{
			testContainer.createUser(name, email, "password");
			return testContainer.getUserByName(name);
		}
	}

	@Test
	public void testAddPlayerSimple() {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("generic",phil);


		User Clint = createUser("Clint", "clint@gmail.com");
		User Abe = createUser("Abe", "abe@gmail.com");

		// Test simple adding
		assertTrue(myGame.hasPlayer(phil));
		assertFalse(myGame.hasPlayer(Clint));

		myGame.addPlayer(Clint);
		assertTrue(myGame.hasPlayer(Clint));
		assertFalse(myGame.hasPlayer(Abe));

		myGame.addPlayer(Abe);
		assertTrue(myGame.hasPlayer(Abe));
	}

	@Test
	public void testStartBad() {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("First",phil);

		User Clint = createUser("Clint", "clint@gmail.com");
		myGame.addPlayer(Clint);

		// Test too few players for game start
		exception.expect(IllegalStateException.class);
		myGame.start();
		
	}

	
	@Test
	public void testStartGood() {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("start game",phil);

		User Clint = createUser("Clint", "clint@gmail.com");
		myGame.addPlayer(Clint);

		User Abe = createUser("Abe", "abe@gmail.com");
		myGame.addPlayer(Abe);
		
		assertTrue(myGame.hasPlayer(Abe));

		myGame.start();
		assertTrue(myGame.isStarted());
	}
	
	@Test
	public void testAddPlayerSameUserTwice() {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game otherGame = createGameWithUser("Second",phil);

		User clint = createUser("Clint", "clint@gmail.com");
		User abe = createUser("Abe", "abe@gmail.com");

		otherGame.addPlayer(clint);
		otherGame.addPlayer(abe);

		// Test more than one game per user allowed. (1 user -> many games)
		assertTrue(otherGame.hasPlayer(phil));

		// Test cannot add same user
		exception.expect(IllegalStateException.class);
		otherGame.addPlayer(phil);

	}

	@Test
	public void testAddPlayerMax() {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game someGame = createGameWithUser("whatever",phil);

		
		exception.expect(IllegalStateException.class);

		// Test too many players
		for (int i = 1; i <= Game.MAX_PLAYERS; i++) {
			User tempUser = createUser("name" + i, "a" + i + "email@address");
			someGame.addPlayer(tempUser);
		}

		assertTrue(someGame.getPlayerCount() == Game.MAX_PLAYERS+1);

	}

	@Test
	public void testGetPlayer() {

		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("my game",phil);

		User Clint = new User("Clint", "clint@gmail.com");
		User Abe = new User("Abe", "abe@gmail.com");

		myGame.addPlayer(Clint);

		assertNotNull(myGame.getPlayer(phil));
		assertNotNull(myGame.getPlayer(Clint));

		assertNull(myGame.getPlayer(Abe));
		myGame.addPlayer(Abe);

		assertNotNull(myGame.getPlayer(Abe));
	}
	
	@Test
	public void testRemovePlayer() {
		
		User john = createUser("John", "john@gmail.com");
		Game myGame = createGameWithUser("aGame",john);
		assertNotNull(myGame.getPlayer(john));
		
		User clint = new User("Clint", "clint@gmail.com");
		User abe = new User("Abe", "abe@gmail.com");

		myGame.addPlayer(clint);
		assertNotNull(myGame.getPlayer(clint));
		
		myGame.remove(john);
		assertNull(myGame.getPlayer(john));
		assertTrue(myGame.getOwner().isUser(clint));
		
		myGame.remove(clint);
		assertNull(myGame.getPlayer(abe));
		myGame.addPlayer(abe);

		assertNotNull(myGame.getPlayer(abe));
	}
	
	
	
	
	

	@Test
	public void testDoTurnBeforeStart() throws Exception {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("myGame",phil);


		User clint = createUser("Clint", "clint@gmail.com");
		User abe = createUser("Abe", "abe@gmail.com");
		User john = createUser("John", "john@gmail.com");

		myGame.addPlayer(clint);
		myGame.addPlayer(abe);
		myGame.addPlayer(john);

		
		// Test do turn without start.
		Turn myTurn = new Turn(myGame.getName());

		myTurn.setLemonToBuy(7);
		myTurn.setIceToBuy(7);
		myTurn.setWaterToBuy(7);
		myTurn.setSugarToBuy(7);
		myTurn.setCupsToBuy(7);

		exception.expect(IllegalStateException.class);
		myGame.doTurn(phil, myTurn);


	}

	@Test
	public void testDoTurnAfterFinish() throws Exception {
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("b game",phil);

		User clint = createUser("Clint", "clint@gmail.com");
		User abe = createUser("Abe", "abe@gmail.com");
		User john = createUser("John", "john@gmail.com");

		myGame.addPlayer(clint);
		myGame.addPlayer(abe);
		myGame.addPlayer(john);

		
		
		// Test do turn after finish.
		myGame.start();

		myGame.finish();
		
		Turn myTurn = new Turn(myGame.getName());

		myTurn.setLemonToBuy(7);
		myTurn.setIceToBuy(7);
		myTurn.setWaterToBuy(7);
		myTurn.setSugarToBuy(7);
		myTurn.setCupsToBuy(7);
		
		exception.expect(IllegalStateException.class);
		myGame.doTurn(phil, myTurn);
	}

	@Test
	public void testFinishStart()
	{
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("if game",phil);

		User clint = createUser("Clint", "clint@gmail.com");
		User abe = createUser("Abe", "abe@gmail.com");
		User john = createUser("John", "john@gmail.com");

		myGame.addPlayer(clint);
		myGame.addPlayer(abe);
		myGame.addPlayer(john);

		
		exception.expect(IllegalStateException.class);
		myGame.finish();
		
		
	}
	
	@Test
	public void testDoTurnNoTurnsLeft()
	{
		
		User phil = createUser("Phil", "phil7scott7@gmail.com");
		Game myGame = createGameWithUser("ggame",phil);

		User clint = createUser("Clint", "clint@gmail.com");
		User abe = createUser("Abe", "abe@gmail.com");
		User john = createUser("John", "john@gmail.com");

		myGame.addPlayer(clint);
		myGame.addPlayer(abe);
		myGame.addPlayer(john);

		myGame.start();
		
		Turn myTurn = new Turn(myGame.getName());
		
		myTurn.setLemonToBuy(0);
		myTurn.setIceToBuy(0);
		myTurn.setWaterToBuy(0);
		myTurn.setSugarToBuy(0);
		myTurn.setCupsToBuy(0);
		
		exception.expect(IllegalStateException.class);
		
		int i;
		for (i = 1; i <= Game.TURNS_PER_DAY+1; i++)
		{
			myGame.doTurn(phil, myTurn);
		}

	}
	
	


}
