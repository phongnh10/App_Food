package model;

public class OrderDetails {
    private int idOrderDetails,idShop,idProduct,idOrder,quantity;
    private double price,totalPrice;
    private byte[] image;
    private String name;

    public OrderDetails() {
    }

    public OrderDetails(int idOrderDetails, int idShop, int idProduct, int idOrder, int quantity, double price, double totalPrice, byte[] image, String name) {
        this.idOrderDetails = idOrderDetails;
        this.idShop = idShop;
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.image = image;
        this.name = name;
    }

    public int getIdOrderDetails() {
        return idOrderDetails;
    }

    public void setIdOrderDetails(int idOrderDetails) {
        this.idOrderDetails = idOrderDetails;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
