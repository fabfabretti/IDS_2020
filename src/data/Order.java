package data;

import java.time.LocalDate;
import java.util.Date;

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
	//Identificativo dell'ordine
	int orderid;
	//Consegna prevista
	LocalDate date;
	OrderDeliveryTime time;
	OrderDeliveryState state= OrderDeliveryState.CONFERMATA;
	Cart cart;
	User user=(User) Globals.currentUser;
	//Il costo totale e i punti sono nel carrello!
	Payment payment;
	String paymentInfo;
	
	public Order() {
		
	}
	
	public Order(Cart cart, Payment payment, String paymentInfo) {
		Globals.storico.add(this);
		System.out.println("[✓] Ordine generato");
		this.cart=cart.copyCart();
		this.payment=payment;
		this.paymentInfo=paymentInfo;
	}
	
	public void confirmOrder(LocalDate date, OrderDeliveryTime time) {
		this.date=date;
		this.time=time;
		
		orderid= Globals.storico.size()+1;
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
	 * @return the time
	 */
	public OrderDeliveryTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
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
	 * @return the cart
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
}
