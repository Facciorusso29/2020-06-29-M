/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.boxRegista.getItems().clear();
    	Integer anno= this.boxAnno.getValue();
    	model.creaGrafo(anno);
    	this.txtResult.setText("Vertici: "+model.numVertici()+"\nArchi: "+model.numArchi());
    	this.boxRegista.getItems().addAll(model.getDirectors());
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	this.txtResult.clear();
    	Director d=this.boxRegista.getValue();
    	List <Director> adiacenti=model.getAdiacenti(d);
    	this.txtResult.appendText("REGISTI ADIACENTI A: "+d+"\n");
    	for(Director dir : adiacenti) {
    		this.txtResult.appendText(dir+" - # attori condivisi: "+dir.getPeso()+"\n");
    	}

    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	this.txtResult.clear();
    	Director d=this.boxRegista.getValue();
    	String n=this.txtAttoriCondivisi.getText();
    	int c=0;
    	try {
    		c=Integer.parseInt(n);
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero intero!\n");
    	}
    	List <Director> massimo= model.cercaMassimo(c, d);
    	this.txtResult.appendText("Peso Cammino: "+model.pesoCamminoMassimo()+"\n");
    	for(Director dir: massimo) {
    		this.txtResult.appendText(dir+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	this.boxAnno.getItems().add(2004);
    	this.boxAnno.getItems().add(2005);
    	this.boxAnno.getItems().add(2006);
    	
    	
    }
    
}
