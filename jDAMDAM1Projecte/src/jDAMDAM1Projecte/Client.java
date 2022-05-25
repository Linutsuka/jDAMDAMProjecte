package jDAMDAM1Projecte;
import java.util.ArrayList;
public class Client {
	
	protected String dni;
	protected String nom;
	protected String telefon;
	protected String mail;
	protected String adreca;
	protected String contransenya;
	protected boolean actiu;
	protected ArrayList<Client> clients = new ArrayList<Client>();
	//CONSTRUCTORS
	public Client(String dni,String nom,String telefon,String mail,String adreca,String contrasenya,boolean actiu) {
		this.dni = dni; this.nom = nom; this.telefon = telefon; this.mail = mail; this.adreca = adreca; this.contransenya = contrasenya;
		this.actiu = actiu;
	}
	public Client() {
		//CONSTRUCTOR BUIT
	}
	//MÈTODES
		//---GETTERS
	public String getDni() {
		return this.dni;
	}
	public String getNom() {
		return this.nom;
	}
	public String getMail() {
		return this.mail;
	}
	public String getTelefon() {
		return this.telefon;
	}
	public String getAdreca() {
		return this.adreca;
	}
	public String getContrasenya() {
		return this.contransenya;
	}
	public boolean getEstat() {
		return this.actiu;
	}
		//--SETTERS
	public void setDNI(String dni) {
		this.dni = dni;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public void setAdreca(String adreca) {
		this.adreca = adreca;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public void setContrasenya(String contrasenya) {
		this.contransenya= contrasenya;
	}
	public void setEstat(boolean estat) {
		this.actiu = estat;
	}
	public void afegir(Client e) {
		clients.add(e);
	}
	public Client agafar(String dni) {
		int k = 0;
		while(k < clients.size() && (!(clients.get(k).getDni().equals(dni)))){
			k++;
		}
		if(k < clients.size()) {
			return clients.get(k);
		}
		else return null;	
	}
	public void llistar(String dni) {
		System.out.println(agafar(dni).getNom());
	}
	public void modificarMail(String dni, String adreca) {
		agafar(dni).setAdreca(adreca);
	}
	public void modificarTelefon(String dni, String telefon) {
		agafar(dni).setTelefon(telefon);
	}
	public void modificarCorreu(String dni, String correu) {
		agafar(dni).setMail(correu);;
	}
	public void baixa(String dni) {
		agafar(dni).setEstat(false);
	}
	
	
	
	

}
