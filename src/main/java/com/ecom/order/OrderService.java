package com.ecom.order;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.ecom.app.AppSession;
import com.ecom.cart.Cart;
import com.ecom.cart.CartRepo;

public class OrderService {

	Scanner sc = new Scanner(System.in);

	OrderRepo orderRepo = new OrderRepo();

	CartRepo cartRepo = new CartRepo();

	AppSession appSession = new AppSession();

	public void placeOrder()//1
	        throws FailedToCreateOrderException, InsufficientStockException {

	    try {

	        String customerId = appSession.getLoggedInUser().getUserId();

	        List<Cart> cartList = cartRepo.getCartItems(customerId);

	        if (cartList.isEmpty()) {
	            System.out.println("Your cart is empty.");
	            return;
	        }

	        double totalAmount = 0;

	        for (Cart cart : cartList) {

	            orderRepo.verifyStock(cart.getProductId(), cart.getQuantity());

	            totalAmount += cart.getTotalPrice();
	        }

	        Order order = new Order();

	        order.setUserId(customerId);
	        order.setTotalAmount(totalAmount);
	        order.setOrderStatus(OrderStatus.PLACED);
	        order.setCreatedDate(LocalDate.now());
	        order.setCreatedBy(appSession.getLoggedInUser().getUserName());
	        order.setModifiedDate(LocalDate.now());
	        order.setModifiedBy(appSession.getLoggedInUser().getUserName());

	        int orderId = orderRepo.saveOrder(order);

	        for (Cart cart : cartList) {

	            OrderItem item = new OrderItem();

	            item.setOrderId(orderId);
	            item.setProductId(cart.getProductId());
	            item.setQuantity(cart.getQuantity());
	            item.setPrice(cart.getProductPrice());

	            orderRepo.saveOrderItem(item);

	            orderRepo.reduceStock(cart.getProductId(), cart.getQuantity());
	        }

	        orderRepo.clearCart(customerId);

	        System.out.println("Order Placed Successfully.");

	    } catch (Exception e) {

	        throw new FailedToCreateOrderException("Unable to place order. "+ e.getMessage());

	    }
	}

	public void showMyOrders()//2
	        throws FailedToGetOrderException {

	    try {

	        String customerId = appSession.getLoggedInUser().getUserId();

	        orderRepo.showMyOrders(customerId);

	    } catch (Exception e) {

	        throw new FailedToGetOrderException(
	                "Unable to fetch orders.", e);

	    }
	}
	public void cancelOrder()//3
	        throws FailedToCreateOrderException, OrderNotFoundException {

	    try {

	        System.out.print("Enter Order Id : ");
	        int orderId = Integer.parseInt(sc.nextLine());

	        boolean isCancelled = orderRepo.cancelOrder(orderId);

	        if (isCancelled) {
	            System.out.println("Order Cancelled Successfully.");
	        }

	    } catch (FailedToCreateOrderException e) {

	        throw e;

	    } catch (Exception e) {

	        throw new OrderNotFoundException("Invalid Order Id.", e);

	    }
	}
	
}