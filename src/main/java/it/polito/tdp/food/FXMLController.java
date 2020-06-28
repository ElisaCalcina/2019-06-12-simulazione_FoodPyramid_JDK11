package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Archi;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
		private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<?> boxIngrediente;

    @FXML
    private Button btnDietaEquilibrata;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaDieta(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String calorie= txtCalorie.getText();
    	Integer calorieI=0;
    	
    	try {
    		calorieI=Integer.parseInt(calorie);
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero");
    	}

    	this.model.creaGrafo(calorieI);
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("#vertici: "+ this.model.nVertici()+"\n");
    	txtResult.appendText("#archi: "+ this.model.nArchi()+"\n");
    	
    	for(Archi a: this.model.listaC()) {
    		txtResult.appendText(a.getC1().getDisplay_name()+" "+a.getC1().getCondiment_calories()+" "+ this.model.nCibi(a.getC1()));
    	}
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    
}


	public void setModel(Model model) {
		this.model = model;
	}
}
