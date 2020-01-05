package com.delgadotrueba.sd.servidorJuegoMonohilo2.models;

public interface IGame {

	public byte newGame();
	
	public byte[][] obtenerTiposCartas();
	
	public int obtenerCartasEmparejadas();
	
	public byte obtenerPuntosJ1();
	
	public byte obtenerPuntosJ2();
	
	public boolean matchCardsP1(byte r1, byte c1, byte r2, byte c2);
	
	public boolean isSolved();
	
	public int obtenerCartasSeleccionadasJ2();

}
