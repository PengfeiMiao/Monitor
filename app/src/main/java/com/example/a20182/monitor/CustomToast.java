package com.example.a20182.monitor;

/*
 * Package the Special Style for the Toast
 * design the display location,content(image and text)
 */

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
    private static TextView mTextView;
    private static ImageView mImageView;

    public static void showToast(Context context, String message) {
        //load layout
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
        //init
        mTextView = (TextView) toastRoot.findViewById(R.id.message);
        mImageView = (ImageView) toastRoot.findViewById(R.id.imageView);
        //settings
        mTextView.setText(message);
        mImageView.setImageResource(R.drawable.alert);
        //Toast init
        Toast toastStart = new Toast(context);
        //get screen height
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast.Y = screen.height*1/3
        toastStart.setGravity(Gravity.TOP, 0, height / 3);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}