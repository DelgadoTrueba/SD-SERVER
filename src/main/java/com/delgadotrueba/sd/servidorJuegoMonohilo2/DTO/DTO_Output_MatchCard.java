package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_MatchCard extends DTO_Output {
	
	private boolean matched;
	
	public DTO_Output_MatchCard(boolean err, Exception excep, boolean matched) {
		super(err, excep);
		this.matched = matched;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.writeBoolean(matched);
		}
	}
}
