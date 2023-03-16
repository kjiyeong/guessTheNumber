/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.guessthenumberdb.Data;

import com.mycompany.guessthenumberdb.Model.Round;
import java.util.List;

/**
 *
 * @author steve
 */
public interface RoundData {
    
    public Round create(Round round);
    public List<Round> readAll();
    public Round readByID(int id);
    public List<Round> readByGameID(int id);
    public boolean delete(int id);;
}
