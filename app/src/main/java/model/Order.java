package model;

import java.util.Date;

public class Order {
    private int idOrder,idShop,idUser,status,quantity;
    private double totalPrice;
    private String date;

    public Order() {
    }

    public Order(int idOrder, int idShop, int idUser, int status, int quantity, double totalPrice, String date) {
        this.idOrder = idOrder;
        this.idShop = idShop;
        this.idUser = idUser;
        this.status = status;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

