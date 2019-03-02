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
public class Szabadsag {
    private int id;
    private String nev;
    private String szabiKezdete;
    private String szabiVege;
    private int szabiHossza;

    public Szabadsag(int id, String nev, String szabiKezdete, String szabiVege, int szabiHossza) {
        this.id = id;
        this.nev = nev;
        this.szabiKezdete = szabiKezdete;
        this.szabiVege = szabiVege;
        this.szabiHossza = szabiHossza;
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

    public String getSzabiKezdete() {
        return szabiKezdete;
    }

    public void setSzabiKezdete(String szabiKezdete) {
        this.szabiKezdete = szabiKezdete;
    }

    public String getSzabiVege() {
        return szabiVege;
    }

    public void setSzabiVege(String szabiVege) {
        this.szabiVege = szabiVege;
    }

    public int getSzabiHossza() {
        return szabiHossza;
    }

    public void setSzabiHossza(int szabiHossza) {
        this.szabiHossza = szabiHossza;
    }
    
    
}
