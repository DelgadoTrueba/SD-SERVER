package com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidCodopException;

public class IONetworkServer extends IONetwork{
	
	private static byte codopMIN = 1;
	private static byte codopMAX = 8;

	public IONetworkServer(Socket socketCliente) throws IOException {
		super(socketCliente);
	}
	
	public void cerrarSocket() throws IOException {
		super.cerrarSocket();
	}
	
	/*********************************************************************************/
	/** RECEPCIÓN DE MENSAJES
	/*********************************************************************************/
	
	public byte recibir_codop() throws InvalidCodopException {
		try {
			byte codop = super.dataInput.readByte();
			if(codop < codopMIN || codop > codopMAX){
				throw new InvalidCodopException();				
			}
			return codop;
		} catch (IOException e) {
			throw new InvalidCodopException();
		}
	}
	
	public byte recibir_oid() throws InvalidCodopException {
		try {
			byte oid = super.dataInput.readByte();
			return oid;
		} catch (IOException e) {
			throw new InvalidCodopException();
		}
	}

	/*********************************************************************************/
	/** ENVIO DE RESPUESTAS
	/*********************************************************************************/

	public void enviarRespuesta(DTO_Output dtoOutput) throws IOException {
		dtoOutput.enviarDatosAlCliente(super.dataOutput);
	}

	
	/*********************************************************************************/
	/** GETTERS AND SETTERS
	/*********************************************************************************/

	public DataInputStream getDataIn() {
		return super.dataInput;
	}

	public DataOutputStream getDataOut() {
		return super.dataOutput;
	}

	

}
