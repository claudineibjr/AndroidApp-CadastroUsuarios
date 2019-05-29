package com.claudineibjr.cadastrousuario.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.claudineibjr.cadastrousuario.R;
import com.claudineibjr.cadastrousuario.Services.*;


public class LoginActivity extends AppCompatActivity {

    //region Componentes de tela
    EditText txtEmail;
    EditText txtSenha;

    Switch swLembraSenha;

    Button btnEntrar;
    Button btnCadastrar;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViewElements();
        verifyIfHasUserSaved();
    }

    private void setViewElements() {
        //region Instancia Edit Text
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        //endregion

        //region Instancia Switch
        swLembraSenha = findViewById(R.id.swLembraSenha);
        //endregion

        //region Instancia botão de login e sua respectiva ação
        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    login();
                    goToMainMenu();
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        //region Instancia botão de cadastro e sua respectiva ação
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    login();
                    Toast.makeText(getApplicationContext(), "Usuário criado com sucesso", Toast.LENGTH_LONG).show();
                    goToMainMenu();
                }catch(Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion
    }

    private void verifyIfHasUserSaved() {
        // Busca recuperar informações de preferências compartilhadas
        SharedPreferences sharedPreferences = getSharedPreferences(Services.PREFERENCES_FILE, Context.MODE_PRIVATE);

        // Verifica se contém e-mail salvo, se conter, seta-o ao campo de e-mail
        if (sharedPreferences.contains("email"))
            txtEmail.setText(sharedPreferences.getString("email", ""));

        // Verifica se contém senha salva, se conter, seta-a ao campo de senha e realiza o login
        if (sharedPreferences.contains("password")){
            txtSenha.setText(sharedPreferences.getString("password", ""));

            // Tenta realizar o login com base nas informações que foram salvas anteriormente
            try{
                login();
                Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                goToMainMenu();
            }catch(Exception ex){
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean login() throws IllegalArgumentException {
        String email, password;

        // Recupera o e-mail e senha preenchidos anteriormente
        email = txtEmail.getText().toString();
        password = txtSenha.getText().toString();

        // Verifica se algum dos campos está sem preenchimento
        if (Services.emptyField(txtEmail) && Services.emptyField(txtSenha))
            throw new IllegalArgumentException("Digite o e-mail e a senha");
        else if (Services.emptyField(txtEmail))
            throw new IllegalArgumentException("Digite o e-mail");
        else if (Services.emptyField(txtSenha))
            throw new IllegalArgumentException("Digite a senha");

        // Salva o e-mail digitado nas preferências compartilhadas
        savePreferences("email", email);

        // Verifica se o usuário quer salvar a senha e se sim, salva a senha digitada nas prefer
        if (swLembraSenha.isChecked()){
            savePreferences("password", password);
        }

        return true;
    }

    private void savePreferences(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(Services.PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    private void goToMainMenu(){
        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra("email", txtEmail.getText().toString());
        startActivity(intent);
    }
}