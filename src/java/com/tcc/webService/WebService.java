/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.webService;

import com.tcc.bo.UsuarioBo;
import com.tcc.eventos.CoordenadasGps;
import com.tcc.model.UsrUsuario;
import com.tcc.util.Encripta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author paulo
 */
@Path("/eventos")
public class WebService {

    private static final Map<Integer, CoordenadasGps> eventosMap;
    private UsuarioBo usuarioBo = new UsuarioBo();

    static {
        eventosMap = new HashMap<Integer, CoordenadasGps>();

        CoordenadasGps b1 = new CoordenadasGps();
        b1.setId(1);
        b1.setLatitude("41.381542");
        b1.setLongitude("2.122893");
        eventosMap.put(b1.getId(), b1);

        CoordenadasGps b2 = new CoordenadasGps();
        b2.setId(2);
        b2.setLatitude("42.382522");
        b2.setLongitude("2.122813");
        eventosMap.put(b2.getId(), b2);
    }

    @GET
    @Produces("application/json")
    public List<CoordenadasGps> getCoordenadas() {
        return new ArrayList<CoordenadasGps>(eventosMap.values());
    }

    @POST
    @Path("/logar")
    @Consumes("application/json")
    @Produces("text/plain")
    public String logar(@PathParam("login") String login, @PathParam("senha") String senha) {
        try {
            String id = "";
            UsrUsuario usr = usuarioBo.pesquisarPorLoginSenha(login, Encripta.criptografaSenha(senha));
            if (usr != null && usr.getUsrId() != null && !usr.isUsrSenhaBloqueada()) {
                return String.valueOf(usr.getUsrId());
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @POST
    @Path("/post")
    @Consumes("text/json")
    @Produces("text/plain")
    public String adiciona(Object latLongGPS) {
        if(latLongGPS!=null){
            
        }
       //Object get = eventosMap.get(latLongGPS.getId());
       // CoordenadasGps get = eventosMap.get(latLongGPS.getId());
    //    if (get != null) {
//            get.setLatitude(latLongGPS.getLatitude());
//            get.setLongitude(latLongGPS.getLongitude());
//            get.addCoordenadas(latLongGPS.getLatitude(), latLongGPS.getLongetude());
//        } else {
//            latLongGPS.setId(latLongGPS.getId());
//            eventosMap.put(latLongGPS.getId(), latLongGPS);
//        }
        return "Adicionado com sucesso.";
    }
}
