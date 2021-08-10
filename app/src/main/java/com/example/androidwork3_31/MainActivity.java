package com.example.androidwork3_31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ingenerButton = findViewById(R.id.ingenerButton);
        FrameLayout ingenerPanel = findViewById(R.id.IngenerPanel);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        ingenerPanel.setVisibility(View.GONE);
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("filename");
        if (fileName != null) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            ImageView im = findViewById(R.id.background_img);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            im.setImageBitmap(bitmap);
        }

            ingenerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ingenerPanel.getVisibility() == View.VISIBLE) {
                        ingenerPanel.setVisibility(View.INVISIBLE);
                    } else {
                        ingenerPanel.setVisibility(View.VISIBLE);
                    }
                }
            });
            resultField = (TextView) findViewById(R.id.resultField);
            numberField = (EditText) findViewById(R.id.numberField);
            operationField = (TextView) findViewById(R.id.operationField);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
        @Override
        protected void onSaveInstanceState (Bundle outState){
            outState.putString("OPERATION", lastOperation);
            if (operand != null)
                outState.putDouble("OPERAND", operand);
            super.onSaveInstanceState(outState);
        }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, Setings.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        protected void onRestoreInstanceState (Bundle savedInstanceState){
            super.onRestoreInstanceState(savedInstanceState);
            lastOperation = savedInstanceState.getString("OPERATION");
            operand = savedInstanceState.getDouble("OPERAND");
            resultField.setText(operand.toString());
            operationField.setText(lastOperation);
        }
        public void onNumberClick (View view){
            Button button = (Button) view;
            numberField.append(button.getText());
            if (lastOperation.equals("=") && operand != null) {
                operand = null;
            }
        }
        public void onOperationClick (View view){
            Button button = (Button) view;
            String op = button.getText().toString();
            String number = numberField.getText().toString();
            if (number.length() > 0) {
                number = number.replace(',', '.');
                try {
                    performOperation(Double.valueOf(number), op);
                } catch (NumberFormatException ex) {
                    numberField.setText("");
                }
            }
            lastOperation = op;
            operationField.setText(lastOperation);
        }

        private void performOperation (Double number, String operation){
            if (operand == null) {
                operand = number;
            } else {
                if (lastOperation.equals("=")) {
                    lastOperation = operation;
                }
                switch (lastOperation) {
                    case "=":
                        operand = number;
                        break;
                    case "/":
                        if (number == 0) {
                            operand = 0.0;
                        } else {
                            operand /= number;
                        }
                        break;
                    case "*":
                        operand *= number;
                        break;
                    case "+":
                        operand += number;
                        break;
                    case "-":
                        operand -= number;
                        break;
                }
            }
            resultField.setText(operand.toString().replace('.', ','));
            numberField.setText("");
        }
        public void onClickButtonDelite (View view){
            String word = numberField.getText().toString();
            int input = word.length();
            if (input > 0) {
                numberField.setText(word.substring(0, input - 1));
            }
        }
        public void onCkickButtonClear (View view){
            resultField.setText(" ");
            operationField.setText(" ");
            numberField.setText(" ");
        }

    }

