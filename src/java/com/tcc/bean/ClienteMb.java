/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.CategoriaBo;
import com.tcc.bo.ContatoBo;
import com.tcc.bo.EnderecoBo;
import com.tcc.bo.LogradouroBo;
import com.tcc.bo.PedidoBo;
import com.tcc.bo.ProdutoBo;
import com.tcc.bo.ProdutoPedidoBo;
import com.tcc.bo.StatusBo;
import com.tcc.bo.UsuarioBo;
import com.tcc.model.CatCategoria;
import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.LgrLogradouro;
import com.tcc.model.PdtProduto;
import com.tcc.model.PedPedido;
import com.tcc.model.PpdProdutoPedido;
import com.tcc.model.SttStatus;
import com.tcc.model.UsrUsuario;
import com.tcc.util.Encripta;
import com.tcc.util.JPAUtil;
import com.tcc.util.Siglas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class ClienteMb extends BaseMb implements Serializable {

    private UsrUsuario usuario = new UsrUsuario();
    private UsuarioBo usuarioBo = new UsuarioBo();
    private EnderecoBo enderecoBo = new EnderecoBo();
    private ContatoBo contatoBo = new ContatoBo();
    private CategoriaBo categoriaBo = new CategoriaBo();
    private ProdutoBo produtoaBo = new ProdutoBo();
    private PedidoBo pedidoBo = new PedidoBo();
    private StatusBo statusBo = new StatusBo();
    private String login = "";
    private String senha = "";
    private String telaAtual = "";
    //contato
    private List<CntContato> cntList = new ArrayList<CntContato>();
    private CntContato cnt = new CntContato();
    private String cntDescricao;
    private boolean telSp;
    //endereco
    private EndEndereco endereco = new EndEndereco();
    private EndEndereco enderecoMemoria = new EndEndereco();
    private List<EndEndereco> endList = new ArrayList<EndEndereco>();
    private List<EndEndereco> endDeletadosList = new ArrayList<EndEndereco>();
    private String cepEnderecoNovo;
    //senha
    private String senhaAtual = "";
    private String senhaNova = "";
    private String senhaNovaConfirmacao = "";
    //visualizar produtos
    private Integer catId = 0;
    private List<SelectItem> catList = new ArrayList<SelectItem>();
    private List<PdtProduto> pdtList = new ArrayList<PdtProduto>();
    private List<PdtProduto> produtosSelecionados = new ArrayList<PdtProduto>();
    private Double valorTotal = 0.0;
    private PdtProduto pdt = new PdtProduto();
    private String enderecoEntregaString = "";
    private EndEndereco enderecoEntrega = new EndEndereco();
    private List<PedPedido> pedidos = new ArrayList<PedPedido>();
    private PedPedido pedido = new PedPedido();
    private List<PpdProdutoPedido> produtosPedidos = new ArrayList<PpdProdutoPedido>();
    private String contatoString = "";
    

    /**
     * Metodos
     */
    public ClienteMb() {
    }

    public void deslogar() {
        usuario = new UsrUsuario();
        telaAtual = "";
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.setAttribute("currentUser", null);
    }

    public void logar() {
        try {
            validarLogin();
            UsrUsuario usuarioBanco = usuarioBo.pesquisarPorLogin(login);
            if (usuarioBanco != null && usuarioBanco.getUsrId() != null) {
                if (usuarioBanco.getUsrSenha().equals(Encripta.criptografaSenha(senha))) {
                    if (usuarioBanco.isUsrSenhaBloqueada()) {
                        throw new Exception("Senha Bloqueada, favor entrar em contato com o Administrador");
                    } else {
                        usuario = usuarioBanco;
                        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                        session.setAttribute("currentUser", usuario);
                    }
                } else {
                    throw new Exception("Senha Incorreta");
                }
            } else {
                throw new Exception("Usuário não encontrado");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void validarLogin() throws Exception {
        if (login == null || login.isEmpty()) {
            throw new Exception("Informe o usuário");
        }
        if (senha == null || senha.isEmpty()) {
            throw new Exception("Informe a senha");
        }
    }

    public void carregaDados() {
        try {
            cntList = new ArrayList<CntContato>();
            endList = new ArrayList<EndEndereco>();
            if (usuario != null && usuario.getPss() != null && usuario.getPss().getPssId() != null) {
                cntList = contatoBo.listarPorPessoa(usuario.getPss());
                endList = enderecoBo.listarPorPessoa(usuario.getPss());
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //contatos
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void excluirContato() {
        if (cnt != null) {
            Iterator<CntContato> it = cntList.iterator();
            while (it.hasNext()) {
                CntContato c = (CntContato) it.next();
                if (c.getCntDescricao().equalsIgnoreCase(cnt.getCntDescricao())) {
                    it.remove();
                }
            }
            if (cnt.getCntId() != null) {
                contatoBo.excluir(cnt);
            }
            cnt = new CntContato();
        }
    }

    public void limparContato() {
        cntDescricao = null;
    }

    public void adicionarCntList() {
        try {
            if (cntDescricao == null || cntDescricao.trim().isEmpty()) {
                throw new Exception("Favor inserir um contato!");
            }
            for (CntContato c : cntList) {
                if (c.getCntDescricao().equalsIgnoreCase(cntDescricao)) {
                    throw new Exception("Esse contato já foi cadastrado para esse usuário");
                }
            }
            cnt = new CntContato();
            cnt.setCntDescricao(cntDescricao);
            cnt.setPss(usuario.getPss());
            contatoBo.inserir(cnt);
            cntList.add(cnt);
            cntDescricao = "";

            JPAUtil.addMensagemSucesso("Contato adicionado!");
            RequestContext request = RequestContext.getCurrentInstance();
            request.addCallbackParam("sucesso", true);
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void novoContato() {
        cnt = new CntContato();
        cntDescricao = "";
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //endereco
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void validarEndereco() throws Exception {
        if (endereco.getLgr() == null || endereco.getLgr().getLgrDescricao() == null || endereco.getLgr().getLgrDescricao().trim().isEmpty()) {
            throw new Exception("Favor informar um logradouro!");
        }
        if (endereco.getLgr().getLgrCep() == null || endereco.getLgr().getLgrCep().isEmpty()) {
            throw new Exception("Favor informar um cep");
        }
        if (endereco.getEndNumero() == null || endereco.getEndNumero().isEmpty()) {
            throw new Exception("Favor informar um número!");
        }
        if (endList != null && !endList.isEmpty()) {
            for (EndEndereco e : endList) {
                //verifica se não é o proprio obj (ele na lista)
                if (!e.isSelecionado()) {
                    //vefica duplicidade
                    if ((e.getEndNumero().equalsIgnoreCase(endereco.getEndNumero()))
                            && (e.getLgr().getLgrDescricao().trim().equalsIgnoreCase(endereco.getLgr().getLgrDescricao().trim()))
                            && (e.getLgr().getLgrCep().equalsIgnoreCase(endereco.getLgr().getLgrCep()))) {
                        throw new Exception("Endereço já cadastrado para essa pessoa!");
                    }
                }
            }
        }
    }

    public void adicionarEndEndereco() {
        try {
            if (endereco != null) {
                validarEndereco();
                //verifica se logradouro já existe.
                LogradouroBo logradourobo = new LogradouroBo();
                LgrLogradouro logradouroExistente = logradourobo.carregarLogradouroPorDescricaoECep(endereco.getLgr().getLgrDescricao(), endereco.getLgr().getLgrCep());
                if (logradouroExistente != null && logradouroExistente.getLgrId() != null) {
                    endereco.setLgr(logradouroExistente);
                } else {
                    logradourobo.inserir(endereco.getLgr());
                }
                if (!endereco.isSelecionado()) {
                    endList.add(endereco);
                }
                endereco.setSelecionado(false);
                JPAUtil.addMensagemSucesso("Endereço adicionado!");
                endereco = new EndEndereco();
                enderecoMemoria = new EndEndereco();
                RequestContext request = RequestContext.getCurrentInstance();
                request.addCallbackParam("sucesso", true);
            }
        } catch (Exception e) {
            endereco.setEndNumero(enderecoMemoria.getEndNumero());
            endereco.getLgr().setLgrDescricao(enderecoMemoria.getLgr().getLgrDescricao());
            endereco.getLgr().setLgrCep(enderecoMemoria.getLgr().getLgrCep());
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void limparEndereco() {
        endereco = new EndEndereco();
        enderecoMemoria = new EndEndereco();
        if (endList != null && !endList.isEmpty()) {
            for (EndEndereco e : endList) {
                e.setSelecionado(false);
            }
        }
    }

    public void excluirEndereco() {
        if (endereco != null) {
            Iterator<EndEndereco> it = endList.iterator();
            while (it.hasNext()) {
                EndEndereco e = (EndEndereco) it.next();
                if ((e.getEndNumero().equalsIgnoreCase(endereco.getEndNumero()))
                        && (e.getLgr().getLgrDescricao().equalsIgnoreCase(endereco.getLgr().getLgrDescricao()))
                        && (e.getLgr().getLgrCep().equalsIgnoreCase(endereco.getLgr().getLgrCep()))) {
                    it.remove();
                }
            }

            if (endereco.getEndId() != null) {
                endDeletadosList.add(endereco);
            }
            endereco = new EndEndereco();
        }
    }

    public void novoEndereco() {
        cepEnderecoNovo = "";
        limparEndereco();
    }

    public void trocaSenha() {
        try {
            if (usuario != null && usuario.getUsrId() != null) {
                if (!usuario.getUsrSenha().equals(Encripta.criptografaSenha(senhaAtual))) {
                    throw new Exception("A senha atual não está correta");
                }
                if (!senhaNova.equals(senhaNovaConfirmacao)) {
                    throw new Exception("A confirmação da senha e a nova senha não estão iguais");
                }
                usuario.setUsrSenha(Encripta.criptografaSenha(senhaNova));
                usuario.setUsrDataAlteracao(new Date());
                usuario.setUsrSenhaBloqueada(Boolean.FALSE);
                usuarioBo.alterar(usuario);
                telaAtual = "";
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Visualizacao de Produtos
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void recarregarCatList() {
        catList = new ArrayList<SelectItem>();
        List<CatCategoria> listaAtivos = categoriaBo.listarAtivos();
        if (listaAtivos != null && !listaAtivos.isEmpty()) {
            for (CatCategoria c : listaAtivos) {
                catList.add(new SelectItem(c.getCatId(), c.getCatDescricao().toUpperCase()));
            }
            if (catList != null && !catList.isEmpty() && catList.get(0).getValue() != null) {
                catId = (Integer) catList.get(0).getValue();
                recarregarProdutos();
            }
        }
    }

    public void recarregarProdutos() {
        pdtList = new ArrayList<PdtProduto>();
        if (catId != null && catId > 0) {
            pdtList = produtoaBo.listarPorCategoria(new CatCategoria(catId));
        }
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

    public void addCarrinho() {
        if (pdt != null && pdt.getPdtId() != null) {
            pdt.setQuantidade(1);
            if (produtosSelecionados == null) {
                produtosSelecionados = new ArrayList<PdtProduto>();
            }
            if (!produtosSelecionados.contains(pdt)) {
                produtosSelecionados.add(pdt);
            }
            calculaPreco();
            JPAUtil.addMensagemSucesso("Produto adicionado ao carrinho de compras");
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

    public void excluirProduto() {
        if (pdt != null && pdt.getPdtId() != null) {
            Iterator<PdtProduto> it = produtosSelecionados.iterator();
            while (it.hasNext()) {
                PdtProduto p = (PdtProduto) it.next();
                if (p.getPdtId().equals(pdt.getPdtId())) {
                    it.remove();
                }
            }
            calculaPreco();
        }
    }

    public void comprar() {
        try {
            if (produtosSelecionados == null || produtosSelecionados.isEmpty()) {
                throw new Exception("Favor selecionar algum produto");
            }
            if (enderecoEntrega == null || enderecoEntrega.getEndId() == null) {
                throw new Exception("Favor selecionar um endereço de entrega");
            }
            PedPedido pedPedido = new PedPedido();
            pedPedido.setEnd(enderecoEntrega);
            pedPedido.setPss(usuario.getPss());
            pedPedido.setPedValor(valorTotal);
            List<SttStatus> stt_ = statusBo.carrgar("em espera");
            pedPedido.setStt(stt_.get(0));
            pedPedido.setFun(null);
            pedPedido.setProdutosPedido(new ArrayList<PpdProdutoPedido>());

            pedidoBo.inserir(pedPedido);
            if (pedPedido.getPedId() != null) {
                for (PdtProduto p : produtosSelecionados) {
                    PpdProdutoPedido ppd = new PpdProdutoPedido();
                    ppd.setPdt(p);
                    ppd.setPpdQuantidade(p.getQuantidade());
                    ppd.setPed(new PedPedido(pedPedido.getPedId()));
                    pedPedido.getProdutosPedido().add(ppd);
                }
                if (pedPedido.getProdutosPedido() != null && !pedPedido.getProdutosPedido().isEmpty()) {
                    pedidoBo.salvarProdutoPedidoEmLote((List<PpdProdutoPedido>) pedPedido.getProdutosPedido());
                }
            } else {
                throw new Exception("Erro ao efetuar a compra :(");
            }
            JPAUtil.addMensagemSucesso("Compra efeuada com sucesso !!!");
            pdt = new PdtProduto();
            produtosSelecionados = new ArrayList<PdtProduto>();
            enderecoEntrega = new EndEndereco();
            enderecoEntregaString = "";
            recarregarPedidosCliente();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void recarregarPedidosCliente() {
        pedidos = new ArrayList<PedPedido>();
        pedidos = pedidoBo.listarPorPessoa(usuario.getPss());
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
    
    /**
     * Getters and Setters
     */
    public UsrUsuario getUsuario() {
        if (usuario == null || usuario.getUsrId() == null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null && session.getAttribute("currentUser") != null) {
                usuario = (UsrUsuario) session.getAttribute("currentUser");
            }
        }
        return usuario;
    }

    public void setUsuario(UsrUsuario usuario) {
        this.usuario = usuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTELA_TROCA_SENHA() {
        return Siglas.TELA_TROCA_SENHA;
    }

    public String getTELA_ALTERA_DADOS() {
        return Siglas.TELA_ALTERA_DADOS;
    }

    public String getTELA_ACOMPANHAR_PEDIDO() {
        return Siglas.TELA_ACOMPANHAR_PEDIDO;
    }

    public String getTELA_CARRINHO() {
        return Siglas.TELA_CARRINHO;
    }
    
      public String getEM_ENTREGA() {
        return "Em entrega";
    }

    public String getTELA_VISUALIZA_PRODUTO() {
        return Siglas.TELA_VISUALIZA_PRODUTO;
    }

    public String getTelaAtual() {
        return telaAtual;
    }

    public void setTelaAtual(String telaAtual) {
        this.telaAtual = telaAtual;
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

    public CntContato getCnt() {
        return cnt;
    }

    public void setCnt(CntContato cnt) {
        this.cnt = cnt;
    }

    public String getCntDescricao() {
        return cntDescricao;
    }

    public void setCntDescricao(String cntDescricao) {
        this.cntDescricao = cntDescricao;
    }

    public boolean isTelSp() {
        return telSp;
    }

    public void setTelSp(boolean telSp) {
        this.telSp = telSp;
    }

    public EndEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(EndEndereco endereco) {
        this.endereco = endereco;
    }

    public EndEndereco getEnderecoMemoria() {
        return enderecoMemoria;
    }

    public void setEnderecoMemoria(EndEndereco enderecoMemoria) {
        this.enderecoMemoria = enderecoMemoria;
    }

    public List<EndEndereco> getEndDeletadosList() {
        return endDeletadosList;
    }

    public void setEndDeletadosList(List<EndEndereco> endDeletadosList) {
        this.endDeletadosList = endDeletadosList;
    }

    public String getCepEnderecoNovo() {
        return cepEnderecoNovo;
    }

    public void setCepEnderecoNovo(String cepEnderecoNovo) {
        this.cepEnderecoNovo = cepEnderecoNovo;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public String getSenhaNovaConfirmacao() {
        return senhaNovaConfirmacao;
    }

    public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
        this.senhaNovaConfirmacao = senhaNovaConfirmacao;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public List<SelectItem> getCatList() {
        if (catList == null || catList.isEmpty()) {
            recarregarCatList();
        }
        return catList;
    }

    public void setCatList(List<SelectItem> catList) {
        this.catList = catList;
    }

    public List<PdtProduto> getPdtList() {
        return pdtList;
    }

    public void setPdtList(List<PdtProduto> pdtList) {
        this.pdtList = pdtList;
    }

    public List<PdtProduto> getProdutosSelecionados() {
        return produtosSelecionados;
    }

    public void setProdutosSelecionados(List<PdtProduto> produtosSelecionados) {
        this.produtosSelecionados = produtosSelecionados;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public PdtProduto getPdt() {
        return pdt;
    }

    public void setPdt(PdtProduto pdt) {
        this.pdt = pdt;
    }

    public String getEnderecoEntregaString() {
        return enderecoEntregaString;
    }

    public void setEnderecoEntregaString(String enderecoEntregaString) {
        this.enderecoEntregaString = enderecoEntregaString;
    }

    public EndEndereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EndEndereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<PedPedido> getPedidos() {
        if (pedidos == null || pedidos.isEmpty()) {
            recarregarPedidosCliente();
        }
        return pedidos;
    }

    public void setPedidos(List<PedPedido> pedidos) {
        this.pedidos = pedidos;
    }

    public PedPedido getPedido() {
        return pedido;
    }

    public void setPedido(PedPedido pedido) {
        this.pedido = pedido;
    }

    public List<PpdProdutoPedido> getProdutosPedidos() {
        return produtosPedidos;
    }

    public void setProdutosPedidos(List<PpdProdutoPedido> produtosPedidos) {
        this.produtosPedidos = produtosPedidos;
    }

    public String getContatoString() {
        return contatoString;
    }

    public void setContatoString(String contatoString) {
        this.contatoString = contatoString;
    }
}
