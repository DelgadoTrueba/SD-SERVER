package com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO;

import java.io.DataOutputStream;
import java.io.IOException;

public class DTO_Output_SelectedCardsP2 extends DTO_Output {
	
	private int selectedCards;
	
	public DTO_Output_SelectedCardsP2(boolean err, Exception excep, int selectedCards) {
		super(err, excep);
		this.selectedCards = selectedCards;
	}
	
	public void enviarDatosAlCliente(DataOutputStream dataOutput) throws IOException {
		super.enviarDatosAlCliente(dataOutput);
		if(!err) {
			dataOutput.writeInt(selectedCards);
		}
	}
}
