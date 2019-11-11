package br.com.jsf.controle;


import br.com.jsf.dao.FornecedorDao;
import br.com.jsf.dao.FornecedorDaoImpl;
import br.com.jsf.dao.HibernateUtil;

import br.com.jsf.entidade.Fornecedor;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "fornecedorC")
@ViewScoped
public class FornecedorControle {

    private Fornecedor fornecedor;
    private FornecedorDao fornecedorDao;
    private Session session;
    private boolean mostra_toolbar = true;
    private ListDataModel fornecedores;

    @PostConstruct
    public void iniciar() {
        fornecedor = new Fornecedor();
        fornecedorDao =  new FornecedorDaoImpl();
    }

    public String salvar() {

        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            fornecedorDao.salvarOuAlterar(fornecedor, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            fornecedor = new Fornecedor();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }

    public void pesquisarFornecedores(){
        try 
           {
            fornecedorDao = new FornecedorDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Fornecedor> listFornecedores = fornecedorDao.pesquisarPorNome(fornecedor.getNome(), session);
            fornecedores = new ListDataModel(listFornecedores);
            
        } catch (HibernateException e) {
            System.out.println("Erro  ao pesquisar por nome" + e.getMessage());
        }
    }

     public void novo(){
            mostra_toolbar = !mostra_toolbar;
        }
       public void carregaFornecedor() {
        mostra_toolbar = !mostra_toolbar;
    }

     public void excluir() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        fornecedor = (Fornecedor) fornecedores.getRowData();
        try {
            session = HibernateUtil.abrirSessao();
            fornecedorDao = new FornecedorDaoImpl();
            fornecedorDao.excluir(fornecedor, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            fornecedor = null;
            fornecedor = new Fornecedor();
        } catch (Exception e) {
            System.out.println("Erro ao excluir " + e.getLocalizedMessage());
        } finally {
            session.close();

        }
        
    }

//    getters e setters eles sao utilizados na interface grafica

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ListDataModel getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(ListDataModel fornecedores) {
        this.fornecedores = fornecedores;
    }
    
  
    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }
    

    
    
}
