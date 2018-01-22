/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Wagner
 */
@Entity
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int musica_id;
    private String nome;
    private String duracao;
    @ManyToOne
    @JoinColumn(name = "disco_id")
    private Disco disco;

    public Musica() {
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public int getMusica_id() {
        return musica_id;
    }

    public void setMusica_id(int musica_id) {
        this.musica_id = musica_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
    
    

}
