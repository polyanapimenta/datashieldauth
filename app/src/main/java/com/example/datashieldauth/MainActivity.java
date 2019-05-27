package com.example.datashieldauth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private Button button;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // components from main.xml
        button = (Button) findViewById(R.id.buttonSignin);
        result = (EditText) findViewById(R.id.textInputUserPhone);

        // add button listener
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // get dialog_signin.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog_signin, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set dialog_signin.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText codeInput = (EditText) promptsView.findViewById(R.id.textDialogCodeInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Validar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        if (verifySMScode(codeInput)) {
                                            Toast.makeText(getApplicationContext(), "Código Incorreto. Insira os 5 Dígitos Recebido por SMS", Toast.LENGTH_LONG).show();
                                        } else {
                                            startActivity(new Intent(MainActivity.this, TokenActivity.class));
                                        }
                                    }
                                })
                        .setNegativeButton("Reenviar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                if (verifyPhoneNumber(result)) {
                    Toast.makeText(getApplicationContext(), "Celular Inválido. Insira no Mínimo 9 Dígitos.", Toast.LENGTH_LONG).show();

                } else {
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            }
        });
    }

    private boolean verifySMScode(EditText codeInput) {
        String SMSCode = codeInput.getText().toString().trim();
        if (SMSCode.length() < 5 || SMSCode.length() > 5)
            return true;
        return false;
    }

    private boolean verifyPhoneNumber(EditText phoneInput) {
        String phone = phoneInput.getText().toString().trim();
        if (phone.length() < 9 || phone.length() > 13)
            return true;
        return false;
    }
}