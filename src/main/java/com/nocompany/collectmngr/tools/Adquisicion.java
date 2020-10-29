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
public class Adquisicion {
    private final double precio;
    private final String fecha;
    private final String lugar;
    private final String estado;
    private final int calif;
    private int id_fig;

    public Adquisicion(double precio, String fecha, String lugar, String estado, int calif, int id_fig) {
        this.precio = precio;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = estado;
        this.calif = calif;
        this.id_fig = id_fig;
    }
    
    public Adquisicion(double precio, String fecha, String lugar, String estado, int id_fig) {
        this.precio = precio;
        this.fecha = fecha;
        this.lugar = lugar;
        this.estado = estado;
        this.calif = 3;
        this.id_fig = id_fig;
    }

    public double getPrecio() {
        return precio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public String getEstado() {
        return estado;
    }

    public int getCalif() {
        return calif;
    }

    public int getId_fig() {
        return id_fig;
    }

    public void setId_fig(int id_fig) {
        this.id_fig = id_fig;
    }

    @Override
    public String toString() {
        return "{\"precio\":" + precio + ", \"fecha\":" + fecha + ", \"lugar\"" + lugar + ", \"estado\":" + estado + ", \"calif\":" + calif + ", \"id_fig\":" + id_fig + '}';
    }
    
    
}
