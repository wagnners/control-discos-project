/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.core;

import model.jpa.JPADiscoDao;
import model.jpa.JPAMusicaDao;

/**
 *
 * @author Wagner
 */

//Classe abstrata responsável por criar classes de manipulação JPA de cada tipo (Disco e Música)
public abstract class JPAFactory {
     
    public static DiscoDao getDiscoDao(){
        return new JPADiscoDao();
    }
    
     public static MusicaDao getMusicaDao(){
        return new JPAMusicaDao();
    }
    
}
