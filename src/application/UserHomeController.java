package application;

import java.io.IOException;
import java.util.HashSet;

import com.jfoenix.controls.JFXButton;

import data.Product;
import data.Sections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		lblHiUser.setText("Ciao, " + Globals.currentUser.getAnagrafica().getName() + " :)");
		
		if(Globals.cart.getNumberOfProd()==0) {
			circleCartNumber.setVisible(false);
			lblCartNumber.setVisible(false);
		}
	}
	
	
	
	
		/*
		 * 
		 * 		GESTORI  BOTTONI
		 * 				|
		 * 				v
		 */
	
	//ICONA PROFILO
	/**
	 * Al rilevamento di clic su user apre il profilo
	 * 
	 * @param e ActionEvent click sul'icona user
	 */
	public void loadProfile(ActionEvent e) {
		launchUI("/application/UserProfile.fxml");
	}


	public void openCart(ActionEvent e) {
		launchUI("/application/Cart.fxml");
	}
	
	

	public void loadSection(ActionEvent e) {

		HashSet<Product> section=null;
		
		
		if(((JFXButton)e.getSource()).getId().equals("btnVeg"))
			section=Sections.vegetali;
		
		ScrollPane newpane = formPanel(section);

		mainPane.getChildren().add(newpane);

		// Il nuovo pannello deve essere grande tanto quanto il pannello di partenza,
		// quindi faccio un bel fit
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);

	}

	/**
	 * E' la funzione che genera il pannello con tutti i prodotti
	 * 
	 * @param set Prende un set generico di oggetti (Può essere il risultato di una
	 *            ricerca o anche il set di prodotti di un reparto)
	 * @return uno scrollpane con gli oggetti mostrati.
	 */
	private ScrollPane formPanel(HashSet<Product> products) {
		

		//// DICHIARO I PANNELLI NECESSARI:

		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();


		
		
		// 1. Genero la barra x filtrare //TODO ora è un dummy
		try {
			flowProdotti.getChildren().add(FXMLLoader.load(getClass().getResource("/application/FilterOrderBar.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		

		if(products==null) {
			System.out.println("[✓] Caricata sezione vuota");
			return scroller;
			}
		
		
		// 2. Genero tutti i prodotti

		for (Product p : products) {

			ProductPaneController.setInitProduct(p);
			System.out.println("[✓] Inizializzato product");
			AnchorPane productPane=null;
			
			try {
				productPane = FXMLLoader.load(this.getClass().getResource("/application/ProductPane.fxml"));

			//	System.out.println("[✓] Generato productpane: " + productPane +" for "+ p.getName());
			} catch (Exception e) {
				System.out.println("[x] Fxml non pervenuto :(" + e);
			}
			
			
			flowProdotti.getChildren().add(productPane);

			scroller.setFitToWidth(true);
			
			
		}

		scroller.setContent(flowProdotti);
		return scroller;
	}

}
