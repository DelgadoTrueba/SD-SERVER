package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_NewGame extends DTO_Output {

	private byte OID;
	
	public DTO_Output_NewGame(boolean err, Exception excep, byte OID) {
		super(err, excep);
		this.OID = OID;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.write(this.OID);
		}
	}

}
