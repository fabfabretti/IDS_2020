package application;

import com.jfoenix.controls.JFXButton;

import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ProductPaneController extends Controller{

	private Product  product = null;


	@FXML
	private ImageView imagePath;
	@FXML
	private Text txtFinished;

	@FXML 
	private Text productName;

	@FXML
	private Label weightPrice; 

	@FXML
	private Label price;

	@FXML
	private FlowPane flagFlowPane;
	
	
	@FXML
	private Spinner<Integer> spinnerQuantity;
	@FXML
	private JFXButton btnAdd;


	Image flags[]= {new Image("file:images/UI_organic.png"),new Image("file:images/UI_glutenfree.png"),
			new Image("file:images/UI_vegan.png"),new Image("file:images/UI_diaryfree.png")};

	public void initialize() {
		
		if(product.getAvailable()!=0)
			spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getAvailable(), 1));
		else {
			spinnerQuantity.setVisible(false);
			btnAdd.setDisable(true);
			txtFinished.setVisible(true);
		}
		
		

		productName.setText(product.getName() +  " " + product.getWeight());
		price.setText(product.getPrice()+"€");
		weightPrice.setText(product.getWeightPrice());


		//image prodotto
		Image image;
		try {
			image = new Image(product.getImagePath());    
		}catch(Exception e) {

			image = new Image("file:images/Product_placeholder.png");    
		}
		imagePath.setImage(image);

		//Lista caratteristiche
		boolean characteristics[] = product.getCharacteristics();

		for(int i=0;i<4;i++) {
			if (characteristics[i]==true)
				flagFlowPane.getChildren().add(new ImageView(flags[i]));

		}


	}
	
	void setProduct(Product p) {
		product=p;
	}


	
	
}
