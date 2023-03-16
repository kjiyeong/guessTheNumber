/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.GuessTheNumberDBController;

import com.mycompany.guessthenumberdb.Model.Game;
import com.mycompany.guessthenumberdb.Model.Postman;
import com.mycompany.guessthenumberdb.Model.Round;
import com.mycompany.guessthenumberdb.Service.GuessTheNumberService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author steve
 */
@RestController
@RequestMapping("/api")
public class Controller {
    
    private final GuessTheNumberService service;

    public Controller(GuessTheNumberService service) {
        this.service = service; 
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int beginGame() {
        Game game = service.beginGame();
        return game.getID();
    }

    @PostMapping("/guess")
    public Round checkGuess(@RequestBody Postman input) {
        Round round = service.checkGuess(input.getGuess(), input.getGameID());
        if (!round.getGame().getIsFinished()) {
            round.getGame().setAnswer("Still not solved!");
        }
        return round;
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        List<Game> games = service.getAllGames();
        for (Game game : games) {
            if (!game.getIsFinished()) {
                game.setAnswer("Still not solved!");
            }
        }
        return games;
    }

    @GetMapping("/game/{gameID}")
    public ResponseEntity<Game> getGameByID(@PathVariable int gameID) {
        Game game = service.getGameByID(gameID);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        if (!game.getIsFinished()) {
            game.setAnswer("Still not solved!");
        }
        return ResponseEntity.ok(game);
    }

    @GetMapping("/rounds/{gameID}")
    public List<Round> getAllRoundsByID(@PathVariable int gameID) {
        List<Round> rounds = service.getAllRoundsByGameID(gameID);

        for (Round round : rounds) {
            if (!round.getGame().getIsFinished()) {
                round.getGame().setAnswer("Still not solved!");
            }
        }
        return rounds;
    }

}
