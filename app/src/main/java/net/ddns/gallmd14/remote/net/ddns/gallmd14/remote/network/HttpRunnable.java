package net.ddns.gallmd14.remote.net.ddns.gallmd14.remote.network;

import net.ddns.gallmd14.remote.NetworkCommand;

/**
 * Created by matt on 3/19/16.
 */
public class HttpRunnable implements Runnable {

    private NetworkCommand networkCommand;

    public HttpRunnable(NetworkCommand networkCommand){
        this.networkCommand = networkCommand;
    }
    @Override
    public void run() {

    }
}
