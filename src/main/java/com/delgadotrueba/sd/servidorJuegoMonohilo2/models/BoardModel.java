package com.delgadotrueba.sd.servidorJuegoMonohilo2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class BoardModel{
			
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int MAX_SELECTED_CARDS = 2;
	
	// SIEMPRE DEBE DE DAR UNA MATRIZ PAR
	private int NUMBER_OF_ROWS;
	private int NUMBER_OF_COLUMNS;
	
	// GENERAR CELL TYPE => IMAGEN CORRESPONDIENTE
	private int MIN_NUM_OF_CARDS;
	private int MAX_NUM_OF_CARDS;
	private int NUMBER_OF_PAIRS;
	
	public CellModel[][] mBoard = null;
	public byte[][] typesCard = null;
	
	public BoardModel(int rows, int cols) {
		
		NUMBER_OF_ROWS = rows;
		NUMBER_OF_COLUMNS = cols;
		
		MIN_NUM_OF_CARDS = 1;
		MAX_NUM_OF_CARDS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;
		NUMBER_OF_PAIRS = MAX_NUM_OF_CARDS / 2;
		 
		mBoard = new CellModel[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		typesCard = this.initCardStorage();
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   byte type = typesCard[row][column];
				   mBoard[row][column] = new CellModel(type);
			   }
		}
	}
	
	public BoardModel(int rows, int cols, byte[][]tipos) {
		
		NUMBER_OF_ROWS = rows;
		NUMBER_OF_COLUMNS = cols;
		
		MIN_NUM_OF_CARDS = 1;
		MAX_NUM_OF_CARDS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;
		NUMBER_OF_PAIRS = MAX_NUM_OF_CARDS / 2;
		 
		mBoard = new CellModel[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		typesCard = tipos;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   byte type = tipos[row][column];
				   mBoard[row][column] = new CellModel(type);
			   }
		}
	}
			
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards ) {
		//JButton[][] btnBoard;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface	 
	////////////////////////////////////////////////////////////////////////////
	
	/** This method initializes the board with a new set of cards*/
	public void initializeNewBoard() {
		
	}
		
	public boolean isCardValid(int row, int col) {
		// ES UNA POSICIÓN VALIDA DE LA MATRIZ
		if ( !coordenatesValid(row, col) ) {
			ErrorHandler.error("BoardModel Model", "isCardValid(Cell) received null", false);
			return false;
		}
		
  		// NO ESTA SELECCIONADA NI EMPAREJADA
		CellModel aCard = this.mBoard[row][col];
		if ( aCard.isMatched() || aCard.isSelected() ) {
			return false;
		}
		return true;
	}
	
	public boolean areSelectedCardsSameType() {
		CellModel[] cellModelArray = new CellModel[MAX_SELECTED_CARDS];
		int pos = 0;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (mBoard[row][column].isSelected()) {
					cellModelArray[pos] = this.mBoard[row][column];
					pos++;
				}
			}
		}
		
		CellModel fisrtCard = cellModelArray[FIRST];
		CellModel secondCard = cellModelArray[SECOND];

		if ( !fisrtCard.sameType(secondCard) ) {
			return false;
		}
		return true;
	}
	
	public boolean isPlayable() {
		int numOfSelectedCards = 0;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (mBoard[row][column].isSelected()) {
					numOfSelectedCards++;
				}
			}
		}
		return numOfSelectedCards == MAX_SELECTED_CARDS;
	}
	
	public boolean isSolved() {	
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (!mBoard[row][column].isMatched()) {
					return false;
				}
			}
		}
		return true;	
	}
	
	public void setCardSelected(int row, int col){
		CellModel cellModel = this.mBoard[row][col];
		cellModel.setMatched(false);
		cellModel.setSelected(true);
	}
	
	public int[][] getSelectedCards() {
		int[][] posiciones = new int[2][2];
		int iteracion = 0;
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isSelected()) {
					
					posiciones[iteracion][0] = row;
					posiciones[iteracion][1] = column;

					iteracion = iteracion + 1;					
				}
			}
		}
		
		return posiciones;
	}
	
	public int[][] setSelectedCardsHidden() {
		int[][] posiciones = new int[2][2];
		int iteracion = 0; /** it = 0, carta 1, it=1, carta= 2**/
		
		posiciones[0][0] = -1;
		posiciones[0][1] = -1;
		posiciones[1][0] = -1;
		posiciones[1][1] = -1;
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isSelected()) {
					CellModel cellModel = this.mBoard[row][column];
					cellModel.setMatched(false);
					cellModel.setSelected(false);
					
					posiciones[iteracion][0] = row;
					posiciones[iteracion][1] = column;

					iteracion = iteracion + 1;					
				}
			}
		}
		
		return posiciones;
	}
	
	public int[][] setSelectedCardsMatched() {
		
		int[][] posiciones = new int[2][2];
		int iteracion = 0; /** it = 0, carta 1, it=1, carta= 2**/
		
		posiciones[0][0] = -1;
		posiciones[0][1] = -1;
		posiciones[1][0] = -1;
		posiciones[1][1] = -1;
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isSelected()) {
					CellModel cellModel = this.mBoard[row][column];
					cellModel.setMatched(true);
					cellModel.setSelected(false);
					
					posiciones[iteracion][0] = row;
					posiciones[iteracion][1] = column;

					iteracion = iteracion + 1;	
				}
			}
		}
		return posiciones;
	}
		
	public int getMatchedCard() {
		int R1C1 = 0b0000000000000001;
		int R1C2 = 0b0000000000000010;
		int R1C3 = 0b0000000000000100;
		int R1C4 = 0b0000000000001000;
		
		int R2C1 = 0b0000000000010000;
		int R2C2 = 0b0000000000100000;
		int R2C3 = 0b0000000001000000;
		int R2C4 = 0b0000000010000000;
		
		int R3C1 = 0b0000000100000000;
		int R3C2 = 0b0000001000000000;
		int R3C3 = 0b0000010000000000;
		int R3C4 = 0b0000100000000000;
		
		int R4C1 = 0b0001000000000000;
		int R4C2 = 0b0010000000000000;
		int R4C3 = 0b0100000000000000;
		int R4C4 = 0b1000000000000000;
		
		int [][]aux = new int[][]{{R1C1, R1C2, R1C3, R1C4},{R2C1, R2C2, R2C3, R2C4},{R3C1, R3C2, R3C3, R3C4},{R4C1, R4C2, R4C3, R4C4}};

		int emparejadas = 0;
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isMatched()) {
					emparejadas = emparejadas | aux[row][column];
				}
			}
		}
		
		return emparejadas;
	}
	
	public byte[][] getTypeCards(){
		return this.typesCard;
	}
	
	public void printState() {
		System.out.println("\nTIPOS: ");
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				System.out.print(" "+ this.getTypeCards()[row][column] +" ");
			}
			System.out.println();
		}
		
		System.out.println("\nPAREJAS: ");
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(this.mBoard[row][column].isMatched()) {
					System.out.print(" M ");
				}
				if(!this.mBoard[row][column].isMatched() && !this.mBoard[row][column].isSelected()) {
					System.out.print(" H ");
				}
			}
			System.out.println();
		}
		
		System.out.println("\nESTA RESUELTO: " + this.isSolved());
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Private Interface	 
	////////////////////////////////////////////////////////////////////////////
	
	private byte[][] initCardStorage() {
		
		byte[] cardStorage = new byte[MAX_NUM_OF_CARDS];
		byte[] firstPair = new byte[NUMBER_OF_PAIRS];
		byte[] secondPair = new byte[NUMBER_OF_PAIRS];
	
		firstPair = randomListWithoutRep();
	
		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
			cardStorage[i] = firstPair[i];
		}
	
		Random rand = new Random();
		/*shuffle*/
		for (int i = 0; i < firstPair.length; i++) {
			int randomIndexToSwap = rand.nextInt(firstPair.length);
			byte temp = firstPair[randomIndexToSwap];
			firstPair[randomIndexToSwap] = firstPair[i];
			firstPair[i] = temp;
		}
		/*shuffle*/

		for (int j = 0; j < NUMBER_OF_PAIRS; j++) {
			secondPair[j] = firstPair[j];
		}
	
		for (int k = NUMBER_OF_PAIRS; k < MAX_NUM_OF_CARDS; k++) {
			cardStorage[k] = secondPair[k - NUMBER_OF_PAIRS];
		}
		
		byte[][] typeCell = new byte[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
				
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				typeCell[row][column] = cardStorage[column + (NUMBER_OF_COLUMNS * row)];
			}
		}
	
		return typeCell;
		//return new int[][]{{1, 1, 2, 2, 3, 3},{4, 4, 5, 5, 6, 6},{ 7, 7, 8, 8, 9, 9},{10, 10, 11, 11,12, 12}};
	}
	
	private byte[] randomListWithoutRep() {

		byte[] generatedArray = new byte[NUMBER_OF_PAIRS];
		ArrayList<Byte> generated = new ArrayList<Byte>();
	
		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
			while (true) {
				byte next = generateRandomImageType(MAX_NUM_OF_CARDS, MIN_NUM_OF_CARDS);
			
				if (!generated.contains(next)) {
					generated.add(next);
					generatedArray[i] = generated.get(i);
					break; // breaks back to "for" loop
				}
			} // inner loop - for every random card, ensure its not already
			// existing
		} // outer loop - we want NUMBER_OF_PAIRS different pairs
	
		return generatedArray;
	}

	private byte generateRandomImageType(int max, int min) {

		Random random = new Random();
		byte aNumber = (byte)(min + random.nextInt(max));
	
		return aNumber;
	}
		
	private boolean coordenatesValid(int row, int col) {
		if(row < 0 || row >= NUMBER_OF_ROWS || col < 0 || col >= NUMBER_OF_COLUMNS) {
			return false;
		}
		else{
			return true;
		}
	}
	
	
}