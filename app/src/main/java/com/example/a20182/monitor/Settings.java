package com.example.a20182.monitor;

/*
 * The Settings activity ie used to set some values for the selected application
 * which will be displayed in the list in MainActivity.
 * I stipulate that the input for max-time should not be null or a large number.
 * I use storageData function to store the data before the jump to
 * make sure that the data will convey to main activity correctly
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Settings extends AppCompatActivity {

    private static EditText TipsText;

    private Application curset = MainActivity.AppList.get(Select.curPosition);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView appName = (TextView)findViewById(R.id.tv_appName);
        appName.setText(curset.getName()+"  Settings");

        TipsText = findViewById(R.id.et_content);
        boolean temp = MainActivity.AppList.get(Select.curPosition).getTips().equals("");
        TipsText.setText(temp?"Please have a rest!":MainActivity.AppList.get(Select.curPosition).getTips());

    }

    public void btnConfirm(View view){
        EditText H_Text = findViewById(R.id.et_limit_h);
        EditText M_Text = findViewById(R.id.et_limit_m);
        EditText S_Text = findViewById(R.id.et_limit_s);

        int h = (H_Text.getText().toString().equals(""))?0:Integer.parseInt(H_Text.getText().toString());
        int m = (M_Text.getText().toString().equals(""))?0:Integer.parseInt(M_Text.getText().toString());
        int s = (S_Text.getText().toString().equals(""))?0:Integer.parseInt(S_Text.getText().toString());

        int maxtime = Integer.parseInt(H_Text.getText().toString()) * 3600 +
                Integer.parseInt(M_Text.getText().toString()) * 60 +
                Integer.parseInt(S_Text.getText().toString());

        if(m>=60 || s>=60 || maxtime==0)Toast.makeText(getApplicationContext(), "Unapt-Input!", Toast.LENGTH_SHORT).show();
        else {
            MainActivity.AppList.get(Select.curPosition).setSelected(true);
            MainActivity.AppList.get(Select.curPosition).setLimiTime(maxtime);
            MainActivity.AppList.get(Select.curPosition).setTips(TipsText.getText().toString());
            MainActivity.AppList.get(Select.curPosition).setRuntime(0);
            storageData();
            startActivity(new Intent(Settings.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        }
    }

    public void storageData(){
        FileOutputStream fos = null;
        MainActivity.pStoreInfo = appToInfo(MainActivity.AppList);
        try {
            fos=openFileOutput("application.txt", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonstr = gson.toJson(MainActivity.pStoreInfo);
            fos.write(jsonstr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<StoreInfo> appToInfo(List<Application> AppList){
        MainActivity.pStoreInfo.clear();
        for(int i=0;i<AppList.size();i++)
        {
            StoreInfo info = new StoreInfo(AppList.get(i).getName(),
                    AppList.get(i).getIsRun(),
                    AppList.get(i).getRuntime(),
                    AppList.get(i).getLimiTime(),
                    AppList.get(i).getTips(),
                    AppList.get(i).getSelected());
            MainActivity.pStoreInfo.add(info);
        }
        return MainActivity.pStoreInfo;
    }

}
