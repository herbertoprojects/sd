package ivote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class getOptions {
	public int portServerTCP = 0;
	public int portRmiServer = 0;
	public int portRmiServer_2 = 0;
	public String ipServerTCP = "";
	public String ipRmiServer = "";
	public String ipRmiServer_2 = "";
	public int hearthBitPort = 0;
	
	getOptions(){
		try {
			BufferedReader lerOptions = new BufferedReader(new FileReader("options"));
			String texto = "";
			while((texto = lerOptions.readLine())!=null) {
				String [] textos = texto.split(":");
				if(textos[0].equalsIgnoreCase("ipServerTcp")) {ipServerTCP=textos[1];}
				else{if(textos[0].equalsIgnoreCase("portServerTcp")) {portServerTCP=Integer.parseInt(textos[1]);}
				else{if(textos[0].equalsIgnoreCase("ipRmiServer")) {ipRmiServer=textos[1];}
				else{if(textos[0].equalsIgnoreCase("portRmiServer")) {portRmiServer=Integer.parseInt(textos[1]);}
				else{if(textos[0].equalsIgnoreCase("portRmiServer_2")) {portRmiServer_2=Integer.parseInt(textos[1]);}
				else{if(textos[0].equalsIgnoreCase("ipRmiServer_2")) {ipRmiServer_2=textos[1];}
				else{if(textos[0].equalsIgnoreCase("hearthBitPort")) {hearthBitPort=Integer.parseInt(textos[1]);}
				else {System.out.println("Options: Opcoes sem correspondencia");}}}}}}}
			}
			lerOptions.close();
		}catch(IOException e) {
			System.out.println("Sem ficheiro de opcoes");
		}
		
		if(!ipServerTCP.isEmpty()) {if(portServerTCP!=0) {System.out.println("Servidor TCP : "+ipServerTCP+":"+portServerTCP);}else{System.out.println("Sem dados IP Server TCP");}}
		else{System.out.println("Sem dados IP Server TCP");}
		if(!ipRmiServer.isEmpty()) {if(portRmiServer!=0) {System.out.println("Servidor RMI : "+ipRmiServer+":"+portRmiServer);}else {System.out.println("Sem dados IP Server TCP");}}
		else {System.out.println("Sem dados IP Server TCP");}
		if(!ipRmiServer_2.isEmpty()) {if(portRmiServer_2!=0) {System.out.println("Servidor RMI : "+ipRmiServer_2+":"+portRmiServer_2);}else {System.out.println("Sem dados IP Server TCP");}}
		else {System.out.println("Sem dados IP Server TCP");}

	}
}
