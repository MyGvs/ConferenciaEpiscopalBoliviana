package com.fuzzyapps.conferenciaepiscopalboliviana;

/**
 * Created by Geovani on 06/10/2016.
 */

public class _Evento {
    final String id;
    final String nombre;
    final String descripcion;
    final String fecha_evento;
    final String hora_inicio;
    final String hora_fin;
    final String lugar;
    final String imagen_url;
    final int likes;
    final int comments;
    final String miLike;
    final String miLikeId;


    public _Evento(String id, String nombre, String descripcion, String fecha_evento, String hora_inicio, String hora_fin, String lugar, String imagen_url, int likes, int comments, String miLike, String miLikeId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_evento = fecha_evento;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.lugar = lugar;
        this.imagen_url = imagen_url;
        this.likes = likes;
        this.comments = comments;
        this.miLike = miLike;
        this.miLikeId = miLikeId;
    }

}
