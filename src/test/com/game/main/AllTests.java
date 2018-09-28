package com.game.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.game.main.app.TestGameApp;

@RunWith(Suite.class)
@SuiteClasses({TestGame.class, TestGameContainer.class, TestGameApp.class})
public class AllTests {


}

