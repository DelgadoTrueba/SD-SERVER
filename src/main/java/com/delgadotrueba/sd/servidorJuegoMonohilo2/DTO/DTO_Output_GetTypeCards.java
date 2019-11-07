package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_GetTypeCards extends DTO_Output {

	private byte[][] typeCards;
	
	public DTO_Output_GetTypeCards(boolean err, Exception excep, byte[][] typeCards) {
		super(err, excep);
		this.typeCards = typeCards;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			byte[] tipos = tradudirTipoCarta(this.typeCards);
			dataOutput.write(tipos);
		}
	}
	
	//////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	//////////////////////////////////////////////////////////////////

	private byte[] tradudirTipoCarta(byte[][] tipos) {
		byte[] bytes_tipos = new byte[tipos.length*tipos.length];
		int i = 0;
		for (int row = 0; row < tipos.length; row++) {
			   for (int column = 0; column < tipos[0].length; column++) {
				   byte tipo = tipos[row][column];
				   byte aux = tipo;
				   bytes_tipos[i] = aux;
				   i++;
			   }
		}
		return bytes_tipos;
	}
	
}
