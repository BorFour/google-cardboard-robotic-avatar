package com.example.borch.surfaceviewcarboard;

/**
 * Created by borch on 20/06/16.
 */
public class AddressManager {

    private static AddressManager ourInstance = new AddressManager();

    public static AddressManager getInstance() {
        return ourInstance;
    }

    private String cameraLeft = "";
    private String cameraRight = "";
    private String controlServerAddress = "";
    private int controlServerPort = 0;
    private boolean send = false;

    public String getCameraLeft() {
        return cameraLeft;
    }

    public void setCameraLeft(String cameraLeft) {
        this.cameraLeft = cameraLeft;
    }

    public String getCameraRight() {
        return cameraRight;
    }

    public void setCameraRight(String cameraRight) {
        this.cameraRight = cameraRight;
    }

    public String getControlServerAddress() {
        return controlServerAddress;
    }

    public void setControlServerAddress(String controlServerAddress) {
        this.controlServerAddress = controlServerAddress;
    }

    public int getControlServerPort() {
        return controlServerPort;
    }

    public void setControlServerPort(int controlServerPort) {
        this.controlServerPort = controlServerPort;
    }

    public boolean shouldSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
  

    public static AddressManager getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(AddressManager ourInstance) {
        AddressManager.ourInstance = ourInstance;
    }

    private AddressManager() {
    }
}
