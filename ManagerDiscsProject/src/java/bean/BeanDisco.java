/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.print.Collation;
import javax.faces.application.FacesMessage;
import model.Disco;
import model.Musica;
import model.core.DiscoDao;
import model.core.JPAFactory;
import model.core.MusicaDao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.tomcat.util.http.fileupload.RequestContext;

/**
 *
 * @author Wagner
 */
@SessionScoped
@ManagedBean(name = "beanDisco")
public class BeanDisco implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idDiscoAtual;
    private Musica musica;
    private Musica musicaSelecionada; //musica selecionada ao clicar para editar/excluir
    private Disco disco;
    private Disco discoSelecionado; //disco selecionado
    private DiscoDao daoDisco;
    private MusicaDao daoMusica;
    private List<Musica> musicas; //musicas Salvas no banco
    private List<Musica> musicasAtualTemp; //Musicas temporarias ao cadastrar
    private List<Disco> listaDeDiscos;
    private String palavraPesquisa;    

    public BeanDisco() {
        musica = new Musica();
        disco = new Disco();
        daoDisco = JPAFactory.getDiscoDao();
        daoMusica = JPAFactory.getMusicaDao();
        musicas = new ArrayList<>();
        musicasAtualTemp = new ArrayList<>();
        listaDeDiscos = daoDisco.listar();
      

    }
   

    
    //chamado quando persistido um disco no banco chamando o listar da classe daoDisco, onde é feita a persistência dos dados
    public void atualizarListaDiscos() {
        listaDeDiscos = daoDisco.listar();
    }

    
    public String salvarDisco() {
        disco.setMusicas(musicasAtualTemp);
        daoDisco.salvar(disco);
        disco = new Disco();
        musicasAtualTemp = new ArrayList<>();
        atualizarListaDiscos();
        return "index.jsf";
    }

    
    public String atualizarMusica(Musica musica) {
        String novoNome = "";
        String novaDuracao = "";
        FacesMessage message;
        if (musica == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "É necessário selecionar uma música");
        //verifica se o elemento editado está persistido no banco ou está na lista de musicas temporarias.
        //caso esteja, faz a alteração apenas no array e limpa o objeto Musica
        } else if (musica.getMusica_id() == 0) {
            novoNome = musica.getNome();
            novaDuracao = musica.getDuracao();
           
            for (int i = 0; i < musicasAtualTemp.size(); i++) {
                if (musicasAtualTemp.get(i).getNome() == this.musica.getNome()) {
                    musicasAtualTemp.remove(i);
                    Musica m = new Musica();
                    m.setDisco(disco);
                    m.setDuracao(novaDuracao);
                    m.setNome(novoNome);
                    musicasAtualTemp.add(m);
                    musica = new Musica();
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Não foi possível editar sua música");
                }
            }
          //caso musica esteja persistida no banco, atualiza na classe de persistência daoMúsica
        } else if (musica.getMusica_id() > 0) {
            daoMusica.atualizar(musica);
            musica = new Musica();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Música alterada");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Não foi possível editar sua música");
        }
        return "cadastroDiscos.jsf";
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public List<Disco> getListaDiscos() {
        return listaDeDiscos;
    }

    public String editarMusicaArray() {
        return "cadastroDiscos.jsf";
    }

    //se música estiver no banco, apaga pela classe daoMusica, pelo contrário apaga apenas no array
    public String excluirMusica(Musica musica) {
        if (musica.getMusica_id() != 0) {
            daoMusica.excluir(musica);
        } else {          
            for (int i = 0; i < musicasAtualTemp.size(); i++) {
                if (musica.getNome().equalsIgnoreCase(musicasAtualTemp.get(i).getNome())) {
                    musicasAtualTemp.remove(i);
                   
                }
            }
        }
        this.musica = new Musica();
        return "cadastroDiscos.jsf";
    }

//Método responsável por adicionar as músicas em array antes de salvar o disco
//referencia objeto Disco em Musica e adiciona músicas em um array temporario de musicas atuais
    public String adicionarMusicasArray() {
        FacesMessage message;
        try {
            musica.setDisco(disco);
            musicasAtualTemp.add(musica);
            musica = new Musica();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Música inserida!");
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao inserir!", "Música não inserida");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "cadastroDiscos.jsf";
    }

    //ao passar disco por parametro para a classe daoDisco excluir, atualiza a lista de discos
    public String excluirDisco(Disco disco) {
        daoDisco.excluir(disco);
        atualizarListaDiscos();
        return "index.jsf";
    }

  
   //referenciado musicas temporarias no disco para que seja feito o merge no daoDisco caso exista uma alteração
    public String atualizarDisco() {
        disco.setMusicas(musicasAtualTemp);
        daoDisco.atualizar(disco);
        musicasAtualTemp = new ArrayList<>();
        atualizarListaDiscos();

        return "index.jsf";
    }

    //método responável por pegar as músicas a serem listadas ao adicionar um disco
    public List<Musica> getListMusicas() {
        //se for disco_id > 0 e lista atual vazia, retorna lista de musicas do banco
        if ((disco.getDisco_id() > 0) && (musicasAtualTemp.size() == 0)) {
           
            musicasAtualTemp = daoMusica.listarPorId(disco.getDisco_id());
        } else {
             //caso lista não esteja vazia, removeall garante que não duplique musicas
            musicasAtualTemp.removeAll(daoMusica.listarPorId(disco.getDisco_id()));
        }
        return musicasAtualTemp;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    //caso usuário não informe valor ao pesquisar por ano, carrega lista inteira de discos, pelo contrário só os discos filtrados
    public String listarPorAno() {
        if (disco.getAnoLancamento() != 0) {
            listaDeDiscos = daoDisco.listarDiscoPorAno(disco.getAnoLancamento());
        } else {
            listaDeDiscos = daoDisco.listar();
        }
        
        return "index.jsf";
    }
    
    //mesmo procedimento do listarPorAno
    public String listarPorArtista(){
         if (disco.getArtista() != null) {
            listaDeDiscos = daoDisco.listarDiscoPorArtista(disco.getArtista());
        } else {
            listaDeDiscos = daoDisco.listar();
        }
        return "index.jsf";
    }

    public void setaMusicaParaEditar(Musica m) {
        musica = m;
    }

    public String setaDiscoParaEditar(Disco disco) {
        this.disco = disco;
        return "cadastroDiscos.jsf";
    }


    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public String irPaginaCadastro(){
        disco = new Disco();
        musicasAtualTemp = new ArrayList<>();
        return "cadastroDiscos.jsf";
    }
    
    
//    public String limparCadastroDisco(){
//        if(disco.get)
//        return "cadastroDisco.jsf";
//    }

    public String getPalavraPesquisa() {
        return palavraPesquisa;
    }

    public void setPalavraPesquisa(String palavraPesquisa) {
        this.palavraPesquisa = palavraPesquisa;
    }

    public int getQuantidadeMusica(int id) {
       return daoMusica.listarPorId(id).size();
        
    }

    public String ordenarPorNome(){
        listaDeDiscos = daoDisco.listarOrdenadaPorNome();
        return "cadastroDiscos.jsf";
    }
  
    
    
}
