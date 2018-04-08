package com.example.a20182.monitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Teaching extends AppCompatActivity {

    private boolean flag_step = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching);

    }
    public void btnNext(View view){
        if(!flag_step)
        {
            ImageView iv_next = findViewById(R.id.iv_teachstep);
            iv_next.setImageResource(R.drawable.teachstep1);
            flag_step = !flag_step;
        }else{
            startActivity(new Intent(Teaching.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
