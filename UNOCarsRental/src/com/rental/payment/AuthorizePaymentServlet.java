/**
 * AuthorizePaymentServlet class - requests PayPal for payment.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package com.rental.payment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.paypal.base.rest.PayPalRESTException;

/**
 * 
 * When you checkout from the UserInterface, this is the first servlet that will be invoked.
 * 
 */
//@WebServlet("/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthorizePaymentServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * Assumption : FrondendUser sends all the below parameters correctly.
		 */
		String carType = request.getParameter("carType");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String subtotal = request.getParameter("subtotal");
		String tax = request.getParameter("tax");
		String total = request.getParameter("total");

		//		System.out.println(subtotal);
		//		System.out.println(total);
		//		System.out.println(tax);


		OrderDetail orderDetail = new OrderDetail(carType,startDate,endDate, Float.parseFloat(subtotal), Float.parseFloat(tax), Float.parseFloat(total));

		try 
		{
			/*
			 * PayPal Payment Service initialization.
			 */
			PaymentServices paymentServices = new PaymentServices();
			
			/*
			 * This will prompt us to login to Paypal account and
			 *  wait for our approval to proceed with payment. 
			 */
			String approvalLink = paymentServices.authorizePayment(orderDetail);

			response.sendRedirect(approvalLink);

		} catch (PayPalRESTException ex) {
			request.setAttribute("errorMessage", ex.getMessage());
			ex.printStackTrace();
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
