package adityagoel.staffapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IP_settings extends AppCompatActivity {

    public static final String ip_pref = "Server params";
    SharedPreferences sp;
    EditText et1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_ip);
        et1=(EditText)findViewById(R.id.ip_string);
        b1=(Button)findViewById(R.id.btn_ip);
        sp=getSharedPreferences(ip_pref, Context.MODE_PRIVATE);
        et1.setText(sp.getString("IP_address","default"));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = et1.getText().toString();
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("IP_address",ip);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Context context = getApplicationContext();
                Toast.makeText(context, "IP Updated Successfully", Toast.LENGTH_LONG).show();

            }
        });
    }
}
