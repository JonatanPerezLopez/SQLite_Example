package com.example.sqliteexample;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText ssid, bssid, fake, active, previous;
    Button saveBtn;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ssid = (EditText)findViewById(R.id.txtName);
        bssid = (EditText)findViewById(R.id.txtLocation);
        fake = (EditText)findViewById(R.id.txtDesignation);
        active = (EditText)findViewById(R.id.txtActive);
        saveBtn = (Button)findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SSID = ssid.getText().toString()+"\n";
                String BSSID = bssid.getText().toString();
                int FAKE = Integer.parseInt(fake.getText().toString());
                int ACTIVE = Integer.parseInt(active.getText().toString());
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertNetworkDetails(SSID,BSSID,FAKE,ACTIVE,0);
                intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
