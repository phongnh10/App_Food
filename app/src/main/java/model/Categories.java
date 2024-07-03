package model;

public class Categories {
    private int idCategories;
    private int idShop;
    private String nameCategories;
    private int imageId;

    public Categories() {
    }

    public Categories(int idCategories, int idShop, String nameCategories, int imageId) {
        this.idCategories = idCategories;
        this.idShop = idShop;
        this.nameCategories = nameCategories;
        this.imageId = imageId;
    }

    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public String getNameCategories() {
        return nameCategories;
    }

    public void setNameCategories(String nameCategories) {
        this.nameCategories = nameCategories;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
