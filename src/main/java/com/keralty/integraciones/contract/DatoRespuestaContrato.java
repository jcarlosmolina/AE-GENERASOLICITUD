package com.keralty.integraciones.contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de información para capturar la información de un contrato recuperado del CORE y utilizarla 
 *  como convenga
 */
public class DatoRespuestaContrato {

	/** Respuesta del CORE */
	private String errorCode;
//	/** Cierre de la solicitud */
//	private String confirmacionCierreSolicitud;
//	/** Nº de contrato */
//	private String numContrato;
//	/** Nº de Familia */
//	private String numFamilia;
//	/** Estado del contrato */
//	private String estadoContrato;
	/** Mensajes de error del CORE */
	private List<String> mensajesDeError = new ArrayList<String>();
//	/** Mensaje de confirmación  */
//	private String mensajeConfirmacion;
//	/** Datos de beneficarios */
//	private List<DatoBeneficiario> beneficiarios = new ArrayList<DatoBeneficiario>();

    private List<DatoLogLlamadaBH> datosLlamadasBH = new ArrayList<DatoLogLlamadaBH>();

	/**
	 * Constructor
	 */
	public DatoRespuestaContrato(){
	}

//	public String getNumContrato() {
//		return numContrato;
//	}
//
//	public void setNumContrato(String numContrato) {
//		this.numContrato = numContrato;
//	}
//
//	public String getEstadoContrato() {
//		return estadoContrato;
//	}
//
//	public void setEstadoContrato(String estadoContrato) {
//		this.estadoContrato = estadoContrato;
//	}
//
//	public String getNumFamilia() {
//		return numFamilia;
//	}
//
//	public void setNumFamilia(String numFamilia) {
//		this.numFamilia = numFamilia;
//	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

//	public String getConfirmacionCierreSolicitud() {
//		return confirmacionCierreSolicitud;
//	}
//
//	public void setConfirmacionCierreSolicitud(String confirmacionCierreSolicitud) {
//		this.confirmacionCierreSolicitud = confirmacionCierreSolicitud;
//	}

	public List<String> getMensajesDeError() {
		return mensajesDeError;
	}

	public void setMensajesDeError(List<String> mensajesDeError) {
		this.mensajesDeError = mensajesDeError;
	}

	public List<DatoLogLlamadaBH> getDatosLlamadasBH() {
		return datosLlamadasBH;
	}

