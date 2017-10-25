package ivote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class threadClienteTCP extends Thread {
	
	public int tentativas = 4;
	
	public ArrayList <terminalVoto> listaDeTerminais;
	public terminalVoto terminalAssociado = null;
	public Socket cliente;
	
	public BufferedReader readMesa;
	public PrintWriter writeMesa;
	
	public threadClienteTCP(ArrayList <terminalVoto> listaDeTerminais, Socket cliente) {
		// TODO Auto-generated constructor stub
		this.listaDeTerminais = listaDeTerminais;
		this.cliente = cliente;
		this.start();
	}
	@Override
	public void run() {
		try {
			
			System.out.println("Comunicacao Aceite!!!");
			readMesa = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			writeMesa = new PrintWriter(cliente.getOutputStream(), true);
			
			writeMesa.println("message|Servidor Ivoto v3.2");
			writeMesa.println("message|Bem-vindo");
			
			while(true) {
				String texto = readMesa.readLine();
				writeMesa.println(executaComando(texto));
				if(tentativas == 0) {
					cliente.close();
					return;
				}
				if(terminalAssociado == null) {
					if(tentativas!=1) {
						writeMesa.println("Tem mais "+tentativas+" tentativas.");
					}else {
						writeMesa.println("Ultima tentativa");
					}
				}else {
					if(!terminalAssociado.vote) {
						synchronized (this) {
							try {
								wait();
								terminalAssociado.vote = true;
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						writeMesa.println("type|message;text|Utilizador: "+terminalAssociado.nccCLiente);
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String executaComando(String texto) {
		
		ArrayList <String []> textos = new ArrayList<String []>();
		String tipo;
		
		if (texto==null || texto.contains("|")==false) {
			if (terminalAssociado== null) {tentativas --;}
			return "type|message;text|Comando invalido";
		}
		for(String s:texto.split(";")) {
			String [] temp = s.split("\\|");
			textos.add(temp);
		}
		
		try {
			tipo = textos.get(0)[1];
			}
		catch(Exception e) {
			if (terminalAssociado== null) {tentativas --;}
			return "type|message;text|Comando invalido";
		}
		
		
		if(tipo.equalsIgnoreCase("login")) {
			return loginMesaVoto(textos);
		}
		
		if(tipo.equalsIgnoreCase("authenticate")) {
			if (terminalAssociado== null) {
				tentativas --;
				return "type|message;text|Primeiro fazer login";
			}
			return authenticateUser(textos);
		}
		if(tipo.equalsIgnoreCase("sendvote")) {
			if (terminalAssociado== null) {
				tentativas --;
				return "type|message;text|Primeiro fazer login";
			}
			return sendVote(textos);	
		}
		if(tipo.equalsIgnoreCase("help")) {
			if (terminalAssociado== null) {tentativas --;}
			return help(textos);
		}
		if (terminalAssociado== null) {tentativas --;}
		return "type|message;text|Comando invalido";
	}
	
	private String loginMesaVoto(ArrayList <String []> textos) {
		if(textos.size()==3) {
			
			for (terminalVoto temp:listaDeTerminais) {
				if(temp.nome.equalsIgnoreCase(textos.get(1)[1])) {
					if(temp.password.equals(textos.get(2)[1])) {
						if(temp.login) {
							if (terminalAssociado== null) {tentativas --;}
							return "type|message;text|Utilizador ja ligado";
						}
						terminalAssociado =temp;
						terminalAssociado.login = true;
						terminalAssociado.threadAssociada = this;
						return "type|message;text|Terminal ligado";
					}
				}
			}
			if (terminalAssociado== null) {tentativas --;}
			return "type|message;text|Utilizador ou password erradas";
		}
		if (terminalAssociado== null) {tentativas --;}
		return "type|message;text|Comando errado";
	}
	private String authenticateUser(ArrayList <String []> textos) {
		if(textos.size()==2) {
			try {
				terminalAssociado.passCliente = textos.get(1)[1];
				if(terminalAssociado.comunicacao.desbloquearVoto(terminalAssociado.nome, terminalAssociado.password,terminalAssociado.nccCLiente, terminalAssociado.passCliente)) {
					terminalAssociado.vote = true;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "type|message;text|Utilizador autenticado";
		}
		return "type|message;text|Comando errado";
	}
	private String sendVote(ArrayList <String []> textos) {
		if(textos.size()==2) {
			if(terminalAssociado.vote) {
				int numTemp;
				try {
					numTemp = Integer.parseInt(textos.get(1)[1]);
					if(terminalAssociado.comunicacao.votar(terminalAssociado.nome, terminalAssociado.password, terminalAssociado.nccCLiente, terminalAssociado.passCliente,numTemp)) {
						terminalAssociado.nccCLiente = 0;
						terminalAssociado.passCliente = "";
						terminalAssociado.vote = false;
						terminalAssociado.autentication = false;
						return "type|message;text|Voto enviado com sucesso";
					}
				}catch(NumberFormatException e) {
					return "type|message;text|Comando errado. O voto é um numero.";
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "type|message;text|Erro na execução do comando";
			}
		}
		return "type|message;text|Comando errado";
	}
	private String help(ArrayList <String []> textos) {
		if(textos.size()==1) {
			return "informaçao validada";
		}
		return "help";
	}
}
