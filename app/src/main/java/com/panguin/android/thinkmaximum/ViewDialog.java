package com.panguin.android.thinkmaximum;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;

import static com.panguin.android.thinkmaximum.remote.ApiUtils.BASE_URL;

public class ViewDialog {


        public void showDialog(Activity activity, String msg){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

//            AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
//            alertDialog.setTitle("Error");
//            alertDialog.setMessage("cant connect");
//            alertDialog.setButton(" Try Again", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
//
//
//
//            alertDialog.show();

        }
    public boolean isConnectedToServer(Activity activity) {
        try{
            URL myUrl = new URL(BASE_URL);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(2000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            Log.e("net","nousdbpsjb");
            Log.e("net",e.toString());

            showDialog(activity,"Please Connect to Server");
            return false;
        }
    }

}
