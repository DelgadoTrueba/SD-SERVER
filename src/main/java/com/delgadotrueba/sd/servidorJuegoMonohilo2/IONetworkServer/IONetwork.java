package com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IONetwork {

	protected DataInputStream dataInput;
	protected DataOutputStream dataOutput;

	private Socket socketCliente;
	
	public IONetwork(Socket socketCliente) throws IOException {
		this.socketCliente = socketCliente;
		
		InputStream inputN = null;
		OutputStream outputN = null;
				
		inputN = socketCliente.getInputStream();
		outputN = socketCliente.getOutputStream();
		this.dataInput = new DataInputStream(inputN);
		this.dataOutput = new DataOutputStream(outputN);
		
	}
	
	protected void cerrarSocket() throws IOException {
		this.socketCliente.close();
	}
	
}
