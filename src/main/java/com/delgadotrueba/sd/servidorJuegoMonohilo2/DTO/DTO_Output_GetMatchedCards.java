package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_GetMatchedCards extends DTO_Output {

	private int matchedCards;
	
	public DTO_Output_GetMatchedCards(boolean err, Exception excep, int matchedCards) {
		super(err, excep);
		this.matchedCards = matchedCards;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.writeInt(matchedCards);
		}
	}
}
