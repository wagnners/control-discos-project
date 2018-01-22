/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.core;

import java.util.List;
import model.Musica;

/**
 *
 * @author Wagner
 */
public interface MusicaDao {
    
      public Musica salvar(Musica musica);

    public void excluir(Musica musica);

    public Musica pesquisar(int id);

    public List<Musica> listar();
    
 public List<Musica> listarPorId(int idDisco);

    public void atualizar(Musica musica);
    
}
