/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.CategoriaBo;
import com.tcc.bo.ProdutoBo;
import com.tcc.model.CatCategoria;
import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.PdtProduto;
import com.tcc.model.PssPessoa;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.hibernate.sql.Select;
import org.primefaces.context.RequestContext;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class ProdutoMb extends BaseMb implements Serializable {

    private ProdutoBo produtoBo = new ProdutoBo();
    private PdtProduto produto = new PdtProduto();
    private List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
    private ModoTela modo = new ModoTela();
    private Double valor = 0.0;
    private List<SelectItem> categorias = new ArrayList<SelectItem>();

    public ProdutoMb() {
        produto = new PdtProduto();
        produto.setCat(new CatCategoria());
    }

    public void limpar() {
        produto = new PdtProduto();
        produto.setCat(new CatCategoria());
        pdtList = new ArrayList<PdtProduto>();
        valor = 0.0;
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void cancelar() {
        limpar();
    }

    public void novo() {
        produto = new PdtProduto();
        produto.setCat(new CatCategoria());
        pdtList = new ArrayList<PdtProduto>();
        valor = 0.0;
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void salvar() {
        try {
            verificarDuplicidade();
            produto.setPdtValor(valor);
            if (produto.getPdtId() != null) {
                produtoBo.alterar(produto);
                JPAUtil.addMensagemSucesso(JPAUtil.ALTERADO_COM_SUCESSO);
            } else {
                produtoBo.inserir(produto);
                JPAUtil.addMensagemSucesso(JPAUtil.INSERIDO_COM_SUCESSO);
            }
            limpar();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisar() {
        try {
            pdtList = new ArrayList<PdtProduto>();
            if (produto.getPdtNome() != null || !produto.getPdtNome().trim().isEmpty() || produto.getPdtNome().length() > 2) {
                produto.setPdtNome(produto.getPdtNome().toUpperCase());
                pdtList = produtoBo.carrgarPorNome(produto.getPdtNome());
                if (pdtList == null || pdtList.isEmpty()) {
                    throw new Exception(JPAUtil.PESQUISA_SEM_RESULTADOS);
                } else if (pdtList.size() == 1) {
                    produto = new PdtProduto();
                    setProduto(pdtList.get(0));
                    valor = produto.getPdtValor();
                    if(produto.getCat()==null || produto.getCat().getCatId()==null){
                        produto.setCat(new CatCategoria());
                    }
                    pdtList = new ArrayList<PdtProduto>();
                } else {
                    RequestContext request = RequestContext.getCurrentInstance();
                    request.addCallbackParam("sucesso", true);
                }
            } else {
                throw new Exception("Favor informa mais que 2 letras do nome para pesquisar!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }

    }

    public void verificarDuplicidade() throws Exception {
        List<PdtProduto> lista = produtoBo.carrgarPorNome(produto.getPdtNome());
        if (lista != null && !lista.isEmpty()) {
            if (lista.size() == 1) {
                if (!lista.get(0).getPdtId().equals(produto.getPdtId())) {
                    throw new Exception("Já existe um produto com esse nome!");
                }
            } else {
                throw new Exception("Já existe um produto com esse nome!");
            }
        }
    }

    public void carregarCategorias() {
        categorias = new ArrayList<SelectItem>();
        CategoriaBo catBo = new CategoriaBo();
        List<CatCategoria> lista = catBo.listarTodos();
        if (lista != null && !lista.isEmpty()) {
            for (CatCategoria c : lista) {
                categorias.add(new SelectItem(c.getCatId(), c.getCatDescricao()));
            }
        }
    }

    /**
     * Getters and Setters
     */
    public PdtProduto getProduto() {
        return produto;
    }

    public void setProduto(PdtProduto produto) {
        this.produto = produto;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<PdtProduto> getPdtList() {
        return pdtList;
    }

    public void setPdtList(List<PdtProduto> pdtList) {
        this.pdtList = pdtList;
    }

    public List<SelectItem> getCategorias() {
        if (categorias == null || categorias.isEmpty()) {
            carregarCategorias();
        }
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }
}
