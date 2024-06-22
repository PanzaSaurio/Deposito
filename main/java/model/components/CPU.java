package model.components;

import model.Product;

public class CPU extends Product implements  IInstallableComponent {

    private String clockSpeed;
    private int coreCount;
    private int threadCount;
    private String socketType;
    private boolean installed;
    private float installationTime;
    private String installationGuideURL;

    public CPU() {
        super();
    }

    public CPU(int id_Product, int SKU, String brand, String model, String description, double price, String clockSpeed,
               int coreCount, int threadCount, String socketType, boolean installed, float installationTime, String installationGuideURL) {
        super(id_Product, SKU, brand, model, description, price);
        this.clockSpeed = clockSpeed;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
        this.socketType = socketType;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public CPU(int SKU, String brand, String model, String description, double price, String clockSpeed, int coreCount,
               int threadCount, String socketType, boolean installed, float installationTime, String installationGuideURL) {
        super(SKU, brand, model, description, price);
        this.clockSpeed = clockSpeed;
        this.coreCount = coreCount;
        this.threadCount = threadCount;
        this.socketType = socketType;
        this.installed = installed;
        this.installationTime = installationTime;
        this.installationGuideURL = installationGuideURL;
    }

    public String getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(String clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public void setCoreCount(int coreCount) {
        this.coreCount = coreCount;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public String getSocketType() {
        return socketType;
    }

    public void setSocketType(String socketType) {
        this.socketType = socketType;
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
            System.out.println("La CPU fue instalada.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La CPU ya esta instalada.");
        }
    }

    @Override
    public void upgrade(float time) {
        if (installed) {
            setInstalled(true);
            setInstallationTime(time);
            System.out.println("Se actualizo la CPU.");
        } else {
            setInstalled(false);
            setInstallationTime(0f);
            System.out.println("La CPU debe estar instalada antes de actualizar.");
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "CPU{" +
                "clockSpeed='" + clockSpeed + '\'' +
                ", coreCount=" + coreCount +
                ", threadCount=" + threadCount +
                ", socketType='" + socketType + '\'' +
                ", installed=" + installed +
                ", installationTime=" + installationTime +
                ", installationGuideURL='" + installationGuideURL + '\'' +
                '}';
    }
}
