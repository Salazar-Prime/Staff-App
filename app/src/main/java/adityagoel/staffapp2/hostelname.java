package adityagoel.staffapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class hostelname extends AppCompatActivity {

    Intent intent = new Intent();
    RadioGroup radiogrouphostel;
    RadioButton radiobuttonaibaan, radiobuttonbeauki, radiobuttonchimair, radiobuttonduven, radiobuttonemiet, radiobuttonfirpeal;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hostelname);

        radiogrouphostel = (RadioGroup)findViewById(R.id.radiogrouphostel);
        radiobuttonaibaan = (RadioButton)findViewById(R.id.radiobuttonaibaan);
        radiobuttonbeauki = (RadioButton)findViewById(R.id.radiobuttonbeauki);
        radiobuttonchimair = (RadioButton)findViewById(R.id.radiobuttonchimair);
        radiobuttonduven = (RadioButton)findViewById(R.id.radiobuttonduven);
        radiobuttonemiet = (RadioButton)findViewById(R.id.radiobuttonemiet);
        radiobuttonfirpeal = (RadioButton)findViewById(R.id.radiobuttonfirpeal);

        radiobuttonaibaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("A", radiobuttonaibaan.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonaibaan.getText().toString()).apply();
                startActivity(intent);
            }
        });

        radiobuttonbeauki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("B", radiobuttonbeauki.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonbeauki.getText().toString()).apply();
                startActivity(intent);
            }
        });

        radiobuttonchimair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("C", radiobuttonchimair.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonchimair.getText().toString()).apply();
                startActivity(intent);
            }
        });

        radiobuttonduven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("D", radiobuttonduven.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonduven.getText().toString()).apply();
                startActivity(intent);
            }
        });

        radiobuttonemiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("E", radiobuttonemiet.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonemiet.getText().toString()).apply();
                startActivity(intent);
            }
        });

        radiobuttonfirpeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobutton_function();
                prefs.edit().putString("F", radiobuttonfirpeal.getText().toString()).apply();
                prefs.edit().putString("hostelname", radiobuttonfirpeal.getText().toString()).apply();
                startActivity(intent);
            }
        });
    }//onCreate

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
            return true;
        }

        if (id == R.id.status) {
            Intent intent = new Intent(getApplicationContext(), today_status.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void radiobutton_function(){
        intent = new Intent(getApplicationContext(), roomnumber.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
