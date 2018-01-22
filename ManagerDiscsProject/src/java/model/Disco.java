/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Wagner
 */
@Entity
public class Disco {
    @Id
    @GeneratedValue
    @Column(name="disco_id")
    private int disco_id;
    private String nome;
    private int anoLancamento;
    private String artista;
    @OneToMany(mappedBy = "disco", targetEntity = Musica.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "disco_id")
    List<Musica>  musicas;

    public Disco() {
        musicas = new ArrayList<>();
    }
    public void addMusica(Musica m){      
        musicas.add(m);
    }
        
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDisco_id() {
        return disco_id;
    }

    public void setDisco_id(int disco_id) {
        this.disco_id = disco_id;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

   
    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
    
    
    
    
    
}
