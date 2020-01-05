package com.delgadotrueba.sd.servidorJuegoMonohilo2.models;

import java.util.Random;

public class PartidaModel implements IGame {
	
	private BoardModel boardModel;
	private byte puntosJ1;
	private byte puntosJ2;
	private int cartasSeleccionadasJugador2;
	
	public PartidaModel() {
		this.boardModel = new BoardModel(4, 4);
		this.puntosJ1 = 0;
		this.puntosJ2 = 0;
		this.cartasSeleccionadasJugador2 = 0;
	}
	
	public byte[][] obtenerTiposCartas() {
		return this.boardModel.getTypeCards();
	}
	
	public int obtenerCartasEmparejadas() {
		return this.boardModel.getMatchedCard();
	}

	public byte obtenerPuntosJ1() {
		return puntosJ1;
	}

	public byte obtenerPuntosJ2() {
		return puntosJ2;
	}

	public boolean matchCardsP1(int r1, int c1, int r2, int c2) {
		if(!this.boardModel.isCardValid(r1, c1) || !this.boardModel.isCardValid(r2, c2)) {
			//throw new CardNotValidException();	
		}
		
		
		this.boardModel.setCardSelected(r1, c1);
		this.boardModel.setCardSelected(r2, c2);
				
		if(!this.boardModel.areSelectedCardsSameType()) {
			this.boardModel.setSelectedCardsHidden();
			/******************************************/
			this.cartasSeleccionadasJugador2 = 0;
			/*****************************************/
			this.emparejarCartasRival();
			System.out.println("<><><>");
			System.out.println(
					"CARTAS SELECCIONDAS JUGADOR 2: " + 
					String.format("%16s", Integer.toBinaryString(this.cartasSeleccionadasJugador2)).replace(' ', '0')
					);
			System.out.println("<><><>");
			return false;
		}
		else {
			this.boardModel.setSelectedCardsMatched();
			this.puntosJ1++;
			return true;
		}
	}
	
	private void emparejarCartasRival() {
		int r1 =-1;
		int c1 =-1;
		int r2 =-1;
		int c2 =-1;
		Random random = new Random();
		
		while(r1 == -1 || c1 == -1 || r2 == -1 || c2 == -1) {
			int row = (0 + random.nextInt(4));
			int col = (0 + random.nextInt(4));
			
			if(r1 == -1 && c1 == -1 && this.boardModel.isCardValid(row, col)) {
				r1 = row;
				c1 = col;
			}else if(r2 == -1 && c2 == -1 && this.boardModel.isCardValid(row, col) && !(row == r1 && col == c1)) {
				r2 = row;
				c2 = col;
			}
		}
				
		this.boardModel.setCardSelected(r1, c1);
		this.almacenarCarta(r1, c1);
		this.boardModel.setCardSelected(r2, c2);
		this.almacenarCarta(r2, c2);
		System.out.println("JUGADOR2: "+r1+c1+r2+c2);
		
		if (this.boardModel.areSelectedCardsSameType()){
			this.boardModel.setSelectedCardsMatched();	
			this.puntosJ2++;
			if(this.boardModel.isSolved()) {
				return;
			}
			else {
				this.emparejarCartasRival();
			}
		} else {
			this.boardModel.setSelectedCardsHidden();
			return;
		}
	}
	
	public boolean isSolved() {
		return this.boardModel.isSolved();
	}
	
	public int obtenerCartasSeleccionadasJ2() {
		return this.cartasSeleccionadasJugador2;
	}
	
	private void almacenarCarta(int row, int col) {
		int R1C1 = 1; // 0000 0000 0000 0001
		int R1C2 = 2; // 0000 0000 0000 0010
		int R1C3 = 4; // 0000 0000 0000 0100
		int R1C4 = 8; // 0000 0000 0000 1000
		
		int R2C1 = 16; // 0000 0000 0001 0000
		int R2C2 = 32; // 0000 0000 0010 0000
		int R2C3 = 64; // 0000 0000 0100 0000
		int R2C4 = 128; // 0000 0000 1000 0000
		
		int R3C1 = 256; // 0000 0001 0000 0000
		int R3C2 = 512; // 0000 0010 0000 0000
		int R3C3 = 1024; // 0000 0100 0000 0000
		int R3C4 = 2048; // 0000 1000 0000 0000
		
		int R4C1 = 4096; // 0000 0001 0000 0000
		int R4C2 = 8192; // 0000 0010 0000 0000
		int R4C3 = 16384; // 0000 0100 0000 0000
		int R4C4 = 32768; // 0000 1000 0000 0000
		
		int [][]aux = new int[][]{{R1C1, R1C2, R1C3, R1C4},{R2C1, R2C2, R2C3, R2C4},{R3C1, R3C2, R3C3, R3C4},{R4C1, R4C2, R4C3, R4C4}};
		
		this.cartasSeleccionadasJugador2 = this.cartasSeleccionadasJugador2 | aux[row][col];
	}
	
	public void printState() {
		System.out.println("/****************************************************************/");
		this.boardModel.printState();
		int resul = this.cartasSeleccionadasJugador2;
		System.out.println(
				"ULT CARTA SELECCIONADA JUGADOR 2: " + 
				String.format("%16s", Integer.toBinaryString(resul)).replace(' ', '0')
				);
		System.out.println("Puntos J1: "+this.puntosJ1);
		System.out.println("Puntos J2: "+this.puntosJ2);
		System.out.println("/****************************************************************/");
	}

	@Override
	public byte newGame() {
		return 0;
	}

	@Override
	public boolean matchCardsP1(byte r1, byte c1, byte r2, byte c2) {
		// TODO Auto-generated method stub
		return false;
	}
}
