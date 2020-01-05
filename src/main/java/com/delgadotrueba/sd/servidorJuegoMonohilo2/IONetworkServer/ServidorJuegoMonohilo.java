package com.delgadotrueba.sd.servidorJuegoMonohilo2.IONetworkServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.delgadotrueba.sd.servidorJuegoMonohilo2.SkEmparejados;
import com.delgadotrueba.sd.servidorJuegoMonohilo2.TareaSkEmparejados;
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
	
	private SkEmparejados sk = new SkEmparejados();
	
	public void init () {
		ServerSocket server = null;
		
		 try {
			 server = new ServerSocket(10000);
			 System.out.println("Servidor Levantado");
			 
			 while (true) {
				 Socket socketcliente = server.accept(); //accept new connection
				 System.out.println("Cliente Conectado");
				 
				 IONetworkServer serverIO = new IONetworkServer(socketcliente);
				 byte oid = serverIO.recibir_oid();
				 System.out.println("oid: " + oid);
				 
				 this.sk.setOID(oid);
				 
				 TareaSkEmparejados tarea = new TareaSkEmparejados(sk, socketcliente);
				 Thread thread = new Thread(tarea);
				 thread.start();
				 
				 System.out.println("Thread Main: Cliente Desconectado");
			 }
		 }catch (IOException e) {
			 e.printStackTrace();
		 } catch (InvalidCodopException e) {
			e.printStackTrace();
		}
	}
	
}
