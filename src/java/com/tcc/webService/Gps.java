package com.tcc.webService;

import com.tcc.model.LatLongGPS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@RequestScoped
@Path("/gps")
public class Gps {

    static final private Map<Integer, LatLongGPS> eventosMap;

    static {
        eventosMap = new HashMap<Integer, LatLongGPS>();

        LatLongGPS latLongGPS = new LatLongGPS();
        latLongGPS.setId(1); // Código do aparelho
        latLongGPS.setLatitude("-30.030442381294925"); //Latitude atual
        latLongGPS.setLongitude("-51.20699644088745"); //Longetude atual

        latLongGPS.addCoordenadas("-20.030442381294925", "-45.20699644088745");
        latLongGPS.addCoordenadas("-30.030442381294925", "-51.20699644088745");

        eventosMap.put(latLongGPS.getId(), latLongGPS);
    }

    @GET
    @Produces("text/json")
    public List<LatLongGPS> getEventos() {
        return new ArrayList<LatLongGPS>(eventosMap.values());
    }

    @POST
    @Path("/post")
    @Consumes("text/json")
    @Produces("text/plain")
    public String adiciona(LatLongGPS latLongGPS) {
        LatLongGPS get = eventosMap.get(latLongGPS.getId());
        if (get != null) {
            get.setLatitude(latLongGPS.getLatitude());
            get.setLongitude(latLongGPS.getLongitude());
            get.addCoordenadas(latLongGPS.getLatitude(), latLongGPS.getLongitude());
        } else {
            latLongGPS.setId(latLongGPS.getId());
            eventosMap.put(latLongGPS.getId(), latLongGPS);
        }
        return "Adicionado com sucesso.";
    }

    @GET
    @Path("/get")
    @Produces("text/json")
    public List<LatLongGPS> getCoodenadas() {
        return new ArrayList<LatLongGPS>(eventosMap.values());
    }
    
    @GET
    @Path("/set/{lat}/{lng}")
    public void setCoodenadas(@PathParam("lat") String lat, @PathParam("lng") String lng) {
        System.out.println(lat);
        System.out.println(lng);
        
        new DadosCoordenadas().add(1, lat, lng);
    }
    
    
    
}
