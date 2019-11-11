package br.com.jsf.controle;

import br.com.jsf.dao.ClienteDao;
import br.com.jsf.dao.ClienteDaoImpl;
import br.com.jsf.dao.HibernateUtil;
import br.com.jsf.entidade.Categoria;

import br.com.jsf.entidade.Cliente;
import static br.com.jsf.entidade.Cliente_.categoria;
import br.com.jsf.entidade.Fornecedor;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "clienteC")
@ViewScoped
public class ClienteControle {

    private Cliente cliente;
    private Categoria categoria;
    private ClienteDao clienteDao;
    private Session session;
    private DataModel<Cliente> clientes;
    private boolean mostra_toolbar = true;

    @PostConstruct
    public void iniciar() {
        cliente = new Cliente();
        clienteDao =  new ClienteDaoImpl();
    }

    public String salvar() {

        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            clienteDao.salvarOuAlterar(cliente, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            cliente = new Cliente();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }

    public void pesquisarCliente(){
        try 
           {
            clienteDao = new ClienteDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Cliente> listClientes = clienteDao.pesquisarPorNome(cliente.getNome(), session);
            clientes = new ListDataModel(listClientes);
            
        } catch (HibernateException e) {
            System.out.println("Erro  ao pesquisar por nome" + e.getMessage());
        }
    }

     public void novo(){
            mostra_toolbar = !mostra_toolbar;
        }
       public void carregaCliente() {
            cliente = clientes.getRowData();
        categoria = cliente.getCliente();
        mostra_toolbar = !mostra_toolbar;
    }

     public void excluir() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        cliente = clientes.getRowData();
        try {
            session = HibernateUtil.abrirSessao();
            clienteDao = new ClienteDaoImpl();
            clienteDao.excluir(cliente, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluido com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            clientes = null;
            cliente = new Cliente();
        } catch (Exception e) {
            System.out.println("Erro ao excluir " + e.getLocalizedMessage());
        } finally {
            session.close();

        }
        
    }

//    getters e setters eles sao utilizados na interface grafica
    
    public DataModel<Cliente> getClientes() {
        return clientes;
    }
    public Categoria getCategoria() {
        if(categoria == null){
            categoria = new Categoria();
        }
            return categoria;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }
    

    
    
}
