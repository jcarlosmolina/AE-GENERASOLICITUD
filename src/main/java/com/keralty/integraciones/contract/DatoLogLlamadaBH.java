package com.keralty.integraciones.contract;

import java.sql.Timestamp;

/**
 * Mantiene la información de una llamada a un WS de BH
 */
public class DatoLogLlamadaBH {

	
	private Timestamp fechaHora;
	private String usuario;
	private String capacidad;
	private String servicio;
	private String solicitud;
	private String peticion;
	private String respuesta;
	private String tipoServicio;
	private Double tiempoEjecucion;
	private String textoError; 
	public Timestamp getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	public String getPeticion() {
		return peticion;
	}
	public void setPeticion(String peticion) {
		this.peticion = peticion;
	}
	public String getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	public Double getTiempoEjecucion() {
		return tiempoEjecucion;
	}
	public void setTiempoEjecucion(Double tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
	public String getTextoError() {
		return textoError;
	}
	public void setTextoError(String textoError) {
		this.textoError = textoError;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
