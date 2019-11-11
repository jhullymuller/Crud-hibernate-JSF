package br.com.jsf.controle;


import br.com.jsf.dao.CategoriaDao;
import br.com.jsf.dao.CategoriaDaoImpl;
import br.com.jsf.dao.HibernateUtil;
import br.com.jsf.entidade.Categoria;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "categoriaC")
@ViewScoped
public class CategoriaControle {

    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private Session session;
    private boolean mostra_toolbar = true;
    private ListDataModel categorias;

    @PostConstruct
    public void iniciar() {
        categoria = new Categoria();
        categoriaDao =  new CategoriaDaoImpl();
    }

    public String salvar() {

        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            categoriaDao.salvarOuAlterar(categoria, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            categoria = new Categoria();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }

    public void pesquisarCategorias(){
        try 
           {
            categoriaDao = new CategoriaDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Categoria> listCategorias = categoriaDao.pesquisarPorNome(categoria.getDescricao(), session);
            categorias = new ListDataModel(listCategorias);
            
        } catch (HibernateException e) {
            System.out.println("Erro  ao pesquisar por nome" + e.getMessage());
        }
    }

     public void novo(){
            mostra_toolbar = !mostra_toolbar;
        }
       public void carregaCategoria() {
        mostra_toolbar = !mostra_toolbar;
    }

     public void excluir() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        categoria = (Categoria) categorias.getRowData();
        try {
            session = HibernateUtil.abrirSessao();
            categoriaDao = new CategoriaDaoImpl();
            categoriaDao .excluir(categoria, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            categoria = null;
            categoria = new Categoria();
        } catch (Exception e) {
            System.out.println("Erro ao excluir " + e.getLocalizedMessage());
        } finally {
            session.close();

        }
        
    }

//    getters e setters eles sao utilizados na interface grafica

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ListDataModel getCategorias() {
        return categorias;
    }

    public void setCategorias(ListDataModel categorias) {
        this.categorias = categorias;
    }

  
    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }
    

    
    
}
