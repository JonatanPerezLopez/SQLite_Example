package com.example.sqliteexample;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;


public class DetailsActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        DbHandler db = new DbHandler(this);
        ArrayList<HashMap<String, String>> networkList = db.GetNetworks();
        ListView lv = (ListView) findViewById(R.id.network_list);
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, networkList, R.layout.list_row,new String[]{"ssid","bssid","is_fake","active","previous"}, new int[]{R.id.ssid, R.id.bssid, R.id.is_fake,R.id.active,R.id.previous});
        lv.setAdapter(adapter);
        Button back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
