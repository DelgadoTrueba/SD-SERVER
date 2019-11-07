package com.delgadotrueba.sd.servidorJuegoMonohilo2.models;

public class ErrorHandler {
	
	public static void error(String tag, String message, boolean crash ){
		System.err.println( tag + message );
		if (crash) System.exit(-1);
	}
}
