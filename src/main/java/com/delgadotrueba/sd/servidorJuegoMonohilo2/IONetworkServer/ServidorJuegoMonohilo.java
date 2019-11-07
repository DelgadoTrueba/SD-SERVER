package com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Input;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Input_MatchCard;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_GetMatchedCards;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_GetPointsP1;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_GetPointsP2;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_GetTypeCards;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_IsSolved;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_MatchCard;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_NewGame;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.DTO.DTO_Output_SelectedCardsP2;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.MaxNumberGamesCreatedException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.GameNotCreatedException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidCodopException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidDataInterfaceException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.models.PartidaModel;

public class ServidorJuegoMonohilo {
	
	public static final boolean ERROR = true;
	public static final boolean NO_ERROR = false;
	
	private static final byte CODOP_1 = 1;
	private static final byte CODOP_2 = 2;
	private static final byte CODOP_3 = 3;
	private static final byte CODOP_4 = 4;
	private static final byte CODOP_5 = 5;
	private static final byte CODOP_6 = 6;
	private static final byte CODOP_7 = 7;
	private static final byte CODOP_8 = 8;
	
	private PartidaModel partida = null;
	
	public void init () {
		ServerSocket server = null;
		
		 try {
			 server = new ServerSocket(10000);
			 System.out.println("Servidor Levantado");
			 
			 while (true) {
				 Socket socket = server.accept(); //accept new connection
				 System.out.println("Cliente Conectado");
				 this.manejadorSocket(socket);
				 System.out.println("Cliente Desconectado");
			 }
		 }catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	private void manejadorSocket(Socket socketcliente) throws IOException{
		IONetworkServer serverIO = new IONetworkServer(socketcliente);
		try {
			byte codop = serverIO.recibir_codop();
			System.out.println("CODOP: " + codop);
			
			switch (codop) {
				case CODOP_1:
					this.iniciarPartida(serverIO);
					break;
				case CODOP_2:
					this.obtenerTiposCartas(serverIO);
					break;
				case CODOP_3:
					this.obtenerCartasEmparejadas(serverIO);
					break;
				case CODOP_4:
					this.obtenerPuntosJ1(serverIO);
					break;
				case CODOP_5:
					this.obtenerPuntosJ2(serverIO);
					break;
				case CODOP_6:
					this.emparejarCartas(serverIO);
					break;
				case CODOP_7:
					this.estaResuelto(serverIO);
					break;
				case CODOP_8:
					this.obtenerCartasSeleccionasJ2(serverIO);
					break;
			}
			partida.printState();
		}catch (InvalidCodopException e) {
			System.err.println("ERROR AL LEER EL CODOP. => CODOP NO AJUSTADO A LA INTERFAZ");
			
			DTO_Output dtoOuput= new DTO_Output(ERROR, e);
			serverIO.enviarRespuesta(dtoOuput);
		}catch (InvalidDataInterfaceException e) {
			System.err.println("ERROR AL RECIBIR DATOS DEL CLIENTE. => DATOS NO AJUSTADOS A LA INTERFAZ");
			
			DTO_Output dtoOuput= new DTO_Output(ERROR, e);
			serverIO.enviarRespuesta(dtoOuput);
		}catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR AL ENVIAR DATOS AL CLIENTE");
		}
	}
		
	private void iniciarPartida(IONetworkServer serverIO) throws IOException {
		
		if(this.partida != null && !this.partida.estaResulto() ) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new MaxNumberGamesCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		this.partida = new PartidaModel();
		DTO_Output_NewGame dtoOuputNewGame = new DTO_Output_NewGame(NO_ERROR, null);
		serverIO.enviarRespuesta(dtoOuputNewGame);
	}
	
	private void obtenerTiposCartas(IONetworkServer serverIO) throws IOException {
	
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte[][]tipos = this.partida.getTiposCartas();
		DTO_Output_GetTypeCards dtoOuputGetTypeCards = new DTO_Output_GetTypeCards(NO_ERROR, null, tipos);
		serverIO.enviarRespuesta(dtoOuputGetTypeCards);
	}
	
	private void obtenerCartasEmparejadas(IONetworkServer serverIO) throws IOException {
		
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		int resul = this.partida.getCartasEmparejadas();
		DTO_Output_GetMatchedCards dtoOuputGetMatchedCards = new DTO_Output_GetMatchedCards(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuputGetMatchedCards);
	}
	
	private void obtenerPuntosJ1(IONetworkServer serverIO) throws IOException {
		if(this.partida == null) {
			DTO_Output dtoOuput= new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte resul = this.partida.getPuntosJ1();
		DTO_Output_GetPointsP1 dtoOuput= new DTO_Output_GetPointsP1(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);	
	}
	
	private void obtenerPuntosJ2(IONetworkServer serverIO) throws IOException {
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte resul = this.partida.getPuntosJ2();
		DTO_Output_GetPointsP2 dtoOuput= new DTO_Output_GetPointsP2(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);	
	}
	
	private void emparejarCartas(IONetworkServer serverIO) throws IOException, InvalidDataInterfaceException {
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}

		DTO_Input_MatchCard dtoInput = new DTO_Input_MatchCard(CODOP_6, serverIO.getDataIn());
		int r1 = dtoInput.getR1();
		int c1 = dtoInput.getC1();
		
		int r2 = dtoInput.getR2();
		int c2 = dtoInput.getC2();
		System.out.println("JUGADOR1: "+r1+c1+r2+c2);
		boolean resul = this.partida.emparejarCartas(r1, c1, r2, c2);
		
		DTO_Output_MatchCard dtoOuput= new DTO_Output_MatchCard(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
	private void estaResuelto(IONetworkServer serverIO) throws IOException {
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		boolean resul = this.partida.estaResulto();
		DTO_Output_IsSolved dtoOuput= new DTO_Output_IsSolved(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
	private void obtenerCartasSeleccionasJ2(IONetworkServer serverIO) throws IOException {
		if(this.partida == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		int resul = this.partida.getCartasSeleccionadasJugador2();
		DTO_Output_SelectedCardsP2 dtoOuput= new DTO_Output_SelectedCardsP2(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
	
}
