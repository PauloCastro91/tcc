/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.bean;

/**
 *
 * @author paulo.castro
 */
import com.tcc.bo.FuncionarioBo;
import com.tcc.model.FunFuncionario;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean
@ViewScoped
public class MapBean implements Serializable {

    private MapModel advancedModel;
    private Marker marker;

    public MapBean() throws Exception {
        advancedModel = new DefaultMapModel();

        //Shared coordinates  
        LatLng coord1 = new LatLng(-23.198787632542654, -45.89969873428345);
        LatLng coord2 = new LatLng(-23.209871321178117, -45.90085744857788);
        LatLng coord3 = new LatLng(-23.200404882815864, -45.88643789291382);
        LatLng coord4 = new LatLng(-23.208490840680412, -45.89081525802612);

        //Icons and Data  
        FuncionarioBo funcionarioBo = new FuncionarioBo();
        List<FunFuncionario> funList = funcionarioBo.listarFuncionariosAtivos();

        advancedModel.addOverlay(new Marker(coord1, funList.get(0).getPss().getPssNome() + " " + funList.get(0).getPss().getPssSobrenome(), null,"images/delivery.png"));
        advancedModel.addOverlay(new Marker(coord2, funList.get(1).getPss().getPssNome() + " " + funList.get(1).getPss().getPssSobrenome(), null,"images/delivery.png"));
        advancedModel.addOverlay(new Marker(coord3, funList.get(2).getPss().getPssNome() + " " + funList.get(2).getPss().getPssSobrenome(), null,"images/delivery.png"));
        advancedModel.addOverlay(new Marker(coord4, funList.get(3).getPss().getPssNome() + " " + funList.get(3).getPss().getPssSobrenome(), null,"images/delivery.png"));
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }
}
