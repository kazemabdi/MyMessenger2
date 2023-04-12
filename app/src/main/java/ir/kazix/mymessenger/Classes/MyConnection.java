package ir.kazix.mymessenger.Classes;

public class MyConnection {

    private String clientId;
    private String socketId;
    private String remotePort;
    private String remoteAddress;
    private String userAgent;
    private String handshakeTime;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setHandshakeTime(String handshakeTime) {
        this.handshakeTime = handshakeTime;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSocketId() {
        return socketId;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getHandshakeTime() {
        return handshakeTime;
    }
}
