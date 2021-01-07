package com.macsags.itemconfigurationview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.macsags.itemconfigure.ItemConfigureView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ItemConfigureView ic  = (ItemConfigureView) findViewById(R.id.ic);
        ic.setOnSettingItemClick(new ItemConfigureView.OnSettingItemClick() {
            @Override
            public void click() {
                Toast.makeText(MainActivity.this, "1111111", Toast.LENGTH_SHORT).show();
            }
        });
        ic.switchRightStyle(3);
        ic.appendLeftText("2222");

    }
}