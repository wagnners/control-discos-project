/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Musica;
import model.core.MusicaDao;

/**
 *
 * @author Wagner
 */
//Classe que implementa os métodos CRUDS da interface DiscoDao (responável por salvar no banco)
public class JPAMusicaDao implements MusicaDao {
    //Criando o gerenciador de entidades

    public EntityManager getEM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ManagerDiscsProjectPU");
        return factory.createEntityManager();
    }

    @Override
    public Musica salvar(Musica musica) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(musica);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return musica;
    }

    @Override
    public void excluir(Musica musica) {
        EntityManager em = null;
        try {
            em = getEM();
            em.getTransaction().begin();
            musica = em.merge(musica);
            em.remove(musica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Musica pesquisar(int id) {
        EntityManager em = null;
        try {
            em = getEM();
            return em.find(Musica.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    @Override
    public List<Musica> listar() {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select m from Musica m");
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
       @Override
    public List<Musica> listarPorId(int idDisco) {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select m from Musica m where m.disco.disco_id = :disco").setParameter("disco", idDisco);
      
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    } 

    @Override
    public void atualizar(Musica musica) {
        EntityManager em = null;
        try {
            em = getEM();
            em.getTransaction().begin();
            em.merge(musica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
