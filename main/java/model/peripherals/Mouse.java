package model.peripherals;

public class Mouse extends Peripheral {

    private int dpi;
    private boolean isGamer;

    public Mouse() {
        super();
    }

    public Mouse(int id_Product, int SKU, String brand, String model, String description, double price,
                 int id_Peripheral,String type, String connectionType, boolean isWireless, int dpi, boolean isGamer) {
        super(id_Product, SKU, brand, model, description, price,id_Peripheral, type, connectionType, isWireless);
        this.dpi = dpi;
        this.isGamer = isGamer;
    }

    public Mouse(int SKU, String brand, String model, String description, double price, String type,
                 String connectionType, boolean isWireless, int dpi, boolean isGamer) {
        super(SKU, brand, model, description, price, type, connectionType, isWireless);
        this.dpi = dpi;
        this.isGamer = isGamer;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public boolean isGamer() {
        return isGamer;
    }

    public void setGamer(boolean gamer) {
        isGamer = gamer;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Mouse{" +
                "dpi=" + dpi +
                ", isGamer=" + isGamer +
                '}';
    }
}
