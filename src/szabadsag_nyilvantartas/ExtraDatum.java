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
public class ExtraDatum {
    String datum;
    int ertek;
    String tipus;

    public ExtraDatum(String datum, int ertek) {
        this.datum = datum;
        this.ertek = ertek;
        setTipus(ertek);
    }
    
    
    
    public void setTipus(int ertek){
        if (ertek == -1) 
            tipus = "Munkaszüneti nap";
        else
            tipus = "Ledolgozás, áthelyezett munkanap";
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getErtek() {
        return ertek;
    }

    public void setErtek(String tipus) {
        if (tipus.equals("Munkaszüneti nap"))
                ertek = -1;
        else 
            ertek = 1;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
    
    
    
}
