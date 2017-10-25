package ivote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class cabecalhoProg {
	public cabecalhoProg() {
		
		try {
			BufferedReader leFile = new BufferedReader(new FileReader("dados"));
			String texto;
			while((texto = leFile.readLine())!=null) {System.out.println(texto);}
			leFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Sem dados do programa");
		};
	}
}
