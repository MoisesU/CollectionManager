/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nocompany.collectmngr.tools;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author MoisésUlises
 */
public class DataBaseControl{
    private final Linker l;
    private final String SERVER = "localhost:1433";
    private final String DATABASE = "COLECT";
    public final String DETAILS = "DETAILS";
    public final String FIGS = "FIGURAS";
    public final String COLLECT = "COLECCION";
    
    public DataBaseControl(){
        this.l = new Linker(this.SERVER, this.DATABASE);
    }
    
    public int add(Figura f){
        //INSERT INTO FIGURAS VALUES ('BLURR', 'HYPERFIRE &', 'PW-TR1', 'PW-TR1_BLURR.png', 1, 2017, 'DELUXE', 'AUTOBOT', 3)
        String values = "'"+f.getPersonaje()+"', '"+f.getSubNombre()+"', '"+f.getSerial()+"', '"+f.getSerial()+"_"+f.getPersonaje()+".png',"
                + " 1, 2019, '"+f.getClase()+"', '"+f.getFaccion()+"', "+f.getDet();
        int id = l.insert("INSERT INTO FIGURAS VALUES("+values+")");
        if(id>0)
            f.setId(id);
        return id;
    }
    
    public int add(Adquisicion adq){
        String values = adq.getPrecio()+", CONVERT(DATE, '"+adq.getFecha()+"', 23), '"+adq.getLugar()+"', '"+adq.getEstado()+"', "
                + ""+adq.getCalif()+", "+adq.getId_fig();
        return l.insert("INSERT INTO COLECCION VALUES("+values+")");
    }
    
    public String get(String table, String data, String where){
        String query = "SELECT "+data+" FROM "+table+" "+where;
        return toJSONString(l.selectColumns(query));
    }
    
    public String get(String table, String data){
        String query = "SELECT "+data+" FROM "+table;
        return toJSONString(l.selectColumns(query));
    }
    
    public void closeDB(){
        l.close();
    }

    private String toJSONString(List<String>[] list) {
        String res = "";
        List<String> head = list[0];
        for(int i = 1; i<list.length; i++){
            res += "\""+head.get(i-1)+"\":[\""+ String.join("\", \"", list[i])+"\"]";
            if(i<list.length-1){
                res += ", ";
            }
        }
        return res;
    }
    
    /*public static void main (String... args){
        DataBaseControl db = new DataBaseControl();
        List<String[]> l = db.l.selectWithHeaders("SELECT * FROM DETAILS");
        for(String [] s: l){
            for(String s1 : s){
                System.out.print(s1+"\t");
            }
            System.out.print("\n");
        }
        System.out.println(db.get(db.DETAILS, "ID_DET, PREFIX, SHORT", "WHERE PREFIX NOT LIKE 'SS-'"));
    }*/
}
