/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Service;

import com.mycompany.guessthenumberdb.Data.GameData;
import com.mycompany.guessthenumberdb.Data.RoundData;
import com.mycompany.guessthenumberdb.Model.Game;
import com.mycompany.guessthenumberdb.Model.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author steve
 */
@Service
public class GuessTheNumberServiceImpl implements GuessTheNumberService {
    
    @Autowired
    private GameData gameData;

    @Autowired
    private RoundData roundData;

    @Override
    public Game beginGame() {
        Game game = new Game();
        game.setAnswer(generateNumber()); 
        game.setIsFinished(false);

        return gameData.create(game);
    }

    @Override
    public Round checkGuess(String guess, int gameID) {
        Game game = gameData.readByID(gameID);

        if (game == null) {
            return null;
        }

        Round round = new Round();
        round.setGame(game);
        round.setGuess(guess);
        round.setGuessTime(LocalDateTime.now());
        round.setResult(matchResult(guess, game.getAnswer())); 
        roundData.create(round);

        if (round.getResult().equalsIgnoreCase("e:4:p:0")) {
            game.setIsFinished(true);
            gameData.update(game);
        }

        return roundData.readByID(round.getID());
    }

    @Override
    public List<Game> getAllGames() {
        return gameData.readAll();
    }

    @Override
    public Game getGameByID(int id) {
        return gameData.readByID(id);
    }

    @Override
    public List<Round> getAllRoundsByGameID(int gameID) {
        return roundData.readByGameID(gameID);
    }

    private String generateNumber() { 
        String randomNumber = "";
        Random rand = new Random();
        int nextNumber;
        int indexToRemove;
        boolean keepGoing = true;
        ArrayList<Integer> numberPool = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            numberPool.add(i);
        }

        for (int i = 0; i < 4; i++) {
            do {
                nextNumber = rand.nextInt(10);

                if (numberPool.contains(nextNumber)) {
                    randomNumber = randomNumber + nextNumber;
                    indexToRemove = numberPool.indexOf(nextNumber);
                    numberPool.remove(indexToRemove);
                    keepGoing = false;
                } else {
                    keepGoing = true;
                }
            } while (keepGoing);
        }

        return randomNumber;
    }

    private String matchResult(String guess, String answer) {
        int exactMatches = 0;
        int partialMatches = 0;
        String finalResult;

        if (guess.length() != 4) {
            finalResult = "e:" + exactMatches + ":p:" + partialMatches;
            return finalResult;
        }

        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                exactMatches++;
            } else if (answer.contains(guess.subSequence(i, i + 1))) {
                partialMatches++;
            }
        }

        finalResult = "e:" + exactMatches + ":p:" + partialMatches;
        return finalResult;
    }
    
}
