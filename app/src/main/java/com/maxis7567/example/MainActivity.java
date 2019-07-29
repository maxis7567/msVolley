package com.maxis7567.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Response;
import com.maxis7567.example.R;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.PhpLaravelError;
import com.maxis7567.msvolley.Respond;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.ResponseError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Api.getBrandModel(this, new Respond<String>() {
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
}
