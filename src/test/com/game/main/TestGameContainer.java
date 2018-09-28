package com.game.main;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestGameContainer {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	
	

	@Test
	public void testCreateGame()
	{
		// Create two games, confirm they exist and are different.
		GameContainer container = new GameContainer();
		
		String philToken = container.createUser("phil",  "phil@gmail.com","philpassword");
		User phil = container.getUserByToken(philToken);
		
		Game game = container.createGameWithUser("first game", phil);
		
		assertTrue(container.hasGame(game));
		assertTrue(game.hasPlayer(phil));
		
		String alexToken = container.createUser("alex",  "alex@gmail.com","alexpassword");
		User alex = container.getUserByToken(alexToken);
		
		Game otherGame = container.createGameWithUser("other game", alex);
		
		assertTrue(container.hasGame(otherGame));
		assertTrue(otherGame.hasPlayer(alex));
		assertTrue(game!=otherGame);
	}
	
	// Use case: start or resume a game that is ready to be played.
	
	@Test
	public void testGetGame() {
		
		// Create two games and confirm that getGame returns the correct ones.
		GameContainer container = new GameContainer();
		String philToken = container.createUser("phil",  "phil@gmail.com","philpassword");
		User phil = container.getUserByToken(philToken);
		
		Game game = container.createGameWithUser("first game", phil);
		Game shouldBeGame = container.getGame(game.getName(),  phil);
		assertTrue(game==shouldBeGame);
		
		
		String alexToken = container.createUser("alex",  "alex@gmail.com","alexpassword");
		User alex = container.getUserByToken(alexToken);
		Game otherGame = container.createGameWithUser("other game", alex);
		Game shouldBeOtherGame = container.getGame(otherGame.getName(),  alex);
		assertTrue(otherGame==shouldBeOtherGame);
	}
	
	@Test
	public void testGetGameUnauthorized() {
		
		// Create a game and confirm that only a player can access it.
		GameContainer container = new GameContainer();
		String philToken = container.createUser("phil",  "phil@gmail.com","philpassword");
		User phil = container.getUserByToken(philToken);
	container.createGameWithUser("first game", phil);
		
		String alexToken = container.createUser("alex",  "alex@gmail.com","alexpassword");
		User alex = container.getUserByToken(alexToken);
		exception.expect(IllegalStateException.class);
		container.getGame("first game",  alex);
	}
	
	

	// Use case: obtain a list of games
	@Test
	public void testListGames() {
		
		// Create two games, confirm they are in the list of games.
		GameContainer container = new GameContainer();
		
		String philToken = container.createUser("phil",  "phil@gmail.com","philpassword");
		User phil = container.getUserByToken(philToken);
		Game game = container.createGameWithUser("first game", phil);
		
		String alexToken = container.createUser("alex",  "alex@gmail.com","alexpassword");
		User alex = container.getUserByToken(alexToken);
		Game otherGame = container.createGameWithUser("other game", alex);
		
		List<Game> myGames = container.getGames();
		
		assertTrue(myGames.size()==2);
		assertTrue(myGames.contains(game));
		assertTrue(myGames.contains(otherGame));
	}
	

	@Test
	public void testJoinGame()
	{
		
		GameContainer container = new GameContainer();
		
		String philToken = container.createUser("phil",  "phil@gmail.com","philpassword");
		User phil = container.getUserByToken(philToken);
		Game game = container.createGameWithUser("first game", phil);
		
		String clintToken = container.createUser("clint", "clint@gmail.com", "clintpassword");
		User clint = container.getUserByToken(clintToken);
		Game otherGame = container.createGameWithUser("second game", phil);
		
		container.joinGame("first game", clint);
		
		
		assertTrue(game.hasPlayer(clint));
		assertFalse(otherGame.hasPlayer(clint));
		
		
		// retrieve game with starting player - same game
		Game sameGame = container.getGame("first game", clint);
		assertTrue(sameGame.hasPlayer(clint));

		assertTrue(game==sameGame);
	}
	
	@Test
	public void testDeleteGame() {
		GameContainer container = new GameContainer();
		
		User phil = container.getUserByToken(container.createUser("phil",  "phil@gmail.com","philpassword"));
		container.createGameWithUser("first game", phil);
		
		container.createGameWithUser("second game", phil);
		
		assertTrue(container.getGames().size()==2);
		container.deleteGame("first game");
		
		assertTrue(container.getGames().size()==1);
		Game firstGame = container.getGame("first game",  phil);
		assertNull(firstGame);
		
		Game secondGame = container.getGame("second game",  phil);
		assertNotNull(secondGame);
		
		
		container.deleteGame("second game");
		assertTrue(container.getGames().size()==0);
		secondGame = container.getGame("second game",  phil);
		assertNull(secondGame);
	}
	
	
}
