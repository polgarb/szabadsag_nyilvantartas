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

    }

    @FXML
    void modositSzabadsag() {

    }

    @FXML
    void torolDolgozo() {

    }

    @FXML
    void torolSzabadsag() {

    }

    @FXML
    void ujDolgozo() {

    }

    @FXML
    void ujSzabadsag() {

    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //dolgozók tábla        
        oNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oSzulDatum.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        oGyerekDB.setCellValueFactory(new PropertyValueFactory<>("gyerekDB"));
        oEvesSzabi.setCellValueFactory(new PropertyValueFactory<>("evesSzabadsag"));
        oMaradekSzabi.setCellValueFactory(new PropertyValueFactory<>("maradekSzabadsag"));
        oFizuNelkul.setCellValueFactory(new PropertyValueFactory<>("fizetesNelkuli"));
        
        dolgozoDB.dolgozo_lista(tblDolgozok.getItems(), cbNev.getItems());
        
        tblDolgozok.getSelectionModel().selectedIndexProperty().addListener((o,regi,uj) -> dolgozok_tablabol(uj.intValue()));
        //szabadság tábla
        oNevSz.setCellValueFactory(new PropertyValueFactory<>("nev"));
        oSzabiKezdet.setCellValueFactory(new PropertyValueFactory<>("szabiKezdete"));
        oSzabiVege.setCellValueFactory(new PropertyValueFactory<>("szabiVege"));
        oSzabiHossza.setCellValueFactory(new PropertyValueFactory<>("szabiHossza"));
        
        szabadsagDB.szabadsag_lista(tblSzabadsagok.getItems());
        
        tblSzabadsagok.getSelectionModel().selectedIndexProperty().addListener((o,regi,uj) -> szabadsagok_tablabol(uj.intValue()));
    }  
    
       
    DB dolgozoDB = new DB();
    DB szabadsagDB = new DB();
    
    private void dolgozok_tablabol(int i) {
        if (i == -1) return;
        Dolgozo d = tblDolgozok.getItems().get(i);
        txtNev.setText(d.getNev());
        dpSzulDatum.setValue(LocalDate.parse(d.getSzuletesiDatum()));
        cbGyerekDB.setValue(d.getGyerekDB());
    }
    
    private void szabadsagok_tablabol(int i) {
        if (i == -1) return;
        Szabadsag sz = tblSzabadsagok.getItems().get(i);
        cbNev.setValue(sz.getNev());
        dpSzabiKezdet.setValue(LocalDate.parse(sz.getSzabiKezdete()));
        dpSzabiVege.setValue(LocalDate.parse(sz.getSzabiVege()));
    }
    
}
