/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nocompany.collectmngr.tools;

/**
 *
 * @author MoisésUlises
 */
public class Figura {
    private int id;
    private final String personaje;
    private final String clase;
    private final String subNombre;
    private final String serial;
    private final String faccion;
    private int det;
    
    
    public Figura(String personaje, String clase, String faccion, String serial, int details){
        this.id = 0;
        String[] name = this.divideName(personaje);
        this.personaje = name[0];
        this.subNombre = name[1];
        this.clase = clase;
        this.det = details;
        this.serial = serial;
        this.faccion = faccion;
    }

    public Figura(int id, String personaje, String faccion, String clase, String subNombre, String serial) {
        this.id = id;
        this.personaje = personaje;
        this.clase = clase;
        this.subNombre = subNombre;
        this.serial = serial;
        this.faccion = faccion;
    }

    public int getDet() {
        return det;
    }

    public void setDet(int det) {
        this.det = det;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getPersonaje() {
        return personaje;
    }

    public String getClase() {
        return clase;
    }

    public String getSubNombre() {
        return subNombre;
    }

    public String getSerial() {
        return serial;
    }
    
    public String getFaccion() {
        return faccion;
    }
    
    public String[] divideName(String s){
        String[] res = {s.trim(), ""};
        if(s.contains("|")){
            res = s.split("|");
            res[0] = res[0].trim();
            res[1] = res[1].trim();
        }
        return res;
    }
    
    

    @Override
    public String toString() {
        return "Figura{" + "personaje=" + personaje + ", clase=" + clase + ", subNombre=" + subNombre + ", serial=" + serial + '}';
    }

    
    
    
}
