/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

/**
 *
 * @author paulo.castro
 */
import com.tcc.bo.CargoBo;
import com.tcc.bo.ContatoBo;
import com.tcc.bo.EnderecoBo;
import com.tcc.bo.LogradouroBo;
import com.tcc.bo.PessoaBo;
import com.tcc.model.CntContato;
import com.tcc.model.CrgCargo;
import com.tcc.model.EndEndereco;
import com.tcc.model.FunFuncionario;
import com.tcc.model.LgrLogradouro;
import com.tcc.model.PssPessoa;
import com.tcc.util.HibernateUtil;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.primefaces.context.RequestContext;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class CargoMb extends BaseMb implements Serializable {

    private CrgCargo crg = new CrgCargo();
    private ModoTela modo = new ModoTela();
    private CargoBo cargoBo = new CargoBo();
    private List<CrgCargo> crgList = new ArrayList<CrgCargo>();
    private String descricao;

    public CargoMb() {
    }

    public void cancelar() {
        limpar();
    }

    public void limpar() {
        crg = new CrgCargo();
        descricao = "";
        recarregarLista();
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void novo() {
        crg = new CrgCargo();
        descricao = "";
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void validarDuplicidade() throws Exception {
        List<CrgCargo> mesmaDescricao = cargoBo.carrgar(descricao);
        if (mesmaDescricao != null && !mesmaDescricao.isEmpty()) {
            if (crg != null && crg.getCrgId() != null) {
                boolean jaUsuada = false;
                for (CrgCargo c : mesmaDescricao) {
                    if ((!c.getCrgId().equals(crg.getCrgId())) && (c.getCrgDescricao().trim().equalsIgnoreCase(crg.getCrgDescricao().trim()))) {
                        jaUsuada = true;
                        break;
                    }
                }
                if (jaUsuada) {
                    throw new Exception("Descrição já usada para outro cargo");
                }
            } else {
                throw new Exception("Descrição já usada para outro cargo");
            }
        }
    }

    public void salvar() {
        try {
            if ((descricao != null) && (!descricao.trim().isEmpty()) && (descricao.trim().length() >= 3)) {
                crg.setCrgDescricao(descricao);
                validarDuplicidade();
                if (crg.getCrgId() != null) {
                    cargoBo.alterar(crg);
                } else {
                    cargoBo.inserir(crg);
                }
                limpar();
            } else {
                throw new Exception("Insira Pelo menos 3 letras na descrição do Cargo!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    @PostConstruct
    public void recarregarLista() {
        try {
            crgList = new ArrayList<CrgCargo>();
            crgList = cargoBo.listarTodos();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisarCargo() {
        try {
            crgList = new ArrayList<CrgCargo>();
            if ((descricao != null) && (!descricao.trim().isEmpty()) && (descricao.trim().length() >= 2)) {
                crgList = new ArrayList<CrgCargo>();
                crgList = cargoBo.carrgar(descricao);
            } else {
                throw new Exception("Insira Pelo menos 2 letras para busca!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void excluir() {
        try {
            if (crg != null && crg.getCrgId() != null) {
                if (!cargoBo.estaSendoUsado(crg)) {
                    cargoBo.excluir(crg);
                    limpar();
                } else {
                    throw new Exception("Não é possível excluir, existem funcionários que o utilizam");
                }
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    /**
     * Getters and Setters
     */
    public CrgCargo getCrg() {
        return crg;
    }

    public void setCrg(CrgCargo crg) {
        this.crg = crg;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public List<CrgCargo> getCrgList() {
        return crgList;
    }

    public void setCrgList(List<CrgCargo> crgList) {
        this.crgList = crgList;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
