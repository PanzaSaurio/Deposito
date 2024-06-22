package model.peripherals;

import model.Product;

public abstract class Peripheral extends Product {

    private int id_Peripheral;
    private String type;
    private String connectionType;
    private boolean isWireless;

    public Peripheral() {
       super();
    }

    public Peripheral(int id_Product, int SKU, String brand, String model, String description, double price,
                      int id_Peripheral,String type, String connectionType, boolean isWireless) {
        super(id_Product, SKU, brand, model, description, price);
        this.id_Peripheral = id_Peripheral;
        this.type = type;
        this.connectionType = connectionType;
        this.isWireless = isWireless;
    }

    public Peripheral(int SKU, String brand, String model, String description, double price,
                      String type, String connectionType, boolean isWireless) {
        super(SKU, brand, model, description, price);
        this.type = type;
        this.connectionType = connectionType;
        this.isWireless = isWireless;
    }

    public int getId_Peripheral() {
        return id_Peripheral;
    }

    public void setId_Peripheral(int id_Peripheral) {
        this.id_Peripheral = id_Peripheral;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public boolean isWireless() {
        return isWireless;
    }

    public void setWireless(boolean wireless) {
        isWireless = wireless;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Periferico{" +
                "type='" + type + '\'' +
                ", connectionType='" + connectionType + '\'' +
                ", isWireless=" + isWireless +
                '}';
    }
}
