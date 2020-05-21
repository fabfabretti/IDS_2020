package application;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import data.Product;
import json.Json;
import json.JsonArray;
import json.JsonValue;

public class JsonLoader {

	static void  loadProduct() {



		//(try/catch è necessario causa operazione input output)

		//		 uso try/catch *with resource* (cioè con il reader dentro la parentesi)
		//		 perché è più bello (cit. Spoto)

		try(Reader reader= new FileReader("./data/product.json")){

			JsonArray fileFruttaVerdura = Json.parse(reader).asObject().get("prodotti").asArray();

			for(JsonValue prodotto : fileFruttaVerdura) {
				//System.out.println(prodotto); 	
				/* 	[! ! ! ! ! ]
				  		|----->	Da questa print di debug si evince che  
				  				ciascun JsonValue (ovvero ciascun elemento di ciascun
				  				 indice del JsonArray)è una schifosissima STRINGA
				  				con dentro tutta la roba del nostro oggetto (assolutamente non parsata,
				  				 ripeto: è una schifosissima stringa)
				  
				  				Get asObject lo rende una cosa papabile 
				  				(perché la schifosissima stringa non gli piace)
				  				su cui si riesce a parse-are la roba
				  				
				  				Le funzioni getString/getInt/etc... traducono il Json Object 
				  				nei suoi valori e sono sostanzialmente 
				  				come una hashmap ("dammi la key e io ti do la value")
				  				
				  				Il primo valore che si passa è la key, mentre il secondo è il
				  				valore di controllo (Aka se ci ritorna quella roba lì significa
				  				che non ha trovato l'elemento
				 */
				
				
				
				
				String name = prodotto.asObject().getString("name", "Unknown prodotto");
				
				int barCode = prodotto.asObject().getInt("barCode", -1);

				String imagePath = prodotto.asObject().getString("imagePath", "Unknown image");

				String brand = prodotto.asObject().getString("brand", "Unknown image");
				
				String weight = prodotto.asObject().getString("weight", "Unknown weight");

				float price = prodotto.asObject().getFloat("price", (float) -1.0);

				String weightPrice = prodotto.asObject().getString("weightPrice", "Unknown w. price");

				int available = prodotto.asObject().getInt("available", -1);

				boolean bio = prodotto.asObject().getBoolean("bio", false);
				boolean glutenfree = prodotto.asObject().getBoolean("glutenfree", false);
				boolean vegan = prodotto.asObject().getBoolean("vegan", false);
				boolean lactosefree = prodotto.asObject().getBoolean("lactosefree", false);
				
			
				
				Product p = new Product(name,barCode,imagePath,brand,weight, price,weightPrice,available,bio,glutenfree,vegan,lactosefree);

				

			}

	

		}catch(IOException e){
			System.out.println("Errore I/O");
			e.printStackTrace();

		}


	}

}



