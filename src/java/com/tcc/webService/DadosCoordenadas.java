/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.webService;

import com.tcc.eventos.CoordenadasGps;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author paulo.castro
 */
public class DadosCoordenadas {
    
    private static Map<Integer, CoordenadasGps> coordenadas = new HashMap();
    
    public void add(Integer id, String lat, String lng) {
        if (coordenadas.containsKey(id)) {
            coordenadas.get(id).setLatitude(lat);
            coordenadas.get(id).setLongitude(lng);
        } else {
            coordenadas.put(id, new CoordenadasGps(lat, lng));
        }
    }
}
