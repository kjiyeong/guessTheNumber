/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Data;

import com.mycompany.guessthenumberdb.Model.Game;
import com.mycompany.guessthenumberdb.Model.Round;
import com.mycompany.guessthenumberdb.TestApplicationConfiguration;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author steve
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDataImplTest {
    
    @Autowired
    private GameData gameData;

    @Autowired
    private RoundData roundData;

    public GameDataImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Game> games = gameData.readAll();
        for (Game game : games) {
            gameData.delete(game.getID());
        }

        List<Round> rounds = roundData.readAll();
        for (Round round : rounds) {
            roundData.delete(round.getID());
        }
    
    }
    
    @After
    public void tearDown() {
    }

     @Test
    public void testCreateReadByID() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Game fromData = gameData.readByID(game.getID());

        assertEquals(game, fromData);
    }

    @Test
    public void testReadByIDNoGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);

        Game fromData = gameData.readByID(game.getID());
        assertNull(fromData);
    }

    @Test
    public void testReadAll() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setIsFinished(false);
        gameData.create(game2);

        List<Game> games = gameData.readAll();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testReadAllNoGame() {
        List<Game> games = gameData.readAll();
        assertEquals(0, games.size());
    }

    @Test
    public void testUpdate() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Game fromData = gameData.readByID(game.getID());

        assertEquals(game, fromData);

        game.setIsFinished(true);

        gameData.update(game);

        assertNotEquals(game, fromData);

        fromData = gameData.readByID(game.getID());

        assertEquals(game, fromData);

    }
    
    @Test
    public void testDelete() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        gameData.delete(game.getID());

        Game fromData = gameData.readByID(game.getID());

        assertNull(fromData);
    }
    
}
