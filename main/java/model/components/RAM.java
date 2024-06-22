package model.components;

import model.Product;

public class RAM extends Product implements IInstallableComponent {
    private String capacity;
    private String type;
    private String speed;
    private String formFactor;
    private boolean installed;
    private float installationTime;
    private String installationGuideURL;

    public RAM(){
        super();
    }

    public RAM(int SKU, String brand, String model, String description, double price, String capacity, String type, String speed,
               String formFactor, boolean installed, float installationTime, String installationGuideURL) {
        super(SKU, brand, model, description, price);
        this.capacity = capacity;
        this.type = type;
        this.speed = speed;
        this.formFactor = formFactor;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public RAM(int id_Product, int SKU, String brand, String model, String description, double price, String capacity, String type, String speed,
               String formFactor, boolean installed, float installationTime, String installationGuideURL) {
        super(id_Product, SKU, brand, model, description, price);
        this.capacity = capacity;
        this.type = type;
        this.speed = speed;
        this.formFactor = formFactor;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
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
            System.out.println("La RAM fue instalada.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La RAM ya esta instalada.");
        }
    }

    @Override
    public void upgrade(float time) {
        if (installed) {
            setInstalled(true);
            setInstallationTime(time);
            System.out.println("Se actualizo la RAM.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La RAM debe estar instalada antes de actualizar.");
        }
    }


    @Override
    public String toString() {
        return super.toString() +
                "RAM{" +
                "capacity='" + capacity + '\'' +
                ", type='" + type + '\'' +
                ", speed='" + speed + '\'' +
                ", formFactor='" + formFactor + '\'' +
                ", installed=" + installed +
                ", installationTime=" + installationTime +
                ", installationGuideURL='" + installationGuideURL + '\'' +
                '}';
    }
}
