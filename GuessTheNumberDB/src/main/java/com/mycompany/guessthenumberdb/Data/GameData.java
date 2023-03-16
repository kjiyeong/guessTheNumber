/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Data;

import com.mycompany.guessthenumberdb.Model.Game;
import java.util.List;

/**
 *
 * @author steve
 */
public interface GameData {
    
    public Game create(Game game);
    public List<Game> readAll();
    public Game readByID(int id);
    public boolean update(Game game);
    public boolean delete(int id);
    
}
