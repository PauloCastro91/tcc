/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author paulo.castro
 */
@Entity
@Table(name = "ped_pedido")
public class PedPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ped_id")
    private Integer pedId;
    @JoinColumn(name = "pss_id", referencedColumnName = "pss_id")
    @ManyToOne(optional = false)
    private PssPessoa pss;
    @JoinColumn(name = "fun_id", referencedColumnName = "fun_id")
    @ManyToOne(optional = true)
    private FunFuncionario fun;
    @JoinColumn(name = "end_id", referencedColumnName = "end_id")
    @ManyToOne(optional = false)
    private EndEndereco end;
    @Column(name = "ped_valor")
    private Double pedValor;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ped")
    private Collection<PpdProdutoPedido> produtosPedido;
    @JoinColumn(name = "stt_id", referencedColumnName = "stt_id")
    @ManyToOne(optional = true)
    private SttStatus stt;
    @Column(name = "ped_dataCadastro", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pedDataCadastro;
    @Column(name = "ped_dataAlteracao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date pedDataAlteracao;

    public PedPedido() {
    }

    public PedPedido(Integer pedId) {
        this.pedId = pedId;
    }

    public Integer getPedId() {
        return pedId;
    }

    public void setPedId(Integer pedId) {
        this.pedId = pedId;
    }

    public PssPessoa getPss() {
        return pss;
    }

    public void setPss(PssPessoa pss) {
        this.pss = pss;
    }

    public FunFuncionario getFun() {
        return fun;
    }

    public void setFun(FunFuncionario fun) {
        this.fun = fun;
    }

    public EndEndereco getEnd() {
        return end;
    }

    public void setEnd(EndEndereco end) {
        this.end = end;
    }

    public Double getPedValor() {
        return pedValor;
    }

    public void setPedValor(Double pedValor) {
        this.pedValor = pedValor;
    }

    public SttStatus getStt() {
        return stt;
    }

    public void setStt(SttStatus stt) {
        this.stt = stt;
    }

    public String getPedDataCadastroString() {
        if (pedDataCadastro != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(pedDataCadastro);
        } else {
            return "";
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.pedId != null ? this.pedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PedPedido other = (PedPedido) obj;
        if (this.pedId != other.pedId && (this.pedId == null || !this.pedId.equals(other.pedId))) {
            return false;
        }
        return true;
    }

    public Collection<PpdProdutoPedido> getProdutosPedido() {
        return produtosPedido;
    }

    public void setProdutosPedido(Collection<PpdProdutoPedido> produtosPedido) {
        this.produtosPedido = produtosPedido;
    }

    public Date getPedDataCadastro() {
        return pedDataCadastro;
    }

    public void setPedDataCadastro(Date pedDataCadastro) {
        this.pedDataCadastro = pedDataCadastro;
    }

    public Date getPedDataAlteracao() {
        return pedDataAlteracao;
    }

    public void setPedDataAlteracao(Date pedDataAlteracao) {
        this.pedDataAlteracao = pedDataAlteracao;
    }

    @Override
    public String toString() {
        String produtosPedidoString = "{";
        if (produtosPedido != null && !produtosPedido.isEmpty()) {
            for (PpdProdutoPedido ppd : produtosPedido) {
                produtosPedidoString = produtosPedidoString + String.valueOf(ppd.getPdt().getPdtId()) + ",";
            }
            produtosPedidoString = produtosPedidoString + "}  ";
        }
        String pssIdString = "";
        if (pss != null && pss.getPssId() != null) {
            pssIdString = String.valueOf(pss.getPssId());
        }
        String funId = "";
        if (fun != null && fun.getFunId() != null) {
            funId = String.valueOf(fun.getFunId());
        }
        String endId = "";
        if (end != null && end.getEndId() != null) {
            endId = String.valueOf(end.getEndId());
        }
        String sttId = "";
        if (stt != null && stt.getSttId() != null) {
            sttId = String.valueOf(stt.getSttId());
        }
        String data = "";
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (pedDataCadastro != null) {
            data = df.format(pedDataCadastro);
        }
        String dataAlt = "";
        if (pedDataAlteracao != null) {
            dataAlt = df.format(pedDataAlteracao);
        }
        return "PedPedido{" + "pedId=" + pedId + ", pss=" + pssIdString + ", fun=" + funId + ", end=" + endId + ", pedValor=" + pedValor + ", produtosPedido=" + produtosPedidoString + ", pedDataCadastro=" + data + ", pedDataAlteracao=" + dataAlt + "}'";
    }
}
