package net.ddns.gallmd14.remote.net.ddns.gallmd14.remote.network;

import net.ddns.gallmd14.remote.NetworkCommand;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by matt on 3/19/16.
 */
public class TvHttpRunnable implements HttpRunnable {

    private NetworkCommand networkCommand;
    private String httpFormatString;
    String charset = "UTF-8";
    private NetworkManager networkManager;

    public TvHttpRunnable(NetworkCommand networkCommand, NetworkManager networkManager){
        this.networkCommand = networkCommand;
        this.networkManager = networkManager;
    }
    @Override
    public void run() {

        try {
            buildURLString();
            URLConnection connection = new URL(this.httpFormatString).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            InputStream response = connection.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(response, writer, charset);

            String responseString = writer.toString();
            networkManager.handleResponse(responseString);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildURLString(){

        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(this.networkCommand.getIpAddress());
        sb.append(":");
        sb.append(this.networkCommand.getPortNumber());
        sb.append("/test.php?commandType=");
        sb.append(this.networkCommand.getCommandType());
        sb.append("&commandArgument1=");
        sb.append(this.networkCommand.getCommandArg1());


        this.httpFormatString = sb.toString();
    }


}
