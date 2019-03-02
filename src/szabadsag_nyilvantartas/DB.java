/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * Adatbázis kezelő osztály
 * @author  Polgár Béla
 */
public class DB {
    
    final String dbUrl = "jdbc:mysql://localhost:3306/nyilvantarto2019?useUnicode=true&characterEncoding=UTF-8";
    final String user = "root";
    final String pass = "";
    
    public void dolgozo_lista (ObservableList<Dolgozo> tabla, ObservableList<String> lista) { //dolgozók listázása
        String s = "SELECT * FROM dolgozok;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            lista.clear();
            while (eredmeny.next()) {
                tabla.add(new Dolgozo(
                    eredmeny.getInt(1),
                    eredmeny.getString(2),
                    eredmeny.getString(3),
                    eredmeny.getInt(4),
                    eredmeny.getInt(5),
                    eredmeny.getInt(6),
                    eredmeny.getInt(7)));
                lista.add(eredmeny.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void szabadsag_lista (ObservableList<Szabadsag> tabla) { //összes sabadság listázása
        String s = "SELECT szabadsagok.id,dolgozok.nev,szabadsag_kezdete,szabadsag_vege,szabadsag_hossza FROM szabadsagok JOIN dolgozok ON szabadsagok.dolgozoid=dolgozok.id;;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            while (eredmeny.next()) {
                tabla.add(new Szabadsag(
                    eredmeny.getInt(1),
                    eredmeny.getString(2),
                    eredmeny.getString(3),
                    eredmeny.getString(4),
                    eredmeny.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
