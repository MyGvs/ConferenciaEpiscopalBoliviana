package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.os.AsyncTask;

/**
 * Created by Geovani on 21/09/2016.
 */

public class Obra {
    public int id;
    public String nombre;
    public String horario_inicio;
    public String horario_fin;
    public String poblacion_neta;
    public String longitud;
    public String latitud;
    public String fecha_entrega;
    public String obras_tipo_obra_id;
    public String obras_jurisdiccion_id;
    public String obras_usuarios_id;
    public String obras_departamento_id;
    public String telefono1;
    public String telefono2;
    public String fax;
    public String municipio;
    public String comunidad_zona_urb;
    public String pagina_web;
    public String provincia;
    public String casilla;
    public String direccion;
    public int atencion_lunes;
    public int atencion_martes;
    public int atencion_miercoles;
    public int atencion_jueves;
    public int atencion_viernes;
    public int atencion_sabado;
    public int atencion_domingo;
    public String fecha_registro;

    public Obra(int id, String nombre, String horario_inicio, String horario_fin, String poblacion_neta, String longitud, String latitud, String fecha_entrega, String obras_tipo_obra_id, String obras_jurisdiccion_id, String obras_usuarios_id, String obras_departamento_id, String telefono1, String telefono2, String fax, String municipio, String comunidad_zona_urb, String pagina_web, String provincia, String casilla, String direccion, int atencion_lunes, int atencion_martes, int atencion_miercoles, int atencion_jueves, int atencion_viernes, int atencion_sabado, int atencion_domingo) {
        this.id = id;
        this.nombre = nombre;
        this.horario_inicio = horario_inicio;
        this.horario_fin = horario_fin;
        this.poblacion_neta = poblacion_neta;
        this.longitud = longitud;
        this.latitud = latitud;
        this.fecha_entrega = fecha_entrega;
        this.obras_tipo_obra_id = obras_tipo_obra_id;
        this.obras_jurisdiccion_id = obras_jurisdiccion_id;
        this.obras_usuarios_id = obras_usuarios_id;
        this.obras_departamento_id = obras_departamento_id;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fax = fax;
        this.municipio = municipio;
        this.comunidad_zona_urb = comunidad_zona_urb;
        this.pagina_web = pagina_web;
        this.provincia = provincia;
        this.casilla = casilla;
        this.direccion = direccion;
        this.atencion_lunes = atencion_lunes;
        this.atencion_martes = atencion_martes;
        this.atencion_miercoles = atencion_miercoles;
        this.atencion_jueves = atencion_jueves;
        this.atencion_viernes = atencion_viernes;
        this.atencion_sabado = atencion_sabado;
        this.atencion_domingo = atencion_domingo;
    }

    public Obra(String nombre, String horario_inicio, String horario_fin, String poblacion_neta, String longitud, String latitud, String fecha_entrega, String obras_tipo_obra_id, String obras_jurisdiccion_id, String obras_usuarios_id, String obras_departamento_id, String telefono1, String telefono2, String fax, String municipio, String comunidad_zona_urb, String pagina_web, String provincia, String casilla, String direccion, int atencion_lunes, int atencion_martes, int atencion_miercoles, int atencion_jueves, int atencion_viernes, int atencion_sabado, int atencion_domingo, String fecha_registro) {
        this.nombre = nombre;
        this.horario_inicio = horario_inicio;
        this.horario_fin = horario_fin;
        this.poblacion_neta = poblacion_neta;
        this.longitud = longitud;
        this.latitud = latitud;
        this.fecha_entrega = fecha_entrega;
        this.obras_tipo_obra_id = obras_tipo_obra_id;
        this.obras_jurisdiccion_id = obras_jurisdiccion_id;
        this.obras_usuarios_id = obras_usuarios_id;
        this.obras_departamento_id = obras_departamento_id;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fax = fax;
        this.municipio = municipio;
        this.comunidad_zona_urb = comunidad_zona_urb;
        this.pagina_web = pagina_web;
        this.provincia = provincia;
        this.casilla = casilla;
        this.direccion = direccion;
        this.atencion_lunes = atencion_lunes;
        this.atencion_martes = atencion_martes;
        this.atencion_miercoles = atencion_miercoles;
        this.atencion_jueves = atencion_jueves;
        this.atencion_viernes = atencion_viernes;
        this.atencion_sabado = atencion_sabado;
        this.atencion_domingo = atencion_domingo;
        this.fecha_registro = fecha_registro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(String horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public String getHorario_fin() {
        return horario_fin;
    }

    public void setHorario_fin(String horario_fin) {
        this.horario_fin = horario_fin;
    }

    public String getPoblacion_neta() {
        return poblacion_neta;
    }

    public void setPoblacion_neta(String poblacion_neta) {
        this.poblacion_neta = poblacion_neta;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getObras_tipo_obra_id() {
        return obras_tipo_obra_id;
    }

    public void setObras_tipo_obra_id(String obras_tipo_obra_id) {
        this.obras_tipo_obra_id = obras_tipo_obra_id;
    }

    public String getObras_jurisdiccion_id() {
        return obras_jurisdiccion_id;
    }

    public void setObras_jurisdiccion_id(String obras_jurisdiccion_id) {
        this.obras_jurisdiccion_id = obras_jurisdiccion_id;
    }

    public String getObras_usuarios_id() {
        return obras_usuarios_id;
    }

    public void setObras_usuarios_id(String obras_usuarios_id) {
        this.obras_usuarios_id = obras_usuarios_id;
    }

    public String getObras_departamento_id() {
        return obras_departamento_id;
    }

    public void setObras_departamento_id(String obras_departamento_id) {
        this.obras_departamento_id = obras_departamento_id;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getComunidad_zona_urb() {
        return comunidad_zona_urb;
    }

    public void setComunidad_zona_urb(String comunidad_zona_urb) {
        this.comunidad_zona_urb = comunidad_zona_urb;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCasilla() {
        return casilla;
    }

    public void setCasilla(String casilla) {
        this.casilla = casilla;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getAtencion_lunes() {
        return atencion_lunes;
    }

    public void setAtencion_lunes(int atencion_lunes) {
        this.atencion_lunes = atencion_lunes;
    }

    public int getAtencion_martes() {
        return atencion_martes;
    }

    public void setAtencion_martes(int atencion_martes) {
        this.atencion_martes = atencion_martes;
    }

    public int getAtencion_miercoles() {
        return atencion_miercoles;
    }

    public void setAtencion_miercoles(int atencion_miercoles) {
        this.atencion_miercoles = atencion_miercoles;
    }

    public int getAtencion_jueves() {
        return atencion_jueves;
    }

    public void setAtencion_jueves(int atencion_jueves) {
        this.atencion_jueves = atencion_jueves;
    }

    public int getAtencion_viernes() {
        return atencion_viernes;
    }

    public void setAtencion_viernes(int atencion_viernes) {
        this.atencion_viernes = atencion_viernes;
    }

    public int getAtencion_sabado() {
        return atencion_sabado;
    }

    public void setAtencion_sabado(int atencion_sabado) {
        this.atencion_sabado = atencion_sabado;
    }

    public int getAtencion_domingo() {
        return atencion_domingo;
    }

    public void setAtencion_domingo(int atencion_domingo) {
        this.atencion_domingo = atencion_domingo;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
