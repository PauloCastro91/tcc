/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.ContatoBo;
import com.tcc.bo.EnderecoBo;
import com.tcc.bo.LogradouroBo;
import com.tcc.bo.PessoaBo;
import com.tcc.model.CntContato;
import com.tcc.model.EndEndereco;
import com.tcc.model.LgrLogradouro;
import com.tcc.model.PssPessoa;
import com.tcc.util.HibernateUtil;
import com.tcc.util.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
public class PessoaMb extends BaseMb implements Serializable {
    //variaveis padroes

    private ModoTela modo = new ModoTela();
    private PessoaBo pessoaBo = new PessoaBo();
    //variaveis de contato
    private List<CntContato> contatos = new ArrayList<CntContato>();
    private CntContato cntContato = new CntContato();
    private ContatoBo contatoBo = new ContatoBo();
    private String cntDescricao;
    private boolean telSp;
    //variaveis de endereço
    private EndEndereco endereco = new EndEndereco();
    private EnderecoBo enderecoBo = new EnderecoBo();
    private EndEndereco enderecoMemoria = new EndEndereco();
    private List<EndEndereco> endList = new ArrayList<EndEndereco>();
    private List<EndEndereco> endDeletadosList = new ArrayList<EndEndereco>();
    private String cepEnderecoNovo;
    //variaveis de pessoa
    private PssPessoa pessoa = new PssPessoa();
    private List<PssPessoa> pessoas;
    private String pesquisaNome;
    private String pesquisaSobrenome;
    private Integer pssId;
    private String telaOrigem;

    public PessoaMb() {
        pessoa = new PssPessoa();
        getPessoas();
    }

    public void verificarContato() {
        System.out.println("Teste");
    }

