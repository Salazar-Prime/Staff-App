package adityagoel.staffapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;
    private ProgressDialog progress;
    EditText user;
    EditText password;
    int responseCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            final String ip_pref = "Server params";
            SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
            if(sp.getString("Login_Flag","default").equalsIgnoreCase("1")){
                Intent intent = new Intent(getApplicationContext(), hostelname.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user= (EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);

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

        if (id == R.id.ip_config) {
            Intent intent = new Intent(getApplicationContext(), IP_settings.class);
            startActivity(intent);
            //            View view = findViewById(android.R.id.set);
//            Snackbar.make(view, "IP Changed Successfully", Snackbar.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.skip) {
            final String ip_pref = "Server params";
            SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Login_Flag","1");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), hostelname.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void verifyCredentials(View View) {
        new PostClass(this,user.getText().toString(),password.getText().toString(),View).execute();

    }


    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private final String user;
        private final String password;
        private final View v;

        public PostClass(Context c, String s, String s1, View view) {
            this.context = c;
            user = s;
            password = s1;
            v = view;
        }//Constructor

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Verifying");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                final String ip_pref = "Server params";
                SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
                String url_login="http://"+sp.getString("IP_address","default")+"/index";
                URL url = new URL(url_login);
                final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "username="+user+"&pass="+password;

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                responseCode = connection.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                LoginActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progress.dismiss();
                        System.out.println(responseCode);
                        if(responseCode==200){
                            final String ip_pref = "Server params";
                            SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("Login_Flag","1");
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), hostelname.class);
                            startActivity(intent);
                        }
                        else if(responseCode==401){
                            Snackbar.make(v,"Incorrect Password or username",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }//doInBackGround
    }//PostClass

    public void onBackPressed() {
       finish();
       super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

}//LoginActivity
