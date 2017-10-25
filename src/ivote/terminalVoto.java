package ivote;


public class terminalVoto {
	
	public RMIRemoteInterface comunicacao;	
	public boolean login = false;
	public boolean autentication = false;
	public boolean vote = false;
	public String nomeCliente;
	public String passCliente;
	public int nccCLiente;
	
	public threadClienteTCP threadAssociada = null;
	
	public String nome;
	public String password;
	
	public terminalVoto (RMIRemoteInterface comunicacao,String nome,String password){
		
		this.comunicacao = comunicacao;
		this.nome = nome;
		this.password = password;
		
	}

}
