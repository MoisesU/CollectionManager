/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nocompany.collectmngr.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MoisésUlises
 */
public class Linker{
   
    private Connection cnx;
    private boolean conected = false;
    
    Linker(String server, String database){
        try {
            String connectionUrl = "jdbc:sqlserver://"+server+";database="+database+";integratedSecurity=true;";
            cnx = DriverManager.getConnection(connectionUrl);
            //System.out.println("Conexion establecida...");
            conected = true;
        } catch (SQLException e){
            System.err.println("Error al conectar la BD...");
            System.err.println(e.getMessage());
        }
    }
    
    public boolean isConected() {
        return conected;
    }
    
    public void close(){
        try {
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("We could not close conexion: "+ex.getMessage());
        }
    }
    
    int insert(String query){
        try {
            Statement cmd = cnx.createStatement();
            int i = cmd.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            int x = -1;
            ResultSet rs = cmd.getGeneratedKeys();
            if (rs != null && rs.next()){
                x = rs.getInt(1);
                rs.close();
                cmd.close();
                return x;
            }
            else{
                cmd.close();
                return x;
            }
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar la funcion INSERT: ");
            System.err.println(ex.getMessage());
            return -1;
        }
    }
    
    public ArrayList<String[]> select(String query){
        ArrayList<String[]> lista = new ArrayList();
        String [] rows;
        ResultSetMetaData rsmd;
        ResultSet res = null;
        try(Statement cmd = cnx.createStatement()){
            res = cmd.executeQuery(query);
            rsmd = res.getMetaData();
            int cols = rsmd.getColumnCount();
            while(res.next()){
                rows = new String[cols];
                for (int i = 0; i < cols; i++) {
                    rows[i] = res.getString(i+1);
                }
                lista.add(rows);
            }
        } 
        catch (SQLException ex) {
            System.err.println("Error no se pudo ejecutar la consulta SELECT:\n"+query);
            System.err.println(ex.getMessage());
        }
        return lista;
    }
    
    public ArrayList<String[]> selectWithHeaders(String query){
        ArrayList<String[]> lista = new ArrayList();
        String [] rows;
        ResultSetMetaData rsmd;
        ResultSet res;
        try(Statement cmd = cnx.createStatement()){
            res = cmd.executeQuery(query);
            rsmd = res.getMetaData();
            int cols = rsmd.getColumnCount();
            String [] head = new String[cols];
            for(int i=1; i<=cols; ++i)
                head[i-1]=rsmd.getColumnName(i);
            lista.add(head);
            while(res.next()){
                rows = new String[cols];
                for (int i = 0; i < cols; i++) {
                    rows[i] = res.getString(i+1);
                }
                lista.add(rows);
            }
        } 
        catch (SQLException ex) {
            System.err.println("Error no se pudo ejecutar la consulta SELECT (with headers):\n"+query);
            System.err.println(ex.getMessage());
        }
        return lista;
    }
    
    public String [] getHeader(String query){
        String [] head = null;
        ResultSet rset;
        try (Statement cmd = cnx.createStatement()) {
            rset = cmd.executeQuery(query);
            ResultSetMetaData rmeta = rset.getMetaData();
            int columns = rmeta.getColumnCount();
            head = new String[columns];
            for(int i=1; i<=columns; ++i)
                head[i-1]=rmeta.getColumnName(i);
        }
        catch(SQLException ex){
            System.err.println("Error no se pudo ejecutar la consulta SELECT (header): "+query);
            System.err.println(ex.getMessage());
        }
        return head;
    }

    List<String>[] selectColumns(String query) {
        List<String>[] lista = null;
        String [] rows;
        ResultSetMetaData rsmd;
        ResultSet res;
        try(Statement cmd = cnx.createStatement()){
            res = cmd.executeQuery(query);
            rsmd = res.getMetaData();
            int cols = rsmd.getColumnCount();
            lista = new List[cols+1];
            for (int i=0; i<lista.length; i++) {
                lista[i] = new ArrayList();
            }
            for(int i=1; i<=cols; ++i)
                lista[0].add(rsmd.getColumnName(i));
            while(res.next()){
                for (int i = 1; i <= cols; i++) {
                    lista[i].add(res.getString(i));
                }
            }
        } 
        catch (SQLException ex) {
            System.err.println("Error no se pudo ejecutar la consulta SELECT (with headers):\n"+query);
            System.err.println(ex.getMessage());
        }
        return lista;
    }
    
}
