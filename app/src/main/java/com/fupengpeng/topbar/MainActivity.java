package com.fupengpeng.topbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fupengpeng.topbar.view.Topbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Topbar topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListerner() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this,"leftButton",Toast.LENGTH_LONG).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this,"rightButton",Toast.LENGTH_LONG).show();
            }
        });

//        topbar.setLeftIsVisable(false);//调用自定义view的方法实现对其进行控制
    }
}
