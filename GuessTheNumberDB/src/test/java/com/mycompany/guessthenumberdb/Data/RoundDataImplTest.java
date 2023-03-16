/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Data;

import com.mycompany.guessthenumberdb.Model.Game;
import com.mycompany.guessthenumberdb.Model.Round;
import com.mycompany.guessthenumberdb.TestApplicationConfiguration;
import java.time.LocalDateTime;
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
public class RoundDataImplTest {
    
    @Autowired
    private GameData gameData;

    @Autowired
    private RoundData roundData;
    
    public RoundDataImplTest() {
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
            roundData.delete((round.getID()));
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

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round fromData = roundData.readByID(round.getID());

        assertEquals(round, fromData);
    }

   @Test
    public void testReadByIDNoRound() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");

        Round fromData = roundData.readByID(round.getID());

        assertNull(fromData);
    }

    @Test
    public void testReadByIDNoGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round fromData = roundData.readByID(round.getID());

        assertNull(fromData);
    }

    @Test
    public void testReadAll() {
        Game game = new Game();
        game.setAnswer(("1234"));
        game.setIsFinished(false);
        gameData.create(game);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("5678");
        round2.setGuessTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");
        roundData.create(round2);

        List<Round> rounds = roundData.readAll();

        assertEquals(2, rounds.size());
        assertTrue(rounds.contains(round));
        assertTrue(rounds.contains(round2));
    }

    @Test
    public void testReadAllNoRounds() {
        Game game = new Game();
        game.setAnswer(("1234"));
        game.setIsFinished(false);
        gameData.create(game);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");

        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("5678");
        round2.setGuessTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");

        List<Round> rounds = roundData.readAll();

        assertEquals(0, rounds.size());
        assertFalse(rounds.contains(round));
        assertFalse(rounds.contains(round2));
    }
    
    @Test
    public void testReadAllNoGames() {
        Game game = new Game();
        game.setAnswer(("1234"));
        game.setIsFinished(false);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("5678");
        round2.setGuessTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");
        roundData.create(round2);

        List<Round> rounds = roundData.readAll();

        assertEquals(0, rounds.size());
        assertFalse(rounds.contains(round));
        assertFalse(rounds.contains(round2));
    }
    
    @Test
    public void testReadByGameID() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("5678");
        round2.setGuessTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");
        roundData.create(round2);

        List<Round> roundsGame1 = roundData.readByGameID(game.getID());
        assertEquals(2, roundsGame1.size());
        assertTrue(roundsGame1.contains(round));
        assertTrue(roundsGame1.contains(round2));

        Game game2 = new Game();
        game2.setAnswer("0317");
        game2.setIsFinished(false);
        gameData.create(game2);

        Round round3 = new Round();
        round3.setGame(game2);
        round3.setGuess("1317");
        round3.setGuessTime(LocalDateTime.now().withNano(0));
        round3.setResult("e:3:p:1");
        roundData.create(round3);

        List<Round> roundsGame2 = roundData.readByGameID(game2.getID());
        assertEquals(1, roundsGame2.size());
        assertTrue(roundsGame2.contains(round3));
    }
    
    @Test
    public void testReadByGameIDNoGames() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        Round round2 = new Round();
        round2.setGame(game);
        round2.setGuess("5678");
        round2.setGuessTime(LocalDateTime.now().withNano(0));
        round2.setResult("e:0:p:0");
        roundData.create(round2);

        List<Round> roundsGame1 = roundData.readByGameID(game.getID());
        assertEquals(0, roundsGame1.size());
        assertFalse(roundsGame1.contains(round));
        assertFalse(roundsGame1.contains(round2));
    }
    
    @Test
    public void testDelete() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setIsFinished(false);
        gameData.create(game);

        Round round = new Round();
        round.setGame(game);
        round.setGuess("1234");
        round.setGuessTime(LocalDateTime.now().withNano(0));
        round.setResult("e:4:p:0");
        roundData.create(round);

        roundData.delete(round.getID());

        Round fromData = roundData.readByID(round.getID());

        assertNull(fromData);
    }

    
}
