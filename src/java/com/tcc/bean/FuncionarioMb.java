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
import com.tcc.bo.FuncionarioBo;
import com.tcc.bo.PessoaBo;
import com.tcc.model.CntContato;
import com.tcc.model.CrgCargo;
import com.tcc.model.EndEndereco;
import com.tcc.model.FunFuncionario;
import com.tcc.model.PssPessoa;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class FuncionarioMb extends BaseMb implements Serializable {

    private FunFuncionario funcionario = new FunFuncionario();
    private String nome;
    private Integer pssId = 0;
    private Integer crgId = 0;
    private ModoTela modo = new ModoTela();
    private List<PssPessoa> pssList = new ArrayList<PssPessoa>();
    private PessoaBo pessoaBo = new PessoaBo();
    private PssPessoa pessoa = new PssPessoa();
    private FuncionarioBo funcionarioBo = new FuncionarioBo();
    private List<SelectItem> crgList = new ArrayList<SelectItem>();

    public FuncionarioMb() {
    }

    @PostConstruct
    public void carregarPessoa() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (request.getParameter("pssId") != null && !String.valueOf(request.getParameter("pssId")).isEmpty()) {
            pssId = Integer.parseInt(request.getParameter("pssId"));
            PessoaBo pessoaBo = new PessoaBo();
            PssPessoa pss = pessoaBo.carregar(pssId);
            if (pss != null && pss.getPssId() != null) {
                funcionario.setPss(pss);
                nome = pss.getPssNome() + " " + pss.getPssSobrenome();
                modo.setModoTela(ModoTela.INSERCAO);
            }
        }
    }

    public void cancelar() {
        pssId = 0;
        nome = "";
        funcionario = new FunFuncionario();
        crgId = null;
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void novo() {
        funcionario = new FunFuncionario();
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void salvar() {
        try {
            validarFuncionario();
            funcionario.setCrg(new CrgCargo(crgId));
            if (funcionario.getFunId() != null) {
                funcionarioBo.alterar(funcionario);
                JPAUtil.addMensagemSucesso(JPAUtil.ALTERADO_COM_SUCESSO);
            } else {
                funcionarioBo.inserir(funcionario);
                JPAUtil.addMensagemSucesso(JPAUtil.INSERIDO_COM_SUCESSO);
            }
            modo.setModoTela(ModoTela.VISUALIZACAO);
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void validarFuncionario() throws Exception {
        if (funcionario.getPss() == null || funcionario.getPss().getPssId() == null) {
            throw new Exception("Primeiramente pesquise uma pessoa!");
        }
        if (!funcionario.isFunAtivo() && funcionario.getFunDataFim() == null) {
            throw new Exception("No caso do funcionário não ser ativo, é obrigatório informar a data fim (desligamento)!");
        }
        if (funcionario.getFunDataFim() != null && (funcionario.getFunDataFim().before(funcionario.getFunDataInicio()))) {
            throw new Exception("A data fim (desligamento) não pode ser mais recente que a data de início!");
        }
    }

    public String telaPessoa() {
        return "pessoa.xhtml?faces-redirect=true&tela=funcionarios";
    }

    public void pesquisarPss() {
        try {
            pssList = new ArrayList<PssPessoa>();
            if (nome != null && !nome.trim().isEmpty() && nome.length() >= 3) {
                pssList = pessoaBo.pesquisarPorNomeSobreNome(nome);
                if (pssList != null && pssList.size() == 1) {
                    //verifica se existe um usuario ativo para essa pessoa
                    FunFuncionario fun = funcionarioBo.carregarFuncionarioPorPessoa(pssList.get(0));
                    if (fun != null && fun.getFunId() != null) {
                        funcionario = fun;
                        if (funcionario.getCrg() != null && funcionario.getCrg().getCrgId() != null) {
                            crgId = funcionario.getCrg().getCrgId();
                        }
                        modo.setModoTela(ModoTela.VISUALIZACAO);
                    } else {
                        funcionario = new FunFuncionario();
                        funcionario.setPss(pssList.get(0));
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
                throw new Exception("Para pesquisar insira pelo menos 3 letras do nome do Funcionário");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void recarregarCrgList() {
        crgList = new ArrayList<SelectItem>();
        CargoBo cargoBo = new CargoBo();
        List<CrgCargo> cargos = cargoBo.listarTodos();
        if (cargos != null && !cargos.isEmpty()) {
            for (CrgCargo c : cargos) {
                crgList.add(new SelectItem(c.getCrgId(), c.getCrgDescricao()));
            }
        }
    }

    public void carregarFuncionario() {
        try {
            funcionario = new FunFuncionario();
            if (pessoa != null && pessoa.getPssId() != null) {
                FunFuncionario func_ = funcionarioBo.carregarFuncionarioPorPessoa(pessoa);
                if (func_ != null && func_.getFunId() != null) {
                    funcionario = func_;
                    modo.setModoTela(ModoTela.VISUALIZACAO);
                } else {
                    funcionario.setPss(pessoa);
                    modo.setModoTela(ModoTela.INSERCAO);
                }
                nome = funcionario.getPss().getPssNome() + " " + funcionario.getPss().getPssSobrenome();
                if (funcionario.getCrg() != null && funcionario.getCrg().getCrgId() != null) {
                    crgId = funcionario.getCrg().getCrgId();
                }
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }

    }

    /**
     * Getters and Setters
     */
    public FunFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FunFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public Integer getPssId() {
        return pssId;
    }

    public void setPssId(Integer pssId) {
        this.pssId = pssId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCrgId() {
        return crgId;
    }

    public void setCrgId(Integer crgId) {
        this.crgId = crgId;
    }

    public List<PssPessoa> getPssList() {
        return pssList;
    }

    public void setPssList(List<PssPessoa> pssList) {
        this.pssList = pssList;
    }

    public List<SelectItem> getCrgList() {
        if (crgList == null || crgList.isEmpty()) {
            recarregarCrgList();
        }
        return crgList;
    }

    public void setCrgList(List<SelectItem> crgList) {
        this.crgList = crgList;
    }

    public PssPessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(PssPessoa pessoa) {
        this.pessoa = pessoa;
    }
}
