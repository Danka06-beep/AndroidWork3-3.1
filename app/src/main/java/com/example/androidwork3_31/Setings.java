package com.example.androidwork3_31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Setings extends AppCompatActivity implements View.OnClickListener{
    public static final int REQUSET_CODE_PERMISSION_READ_STORAGE = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            Button btnImg = findViewById(R.id.button_img);
            btnImg.setOnClickListener(this);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUSET_CODE_PERMISSION_READ_STORAGE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUSET_CODE_PERMISSION_READ_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Button btnImg = findViewById(R.id.button_img);
                    btnImg.setOnClickListener(this);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_access), Toast.LENGTH_LONG);
                    toast.show();
                }
        }
    }
    public void checkFile(String fileName) {
        if (isExternalStorageReadable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            if (file.isFile()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("filename", fileName);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_LONG);
                toast.show();
            }


        }

    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        EditText intit = findViewById(R.id.edit_img);
        String in = intit.getText().toString();
        checkFile(in);
    }
}