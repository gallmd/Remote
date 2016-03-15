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
public class NetworkCommandBuilder {
    private String rokuIP;
    private String serverIP;
    private int rokuPort;
    private int serverPort;
    private Hashtable<String, String> commandHash;

    private String commandMap;


    public NetworkCommandBuilder(String rokuIP, String serverIP, String commandMap){
        this.rokuIP = rokuIP;
        this.serverIP = serverIP;
        this.commandMap = commandMap;
        buildHash();

    }

    public String buildNetworkCommand(View parentLayout,View button){
        String buttonID;

        buttonID = (String)commandHash.get(parentLayout.getContentDescription() + ";" + button.getContentDescription());
        return buttonID;

        }



    private void buildHash(){

        commandHash = new Hashtable<String,String>();
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
                key1 = jsonCommandObject.getString("command destination");
                key2 = jsonCommandObject.getString("content description");
                commandText = jsonCommandObject.getString("command text");

                key = key1 + ";" + key2;


                commandHash.put(key, commandText);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
