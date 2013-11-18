/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

/**
 *
 * @author paulo.castro
 */
import com.tcc.bo.CategoriaBo;
import com.tcc.model.CatCategoria;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class CategoriaMb extends BaseMb implements Serializable {

    private CatCategoria cat = new CatCategoria();
    private ModoTela modo = new ModoTela();
    private CategoriaBo statusBo = new CategoriaBo();
    private List<CatCategoria> catList = new ArrayList<CatCategoria>();
    private String descricao;

    public CategoriaMb() {
    }

    public void cancelar() {
        limpar();
    }

    public void limpar() {
        cat = new CatCategoria();
        descricao = "";
        recarregarLista();
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void novo() {
        cat = new CatCategoria();
        descricao = "";
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void validarDuplicidade() throws Exception {
        List<CatCategoria> mesmaDescricao = statusBo.carrgar(descricao);
        if (mesmaDescricao != null && !mesmaDescricao.isEmpty()) {
            if (cat != null && cat.getCatId() != null) {
                boolean jaUsuada = false;
                for (CatCategoria c : mesmaDescricao) {
                    if ((!c.getCatId().equals(cat.getCatId())) && (cat.getCatDescricao().trim().equalsIgnoreCase(cat.getCatDescricao().trim()))) {
                        jaUsuada = true;
                        break;
                    }
                }
                if (jaUsuada) {
                    throw new Exception("Descrição já usada para outro registro");
                }
            } else {
                throw new Exception("Descrição já usada para outro registro");
            }
        }
    }

    public void salvar() {
        try {
            if ((descricao != null) && (!descricao.trim().isEmpty()) && (descricao.trim().length() >= 3)) {
                cat.setCatDescricao(descricao);
                validarDuplicidade();
                if (cat.getCatId() != null) {
                    statusBo.alterar(cat);
                } else {
                    statusBo.inserir(cat);
                }
                limpar();
            } else {
                throw new Exception("Insira Pelo menos 3 letras na descrição do Categoria!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    @PostConstruct
    public void recarregarLista() {
        try {
            catList = new ArrayList<CatCategoria>();
            catList = statusBo.listarTodos();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisarCategoria() {
        try {
            catList = new ArrayList<CatCategoria>();
            if ((descricao != null) && (!descricao.trim().isEmpty()) && (descricao.trim().length() >= 2)) {
                catList = new ArrayList<CatCategoria>();
                catList = statusBo.carrgar(descricao);
            } else {
                throw new Exception("Insira Pelo menos 2 letras para busca!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void inativar() {
        statusBo.inativar(cat);
        cat = new CatCategoria();
        modo = new ModoTela();
    }

    public void ativar() {
        statusBo.ativar(cat);
        cat = new CatCategoria();
        modo = new ModoTela();
    }

    /**
     * Getters and Setters
     */
    public CatCategoria getCat() {
        return cat;
    }

    public void setCat(CatCategoria cat) {
        this.cat = cat;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public CategoriaBo getCategoriaBo() {
        return statusBo;
    }

    public void setCategoriaBo(CategoriaBo statusBo) {
        this.statusBo = statusBo;
    }

    public List<CatCategoria> getCatList() {
        return catList;
    }

    public void setCatList(List<CatCategoria> catList) {
        this.catList = catList;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
