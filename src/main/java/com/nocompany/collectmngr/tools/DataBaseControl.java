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
    public final String COLLECT_FIG = "COLECCION C INNER JOIN FIGURAS F ON C.ID_FIG = F.ID_FIG";
    
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
    
    public String get2(String table, String data, String where){
        String query = "SELECT "+data+" FROM "+table+" "+where;
        return toJSONString(l.selectWithHeaders(query));
    }
    
    public String get(String table, String data){
        String query = "SELECT "+data+" FROM "+table;
        return toJSONString(l.selectColumns(query));
    }
    
    public String getTotals(){
        String query = "SELECT SUM(X.PRECIO) AS 'total' FROM (SELECT DISTINCT(SERIAL), PRECIO FROM FIGURAS "
            + "F INNER JOIN COLECCION C ON F.ID_FIG = C.ID_FIG) X UNION SELECT COUNT(DISTINCT(SERIAL)) "
            + "FROM FIGURAS UNION SELECT COUNT(*) FROM FIGURAS";
        return toJSONString(l.selectColumns(query));
    }
    
    public String getData(){
        String query = "SELECT MONTH(X.FECHA_ADQ) AS MES, SUM(X.PRECIO) AS 'MONTO' FROM (SELECT DISTINCT(SERIAL), "
                + "PRECIO, FECHA_ADQ FROM FIGURAS F INNER JOIN COLECCION C ON F.ID_FIG = C.ID_FIG WHERE YEAR(FECHA_ADQ) = 2020) "
                + "X GROUP BY MONTH(X.FECHA_ADQ)";
        return toJSONString(l.selectWithHeaders(query));
    }
    
    public final int PURCHASES_PER_MONTH = 1;
    public final int AMOUNT_PER_MONTH = 0;
    
    
    public String getData(int value){
        String [] querys = {
            "SELECT MONTH(X.FECHA_ADQ) AS MES, SUM(X.PRECIO) AS 'MONTO' FROM (SELECT DISTINCT(SERIAL), PRECIO, FECHA_ADQ "
                + "FROM FIGURAS F INNER JOIN COLECCION C ON F.ID_FIG = C.ID_FIG WHERE YEAR(FECHA_ADQ) = YEAR(GETDATE())) X GROUP BY MONTH(X.FECHA_ADQ)",
            "SELECT MONTH(C.FECHA_ADQ) AS 'mes', F.SERIAL AS 'serial', CONCAT(F.SUB_NOM,' ',F.NOM_FIG) AS 'figura', DAY(C.FECHA_ADQ) AS 'dia', C.PRECIO AS 'precio' "
                + "FROM FIGURAS F INNER JOIN COLECCION C ON F.ID_FIG = C.ID_FIG WHERE YEAR(C.FECHA_ADQ) = YEAR(GETDATE()) ORDER BY FECHA_ADQ"
    
        };
        return toJSONString(l.selectWithHeaders(querys[value]));
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
    
    private String toJSONString(List<String[]> list) {
        String res = "[";
        String[] head = list.remove(0);
        for(Iterator<String[]> i = list.iterator(); i.hasNext();){
            String[] current = i.next();
            res += "{";
            for (int j = 0; j < current.length; j++) {
                res += "\""+head[j]+"\":\""+current[j]+"\"";
                if(j<current.length-1){
                    res += ", ";
                }
            }
            res += "}";
            if(i.hasNext()){
                res += ", ";
            }
        }
        return res+"]";
    }
    
    /*public static void main (String... args){
        DataBaseControl db = new DataBaseControl();
        System.out.println("\"recent\"="+db.get2(db.COLLECT_FIG, "TOP 5 C.ID_FIG, C.FECHA_ADQ, F.NOM_FIG, F.SUB_NOM, F.SERIAL, F.FACCION", "ORDER BY C.FECHA_ADQ DESC"));
    }*/
}
