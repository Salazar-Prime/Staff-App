package adityagoel.staffapp2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class roomnumber extends AppCompatActivity {

    Button donebutton, changehostelbutton;
    EditText editText;
    Intent intent;
    SharedPreferences prefs;
    SharedPreferences prefs2;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    String a,b,c;
    String hostelname;
    String DoneDate;
    String DoneTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomnumber);

        changehostelbutton = (Button)findViewById(R.id.changehostelbutton);
        donebutton = (Button)findViewById(R.id.donebutton);
        editText = (EditText)findViewById(R.id.edittext);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        hostelname = prefs.getString("hostelname", null);
        editText.setText(hostelname);

        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                a = spinner1.getSelectedItem().toString();
                b = spinner2.getSelectedItem().toString();
                c = spinner3.getSelectedItem().toString();

                DoneDate = (DateFormat.getDateInstance().format(new Date())).toString();
                DoneTime = (DateFormat.getTimeInstance().format(new Date())).toString();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(roomnumber.this);
                alertDialogBuilder.setMessage("Are you sure, room " + hostelname + "-" + a + b + c + " ?");

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        ll301.setVisibility(View.INVISIBLE);
//                        Runnable mRunnable;
//                        mRunnable=new Runnable() {

//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                            }
                        button_function();
                        prefs2.edit().putString("DoneDate", DoneDate).apply();
                        prefs2.edit().putString("DoneTime", DoneTime).apply();
                        prefs2.edit().putString("firstnumber", a).apply();
                        prefs2.edit().putString("secondnumber", b).apply();
                        prefs2.edit().putString("thirdnumber", c).apply();
                        startActivity(intent);
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        changehostelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), hostelname.class);

                startActivity(i);
                finish();
            }
        });
    }//oncreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            final String ip_pref = "Server params";
            SharedPreferences sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Login_Flag","0");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.status) {
            Intent intent = new Intent(getApplicationContext(), today_status.class);
            startActivity(intent);
            //finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);

    }

    private void button_function() {
        intent = new Intent(getApplicationContext(), infodisplay.class);

        prefs2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        intent = new Intent(getApplicationContext(), hostelname.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}