package model.components;

public interface IInstallableComponent {

    boolean isInstalled = false;
    float getInstallationTime = 0;
    String getInstallationGuideURL = null;
    void install(float time);
    void upgrade(float time);
}
