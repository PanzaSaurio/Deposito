package model.components;

import model.Product;

public class Motherboard extends Product implements IInstallableComponent {

    private String formFactor;
    private String chipset;
    private String socketType;
    private String memoryType;
    private int maxMemory;
    private boolean installed;
    private float installationTime;
    private String installationGuideURL;

    public Motherboard(){
        super();
    }

    public Motherboard(int id_Product, int SKU, String brand, String model, String description, double price, String formFactor, String chipset,
                       String socketType, String memoryType, int maxMemory, boolean installed, float installationTime, String installationGuideURL) {

        super(id_Product, SKU, brand, model, description, price);
        this.formFactor = formFactor;
        this.chipset = chipset;
        this.socketType = socketType;
        this.memoryType = memoryType;
        this.maxMemory = maxMemory;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public Motherboard(int SKU, String brand, String model, String description, double price, String formFactor, String chipset,
                       String socketType, String memoryType, int maxMemory, boolean installed, float installationTime, String installationGuideURL) {
        super(SKU, brand, model, description, price);
        this.formFactor = formFactor;
        this.chipset = chipset;
        this.socketType = socketType;
        this.memoryType = memoryType;
        this.maxMemory = maxMemory;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocketType() {
        return socketType;
    }

    public void setSocketType(String socketType) {
        this.socketType = socketType;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public float getInstallationTime() {
        return installationTime;
    }

    public void setInstallationTime(float installationTime) {
        this.installationTime = installationTime;
    }

    public String getInstallationGuideURL() {
        return installationGuideURL;
    }

    public void setInstallationGuideURL(String installationGuideURL) {
        this.installationGuideURL = installationGuideURL;
    }

    @Override
    public void install(float time) {
        if (!installed) {
            setInstalled(true);
            setInstallationTime(time);
            System.out.println("La Motherboard fue instalada.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La Motherboard ya esta instalada.");
        }
    }

    @Override
    public void upgrade(float time) {
        if (installed) {
            setInstalled(true);
            setInstallationTime(time);
            System.out.println("Se actualizo la Motherboard.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La Motherboard debe estar instalada antes de actualizar.");
        }
    }

    @Override
    public String toString() {
        return  super.toString() +
                "Motherboard{" +
                "formFactor='" + formFactor + '\'' +
                ", chipset='" + chipset + '\'' +
                ", socketType='" + socketType + '\'' +
                ", memoryType='" + memoryType + '\'' +
                ", maxMemory=" + maxMemory +
                ", installed=" + installed +
                ", installationTime=" + installationTime +
                ", installationGuideURL='" + installationGuideURL + '\'' +
                '}';
    }


}
