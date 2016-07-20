package adityagoel.staffapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class infodisplay extends AppCompatActivity {

    Button okbutton;
    EditText ethostelname, etDoneDate, etDoneTime, etroom;
    SharedPreferences prefs3;
    String hostelname, DoneDate, DoneTime, firstnumber, secondnumber, thirdnumber;
    private ProgressDialog progress;
    String hostelnamestring, donedatestring, donetimestring, roomnumberstring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infodisplay);

        prefs3 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        hostelname = prefs3.getString("hostelname", null);
        DoneDate = prefs3.getString("DoneDate", null);
        DoneTime = prefs3.getString("DoneTime", null);
        firstnumber = prefs3.getString("firstnumber", null);
        secondnumber = prefs3.getString("secondnumber", null);
        thirdnumber = prefs3.getString("thirdnumber", null);

        ethostelname = (EditText)findViewById(R.id.ethostelname);
        etDoneDate = (EditText)findViewById(R.id.etdate);
        etDoneTime = (EditText)findViewById(R.id.ettime);
        etroom = (EditText)findViewById(R.id.etroom);
        okbutton = (Button)findViewById(R.id.okbutton);

        ethostelname.setText(hostelname);
        etDoneDate.setText(DoneDate);
        etDoneTime.setText(DoneTime);
        etroom.setText(firstnumber + secondnumber + thirdnumber);

        hostelnamestring = ethostelname.getText().toString();
        donedatestring = etDoneDate.getText().toString();
        donetimestring = etDoneTime.getText().toString();
        roomnumberstring = etroom.getText().toString();

    }//onCreate



    public void sendPostRequest(View View) {
        //    Log.d("EditText value=", fs_nam.getText().toString());
        new PostClass(this,hostelnamestring,donedatestring,donetimestring,roomnumberstring).execute();

    }//sendPostRequest


    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private final String hostelnamestring;
        private final String donedatestring;
        private final String donetimestring;
        private final String roomnumberstring;

        public PostClass(Context c, String hostelname, String donedate, String donetime, String roomnumber) {
            this.context = c;
            hostelnamestring = hostelname;
            donedatestring = donedate;
            donetimestring = donetime;
            roomnumberstring = roomnumber;
        }



        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                final String ip_pref = "Server IP";
                SharedPreferences sp = getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
                String url_post="http://"+sp.getString("IP_address","default")+"/process_post";
                URL url = new URL(url_post);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Log.d("Connected","good");
                String urlParameters = "hostelname="+hostelnamestring+"&donedate="+donedatestring + "&donetime="+donetimestring + "&roomnumber="+roomnumberstring;

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
                Log.d("helloo", output.toString());
                progress.dismiss();
                Intent intent= new Intent(getApplicationContext(), roomnumber.class);
                startActivity(intent);


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


    }//PostClass

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), roomnumber.class);
        startActivity(intent);
    }


}//infodisplay

