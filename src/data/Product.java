package data;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Product {
	
	//
	//
	// VARIABILI E FLAG DEI PRODOTTI
	//
	//
	
	
	/*
	 * Nome del prodotto
	 */
	private String name;
	
	
	/*
	 * Codice univoco relativo ad ogni prodotto
	 */
	private int barCode;
	
	/*
	 * Immagine relativa ad ogni prodotto
	 */
	private String imagePath = "";

	
	/*
	 * Nome della marca
	 */
	private String brand;
	
	/*
	 * Quantità contenuta nella confezione
	 */
	private String weight;
	
	/*
	 * Prezzo del prodotto
	 */
	private float price;	
	
	/*
	 * Prezzo del prodotto
	 */
	private String weightPrice;
	
	// hashlist dei tag
	private int available;

	
	/*
	 * Array che indica quali caratteristiche possiede il prodotto
	 * c[0] = biologico 
	 * c[1] = celiachi
	 * c[2] = vegano 
	 * c[3] = no lattosio
	 */
	private boolean[] characteristics = new boolean[] { false, false, false, false };

	
	
		//
		//
		//   COSTRUTTORE
		//
		//
	
		public Product(String name, int barCode, String imagePath, String brand, String weight, float price, String weightPrice, int available, boolean bio, boolean glutenfree, boolean vegan, boolean lactosefree) {
			this.name=name;
			this.barCode=barCode;
			this.imagePath=imagePath;
			this.brand=brand;
			this.weight=weight;
			this.price=price;
			this.weightPrice=weightPrice;
			this.available=available;
			
			
			characteristics[0]=bio;	
			characteristics[1]=glutenfree;	
			characteristics[2]=vegan;	
			characteristics[3]=lactosefree;

			Sections.addVeg(this);
		}
		
		public Product() {
		};
		

	
	
	
	
	//
	//
	// METODI JAVA FX
	//
	//
	
	
	/**
	 * Costruisce un pannello standard con i dati di questo prodotto
	 * 
	 * @return anchorpane
	 */
	public AnchorPane buildPane() {
		AnchorPane res = null;
		try {
			res = FXMLLoader.load(getClass().getResource("/application/ProductPane1.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Costruisce un pannello compresso con i dati di questo prodotto
	 * 
	 * @return anchorpane
	 */
	public AnchorPane buildCompactPane() {
		AnchorPane res = null;
		try {
			res = FXMLLoader.load(getClass().getResource("/application/ProductPane2.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	
	//
	// DEBUG METHODS
	//
	
	public String toStringVerbose() {
		String res="-----prodotto: " + name
				+ "\n code: " + barCode 
				+ "\t img: " + imagePath
				+ "\t brand: " + brand
				+ "\t weight: " + weight
				+ "\t price: " + price
				+ "\t priceWeight: " + weightPrice
				+ "\t available: " + available
				+ "\nchar:"+ characteristics[0] +
							characteristics[1]+	
							characteristics[2]+	
							characteristics[3]
				+"\n";
		return res;
	}
	
	public String toString() {
		String res=" ["+ name
				+ "|| code: " + barCode 
				+ "|| brand: " + brand
				+ "|| weight: " + weight
				+ "|| price: " + price
				+ "|| available: " + available + " ]";
		return res;
	}
	
	

	//
	//
	//  GET&SET
	//
	//



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the barCode
	 */
	public int getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(int barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the weightPrice
	 */
	public String getWeightPrice() {
		return weightPrice;
	}

	/**
	 * @param weightPrice the weightPrice to set
	 */
	public void setWeightPrice(String weightPrice) {
		this.weightPrice = weightPrice;
	}

	/**
	 * @return the available
	 */
	public int getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(int available) {
		this.available = available;
	}

	/**
	 * @return the characteristics
	 */
	public boolean[] getCharacteristics() {
		return characteristics;
	}

	/**
	 * @param characteristics the characteristics to set
	 */
	public void setCharacteristics(boolean[] characteristics) {
		this.characteristics = characteristics;
	}
	
}
