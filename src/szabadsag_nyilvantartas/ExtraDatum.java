/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

/**
 * Egy munkaszüneti nap vagy egy ledolgozásos szombat adatai
 * 
 * @author Polgár Béla
 */
public class ExtraDatum {

    int id;
    String datum;
    int ertek;
    String tipus;
    
    /**
     * 
     * @param id egyedi sorszáma az extra napnak
     * @param datum az extra nap dátuma
     * @param ertek az extra nap értéke (értéke -1, ha munkaszüneti nap, +1 ha ledolgozásos Szombat)
     */
    public ExtraDatum(int id, String datum, int ertek) {
        this.id = id;
        this.datum = datum;
        this.ertek = ertek;
        setTipus(ertek);
    }
    /**
     * @return Az extra nap sorszámát adja vissza
     */
    public int getId() {
        return id;
    }
    
    /**
     * Az egyedi sorszámot állítja be
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Az extra nap értéke alapján a típusát beállítja
     * @param ertek Ha -1 akkor munkaszüneti nap, ha +1 akkor ledolgozásos Szombat
     */
    private void setTipus(int ertek) {
        if (ertek == -1) {
            tipus = "Munkaszüneti nap";
        } else {
            tipus = "Ledolgozás, áthelyezett munkanap";
        }
    }
    /**
     * Az extra nap dátumát adja vissza
     * @return 
     */
    public String getDatum() {
        return datum;
    }

    /**
     * A datum ként kapott dátumot állítja be az adott extra naphoz
     * @param datum 
     */
    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    /**
     * Az extra nap értékét kérdezi le
     * @return értéke -1, ha munkaszüneti nap, +1 ha ledolgozásos Szombat
     */
    public int getErtek() {
        return ertek;
    }
    
    /**
     * Az extra nap értékét állítja be a típusban kapott string alapján
     * @param tipus Ha munakszüneti nap akkor érték -1, ha más akkor +1
     */
    public void setErtek(String tipus) {
        if (tipus.equals("Munkaszüneti nap")) {
            ertek = -1;
        } else {
            ertek = 1;
        }
    }
    
    /**
     * Az extra nap típusát kérdezi le
     * @return Vagy munkaszüneti nap vagy ledolgozás, áthelyezett munkanap
     */
    public String getTipus() {
        return tipus;
    }
    
    /**
     * Az extra nap típusát állítja be
     * @param tipus 
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

}
