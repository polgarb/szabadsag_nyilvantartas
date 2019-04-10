/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

/**
 * Egy dolgozó adatai 
 * @author Polgár Béla
 */
public class Dolgozo {

    private int id;
    private String nev;
    private String szuletesiDatum;
    private int gyerekDB;
    private int evesSzabadsag;
    private int maradekSzabadsag;
    private int kivettszabi;
    
    /**
     * 
     * @param id egyedi sorszáma a dolgozónak
     * @param nev a dolgozó neve
     * @param szuletesiDatum a dolgozó születési dátuma
     * @param gyerekDB a dolgozó gyermekeinek száma
     * @param evesSzabadsag a dolgozó éves szabadságainak a száma
     * @param maradekSzabadsag a dolgozó megmaradt szabadságainak száma
     */

    public Dolgozo(int id, String nev, String szuletesiDatum, int gyerekDB, int evesSzabadsag, int maradekSzabadsag) {
        this.id = id;
        this.nev = nev;
        this.szuletesiDatum = szuletesiDatum;
        this.gyerekDB = gyerekDB;
        this.evesSzabadsag = evesSzabadsag;
        this.maradekSzabadsag = maradekSzabadsag;
        this.kivettszabi = evesSzabadsag - maradekSzabadsag;
        
    }
    
    /**
     * A dolgozó egyedi sorszámát kérdezi le
     * @return a dolgozó sorszáma
     */
    public int getId() {
        return id;
    }
    
    /**
     * A dolgozó sorszámát állítja be
     * @param id a dolgozó sorszáma
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * A dolgozó nevét adja vissza
     * @return a dolgozó neve
     */
    public String getNev() {
        return nev;
    }
    
    /**
     * A dolgozó nevét állítja be 
     * @param nev a dolgozó neve
     */
    public void setNev(String nev) {
        this.nev = nev;
    }
    
    /**
     * A dolgozó születési dátumát adja vissza
     * @return születési dátum
     */
    public String getSzuletesiDatum() {
        return szuletesiDatum;
    }
    
    /**
     * A dolgozó születési dátumát állítja be
     * @param szuletesiDatum születési dátum
     */
    public void setSzuletesiDatum(String szuletesiDatum) {
        this.szuletesiDatum = szuletesiDatum;
    }

    /**
     * A dolgozó gyermekeinek számát adja vissza
     * @return gyerekek száma
     */
    public int getGyerekDB() {
        return gyerekDB;
    }
    
    /**
     * A dolgozó gyermekeinek számát állítja be
     * @param gyerekDB gyerekek száma
     */
    public void setGyerekDB(int gyerekDB) {
        this.gyerekDB = gyerekDB;
    }
    
    /**
     * A dolgozó éves szabadságának számát adja vissza
     * @return éves szabadság száma
     */
    public int getEvesSzabadsag() {
        return evesSzabadsag;
    }

    /**
     * A dolgozó éves szabadságait állítja be a kapott értékre
     * @param evesSzabadsag éves szabadság száma
     */
    public void setEvesSzabadsag(int evesSzabadsag) {
        this.evesSzabadsag = evesSzabadsag;
    }

    /**
     * A dolgozó megmaradt szabadságát adja vissza
     * @return maradék szabadság
     */
    public int getMaradekSzabadsag() {
        return maradekSzabadsag;
    }

    /**
     * A dolgozó maradék szabadságát állítja be
     * @param maradekSzabadsag maradék szabadság
     */
    public void setMaradekSzabadsag(int maradekSzabadsag) {
        this.maradekSzabadsag = maradekSzabadsag;
    }
    
    /**
     * A dolgozó kivett szabadságait adja vissza
     * @return kivett szabadságok száma
     */
    public int getKivettszabi() {
        return kivettszabi;
    }
    
    /**
     * A dolgozó kivett szabadságát állítja be
     * @param kivettszabi kivett szabadságok száma
     */
    public void setKivettszabi(int kivettszabi) {
        this.kivettszabi = kivettszabi;
    }

    
}
