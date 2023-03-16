/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Service;

import com.mycompany.guessthenumberdb.Model.Game;
import com.mycompany.guessthenumberdb.Model.Round;
import java.util.List;

/**
 *
 * @author steve
 */
public interface GuessTheNumberService {
    
    public List<Game> getAllGames();
    public Game beginGame();
    public Round checkGuess(String guess, int gameID);
    public Game getGameByID(int id);
    public List<Round> getAllRoundsByGameID(int gameID);
}
