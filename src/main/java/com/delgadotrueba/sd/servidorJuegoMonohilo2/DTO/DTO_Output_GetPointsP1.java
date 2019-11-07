package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_GetPointsP1 extends DTO_Output {
	
	private byte points;
	
	public DTO_Output_GetPointsP1(boolean err, Exception excep, byte points) {
		super(err, excep);
		this.points = points;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.write(this.points);
		}
	}
}
