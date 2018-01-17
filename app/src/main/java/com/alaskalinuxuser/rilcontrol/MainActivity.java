/*  Copyright 2017 by AlaskaLinuxUser (https://thealaskalinuxuser.wordpress.com)
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.alaskalinuxuser.rilcontrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView stoprd, startrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stoprd = (ImageView)findViewById(R.id.stopRilView);
        startrd = (ImageView)findViewById(R.id.startRilView);

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
        if (id == R.id.action_about) {
            aboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Okay, so here we build the popup to tell them about the app.
    public void aboutDialog () {

        // Here is what the popup will do.
        new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("About")
                .setMessage(getString(R.string.action_aboutApp))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Testing only //
                        Log.i("WJH", "Chose OK.");// Testing only //
                    }
                })

                .setNegativeButton("Website", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Uri uriUrl = Uri.parse("https://thealaskalinuxuser.wordpress.com");
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                })

                .setNeutralButton("GitHub", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Uri uriUrl = Uri.parse("https://github.com/alaskalinuxuser");
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                })

                .show(); // Make sure you show your popup or it wont work very well!

    } // END About Dialog builder.

    public void stopRilNow (View view) {

        try {

            // the command to run
            String[] rilStop = { "su", "-c", "stop ril-daemon" };
            Runtime.getRuntime().exec(rilStop);
            stoprd.setImageResource(R.drawable.gsmstopdark);

        } catch (IOException e) {
            // tell the user the problem
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        new CountDownTimer(1000, 1000) {

            // Implement code to happen every tick of the countdown.
            public void onTick (long myTimer) {
                // Do nothing.
            }

            // Implement code to happen when it is done counting down.
            public void onFinish() {

                stoprd.setImageResource(R.drawable.gsmstop);

            }

        }.start();


    } // End stopRilNow

    public void startRilNow (View view) {

        try {

            // the command to run
            String[] rilStop = { "su", "-c", "start ril-daemon" };
            Runtime.getRuntime().exec(rilStop);
            startrd.setImageResource(R.drawable.gsmstartdark);

        } catch (IOException e) {
            // tell the user the problem
            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        new CountDownTimer(1000, 1000) {

            // Implement code to happen every tick of the countdown.
            public void onTick (long myTimer) {
                // Do nothing.
            }

            // Implement code to happen when it is done counting down.
            public void onFinish() {

                startrd.setImageResource(R.drawable.gsmstart);

            }

        }.start();

    } // end startRilNow

}
