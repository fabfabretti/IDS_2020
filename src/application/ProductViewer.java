package application;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

import data.Cart;
import data.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


/**
 * Classe che gestisce la visualizzazione di un elenco di prodotti.
 *
 */
public class ProductViewer {
	

	private Comparator <Product> currComparator = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            if(Float.compare(p1.getPrice(), p2.getPrice())!=0) {
            	return Float.compare(p1.getPrice(), p2.getPrice());
            	}
            return p1.getBarCode() - p2.getBarCode();
        }
    };
	
	
	// Scroller ove verrà agganciato il flowpane con i prodotti
	private ScrollPane scroller = new ScrollPane();

	//Insieme dei prodotti attualmente mostrati
	private TreeSet<Product> displayed = new TreeSet<Product>(currComparator);
	//Insieme dei prodotti attualmente mostrati
	private TreeSet<Product> actuallydisplayed = new TreeSet<Product>(currComparator);
	
	private AnchorPane filterBar = null;
	

	//Pannello al quale voglio agganciare lo scrollpane
	private AnchorPane parent = new AnchorPane();
	
	private String type;
	
	
	
	/**
	 * Costruisce un ProductViewer attaccando già lo scrollpane al parent indicato e impostando la visualizzazione in modalità  SECTION
	 * 
	 * @param parent pannello su cui attaccare lo scrollpane con tuttecose
	 * @param result elenco prodotti da mostrare
	 */
	public ProductViewer(AnchorPane parent, TreeSet<Product> result, String type) {
		Globals.currentView=this;
		this.displayed.addAll(result);
		this.actuallydisplayed.addAll(result);
		this.setParent(parent);
		this.type=type;

		formPanel(displayed,type);
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}
	
	/**
	 * Costruisce un ProductViewer attaccando già lo scrollpane al parent indicato e impostando la visualizzazione in modalità  CART
	 * 
	 * @param parent pannello su cui attaccare lo scrollpane con tuttecose
	 * @param result elenco prodotti da mostrare
	 */
	public ProductViewer(AnchorPane parent, Cart cart) {
		Globals.currentView=this;
		this.displayed.addAll(cart.getProducts().keySet());
		this.actuallydisplayed.addAll(cart.getProducts().keySet());
		this.setParent(parent);
		System.out.println("Visualizzo in modalità cart!");
		formPanel(displayed,"cart");
		ScrollPane newpane = scroller;
		parent.getChildren().add(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}



	/**
	 * Crea il pannello con tutti i prodotti (presumibilmente, questo pannello andrà in uno scrollpane).
	 * 
	 * @param result elenco dei prodotti da mostrare
	 * @param mode è "cart" o "section"; cambia alcune cose grafiche
	 */
	private void formPanel(TreeSet<Product> result, String mode) {
		
		// 1. Genero la barra x filtrare //TODO ora è un dummy// se necessaria
		
		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();
		
		//0 : formulo la barra

		if (filterBar==null) {
			if (!mode.equals("cart"))
				try {
					AnchorPane bar=null;
					if (mode.equals("section")) {
						bar = FXMLLoader.load(result.getClass().getResource("/application/FilterOrderBar.fxml"));
						}
					if (mode.equals("edit")) {
						bar = FXMLLoader.load(result.getClass().getResource("/application/FilterEditBar.fxml"));
						}
						flowProdotti.getChildren().add(bar);
					filterBar=bar;
				} catch (IOException e) {
					e.printStackTrace();
				} 
		}
		
		else {
			//System.out.println("Keeping previous bar: " + filterBar);
			flowProdotti.getChildren().add(filterBar);
		}
		
		
		//se non ho prodotti, carico il pannello vuoto
		
		if(result==null || (result!=null && result.size()==0) ) {
			System.out.println("[✓] Nessun prodotto da visualizzare! Carico pannello vuoto...");
			Pane res;
				try {
						if(mode.equals("cart"))
							res=FXMLLoader.load( getClass().getResource("/application/CartViewEmpty.fxml") );
						
						else if (mode.equals("section")) 
							res=FXMLLoader.load( getClass().getResource("/application/ProductViewEmpty.fxml") );
						
						else if (mode.equals("edit"))  
							res=FXMLLoader.load( getClass().getResource("/application/EditViewEmpty.fxml") );
						else res=null;
						
					flowProdotti.getChildren().add(res);
					scroller.setContent(flowProdotti);
				} catch (Exception e) {
					System.out.println("[x] Errore caricamento UI interna");
				}
			scroller.setContent(flowProdotti);
			this.scroller=scroller;	
			//System.out.println("[✓] Pannello vuoto isualizzato");
			return;
			}

		//
		// Case 1: Ho prodotti!
		//
		// 2. Genero tutti i prodotti: per ogni prodotto genero il pannellino
		for (Product p : result) {

			ProductPaneController.setInitProduct(p);
			AnchorPane productPane=null;
			
			try {
				
				if(mode.equals("section"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPane.fxml"));
				if(mode.equals("cart"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPaneCompact.fxml"));
				if(mode.equals("edit"))
					productPane = FXMLLoader.load(result.getClass().getResource("/application/ProductPaneEdit.fxml"));
			} catch (Exception e) {
				System.out.println("[x] Fxml non pervenuto :(" + e);
			}
			
			
			flowProdotti.getChildren().add(productPane);
			//System.out.println("[✓] Mostrato: " + p);
			scroller.setFitToWidth(true);
			
			
		}

		scroller.setContent(flowProdotti);
		this.scroller=scroller;
	}

	public ScrollPane getScroller() {
		return scroller;
	}
	
	public void changeOrder(String mode) {

		// STEP 1: DEFINISCO IL NUOVO COMPARATOR
		
		if(mode.equals("Prezzo crescente")) 
			currComparator = new Comparator<Product>() {
		        @Override
		        public int compare(Product p1, Product p2) {
		            if(Float.compare(p1.getPrice(), p2.getPrice())!=0) {
		            	return Float.compare(p1.getPrice(), p2.getPrice());
		            	}
		            return p1.getBarCode() - p2.getBarCode();
		        }
		    };
		
		
		if(mode.equals("Prezzo decrescente")) 
			currComparator = new Comparator<Product>() {
		        @Override
		        public int compare(Product p1, Product p2) {
		            if(Float.compare(p1.getPrice(), p2.getPrice())!=0) {
		            	return -1*Float.compare(p1.getPrice(), p2.getPrice());
		            	}
		            return p1.getBarCode() - p2.getBarCode();
		        }
		    };
	
		
		if(mode.equals("Marca")) 
			currComparator = new Comparator<Product>() {
		        @Override
		        public int compare(Product p1, Product p2) {
		            if(p1.getBrand().compareTo(p2.getBrand())!=0) {
		            	return p1.getBrand().compareTo(p2.getBrand());
		            	}
		            return p1.getBarCode() - p2.getBarCode();
		        }
		    };
		
	
	   TreeSet<Product> dispOrdered = new TreeSet<>(currComparator);
	
	   dispOrdered.addAll(actuallydisplayed);
	   

	   formPanel(dispOrdered,type);
		ScrollPane newpane = scroller;
		parent.getChildren().clear();
		parent.getChildren().addAll(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
		
		System.out.println("[✓] Sorted in mode \"" + mode + "\"");
	}
	
	public class ComparatorP implements Comparator<Product> {
		 
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getBrand().compareTo(p2.getBrand());
        }
	}
	
	public void filterChara(boolean selectedCharas[]) {
		
		TreeSet<Product> newset = new TreeSet<Product>(currComparator);
		
		boolean condition;
		for(Product p : displayed) {

			//rispetta vegano?
			condition= !selectedCharas[0] ||(selectedCharas[0] && p.isChar("vegan") );
			//rispetta diary?
			condition=condition&&(( !selectedCharas[1] || selectedCharas[1] && p.isChar("diary") ));
			//rispetta bio?
			condition=condition&&((!selectedCharas[2] || selectedCharas[2] && p.isChar("bio") ));
			//rispetta gluten?
			condition=condition&&((!selectedCharas[3] || selectedCharas[3] && p.isChar("gluten") ));
			
			
			if(condition == true) {
				newset.add(p);
			}
		

		}
		actuallydisplayed=newset;
		System.out.println("[✓] Filtered: " + newset.size() + " products found");
		formPanel(newset,type);
		ScrollPane newpane = scroller;
		parent.getChildren().clear();
		parent.getChildren().addAll(newpane);
		AnchorPane.setTopAnchor(newpane, 0.0);
		AnchorPane.setBottomAnchor(newpane, 0.0);
		AnchorPane.setLeftAnchor(newpane, 0.0);
		AnchorPane.setRightAnchor(newpane, 0.0);
	}

	public AnchorPane getParent() {
		return parent;
	}

	public void setParent(AnchorPane parent) {
		this.parent = parent;
	}

	/**
	 * @return the displayed
	 */
	public TreeSet<Product> getDisplayed() {
		return displayed;
	}

	/**
	 * @param displayed the displayed to set
	 */
	public void setDisplayed(TreeSet<Product> displayed) {
		this.displayed = displayed;
	}
}




