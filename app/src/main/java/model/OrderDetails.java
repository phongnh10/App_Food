package model;

public class OrderDetails {
    private int idOrderDetails,idProduct,idOrder,quantity;
    private double price;
    private byte[] image;
    private String name;

    public OrderDetails() {
    }

    public OrderDetails(int idOrderDetails, int idProduct, int idOrder, int quantity, double price, byte[] image, String name) {
        this.idOrderDetails = idOrderDetails;
        this.idProduct = idProduct;
        this.idOrder = idOrder;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public int getIdOrderDetails() {
        return idOrderDetails;
    }

    public void setIdOrderDetails(int idOrderDetails) {
        this.idOrderDetails = idOrderDetails;
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
