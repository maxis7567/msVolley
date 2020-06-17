package com.maxis7567.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import com.aditya.filebrowser.Constants;

import com.aditya.filebrowser.FileChooser;

import com.maxis7567.msvolley.LocalError;

import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.ResponseError;
import com.maxis7567.msvolley.uploder.Test;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent i2 = new Intent(getApplicationContext(), FileChooser.class);
//        i2.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.SINGLE_SELECTION.ordinal());
//        startActivityForResult(i2, 200);
        Api.getBrandModel(this, new Response<String>() {
            @Override
            public void respond(String respond) {

            }
        }, new ResponseError<String>() {
            @Override
            public void error(RespondError<String> error) {

            }
        }, new LocalError() {
            @Override
            public void error(String message) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && data!=null) {
            if (resultCode == RESULT_OK) {
                Uri file = data.getData();
                Test.uploadMultipart(this,file);
            }
        }

    }
   
}
