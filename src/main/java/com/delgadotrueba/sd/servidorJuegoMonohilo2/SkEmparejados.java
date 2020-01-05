package com.delgadotrueba.sd.servidorJuegoMonohilo2;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;

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
import com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer.IONetworkServer;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.GameNotCreatedException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidCodopException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.InvalidDataInterfaceException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.exceptions.MaxNumberGamesCreatedException;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.models.PartidaModel;

public class SkEmparejados {

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
	
	private Hashtable<Integer, PartidaModel> objectHash = new Hashtable<Integer, PartidaModel>();
	private byte partidaId = 0;
	private PartidaModel partidaActual;
	
	public void setOID(byte oid) {
		this.partidaActual = this.objectHash.get((int)oid);
	}
	
	public void process(Socket socketcliente){
		try {
			IONetworkServer serverIO = new IONetworkServer(socketcliente);
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
			if(partidaActual != null) partidaActual.printState();
		}catch (InvalidCodopException e) {
			System.err.println("ERROR AL LEER EL CODOP. => CODOP NO AJUSTADO A LA INTERFAZ");
			
			DTO_Output dtoOuput= new DTO_Output(ERROR, e);
			//serverIO.enviarRespuesta(dtoOuput);
		}catch (InvalidDataInterfaceException e) {
			System.err.println("ERROR AL RECIBIR DATOS DEL CLIENTE. => DATOS NO AJUSTADOS A LA INTERFAZ");
			
			DTO_Output dtoOuput= new DTO_Output(ERROR, e);
			//serverIO.enviarRespuesta(dtoOuput);
		}catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR AL ENVIAR DATOS AL CLIENTE");
		}
	}
	
	private void iniciarPartida(IONetworkServer serverIO) throws IOException {
		
		if(this.partidaActual != null && !this.partidaActual.isSolved() ) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new MaxNumberGamesCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		PartidaModel partida = new PartidaModel();
		
		objectHash.put((int)partidaId, partida);	
		DTO_Output_NewGame dtoOuputNewGame = new DTO_Output_NewGame(NO_ERROR, null, partidaId);
		System.out.println("oid: "+partidaId);
		partidaId++;
		serverIO.enviarRespuesta(dtoOuputNewGame);
	}
	
	private void obtenerTiposCartas(IONetworkServer serverIO) throws IOException {
	
		if(this.partidaActual == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte[][]tipos = this.partidaActual.obtenerTiposCartas();
		DTO_Output_GetTypeCards dtoOuputGetTypeCards = new DTO_Output_GetTypeCards(NO_ERROR, null, tipos);
		serverIO.enviarRespuesta(dtoOuputGetTypeCards);
	}
	
	private void obtenerCartasEmparejadas(IONetworkServer serverIO) throws IOException {
		
		if(this.partidaActual == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		int resul = this.partidaActual.obtenerCartasEmparejadas();
		DTO_Output_GetMatchedCards dtoOuputGetMatchedCards = new DTO_Output_GetMatchedCards(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuputGetMatchedCards);
	}
	
	private void obtenerPuntosJ1(IONetworkServer serverIO) throws IOException {
		if(this.partidaActual == null) {
			DTO_Output dtoOuput= new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte resul = this.partidaActual.obtenerPuntosJ1();
		DTO_Output_GetPointsP1 dtoOuput= new DTO_Output_GetPointsP1(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);	
	}
	
	private void obtenerPuntosJ2(IONetworkServer serverIO) throws IOException {
		if(this.partidaActual == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		byte resul = this.partidaActual.obtenerPuntosJ2();
		DTO_Output_GetPointsP2 dtoOuput= new DTO_Output_GetPointsP2(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);	
	}
	
	private void emparejarCartas(IONetworkServer serverIO) throws IOException, InvalidDataInterfaceException {
		if(this.partidaActual == null) {
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
		boolean resul = this.partidaActual.matchCardsP1(r1, c1, r2, c2);
		
		DTO_Output_MatchCard dtoOuput= new DTO_Output_MatchCard(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
	private void estaResuelto(IONetworkServer serverIO) throws IOException {
		if(this.partidaActual == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		boolean resul = this.partidaActual.isSolved();
		DTO_Output_IsSolved dtoOuput= new DTO_Output_IsSolved(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
	private void obtenerCartasSeleccionasJ2(IONetworkServer serverIO) throws IOException {
		if(this.partidaActual == null) {
			DTO_Output dtoOuput = new DTO_Output(ERROR, new GameNotCreatedException());
			serverIO.enviarRespuesta(dtoOuput);
			return;
		}
		
		int resul = this.partidaActual.obtenerCartasSeleccionadasJ2();
		DTO_Output_SelectedCardsP2 dtoOuput= new DTO_Output_SelectedCardsP2(NO_ERROR, null, resul);
		serverIO.enviarRespuesta(dtoOuput);
	}
	
}
