package application;

import java.io.IOException;
import java.util.HashSet;
import java.util.PriorityQueue;

import com.jfoenix.controls.JFXButton;

import data.Product;
import data.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Gestisce la pagina "home" dell'utente; in particolare tutti gli input che può
 * ricevere (es. click sulla X, click su un reparto) per esempio cambiando i
 * pane o chiamando la funzione di salvataggio quando si esce
 * 
 * (! la generazione dei pane meglio lasciarla delegata ad altre funzioni)
 */
public class UserHomeController extends Controller {
	

	
	///	PANNELLI SU CUI ATTACCHIAMO LE SUDDETTE COSE
	@FXML
	private GridPane gridmainpane;

	@FXML
	private AnchorPane mainPane; // <-- QUESTO è IL PANNELLO CHE MANTIENE TUTTE LE NOSTRE COSE (=LISTA PRODOTTI)
	// OGNI VOLTA CHE DOBBIAMO CAMBIARE FINESTRA DOBBIAMO MODIFICARE LUI
	// CON "mainPane.getChildren().setAll(pane)" !!!
	/*
	 * Per intenderci:
	 * 
	 * -La finestra è basata su un GridPane che divide la schermata così
	 * 
	 *  ____________
	 * |      1.    |--->1. E' una menubar "homebrew" perché quella "vera" continuava a sballarsi xD
	 * |____________|		Così possiamo personalizzarla meglio imo
	 * |            |
	 * |            |--->2. E' tutta la parte  inferiore, la quale è a sua volta divisa così:	
	 * |     2.     |         ____________
	 * |            |        |----|       |	->	2a. E' la barra con i reparti; contiene una GridPane che divide 	
	 * |____________|		 |-2a-|  2b   |         in fasce orizzontali (una per reparto )
	 * 						 |----|       |
	 *					     |____|_______|	->	2b. E' un PANE GENERICO che all'inizio contiene la scritta "Benvenuto"
	 *												 [ ! ! ! ]	QUESTO è QUELLO CHE VA SOSTITUITO OGNI VOLTA
	 */

	
	@FXML 
	private Text lblHiUser;
	@FXML
	private Label lblCartNumber;
	@FXML
	private Circle circleCartNumber;
	@FXML
	private JFXButton btnVeg;
	@FXML
	private JFXButton btnFish;
	@FXML
	private JFXButton btnMeat;
	@FXML
	private JFXButton btnDiary;
	@FXML
	private JFXButton btnDrink;
	
	
	public void initialize() {
		
		//Saluta Antonio
		lblHiUser.setText("Ciao, " + Globals.currentUser.getAnagrafica().getName() + " :)");
		lblCartNumber.setText(""+Globals.cart.getNumberOfProd());
		
		//(De-)Visualizza correttamente la notifica del carrello
		if(Globals.cart.getNumberOfProd()==0) {
			circleCartNumber.setVisible(false);
			lblCartNumber.setVisible(false);
		}
	}
	
	
	
	
		/* 
		 * 		GESTORI  BOTTONI
		 * 				|
		 * 				v
		 */

	public void loadProfile(ActionEvent e) {
		launchUI("/application/UserProfile.fxml");
	}


	public void openCart(ActionEvent e) {
		launchUI("/application/Cart.fxml");
	}
	
	
	
	
	
	//Visualizzazione e ricerca prodotti (sostanzialmente devono generare la queue di prodotti; poi ci pensa il viewer a visualizzarla

	public void loadSection(ActionEvent e) {

		PriorityQueue<Product> section=null;

		
		
		//Scopri quale è il reparto richiesto
		String sourcebutton = ((JFXButton)e.getSource()).getId();
		
		if(sourcebutton.equals("btnVeg"))
			section=Globals.vegetali.getProducts();
	
		if(sourcebutton.equals("btnFish"))
			section=Globals.pesce.getProducts();
		
		if(sourcebutton.equals("btnMeat"))
			section=Globals.carne.getProducts();

		if(sourcebutton.equals("btnDiary"))
			section=Globals.latticini.getProducts();

		if(sourcebutton.equals("btnDrink"))
			section=Globals.bevande.getProducts();
	
	
		//Per riutilizzare facilmente questo codice anche nella parte del Worker, usiamo un gestore della view dei prodotti chiamato ProductViewer :)
		ProductViewer viewer = new ProductViewer(mainPane,section);
	}


	
	
	
	public void loadSearch() {
		
		PriorityQueue<Product> result = new PriorityQueue<Product>();
		
		for(Section s : Globals.reparti) {
			for(Product p : s.getProducts()) {
			//	if(p.search("albicocche")!=null)
				//result.add(p);
				
			}
		}
		
	}

}
