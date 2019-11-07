package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_NewGame extends DTO_Output {

	public DTO_Output_NewGame(boolean err, Exception excep) {
		super(err, excep);
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
	}

}
