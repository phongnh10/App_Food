package model;

public class Shop {

    private int idShop;
    private int idUser;
    private String name;
    private String address;

    public Shop() {
    }

    public Shop(int idShop, int idUser, String name, String address) {
        this.idShop = idShop;
        this.idUser = idUser;
        this.name = name;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}