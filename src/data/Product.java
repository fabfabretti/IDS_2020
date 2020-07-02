package data;

import java.io.IOException;

import application.Globals;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class Product implements Comparable<Product>{
	
	//
	//
	// VARIABILI E FLAG DEI PRODOTTI
	//
	//
	
	private String name;

	private int barCode;

	private String imagePath = "";

	private String brand;
	
	private String unit;
	
	private float weight;
	
	private float price;	
	
	private String weightPrice;
	
	private int available;
	
	private Section section;

	
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
	
		public Product(String name, int barCode, String imagePath, String brand, float weight, String unit, float price, int available, boolean bio, boolean glutenfree, boolean vegan, boolean lactosefree, Section section) {
			this.name=name;
			this.barCode=barCode;
			this.imagePath=imagePath;
			this.brand=brand;
			this.weight=weight;
			this.unit=unit;
			this.price=price;
			this.weightPrice= String.format("%.2f €/%s", (float)price / weight, unit);
			this.available=available;
			this.section=section;
			
			characteristics[0]=bio;	
			characteristics[1]=glutenfree;	
			characteristics[2]=vegan;	
			characteristics[3]=lactosefree;

			section.addProduct(this);
		}
		
		public Product() {
			name="";

			imagePath = "";

			brand="";
			
			unit="KG";
			
			weight=0;
			
			price=0;	
			
			weightPrice="";
			
			available=0;
			
			section=null;
			
			barCode=0 + Globals.reparti[0].getProducts().size();

			
			/*
			 * Array che indica quali caratteristiche possiede il prodotto
			 * c[0] = biologico 
			 * c[1] = celiachi
			 * c[2] = vegano 
			 * c[3] = no lattosio
			 */
			characteristics = new boolean[] { false, false, false, false };

			
			
		};
		
		//
		//
		// Utility
		//
		//
	
		public boolean search(String keyword) {			
			if(name.toLowerCase().contains(keyword.toLowerCase()) || brand.toLowerCase().contains(keyword.toLowerCase()))
				return true;
			return false;
			
		}
	
	
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
	public float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(float weight) {
		this.weight = weight;
		this.weightPrice=String.format("%.2f €/%s", (float)price / weight, unit);		
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
		this.weightPrice=String.format("%.2f €/%s", (float)price / weight, unit);
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
	
	/**
	 * @param the section
	 */
	public Section getSection() {
		return section;
	}
	
	/**
	 * @param characteristics the characteristics to set
	 */
	public void setSection(Section section) {
		this.section=section;
	}
	
	
	
	
	

	@Override
	public int compareTo(Product other) {	
		
		
	 return barCode - other.barCode ;
			
	
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public boolean isChar(String chara) {
		
		
		/*
		 * Array che indica quali caratteristiche possiede il prodotto
		 * c[0] = biologico 
		 * c[1] = celiachi
		 * c[2] = vegano 
		 * c[3] = no lattosio
		 */
		if(chara.toLowerCase().equals("bio"))
			return characteristics[0];
		if(chara.toLowerCase().equals("gluten"))
			return characteristics[1];		
		if(chara.toLowerCase().equals("vegan"))
			return characteristics[2];	
		if(chara.toLowerCase().equals("diary"))
			return characteristics[3];
	return false;
	
	}
	
	
}
