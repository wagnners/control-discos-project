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
import model.Disco;
import model.Musica;
import model.core.DiscoDao;

/**
 *
 * @author Wagner
 */
//Classe que implementa os métodos CRUDS da interface DiscoDao (responável por salvar no banco)
public class JPADiscoDao implements DiscoDao {

    //Criando o gerenciador de entidades
    public EntityManager getEM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ManagerDiscsProjectPU");
        return factory.createEntityManager();
    }

    //Inicia uma conexão com o banco, persiste os dados e fecha
    @Override
    public Disco salvar(Disco disco) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.persist(disco);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            em.close();
        }
        return disco;
    }

    //Inicia uma conexão com o banco, remove o objeto e fecha sessão 
    @Override
    public void excluir(Disco disco) {
        EntityManager em = null;
        try {
            em = getEM();
            em.getTransaction().begin();
            disco = em.merge(disco);
            em.remove(disco);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Disco pesquisar(int id) {
        EntityManager em = null;
        try {
            em = getEM();
            return em.find(Disco.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Disco> listar() {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select d from Disco d");
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Disco> listarDiscoPorId(int id) {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select d from Disco d where d.disco_id = :id").setParameter("id", id);

            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Disco> listarOrdenadaPorNome() {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select d from Disco d Order By d.nome");
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void atualizar(Disco disco) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(disco);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public List<Disco> listarDiscoPorAno(int ano) {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select d from Disco d where d.anoLancamento = :ano").setParameter("ano", ano);
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Disco> listarDiscoPorArtista(String artista) {
        EntityManager em = null;
        try {
            em = getEM();
            Query consulta = em.createQuery("select d from Disco d where d.artista LIKE :artista").setParameter("artista", "%" + artista + "%");
            return consulta.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

   
}
