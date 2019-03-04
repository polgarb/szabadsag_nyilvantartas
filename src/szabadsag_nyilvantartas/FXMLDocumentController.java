/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
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
    private TableColumn<Dolgozo, Integer> oFizuNelkul;

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

    @FXML
    void modositDolgozo() {
        int i = tblDolgozok.getSelectionModel().getSelectedIndex();
        if (i > -1) {
            int id = tblDolgozok.getItems().get(i).getId();
            dolgozoDB.dolgozo_modositas(id, txtNev.getText(), dpSzulDatum.getValue().toString(), cbGyerekDB.getValue());
            dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
            tblDolgozok.getSelectionModel().select(i);
        }
    }

    @FXML
    void modositSzabadsag() {
        int i = tblSzabadsagok.getSelectionModel().getFocusedIndex();
        if (i > -1) {
            int id = tblSzabadsagok.getItems().get(i).getId();
            if (dpSzabiVege.getValue().isBefore(dpSzabiKezdet.getValue())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hibás Dátum");
                alert.setHeaderText(null);
                alert.setContentText("A szabadság vége nem lehet korábbi dátum");
                alert.showAndWait();
            } else {
                if (szabadsagDB.szabinVanE(cbNev.getValue(), dpSzabiKezdet.getValue(), dpSzabiVege.getValue(), id)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hibás Dátum");
                alert.setHeaderText(null);
                alert.setContentText("Már szabadságon van");
                alert.showAndWait();
            } else {
                szabadsagDB.szabadsag_modositas(id, cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString());
                szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
                dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
                tblSzabadsagok.getSelectionModel().select(i);
                }
                }
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

        }

    }

    @FXML
    void ujDolgozo() {
        dolgozoDB.ujdolgozo(txtNev.getText(), dpSzulDatum.getValue().toString(), cbGyerekDB.getValue());
        dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
    }

    @FXML
    void ujSzabadsag() {
        if (dpSzabiVege.getValue().isBefore(dpSzabiKezdet.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hibás Dátum");
            alert.setHeaderText(null);
            alert.setContentText("A szabadság vége nem lehet korábbi dátum");
            alert.showAndWait();
        } else {
            if (szabadsagDB.szabinVanE(cbNev.getValue(), dpSzabiKezdet.getValue(), dpSzabiVege.getValue(), 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hibás Dátum");
                alert.setHeaderText(null);
                alert.setContentText("Már szabadságon van");
                alert.showAndWait();
            } else {
                szabadsagDB.uj_szabadsag(cbNev.getValue(), dpSzabiKezdet.getValue().toString(), dpSzabiVege.getValue().toString());
                szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
                dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
            }
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
        oFizuNelkul.setCellValueFactory(new PropertyValueFactory<>("fizetesNelkuli"));

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

        szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());

        tblSzabadsagok.getSelectionModel().selectedIndexProperty().addListener((o, regi, uj) -> szabadsagok_tablabol(uj.intValue()));
    }

    DB dolgozoDB = new DB();
    DB szabadsagDB = new DB();

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

}
