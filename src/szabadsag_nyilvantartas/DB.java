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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    
        public void dolgozo_torles (int id){ 
        //dolgozó törlése
        String s = "DELETE FROM dolgozok WHERE id = ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
            PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
    public void ujdolgozo(String nev, String szuletesi_datum, int gyerekek_szama) {
        //új dolgozó adatainak felvitele az adatok táblába
        String s = "INSERT INTO dolgozok (nev,szuletesi_datum,gyerekek_szama,eves_szabadsag,maradek_szabadsag) VALUES(?,?,?,?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, szuletesi_datum);
            ekp.setInt(3, gyerekek_szama);
            ekp.setInt(4, setEvesSzabi(szuletesi_datum, gyerekek_szama));
            ekp.setInt(5, setEvesSzabi(szuletesi_datum, gyerekek_szama));

            ekp.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private int setEvesSzabi(String szulido, int gyerekdb) {
        //éves alapszabadság meghatározása
        int eveszabadsag = 20;
        //gyerekek utáni szabadnapok
        if (gyerekdb == 1) {
            eveszabadsag += 2;
        } else if (gyerekdb == 2) {
            eveszabadsag += 4;
        } else if (gyerekdb > 2) {
            eveszabadsag += 7;
        }
        //életév kiszámítása
        String strOut = szulido.substring(0, 4);
        int kor = 2019 - Integer.parseInt(strOut);
        //kor alapjáni plusz napok
        if (kor > 24 && kor < 28) {
            eveszabadsag += 1;
        } else if (kor > 27 && kor < 31) { //28,29,30
            eveszabadsag += 2;
        } else if (kor > 30 && kor < 33) { //31,32
            eveszabadsag += 3;
        } else if (kor > 32 && kor < 35) { //33,34
            eveszabadsag += 4;
        } else if (kor > 34 && kor < 37) {
            eveszabadsag += 5;
        } else if (kor > 36 && kor < 39) {
            eveszabadsag += 6;
        } else if (kor > 38 && kor < 41) {
            eveszabadsag += 7;
        } else if (kor > 40 && kor < 43) {
            eveszabadsag += 8;
        } else if (kor > 42 && kor < 45) {
            eveszabadsag += 9;
        } else if (kor > 44) {
            eveszabadsag += 10;
        }
        return eveszabadsag;
    }
    
    public void dolgozo_modositas(int id,String nev, String szuletesiDatum, int gyerekDB){
        //dolgozói adatok modosítása
        String s = "UPDATE dolgozok SET nev =?,szuletesi_datum=?,gyerekek_szama=?,eves_szabadsag=? WHERE id =?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, szuletesiDatum);
            ekp.setInt(3, gyerekDB);
            ekp.setInt(4, setEvesSzabi(szuletesiDatum, gyerekDB));
            ekp.setInt(5, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void maradek_szabadsag_modositas (String nev, int valtozas){
        String s = "UPDATE dolgozok SET maradek_szabadsag = maradek_szabadsag + ? WHERE nev = ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setInt(1, valtozas);
            ekp.setString(2, nev);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        public void uj_szabadsag(String nev, String szabistart, String szabivege) {
        
            //maradekszabi csökkentése a szabihosszal az adatbázisban
            String sz = "UPDATE dolgozok SET maradek_szabadsag = maradek_szabadsag - ? WHERE nev = ?";
            try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                    PreparedStatement ekp = kapcs.prepareStatement(sz)) {
                ekp.setInt(1, szabadsagHossz(szabistart, szabivege));
                ekp.setString(2, nev);
                ekp.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            //Szabadság bevitele a szabadságok táblába
            String s = "INSERT INTO szabadsagok (dolgozoid,szabadsag_kezdete,szabadsag_vege,szabadsag_hossza) VALUES((SELECT id FROM dolgozok WHERE nev = ?),?,?,?);";
            try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                    PreparedStatement ekp = kapcs.prepareStatement(s)) {
                ekp.setString(1, nev);
                ekp.setString(2, szabistart);
                ekp.setString(3, szabivege);
                ekp.setInt(4, szabadsagHossz(szabistart, szabivege));
                ekp.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       }
        
    public void szabadsag_torles (int id){ 
        //szabi törlése
        String s = "DELETE FROM szabadsagok WHERE id = ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
            PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        public void szabadsag_modositas(int id,String nev, String szabikezdete, String szabivege){
        //szabadságok modosítása
        String s = "UPDATE szabadsagok SET dolgozoid = (SELECT id FROM dolgozok WHERE nev = ?),szabadsag_kezdete=?,szabadsag_vege=?,szabadsag_hossza = ? WHERE id =?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, szabikezdete);
            ekp.setString(3, szabivege);
            ekp.setInt(4, szabadsagHossz(szabikezdete, szabivege));
            ekp.setInt(5, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        }
    /**
     *
     * @param szabistart
     * @param szabivege
     * @return
     */
    private int szabadsagHossz (String szabistart,String szabivege) {
            
            LocalDate szabiStart = LocalDate.parse(szabistart, DateTimeFormatter.ISO_DATE);
            LocalDate szabiVege = LocalDate.parse(szabivege, DateTimeFormatter.ISO_DATE);
            int szabihossza = 0;
        //a szabi kezdete korábbi dátum e mint a vége
        if (!szabiVege.isBefore(szabiStart)) {
            //a dátumok hanyadik napok az évben
            int kezdonap = szabiStart.getDayOfYear();
            int vegenap = szabiVege.getDayOfYear();
            //szabadság hosszának kiszámítása
            szabihossza = vegenap - kezdonap + 1;
            //van e benne hétége ,ha igen akkor ezek a napok levonása a szabiból
            int szabistartaheten = szabiStart.getDayOfWeek().getValue();
            if ((szabistartaheten + szabihossza) > 5) {
                int x = 0;
                int hetveginapok = 0;
                for (int i = 0; i < szabihossza; i++) {
                    x = szabiStart.getDayOfWeek().getValue() + i;
                    if (x > 7) {
                        x -= 7;
                    }
                    if (x == 6 || x == 7) {
                        hetveginapok++;
                    }
                }
                szabihossza -= hetveginapok;
            }
            //extranapok vizsgálata
            int extranap = 0;
            String zs = "SELECT ertek FROM extranapok WHERE datum BETWEEN ? AND ?";
            try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                    PreparedStatement ekp = kapcs.prepareStatement(zs)) {
                ekp.setString(1, szabistart);
                ekp.setString(2, szabivege);
                ResultSet eredmeny = ekp.executeQuery();
                eredmeny.next();
                extranap = eredmeny.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            szabihossza += extranap;
        
        }
            return szabihossza;
        } 
        
    
}
