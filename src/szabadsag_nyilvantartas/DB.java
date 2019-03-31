/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * Adatbázis kezelő osztály az összes adatbázis lekérdezés, módosítást illetve
 * törlést ez az osztály végzi
 *
 * @author Polgár Béla
 */
public class DB {
    
    Properties ini = new Properties();
    String dbUrl, user, pass;
    
    
    /**
     * Az adatbázis eléréséhez szükséges cím (dbUrl), a felhasználónév (user) és jelszó (pass) beolvasása a setup.ini file -ból.
     */
    
    public DB() {
        try (FileInputStream be = new FileInputStream("setup.ini")) {
            ini.load(be);
            dbUrl = "jdbc:mysql://" + ini.getProperty("ip") + "/szabadsag-nyilvantartas?useUnicode=true&characterEncoding=UTF-8";
            user = ini.getProperty("user");
            pass = ini.getProperty("pass");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Platform.exit();
        }
    }
    
    /**
     * A dolgozók listázása
     *
     * @param tabla a dolgozok táblából az összes adatatot tartalmazza
     * @param lista a dolgozok táblából a dolgozók neveit tartalmazza
     */
    public void dolgozo_lista(ObservableList<Dolgozo> tabla, ObservableList<String> lista) { //dolgozók listázása
        String s = "SELECT * FROM dolgozok ORDER BY nev;";
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
                        eredmeny.getInt(6)));
                lista.add(eredmeny.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * A szabadságok listázása
     *
     * @param tabla a szabadsagok táblából az összes adatot tartalmazza
     */
    public void szabadsag_lista(ObservableList<Szabadsag> tabla) { //összes sabadság listázása
        String s = "SELECT szabadsagok.id,dolgozok.nev,szabadsag_kezdete,szabadsag_vege,szabadsag_hossza FROM szabadsagok JOIN dolgozok ON szabadsagok.dolgozoid=dolgozok.id ORDER BY szabadsagok.id;";
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

    /**
     * Dolgozó törlése a dolgozo táblából Az a dolgozó kerül törlésre, amelyik
     * id-ját adjuk meg
     *
     * @param id a dolgozó egyedi azonosítója
     */
    public void dolgozo_torles(int id) {
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

    /**
     * Új dolgozó bevitele dolgozó táblába
     *
     * @param nev a dolgozó neve
     * @param szuletesi_datum a dolgozó születési dátuma
     * @param gyerekek_szama a dolgozó gyermekeinek száma
     */
    public void ujdolgozo(String nev, String szuletesi_datum, int gyerekek_szama) {
        //új dolgozó adatainak felvitele az adatok táblába
        String s = "INSERT INTO dolgozok (nev,szuletesi_datum,gyerekek_szama,eves_szabadsag,maradek_szabadsag) VALUES(?,?,?,?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, szuletesi_datum);
            ekp.setInt(3, gyerekek_szama);
            ekp.setInt(4, EvesSzabiSzamitas(szuletesi_datum, gyerekek_szama));
            ekp.setInt(5, EvesSzabiSzamitas(szuletesi_datum, gyerekek_szama));
            ekp.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Kiszámítja a születési időből és a gyermekek számából, hogy 2019-ben hány
     * nap szabadság jár
     *
     * @param szulido a dolgozó születési ideje
     * @param gyerekdb a dolgozó gyermekeinek száma
     * @return a dolgozó éves szabadságának száma
     */
    private int EvesSzabiSzamitas(String szulido, int gyerekdb) {
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

    /**
     * Dolgozó adatainak modósítása
     *
     * @param id a dolgozó egyedi sorszáma
     * @param nev a dolgozó neve
     * @param szuletesiDatum a dolgozó születési dátuma
     * @param gyerekDB a dolgozó gyermekeinek száma
     */
    public void dolgozo_modositas(int id, String nev, String szuletesiDatum, int gyerekDB) {
        //dolgozói adatok modosítása
        String s = "UPDATE dolgozok SET nev =?,szuletesi_datum=?,gyerekek_szama=?,eves_szabadsag=? WHERE id =?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setString(2, szuletesiDatum);
            ekp.setInt(3, gyerekDB);
            ekp.setInt(4, EvesSzabiSzamitas(szuletesiDatum, gyerekDB));
            ekp.setInt(5, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * A nev paraméterbe kapott dolgozó maradék szabadságát módosítja a valtozás
     * mértékével
     *
     * @param nev a modosítandó dolgozó neve
     * @param valtozas a változás mértéke
     */
    public void maradek_szabadsag_modositas(String nev, int valtozas) {
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

    /**
     * Új szabadság hozzáadása a szabadsagok táblához és maradék szabadságok
     * csökkentése a szabadság hosszával
     *
     * @param nev a dolgozó neve
     * @param szabistart a szabadság kezdeti dátuma (szövegesen megadva)
     * @param szabivege a szabadság végének dátuma (szövegesen megadva)
     */
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

    /**
     * Az id - ben megadott sorszámú szabadság törlése a szabadsagok táblából
     *
     * @param id a törölni kívánt szabadság egyedi sorszám
     */
    public void szabadsag_torles(int id) {
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

    /**
     * Az id - ben megadott sorszámú szabadság módosítása, ellenőrzése, hogy nem
     * e ütközik már a dolgozó másik szabadságával, illetve a dolgozó maradék
     * szabadságának változtatása
     *
     * @param id a módosítani kívánt szabadság egyedi sorszám
     * @param nev a módosítani kívánt szabadsághoz tartozó név
     * @param szabikezdete a módosítani kívánt szabadsághoz tartozó új szabadság
     * kezdet dátum
     * @param szabivege a módosítani kívánt szabadsághoz tartozó új szabadsá
     * vége dátum
     */
    public void szabadsag_modositas(int id, String nev, String szabikezdete, String szabivege) {

        //A szabadság eredeti dátumainak lekérdezése
        String regiSzabiKezdete = "";
        String regiSzabiVege = "";
        String sz = "SELECT szabadsag_kezdete,szabadsag_vege FROM szabadsagok WHERE id = ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(sz)) {
            ekp.setInt(1, id);
            ResultSet eredmeny = ekp.executeQuery();
            eredmeny.next();
            regiSzabiKezdete = eredmeny.getString(1);
            regiSzabiVege = eredmeny.getString(2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        if (!(szabikezdete.equals(regiSzabiKezdete) && szabivege.equals(regiSzabiVege))) {
            int regiSzabiHossz = szabadsagHossz(regiSzabiKezdete, regiSzabiVege);
            int szabiKulonbseg = regiSzabiHossz - szabadsagHossz(szabikezdete, szabivege);
            maradek_szabadsag_modositas(nev, szabiKulonbseg);
        }

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
     * a kapott String típusú dátumokból kiszámolja a szabadság hosszát,
     * figyelembe véve a hétvégi napokat, a munkaszüneti napokat és a
     * ledolgozásos napokat
     *
     * @param szabistart a szabadság kezdete
     * @param szabivege a stabadság vége
     * @return visszaadja a szabadság hosszát
     */
    private int szabadsagHossz(String szabistart, String szabivege) {
        //String átkonvertálása dátummá
        LocalDate szabiStart = LocalDate.parse(szabistart, DateTimeFormatter.ISO_DATE);
        LocalDate szabiVege = LocalDate.parse(szabivege, DateTimeFormatter.ISO_DATE);
        //a dátumok hanyadik napok az évben
        int kezdonap = szabiStart.getDayOfYear();
        int vegenap = szabiVege.getDayOfYear();
        //szabadság hosszának kiszámítása
        int szabihossza = vegenap - kezdonap + 1;
        //van e benne hétége ,ha igen akkor ezek a napok levonása a szabiból
        int szabistartaheten = szabiStart.getDayOfWeek().getValue();
        if ((szabistartaheten + szabihossza) > 5) {
            int x = szabiStart.getDayOfWeek().getValue();
            int hetveginapok = 0;
            for (int i = 0; i < szabihossza; i++) {
                if (x > 7) {
                    x -= 7;
                }
                if (x == 6 || x == 7) {
                    hetveginapok++;
                }
                x++;
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
            while (eredmeny.next()) {
                if (!eredmeny.wasNull()) {
                    extranap += eredmeny.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        szabihossza += extranap;
        return szabihossza;
    }

    /**
     * Az adott dolgozó szabadságon van e, a szabiStart dátum és a szabiVege
     * dátum intervallumban, az id paraméter megadásával figyelmen kívűl hagyja
     * a megadott id - hez tartozó szabadságot, így szabadság módosításnál nem
     * veszi figyelembe az éppen módosítás alatt lévő szabadságot
     *
     * @param nev a dolgozó neve
     * @param szabiStart az ellenőrizni kívánt dátum intervallum kezdete
     * @param szabiVege az ellenőrizni kívánt dátum intervallum vége
     * @param id azon szabadság egyedi sorszáma amit figyelmen kívűl szeretnénk
     * hagyni (módosításkor van jelentősége)
     * @return true -t ad vissza, ha szabadságon van már
     */
    public boolean szabinVanE(String nev, LocalDate szabiStart, LocalDate szabiVege, int id) {
        // Ellenőrzés, hogy a kapott dátum intervallum beleesik e valamilyen meglévő szabiba (Ha igen akkor true -t ad vissza)
        boolean naptar[] = new boolean[366];
        String s = "SELECT szabadsag_kezdete,szabadsag_vege  FROM szabadsagok WHERE dolgozoid = (SELECT id FROM dolgozok WHERE nev = ?) AND id != ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ekp.setInt(2, id);
            ResultSet eredmeny = ekp.executeQuery();
            while (eredmeny.next()) {
                // az összes szabi tömbbe írása
                LocalDate szabiS = LocalDate.parse(eredmeny.getString(1), DateTimeFormatter.ISO_DATE);
                LocalDate szabiV = LocalDate.parse(eredmeny.getString(2), DateTimeFormatter.ISO_DATE);

                for (int i = szabiS.getDayOfYear(); i < szabiV.getDayOfYear() + 1; i++) {
                    naptar[i] = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        int i = szabiStart.getDayOfYear();
        boolean valtozo = false;
        while (i <= szabiVege.getDayOfYear() && !valtozo) {
            if (naptar[i]) {
                valtozo = true;
            }
            i++;
        }
        return valtozo;
    }

    /**
     * A munkaszüneti napokat, illetve a szombati munkanapokat kérdezi le
     *
     * @param tabla az extranapok tábla összes elemét tartalmazza
     */
    public void extranap_lista(ObservableList<ExtraDatum> tabla) { //összes sabadság listázása
        String s = "SELECT id,datum,ertek FROM extranapok ORDER BY datum;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ResultSet eredmeny = ekp.executeQuery();
            tabla.clear();
            while (eredmeny.next()) {
                tabla.add(new ExtraDatum(
                        eredmeny.getInt(1),
                        eredmeny.getString(2),
                        eredmeny.getInt(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Új extranap felvitele (munkaszüneti nap, szombati munkanap..)
     *
     * @param datum Az új extranap dátuma
     * @param tipus Az új extranap típusa (munkaszüneti nap, szombati munkanap)
     */
    public void uj_extranap(String datum, String tipus) {
        int ertek = 0;
        if (tipus.equals("Munkaszüneti nap")) {
            ertek = -1;
        } else {
            ertek = 1;
        }

        String s = "INSERT INTO extranapok (datum,ertek) VALUES(?,?);";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, datum);
            ekp.setInt(2, ertek);
            ekp.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Az id - ben megadott sorszámú extranap törlése a szabadsagok táblából
     *
     * @param id a törölni kívánt extranap egyedi sorszáma
     */
    public void extranap_torles(int id) {
        //extranap törlése
        String s = "DELETE FROM extranapok WHERE id = ?;";
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setInt(1, id);
            ekp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Ellenőrzi, hogy az adott dolgozónak van e elég szabadsága
     *
     * @param nev a dolgozó neve
     * @param szabiKezdete a szabadság kezdete
     * @param szabiVege a szabadság vége
     * @param eredetiSzabiHossz az eredeti szabadság hossza, amennyiben
     * módosításról van szó
     * @return true , ha van elég szabadsága
     */
    public boolean vanEelegszabija(String nev, String szabiKezdete, String szabiVege, int eredetiSzabiHossz) {
        String s = "SELECT maradek_szabadsag FROM dolgozok WHERE nev = ?;";
        boolean vanelegszabija = false;
        int maradekszabi = 0;
        try (Connection kapcs = DriverManager.getConnection(dbUrl, user, pass);
                PreparedStatement ekp = kapcs.prepareStatement(s)) {
            ekp.setString(1, nev);
            ResultSet eredmeny = ekp.executeQuery();
            eredmeny.next();
            maradekszabi = eredmeny.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if ((eredetiSzabiHossz + maradekszabi - szabadsagHossz(szabiKezdete, szabiVege)) < 0) {
            vanelegszabija = true;
        }
        return vanelegszabija;
    }

}
