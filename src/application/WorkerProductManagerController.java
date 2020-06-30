package application;

import java.util.TreeSet;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import data.Product;
import data.Section;
import data.Units;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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
	private JFXTextField fieldPrice;
	@FXML
	private Text  txtCode;
	@FXML
	private Text  txtWeightPrice;
	@FXML
	private ImageView  imgImage;
	@FXML
	private JFXTextField  fieldImagePath;
	
	@FXML
	private ChoiceBox<String> chboxSection;
	@FXML
	private ChoiceBox<String> chboxUnit;
	

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

		//Inizializzo choicebox con i reparti
		for(Section s : Globals.reparti) {
	        chboxSection.getItems().add(s.getName());
	        }
		

		//Nascondiamo la scritta di errore
		txtStatus.setText("");
		mainEditor.setVisible(false);
		defaultPane.setVisible(true);
		
		

		initializeView();
		

	}
	
	
	private void initializeView() {
		//carichiamo TUTTI i prodotti!
		
		Globals.editController=this;
		
		TreeSet<Product> allproducts = new TreeSet<Product>();
		
		for(Section s : Globals.reparti) {
			System.out.println("checking "+ s.getName());
			for(Product p : s.getProducts()) {
				System.out.println("		checking "+ p.getName());
				allproducts.add(p);
			}
			
		}
		
		
		ProductViewer viewer = new ProductViewer(viewScrollPane,allproducts,"edit");
		
	}


	public void editInfo(Product p){
//Nascondiamo la scritta di errore
		txtStatus.setText("");
		mainEditor.setVisible(false);
		defaultPane.setVisible(true);
		//Inizializzo choicebox con i reparti
		for(Section s : Globals.reparti) {
			if(!chboxSection.getItems().contains(s.getName()))
				chboxSection.getItems().add(s.getName());
        }
		
		for(Units u : data.Units.values()) {
			if(!chboxUnit.getItems().contains(u.toString()))
				chboxUnit.getItems().add(u.toString());
        }
		
		displayed=p;
		defaultPane.setVisible(false);
		mainEditor.setVisible(true);
		
		fieldName.setText(p.getName());
		fieldBrand.setText(p.getBrand());
		fieldWeight.setText(p.getWeight()+"");
		txtWeightPrice.setText(p.getWeightPrice());
		fieldPrice.setText(p.getPrice()+"");
		if(p.getImagePath()!="")
			fieldImagePath.setText(p.getImagePath().substring(12));
		else
			fieldImagePath.setText(p.getImagePath());
		txtCode.setText("Codice Prodotto:" + p.getBarCode());


		if(displayed.getSection()!=null)
			chboxSection.setValue(displayed.getSection().getName());
		chboxUnit.setValue(displayed.getUnit());
		
		
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
		if(fieldName.getText().equals("") || fieldBrand.getText().equals("") || fieldWeight.getText().equals("") ||fieldPrice.getText().equals("") || chboxSection.getValue()==null ) {
			txtStatus.setStyle("-fx-text-fill: red;");
			txtStatus.setVisible(true);
			txtStatus.setText("Errore:\nE' necessario compilare tutti i campi!");
			return;
		}			
		
		if(spinnerQuantity.getValue() < 0) {
			txtStatus.setStyle("-fx-text-fill: red;");
			txtStatus.setVisible(true);
			txtStatus.setText("Errore:\nLa quantità disponibile deve essere un numero non negativo.");
			return;
		}
		

		
	
		try {
			if(Float.parseFloat(fieldWeight.getText())<= 0) 
				txtStatus.setText("Errore: Il peso deve essere maggiore di zero.");
			if(Float.parseFloat(fieldPrice.getText()) <= 0 ) 
				txtStatus.setText("Errore: il prezzo deve essere maggiore di zero.");
			if(Float.parseFloat(fieldPrice.getText()) == Float.NaN ||Float.parseFloat(fieldWeight.getText()) == Float.NaN) 
				txtStatus.setText("Errore: un valore non è valido.");
		
		}catch(Exception ee) {
			txtStatus.setVisible(true);
			return;
		}
		
		

		

		
		
		//salvataggio
		
		
		//Controllo se è un nuovo prodotto!
		boolean isPresent=false;
		for(Section s : Globals.reparti) {
			if (s.getProducts().contains(displayed)==true)
				isPresent=true;	
		}
		
		if(isPresent==false)
			Globals.reparti[0].addProduct(displayed);
			
		
		//Ora posso salvare tutto		
		displayed.setName(fieldName.getText());
		
		displayed.setBrand(fieldBrand.getText());
		
		displayed.setWeight(Float.parseFloat(fieldWeight.getText()));

		displayed.setPrice(Float.parseFloat(fieldPrice.getText()));

		displayed.setImagePath("file:images/"+fieldImagePath.getText());
		

		displayed.setAvailable(spinnerQuantity.getValue());
		
		
		
		//Choiceboxes:
		
			//level 1: easy! Cambio di unità
			displayed.setUnit(chboxUnit.getValue());
			
			//level2: difficult! Cambio di sezione
			//se la sezione è cambiata...
				Section newsection = null;
			if(displayed.getSection()==null || !displayed.getSection().getName().equals(chboxSection.getValue())) {
				//rimuovo dalla vecchia sezione se necessario (ovvero se section non era null)
				if(displayed.getSection()!=null)
					displayed.getSection().getProducts().remove(displayed);
				//cerchiamo la nuova section usandone il nome
				for(int i=0;i < 5; i++) {
					if (Globals.reparti[i].getName().equals(chboxSection.getValue())) {
						newsection=Globals.reparti[i];
						//Se il prodotto è nuovo, setto il barcode
						displayed.setBarCode((i+1)*1000000+ 1 + Globals.reparti[i].getProducts().size());
						txtCode.setText("Codice Prodotto: "+ displayed.getBarCode());
						}
				}
				//aggiungiamolo :)
				newsection.addProduct(displayed);
				displayed.setSection(newsection);
				System.out.println("[✓] E' avvenuto un cambio di reparto");
			}
		
		
		boolean chara[] = {toggleBio.isSelected(),toggleGluten.isSelected(),toggleVegan.isSelected(),toggleDiary.isSelected()};
		
		displayed.setCharacteristics(chara);
		
		
		
		System.out.println("[✓] Salvati nuovi valori di "+ displayed);
		txtStatus.setStyle("-fx-text-fill: black;");
		txtStatus.setText("Salvataggio effettuato!");
		

		//Aggiorno la stima del prezzo
		txtWeightPrice.setText(displayed.getWeightPrice());
		txtWeightPrice.setText(displayed.getWeightPrice());
		

		initializeView();
	}
	
	public void refresh(ActionEvent ae) {

		Image image;
		try {
				image = new Image("file:images/" + fieldImagePath.getText());  
		}catch(Exception e) {

			image = new Image("file:images/Product_placeholder.png");   
		}
		imgImage.setImage(image);
		

		//Aggiorno la stima del prezzo
		txtWeightPrice.setText(displayed.getWeightPrice());
		txtWeightPrice.setText(displayed.getWeightPrice());

	}

	public void newProduct(ActionEvent ae) {
		Product dummy = new Product();
		editInfo(dummy);
	}
	
	
	
}





	