	public void setDatosLlamadasBH(List<DatoLogLlamadaBH> datosLlamadasBH) {
		this.datosLlamadasBH = datosLlamadasBH;
	}

//	public String getMensajeConfirmacion() {
//		return mensajeConfirmacion;
//	}
//
//	public void setMensajeConfirmacion(String mensajeConfirmacion) {
//		this.mensajeConfirmacion = mensajeConfirmacion;
//	}
//
//	public List<DatoBeneficiario> getBeneficiarios() {
//		return beneficiarios;
//	}
//
//	public void setBeneficiarios(List<DatoBeneficiario> beneficiarios) {
//		this.beneficiarios = beneficiarios;
//	}

//	/**
//	 * Devuelve el estado en el que debe quedar la solicitud tras la respuesta del CORE
//	 * @param ignorarPreexistencias 
//	 * @param estadoSolicitud 
//	 * @return
//	 */
//	public Long calcularEstadoSolicitud(String textoErrorPendMedico, boolean ignorarPreexistencias, Long estadoSolicitud){
//
//		// El dato errorCode: OK-ERROR indica que no persistio la información en el core por lo tanto  se puede volver a invocar esta capacidad para la misma solicitud. En este caso la solicitud no debe cambiar su estado, quedando en radicado y se debe almacenar las incidencias que el core devuelva en la lista descripcionError. 
//		//
//		// El dato errorCode: OK indica que persistio la información en el core por lo tanto no se puede volver a invocar esta capacidad para la misma solicitud ya que devolvería la incidencia que ya existe la solicitud en el core.  En este caso para saber el estado de la solicitud no se debe evaluar el dato que devuelve esta capacidad como estado del contrato. 
//		//
//		//	- Si el dato ConfirmacionCierreSolicitud viene en S, en Afiliación electrónica la solicitud debe quedar en estado
//		//		Tramitada y se debe almacenar las incidencias que el core devuelva en la lista descripcionError.
//		//  - Si el dato ConfirmacionCierreSolicitud viene en N, en Afiliación electrónica la solicitud debe quedar en estado
//		//		Error y se debe almacenar las incidencias que el core devuelva en la lista descripcionError.
//		
//		//		0	ERROR
//		//		1	RADICADO
//		//		2	TRAMITADO
//		//		3	TRAMITADO CON PREEXISTENCIAS
//		//		4 	ANULADO
//		// 		5 	PENDIENTE MEDICO
//		
//// 		GdC4-8: Si el Plan o el Dominio indican que se deben ignorar las preexistencias (argumento de la función), 
////		nunca se devuelve el estado TRAMITADO CON PREEXISTENCIAS, siempre TRAMITADO  
//
//		Long estado = 1L;
//
//		if (textoErrorPendMedico != null && !textoErrorPendMedico.isEmpty()){
//			// Comprobamos si existe el mensaje entre los errores recibidos
//			for(String msgError : getMensajesDeError()){
//				
//				if (msgError.toUpperCase().contains(textoErrorPendMedico.toUpperCase())){
//					estado = 5L;
//					break;
//				}
//			}
//		}
//		
//		
//		// Unicamente si este flag toma valor OK ...
//		if (estado == 1 && getErrorCode() != null && (getErrorCode().equalsIgnoreCase("OK") || getErrorCode().isEmpty())){
//			if (getConfirmacionCierreSolicitud() != null){
//				if ( getConfirmacionCierreSolicitud().equalsIgnoreCase("S")){
//					// TRAMITADO
//					estado = 2L;
//					// Comprobamos si algún beneficiario tiene preexistencias
//					if (!ignorarPreexistencias){
//						for (DatoBeneficiario benef : getBeneficiarios()){
//							if (benef.isTienePreexistencias()){
//								// TRAMITADO CON PREEXISTENCIAS
//								estado = 3L;
//								break;
//							}
//						}
//					}					
//				} else if(getConfirmacionCierreSolicitud().equalsIgnoreCase("N")){
//					// GdC10-10 Si no se indica S en el tag ConfirmacionCierreSolicitud, no debe cambiar el estado de la solicitud
//					estado = estadoSolicitud;
//				} else{
//					// ERROR
//					estado = 0L;
//				}
//			}
//		}
//		return estado;
//	}
//	
//	/**
//	 * Devuelve el estado en el que debe quedar la solicitud tras la respuesta del CORE que ha sido erronea. El estado será ERROR o PENDIENTE MEDICO
//	 * @param textoErrorPendMedico Texto de error Pendiente médico
//	 * @return
//	 */
//	public Long calcularEstadoSolicitud(String textoErrorPendMedico){
//		
//		//		0	ERROR
//		//		1	RADICADO
//		//		2	TRAMITADO
//		//		3	TRAMITADO CON PREEXISTENCIAS
//		//		4 	ANULADO
//		// 		5 	PENDIENTE MEDICO
//
//		
//		Long estado = 1L;
//
//		// GdC-26 Req 17:
//		// Se debe modificar el tratamiento de la respuesta de BH para los casos de inclusiones. En el sentido de que, 
//		//  si el servicio de BH devuelve el texto configurado para el parámetro de configuración “TEXTOERRORPENDMEDICO”,
//		//  la solicitud quede en estado PENDIENTE MEDICO.
//		// Este nuevo comportamiento no deberá tener en cuenta la marca de confirmación de cierre de solicitud,
//		//  no se tendrá en cuenta el valor de dicha marca para esta nueva funcionalidad.
//
//		if (textoErrorPendMedico != null && !textoErrorPendMedico.isEmpty()){
//			// Comprobamos si existe el mensaje entre los errores recibidos
//			for(String msgError : getMensajesDeError()){
//				
//				if (msgError.toUpperCase().contains(textoErrorPendMedico.toUpperCase())){
//					estado = 5L;
//					break;
//				}
//			}
//		}
//		
//		if (!estado.equals(5L) && !"S".equalsIgnoreCase(getConfirmacionCierreSolicitud())){
//			estado = 0L;
//		}
//		
//		return estado;
//	}
//	
	
	
}
