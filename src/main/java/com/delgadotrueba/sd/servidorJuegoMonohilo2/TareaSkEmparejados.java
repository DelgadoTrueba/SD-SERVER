package com.delgadotrueba.sd.servidorJuegoMonohilo2;

import java.io.IOException;
import java.net.Socket;

public class TareaSkEmparejados implements Runnable {

	private SkEmparejados sk;
	private Socket socketcliente;
	
	public TareaSkEmparejados(SkEmparejados sk, Socket socketcliente){
		this.sk = sk;
		this.socketcliente = socketcliente;
	}
	
	@Override
	public void run() {
		System.out.println("Thread sk: cliente conectado");
		sk.process(socketcliente);
		System.out.println("Thread sk: cliente desconectado");
	}

}
