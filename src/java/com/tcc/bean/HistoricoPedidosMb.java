/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

import com.tcc.bo.PedidoBo;
import com.tcc.bo.StatusBo;
import com.tcc.model.PedPedido;
import com.tcc.model.SttStatus;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author paulo.castro
 */
@ManagedBean
@ViewScoped
public class HistoricoPedidosMb extends BaseMb implements Serializable {

    private CartesianChartModel modeloGrafico = new CartesianChartModel();
    private CartesianChartModel modeloGraficoDia = new CartesianChartModel();
    private ModoTela modo = new ModoTela();
    private PedidoBo pedidoBo = new PedidoBo();
    private StatusBo statusBo = new StatusBo();
    private Integer max = 0;

    public HistoricoPedidosMb() {
    }

    @PostConstruct
    public void recarregaGraficos() {
        modeloGrafico = new CartesianChartModel();
        modeloGraficoDia = new CartesianChartModel();
        //lista os status
        recarregaGraficoMes();
        recarregaGraficoDia();
    }

    private void recarregaGraficoMes() {
        List<SttStatus> sttList = statusBo.listarTodos();
        if (sttList != null && !sttList.isEmpty()) {
            for (SttStatus s : sttList) {
                //cria uma serie para cada status
                ChartSeries serie = new ChartSeries(s.getSttDescricao());
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                //joga  4 meses atras
                c.add(Calendar.MONTH, -4);
                for (int i = 0; i < 5; i++) {
                    SimpleDateFormat mes = new SimpleDateFormat("MM");
                    SimpleDateFormat ano = new SimpleDateFormat("yyyy");
                    String mesString = mes.format(c.getTime());
                    String anoString = ano.format(c.getTime());
                    Integer qnt = pedidoBo.listarQtdPedidosMesAno(c, s);
                    if (qnt > max) {
                        max = qnt;
                    }
                    serie.set(retornaMesString(mesString) + "/" + anoString, qnt);
                    c.add(Calendar.MONTH, 1);
                }
                modeloGrafico.addSeries(serie);
            }
        }
    }

    private void recarregaGraficoDia() {
        List<PedPedido> pedidosDia = pedidoBo.listarPedidosDoDia(new Date());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (pedidosDia != null && !pedidosDia.isEmpty()) {
            List<SttStatus> sttList = statusBo.listarTodos();
            if (sttList != null && !sttList.isEmpty()) {
                for (SttStatus s : sttList) {
                    //cria uma serie para cada status
                    ChartSeries serie = new ChartSeries(s.getSttDescricao());
                    Integer quantidade = 0;
                    for (PedPedido p : pedidosDia) {
                        if (p.getStt().getSttId().equals(s.getSttId())) {
                            quantidade++;
                        }
                    }
                    serie.set(df.format(new Date()), quantidade);
                    modeloGraficoDia.addSeries(serie);
                }
            }
        }
    }

    public String retornaMesString(String mes) {
        String retorno = "Janeiro";
        if (mes.equalsIgnoreCase("02") || mes.equalsIgnoreCase("2")) {
            retorno = "Fevereiro";
        } else if (mes.equalsIgnoreCase("03") || mes.equalsIgnoreCase("3")) {
            retorno = "Março";
        } else if (mes.equalsIgnoreCase("04") || mes.equalsIgnoreCase("4")) {
            retorno = "Abril";
        } else if (mes.equalsIgnoreCase("05") || mes.equalsIgnoreCase("5")) {
            retorno = "Maio";
        } else if (mes.equalsIgnoreCase("06") || mes.equalsIgnoreCase("6")) {
            retorno = "Junho";
        } else if (mes.equalsIgnoreCase("07") || mes.equalsIgnoreCase("7")) {
            retorno = "Julho";
        } else if (mes.equalsIgnoreCase("08") || mes.equalsIgnoreCase("8")) {
            retorno = "Agosto";
        } else if (mes.equalsIgnoreCase("09") || mes.equalsIgnoreCase("9")) {
            retorno = "Setembro";
        } else if (mes.equalsIgnoreCase("10")) {
            retorno = "Outubro";
        } else if (mes.equalsIgnoreCase("11")) {
            retorno = "Novembro";
        } else if (mes.equalsIgnoreCase("12")) {
            retorno = "Dezembro";
        }
        return retorno;
    }

    /**
     * Getters and Setters
     *
     */
    public CartesianChartModel getModeloGrafico() {
        return modeloGrafico;
    }

    public void setModeloGrafico(CartesianChartModel modeloGrafico) {
        this.modeloGrafico = modeloGrafico;
    }

    public ModoTela getModo() {
        return modo;
    }

    public void setModo(ModoTela modo) {
        this.modo = modo;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public CartesianChartModel getModeloGraficoDia() {
        return modeloGraficoDia;
    }

    public void setModeloGraficoDia(CartesianChartModel modeloGraficoDia) {
        this.modeloGraficoDia = modeloGraficoDia;
    }
}
