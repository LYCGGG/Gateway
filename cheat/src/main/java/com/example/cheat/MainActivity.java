package com.example.cheat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static Toast test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch lightSwitch = findViewById(R.id.light_switch);
        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(MainActivity.this,getString(R.string.test),Toast.LENGTH_SHORT).show();
//                test.setText(R.string.test);
            }
        });
    }
}
