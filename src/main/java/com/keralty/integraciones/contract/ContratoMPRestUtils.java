package com.keralty.integraciones.contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.keralty.aegenerasolicitud.exception.ModelException;
import com.keralty.aegenerasolicitud.exception.SystemException;
import com.keralty.aegenerasolicitud.global.STDFunctions;
import com.keralty.aegenerasolicitud.global.UserFunctions;
import com.keralty.aegenerasolicitud.persistence.Persona;
import com.keralty.aegenerasolicitud.persistence.Solicitud;
import com.keralty.aegenerasolicitud.persistence.Usuario;

import jakarta.enterprise.context.ApplicationScoped;


/**
 * Clase de utilidad para las llamadas a los servicios REST de Crear contrato e inclusión beneficiario de Contract
 */
@ApplicationScoped
public class ContratoMPRestUtils {

	@ConfigProperty(name="quarkus.rest-client.integraciones_token.url", defaultValue = "" )
    private String urlToken;

	@ConfigProperty(name="com.keralty.integraciones.token.hash", defaultValue = "" )
    private String hashToken;

	@ConfigProperty(name="com.keralty.integraciones.token.usr", defaultValue = "" )
    private String usrToken;

	@ConfigProperty(name="com.keralty.integraciones.token.pwd", defaultValue = "" )
    private String pwdToken;

	@ConfigProperty(name="com.keralty.integraciones.maxintentosllamada", defaultValue = "1" )
    private int maxIntentosLlamada;

    private int maxReintentos401 = 2;

	@ConfigProperty(name="quarkus.rest-client.integraciones_contract.url", defaultValue = "" )
    private String urlContrato;

	@ConfigProperty(name="com.keralty.integraciones.codaplicacion", defaultValue = "" )
    private String codAplicacion;

	@ConfigProperty(name="com.keralty.integraciones.funcionnegocio", defaultValue = "" )
    private String funcionNegocio;

	private UserFunctions userFunctionsSrv;
	
