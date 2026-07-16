package com.ecom.product;

import java.time.LocalDate;

public class Product {

	    private int productId;
	    private String productName;
	    private String productDescription;
	    private String category;
	    private String brand;
	    private double price;
	    private int quantity;
	    private LocalDate createdDate;
	    private String createdBy;
	    private LocalDate modifiedDate;
	    private String modifiedBy;
	    
	    // Getters and Setters
		
	    public int getProductId() {
			return productId;
		}
		public void setProductId(int productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProductDescription() {
			return productDescription;
		}
		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public LocalDate getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(LocalDate createdDate) {
			this.createdDate = createdDate;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public LocalDate getModifiedDate() {
			return modifiedDate;
		}
		public void setModifiedDate(LocalDate modifiedDate) {
			this.modifiedDate = modifiedDate;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

}
