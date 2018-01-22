/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.core;

import java.util.List;
import model.Disco;

/**
 *
 * @author Wagner
 */
public interface DiscoDao {

    public Disco salvar(Disco disco);

    public void excluir(Disco disco);

    public Disco pesquisar(int id);

    public List<Disco> listar();

    public List<Disco> listarDiscoPorId(int id);

    public List<Disco> listarDiscoPorAno(int ano);

    public List<Disco> listarDiscoPorArtista(String artista);

    public List<Disco> listarOrdenadaPorNome();

    public void atualizar(Disco disco);

}
