package ivote;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class userInterface extends Thread {
	
	public RMIRemoteInterface comunicacaoRMI;
	public String username = "";
	public String password = "";
	public LocalDateTime data_ligado;
	public ArrayList <terminalVoto> listaDeTerminais = new ArrayList<terminalVoto>();//sincronizado
	
	public getScanner leitorTeclado = new getScanner();
	
	
	public userInterface(RMIRemoteInterface comunicacaoRMI) {
		// TODO Auto-generated constructor stub
		this.comunicacaoRMI = comunicacaoRMI;
		this.start();
	}
	
	public void run() {
		this.menuDesligado();
		
	}
	
	public void menuDesligado() {
		int opc=0;
		while (true) {
			System.out.println();
			System.out.println("1) Estado do Servidor");
			System.out.println("2) Ligar a eleicao");
			System.out.println("0) Sair");
			opc = leitorTeclado.pedeNumero("opc: ",0,2);
			
			switch (opc) {
				case 1:
					System.out.println("Servidor desligado...");
					break;
				case 2:
					if(ligaEleicao()) {
						data_ligado = LocalDateTime.now();
						this.menuLigado();
					}
					break;
				case 0:
					return;
			}
				
					
		}
	}
	
	public void menuLigado() {
		int opc=0;
		while (true) {
			System.out.println();
			System.out.println("1) Estado do Servidor");
			System.out.println("2) Adicionar terminal de voto");
			System.out.println("3) Remover terminal de voto");
			System.out.println("4) Lista de terminais de voto");
			System.out.println("5) Autenticar utilizador");
			System.out.println("0) Sair");
			opc = leitorTeclado.pedeNumero("opc: ",0,5);
			
			switch (opc) {
				case 1:
					System.out.println();
					System.out.println("Servidor: "+username);
					System.out.println("Tempo ligado: "+(LocalDateTime.now().getHour()-data_ligado.getHour())+":"+
										(LocalDateTime.now().getMinute()-data_ligado.getMinute())+":"+
										(LocalDateTime.now().getSecond()-data_ligado.getSecond())+
										" "
										+ "(HH:MM:SS)");
					leitorTeclado.leLinha("Continuar...");
					break;
				case 2:
					System.out.println("Criar novo terminal de voto");
					String userTemp;
					
					userTemp = leitorTeclado.leLinha("User do terminal: ");
					if(verificaUser(userTemp)) {
						do {
							System.out.println("Utilizador já em uso...");
							userTemp = leitorTeclado.leLinha("User do terminal: ");
						}while(verificaUser(userTemp));
					}
					
					String passTemp = leitorTeclado.leLinha("Password do terminal: ");
					System.out.println("1) Confirmar");
					System.out.println("0) Cancelar");
					if(leitorTeclado.pedeNumero("Opção: ",0, 1)==1) {
						terminalVoto tempTerminal = new terminalVoto(comunicacaoRMI, userTemp, passTemp);
						listaDeTerminais.add(tempTerminal);
						leitorTeclado.leLinha("terminal adicionado...");
						
					}else {
						leitorTeclado.leLinha("continuar...");
					}
					
					synchronized (this) {
						notify();
					}
					break;
				case 3:
					imprimeTerminalVoto();
					System.out.println("0) cancelar");
					int num = leitorTeclado.pedeNumero("Opção: ", 0, listaDeTerminais.size());
					if(num!=0) {
						if(listaDeTerminais.get(num).login) {
							System.out.println("Terminal ligado.");
							System.out.println("Por favor desligue...");
							leitorTeclado.leLinha("Continuar....");
						}
						else {
							listaDeTerminais.remove(num-1);
						}
					}
					break;
				case 4:
					imprimeTerminalVoto();
					leitorTeclado.leLinha("Continuar...");
					break;
				case 5:
					int numOpc;
					do {
						System.out.println("1) Novo Utilizador");
						System.out.println("0) Sair");
						numOpc = leitorTeclado.pedeNumero("Opção: ", 0, 1);
						if(numOpc == 0) {
							break;
						}
						if(procuraTerminalVazio()==null) {
							System.out.println("Sem terminais livres...");
							leitorTeclado.leLinha("Aguarde...");
						}
						else {
							try {
								int numCC = leitorTeclado.pedeNumero("Numero do Cartão de Cidadão: ", 9999999, 100000000);
								System.out.println("1) Confirmar");
								System.out.println("0) Cancelar");
								if(leitorTeclado.pedeNumero("Opção: ", 0, 1)==1) {
									if(comunicacaoRMI.desbloquearUser(username, password,numCC )){
										terminalVoto temp = procuraTerminalVazio();
										temp.nccCLiente = numCC;
										synchronized(temp.threadAssociada) {
											temp.autentication = true;
											temp.threadAssociada.notify();
											
										}
										
									}else{
										System.out.println("Utilizador invalido.");
									}
								}
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							leitorTeclado.leLinha("Continuar...");
						}
						
					}while(true);
					break;
				case 0:
					username = "";
					password = "";
					return;
			}
				
					
		}
	}
	
	public boolean ligaEleicao() {
		username = leitorTeclado.leLinha("Username: ");
		password = leitorTeclado.leLinha("Password: ");
		
		try {
			return comunicacaoRMI.ligarServidor(username, password);
		} catch (RemoteException|NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("Sem possiblidade de ligar ao servidor rmi...");
			return false;
			//e.printStackTrace();
		}
		
	}
	
	public boolean verificaUser(String user) {
		if(listaDeTerminais.size()>0) {
			for(terminalVoto temp:listaDeTerminais) {
				if(temp.nome.equalsIgnoreCase(user)) {
					return true;
				}
			}
			return false;
		}else {
			return false;
		}
	}
	
	public void imprimeTerminalVoto() {
		int num = 1;
		for(terminalVoto temp:listaDeTerminais) {
			System.out.println(num+") "+temp.nome+":"+(temp.login?"Ligado":"Desligado")+(temp.autentication?"-Ocupado":"-Livre"));
			num++;
		}
	}
	
	public terminalVoto procuraTerminalVazio() {
		
		for(terminalVoto temp:listaDeTerminais) {
			if(temp.login && !temp.autentication) {
				return temp;
				
			}
		}
		return null;
	}

}
