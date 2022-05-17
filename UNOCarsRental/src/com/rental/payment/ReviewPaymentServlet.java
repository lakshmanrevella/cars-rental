/**
 * ReviewPaymentServlet class - show review payment page.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package com.rental.payment;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;

//@WebServlet("/review_payment")
public class ReviewPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReviewPaymentServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		try {
			PaymentServices paymentServices = new PaymentServices();
			Payment payment = paymentServices.getPaymentDetails(paymentId);
			
			
			PayerInfo payerInfo = payment.getPayer().getPayerInfo();
			Transaction transaction = payment.getTransactions().get(0);
			System.out.println("Transaction :"+transaction);
			System.out.println("-----------------------------");
			System.out.println("Full Transaction : \n"+payment.getTransactions());
			ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
			
			
			LocalDateTime id = LocalDateTime.now();
			String refId = id.toString().replace("-", "").replace(":", "").replace("T", "").replace(".", "");
			
			System.out.println("refId :"+refId);
			request.setAttribute("refId", refId);
			request.setAttribute("payer", payerInfo);
			request.setAttribute("transaction", transaction);
			
			System.out.println("Shipping Address :"+shippingAddress);
			shippingAddress.setLine1(refId);
			request.setAttribute("shippingAddress", shippingAddress);
			
			String url = "review.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
			
			request.getRequestDispatcher(url).forward(request, response);
			
		} catch (PayPalRESTException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}		
	}

}