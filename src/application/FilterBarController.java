package application;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class FilterBarController extends Controller {
	
	@FXML
	private ChoiceBox<String> chboxOrder;
	@FXML
	private ChoiceBox<String> chboxBrand;

	
	@FXML
	private JFXToggleButton toggleGluten;
	@FXML
	private JFXToggleButton toggleDiary;
	@FXML
	private JFXToggleButton toggleBio;
	@FXML
	private JFXToggleButton toggleVegan;
	
	
	
	
	public void initialize() {
		
		String choices[] = {"Prezzo crescente","Prezzo decrescente","Marca"};

		for(String s : choices)
			chboxOrder.getItems().add(s);
		
		chboxBrand.getItems().add("Tutti");
		chboxBrand.setValue("Tutti");
		for(String s : Globals.brand)
			chboxBrand.getItems().add(s);
		
		chboxOrder.setValue("Prezzo crescente");

		chboxOrder.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) ->  Globals.currentView.changeOrder(newValue) );

		chboxBrand.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> this.brandfilter(newValue) );
		
		
	}
	
	
	private void brandfilter(String newValue) {
		
		boolean selectedCharas[] = {toggleVegan.isSelected(),toggleDiary.isSelected(),toggleBio.isSelected(),toggleGluten.isSelected()};

		Globals.currentView.filterChara(selectedCharas,chboxBrand.getValue());
	}


	public void filter(ActionEvent ae) {


		boolean selectedCharas[] = {toggleVegan.isSelected(),toggleDiary.isSelected(),toggleBio.isSelected(),toggleGluten.isSelected()};
		

		Globals.currentView.filterChara(selectedCharas,chboxBrand.getValue());
		
		
	}

}
