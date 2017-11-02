package ivote;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

public class getScanner {
	
	public getScanner(){
		
	}
	
	public int pedeNumero(String texto,int minNum,int maxNum) {
			Scanner sc = new Scanner(System.in);
			String s;
			int num = 0;
			boolean teste = true;
			
			do {
				System.out.print(texto);
				s = sc.nextLine();
				try {
					num = Integer.parseInt(s);
					teste = false;
					if(num<minNum) {teste = true;System.out.println("O numero tera de ser maior que "+minNum);}
					if(num>maxNum) {teste = true;System.out.println("O numero tera de ser menor que "+maxNum);}
				}catch(NumberFormatException e) {
					teste = true;
					System.out.println("Formato invalido. Por favor coloque um numero.");
				}
				
			}while(teste);
			return num;
	}
		
	
	public String leLinha(String texto) {
		Scanner sc = new Scanner(System.in);
		System.out.print(texto);
		String textoR = sc.nextLine();
		return textoR;
	}
	
	public String pedeData(String text) {
		Scanner sc = new Scanner(System.in);
		System.out.println(text);
		int anoTemp = pedeNumero("Ano: ", 1900,1900 + (new Date(System.currentTimeMillis()).getYear()));
		int mesTemp = pedeNumero("Mês: ", 1, 12);		

		String data = formatNum(mesTemp, 2)+"/"+formatNum(anoTemp, 4);
		return data;
		
	}
	
	public int pedeDia(String texto, int mes) {
		int diaTemp;
		while(true) {
			diaTemp = pedeNumero(texto, 1, 31);
			switch (mes){
			case 2:
				if(diaTemp>29) {System.out.println("Dia invalido");}else {return diaTemp;}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if(diaTemp>30) {System.out.println("Dia invalido");}else {return diaTemp;}
				break;
			default:
				return diaTemp;
				
			}
		}
	}
	
	public String pedeDataHora(String texto) {
		System.out.println(texto);
		
		int anoTemp = pedeNumero("Ano: ", 1900,2200);
		int mesTemp = pedeNumero("Mês: ", 1, 12);
		int diaTemp = pedeDia("Dia: ",mesTemp);
		
		int horaTem = pedeNumero("Horas: ",0,23);
		int minTemp = pedeNumero("Minutos: ",0,59);
		
		return (formatNum(anoTemp, 4)+"/"+
				formatNum(mesTemp, 2)+"/"+
				formatNum(diaTemp, 2)+" "+
				formatNum(horaTem, 2)+":"+
				formatNum(minTemp, 2));
	}
	
	public String formatNum (int num,int casas) {
		String texto = ""+num;
		while(texto.length()<casas) {
			texto = "0"+texto;
		}
		return texto;
	}
	
	public String mudaString(String texto) {
		System.out.println("Anterior: "+ texto);
		String texto1 = leLinha("Novo: ");
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(pedeNumero("Opção: ",0, 1)==1) {
			return texto1;
		}
		else {
			return texto;
		}
	}
	
	public int mudaInt(int num,int valor_inicial,int valor_final) {
		System.out.println("Anterior: "+ num);
		int num1 = pedeNumero("Novo: ",valor_inicial , valor_final);
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(pedeNumero("Opção: ",0, 1)==1) {
			return num1;
		}
		else {
			return num;
		}
	}
	
	//A lista é uma string separa por;
	public String mudaListaString(String texto, String lista) {
		System.out.println("");
		System.out.println(texto);
		System.out.println(lista);
		String [] textos = lista.split(";");
		for(int i = 0;i<textos.length;i++) {
			System.out.println((i+1)+"- "+textos[i]);
		}
		System.out.println("0- Sair");
		int opcao = pedeNumero("Opção: ", 0, textos.length);
		if(opcao != 0) {
			String texto1 = textos[opcao-1];
			return texto1;
		}
		return null;

	}
	
}
