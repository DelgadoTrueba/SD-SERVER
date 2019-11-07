package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataInputStream;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidDataInterfaceException;

public abstract class DTO_Input {

	protected byte codop;

	public DTO_Input(byte codop) {
		this.codop = codop;
	}

	public byte getCodop() {
		return codop;
	}	
	
	protected abstract void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException ;
	
}
