package com.rapidbox.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String text = " ";
    String passwordEnc = null;
    String passwordDec = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button paste = (Button) findViewById(R.id.paste);
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    passwordDec = AESencrp.decrypt(passwordEnc);
                    System.out.println("Decrypted Text "+passwordDec);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
         final ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        // ClipData clip = ClipData.newPlainText("Password","myPassword");
        //clipboard.setPrimaryClip(clip);


        assert clipboard != null;
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {

                ClipData clipData =clipboard.getPrimaryClip();
                if(clipData!=null) {
//                    System.out.println("Text " + clipData.getItemAt(0).getText().toString());
                    ClipData.Item item = clipData.getItemAt(0);
                    try {
                        text = item.getText().toString();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        passwordEnc = AESencrp.encrypt(text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("Encrypted Data "+passwordEnc);

                    APIService apiService = APIService.getInstance();
                    IAPIInterface iapiInterface = apiService.getIapiInterface();
                    Gson gson = new Gson();
                    AppData appData = new AppData();
                    appData.setData(passwordEnc);
                    String newText = gson.toJson(appData);
                    Call<Boolean> call = iapiInterface.sendClipdata(appData);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            System.out.println("Response" + response.body());
                            Toast.makeText(MainActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            // System.out.println(t.toString());
                        }
                    });
                }

                
            }



        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
