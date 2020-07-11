package application;

import data.Product;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
/**
 * Superclasse che contiene la roba condivisa fra tutti i productPane.
 *
 */
public class ProductPaneController extends Controller{

	//Necessario per sapere che prodotto devo mostrare.
	protected static Product  productInit = null;
	
	//Necessario per sapere che prodotto sto mostrando.
	protected Product  product = null;

	//FXML

	@FXML
	protected ImageView imagePath;
	
	@FXML 
	protected Text productName;
	
	//Sono le immagini delle flaag (glutine, vegan...()
	protected static Image flags[]= {new Image("file:images/UI_organic.png"),new Image("file:images/UI_glutenfree.png"),
			new Image("file:images/UI_vegan.png"),new Image("file:images/UI_diaryfree.png")};

	static void setInitProduct(Product p) {
		productInit=p;
	}


	
}
