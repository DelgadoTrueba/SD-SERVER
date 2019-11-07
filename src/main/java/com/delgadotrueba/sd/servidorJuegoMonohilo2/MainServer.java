package com.delgadotrueba.sd.servidorJuegoMonohilo2;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer.ServidorJuegoMonohilo;

public class MainServer {

	public static void main(String[] args) {
		ServidorJuegoMonohilo juego = new ServidorJuegoMonohilo();
		juego.init();
	}

}
