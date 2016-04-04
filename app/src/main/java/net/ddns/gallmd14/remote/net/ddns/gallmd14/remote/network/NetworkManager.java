package net.ddns.gallmd14.remote.net.ddns.gallmd14.remote.network;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import net.ddns.gallmd14.remote.MainActivity;
import net.ddns.gallmd14.remote.NetworkCommand;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by matt on 3/22/16.
 */
public class NetworkManager {


    private static final int KEEP_ALIVE_TIME = 1;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    private static final int CORE_POOL_SIZE = 8;

    private static final int MAX_POOL_SIZE = 8;

    @SuppressWarnings("unused")
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private static NetworkManager sInstance = null;

    private final ThreadPoolExecutor commandThreadPool;

    private final BlockingQueue<Runnable> mHttpCommandQueue;

    private Context applicationContext;

    private Handler mHandler;

    static{

        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        sInstance = new NetworkManager();
    }

    private NetworkManager(){

        mHttpCommandQueue = new LinkedBlockingQueue<Runnable>();

        commandThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,KEEP_ALIVE_TIME_UNIT,mHttpCommandQueue);

        mHandler = new Handler(Looper.getMainLooper()){

            @Override
            public void handleMessage(Message inputMessage){

                String response = (String) inputMessage.obj;
                Toast toast = Toast.makeText(MainActivity.getContext(), response,Toast.LENGTH_SHORT);
                toast.show();
            }
        };

    }

    public static NetworkManager getInstance(){
        return sInstance;
    }

    public static void initializeNetworkManager(Context applicationContext){

        applicationContext = applicationContext;
    }

    public void sendCommand(NetworkCommand networkCommand){

        switch (networkCommand.getCommandType()){

            case NetworkCommand.COMMAND_TYPE_TV:
                TvHttpRunnable tvHttpRunnable = new TvHttpRunnable(networkCommand, sInstance);
                commandThreadPool.execute(tvHttpRunnable);
                break;

            case NetworkCommand.COMMAND_TYPE_ROKU:
                RokuHttpRunnable rokuHttpRunnable = new RokuHttpRunnable(networkCommand);
                commandThreadPool.execute(rokuHttpRunnable);
                break;

            case NetworkCommand.COMMAND_TYPE_SOUND:
                SoundHttpRunnable soundHttpRunnable =new SoundHttpRunnable(networkCommand);
                commandThreadPool.execute(soundHttpRunnable);
                break;

        }
    }

    public void handleResponse(String response){
        Message message = mHandler.obtainMessage(1, response);
        message.sendToTarget();
    }
}
