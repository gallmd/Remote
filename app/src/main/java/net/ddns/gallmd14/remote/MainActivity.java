package net.ddns.gallmd14.remote;

import android.os.Bundle;
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

    private NetworkCommandBuilder networkCommandBuilder;
    private String commandMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            this.commandMap = playWithRawFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        networkCommandBuilder = new NetworkCommandBuilder("192","192", this.commandMap);


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View button){

        View parentLayout =(View) button.getParent();
//       Snackbar snackbar = Snackbar.make(button, parentLayout.getContentDescription(), Snackbar.LENGTH_LONG);
//        snackbar.show();

       Snackbar snackbar = Snackbar.make(button, networkCommandBuilder.buildNetworkCommand(parentLayout,button), Snackbar.LENGTH_LONG);
        snackbar.show();



    }


    public String playWithRawFiles() throws IOException {
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



    }// PlayWithSDFiles
}