    public void limpar() {
        pessoa = new PssPessoa();
        pessoas = new ArrayList<PssPessoa>();
        contatos = new ArrayList<CntContato>();
        endList = new ArrayList<EndEndereco>();
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void cancelar() {
        limpar();
    }

    public void novo() {
        pessoa = new PssPessoa();
        pessoas = new ArrayList<PssPessoa>();
        contatos = new ArrayList<CntContato>();
        endList = new ArrayList<EndEndereco>();
        endDeletadosList = new ArrayList<EndEndereco>();
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void carregar() {
        if (pssId != null && pssId > 0) {
            pessoa = new PssPessoa();
            pessoa = pessoaBo.carregar(pssId);
            pessoas = new ArrayList<PssPessoa>();
            recarregarContatos();
            recarregarEnderecos();
            modo.setModoTela(ModoTela.VISUALIZACAO);
        }
    }

    public void validar() throws Exception {
        if (pessoa.getPssNome() == null || pessoa.getPssNome().trim().isEmpty() || pessoa.getPssNome().length() < 3) {
            throw new Exception("O Nome deve conter 3 ou mais letras!!");
        }
        if (pessoa.getPssSobrenome() == null || pessoa.getPssSobrenome().trim().isEmpty() || pessoa.getPssSobrenome().length() < 3) {
            throw new Exception("O Sobrenome deve conter 3 ou mais letras!!");
        }
        if (contatos == null || contatos.isEmpty()) {
            throw new Exception("Insira pelo menos um contato!");
        }
        if (endList == null || endList.isEmpty()) {
            throw new Exception("Insira pelo menos um endereço!");
        }
    }

    public String salvar() {
        try {
            validar();
            for (CntContato c : contatos) {
                c.setPss(pessoa);
            }
            pessoa.setContatos(contatos);
            //deletados
            if (endDeletadosList != null && !endDeletadosList.isEmpty()) {
                enderecoBo.deletarEmLote(endDeletadosList);
                endDeletadosList = new ArrayList<EndEndereco>();
            }
            for (EndEndereco e : endList) {
                e.setPss(pessoa);
            }
            pessoa.setEnderecos(endList);
            verificarDuplicidade();
            if (pessoa.getPssId() == null) {
                pessoa = pessoaBo.inserir(pessoa);
                JPAUtil.addMensagemSucesso(JPAUtil.INSERIDO_COM_SUCESSO);
            } else {
                pessoaBo.alterar(pessoa);
                JPAUtil.addMensagemSucesso(JPAUtil.ALTERADO_COM_SUCESSO);
            }
            modo.setModoTela(ModoTela.VISUALIZACAO);
            if (telaOrigem != null && telaOrigem.equalsIgnoreCase("funcionarios")) {
                return "funcionario.xhtml?faces-redirect=true&pssId=" + pessoa.getPssId();
            }
            if (telaOrigem != null && telaOrigem.equalsIgnoreCase("pedidos")) {
                return "pedido.xhtml?faces-redirect=true&pssId=" + pessoa.getPssId();
            }
            if (telaOrigem != null && telaOrigem.equalsIgnoreCase("usuarios")) {
                return "usuario.xhtml?faces-redirect=true&pssId=" + pessoa.getPssId();
            }
            return "";
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
            return "";
        }
    }

    public void verificarDuplicidade() throws Exception {

        List<PssPessoa> pessoasCadastradas = pessoaBo.carregaPorNomeSobrenome(pessoa.getPssNome(), pessoa.getPssSobrenome());
        if (pessoasCadastradas != null && !pessoasCadastradas.isEmpty()) {
            for (PssPessoa p : pessoasCadastradas) {
                if ((pessoa.getPssId() == null) || (!pessoa.getPssId().equals(p.getPssId()))) {
                    List<CntContato> contatosBanco = contatoBo.listarPorPessoa(p);
                    boolean mesmoContato = false;
                    if (contatosBanco != null && !contatosBanco.isEmpty()) {
                        for (CntContato c : contatosBanco) {
                            for (CntContato contato_ : contatos) {
                                if (c.getCntDescricao().equalsIgnoreCase(contato_.getCntDescricao())) {
                                    mesmoContato = true;
                                    break;
                                }
                            }
                        }
                    }
                    List<EndEndereco> enderecosBanco = enderecoBo.listarPorPessoa(p);
                    boolean mesmoEndereco = false;
                    if (enderecosBanco != null && !enderecosBanco.isEmpty()) {
                        for (EndEndereco e : enderecosBanco) {
                            for (EndEndereco end_ : endList) {
                                if ((e.getEndNumero().equalsIgnoreCase(end_.getEndNumero()))
                                        && (e.getLgr().getLgrCep().equalsIgnoreCase(end_.getLgr().getLgrCep()))
                                        && (e.getLgr().getLgrDescricao().trim().equalsIgnoreCase(end_.getLgr().getLgrDescricao().trim()))) {
                                    mesmoEndereco = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (mesmoContato && mesmoEndereco) {
                        throw new Exception("Já existe uma pessoa com o mesmo Nome, Sobrenome, Endereço e Contato que esta!");
                    }
                }
            }
        }
    }

    public void pesquisar() {
        try {
            pessoas = new ArrayList<PssPessoa>();
            if ((pesquisaNome == null || pesquisaNome.trim().isEmpty()) && (pesquisaSobrenome == null || pesquisaSobrenome.trim().isEmpty())) {
                throw new Exception("Insira pelo menos o nome ou o sobrenome para a busca!!");
            }
            pessoas = pessoaBo.pesquisarPorNomeSobrenome(pesquisaNome, pesquisaSobrenome);
            //caso busca não retorne resultados dá um erro
            if (pessoas == null || pessoas.isEmpty()) {
                throw new Exception(JPAUtil.PESQUISA_SEM_RESULTADOS);
            } //caso retorne somente um resultado, já é relacionado a variavel pessoa
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }
    //contato

    public void recarregarContatos() {
        contatos = new ArrayList<CntContato>();
        if (pessoa != null && pessoa.getPssId() != null) {
            contatos = contatoBo.listarPorPessoa(pessoa);
        }
    }

    public void recarregarEnderecos() {
        endDeletadosList = new ArrayList<EndEndereco>();
        endList = new ArrayList<EndEndereco>();
        if (pessoa != null && pessoa.getPssId() != null) {
            endList = enderecoBo.listarPorPessoa(pessoa);
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
            for (CntContato c : contatos) {
                if (c.getCntDescricao().equalsIgnoreCase(cntDescricao)) {
                    throw new Exception("Esse contato já foi cadastrado para esse usuário");
                }
            }
            CntContato cnt = new CntContato();
            cnt.setCntDescricao(cntDescricao);
            contatos.add(cnt);
            cntDescricao = "";
            JPAUtil.addMensagemSucesso("Contato adicionado!");
            RequestContext request = RequestContext.getCurrentInstance();
            request.addCallbackParam("sucesso", true);
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void excluirContato() {
        if (cntContato != null) {
            Iterator<CntContato> it = contatos.iterator();
            while (it.hasNext()) {
                CntContato c = (CntContato) it.next();
                if (c.getCntDescricao().equalsIgnoreCase(cntContato.getCntDescricao())) {
                    it.remove();
                }
            }

            //contatos.remove(cntContato);
            if (cntContato.getCntId() != null) {
                contatoBo.excluir(cntContato);
            }
            cntContato = new CntContato();
        }
    }
    //endereco

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

    /**
     * GET & SET
     */
    public PssPessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(PssPessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<PssPessoa> getPessoas() {
        if (pessoas == null) {
            Session session = new HibernateUtil().openSession();
            try {
                session.getTransaction().begin();
                pessoas = session.createQuery("select p from PssPessoa p").list();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
        }
        return pessoas;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public String getPesquisaNome() {
        return pesquisaNome;
    }

    public void setPesquisaNome(String pesquisaNome) {
        this.pesquisaNome = pesquisaNome;
    }

    public String getPesquisaSobrenome() {
        return pesquisaSobrenome;
    }

    public void setPesquisaSobrenome(String pesquisaSobrenome) {
        this.pesquisaSobrenome = pesquisaSobrenome;
    }

    public Integer getPssId() {
        return pssId;
    }

    public void setPssId(Integer pssId) {
        this.pssId = pssId;
    }

    public List<CntContato> getContatos() {
        return contatos;
    }

    public void setContatos(List<CntContato> contatos) {
        this.contatos = contatos;
    }

    public CntContato getCntContato() {
        return cntContato;
    }

    public void setCntContato(CntContato cntContato) {
        this.cntContato = cntContato;
    }

    public String getCntDescricao() {
        return cntDescricao;
    }

    public void setCntDescricao(String cntDescricao) {
        this.cntDescricao = cntDescricao;
    }

    public EndEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(EndEndereco endereco) {
        this.endereco = endereco;
    }

    public List<EndEndereco> getEndList() {
        return endList;
    }

    public void setEndList(List<EndEndereco> endList) {
        this.endList = endList;
    }

    public String getCepEnderecoNovo() {
        return cepEnderecoNovo;
    }

    public void setCepEnderecoNovo(String cepEnderecoNovo) {
        this.cepEnderecoNovo = cepEnderecoNovo;
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

    public boolean isTelSp() {
        return telSp;
    }

    public void setTelSp(boolean telSp) {
        this.telSp = telSp;
    }

    public String getTelaOrigem() {
        return telaOrigem;
    }

    public void setTelaOrigem(String telaOrigem) {
        this.telaOrigem = telaOrigem;
    }
}
