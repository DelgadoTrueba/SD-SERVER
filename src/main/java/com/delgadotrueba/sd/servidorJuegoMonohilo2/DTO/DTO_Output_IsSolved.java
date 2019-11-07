package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_IsSolved extends DTO_Output {
	
	private boolean solved;
	
	public DTO_Output_IsSolved(boolean err, Exception excep, boolean solved) {
		super(err, excep);
		this.solved = solved;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.writeBoolean(solved);
		}
	}
}
