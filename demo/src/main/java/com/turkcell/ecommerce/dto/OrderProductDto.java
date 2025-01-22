package com.turkcell.ecommerce.dto;

public class OrderProductDto {

    //Hangi üründen kaç tane alındıgını tanımla.

        private int orderId;
        private String productName;
        private int quantity;

        // Constructor
        public OrderProductDto(int orderId, String productName, int quantity) {
            this.orderId = orderId;
            this.productName = productName;
            this.quantity = quantity;
        }

        // Getters and Setters
        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        // toString method for better readability
        @Override
        public String toString() {
            return "OrderProductDTO{" +
                    "orderId=" + orderId +
                    ", productName='" + productName + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }


