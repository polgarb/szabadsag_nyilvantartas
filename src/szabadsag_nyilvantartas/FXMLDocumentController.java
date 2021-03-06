/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * A grafikus megjelenítésért felelős osztály
 * @author Polgár Béla
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField txtNev;

    @FXML
    private DatePicker dpSzulDatum;

    @FXML
    private ComboBox<Integer> cbGyerekDB;

    @FXML
    private TableView<Dolgozo> tblDolgozok;

    @FXML
    private TableColumn<Dolgozo, String> oNev;

    @FXML
    private TableColumn<Dolgozo, String> oSzulDatum;

    @FXML
    private TableColumn<Dolgozo, Integer> oGyerekDB;

    @FXML
    private TableColumn<Dolgozo, Integer> oEvesSzabi;

    @FXML
    private TableColumn<Dolgozo, Integer> oMaradekSzabi;

    @FXML
    private TableColumn<Dolgozo, Integer> oKivettSzabi;

    @FXML
    private DatePicker dpSzabiKezdet;

    @FXML
    private DatePicker dpSzabiVege;

    @FXML
    private ComboBox<String> cbNev;

    @FXML
    private TableView<Szabadsag> tblSzabadsagok;

    @FXML
    private TableColumn<Szabadsag, String> oNevSz;

    @FXML
    private TableColumn<Szabadsag, String> oSzabiKezdet;

    @FXML
    private TableColumn<Szabadsag, String> oSzabiVege;

    @FXML
    private TableColumn<Szabadsag, Integer> oSzabiHossza;

    
    /**
     * A Dolgozó fülön a Módosít gomb lenyomásakor lefutó metódus, ha van sor kijelölve, 
     * akkor módosítja a dolgozó adatait (DB osztály dolgozo_modositas), ha nincs, akkor pedig hibaüzenetet küld. 
     */
    @FXML
    void modositDolgozo() {
        int i = tblDolgozok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            int id = tblDolgozok.getItems().get(i).getId();
            dolgozoDB.dolgozo_modositas(id, txtNev.getText(), dpSzulDatum.getValue().toString(), cbGyerekDB.getValue());
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
            szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
            tblDolgozok.getSelectionModel().select(i);
        } else {
            ertesites("Hiba", "Nincs kiválasztva sor");
        }
    }

    @FXML
    void modositSzabadsag() {
        int i = tblSzabadsagok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            int id = tblSzabadsagok.getItems().get(i).getId();
            if (!cbNev.getValue().equals(tblSzabadsagok.getItems().get(i).getNev())) {
                ertesites("Hibás Név", "A név nem módosítható a szabadságnál");
            } else if (dpSzabiVege.getValue().isBefore(dpSzabiKezdet.getValue())) {
                ertesites("Hibás Dátum", "A szabadság vége nem lehet korábbi dátum a kezdeténél");
            } else if (dpSzabiKezdet.getValue().getYear() != 2019 || dpSzabiVege.getValue().getYear() != 2019) {
                ertesites("Hibás Dátum", "A szabadság kezdete és vége is csak 2019 -es évben lehet");
            } else if (szabadsagDB.szabinVanE(cbNev.getValue(), dpSzabiKezdet.getValue(), dpSzabiVege.getValue(), id)) {
                ertesites("Hibás Dátum", "Már szabadságon van");
            } else if (szabadsagDB.vanEelegszabija(cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString(), tblSzabadsagok.getItems().get(i).getSzabiHossza())) {
                ertesites("Hibás Dátum", "Nincs elég szabadsága");
            } else {
                szabadsagDB.szabadsag_modositas(id, cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString());
                szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
                dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
                tblSzabadsagok.getSelectionModel().select(i);
            }
        } else {
            ertesites("Hiba", "Nincs kiválasztva sor");
        }
    }

    @FXML
    void torolDolgozo() {
        int i = tblDolgozok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            int id = tblDolgozok.getItems().get(i).getId();
            dolgozoDB.dolgozo_torles(id);
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
            szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
            int utolso = tblDolgozok.getItems().size() - 1;
            if (i <= utolso) {
                tblDolgozok.getSelectionModel().select(i);
            } else if (i > 0) {
                tblDolgozok.getSelectionModel().select(i - 1);
            }
        } else {
            ertesites("Hiba", "Nincs kiválasztva sor");
        }

    }

    @FXML
    void torolSzabadsag() {
        int i = tblSzabadsagok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            int id = tblSzabadsagok.getItems().get(i).getId();
            int szabiHossza = tblSzabadsagok.getItems().get(i).getSzabiHossza();
            String nev = tblSzabadsagok.getItems().get(i).getNev();
            szabadsagDB.maradek_szabadsag_modositas(nev, szabiHossza);
            szabadsagDB.szabadsag_torles(id);
            szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
            int utolso = tblSzabadsagok.getItems().size() - 1;
            if (i <= utolso) {
                tblSzabadsagok.getSelectionModel().select(i);
            } else {
                tblSzabadsagok.getSelectionModel().select(i - 1);
            }

        } else {
            ertesites("Hiba", "Nincs kiválasztva sor");
        }

    }

    @FXML
    void ujDolgozo() {
        if (txtNev.getText().equals("")) {
            ertesites("Hibás Név", "Nincs kitöltve a név mező");
            txtNev.requestFocus();
        } else {
            dolgozoDB.ujdolgozo(txtNev.getText(), dpSzulDatum.getValue().toString(), cbGyerekDB.getValue());
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
        }
    }

    @FXML
    void ujSzabadsag() {
        String a = cbNev.getValue();
        if (a == null) {
            ertesites("Hibás Név", "Nem lett név kíválasztva");
        } else if (dpSzabiVege.getValue().isBefore(dpSzabiKezdet.getValue())) {
            ertesites("Hibás Dátum", "A szabadság vége nem lehet korábbi dátum a kezdeténél");
        } else if (dpSzabiKezdet.getValue().getYear() != 2019 || dpSzabiVege.getValue().getYear() != 2019) {
            ertesites("Hibás Dátum", "A szabadság kezdete és vége is csak 2019 -es évben lehet");
        } else if (szabadsagDB.szabinVanE(cbNev.getValue(), dpSzabiKezdet.getValue(), dpSzabiVege.getValue(), 0)) {
            ertesites("Hibás Dátum", "Már szabadságon van");
        } else if (szabadsagDB.vanEelegszabija(cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString(), 0)) {
            ertesites("Hibás Dátum", "Nincs elég szabadsága");
        } else {
            szabadsagDB.uj_szabadsag(cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString());
            szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());

        }
    }

    @FXML
    private DatePicker dpExtraNap;

    @FXML
    private ComboBox<String> cbDatumTipus;

    @FXML
    private TableView<ExtraDatum> tblExtraNap;

    @FXML
    private TableColumn<ExtraDatum, String> oExtraDatum;

    @FXML
    private TableColumn<ExtraDatum, String> oExtraTipus;

    @FXML
    void extradatum_hozzaad() {
        try {
            String datum = dpExtraNap.getValue().toString();
            String tipus = cbDatumTipus.getValue();
            if (tipus == null) {
                ertesites("Hiba", "Típus nincs kiválasztva");
            } else {
                extradatumDB.uj_extranap(datum, tipus);
                extradatumDB.extranap_lista(tblExtraNap.getItems());
            }
        } catch (Exception e) {
            ertesites("Hiba", "Dátum nincs kiválasztva");
        }
    }

    @FXML
    void extradatum_torol() {
        int i = tblExtraNap.getSelectionModel().getSelectedIndex();

        if (i > -1) {
            int id = tblExtraNap.getItems().get(i).getId();
            extradatumDB.extranap_torles(id);
            extradatumDB.extranap_lista(tblExtraNap.getItems());

            int utolso = tblExtraNap.getItems().size() - 1;
            if (i <= utolso) {
                tblExtraNap.getSelectionModel().select(i);
            } else if (i > 0) {
                tblExtraNap.getSelectionModel().select(i - 1);
            }
        }
    }

    @FXML
    void vegeDatumBeallit() {
        dpSzabiVege.setValue(dpSzabiKezdet.getValue());
    }

    @FXML
    void informacio() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Információ");
        alert.setHeaderText("A programot készítette : Polgár Béla");
        alert.setContentText("Segítség a program használatához :");

        ButtonType link = new ButtonType("Program kézikönyve");
        alert.getButtonTypes().clear();

        alert.getButtonTypes().addAll(link);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent()) {
            Desktop.getDesktop().browse(new URI("https://polgarb.github.io/szabadsag_nyilvantartas/"));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //dolgozók tábla
        cbGyerekDB.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        oNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oSzulDatum.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        oGyerekDB.setCellValueFactory(new PropertyValueFactory<>("gyerekDB"));
        oEvesSzabi.setCellValueFactory(new PropertyValueFactory<>("evesSzabadsag"));
        oMaradekSzabi.setCellValueFactory(new PropertyValueFactory<>("maradekSzabadsag"));
        oKivettSzabi.setCellValueFactory(new PropertyValueFactory<>("kivettszabi"));

        dpSzulDatum.setValue(LocalDate.parse("1980-01-01"));
        cbGyerekDB.setValue(0);

        dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());

        tblDolgozok.getSelectionModel().selectedIndexProperty().addListener((o, regi, uj) -> dolgozok_tablabol(uj.intValue()));
        //szabadság tábla
        oNevSz.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oSzabiKezdet.setCellValueFactory(new PropertyValueFactory<>("szabiKezdete"));
        oSzabiVege.setCellValueFactory(new PropertyValueFactory<>("szabiVege"));
        oSzabiHossza.setCellValueFactory(new PropertyValueFactory<>("szabiHossza"));

        dpSzabiKezdet.setValue(LocalDate.now());
        dpSzabiVege.setValue(dpSzabiKezdet.getValue());

        dpSzabiKezdet.setShowWeekNumbers(true);
        dpSzabiVege.setShowWeekNumbers(true);

        szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());

        tblSzabadsagok.getSelectionModel().selectedIndexProperty().addListener((o, regi, uj) -> szabadsagok_tablabol(uj.intValue()));

        //extranap tábla
        cbDatumTipus.getItems().addAll("Munkaszüneti nap", "Ledolgozás, áthelyezett munkanap");

        oExtraDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        oExtraTipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));

        extradatumDB.extranap_lista(tblExtraNap.getItems());

        tblExtraNap.getSelectionModel().selectedIndexProperty().addListener((o, regi, uj) -> extranap_tablabol(uj.intValue()));
    }

    DB dolgozoDB = new DB();
    DB szabadsagDB = new DB();
    DB extradatumDB = new DB();

    private void dolgozok_tablabol(int i) {
        if (i == -1) {
            return;
        }
        Dolgozo d = tblDolgozok.getItems().get(i);
        txtNev.setText(d.getNev());
        dpSzulDatum.setValue(LocalDate.parse(d.getSzuletesiDatum()));
        cbGyerekDB.setValue(d.getGyerekDB());
    }

    private void szabadsagok_tablabol(int i) {
        if (i == -1) {
            return;
        }
        Szabadsag sz = tblSzabadsagok.getItems().get(i);
        cbNev.setValue(sz.getNev());
        dpSzabiKezdet.setValue(LocalDate.parse(sz.getSzabiKezdete()));
        dpSzabiVege.setValue(LocalDate.parse(sz.getSzabiVege()));
    }

    private void extranap_tablabol(int i) {
        if (i == -1) {
            return;
        }
        ExtraDatum e = tblExtraNap.getItems().get(i);
        cbDatumTipus.setValue(e.getTipus());
        dpExtraNap.setValue(LocalDate.parse(e.getDatum()));

    }

    private void ertesites(String cim, String szoveg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(cim);
        alert.setHeaderText(null);
        alert.setContentText(szoveg);
        alert.showAndWait();
    }

}
