package model.videoGame;

import model.Product;

public class Console extends Product {
    private String storageCapacity;
    private boolean includesGames;
    private boolean isSpecialEdition;

    public Console() {
        super();
    }

    public Console(int id_Product, int SKU, String brand, String model, String description, double price, String storageCapacity, boolean includesGames, boolean isSpecialEdition) {
        super(id_Product, SKU, brand, model, description, price);
        this.storageCapacity = storageCapacity;
        this.includesGames = includesGames;
        this.isSpecialEdition = isSpecialEdition;
    }

    public Console(int SKU, String brand, String model, String description, double price, String storageCapacity, boolean includesGames, boolean isSpecialEdition) {
        super(SKU, brand, model, description, price);
        this.storageCapacity = storageCapacity;
        this.includesGames = includesGames;
        this.isSpecialEdition = isSpecialEdition;
    }

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public boolean isIncludesGames() {
        return includesGames;
    }

    public void setIncludesGames(boolean includesGames) {
        this.includesGames = includesGames;
    }

    public boolean isSpecialEdition() {
        return isSpecialEdition;
    }

    public void setSpecialEdition(boolean specialEdition) {
        isSpecialEdition = specialEdition;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Consola{" +
                "storageCapacity='" + storageCapacity + '\'' +
                ", includesGames=" + includesGames +
                ", isSpecialEdition=" + isSpecialEdition +
                '}';
    }
}
