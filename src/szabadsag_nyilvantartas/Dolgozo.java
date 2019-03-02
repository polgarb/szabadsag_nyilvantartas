/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

/**
 *
 * @author Polgár Béla
 */
public class Dolgozo {
    private int id;
    private String nev;
    private String szuletesiDatum;
    private int gyerekDB;
    private int evesSzabadsag;
    private int maradekSzabadsag;
    private int fizetesNelkuli;

    public Dolgozo(int id, String nev, String szuletesiDatum, int gyerekDB, int evesSzabadsag, int maradekSzabadsag, int fizetesNelkuli) {
        this.id = id;
        this.nev = nev;
        this.szuletesiDatum = szuletesiDatum;
        this.gyerekDB = gyerekDB;
        this.evesSzabadsag = evesSzabadsag;
        this.maradekSzabadsag = maradekSzabadsag;
        this.fizetesNelkuli = fizetesNelkuli;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getSzuletesiDatum() {
        return szuletesiDatum;
    }

    public void setSzuletesiDatum(String szuletesiDatum) {
        this.szuletesiDatum = szuletesiDatum;
    }

    public int getGyerekDB() {
        return gyerekDB;
    }

    public void setGyerekDB(int gyerekDB) {
        this.gyerekDB = gyerekDB;
    }

    public int getEvesSzabadsag() {
        return evesSzabadsag;
    }

    public void setEvesSzabadsag(int evesSzabadsag) {
        this.evesSzabadsag = evesSzabadsag;
    }

    public int getMaradekSzabadsag() {
        return maradekSzabadsag;
    }

    public void setMaradekSzabadsag(int maradekSzabadsag) {
        this.maradekSzabadsag = maradekSzabadsag;
    }

    public int getFizetesNelkuli() {
        return fizetesNelkuli;
    }

    public void setFizetesNelkuli(int fizetesNelkuli) {
        this.fizetesNelkuli = fizetesNelkuli;
    }
    
    
}
