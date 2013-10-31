/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.ContatoBo;
import com.tcc.bo.EnderecoBo;
import com.tcc.bo.FuncionarioBo;
import com.tcc.bo.PedidoBo;
import com.tcc.bo.PessoaBo;
import com.tcc.bo.ProdutoBo;
import com.tcc.bo.ProdutoPedidoBo;
import com.tcc.bo.StatusBo;
import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.FunFuncionario;
import com.tcc.model.PdtProduto;
import com.tcc.model.PedPedido;
import com.tcc.model.PpdProdutoPedido;
import com.tcc.model.PssPessoa;
import com.tcc.model.SttStatus;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class PedidoMb extends BaseMb implements Serializable {

    private PedPedido pedido = new PedPedido();
    private String nomePessoa;
    private PssPessoa pss = new PssPessoa();
    private List<PssPessoa> pssList = new ArrayList<PssPessoa>();
    private List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
    private EndEndereco endereco = new EndEndereco();
    private PessoaBo pessoaBo = new PessoaBo();
    private List<SelectItem> sttList = new ArrayList<SelectItem>();
    private Integer sttId = 0;
    private ModoTela modo = new ModoTela();
    private List<CntContato> cntList = new ArrayList<CntContato>();
    private List<EndEndereco> endList = new ArrayList<EndEndereco>();
    private EndEndereco enderecoEntrega = new EndEndereco();
    private String enderecoEntregaString = "";
    private DualListModel<PdtProduto> produtos = new DualListModel<PdtProduto>();
    private PdtProduto prod = new PdtProduto();
    private List<PdtProduto> produtosSelecionados = new ArrayList<PdtProduto>();
    private ProdutoBo produtoBo = new ProdutoBo();
    private Double valorTotal = 0.0;
    private List<PedPedido> pedidos = new ArrayList<PedPedido>();
    private StatusBo statusBo = new StatusBo();
    private PedidoBo pedidoBo = new PedidoBo();
    private Date dataInicio;
    private Date dataFim;
    private String contatoString = "";
    private String produtoString = "";
    private List<PpdProdutoPedido> produtosPedidos = new ArrayList<PpdProdutoPedido>();
    private List<SelectItem> sttListMovificar = new ArrayList<SelectItem>();
    private Integer sttIdNovo = 0;
    private Integer funIdNovo = 0;
    private List<SelectItem> funList = new ArrayList<SelectItem>();
    private FuncionarioBo funcionarioBo = new FuncionarioBo();

    public PedidoMb() {
    }

    public void pesquisarPedidos() {
        pedidos = new ArrayList<PedPedido>();
        if (sttId != null && sttId > 0) {
            pedidos = pedidoBo.listarPorStatusDataHora(new SttStatus(sttId), dataInicio, dataFim);
        }
    }

    @PostConstruct
    public void carregarPessoa() {
        //carrega os produtos
        ProdutoBo produtoBo = new ProdutoBo();
        produtos.setSource(produtoBo.listarTods());
        produtos.setTarget(new ArrayList<PdtProduto>());

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (request.getParameter("pssId") != null && !String.valueOf(request.getParameter("pssId")).isEmpty()) {
            int pssId = Integer.parseInt(request.getParameter("pssId"));
            pss = pessoaBo.carregar(pssId);
            if (pss != null && pss.getPssId() != null) {
                nomePessoa = pss.getPssNome() + " " + pss.getPssSobrenome();
                ContatoBo contatoBo = new ContatoBo();
                EnderecoBo enderecoBo = new EnderecoBo();
                cntList = contatoBo.listarPorPessoa(pss);
                endList = enderecoBo.listarPorPessoa(pss);
                modo.setModoTela(ModoTela.INSERCAO);
            }
        }
    }

    public void onTransfer(TransferEvent event) {
        boolean add = event.isAdd();
        List<String> items = (List<String>) event.getItems();
        for (String pdtSelecionado : items) {
            PdtProduto pdtItem = produtoBo.carrgarPorNomeCompleto(pdtSelecionado);
            if (pdtItem != null && pdtItem.getPdtId() != null) {
                pdtItem.setQuantidade(1);
                if (add) {
                    produtosSelecionados.add(pdtItem);
                } else {
                    Iterator<PdtProduto> it = produtosSelecionados.iterator();
                    while (it.hasNext()) {
                        PdtProduto p = (PdtProduto) it.next();
                        if (p.getPdtId().equals(pdtItem.getPdtId())) {
                            it.remove();
                        }
                    }
                }
            }
        }
        calculaPreco();
    }

    public void onCellEdit(CellEditEvent event) {
        calculaPreco();
    }

    public void calculaPreco() {
        valorTotal = 0.0;
        if (produtosSelecionados != null && !produtosSelecionados.isEmpty()) {
            for (PdtProduto p : produtosSelecionados) {
                valorTotal = valorTotal + ((p.getPdtValor()) * (p.getQuantidade()));
            }
        }
    }

    public void carregarListasPessoa() {
        if (pss != null && pss.getPssId() != null) {
            nomePessoa = pss.getPssNome() + " " + pss.getPssSobrenome();
            ContatoBo contatoBo = new ContatoBo();
            EnderecoBo enderecoBo = new EnderecoBo();
            cntList = contatoBo.listarPorPessoa(pss);
            endList = enderecoBo.listarPorPessoa(pss);
        }
    }

    public void cancelar() {
        limpar();
    }

    public void limpar() {
        pedido = new PedPedido();
        nomePessoa = null;
        pss = new PssPessoa();
        pssList = new ArrayList<PssPessoa>();
        pdtList = new ArrayList<PdtProduto>();
        endereco = new EndEndereco();
        endList = new ArrayList<EndEndereco>();
        pessoaBo = new PessoaBo();
        sttList = new ArrayList<SelectItem>();
        sttId = 0;
        modo = new ModoTela();
        cntList = new ArrayList<CntContato>();
        endList = new ArrayList<EndEndereco>();
        enderecoEntrega = new EndEndereco();
        enderecoEntregaString = "";
        produtos = new DualListModel<PdtProduto>();
        //carrega os produtos
        ProdutoBo produtoBo = new ProdutoBo();
        produtos.setSource(produtoBo.listarTods());
        prod = new PdtProduto();
        produtosSelecionados = new ArrayList<PdtProduto>();
        valorTotal = 0.0;
    }

    public void novaCompra() {
        limpar();
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void validarCompra() throws Exception {
        if (pss == null || pss.getPssId() == null) {
            throw new Exception("Favor carregar uma pessoa!");
        }
        if (produtosSelecionados == null || produtosSelecionados.isEmpty()) {
            throw new Exception("Favor inserir pelo menos um produto!");
        }
        if (enderecoEntrega == null || enderecoEntrega.getEndId() == null) {
            throw new Exception("Favor inserir um endereço de entrega!");
        }
    }

    public void comprar() {
        try {
            validarCompra();
            pedido = new PedPedido();
            pedido.setEnd(enderecoEntrega);
            pedido.setPss(pss);
            pedido.setPedValor(valorTotal);
            pedido.setStt(new SttStatus(sttId));
            pedido.setFun(null);
            pedido.setProdutosPedido(new ArrayList<PpdProdutoPedido>());
            PedidoBo pedidoBo = new PedidoBo();
            pedidoBo.inserir(pedido);
            for (PdtProduto p : produtosSelecionados) {
                PpdProdutoPedido ppd = new PpdProdutoPedido();
                ppd.setPdt(p);
                ppd.setPpdQuantidade(p.getQuantidade());
                ppd.setPed(pedido);
                pedido.getProdutosPedido().add(ppd);
            }
            if (pedido.getPedId() != null) {
                if (pedido.getProdutosPedido() != null && !pedido.getProdutosPedido().isEmpty()) {
                    pedidoBo.salvarProdutoPedidoEmLote((List<PpdProdutoPedido>) pedido.getProdutosPedido());
                }
            } else {
                throw new Exception("Erro ao efetuar a compra :(");
            }
            JPAUtil.addMensagemSucesso("Compra efeuada com sucesso !!!");
            limpar();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisarPessoa() {
        try {
            pssList = new ArrayList<PssPessoa>();
            endList = new ArrayList<EndEndereco>();
            cntList = new ArrayList<CntContato>();
            pss = new PssPessoa();
            if ((nomePessoa == null || nomePessoa.trim().isEmpty() || nomePessoa.length() < 3)) {
                throw new Exception("Insira pelo menos 3 letras para buscar!!");
            }
            pssList = pessoaBo.pesquisarPorNomeSobreNome(nomePessoa);
            //caso busca não retorne resultados dá um erro
            if (pssList != null) {
                if (pssList.size() == 1) {
                    setPss(pssList.get(0));
                    nomePessoa = pss.getPssNome() + " " + pss.getPssSobrenome();
                    modo.setModoTela(ModoTela.INSERCAO);
                } else {
                    RequestContext request = RequestContext.getCurrentInstance();
                    request.addCallbackParam("sucesso", true);
                }
                ContatoBo contatoBo = new ContatoBo();
                EnderecoBo enderecoBo = new EnderecoBo();
                for (PssPessoa p : pssList) {
                    cntList = contatoBo.listarPorPessoa(p);
                    if (cntList != null && !cntList.isEmpty()) {
                        p.setContatosString("");
                        for (CntContato c : cntList) {
                            p.setContatosString(p.getContatosString() + c.getCntDescricao() + "*");
                        }
                        if (!p.getContatosString().trim().isEmpty()) {
                            p.setContatosString(p.getContatosString().substring(0, p.getContatosString().length() - 1));
                        }
                    }
                    endList = enderecoBo.listarPorPessoa(p);
                    if (endList != null && !endList.isEmpty()) {
                        p.setEnderecosString("");
                        for (EndEndereco e : endList) {
                            p.setEnderecosString(p.getEnderecosString() + "Logr: " + e.getLgr().getLgrDescricao() + " nº " + e.getEndNumero() + " " + e.getLgr().getLgrCidade() + "/" + e.getLgr().getLgrEstado() + "*");
                        }
                        if (!p.getEnderecosString().trim().isEmpty()) {
                            p.setEnderecosString(p.getEnderecosString().substring(0, p.getEnderecosString().length() - 1));
                        }
                    }
                }
            } else {
                RequestContext request = RequestContext.getCurrentInstance();
                request.addCallbackParam("sucesso", true);
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public String telaPessoa() {
        return "pessoa.xhtml?faces-redirect=true&tela=pedidos";
    }

    public void recarregarStatus() {
        sttList = new ArrayList<SelectItem>();
        StatusBo statusBo = new StatusBo();
        List<SttStatus> todos = statusBo.listarTodos();
        if (todos != null && !todos.isEmpty()) {
            for (SttStatus s : todos) {
                if (s.getSttAtivo()) {
                    sttList.add(new SelectItem(s.getSttId(), s.getSttDescricao()));
                }
            }
        }
    }

    public void carregarEnderecoEntrega() {
        enderecoEntregaString = "";
        if (enderecoEntrega != null && enderecoEntrega.getEndId() != null) {
            if (enderecoEntrega.getLgr() != null && enderecoEntrega.getLgr().getLgrId() != null) {
                enderecoEntregaString = "Logradouro: " + enderecoEntrega.getLgr().getLgrDescricao();
                if (enderecoEntrega.getEndNumero() != null) {
                    enderecoEntregaString = enderecoEntregaString + " Nº: " + enderecoEntrega.getEndNumero();
                }
                if (enderecoEntrega.getLgr().getLgrCep() != null) {
                    enderecoEntregaString = enderecoEntregaString + " - CEP:" + enderecoEntrega.getLgr().getLgrCep();
                }
                enderecoEntregaString = enderecoEntregaString + " - " + enderecoEntrega.getLgr().getLgrCidade();
            }
        }
    }

    public void detalharPedido() {
        if (pedido != null && pedido.getPedId() != null) {
            if (pedido.getEnd().getEndComplemento() != null) {
                pedido.getEnd().setEndComplemento(pedido.getEnd().getEndComplemento().toUpperCase());
            }
            if (pedido.getEnd().getLgr().getLgrDescricao() != null) {
                pedido.getEnd().getLgr().setLgrDescricao(pedido.getEnd().getLgr().getLgrDescricao().toUpperCase());
            }
            if (pedido.getEnd().getLgr().getLgrCidade() != null) {
                pedido.getEnd().getLgr().setLgrCidade(pedido.getEnd().getLgr().getLgrCidade().toUpperCase());
            }
            ContatoBo contatoBo = new ContatoBo();
            List<CntContato> listCnt = contatoBo.listarPorPessoa(pedido.getPss());
            contatoString = "";
            if (listCnt != null && !listCnt.isEmpty()) {
                for (CntContato c : listCnt) {
                    contatoString = contatoString + c.getCntDescricao() + " / ";
                }
                contatoString = contatoString.substring(0, (contatoString.length() - 3));
            }
            produtosSelecionados = new ArrayList<PdtProduto>();
            ProdutoPedidoBo prodPedidoBo = new ProdutoPedidoBo();
            produtosPedidos = new ArrayList<PpdProdutoPedido>();
            produtosPedidos = prodPedidoBo.listarPorPedido(pedido);
        }
    }

    public void modifcarStatus() {
        if (pedido != null && pedido.getPedId() != null) {
            sttListMovificar = new ArrayList<SelectItem>();
            List<SttStatus> statusAtivos = statusBo.listarAtivos();
            if (statusAtivos != null && !statusAtivos.isEmpty()) {
                for (SttStatus s : statusAtivos) {
                    sttListMovificar.add(new SelectItem(s.getSttId(), s.getSttDescricao()));
                }
            }
        }
    }

    public void salvarNovoStatus() {
        try {
            if (pedido != null && pedido.getPedId() != null) {
                pedido.setStt(new SttStatus(sttIdNovo));
                pedido.setProdutosPedido(produtosPedidos);
                if(funIdNovo!=null && funIdNovo>0){
                pedido.setFun(new FunFuncionario(funIdNovo));
                }else{
                    pedido.setFun(null);
                }
                pedidoBo.alterar(pedido);
                JPAUtil.addMensagemSucesso(JPAUtil.ALTERADO_COM_SUCESSO);
                sttId = sttIdNovo;
                pesquisarPedidos();
                RequestContext request = RequestContext.getCurrentInstance();
                request.addCallbackParam("sucesso", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JPAUtil.addMensagemErro("Erro ao Alterar");
        }
    }

    public void recarregarFunList() {
        try {
            funList = new ArrayList<SelectItem>();
            List<FunFuncionario> lista = funcionarioBo.listarFuncionariosAtivos();
            if (lista != null && !lista.isEmpty()) {
                for (FunFuncionario f : lista) {
                    funList.add(new SelectItem(f.getFunId(), f.getPss().getPssNome() + " " + f.getPss().getPssSobrenome()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JPAUtil.addMensagemErro("Erro ao carregar funcionários ativos");
        }
    }

    /**
     * Getters and Setters
     */
    public Integer getSttIdNovo() {
        return sttIdNovo;
    }

    public void setSttIdNovo(Integer sttIdNovo) {
        this.sttIdNovo = sttIdNovo;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public List<PdtProduto> getPdtList() {
        return pdtList;
    }

    public void setPdtList(List<PdtProduto> pdtList) {
        this.pdtList = pdtList;
    }

    public EndEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(EndEndereco endereco) {
        this.endereco = endereco;
    }

    public PedPedido getPedido() {
        return pedido;
    }

    public void setPedido(PedPedido pedido) {
        this.pedido = pedido;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    public List<PssPessoa> getPssList() {
        return pssList;
    }

    public void setPssList(List<PssPessoa> pssList) {
        this.pssList = pssList;
    }

    public List<SelectItem> getSttList() {
        if (sttList == null || sttList.isEmpty()) {
            recarregarStatus();
        }
        return sttList;
    }

    public void setSttList(List<SelectItem> sttList) {
        this.sttList = sttList;
    }

    public Integer getSttId() {
        return sttId;
    }

    public void setSttId(Integer sttId) {
        this.sttId = sttId;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public List<CntContato> getCntList() {
        return cntList;
    }

    public void setCntList(List<CntContato> cntList) {
        this.cntList = cntList;
    }

    public List<EndEndereco> getEndList() {
        return endList;
    }

    public void setEndList(List<EndEndereco> endList) {
        this.endList = endList;
    }

    public PessoaBo getPessoaBo() {
        return pessoaBo;
    }

    public void setPessoaBo(PessoaBo pessoaBo) {
        this.pessoaBo = pessoaBo;
    }

    public EndEndereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EndEndereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getEnderecoEntregaString() {
        return enderecoEntregaString;
    }

    public void setEnderecoEntregaString(String enderecoEntregaString) {
        this.enderecoEntregaString = enderecoEntregaString;
    }

    public DualListModel<PdtProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(DualListModel<PdtProduto> produtos) {
        this.produtos = produtos;
    }

    public PdtProduto getProd() {
        return prod;
    }

    public void setProd(PdtProduto prod) {
        this.prod = prod;
    }

    public List<PdtProduto> getProdutosSelecionados() {
        return produtosSelecionados;
    }

    public void setProdutosSelecionados(List<PdtProduto> produtosSelecionados) {
        this.produtosSelecionados = produtosSelecionados;
    }

    public ProdutoBo getProdutoBo() {
        return produtoBo;
    }

    public void setProdutoBo(ProdutoBo produtoBo) {
        this.produtoBo = produtoBo;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedPedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getContatoString() {
        return contatoString;
    }

    public void setContatoString(String contatoString) {
        this.contatoString = contatoString;
    }

    public String getProdutoString() {
        return produtoString;
    }

    public void setProdutoString(String produtoString) {
        this.produtoString = produtoString;
    }

    public List<PpdProdutoPedido> getProdutosPedidos() {
        return produtosPedidos;
    }

    public void setProdutosPedidos(List<PpdProdutoPedido> produtosPedidos) {
        this.produtosPedidos = produtosPedidos;
    }

    public List<SelectItem> getSttListMovificar() {
        return sttListMovificar;
    }

    public void setSttListMovificar(List<SelectItem> sttListMovificar) {
        this.sttListMovificar = sttListMovificar;
    }

    public Integer getFunIdNovo() {
        return funIdNovo;
    }

    public void setFunIdNovo(Integer funIdNovo) {
        this.funIdNovo = funIdNovo;
    }

    public List<SelectItem> getFunList() {
        if (funList == null || funList.isEmpty()) {
            recarregarFunList();
        }
        return funList;
    }

    public void setFunList(List<SelectItem> funList) {
        this.funList = funList;
    }
}
