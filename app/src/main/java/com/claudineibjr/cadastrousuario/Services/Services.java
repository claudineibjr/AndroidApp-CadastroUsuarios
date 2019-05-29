package com.claudineibjr.cadastrousuario.Services;

import android.widget.EditText;

import com.claudineibjr.cadastrousuario.Model.*;

public class Services {

    public static final String PREFERENCES_FILE = "CadastroAlunosUSP";

    public static Usuario getUsuarioByEmail(String email){
        return getUsuarioFake(email);
    }

    public static boolean emptyField(EditText editText){
        return editText.getText().toString().length() == 0;
    }

    private static Usuario getUsuarioFake(String email){
        String nome;
        if (email.contains("@"))
            nome = email.substring(0, email.indexOf("@")) + " da Silva";
        else
            nome = email;

        return new Usuario(email, "", nome, 1086541, "018");
    }

}