/**
 * PaymentServices class - encapsulates PayPal payment integration functions.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package com.rental.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaymentServices {
	
	/**
	 * 
	 * ID and SECRET keys are must to get connected to the merchant account.
	 * These belongs to a carrental app created for development purpose in sandbox.paypal developer account.
	 */
	private static final String CLIENT_ID = "ATBKWpU3h8PP7gOPEUrKAAH2BJ8eNqi9tJzlahDvFm4VlvUXi9dDMSMYllTD3YbUCXys3D1-bdfjiPA8";
	private static final String CLIENT_SECRET = "EDK-Ue1YcviebtPujjpVQZ76u34yPwSK_RePwTA26LrUoo6mbLhZte9vOhHkH7QDvPb6BBaGtJs0oU5p";

	private static final String MODE = "sandbox";
	

	public String authorizePayment(OrderDetail orderDetail)			
			throws PayPalRESTException {		

		Payer payer = getPayerInformation();
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(orderDetail);
		
		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction);
		requestPayment.setRedirectUrls(redirectUrls);
		requestPayment.setPayer(payer);
		requestPayment.setIntent("authorize");

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		Payment approvedPayment = requestPayment.create(apiContext);
		

		System.out.println("------------------ CREATED PAYMENT:----------------------");
		System.out.println(approvedPayment);

		return getApprovalLink(approvedPayment);

	}
	
	private Payer getPayerInformation() {
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		PayerInfo payerInfo = new PayerInfo();
		
		/**
		 * Feeding Static Data for testing purpose.
		 */
		payerInfo.setFirstName("Lakshman")
				 .setLastName("Revella")
				 .setEmail("lakshman.revella@gmail.com");
		
		payer.setPayerInfo(payerInfo);
		
		return payer;
	}
	
	private RedirectUrls getRedirectURLs() {
		
		/**
		 * These urls need to be modified if the project name changes.
		 */
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/UNOCarsRental/cancel.jsp");
		redirectUrls.setReturnUrl("http://localhost:8080/UNOCarsRental/review_payment");
		
		return redirectUrls;
	}
	
	private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
		Details details = new Details();
		
		String subtotal=String.valueOf(orderDetail.subtotal());
		String total = String.valueOf(orderDetail.total());
		String tax = String.valueOf( orderDetail.tax());
		details.setSubtotal(subtotal);
		details.setTax(tax);
		

		Amount amount = new Amount();
		amount.setCurrency("GBP");
		amount.setTotal(total);
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(orderDetail.carType());
		
		ItemList itemList = new ItemList();
		List<Item> items = new ArrayList<>();
		
		Item item = new Item();
		item.setCurrency("GBP");
		item.setName(orderDetail.carType());
		item.setPrice(subtotal);
		item.setTax(tax);
		item.setQuantity("1");
		
		items.add(item);
		itemList.setItems(items);
		transaction.setItemList(itemList);

		List<Transaction> listTransaction = new ArrayList<>();
		listTransaction.add(transaction);	
		
		
		return listTransaction;
	}
	
	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;
		
		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				approvalLink = link.getHref();
				break;
			}
		}		
		
		return approvalLink;
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		return payment.execute(apiContext, paymentExecution);
	}
	
	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		
		return Payment.get(apiContext, paymentId);
	}
}
