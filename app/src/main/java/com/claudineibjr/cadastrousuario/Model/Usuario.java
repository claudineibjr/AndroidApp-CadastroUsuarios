package com.claudineibjr.cadastrousuario.Model;

import org.json.JSONObject;

public class Usuario {

    private String email;
    private String password;
    private String nome;
    private String turma;
    private long nusp;

    public Usuario(){}

    public Usuario(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Usuario(String email, String password, String nome, long nusp, String turma){
        this(email, password);
        this.nome = nome;
        this.nusp = nusp;
        this.turma = turma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public long getNusp() {
        return nusp;
    }

    public void setNusp(long nusp) {
        this.nusp = nusp;
    }
}