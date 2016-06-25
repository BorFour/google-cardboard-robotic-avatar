package com.example.borch.surfaceviewcarboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by borch on 20/06/16.
 */
public class FormActivity extends Activity {

    AddressManager manager = AddressManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form);
        updateValues(null);
    }

    public void updateValues(View button) {

        final EditText cameraLeftField =
                (EditText) findViewById(R.id.cameraLeft);
        String cameraLeft = cameraLeftField.getText().toString();

        final EditText cameraRightField =
                (EditText) findViewById(R.id.cameraRight);
        String cameraRight = cameraRightField.getText().toString();

        final EditText controlServerAddressField =
                (EditText) findViewById(R.id.controlServerAddress);
        String controlServerAddress = controlServerAddressField.getText().toString();


        final EditText controlServerPortField =
                (EditText) findViewById(R.id.controlServerPort);
        String controlServerPort = controlServerPortField.getText().toString();


        manager.setCameraLeft(cameraLeft);
        manager.setCameraRight(cameraRight);
        manager.setControlServerAddress(controlServerAddress);
        manager.setControlServerPort(Integer.parseInt(controlServerPort));

    }


    public void nextActivity(View button) {
        Intent intent = new Intent(this, MainActivity.class);
        manager.setSend(true);
        finish();
        startActivity(intent);
    }


}
