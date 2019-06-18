package com.colombelli.myapplication;

import java.io.Serializable;

/**
 * Esta classe tem como único objetivo estruturar o que é o objeto do tipo EVENTO, usado para
 * representar os eventos que o usuário tem marcados em sua AgendaActivities, e como ele deve se comportar.
 * Apenas a listagem de atributos e funções de get() e set(). "Implements Serializable" para
 * serializar o conteúdo de cada objeto do tipo Evento, evitando problemas de serialização ao
 * enviar objetos deste novo tipo de uma Activity para outra.
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */

public class Evento implements Serializable {

    private int id;
    private String nome;
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

    public String getNome() {
        return nome;
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

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", ano=" + ano +
                ", hora='" + hora + '\'' +
                ", tipo='" + tipo + '\'' +
                ", anotacoes='" + anotacoes + '\'' +
                '}';
    }
}
