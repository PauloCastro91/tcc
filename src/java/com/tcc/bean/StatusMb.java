/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

/**
 *
 * @author paulo.castro
 */
import com.tcc.bo.StatusBo;
import com.tcc.model.SttStatus;
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
public class StatusMb extends BaseMb implements Serializable {

    private SttStatus stt = new SttStatus();
    private ModoTela modo = new ModoTela();
    private StatusBo statusBo = new StatusBo();
    private List<SttStatus> sttList = new ArrayList<SttStatus>();
    private String descricao;

    public StatusMb() {
    }

    public void cancelar() {
        limpar();
    }

    public void limpar() {
        stt = new SttStatus();
        descricao = "";
        recarregarLista();
        modo.setModoTela(ModoTela.VISUALIZACAO);
    }

    public void novo() {
        stt = new SttStatus();
        descricao = "";
        modo.setModoTela(ModoTela.INSERCAO);
    }

    public void alterar() {
        modo.setModoTela(ModoTela.ALTERACAO);
    }

    public void validarDuplicidade() throws Exception {
        List<SttStatus> mesmaDescricao = statusBo.carrgar(descricao);
        if (mesmaDescricao != null && !mesmaDescricao.isEmpty()) {
            if (stt != null && stt.getSttId() != null) {
                boolean jaUsuada = false;
                for (SttStatus c : mesmaDescricao) {
                    if ((!c.getSttId().equals(stt.getSttId())) && (stt.getSttDescricao().trim().equalsIgnoreCase(stt.getSttDescricao().trim()))) {
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
                stt.setSttDescricao(descricao);
                validarDuplicidade();
                if (stt.getSttId() != null) {
                    statusBo.alterar(stt);
                } else {
                    statusBo.inserir(stt);
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
            sttList = new ArrayList<SttStatus>();
            sttList = statusBo.listarTodos();
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void pesquisarCargo() {
        try {
            sttList = new ArrayList<SttStatus>();
            if ((descricao != null) && (!descricao.trim().isEmpty()) && (descricao.trim().length() >= 2)) {
                sttList = new ArrayList<SttStatus>();
                sttList = statusBo.carrgar(descricao);
            } else {
                throw new Exception("Insira Pelo menos 2 letras para busca!");
            }
        } catch (Exception e) {
            JPAUtil.addMensagemErro(e.getMessage());
        }
    }

    public void inativar() {
        statusBo.inativar(stt);
        stt = new SttStatus();
        modo = new ModoTela();
    }

    public void ativar() {
        statusBo.ativar(stt);
        stt = new SttStatus();
        modo = new ModoTela();
    }

    /**
     * Getters and Setters
     */
    public SttStatus getStt() {
        return stt;
    }

    public void setStt(SttStatus stt) {
        this.stt = stt;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public StatusBo getStatusBo() {
        return statusBo;
    }

    public void setStatusBo(StatusBo statusBo) {
        this.statusBo = statusBo;
    }

    public List<SttStatus> getSttList() {
        return sttList;
    }

    public void setSttList(List<SttStatus> sttList) {
        this.sttList = sttList;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
