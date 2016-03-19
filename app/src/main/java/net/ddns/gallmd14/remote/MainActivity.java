package net.ddns.gallmd14.remote;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private NetworkCommandLookup networkCommandLookup;
    private String commandJSON;
    private SharedPreferences preferences;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction trans = getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainActivityFragment());
        trans.addToBackStack("main");
        trans.commit();

        try {
            this.commandJSON = loadCommandsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor;
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.clear();
        editor.commit();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String serverIP = preferences.getString("pref_server_ip", null);
        String rokuIP = preferences.getString("pref_roku_ip", null);
        String serverPort = preferences.getString("pref_server_port", null);
        String rokuPort = preferences.getString("pref_roku_port", null);

        int iServerPort = Integer.parseInt(serverPort);
        int iRokuPort = Integer.parseInt(rokuPort);




        networkCommandLookup = new NetworkCommandLookup(rokuIP, iRokuPort, serverIP, iServerPort, this.commandJSON);



        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                Toast toast = Toast.makeText(getApplicationContext(),"changed", Toast.LENGTH_LONG);
                toast.show();
                rebuildNetworkCommandLookup();

            }
        };
        preferences.registerOnSharedPreferenceChangeListener(prefListener);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast toast = Toast.makeText(getApplication().getApplicationContext(),"settings",Toast.LENGTH_SHORT);
            toast.show();

            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();
            trans.replace(R.id.fragment_container, settingsFragment);
            trans.addToBackStack("settings");
            trans.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View button){


        View parentLayout =(View) button.getParent();

       Snackbar snackbar = Snackbar.make(button, networkCommandLookup.lookupNetworkCommand(parentLayout, button).toString(), Snackbar.LENGTH_LONG);
        snackbar.show();



    }


    public String loadCommandsFromFile() throws IOException {
        String str="";
        StringBuffer buf = new StringBuffer();
        InputStream is = this.getResources().openRawResource(R.raw.command_map);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        if (is!=null) {
            while ((str = reader.readLine()) != null) {
                buf.append(str + "\n" );
            }
        }
        is.close();
        return buf.toString();



    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        int backStackCount = fm.getBackStackEntryCount();

        if(backStackCount>1){
            fm.popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    public void rebuildNetworkCommandLookup(){

        try {
            this.commandJSON = null;
            this.commandJSON = loadCommandsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String serverIP = preferences.getString("pref_server_ip", null);
        String rokuIP = preferences.getString("pref_roku_ip", null);
        String serverPort = preferences.getString("pref_server_port", null);
        String rokuPort = preferences.getString("pref_roku_port", null);

        int iServerPort = Integer.parseInt(serverPort);
        int iRokuPort = Integer.parseInt(rokuPort);



        networkCommandLookup = null;
        networkCommandLookup = new NetworkCommandLookup(rokuIP, iRokuPort, serverIP, iServerPort, this.commandJSON);
    }

}
