/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szabadsag_nyilvantartas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ComboBox<?> cbGyerekDB;

    @FXML
    private TableView<?> tblDolgozok;

    @FXML
    private TableColumn<?, ?> oNev;

    @FXML
    private TableColumn<?, ?> oSzulDatum;

    @FXML
    private TableColumn<?, ?> oGyerekDB;

    @FXML
    private TableColumn<?, ?> oEvesSzabi;

    @FXML
    private TableColumn<?, ?> oMaradekSzabi;

    @FXML
    private TableColumn<?, ?> oFizuNelkul;

    @FXML
    private DatePicker dpSzabiKezdet;

    @FXML
    private DatePicker dbSzabiVege;

    @FXML
    private ComboBox<?> cbNev;

    @FXML
    private TableView<?> tblSzabadsagok;

    @FXML
    private TableColumn<?, ?> oNevSz;

    @FXML
    private TableColumn<?, ?> oSzabiKezdet;

    @FXML
    private TableColumn<?, ?> oSzabiVege;

    @FXML
    private TableColumn<?, ?> oSzabiHossza;

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
        // TODO
    }    
    
}
