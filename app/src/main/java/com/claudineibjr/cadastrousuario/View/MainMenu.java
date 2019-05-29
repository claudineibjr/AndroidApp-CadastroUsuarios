package com.claudineibjr.cadastrousuario.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.claudineibjr.cadastrousuario.Model.Usuario;
import com.claudineibjr.cadastrousuario.R;
import com.claudineibjr.cadastrousuario.Services.Services;

import static java.lang.String.valueOf;

public class MainMenu extends AppCompatActivity {

    // Objeto que representa o usuário que fez o login
    Usuario usuario;

    // Variável booleana utilizada para indicar quanto as informações do usuário já foram carregadas na tela
    //  (isso serve para evitar que o evento textWatcher seja acionado indevidamente)
    boolean infoUserSetted = false;

    //region Componentes de tela
    EditText txtEmail;
    EditText txtNome;
    EditText txtNUSP;
    EditText txtTurma;

    Button btnCancelar;
    Button btnSalvar;
    Button btnSair;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setViewElements();

        getExtrasFromPreviousActivity();
    }

    private void getExtrasFromPreviousActivity() {
        // Função responsável por recuperar as informações que vieram da tela anterior
        Bundle extras = getIntent().getExtras();

        // Verifica se tem informações da tela anterior
        if (extras != null) {

            // Verifica se o e-mail foi passado da tela anterior para a tela atual
            if (extras.containsKey("email"))
                usuario = Services.getUsuarioByEmail(extras.getString("email"));
        }

        setUserInfoInView();
    }

    private void setUserInfoInView() {
        // Função responsável por colocar as informações do usuário em tela
        txtEmail.setText(usuario.getEmail());
        txtNome.setText(usuario.getNome());
        txtNUSP.setText(valueOf(usuario.getNusp()));
        txtTurma.setText(usuario.getTurma());

        infoUserSetted = true;
    }

    private void setViewElements() {
        txtEmail = findViewById(R.id.txtEmail); txtEmail.addTextChangedListener(textWatcher());
        txtNome = findViewById(R.id.txtNome);   txtNome.addTextChangedListener(textWatcher());
        txtNUSP = findViewById(R.id.txtNUSP);   txtNUSP.addTextChangedListener(textWatcher());
        txtTurma = findViewById(R.id.txtTurma); txtTurma.addTextChangedListener(textWatcher());

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserInfoInView();
            }
        });

        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Services.emptyField(txtEmail) || Services.emptyField(txtNome) || Services.emptyField(txtNUSP) || Services.emptyField(txtTurma)){
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show();
                    return;
                }

                // Recupera as informações do usuário que estão em tela
                String email, nome, senha, turma;
                email = txtEmail.getText().toString();
                nome = txtNome.getText().toString();
                senha = usuario.getPassword();
                turma = txtTurma.getText().toString();

                long nusp;
                nusp = Long.parseLong(txtNUSP.getText().toString());

                Usuario newUsuario = new Usuario(email, senha, nome, nusp, turma);
                usuario = newUsuario;
                setUserInfoInView();
                Toast.makeText(getApplicationContext(), "Informações atualizadas", Toast.LENGTH_LONG).show();

                // Desabilita os botões de salvar e cancelar, uma vez que as informações já foram salvas
                btnCancelar.setEnabled(false);
                btnSalvar.setEnabled(false);
            }
        });

        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(Services.PREFERENCES_FILE, Context.MODE_PRIVATE);
                if (sharedPreferences.contains("password")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("password");
                    editor.apply();
                }

                finish();
            }
        });

    }

    private TextWatcher textWatcher(){
        // Função responsável por capturar as alterações no EditText
        return new TextWatcher() {
            @Override   public void beforeTextChanged(CharSequence s, int start, int count, int after) {    }
            @Override   public void afterTextChanged(Editable s) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Verifica se as informações do usuário já foram setadas (caso não tenha sido, esta chamada ao evento onTextChanged é ignorada)
                if (infoUserSetted) {

                    // Se as informações do usuário foram alteradas, habilita os botões de salvar e cancelar alterações
                    btnCancelar.setEnabled(true);
                    btnSalvar.setEnabled(true);
                }
            }
        };
    }

}