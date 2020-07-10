package data;

import java.time.LocalDate;
import java.util.HashMap;

import application.Globals;

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

	// Identificativo dell'ordine
	private int orderid;

	// Data prevista consegna
	private LocalDate date;

	// Orario previsto consegna
	private OrderDeliveryTime time;

	// Stato consegna
	private OrderDeliveryState state = OrderDeliveryState.CONFERMATA;

	// Contiene il cart; la parte rilevante è l'elenco dei prodotti (Now comes in
	// barcodes!! :D)
	private Cart cart;

	// Me serve per tenere in memoria il costo al momento dell'acquisto.
	private HashMap<Integer, Float> prices = new HashMap<Integer, Float>();

	// User che ha effettuato l'ordine
	private User user = (User) Globals.currentUser;

	// Metodo di pagamento
	private Payment payment;

	// Ulteriori informazioni (es. carta di credito, paypal...)
	private String paymentInfo;

	// Info consegna
	private String address;

	/**
	 * Crea un ordine.
	 * 
	 * @param cart        indica il carrello
	 * @param payment     indica tipo di pagamento
	 * @param paymentInfo info sul tipo di pagamento (es.
	 *                    paypal->credenziali,/carta->codice...)
	 * @param address     indica l'indirizzo utile alla consegna della spesa
	 */
	public Order(Cart cart, Payment payment, String paymentInfo, String address) {

		// Inseriamo questo ordine nella lista di ordini esistenti.
		//??Globals.storico.add(this);

		// Inseriamo i dati dell'ordine...
		this.cart = cart.copyCart();
		this.payment = payment;
		this.paymentInfo = paymentInfo;
		this.address = address;
		orderid = Globals.storico.size();

		// Inseriamo i dati sul prezzo!
		for (Integer i : cart.getProducts().keySet()) {
			float currentPrice = Globals.barCodeTable.get(i).getPrice();
			prices.put(i, currentPrice);
		}

		// debug
		System.out.println("[✓] Ordine generato");
	}

	/**
	 * Aggiunge data e ora per il nuovo ordine.
	 * 
	 * @param date la data inserita
	 * @param time la fascia oraria scelta
	 */
	public void confirmOrder(LocalDate date, OrderDeliveryTime time) {
		this.date = date;
		this.time = time;
	}

	/**
	 * @return the orderid
	 */
	public int getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Ritorna la fascia oraria scelta dall'utente.
	 */
	public OrderDeliveryTime getTime() {
		return time;
	}

	/**
	 * imposta la fascia oraria scelta dall'utente.
	 */
	public void setTime(OrderDeliveryTime time) {
		this.time = time;
	}

	/**
	 * @return the state
	 */
	public OrderDeliveryState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(OrderDeliveryState state) {
		this.state = state;
	}

	/**
	 * Ritorna il carrello dell'ordine.
	 */
	public Cart getCart() {
		return cart;
	}

	/**
	 * @param cart the cart to set
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	/**
	 * @return the paymentInfo
	 */
	public String getPaymentInfo() {
		return paymentInfo;
	}

	/**
	 * @param paymentInfo the paymentInfo to set
	 */
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the prices
	 */
	public HashMap<Integer, Float> getPrices() {
		return prices;
	}

	/**
	 * @param prices the prices to set
	 */
	public void setPrices(HashMap<Integer, Float> prices) {
		this.prices = prices;
	}

}
