package br.com.jsf.controle;

import br.com.jsf.dao.FornecedorDao;
import br.com.jsf.dao.FornecedorDaoImpl;
import br.com.jsf.dao.HibernateUtil;
import br.com.jsf.dao.ProdutoDao;
import br.com.jsf.dao.ProdutoDaoImpl;
import br.com.jsf.entidade.Fornecedor;
import br.com.jsf.entidade.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "produtoC")
@ViewScoped
public class ProdutoControle {

    private Produto produto;
    private Fornecedor fornecedor;
    private ProdutoDao produtoDao;
    private Session session;
    private DataModel<Produto> produtos;
    private boolean mostra_toolbar = true;
    private List<SelectItem> fornecedoresCombo;

    @PostConstruct
    public void iniciar() {
        produto = new Produto();
        produtoDao = new ProdutoDaoImpl();
    }

    public void carregarFornecedorCombobox(){
        fornecedoresCombo = new ArrayList();
        
        try {
            FornecedorDao fornecedorDao = new FornecedorDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Fornecedor> fornecedores = fornecedorDao.listarTodo(session);
            session.close();
            for (Fornecedor forne : fornecedores) {
                fornecedoresCombo.add(new SelectItem(forne.getId(), forne.getNome()));
            }
            
        } 
        catch (Exception e) {
            System.out.println("Erro ao carregar combobox" + e.getMessage());
        }
    }
    
    
    public String salvar() {

        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            produto.setFornecedor(fornecedor);
            produtoDao.salvarOuAlterar(produto, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            produto = new Produto();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }

    public void pesquisarProduto() {
        try {
            produtoDao = new ProdutoDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Produto> listProdutos = produtoDao.pesquisarPorNome(produto.getNome(), session);
            produtos = new ListDataModel(listProdutos);

        } catch (HibernateException e) {
            System.out.println("Erro  ao pesquisar por nome" + e.getMessage());
        }
    }

    public void carregaProduto() {
        carregarFornecedorCombobox();
        produto = produtos.getRowData();
        fornecedor = produto.getFornecedor();
        mostra_toolbar = !mostra_toolbar;
    }

    public void excluir() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        produto = produtos.getRowData();
        try {
            session = HibernateUtil.abrirSessao();
            produtoDao = new ProdutoDaoImpl();
            produtoDao.excluir(produto, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            produtos = null;
            produto = new Produto();
        } catch (Exception e) {
            System.out.println("Erro ao excluir " + e.getLocalizedMessage());
        } finally {
            session.close();

        }
        
    }
    public void novo(){
            carregarFornecedorCombobox();
            mostra_toolbar = !mostra_toolbar;
        }

//    getters e setters eles sao utilizados na interface grafica
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public DataModel getProdutos() {
        return produtos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public Fornecedor getFornecedor() {
        if(fornecedor == null){
            fornecedor = new Fornecedor();
        }
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<SelectItem> getFornecedoresCombo() {
        return fornecedoresCombo;
    }

    public void setFornecedoresCombo(List<SelectItem> fornecedoresCombo) {
        this.fornecedoresCombo = fornecedoresCombo;
    }

}

