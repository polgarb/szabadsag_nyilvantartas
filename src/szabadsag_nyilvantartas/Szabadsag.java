/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

/**
 * Egy szabadság adatai
 * @author Polgár Béla
 */
public class Szabadsag {

    private int id;
    private String nev;
    private String szabiKezdete;
    private String szabiVege;
    private int szabiHossza;
    
    /**
     * Konstruktor
     * @param id a szabadság egyedi száma
     * @param nev a szabadságon lévő neve
     * @param szabiKezdete a szabadságának kezdete
     * @param szabiVege a szabadságának vége
     * @param szabiHossza a szabadság hossza (csak munkanapok számítanak)
     */

    public Szabadsag(int id, String nev, String szabiKezdete, String szabiVege, int szabiHossza) {
        this.id = id;
        this.nev = nev;
        this.szabiKezdete = szabiKezdete;
        this.szabiVege = szabiVege;
        this.szabiHossza = szabiHossza;
    }
    
    /**
     * A szabadság sorszámát adja vissza
     * @return szabadság sorszáma
     */
    public int getId() {
        return id;
    }
    
    /**
     * A szabadság sorszámát állítja be
     * @param id szabadság sorszáma
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A szabadsághoz tartozó dolgozó nevét adja vissza
     * @return dolgozó neve
     */
    public String getNev() {
        return nev;
    }
    
    /**
     * A szabadsághoz tartozó dolgozó nevét állítja be 
     * @param nev dolgozó neve
     */
    public void setNev(String nev) {
        this.nev = nev;
    }

    /**
     * A szabadság kezdeti dátumát adja vissza
     * @return szabadság kezdésének dátuma
     */
    public String getSzabiKezdete() {
        return szabiKezdete;
    }

    /**
     * A szabadság kezdeti dátumát állítja be
     * @param szabiKezdete szabadság kezdésének dátuma
     */
    public void setSzabiKezdete(String szabiKezdete) {
        this.szabiKezdete = szabiKezdete;
    }

    /**
     * A szabadság vége dátumát adja vissza
     * @return szabadság végének dátuma
     */
    public String getSzabiVege() {
        return szabiVege;
    }

    /**
     * A szabadság végének dátumát állítja be
     * @param szabiVege szabadság végének dátuma
     */
    public void setSzabiVege(String szabiVege) {
        this.szabiVege = szabiVege;
    }

    /**
     * A szabadság hosszát adja vissza (hány munkanapig tart)
     * @return szabadság hossza
     */
    public int getSzabiHossza() {
        return szabiHossza;
    }

    /**
     * A szabadság hosszát állítja be (hány munkanapig tart)
     * @param szabiHossza szabadság hossza
     */
    public void setSzabiHossza(int szabiHossza) {
        this.szabiHossza = szabiHossza;
    }

}
