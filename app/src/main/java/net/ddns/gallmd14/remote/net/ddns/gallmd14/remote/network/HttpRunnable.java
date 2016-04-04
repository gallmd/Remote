package net.ddns.gallmd14.remote.net.ddns.gallmd14.remote.network;

/**
 * Created by matt on 3/22/16.
 */
public interface HttpRunnable extends Runnable {
    void run();

    void buildURLString();
}
