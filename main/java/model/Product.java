package model;

public abstract class Product {

    private int id_Product;
    private int SKU;
    private String brand;
    private String model;
    private String description;
    private double price;


    public Product() {
    }

    public Product(int id_Product, int SKU, String brand, String model, String description, double price) {
        this.id_Product = id_Product;
        this.SKU = SKU;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
    }

    public Product(int SKU, String brand, String model, String description, double price) {
        this.SKU = SKU;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.price = price;
    }

    public int getId_Product() {
        return id_Product;
    }

    public int getSKU() {
        return SKU;
    }

    public void setSKU(int SKU) {
        this.SKU = SKU;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Producto {" +
                "SKU=" + SKU +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", id_Product=" + id_Product +
                '}';
    }

}
