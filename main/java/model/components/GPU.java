package model.components;

import model.Product;

public class GPU extends Product implements IInstallableComponent {

    private String memorySize;
    private String memoryType;
    private String coreClock;
    private boolean installed;
    private float installationTime;
    private String installationGuideURL;

    public GPU() {
        super();
    }

    public GPU(int id_Product, int SKU, String brand, String model, String description, double price, String memorySize,
               String memoryType, String coreClock, boolean installed, float installationTime, String installationGuideURL) {
        super(id_Product, SKU, brand, model, description, price);
        this.memorySize = memorySize;
        this.memoryType = memoryType;
        this.coreClock = coreClock;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public GPU(int SKU, String brand, String model, String description, double price, String memorySize, String memoryType,
               String coreClock, boolean installed, float installationTime, String installationGuideURL) {
        super(SKU, brand, model, description, price);
        this.memorySize = memorySize;
        this.memoryType = memoryType;
        this.coreClock = coreClock;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public String getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public String getCoreClock() {
        return coreClock;
    }

    public void setCoreClock(String coreClock) {
        this.coreClock = coreClock;
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
            System.out.println("La GPU fue instalada.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La GPU ya está instalada.");
        }
    }

    @Override
    public void upgrade(float time) {
        if (installed) {
            setInstalled(true);
            setInstallationTime(time);
            System.out.println("Se actualizó la GPU.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La GPU debe estar instalada antes de actualizar.");
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "GPU{" +
                "memorySize='" + memorySize + '\'' +
                ", memoryType='" + memoryType + '\'' +
                ", coreClock='" + coreClock + '\'' +
                ", installed=" + installed +
                ", installationTime=" + installationTime +
                ", installationGuideURL='" + installationGuideURL + '\'' +
                '}';
    }
}
