/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.ContatoBo;
import com.tcc.bo.EnderecoBo;
import com.tcc.bo.PessoaBo;
import com.tcc.bo.TipoAcessoBo;
import com.tcc.bo.UsuarioBo;
import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.PssPessoa;
import com.tcc.model.TpaTipoAcesso;
import com.tcc.model.UsrUsuario;
import com.tcc.util.Encripta;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class UsuarioMb extends BaseMb implements Serializable {

    private UsrUsuario usr = new UsrUsuario();
    private PssPessoa pss = new PssPessoa();
    private String nome = "";
    private ModoTela modo = new ModoTela();
    private List<PssPessoa> pssList = new ArrayList<PssPessoa>();
    private PessoaBo pessoaBo = new PessoaBo();
    private UsuarioBo usuarioBo = new UsuarioBo();
    private TipoAcessoBo tipoAcessoBo = new TipoAcessoBo();
    private Integer pssId = 0;
    private List<SelectItem> tpaList = new ArrayList<SelectItem>();
    private Integer tpaId;
    private List<UsrUsuario> usrList = new ArrayList<UsrUsuario>();
    private boolean senhaBloqueada = false;

    public UsuarioMb() {
    }

    @PostConstruct
    public void carregarPessoa() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (request.getParameter("pssId") != null && !String.valueOf(request.getParameter("pssId")).isEmpty()) {
            pssId = Integer.parseInt(request.getParameter("pssId"));
            if (pssId != null && pssId > 0) {
                PessoaBo pessoaBo = new PessoaBo();
                PssPessoa pss = pessoaBo.carregar(pssId);
                if (pss != null && pss.getPssId() != null) {
                    usr.setPss(pss);
                    nome = pss.getPssNome() + " " + pss.getPssSobrenome();
                    modo.setModoTela(ModoTela.INSERCAO);
                }
            }
        }
    }

    public String telaPessoa() {
        return "pessoa.xhtml?faces-redirect=true&tela=usuarios";
    }

    public void novo() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void validarUsr() throws Exception {
        if (usr.getPss() == null || usr.getPss().getPssId() == null) {
            throw new Exception("Favor carregar uma Pessoa");
        }
        verificarDuplicidade();
        validarEmail();
    }

    public void verificarDuplicidade() throws Exception {
        UsrUsuario usrBanco = usuarioBo.pesquisarPorLogin(usr.getUsrLogin());
        if ((usrBanco != null && usrBanco.getUsrId() != null) && ((usr.getUsrId() == null) || (!usrBanco.getUsrId().equals(usr.getUsrId())))) {
            throw new Exception("Infelizmente esse usuário já está sendo usado!!");
        }
    }

    public void validarEmail() throws Exception {
        if (usr.getUsrEmail() == null || usr.getUsrEmail().trim().isEmpty()) {
            throw new Exception("Favor inserir um email");
        } else {
            Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
            Matcher matcher = pattern.matcher(usr.getUsrEmail());
            if (!matcher.find()) {
                throw new Exception("Favor inserir um email válido");
            }
        }
    }

    public void salvar() {
        try {
            validarUsr();
            if (usr.getUsrId() == null) {
                usr.setUsrSenha(Encripta.criptografaSenha("12345678910'"));
            }
            validarUsr();
            usr.setTpa(new TpaTipoAcesso(tpaId));
            usr.setUsrSenhaBloqueada(senhaBloqueada);
            if (usr.getUsrId() == null) {
                usuarioBo.inserir(usr);
                JPAUtil.addMensagemSucesso(JPAUtil.INSERIDO_COM_SUCESSO);
            } else {
                usuarioBo.alterar(usr);
                JPAUtil.addMensagemSucesso(JPAUtil.ALTERADO_COM_SUCESSO);
            }
            modo.setModoTela(ModoTela.VISUALIZACAO);
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void cancelar() {
        modo = new ModoTela();
        senhaBloqueada = false;
        usr = new UsrUsuario();
        nome = "";
    }

    public void pesquisaUsr() {
        try {
            tpaId = null;
            senhaBloqueada = false;
            usrList = new ArrayList<UsrUsuario>();
            if (usr.getUsrLogin() != null && !usr.getUsrLogin().isEmpty()) {
                usrList = usuarioBo.carregarPorLogin(usr.getUsrLogin());
                if (usrList != null && !usrList.isEmpty()) {
                    if (usrList.size() == 1) {
                        usr = usrList.get(0);
                        tpaId = usr.getTpa().getTpaId();
                        senhaBloqueada = usr.isUsrSenhaBloqueada();
                        usrList = new ArrayList<UsrUsuario>();
                        modo.setModoTela(ModoTela.VISUALIZACAO);
                    } else {
                        usr = new UsrUsuario();
                        modo.setModoTela(ModoTela.INSERCAO);
                        RequestContext request = RequestContext.getCurrentInstance();
                        request.addCallbackParam("sucesso", true);
                    }
                } else {
                    throw new Exception("Nenhum usuário encontrado com esse login :(");
                }
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisarPss() {
        try {
            tpaId = null;
            senhaBloqueada = false;
            pssList = new ArrayList<PssPessoa>();
            if (nome != null && !nome.trim().isEmpty() && nome.length() >= 3) {
                pssList = pessoaBo.pesquisarPorNomeSobreNome(nome);
                if (pssList != null && pssList.size() == 1) {
                    //verifica se existe um usuario ativo para essa pessoa
                    UsrUsuario usr_ = usuarioBo.carregarPorPessoa(pssList.get(0));
                    if (usr_ != null && usr_.getUsrId() != null) {
                        usr = usr_;
                        tpaId = usr_.getTpa().getTpaId();
                        senhaBloqueada = usr.isUsrSenhaBloqueada();
                        modo.setModoTela(ModoTela.VISUALIZACAO);
                    } else {
                        usr = new UsrUsuario();
                        usr.setPss(pssList.get(0));
                        modo.setModoTela(ModoTela.INSERCAO);
                    }
                    nome = pssList.get(0).getPssNome() + " " + pssList.get(0).getPssSobrenome();
                    //como não é necessario abrir o dialog, ele não "seta" o request para sucesso
                } else {
                    if (pssList != null && pssList.size() > 1) {
                        ContatoBo contatoBo = new ContatoBo();
                        EnderecoBo enderecoBo = new EnderecoBo();
                        for (PssPessoa p : pssList) {
                            List<CntContato> cList = contatoBo.listarPorPessoa(p);
                            if (cList != null && !cList.isEmpty()) {
                                p.setContatosString("");
                                for (CntContato c : cList) {
                                    p.setContatosString(p.getContatosString() + c.getCntDescricao() + "*");
                                }
                                if (!p.getContatosString().trim().isEmpty()) {
                                    p.setContatosString(p.getContatosString().substring(0, p.getContatosString().length() - 1));
                                }
                            }
                            List<EndEndereco> eList = enderecoBo.listarPorPessoa(p);
                            if (eList != null && !eList.isEmpty()) {
                                p.setEnderecosString("");
                                for (EndEndereco e : eList) {
                                    p.setEnderecosString(p.getEnderecosString() + "Logr: " + e.getLgr().getLgrDescricao() + " nº " + e.getEndNumero() + " " + e.getLgr().getLgrCidade() + "/" + e.getLgr().getLgrEstado() + "*");
                                }
                                if (!p.getEnderecosString().trim().isEmpty()) {
                                    p.setEnderecosString(p.getEnderecosString().substring(0, p.getEnderecosString().length() - 1));
                                }
                            }
                        }
                    }
                    RequestContext request = RequestContext.getCurrentInstance();
                    request.addCallbackParam("sucesso", true);
                }
            } else {
                throw new Exception("Para pesquisar insira pelo menos 3 letras do usuário");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void carregarUsuario() {
        try {
            usr = new UsrUsuario();
            if (pss != null && pss.getPssId() != null) {
                UsrUsuario usr_ = usuarioBo.carregarPorPessoa(pss);
                if (usr_ != null && usr_.getUsrId() != null) {
                    usr = usr_;
                    tpaId = usr_.getTpa().getTpaId();
                    senhaBloqueada = usr.isUsrSenhaBloqueada();
                    modo.setModoTela(ModoTela.VISUALIZACAO);
                } else {
                    tpaId = null;
                    usr.setPss(pss);
                    modo.setModoTela(ModoTela.INSERCAO);
                }
                nome = usr.getPss().getPssNome() + " " + usr.getPss().getPssSobrenome();
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }

    }

    public void recarregarTpaList() {
        tpaList = new ArrayList<SelectItem>();
        List<TpaTipoAcesso> lista = tipoAcessoBo.listarTodos();
        if (lista != null && !lista.isEmpty()) {
            for (TpaTipoAcesso t : lista) {
                tpaList.add(new SelectItem(t.getTpaId(), t.getTpaDescricao()));
            }
        }
    }

    /**
     * Getters and Setters
     */
    public UsrUsuario getUsr() {
        return usr;
    }

    public void setUsr(UsrUsuario usr) {
        this.usr = usr;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public List<PssPessoa> getPssList() {
        return pssList;
    }

    public void setPssList(List<PssPessoa> pssList) {
        this.pssList = pssList;
    }

    public PessoaBo getPessoaBo() {
        return pessoaBo;
    }

    public void setPessoaBo(PessoaBo pessoaBo) {
        this.pessoaBo = pessoaBo;
    }

    public UsuarioBo getUsuarioBo() {
        return usuarioBo;
    }

    public void setUsuarioBo(UsuarioBo usuarioBo) {
        this.usuarioBo = usuarioBo;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    public Integer getPssId() {
        return pssId;
    }

    public void setPssId(Integer pssId) {
        this.pssId = pssId;
    }

    public TipoAcessoBo getTipoAcessoBo() {
        return tipoAcessoBo;
    }

    public void setTipoAcessoBo(TipoAcessoBo tipoAcessoBo) {
        this.tipoAcessoBo = tipoAcessoBo;
    }

    public List<SelectItem> getTpaList() {
        if (tpaList == null || tpaList.isEmpty()) {
            recarregarTpaList();
        }
        return tpaList;
    }

    public void setTpaList(List<SelectItem> tpaList) {
        this.tpaList = tpaList;
    }

    public Integer getTpaId() {
        return tpaId;
    }

    public void setTpaId(Integer tpaId) {
        this.tpaId = tpaId;
    }

    public List<UsrUsuario> getUsrList() {
        return usrList;
    }

    public void setUsrList(List<UsrUsuario> usrList) {
        this.usrList = usrList;
    }

    public boolean isSenhaBloqueada() {
        return senhaBloqueada;
    }

    public void setSenhaBloqueada(boolean senhaBloqueada) {
        this.senhaBloqueada = senhaBloqueada;
    }
}
