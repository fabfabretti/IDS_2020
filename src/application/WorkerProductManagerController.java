package application;

import java.util.TreeSet;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import data.Product;
import data.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class WorkerProductManagerController {

	@FXML 
	private AnchorPane viewScrollPane;
	@FXML 
	private AnchorPane mainEditor;
	@FXML 
	private AnchorPane defaultPane;
	
	@FXML
	private Text txtStatus;
	
	
	//editor
	@FXML
	private JFXTextField fieldName;
	@FXML
	private JFXTextField fieldBrand;
	@FXML
	private JFXTextField fieldWeight;
	@FXML
	private JFXTextField fieldPriceWeight;
	@FXML
	private JFXTextField fieldPrice;
	@FXML
	private Text  txtCode;
	@FXML
	private ImageView  imgImage;
	@FXML
	private JFXTextField  fieldImagePath;
	

	@FXML
	private Spinner<Integer> spinnerQuantity;

	@FXML
	private JFXToggleButton toggleBio;
	@FXML
	private JFXToggleButton toggleVegan;
	@FXML
	private JFXToggleButton toggleDiary;
	@FXML
	private JFXToggleButton toggleGluten;
	
	
	
	private Product displayed;

	public void initialize(){
		
		txtStatus.setText("");
		mainEditor.setVisible(false);
		defaultPane.setVisible(true);
		
		
		
		//carichiamo TUTTI i prodotti!
		
		Globals.editController=this;
		
		TreeSet<Product> allproducts = new TreeSet<Product>();
		
		for(Section s : Globals.reparti) 
			allproducts.addAll(s.getProducts());
		
		
		ProductViewer viewer = new ProductViewer(viewScrollPane,allproducts,"edit");
	}
	
	
	public void editInfo(Product p){
		
		displayed=p;
		defaultPane.setVisible(false);
		mainEditor.setVisible(true);
		
		fieldName.setText(p.getName());
		fieldBrand.setText(p.getBrand());
		fieldWeight.setText(p.getWeight());
		fieldPriceWeight.setText(p.getWeightPrice());
		fieldPrice.setText(p.getPrice()+"");
		fieldImagePath.setText(p.getImagePath());
		txtCode.setText("Codice Prodotto:" + p.getBarCode());
		
		spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, p.getAvailable()));
		

		Image image;
		try {
				image = new Image(p.getImagePath());  
		}catch(Exception e) {

			image = new Image("file:images/Product_placeholder.png");   
		}
		imgImage.setImage(image);

		toggleBio.setSelected(p.getCharacteristics()[0]);
		toggleGluten.setSelected(p.getCharacteristics()[1]);
		toggleVegan.setSelected(p.getCharacteristics()[2]);
		toggleDiary.setSelected(p.getCharacteristics()[3]);
	}
	
	
	
	public void save(ActionEvent e) {
		
		
		//controlli!
		if(fieldName.getText().equals("") || fieldBrand.getText().equals("") || fieldWeight.getText().equals("") || fieldPriceWeight.getText().equals("") ||fieldPrice.getText().equals("") ) {
			txtStatus.setText("Errore: è necessario compilare tutti i campi!");
			return;
		}			
		
		if(spinnerQuantity.getValue() < 0) {
			txtStatus.setText("Errore: la quantità disponibile deve essere un numero non negativo.");
			return;
		}
		
/*		if(Float.parseFloat(fieldPrice.getText()) < 0 || Float.parseFloat(fieldPriceWeight.getText()) <= 0) {
			txtStatus.setText("Errore: il prezzo deve essere maggiore di zero.");
			return;
		}
	
		if(Float.parseFloat(fieldWeight.getText())<= 0) {
			txtStatus.setText("Errore: Il peso deve essere maggiore di zero.");
			return;
		}
		
		
		if(Float.parseFloat(fieldPrice.getText()) == Float.NaN || Float.parseFloat(fieldPriceWeight.getText()) == Float.NaN  ||  Float.parseFloat(fieldWeight.getText()) == Float.NaN) {
			txtStatus.setText("Errore: un valore non è valido.");
			return;
		}
	*/		
		
		if(Float.parseFloat(fieldPrice.getText()) == Float.NaN) {
			txtStatus.setText("Errore: un valore non è valido.");
			return;
	}
		
		
		//salvataggio
		displayed.setName(fieldName.getText());
		
		displayed.setBrand(fieldBrand.getText());
		
		displayed.setWeight(fieldWeight.getText());
		
		displayed.setWeightPrice(fieldPriceWeight.getText());

		displayed.setPrice(Float.parseFloat(fieldPrice.getText()));

		displayed.setImagePath(fieldImagePath.getText());

		displayed.setAvailable(spinnerQuantity.getValue());
		
		
		boolean chara[] = {toggleBio.isSelected(),toggleGluten.isSelected(),toggleVegan.isSelected(),toggleDiary.isSelected()};
		
		displayed.setCharacteristics(chara);
		
		
		
		System.out.println("[✓] Salvati nuovi valori di "+ displayed);
		txtStatus.setText("Salvataggio effettuato!");
	}
	

}