	public DatoRespuestaContrato inclusionBeneficiario(Solicitud solicitud, String usuario, UserFunctions userFunctionsSrv) throws ModelException, SystemException {
		
		this.userFunctionsSrv = userFunctionsSrv;
		
		DatoRespuestaContrato result = new DatoRespuestaContrato();
		int numReintentos = 0;



		String authorization = "";
		Timestamp fechaHora = STDFunctions.systemDateTimeFun();
		String fechaHoraPeticion = formatearFechaHoraREST(STDFunctions.systemDateTimeFun());
		String numSolicitud = solicitud.getSolicitudNumRadicado().toString(); 

		String peticion = "";
		String agenteLlamada = usuario; 

		try {
			authorization = generarAuthorization();
		} catch(SystemException e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			result.setErrorCode("AUTH");
			result.addMensajeDeError("Estamos presentando fallas técnicas, contacte con el administrador");
			return result;
		}


		// Formamos la peticion. 
		// Segun firma definida en archivo "InOut_CrearContratoIncluirAfiliado_16_nov_2021.xlsx"
		JsonObject input = crearPeticionInclusionBeneficiario(solicitud); 

		String urlCompleta = urlContrato + "/includeBeneficiary";
		String respuesta = "";
		Double tiempoEjecucion = 0.0;
		long endTime;
		int numReintentos401 = 0;
		while (numReintentos <= maxIntentosLlamada && numReintentos401 <= maxReintentos401) {
			long startTime = System.currentTimeMillis();
			try {
				URL url = URI.create(urlCompleta).toURL();
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", authorization);

				// Datos de cabecera
				//			IdPeticion
				conn.setRequestProperty("IdPeticion", "AE" + numSolicitud);
				//			CodAplicacion
				conn.setRequestProperty("CodAplicacion", codAplicacion);
				//			Usuario
				conn.setRequestProperty("Usuario", agenteLlamada);
				//			FechaPeticion
				conn.setRequestProperty("FechaPeticion", fechaHoraPeticion);
				//			funcionNegocio
				conn.setRequestProperty("funcionNegocio", "Afiliaciones");

				// Asignamos el input

				peticion = input.toString();

				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				osw.write(input.toString());
				osw.flush();
				osw.close();    		

				StringBuilder response = new StringBuilder();
				String output;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try {
					InputStream is = conn.getInputStream();
					isr = new InputStreamReader(is);
					br = new BufferedReader(isr);
					while ((output = br.readLine()) != null) {
						response.append(output);
					}
				} finally {
					if (br != null) {
						br.close();
					}
					if (isr != null) {
						isr.close();
					}
				}

				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
				respuesta = response.toString();

				// Analizamos la respuesta obtenida
				String errorMsg = "";
				JsonObject responseJson = new Gson().fromJson(response.toString(), JsonObject.class);
				if (responseJson != null) {
					if (responseJson.get("status") != null && !responseJson.get("status").getAsString().equalsIgnoreCase("RECIBIDO")) {
						result.setErrorCode("ERR");
						JsonArray msgError = responseJson.getAsJsonArray("error");
						if (msgError != null) {
							for(JsonElement err : msgError){
								JsonObject errJson = err.getAsJsonObject();
								if (errJson != null && errJson.get("value") != null && errJson.get("value").getAsString() != null && !errJson.get("value").getAsString().isEmpty()) {
									result.addMensajeDeError(errJson.get("value").getAsString());
									errorMsg += errJson.get("value").getAsString() + "\n";
								}
							}
						}
						if (result.getMensajesDeError().isEmpty()) {
							result.addMensajeDeError("E000: Error no definido");
						}
					}
				}


				// Guardamos la llamada al WS
				guardarLlamadaBH(result, fechaHora, agenteLlamada, 
						"inclusionBeneficiario",
						"ContratoMP", 
						numSolicitud, 
						peticion, respuesta,
						"REST Service", tiempoEjecucion, errorMsg);

				break;

			} catch (MalformedURLException e) {
				if (numReintentos == maxIntentosLlamada) {
					endTime = System.currentTimeMillis();
					tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
					// Guardamos la llamada al WS
					guardarLlamadaBH(result, fechaHora, agenteLlamada, 
							"inclusionBeneficiario",
							"ContratoMP", 
							numSolicitud, 
							peticion, respuesta,
							"REST Service", tiempoEjecucion, e.getMessage());
					throw new SystemException(e);
				}
				numReintentos++;
			} catch (IOException e) {
				String msgError = e.getMessage();
				// 401 - GdC-53
				if (msgError.indexOf("response code: 401") > 0) {
					endTime = System.currentTimeMillis();
					tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
					// Guardamos la llamada al WS
					guardarLlamadaBH(result, fechaHora, agenteLlamada, 
							"inclusionBeneficiario",
							"ContratoMP", 
							numSolicitud, 
							peticion, respuesta,
							"REST Service", tiempoEjecucion, msgError);
					if (numReintentos401 == maxReintentos401) {
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("Reintentos por 401 alcanzado");
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
						throw new SystemException("Estamos presentando fallas tecnicas. Pruebe de nuevo pasados unos minutos. CTR-110");
					}
					// Esperamos 2 segundos
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e1.getMessage());
						Thread.currentThread().interrupt();
					}
					//authorizationValorPrevio = authorization;
					fechaHora = STDFunctions.systemDateTimeFun();
					authorization = generarAuthorization();					
					numReintentos401++;
				} else {
					if (numReintentos == maxIntentosLlamada) {

						endTime = System.currentTimeMillis();
						tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
						// Guardamos la llamada al WS
						guardarLlamadaBH(result, fechaHora, agenteLlamada, 
								"inclusionBeneficiario",
								"ContratoMP", 
								numSolicitud, 
								peticion, respuesta,
								"REST Service", tiempoEjecucion, msgError);
						throw new SystemException(e);
					}
					numReintentos++;
				}
			} catch (Exception e) {
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
				// Guardamos la llamada al WS
				guardarLlamadaBH(result, fechaHora, agenteLlamada, 
						"inclusionBeneficiario",
						"ContratoMP", 
						numSolicitud, 
						peticion, respuesta,
						"REST Service", tiempoEjecucion, e.getMessage());
				throw new SystemException(e);
			}
		}
		return result;
	}

	/**
	 * Devuelve el Json correspondiente a la peticiï¿½n de inclusion Beneficiario
	 * @param solicitud Solicitud
	 * @return El Json
	 * @throws SystemException
	 */
	private JsonObject crearPeticionInclusionBeneficiario(Solicitud solicitud) throws SystemException {
		JsonObject peticion = new JsonObject();
		// insurancePlan
		peticion.add("insurancePlan", crearInsurancePlan(solicitud));
		peticion.add("contract", crearContract(solicitud));
		peticion.add("coverage", crearCoverage(solicitud));
		return peticion;
	}

	/**
	 * Llamada a crear contrato de ContratoMP
	 * @param solicitud Solicitud
	 * @param usuario Usuario
	 * @param userFunctionsSrv UserFunctions
	 * @return La respuesta del contrato. Un valor en ErrorCode diferente de Null o cadena vacia indica error que aparecerï¿½n en MensajesDeError
	 * @throws ModelException 
	 * @throws SystemException
	 */
	public DatoRespuestaContrato crearContratoMP(Solicitud solicitud, String usuario, UserFunctions userFunctionsSrv) throws ModelException, SystemException {
		this.userFunctionsSrv = userFunctionsSrv;

		DatoRespuestaContrato result = new DatoRespuestaContrato();

		int numReintentos = 0;

		String authorization = "";
		Timestamp fechaHora = STDFunctions.systemDateTimeFun();
		String fechaHoraPeticion = formatearFechaHoraREST(STDFunctions.systemDateTimeFun());
		String numSolicitud = solicitud.getSolicitudNumRadicado().toString();

		String peticion = "";
		String agenteLlamada = usuario; 

		try {
			authorization = generarAuthorization();
		} catch(SystemException e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
			result.setErrorCode("AUTH");
			result.addMensajeDeError("Estamos presentando fallas técnicas, contacte con el administrador");
			return result;
		}


		// Formamos la peticiï¿½n. 
		// Segun firma definida en archivo "InOut_CrearContratoIncluirAfiliado_16_nov_2021.xlsx"
		JsonObject input = crearPeticionCrearContrato(solicitud); 


		String urlCompleta = urlContrato + "/registerContract";
		int timeout = 180 * 1000;
		String respuesta = "";
		Double tiempoEjecucion = 0.0;
		long endTime;

		int numReintentos401 = 0;

		while (numReintentos <= maxIntentosLlamada && numReintentos401 <= maxReintentos401) {
			long startTime = System.currentTimeMillis();
			try {
				URL url = URI.create(urlCompleta).toURL();
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(timeout);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Authorization", authorization);

				// Datos de cabecera
				//			IdPeticion
				conn.setRequestProperty("IdPeticion", "AE" + numSolicitud);
				//			CodAplicacion
				conn.setRequestProperty("CodAplicacion", codAplicacion);
				//			Usuario
				conn.setRequestProperty("Usuario", agenteLlamada);
				//			FechaPeticion
				conn.setRequestProperty("FechaPeticion", fechaHoraPeticion);
				//			funcionNegocio
				conn.setRequestProperty("funcionNegocio", "Afiliaciones");

				// Asignamos el input

				peticion = input.toString();

				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				osw.write(input.toString());
				osw.flush();
				osw.close();    		

				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				StringBuilder response = new StringBuilder();
				String output;
				while ((output = br.readLine()) != null) {
					response.append(output);
				}
				br.close();
				isr.close();

				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
				respuesta = response.toString();

				// Analizamos la respuesta obtenida
				String errorMsg = "";
				JsonObject responseJson = new Gson().fromJson(response.toString(), JsonObject.class);
				if (responseJson != null) {
					if (responseJson.get("status") != null && !responseJson.get("status").getAsString().equalsIgnoreCase("RECIBIDO")) {
						result.setErrorCode("ERR");
						JsonArray msgError = responseJson.getAsJsonArray("error");
						if (msgError != null) {
							for(JsonElement err : msgError){
								JsonObject errJson = err.getAsJsonObject();
								if (errJson != null && errJson.get("value") != null && errJson.get("value").getAsString() != null && !errJson.get("value").getAsString().isEmpty()) {
									result.addMensajeDeError(errJson.get("value").getAsString());
									errorMsg += errJson.get("value").getAsString() + "\n";

								}
							}
						}
						if (result.getMensajesDeError().isEmpty()) {
							result.addMensajeDeError("E000: Error no definido");
						}
					}
				}


				// Guardamos la llamada al WS
				guardarLlamadaBH(result, fechaHora, agenteLlamada, 
						"crear",
						"ContratoMP", 
						numSolicitud, 
						peticion, respuesta,
						"REST Service", tiempoEjecucion, errorMsg);

				break;

			} catch (MalformedURLException e) {
				if (numReintentos == maxIntentosLlamada) {
					endTime = System.currentTimeMillis();
					tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
					// Guardamos la llamada al WS
					guardarLlamadaBH(result, fechaHora, agenteLlamada, 
							"crear",
							"ContratoMP", 
							numSolicitud, 
							peticion, respuesta,
							"REST Service", tiempoEjecucion, e.getMessage());
					throw new SystemException(e);
				}
				numReintentos++;
			} catch (IOException e) {
				String msgError = e.getMessage();
				// 401 - GdC-53
				if (msgError.indexOf("response code: 401") > 0) {
					endTime = System.currentTimeMillis();
					tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
					// Guardamos la llamada al WS
					guardarLlamadaBH(result, fechaHora, agenteLlamada, 
							"crear",
							"ContratoMP", 
							numSolicitud, 
							peticion, respuesta,
							"REST Service", tiempoEjecucion, msgError);
					if (numReintentos401 == maxReintentos401) {
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error("Reintentos por 401 alcanzado");
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e.getMessage());
						throw new SystemException("Estamos presentando fallas tecnicas. Pruebe de nuevo pasados unos minutos. CTR-100");
					}
					// Esperamos 2 segundos
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						org.slf4j.LoggerFactory.getLogger(this.getClass().getName()).error(e1.getMessage());
						Thread.currentThread().interrupt();
					}
					//authorizationValorPrevio = authorization;
					fechaHora = STDFunctions.systemDateTimeFun();
					authorization = generarAuthorization();					
					numReintentos401++;
				} else {
					if (numReintentos == maxIntentosLlamada) {
						endTime = System.currentTimeMillis();
						tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
						// Guardamos la llamada al WS
						guardarLlamadaBH(result, fechaHora, agenteLlamada, 
								"crear",
								"ContratoMP", 
								numSolicitud, 
								peticion, respuesta,
								"REST Service", tiempoEjecucion, msgError);
						throw new SystemException(e);
					}
					numReintentos++;
				}
			} catch (Exception e) {
				endTime = System.currentTimeMillis();
				tiempoEjecucion = (double) ((endTime - startTime)/1000.0f);
				// Guardamos la llamada al WS
				guardarLlamadaBH(result, fechaHora, agenteLlamada, 
						"crear",
						"ContratoMP", 
						numSolicitud, 
						peticion, respuesta,
						"REST Service", tiempoEjecucion, e.getMessage());
				throw new SystemException(e);
			}
		}
		return result;
	}



	/**
	 * Devuelve el Json correspondiente a la peticiï¿½n de crear contrato
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonObject crearPeticionCrearContrato(Solicitud solicitud) throws SystemException {
		JsonObject peticion = new JsonObject();
		// insurancePlan
		peticion.add("insurancePlan", crearInsurancePlan(solicitud));
		peticion.add("contract", crearContract(solicitud));
		peticion.add("coverage", crearCoverage(solicitud));
		return peticion;
	}

	/**
	 * Devuelve el Json correspondiente a InsurancePlan
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonObject crearInsurancePlan(Solicitud solicitud) throws SystemException {
		//	  "insurancePlan": {
		//	    "identifier": [
		//	      {
		//	        "type": "CODIGO_PRODUCTO",
		//	        "value": "30"
		//	      }
		//	    ]
		//	  }	
		JsonObject insurancePlan = new JsonObject();

		JsonObject codigo_producto = new JsonObject();
		codigo_producto.addProperty("type", "CODIGO_PRODUCTO");
		codigo_producto.addProperty("value", solicitud.getAesolicitudes().getProductoT().getProductosCodigoLegal());
		// identifier
		JsonArray identifier = new JsonArray();
		identifier.add(codigo_producto);
		insurancePlan.add("identifier", identifier);
		return insurancePlan;
	}

	/**
	 * Devuelve el Json correspondiente a Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonObject crearContract(Solicitud solicitud) throws SystemException {

		//	"contract": {
		//	    "identifier": [
		//	    ],
		//	    "issued": 
		//	    "type": [
		//	    ],
		//	    "authority": [
		//	    ],
		//	    "applies": {
		//	    },
		//	    "term": [
		//	    ],
		//	    "supportingInfo": [
		//	    ],
		//	    "legallyBinding": [
		//	    ],
		//	    "subject": {
		//	    },
		//	    "signer": [
		//	    ]
		//	  }		
		JsonObject contract = new JsonObject();
		// identifier
		contract.add("identifier", crearContractIdentifier( solicitud) );
		// issued
		contract.addProperty("issued", formatearFechaHoraREST(solicitud.getAesolicitudes().getAesolicitudesFechaasignacion()));

		// applyLiquidation 02/09/2024
		if (solicitud.getSolicituddrvTramitarEnLinea()) {
			contract.addProperty("applyLiquidation", Boolean.TRUE);
		} else {
			contract.addProperty("applyLiquidation", Boolean.FALSE);
		}
		
		// type
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null){
			JsonArray type = new JsonArray();
			JsonObject code = new JsonObject();
			code.addProperty("code", solicitud.getSolicitudtipoContrato().toString());
			type.add(code);
			contract.add("type", type);
		}

		// authority
		contract.add("authority", crearContractAuthority( solicitud) );

		// applies
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null){
			JsonObject applies = new JsonObject();
			applies.addProperty("start", formatearFechaHoraREST(solicitud.getSolicitudFInicioContrato()));
			contract.add("applies", applies );
		}		
		// term
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null){
			contract.add("term", crearContractTerm( solicitud) );
		}

		// supportingInfo
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null){
			contract.add("supportingInfo", crearContractSupportingInfo( solicitud) );
		}

		// legallyBinding Anexos al contrato

		// subject
		contract.add("subject", crearContractSubject( solicitud) );

		// signer
		contract.add("signer", crearContractSigner( solicitud) );

		return contract;
	}


	/**
	 * Devuelve el Json correspondiente a Signer de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonArray crearContractSigner(Solicitud solicitud) throws SystemException {
		JsonArray signer = new JsonArray();

		// COD_SUC_RADICADORA
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite())  || solicitud.getSolicitudNumFamilia() == null){
			if (solicitud.getAgente() != null && !solicitud.getAgente().isNull() && solicitud.getAgente().getSucursal() != null && !solicitud.getAgente().getSucursal().isNull() ){
				JsonObject sucursal = new JsonObject();
				sucursal.addProperty("type", "SUCURSAL_RADICADORA");
				JsonObject party = new JsonObject();
				JsonObject organization = new JsonObject ();
				organization.add("identifier", crearIdentifierAsArray("SUCURSAL", solicitud.getAgente().getSucursal().getSucursalCodigo().toString()));
				party.add("organization", organization);
				sucursal.add("party", party);

				signer.add(sucursal);
			}
		}

		// COD_ASESOR
		if (solicitud.getAgente() != null && !solicitud.getAgente().isNull() ){
			JsonObject asesor = new JsonObject();
			asesor.addProperty("type", "ASESOR");
			JsonObject party = new JsonObject();
			JsonObject relatedPerson = new JsonObject ();
			String codAsesor = solicitud.getAgente().getFuncionarioUsuario();
			relatedPerson.add("identifier", crearIdentifierAsArray("CODIGO", codAsesor));
			party.add("relatedPerson", relatedPerson);
			asesor.add("party", party);

			signer.add(asesor);
		}

		// COD_DIRECTOR
		if (solicitud.getSolicitudcodDirector() != null && !solicitud.getSolicitudcodDirector().isEmpty() ){
			JsonObject director = new JsonObject();
			director.addProperty("type", "DIRECTOR");
			JsonObject party = new JsonObject();
			JsonObject relatedPerson = new JsonObject ();
			relatedPerson.add("identifier", crearIdentifierAsArray("CODIGO", solicitud.getSolicitudcodDirector()));
			party.add("relatedPerson", relatedPerson);
			director.add("party", party);

			signer.add(director);
		}
		// COD_CENTRO_COSTO
		// No se enviaba

		return signer;
	}

	/**
	 * Utility para devolver un Json con la estructura de in 'identifier' dentro de un array   
	 * @param valorType Texto a asignar en 'type'
	 * @param valorValue Valor a asignar a 'value'
	 * @return
	 */
	private JsonArray crearIdentifierAsArray(String valorType, String valorValue) {
		JsonArray identifierArray = new JsonArray();
		identifierArray.add(crearIdentifier(valorType, valorValue));
		return identifierArray;
	}

	/**
	 * Utility para devolver un Json con la estructura de in 'identifier'   
	 * @param valorType Texto a asignar en 'type'
	 * @param valorValue Valor a asignar a 'value'
	 * @return
	 */
	private JsonObject crearIdentifier(String valorType, String valorValue) {
		JsonObject identifier = new JsonObject();
		identifier.addProperty("type", valorType);
		identifier.addProperty("value", valorValue);
		return identifier;
	}

	/**
	 * Devuelve el Json correspondiente a Subject de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonElement crearContractSubject(Solicitud solicitud) throws SystemException {

		// Titular

		//	    "subject": {
		//	      "person": {
		//	        "identifier": [
		//	          {
		//	            "type": "TIPO_IDENTIFICACION",
		//	            "value": "CC"
		//	          },
		//	          {
		//	            "type": "NUMERO_IDENTIFICACION",
		//	            "value": "123456"
		//	          }
		//	        ],
		//	        "active": true,
		//	        "telecom": [
		//	          {
		//	            "system": "TELEFONO_CONTACTO_PRINCIPAL",
		//	            "value": "3115555555"
		//	          },
		//	          {
		//	            "system": "CELULAR",
		//	            "value": "3115555555"
		//	          },
		//	          {
		//	            "system": "CORREO_ELECTRONICO",
		//	            "value": "abc@correo.com"
		//	          }
		//	        ],
		//	        "address": [
		//	          {
		//	            "use": "DIRECCION_CORRESPONDENCIA_FAMILIA",
		//	            "text": "Calle 100 # 11b - 67",
		//	            "city": {
		//	              "code": "11001",
		//	              "name": "Bogota"
		//	            }
		//	          }
		//	        ]
		//	      },
		//	      "organization": {
		//	        "identifier": [
		//	          {
		//	            "type": "TIPO_IDENTIFICACION",
		//	            "value": "NI"
		//	          },
		//	          {
		//	            "type": "NUMERO_IDENTIFICACION",
		//	            "value": "123456"
		//	          }
		//	        ],
		//	        "telecom": [
		//	          {
		//	            "system": "TELEFONO_CONTACTO_PRINCIPAL",
		//	            "value": "3115555555"
		//	          },
		//	          {
		//	            "system": "CELULAR",
		//	            "value": "3115555555"
		//	          },
		//	          {
		//	            "system": "CORREO_ELECTRONICO",
		//	            "value": "abc@correo.com"
		//	          }
		//	        ],
		//	        "address": [
		//	          {
		//	            "use": "DIRECCION_CORRESPONDENCIA_FAMILIA",
		//	            "text": "Calle 100 # 11b - 67",
		//	            "city": {
		//	              "code": "11001",
		//	              "name": "Bogota"
		//	            }
		//	          }
		//	        ]
		//	      }
		//	    }

		// Si es persona juridica utilizamos organization en lugar de person
		JsonObject jsonObj = new JsonObject();

		JsonObject subject = new JsonObject();

		JsonObject personOrganization = new JsonObject();

		//	NUMERO_IDENTIFICACION y TIPO_IDENTIFICACION del Titular
		String tipoIdent = "";
		String numIdent = "";
		Boolean conBeneficios = Boolean.FALSE;
		Boolean esNIT = Boolean.FALSE;
		if (solicitud.getTitular() != null && !solicitud.getTitular().isNull()){
			conBeneficios = solicitud.getTitular().getTitularTitularConBeneficios();
			tipoIdent = solicitud.getTitular().getTipoIdentificacion().getTipoIdentificacionNombreCorto();
			esNIT = solicitud.getTitular().getTipoIdentificacion().getTipoIdentificacionNIT();
			numIdent = solicitud.getTitular().getTitularNumIdentificacion();
		} else{
			conBeneficios = solicitud.getContratante().getContratanteTitularConBeneficios();
			tipoIdent = solicitud.getContratante().getTipoIdentificacion().getTipoIdentificacionNombreCorto();
			esNIT = solicitud.getContratante().getTipoIdentificacion().getTipoIdentificacionNIT();
			numIdent = solicitud.getContratante().getContratanteNumIdentificacion();
		}
		JsonArray identifier = new JsonArray();
		jsonObj.addProperty("type", "TIPO_IDENTIFICACION");
		jsonObj.addProperty("value", tipoIdent);
		identifier.add(jsonObj);
		jsonObj = new JsonObject();
		jsonObj.addProperty("type", "NUMERO_IDENTIFICACION");
		jsonObj.addProperty("value", numIdent);
		identifier.add(jsonObj);
		personOrganization.add("identifier", identifier);

		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null){

			// "active" Titular con beneficios
			personOrganization.addProperty("active", conBeneficios);

			// GdC-37 Req 3:
			// BH se enviarï¿½n unos valores fijos (comodï¿½n) para evitar que el nuevo servicio rechace la peticiï¿½n.
			// Los valores fijos para enviar en este caso excepcional son:
			// -	Telï¿½fono celular: 1234567890
			// -	Correo electrï¿½nico: COMODIN@COLSANITAS.COM
			// -	Direcciï¿½n completa: COMODIN
			// -	Departamento: Distrito capital
			// -	Municipio: Bogotï¿½ D.C.
			// -	Rural: No			
			// telecom
			// Ajuste para no inicializar a valores comodin (anular GdC-37 Req 3). AT aprobada por email el 01-09-2026 
			String telefonoFijo = null;
			String celular = null;
			String email = null;
			String dirCompleta = null;
			String codLegalMunicipio = null;
			String nombreMunicipio = null;


			// CELULAR, TELEFONO_CONTACTO_PRINCIPAL y CORREO_ELECTRONICO del contratante
			//  GdC-48 Rdeq 2: Para el caso de solicitudes de inclusiï¿½n de familias a 
			//   contratos colectivos los datos del correo electrï¿½nico y celular deben ser los 
			//    del titular de la familia y no los del contratante.
			if (Long.valueOf(2).equals(solicitud.getSolicitudTramite()) && solicitud.getSolicitudNumFamilia() == null){
				if (solicitud.getTitularPersona() != null && !solicitud.getTitularPersona().isNull()) {
					if (solicitud.getTitularPersona().getPersonaTelefonoFijo() != null) { 
						telefonoFijo = solicitud.getTitularPersona().getPersonaTelefonoFijo();
					}
					if (solicitud.getTitularPersona().getPersonaTelefonoCelular() != null) { 
						celular = solicitud.getTitularPersona().getPersonaTelefonoCelular();
					}
					if (solicitud.getTitularPersona().getPersonaCorreoElectronico() != null) { 
						email = solicitud.getTitularPersona().getPersonaCorreoElectronico();
					}
				}
			}
			else {
				if (solicitud.getContratantePersona() != null && !solicitud.getContratantePersona().isNull()) {
					if (solicitud.getContratantePersona().getPersonaTelefonoFijo() != null) { 
						telefonoFijo = solicitud.getContratantePersona().getPersonaTelefonoFijo();
					}
					if (solicitud.getContratantePersona().getPersonaTelefonoCelular() != null) { 
						celular = solicitud.getContratantePersona().getPersonaTelefonoCelular();
					}
					if (solicitud.getContratantePersona().getPersonaCorreoElectronico() != null) { 
						email = solicitud.getContratantePersona().getPersonaCorreoElectronico();
					}
				}
			}


			JsonArray telecom = new JsonArray();
			if (telefonoFijo != null && !telefonoFijo.isEmpty()) { 
				jsonObj = new JsonObject();
				jsonObj.addProperty("system", "TELEFONO_CONTACTO_PRINCIPAL");
				jsonObj.addProperty("value", telefonoFijo);
				telecom.add(jsonObj);
			}
			if (celular != null && !celular .isEmpty()) {
				jsonObj = new JsonObject();
				jsonObj.addProperty("system", "CELULAR");
				jsonObj.addProperty("value", celular);
				telecom.add(jsonObj);
			}

			if (email != null && !email.isEmpty()) {
				jsonObj = new JsonObject();
				jsonObj.addProperty("system", "CORREO_ELECTRONICO");
				jsonObj.addProperty("value", email);
				telecom.add(jsonObj);
			}

			if (!telecom.isEmpty()) {
				personOrganization.add("telecom", telecom);
			}


			if (solicitud.getDireccion() != null && !solicitud.getDireccion().isNull()) {
				dirCompleta = solicitud.getDireccion().getDireccionDireccionCompleta();
				codLegalMunicipio = solicitud.getDireccion().getMunicipio().getMunicipioCodigoLegal();
				nombreMunicipio = solicitud.getDireccion().getMunicipio().getMunicipioDescripcion();

				JsonArray dirs = new JsonArray();
				jsonObj = new JsonObject();
				jsonObj.addProperty("use", "DIRECCION_CORRESPONDENCIA_FAMILIA");
				jsonObj.addProperty("text", dirCompleta);
				JsonObject city = new JsonObject();
				city.addProperty("code", codLegalMunicipio);
				city.addProperty("name", nombreMunicipio);
				jsonObj.add("city", city);
				dirs.add(jsonObj);
				personOrganization.add("address", dirs);
			}

		}

		if (esNIT) {
			subject.add("organization", personOrganization);
		} else {
			subject.add("person", personOrganization);
		}

		return subject;
	}


	/**
	 * Devuelve el Json correspondiente a SupportingInfo de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 */
	private JsonArray crearContractSupportingInfo(Solicitud solicitud) {
		JsonArray supportingInfo = new JsonArray();
		// Observaciones
		if (solicitud.getSolicitudobservacions() != null && !solicitud.getSolicitudobservacions().isEmpty()){
			// Las troceamos en bloques de 100 caracteres
			String obs = solicitud.getSolicitudobservacions();
			while (!obs.isEmpty()){
				JsonObject obsJson = new JsonObject();
				obsJson.addProperty("code", 2);
				if (obs.length() <= 100){
					obsJson.addProperty("value", obs);
					obs = "";
				} else{
					obsJson.addProperty("value", obs.substring(0, 100));
					obs = obs.substring(100);
				}
				supportingInfo.add(obsJson);
			}
		}

		return supportingInfo;
	}

	/**
	 * Devuelve el Json correspondiente a term de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonArray crearContractTerm(Solicitud solicitud) throws SystemException {
		//		"term": [
		//		         {
		//		           "offer": {
		//		             "answer": [
		//		               {
		//		                 "valueString": "5"
		//		               }
		//		             ],
		//		             "text": "CODIGO_TARIFA"
		//		           }
		//		         }
		//		        ]
		JsonArray term = new JsonArray();

		// CODIGO_TARIFA
		// Cï¿½digo Tarifa
		// GdC-23 Req. 1: 'Prorrateos con grupo asociado
		//	Para afiliaciones nuevas, si se selecciona prorrateo y la solicitud tiene grupo asociado, el sistema debe comportarse como si se 
		//	 seleccionara el check de traslado, asï¿½:
		//		Debe enviar el cï¿½digo de tarifa (tag ï¿½CodigoTarifaï¿½) configurado para traslado.
		//		No debe enviar informaciï¿½n para los tags CodConvenioEspecial, DisminucionCarencia.
		if (solicitud.getGrupoAsociado() != null && !solicitud.getGrupoAsociado().isNull() && 
				solicitud.getSolicitudPorcentajeProrrateo() != null && solicitud.getSolicitudPorcentajeProrrateo() > 0) {
			String tarifaTraslado = userFunctionsSrv.fugetValorParametroConfFun("TARIFATRASLADO");
			if (tarifaTraslado == null || tarifaTraslado.length() == 0){
				throw new SystemException("Código de tarifa traslado no encontrado en parametrizacion");
			}

			term.add(crearTerm("CODIGO_TARIFA", tarifaTraslado));
		} else {

			String codigoTarifa = "";

			if (solicitud.getGrupoAsociado() != null && !solicitud.getGrupoAsociado().isNull()){
				//term.add(crearTerm("CODIGO_TARIFA", solicitud.getGrupoAsociado().getCodTarifaCORE()));
				codigoTarifa = solicitud.getGrupoAsociado().getGrupoAsociadoCodTarifaCORE();

				// GcD12:46
				// COD_CONVENIO_ESPECIAL
				term.add(crearTerm("COD_CONVENIO_ESPECIAL", solicitud.getGrupoAsociado().getGrupoAsociadoCodigoLegal()));

				if (solicitud.getGrupoAsociado().getGrupoAsociadoDisminucionCarencia() != null){

					term.add(crearTerm("PERIODOS_DE_CARENCIA", solicitud.getGrupoAsociado().getGrupoAsociadoDisminucionCarencia()));
				}

			} else{
				codigoTarifa = solicitud.getSolicitudCodTarifa();
				// term.add(crearTerm("CODIGO_TARIFA", solicitud.getCodTarifa()));
			}


			// GdC-14: Req. 13: Prorrateo. 
			//  Si se registra el porcentaje la solicitud debe tener el mismo comportamiento que la marcaciï¿½n de traslado. 
			//  Cuando se envï¿½a la solicitud a BH se debe enviar el cï¿½digo de tarifa de traslado que figura en la parametrizaciï¿½n
			//  Esta asignaciï¿½n de tarifa prevalece sobre la del Grupo asociado, por eso se realiza despues
			if (solicitud.getSolicitudPorcentajeProrrateo() != null && solicitud.getSolicitudPorcentajeProrrateo() > 0) {

				String tarifaTraslado = userFunctionsSrv.fugetValorParametroConfFun("TARIFATRASLADO");
				if (tarifaTraslado == null || tarifaTraslado.length() == 0){
					throw new SystemException("Código de tarifa traslado no encontrado en parametrizacion");
				}
				codigoTarifa = tarifaTraslado;
				//term.add(crearTerm("CODIGO_TARIFA", tarifaTraslado));
			}

			term.add(crearTerm("CODIGO_TARIFA", codigoTarifa));
		}



		// PORCENTAJE_PRORRATEO
		if (solicitud.getSolicitudPorcentajeProrrateo() != null && solicitud.getSolicitudPorcentajeProrrateo() > 0) {
			term.add(crearTerm("PORCENTAJE_PRORRATEO", solicitud.getSolicitudPorcentajeProrrateo()));
		}		
		// COD_FRECUENCIA_PAGO
		if (solicitud.getPeriodicidad() != null && !solicitud.getPeriodicidad().isNull() ) {
			term.add(crearTerm("COD_FRECUENCIA_PAGO", solicitud.getPeriodicidad().getPeriodicidadCodigo().toString()));
		}
		// COD_METODO_PAGO
		if (solicitud.getFormaPago() != null && !solicitud.getFormaPago().isNull() ){
			term.add(crearTerm("COD_METODO_PAGO", solicitud.getFormaPago().getFormaPagoCodigo().toString()));
		}
		// COD_EXENTO_IVA
		if (solicitud.getSolicitudexentoIVA() != null){
			term.add(crearTerm("COD_EXENTO_IVA", solicitud.getSolicitudexentoIVA()));
		}

		// IND_ENVIO_FACTURA
		// GdC-23 Req 5: Tampoco se enviarï¿½ a BH los tags ï¿½IndEnvioFacturaï¿½, ï¿½MailTitularFamiliaï¿½ y ï¿½NumeroCelularTitularFamiliaï¿½
		if (Long.valueOf(1).equals(solicitud.getSolicitudTramite()) || solicitud.getSolicitudNumFamilia() == null) {
			if (solicitud.getTipoFactura() != null && !solicitud.getTipoFactura().isNull()) {
				term.add(crearTerm("IND_ENVIO_FACTURA", solicitud.getTipoFactura().getTipoFacturaCodigo()));
			} else {
				term.add(crearTerm("IND_ENVIO_FACTURA", 2L));
			}
		}

		// PERIODOS_DE_CARENCIA_FAMILIA
		// No lo tenemos

		return term;
	}

	/**
	 * Utility para crear un Json de un 'term'
	 * @param valorText texto a asignar a 'text'
	 * @param valueString Valor a asignar
	 * @return El Json
	 */
	private JsonObject crearTerm(String valorText, String valueString) {

		//		{
		//	        "offer": {
		//	          "answer": [
		//	            {
		//	              "valueString": "5"
		//	            }
		//	          ],
		//	          "text": "COD_METODO_PAGO"
		//	        }
		//	      }
		JsonObject term = new JsonObject();

		JsonObject offer = new JsonObject();
		JsonArray answer = new JsonArray();
		JsonObject objectValue = new JsonObject();
		objectValue.addProperty("valueString", valueString);
		answer.add(objectValue);
		offer.add("answer", answer);
		offer.addProperty("text", valorText);

		term.add("offer", offer);
		return term;
	}

	/**
	 * Utility para crear un Json de un 'term'
	 * @param valorText texto a asignar a 'text'
	 * @param valueBool Valor a asignar
	 * @return El Json
	 */
	private JsonObject crearTerm(String valorText, Boolean valueBool) {
		JsonObject term = new JsonObject();

		JsonObject offer = new JsonObject();
		JsonArray answer = new JsonArray();
		JsonObject objectValue = new JsonObject();
		objectValue.addProperty("valueBoolean", valueBool);
		answer.add(objectValue);
		offer.add("answer", answer);
		offer.addProperty("text", valorText);

		term.add("offer", offer);
		return term;
	}

	/**
	 * Utility para crear un Json de un 'term'
	 * @param valorText texto a asignar a 'text'
	 * @param valueDate Valor a asignar
	 * @return El Json
	 */
	private JsonObject crearTerm(String valorText, Date valueDate) {
		JsonObject term = new JsonObject();

		JsonObject offer = new JsonObject();
		JsonArray answer = new JsonArray();
		JsonObject objectValue = new JsonObject();
		objectValue.addProperty("valueDate", formatearFechaHoraREST(valueDate));
		answer.add(objectValue);
		offer.add("answer", answer);
		offer.addProperty("text", valorText);

		term.add("offer", offer);
		return term;
	}


	/**
	 * Utility para crear un Json de un 'term'
	 * @param valorText texto a asignar a 'text'
	 * @param valueLong Valor a asignar
	 * @return El Json
	 */
	private JsonObject crearTerm(String valorText, Long valueLong) {

		JsonObject term = new JsonObject();

		JsonObject offer = new JsonObject();
		JsonArray answer = new JsonArray();
		JsonObject objectValue = new JsonObject();
		objectValue.addProperty("valueDecimal", valueLong);
		answer.add(objectValue);
		offer.add("answer", answer);
		offer.addProperty("text", valorText);

		term.add("offer", offer);
		return term;
	}

	/**
	 * Utility para crear un Json de un 'term'
	 * @param valorText texto a asignar a 'text'
	 * @param valueDouble Valor a asignar
	 * @return El Json
	 */
	private JsonObject crearTerm(String valorText, Double valueDouble) {

		JsonObject term = new JsonObject();

		JsonObject offer = new JsonObject();
		JsonArray answer = new JsonArray();
		JsonObject objectValue = new JsonObject();
		objectValue.addProperty("valueDecimal", valueDouble);
		answer.add(objectValue);
		offer.add("answer", answer);
		offer.addProperty("text", valorText);

		term.add("offer", offer);
		return term;
	}

	/**
	 * Devuelve el Json correspondiente a Authority de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonArray crearContractAuthority(Solicitud solicitud) throws SystemException {
		//  "authority": [
		//	{
		//		"identifier": [
		//	    	{
		//	        	"type": "CODIGO",
		//	            "value": "25"
		//	        }
		//	    ],
		//	 	"type": [
		//	    	{
		//	        	"name": "GRUPO_ASOCIADO_FAMILIAR"
		//	        }
		//	    ]
		//	}
		// ]

		JsonArray authority = new JsonArray();

		if (solicitud.getGrupoAsociado() != null && !solicitud.getGrupoAsociado().isNull()) {
			authority.add(crearIdentifierAsArray("type", solicitud.getGrupoAsociado().getGrupoAsociadoCodigoLegal()));
			JsonArray type = new JsonArray();
			JsonObject name = new JsonObject();
			name.addProperty("name", "GRUPO_ASOCIADO_FAMILIAR");
			type.add(name);
			authority.add(type);;

		}

		return authority;
	}

	/**
	 * Devuelve el Json correspondiente a Identifier de Contract
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonArray crearContractIdentifier(Solicitud solicitud) throws SystemException {

		JsonArray identifier = new JsonArray();

		//		NUM_SOLICITUD
		identifier.add(crearIdentifier("NUM_SOLICITUD", solicitud.getAesolicitudes().getAesolicitudesNumerosolicitud()));
		//		COD_PLAN
		identifier.add(crearIdentifier("PLAN", solicitud.getAesolicitudes().getPlanT().getPlanesCodigoLegal()));
		//		NUM_CONTRATO
		if (Long.valueOf(2).equals(solicitud.getSolicitudTramite()) ){
			identifier.add(crearIdentifier("CONTRATO", solicitud.getSolicitudNumContratoInclusion()));
			identifier.add(crearIdentifier("FAMILIA", solicitud.getSolicitudNumFamilia()));
		}

		return identifier;
	}

	/**
	 * Devuelve el Json correspondiente a Coverage
	 * @param solicitud Solicitud
	 * @return El Json 
	 * @throws SystemException
	 */
	private JsonArray crearCoverage(Solicitud solicitud) throws SystemException {
		// Beneficiarios
		JsonArray coverage = new JsonArray();

		if (solicitud.getUsuarios() != null){
			for (Usuario usuario : solicitud.getUsuarios()) {
				coverage.add(crearCoverageBeneficiario(usuario, solicitud));
			}        	
		}
		return coverage;
	}

	/**
	 * Devuelve el Json correspondiente a Coverage de un beneficiario
	 * @param usuario Beneficiario
	 * @param solicitud
	 * @return EL Json
	 * @throws SystemException
	 */
	private JsonObject crearCoverageBeneficiario(Usuario usuario, Solicitud solicitud) throws SystemException {
		JsonObject beneficiario = new JsonObject();
		Persona personaBeneficiario = usuario.getUsuarioPersona();

		//// suscriber
		JsonObject suscriber = new JsonObject();
		// coverage.suscriber.identifier.type.NUMERO_IDENTIFICACION.value
		// coverage.suscriber.identifier.type.TIPO_IDENTIFICACION.value
		JsonArray identifier = new JsonArray();
		identifier.add(crearIdentifier("TIPO_IDENTIFICACION", personaBeneficiario.getTipoIdentificacion().getTipoIdentificacionNombreCorto()));
		identifier.add(crearIdentifier("NUMERO_IDENTIFICACION", personaBeneficiario.getPersonaNumIdentificacion()));
		suscriber.add("identifier", identifier);

		// EPS
		// coverage.suscriber.managingOrganization.identifier.type.CODIGO.value
		// CodigoLegalEPSActual (opcional)
		if (usuario.getEps() != null && !usuario.getEps().isNull()){
			JsonObject managingOrganization = new JsonObject();
			managingOrganization.add("identifier", crearIdentifierAsArray("CODIGO", usuario.getEps().getEpsCodigo().toString()));
			suscriber.add("managingOrganization", managingOrganization);
		}
		beneficiario.add("suscriber", suscriber);

		//// contract
		JsonArray contractArray = new JsonArray();
		JsonObject contract= new JsonObject();

		// contract.applies
		if (Long.valueOf(2).equals(solicitud.getSolicitudTramite()) ){
			JsonObject appliesBene = new JsonObject();
			appliesBene.addProperty("start", formatearFechaHoraREST(solicitud.getSolicitudFInicioContrato()));
			contract.add("applies", appliesBene );
		}

		// contract.role
		contract.add("role",crearIdentifierAsArray("ID_TIPO_USUARIO", usuario.getUsuarioCodigoTipoUsuario() .toString()));

		//// contract.term
		JsonArray term = new JsonArray();

		// CodigoLegalMPAnterior (opcional)
		if (usuario.getCompanyia() != null && !usuario.getCompanyia().isNull()){
			term.add(crearTerm("MP_ANTERIOR", usuario.getCompanyia().getCompanyiaCodigo().toString()));
		}
		// BB Gestacion
		term.add(crearTerm("BEBE_GESTACION", usuario.getUsuarioBebeGestante()));

		// FEC_ANTIGUEDAD_BENEFICIARIO (opcional)
		if (usuario.getUsuarioFechaAntiguedadBeneficiario()  != null) {
			term.add(crearTerm("FEC_ANTIGUEDAD_BENEFICIARIO", usuario.getUsuarioFechaAntiguedadBeneficiario()));
		}
		// BANCO_REPUBLICA (opcional)
		if (usuario.getUsuariocodigoBancoRep() != null) {
			term.add(crearTerm("BANCO_REPUBLICA", usuario.getUsuariocodigoBancoRep()));
		}

		// GRUPO_BASICO_FAMILIAR No tratado
		// PERIODOS_CARENCIA_BENEFICARIO No tratado

		contract.add("term", term);


		// contract.legallyBinding  Anexos del usuario
		// El beneficiario solo puede tener dos anexos como maximo:
		// - Anexo Neonatal
		// - Anexo de Maternidad
		JsonArray legallyBinding = new JsonArray();

		if (usuario.getAnexoMat() != null && !usuario.getAnexoMat().isNull() ){
			JsonArray termAnexos = new JsonArray();
			JsonObject anexo = new JsonObject();
			JsonObject contractAnexo = new JsonObject();
			if (usuario.getAnexoMat().getInstitucionMaternal() != null && !usuario.getAnexoMat().getInstitucionMaternal().isNull() &&
					usuario.getAnexoMat().getInstitucionMaternal().getTarifa() != null && !usuario.getAnexoMat().getInstitucionMaternal().getTarifa().isNull()	){

				termAnexos.add(crearTerm("CODIGO_TARIFA", usuario.getAnexoMat().getInstitucionMaternal().getTarifa().getTarifaCodigo()));

				if (usuario.getAnexoMat().getInstitucionMaternal().getTarifa().getProductoT() != null && 
						!usuario.getAnexoMat().getInstitucionMaternal().getTarifa().getProductoT().isNull() && 
						usuario.getAnexoMat().getInstitucionMaternal().getTarifa().getProductoT().getProductosCodAnexoMat() != null){
					contractAnexo.add("identifier", crearIdentifierAsArray("PLAN", usuario.getAnexoMat().getInstitucionMaternal().getTarifa().getProductoT().getProductosCodAnexoMat().toString()));
				}
			}

			// Fecha de inicio
			JsonObject applies = new JsonObject();
			applies.addProperty("start", formatearFechaHoraREST(solicitud.getSolicitudFInicioContrato()));
			contractAnexo.add("applies", applies );


			// FECHA_ULTIMA_REGLA
			if (usuario.getAnexoMat().getAnexoMatFechaUltRegla() != null){
				termAnexos.add(crearTerm("FECHA_ULTIMA_REGLA", usuario.getAnexoMat().getAnexoMatFechaUltRegla()));
			}
			// NUM_CUOTAS_PAGO
			if(usuario.getAnexoMat().getAnexoMatNumCuotas() != null){
				termAnexos.add(crearTerm("NUM_CUOTAS_PAGO", usuario.getAnexoMat().getAnexoMatNumCuotas()));
			}

			// COD_FRECUENCIA_PAGO
			// GdC10-13
			termAnexos.add(crearTerm("COD_FRECUENCIA_PAGO", usuario.getAnexoMat().getPeriodicidad().getPeriodicidadCodigo().toString()));

			contractAnexo.add("term", termAnexos);

			anexo.add("contract", contractAnexo);
			legallyBinding.add(anexo);                
		}
		if (usuario.getAnexoNeo() != null && !usuario.getAnexoNeo().isNull() ){
			JsonArray termAnexos = new JsonArray();
			JsonObject anexo = new JsonObject();
			JsonObject contractAnexo = new JsonObject();

			if (usuario.getAnexoNeo().getInstitucionNeonatal() != null && !usuario.getAnexoNeo().getInstitucionNeonatal().isNull() &&
					usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa() != null && !usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().isNull()	){

				termAnexos.add(crearTerm("CODIGO_TARIFA", usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().getTarifaCodigo()));

				if (usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().getProductoT() != null && 
						!usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().getProductoT().isNull() && 
						usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().getProductoT().getProductosCodAnexoNeo() != null){
					contractAnexo.add("identifier", crearIdentifierAsArray("PLAN", usuario.getAnexoNeo().getInstitucionNeonatal().getTarifa().getProductoT().getProductosCodAnexoNeo().toString()));
				}
			}

			// Fecha de inicio
			JsonObject applies = new JsonObject();
			applies.addProperty("start", formatearFechaHoraREST(solicitud.getSolicitudFInicioContrato()));
			contractAnexo.add("applies", applies );


			// NUM_CUOTAS_PAGO
			if(usuario.getAnexoNeo().getAnexoNeoNumCuotas() != null){
				termAnexos.add(crearTerm("NUM_CUOTAS_PAGO", usuario.getAnexoNeo().getAnexoNeoNumCuotas()));
			}

			// GdC10-13
			termAnexos.add(crearTerm("COD_FRECUENCIA_PAGO", usuario.getAnexoNeo().getPeriodicidad().getPeriodicidadCodigo().toString()));

			contractAnexo.add("term", termAnexos);

			anexo.add("contract", contractAnexo);
			legallyBinding.add(anexo);                 
		}        	

		contract.add("legallyBinding", legallyBinding);

		// action  No tratado
		contractArray.add(contract);
		beneficiario.add("contract", contractArray);

		// relationShip Parentesco 
		JsonObject relationShip = new JsonObject();
		relationShip.addProperty("code", usuario.getParentesco().getParentescoNombreCorto());
		beneficiario.add("relationShip", relationShip);

		return beneficiario;
	}


	/**
	 * Obtiene el token de autorización
	 * @return El token de autorización
	 * @throws SystemException
	 */
	private String generarAuthorization() throws SystemException {

		String retorno = "";

		try {
			URL url = new URL(urlToken + "/token");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", "Basic " + hashToken);

			String urlParameters  = "grant_type=password&username=" + usrToken + "&password=" + pwdToken;
			byte[] postData = urlParameters.getBytes();


			OutputStream os = conn.getOutputStream();
			os.write(postData);
			os.flush();
			os.close();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			StringBuilder response = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			br.close();
			isr.close();

			// Analizamos la respuesta obtenida
			// Se debe tomar de la respuesta del Api el access_token y el token_type
			String accesstoken = null;
			String tokentype = null;
			JsonObject responseJson = new Gson().fromJson(response.toString(), JsonObject.class);
			if (responseJson != null && responseJson.get("access_token") != null && responseJson.get("token_type") != null) {
				accesstoken = responseJson.get("access_token").getAsString();
				tokentype = responseJson.get("token_type").getAsString();
				retorno = tokentype + " " + accesstoken;
			}


		} catch (MalformedURLException e) {
			throw new SystemException(e);
		} catch (IOException e) {
			throw new SystemException(e);
		}


		return retorno;
	}


	private String formatearFechaHoraREST(Timestamp fechaHora) {
		SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateTimeFormat.format(fechaHora);
	}

	public String formatearFechaHoraREST(Date fecha) {
		return formatearFechaHoraREST(STDFunctions.toDateTimeFun(fecha, STDFunctions.formatToTimeFun(0, 0, 0)));
	}
	
	public static void guardarLlamadaBH(DatoRespuestaContrato result, Timestamp p_atrFechaHoraArg, String p_atrUsuarioArg, String p_atrCapacidadArg, String p_atrServicioArg, String p_atrSolicitudArg, String p_atrPeticionArg, String p_atrRespuestaArg, String p_atrTipoServicioArg, Double p_atrTiempoEjecucionArg, String p_atrTextoErrorArg){
		if (p_atrServicioArg == null || p_atrServicioArg.isEmpty()) {
			p_atrServicioArg = p_atrCapacidadArg;
		}

		DatoLogLlamadaBH dato = new DatoLogLlamadaBH();
		dato.setFechaHora(p_atrFechaHoraArg);
		dato.setUsuario(p_atrUsuarioArg);
		dato.setCapacidad(p_atrCapacidadArg.toUpperCase());
		dato.setServicio(p_atrServicioArg);
		dato.setSolicitud(p_atrSolicitudArg);
		dato.setPeticion(p_atrPeticionArg);
		dato.setRespuesta(p_atrRespuestaArg);
		dato.setTipoServicio(p_atrTipoServicioArg);
		dato.setTiempoEjecucion(p_atrTiempoEjecucionArg);
		dato.setTextoError(p_atrTextoErrorArg);
		result.addDatoLLamadasBH(dato);
	}	
}
