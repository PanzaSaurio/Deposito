package model.peripherals;

public class Keyboard extends Peripheral{

    private boolean isMechanical;
    private String switchType;

    public Keyboard() {
        super();
    }

    public Keyboard(int id_Product, int SKU, String brand, String model, String description, double price,
                    int id_Peripheral, String type, String connectionType, boolean isWireless, boolean isMechanical,
                    String switchType) {
        super(id_Product, SKU, brand, model, description, price,id_Peripheral, type, connectionType, isWireless);
        this.isMechanical = isMechanical;
        this.switchType = switchType;
    }

    public Keyboard(int SKU, String brand, String model, String description, double price, String type,
                    String connectionType, boolean isWireless, boolean isMechanical, String switchType) {
        super(SKU, brand, model, description, price, type, connectionType, isWireless);
        this.isMechanical = isMechanical;
        this.switchType = switchType;
    }

    public boolean isMechanical() {
        return isMechanical;
    }

    public void setMechanical(boolean mechanical) {
        isMechanical = mechanical;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Keyboard{" +
                "isMechanical=" + isMechanical +
                ", switchType='" + switchType + '\'' +
                '}';
    }
}
