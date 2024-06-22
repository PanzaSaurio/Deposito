package model.peripherals;

public class Headphones extends Peripheral {

    private int id_Peripheral;
    private String type;
    private double weight;
    private boolean microphone;
    private int inputImpedance;
    private int sensitivity;
    private int frequencyResponse;
    private boolean micSensitivity;
    private boolean micFrequencyResponse;

    public Headphones() {
        super();
    }

    public Headphones(int id_Product, int SKU, String brand, String model, String description, double price,
                      int id_Peripheral,String type, String connectionType, boolean isWireless, String type1,
                      double weight, boolean microphone, int inputImpedance, int sensitivity, int frequencyResponse,
                      boolean micSensitivity, boolean micFrequencyResponse) {
        super(id_Product, SKU, brand, model, description, price, id_Peripheral, type, connectionType, isWireless);
        this.id_Peripheral = id_Peripheral;
        this.type = type1;
        this.weight = weight;
        this.microphone = microphone;
        this.inputImpedance = inputImpedance;
        this.sensitivity = sensitivity;
        this.frequencyResponse = frequencyResponse;
        this.micSensitivity = micSensitivity;
        this.micFrequencyResponse = micFrequencyResponse;
    }

    public Headphones(int SKU, String brand, String model, String description, double price, String type,
                      String connectionType, boolean isWireless, String type1, double weight, boolean microphone,
                      int inputImpedance, int sensitivity, int frequencyResponse, boolean micSensitivity
                      , boolean micFrequencyResponse) {
        super(SKU, brand, model, description, price, type, connectionType, isWireless);
        this.type = type1;
        this.weight = weight;
        this.microphone = microphone;
        this.inputImpedance = inputImpedance;
        this.sensitivity = sensitivity;
        this.frequencyResponse = frequencyResponse;
        this.micSensitivity = micSensitivity;
        this.micFrequencyResponse = micFrequencyResponse;
    }


    @Override
    public int getId_Peripheral() {
        return id_Peripheral;
    }

    @Override
    public void setId_Peripheral(int id_Peripheral) {
        this.id_Peripheral = id_Peripheral;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isMicrophone() {
        return microphone;
    }

    public void setMicrophone(boolean microphone) {
        this.microphone = microphone;
    }

    public int getInputImpedance() {
        return inputImpedance;
    }

    public void setInputImpedance(int inputImpedance) {
        this.inputImpedance = inputImpedance;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public int getFrequencyResponse() {
        return frequencyResponse;
    }

    public void setFrequencyResponse(int frequencyResponse) {
        this.frequencyResponse = frequencyResponse;
    }

    public boolean isMicSensitivity() {
        return micSensitivity;
    }

    public void setMicSensitivity(boolean micSensitivity) {
        this.micSensitivity = micSensitivity;
    }

    public boolean isMicFrequencyResponse() {
        return micFrequencyResponse;
    }

    public void setMicFrequencyResponse(boolean micFrequencyResponse) {
        this.micFrequencyResponse = micFrequencyResponse;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Headphones{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", microphone=" + microphone +
                ", inputImpedance=" + inputImpedance +
                ", sensitivity=" + sensitivity +
                ", frequencyResponse=" + frequencyResponse +
                ", micSensitivity=" + micSensitivity +
                ", micFrequencyResponse=" + micFrequencyResponse +
                '}';
    }
}
