package data;

import java.util.Date;

/*
 * Per ogni spesa si memorizzano il codice (univoco), la data prevista per la consegna, insieme
all’intervallo di tempo in cui la spesa potrà essere consegnata, i prodotti che la compongono e in
quale quantità con prezzo unitario e totale di ogni prodotto, l’utente che l’ha effettuata, il costo
totale, il tipo di pagamento (carta di credito, paypal o alla consegna) e il saldo punti. Ad ogni spesa
vengono accreditati sulla tessera fedeltà un numero di punti pari agli euro spesi nella spesa
considerata.
 * 
 * */
public class Order {
	int orderid;
	Date deliveryDate;
	Cart carrello;
	User user;
	//Il costo totale e i punti sono nel carrello
	
	//Pagamento payment;
	

}
