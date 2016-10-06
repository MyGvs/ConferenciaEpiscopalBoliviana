package com.fuzzyapps.conferenciaepiscopalboliviana;

/**
 * Created by Geovani on 05/10/2016.
 */

public class clase_Evento {
    final Double latitud;
    final Double longtud;
    final String nombre;
    final String fecha;
    final String lugar;
    final String hora_inicio;
    final String hora_fin;
    public clase_Evento(Double latitud, Double longtud, String nombre, String fecha, String lugar, String hora_inicio, String hora_fin){

        this.latitud = latitud;
        this.longtud = longtud;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
    }
}
