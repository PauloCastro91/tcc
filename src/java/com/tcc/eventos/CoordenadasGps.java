/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.eventos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author paulo
 */
@XmlRootElement
public class CoordenadasGps implements Serializable{

    private Integer id;
    private String latitude;
    private String longitude;

    public CoordenadasGps(String lat, String lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

  
}
