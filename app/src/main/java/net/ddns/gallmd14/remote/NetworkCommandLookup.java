package net.ddns.gallmd14.remote;

import android.app.Application;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created by matt on 2/24/16.
 */
public class NetworkCommandLookup {
    private String rokuIP;
    private String serverIP;
    private int rokuPort;
    private int serverPort;
    private Hashtable<String, NetworkCommand> commandHash;

    private String commandMap;


    public NetworkCommandLookup(String rokuIP, int rokuPort, String serverIP, int serverPort, String commandMap){
        this.rokuIP = rokuIP;
        this.serverIP = serverIP;
        this.commandMap = commandMap;
        this.rokuPort = rokuPort;
        this.serverPort = serverPort;
        buildHash();

    }


    public NetworkCommand buildNetworkCommand(String commandType, String commandArg1, String commandArg2){

        NetworkCommand networkCommand = new NetworkCommand();

        if(commandType.equals("roku")){

            networkCommand.setCommandType(NetworkCommand.COMMAND_TYPE_ROKU);
            networkCommand.setIpAddress(this.rokuIP);
            networkCommand.setPortNumber(this.rokuPort);

        }else if(commandType.equals("tv")){

            networkCommand.setCommandType(NetworkCommand.COMMAND_TYPE_TV);
            networkCommand.setIpAddress(this.serverIP);
            networkCommand.setPortNumber(this.serverPort);

        }else if(commandType.equals("sound")){

            networkCommand.setCommandType(NetworkCommand.COMMAND_TYPE_SOUND);
            networkCommand.setIpAddress(this.serverIP);
            networkCommand.setPortNumber(this.serverPort);
        }
        networkCommand.setCommandArg1(commandArg1);


        return networkCommand;

    }



    private void buildHash(){

        commandHash = new Hashtable<String,NetworkCommand>();
        JSONArray jsonCommandArray;
        JSONObject jsonCommandObject;
        String key1;
        String key2;
        String key;
        String commandText;

        try {
            JSONObject object = (JSONObject) new JSONTokener(commandMap).nextValue();
            jsonCommandArray = object.getJSONArray("commands");

            for(int i = 0; i <= jsonCommandArray.length(); i++){
                jsonCommandObject = jsonCommandArray.getJSONObject(i);
                key1 = jsonCommandObject.getString("command type");
                key2 = jsonCommandObject.getString("content description");
                commandText = jsonCommandObject.getString("command argument");

                key = key1 + ";" + key2;

                NetworkCommand nc = this.buildNetworkCommand(key1, commandText,null);

                commandHash.put(key, nc);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public NetworkCommand lookupNetworkCommand(View parentLayout, View button){
        NetworkCommand nc = new NetworkCommand();
        nc = commandHash.get(parentLayout.getContentDescription() + ";" + button.getContentDescription());

        return nc;
    }
}
