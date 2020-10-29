/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nocompany.collectmngr.services;

import com.nocompany.collectmngr.tools.Adquisicion;
import com.nocompany.collectmngr.tools.DataBaseControl;
import com.nocompany.collectmngr.tools.Figura;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author MoisésUlises
 */

@Path("/figs")
public class MainService {
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public String getTextService(){
        return "Hola mundo desde un Rest Service";
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String postAFigure(String request){
        JSONObject req = new JSONObject(request);
        DataBaseControl db = new DataBaseControl(); 
        Figura f = new Figura(req.getString("personaje"), req.getString("clase"), req.getString("faccion"), req.getString("serial"), req.getInt("serie"));
        int resp = db.add(f);
        if(resp>0){
            resp = db.add(new Adquisicion(req.getDouble("precio"), req.getString("fecha"), req.getString("lugar"), req.getString("estado"), resp));
        }
        db.closeDB();
        return "{\"status\":"+resp+", \"figura\":\""+f.getSerial()+" "+f.getPersonaje()+"\"}";
    }
    
    @GET
    @Path("/presets")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPresets(){
        //JSONObject resp = new JSONObject();
        DataBaseControl db = new DataBaseControl(); 
        //resp.put("series", new JSONObject(db.get(db.DETAILS, "ID_DET, SHORT, PREFIX", "")));
        String resp = db.get(db.DETAILS, "ID_DET, PREFIX, SHORT", "WHERE ID_DET <8");
        resp += ", " + db.get(db.DETAILS, "ID_DET AS ID, SERIE", "WHERE PREFIX LIKE 'SS-'");
        return "{"+resp+"}";
    }
}
