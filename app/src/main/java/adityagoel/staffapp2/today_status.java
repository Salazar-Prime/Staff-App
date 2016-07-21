package adityagoel.staffapp2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class today_status extends AppCompatActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_status);
        new Get_status(this).execute();
    }

    private class Get_status extends AsyncTask<String, Void, Void> {

        private final Context context;

        public Get_status(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {


                final String ip_pref = "Server params";
                SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
                String url_login="http://"+sp.getString("IP_address","default")+"/index";
                URL url = new URL(url_login);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "fizz=buzz";
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();
                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

                //parsing JSON
                String data_extracted = "";
                String id_disp="";
                String hostel_disp="";
                String room_disp="";
                String time_disp="";
                JSONArray data = new JSONArray(responseOutput.toString());

                for(int i=0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String hos_name = jsonObject.optString("hostel_name");
                    int room = Integer.parseInt(jsonObject.optString("room_number"));
                    String time = jsonObject.optString("time");

                 //   data_extracted+="ID ="+(i+1)+"\n Hostel = "+ hos_name +" \n Room= "+ room +" \n Time= "+ time +" \n ";
                    id_disp+="      "+(i+1)+"\n";
                    hostel_disp+=hos_name+"\n";
                    room_disp+=room+"\n";
                    time_disp+=time+"\n";

                }

                final String finalData_extracted = data_extracted;
                final String finalHostel_disp = hostel_disp;
                final String finalId_disp = id_disp;
                final String finalRoom_disp = room_disp;
                final String finalTime_disp = time_disp;
                today_status.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView tv1 = (TextView) findViewById(R.id.textView1);
                        TextView tv2 = (TextView) findViewById(R.id.textView2);
                        TextView tv3 = (TextView) findViewById(R.id.textView3);
                        TextView tv4 = (TextView) findViewById(R.id.textView4);

                        tv1.setText(finalId_disp);
                        tv2.setText(finalHostel_disp);
                        tv3.setText(finalRoom_disp);
                        tv4.setText(finalTime_disp);


                        progress.dismiss();
                    }
                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }//Getstatus

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}//today_status
