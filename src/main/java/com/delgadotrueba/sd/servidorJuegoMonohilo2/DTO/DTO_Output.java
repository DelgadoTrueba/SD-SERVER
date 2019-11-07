package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output {
	
	protected static boolean ERROR = true;
	protected static boolean NO_ERROR = false;
	
	protected boolean err;
	protected Exception excep;

	public DTO_Output(boolean err, Exception excep) {
		this.err = err;
		this.excep = excep;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		if(err) {
			dataOutput.writeBoolean(ERROR);
			/*ENVIAR EXCEPCION*/
		}
		else {
			dataOutput.writeBoolean(NO_ERROR);
		}
	}
	
}
