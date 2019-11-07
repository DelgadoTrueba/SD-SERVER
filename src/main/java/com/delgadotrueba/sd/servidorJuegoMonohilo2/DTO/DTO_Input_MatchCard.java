package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataInputStream;
import java.io.IOException;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidDataInterfaceException;

public class DTO_Input_MatchCard extends DTO_Input{

	private byte r1;
	private byte c1;
	private byte r2;
	private byte c2;
	
	public DTO_Input_MatchCard(byte codop, DataInputStream dataInput) throws InvalidDataInterfaceException {
		super(codop);
		this.inicializarDatosApartirDeMensaje(dataInput);
	}
	
	protected void inicializarDatosApartirDeMensaje(DataInputStream dataInput) throws InvalidDataInterfaceException {
		try {
			r1 = dataInput.readByte();
			c1 = dataInput.readByte();
			r2 = dataInput.readByte();
			c2 = dataInput.readByte();
		} catch (IOException e) {
			throw new InvalidDataInterfaceException();
		}
		
	}
	
	public byte getR1() {
		return r1;
	}

	public byte getC1() {
		return c1;
	}

	public byte getR2() {
		return r2;
	}

	public byte getC2() {
		return c2;
	}

}
