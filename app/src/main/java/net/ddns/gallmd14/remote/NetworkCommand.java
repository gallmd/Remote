package net.ddns.gallmd14.remote;

import android.view.View;

/**
 * Created by matt on 2/14/16.
 */
public class NetworkCommand {

    private int commandType;
    private String ipAddress;
    private int portNumber;
    private String commandArg1;

    public static int COMMAND_TYPE_ROKU = 1;
    public static int COMMAND_TYPE_TV = 2;
    public static int COMMAND_TYPE_SOUND =3;

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getCommandArg1() {
        return commandArg1;
    }

    public void setCommandArg1(String commandArg1) {
        this.commandArg1 = commandArg1;
    }

    public String getCommandArg2() {
        return commandArg2;
    }

    public void setCommandArg2(String commandArg2) {
        this.commandArg2 = commandArg2;
    }

    private String commandArg2;

    public NetworkCommand(){

    }

    public NetworkCommand(int commandType, String commandArg1, String commandArg2, String ipAddress, int portNumber){
        this.commandType = commandType;
        this.commandArg1 = commandArg1;
        this.commandArg2 = commandArg2;
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.commandType);
        sb.append(";");
        sb.append(this.commandArg1);
        sb.append(";");
        sb.append(this.ipAddress);
        sb.append(";");
        sb.append(this.portNumber);
        return sb.toString();
    }
}
