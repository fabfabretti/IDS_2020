package application;

import java.io.IOException;
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

	//Insieme dei prodotti attualmente mostrabili (es: se siamo in un reparto sono tutti quelli del reparto).
	//Può differire da actuallydisplayed, poiché se applico dei filtri quelli che non li rispettano verranno nascosti.
	private TreeSet<Product> displayable = new TreeSet<Product>(currComparator);
	
	//Insieme dei prodotti attualmente mostrati
	private TreeSet<Product> actuallyDisplayed = new TreeSet<Product>(currComparator);
	
	//Barra per filtrare i prodotti
	private AnchorPane filterBar = null;
	

	//Pannello al quale voglio agganciare lo scrollpane
	private AnchorPane parent = new AnchorPane();
	
	//Modalità di visualizzazione (cart, user...)
	private String type;
	
	
	
	/**
	 * Costruisce un ProductViewer attaccando già lo scrollpane al parent indicato e impostando la visualizzazione in modalità  SECTION
	 * 
	 * @param parent pannello su cui attaccare lo scrollpane con tuttecose
	 * @param result elenco prodotti da mostrare
	 * @param type il tipo di visualizzazione (cart, user...)
	 */
	public ProductViewer(AnchorPane parent, TreeSet<Product> result, String type) {
		
		//Settiamo questa come visualizzazione attuale
		Globals.currentView=this;
		
		//Settiamo result come prodotti visibili
		this.displayable.addAll(result);
		
		//Non abbiamo filtri attivi, dunque tutti i prodotti visibili sono effettivamente mostrati a schermo
		this.actuallyDisplayed.addAll(result);
		
		//Settiamo il parent di questo pannello
		this.setParent(parent);
		
		//Settiamo la modalità
		this.type=type;

		//Avvio la procedura di costruzione di un pannello
		formPanel(displayable,type);
		
		//Al solito vogliamo "scorrere" i prodotti, dunque creiamo lo scroller
		ScrollPane newpane = scroller;
		
		//Aggiungiamo il risultato al parent
		parent.getChildren().add(newpane);
		
		//robba necessaria allo scroller
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
		
		//Settiamo questa come visualizzazione attuale
		Globals.currentView=this;
		
		//Settiamo result come prodotti visibili
		
		TreeSet<Product> codesToProducts = new TreeSet<Product>();
		
		for ( Integer i : cart.getProducts().keySet())
			codesToProducts.add(Globals.barCodeTable.get(i));
		
		this.displayable.addAll(codesToProducts);
		
		//Non abbiamo filtri attivi, dunque tutti i prodotti visibili sono effettivamente mostrati a schermo
		this.actuallyDisplayed.addAll(codesToProducts);
		
		//Settiamo il parent di questo pannello
		this.setParent(parent);
		
		//Settiamo la modalità
		formPanel(displayable,"cart");
		
		//Al solito vogliamo "scorrere" i prodotti, dunque creiamo lo scroller
		ScrollPane newpane = scroller;
		
		//Aggiungiamo il risultato al parent
		parent.getChildren().add(newpane);
		
		//robba necessaria allo scroller
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
		
		// PARTE A. Genero la barra x filtrare se necessaria
		//(= se la modalità lo richiede e se NON è già stata generata)
		
		ScrollPane scroller = new ScrollPane();
		FlowPane flowProdotti = new FlowPane();
		
		//A0) formulo la barra

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
			flowProdotti.getChildren().add(filterBar);
		}
		
		
		//PARTE B. Inserisco i prodotti.
		
		//B0) se non ho prodotti, carico il pannello vuoto
		
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

		// B1) Se ci sono, genero tutti i prodotti: per ogni prodotto genero il pannellino
		
		for (Product p : result) {
			ProductPaneController.setInitProduct(p); //serve a dire al controller che prodotto sta controllando!!
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
			scroller.setFitToWidth(true);
			
			
		}

		scroller.setContent(flowProdotti);
		this.scroller=scroller;
	}
	
	/***
	 * Cambia l'ordine di visualizzazione.
	 * @param mode il nuovo ordine.
	 */
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
		
	    //STEP 2: METTO TUTTI I PRODUCT IN UN ALTRO TREESET
	    //		  Il quale avrà il nuovo comparator :)

	   TreeSet<Product> dispOrdered = new TreeSet<>(currComparator);

	   dispOrdered.addAll(actuallyDisplayed);	   

	   //rigenero la schermata! STessa solfa di prima
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
	
	/**
	 * Filtra i prodotti "visualizzabili", e mostra il risultato (che viene salvato in actuallydisplayed).
	 * @param selectedCharas
	 */
	public void filterChara(boolean selectedCharas[], String brand) {
		
		TreeSet<Product> newset = new TreeSet<Product>(currComparator);
		boolean condition;
		for(Product p : displayable) {
			
			//rispetta vegano?
			condition= !selectedCharas[0] ||(selectedCharas[0] && p.isChar("vegan") );
			
			//rispetta diary?
			condition&=(( !selectedCharas[1] || selectedCharas[1] && p.isChar("diary") ));
			
			//rispetta bio?
			condition&=((!selectedCharas[2] || selectedCharas[2] && p.isChar("bio") ));
			
			//rispetta gluten?
			condition&=((!selectedCharas[3] || selectedCharas[3] && p.isChar("gluten") ));
			
			//Il brand va bene?
			condition&=brand.equals("Tutti") || (!brand.equals("Tutti") && p.getBrand().equals(brand));
			
			//Se le rispetta tutte, allora posso mostrare il prodotto.
			if(condition == true) {
				newset.add(p);
			}
		
		}
		actuallyDisplayed=newset;
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
	

	/**
	 * 
	 * @return the parent
	 */
	public AnchorPane getParent() {
		return parent;
	}
	
	/**
	 * 
	 * @param parent the parent to set
	 */
	public void setParent(AnchorPane parent) {
		this.parent = parent;
	}

	/**
	 * @return the displayed
	 */
	public TreeSet<Product> getDisplayed() {
		return displayable;
	}

	/**
	 * @param displayed the displayed to set
	 */
	public void setDisplayed(TreeSet<Product> displayed) {
		this.displayable = displayed;
	}
	
	/**
	 * @return the scroller
	 */
	public ScrollPane getScroller() {
		return scroller;
	}

}




