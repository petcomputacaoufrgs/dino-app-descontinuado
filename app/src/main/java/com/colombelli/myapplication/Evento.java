package com.colombelli.myapplication;

/**
 * Esta classe tem como único objetivo estruturar o que é o objeto do tipo EVENTO, usado para
 * representar os eventos que o usuário tem marcados em sua Agenda, e como ele deve se comportar.
 * Apenas a listagem de atributos e funções de get() e set().
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */

public class Evento {

    private int id;
    private String data;
    private int dia;
    private int mes;        //Alguns atributos são INT para fins de facilitar comparação.
    private int ano;
    private String hora;
    private String tipo;
    private String anotacoes;


    //A PARTIR DAQUI É TUDO GET E SET

    public int getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }
}
