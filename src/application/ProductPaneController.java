package application;

import com.jfoenix.controls.JFXButton;

import data.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ProductPaneController extends Controller{

	static Product  productInit = null;
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


	private static Image flags[]= {new Image("file:images/UI_organic.png"),new Image("file:images/UI_glutenfree.png"),
			new Image("file:images/UI_vegan.png"),new Image("file:images/UI_diaryfree.png")};

	public void initialize() {
		product = productInit;
		productName.setText(product.getName() +" "+product.getBrand() + " " + product.getWeight());
		price.setText("€ "+product.getPrice());
		weightPrice.setText(product.getWeightPrice());
		
		
		
		//spinner q.tà
		if(product.getAvailable()!=0) {
			txtFinished.setVisible(false);
			spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getAvailable(), 1));
		}else {
			spinnerQuantity.setVisible(false);
			btnAdd.setDisable(true);
			txtFinished.setVisible(true);
		}
		

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
		ImageView a=null;
		for(int i=0;i<4;i++) {
			if (characteristics[i]==true) {
				a= new ImageView(flags[i]);
				a.setFitHeight(25);
				a.setFitWidth(25);
				flagFlowPane.getChildren().add(a);
			}

		}
	}
	

	static void setInitProduct(Product p) {
		productInit=p;
	}

	public void addProduct(ActionEvent e) {
		System.out.println("You want to buy "+spinnerQuantity.getValue() +" shitty " + product.getName()+"?");
	}
	
}
