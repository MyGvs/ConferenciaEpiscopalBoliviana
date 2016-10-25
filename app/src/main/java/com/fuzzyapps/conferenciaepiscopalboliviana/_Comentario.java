package com.fuzzyapps.conferenciaepiscopalboliviana;

/**
 * Created by Geovani on 06/10/2016.
 */

public class _Comentario {
    final String id;
    final String fecha;
    final String mensaje;
    final String nombre;
    final String img_url;

    public _Comentario(String id, String fecha, String mensaje, String nombre, String img_url) {
        this.id = id;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.img_url = img_url;
    }
}
