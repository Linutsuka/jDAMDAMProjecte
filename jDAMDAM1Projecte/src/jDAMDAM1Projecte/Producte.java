package jDAMDAM1Projecte;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Producte {
	
	/*
	 *  codi_producte | character varying(4)  |              | not null |
 nom           | character varying(50) |              |          |
 stock         | integer               |              |          |
 preu          | double precision      |              |          |
 iva
	 */
	protected String codi_producte;
	protected String nom;
	protected int stock; //c
	protected int preu; //c
	protected int iva; //c
	protected boolean actiu;//c
	protected ArrayList<Producte> productes = new ArrayList<Producte>();
	
	public Producte() {
		
		
		
	}
	public Producte(String codi_producte,String nom, int stock,int preu, int iva,boolean actiu) {
		this.codi_producte = codi_producte; this.nom = nom; this.stock = stock; this.preu = preu; this.iva = iva; this.actiu = actiu;
	}
	//GETTERS
	public String getCodi() {
		return this.codi_producte;
	}
	public String getNom() {
		return this.nom;
	}
	public int getStock() {
		return this.stock;
	}
	public int getPreu() {
		return this.preu;
	}
	public int getIva() {
		return this.iva;
	}
	public boolean getEstat() {
		return this.actiu;
	}
	////SETTTERS
	public void setPreu(int preu) {
		this.preu = preu;
	}
	public void setEstat(boolean estat) {
		this.actiu =  estat;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public void setIVA(int iva) {
		this.iva = iva;
	}
	//METODES
	public void afegir(Producte e) {
		productes.add(e);
	}
	public  Producte agafar(String codi) {
		int k = 0;
		while(k < productes.size() && (!(productes.get(k).getCodi().equals(codi)))){
			k++;
		}
		if(k < productes.size()) {
			return productes.get(k);
		}
		else return null;	
	}
	public void modificarPreu(Connection con,String codi,int preu) throws SQLException {
		agafar(codi).setPreu(preu);
		Statement declaracio=con.createStatement();
		declaracio.executeUpdate("update producte  set preu='"+preu+"' where codi_producte='" + agafar(codi).getCodi()+"';");
	}
	public void modificarIVA(Connection con,String codi,int iva) throws SQLException {
		agafar(codi).setPreu(iva);
		Statement declaracio=con.createStatement();
		declaracio.executeUpdate("update producte  set iva='"+iva+"' where codi_producte='" + agafar(codi).getCodi()+"';");
	}
	public void modificarStock(Connection con,String codi,int stock) throws SQLException {
		agafar(codi).setPreu(stock);
		Statement declaracio=con.createStatement();
		declaracio.executeUpdate("update producte  set stock='"+stock+"' where codi_producte='" + agafar(codi).getCodi()+"';");
	}
	public void afegirProducteBD(Connection con, Producte e) throws SQLException {
		Statement declaracio=con.createStatement();
		String sql="insert into producte values('"+e.getCodi()+"','"+e.getNom()+"','"+e.getStock()+"','"+e.getPreu()+"','"+e.getIva()+"','"+e.getEstat()+"');";
		declaracio.executeUpdate(sql);
	}
	public void baixaProducte(Connection con,String codi) throws SQLException {
		agafar(codi).setEstat(false);
		Statement declaracio=con.createStatement();
		declaracio.executeUpdate("update producte  set actiu='"+agafar(codi).getEstat()+"' where codi_producte='" + agafar(codi).getCodi()+"';");
	}
	public static void veureProductes(Producte inventari) {
		for(int i = 0; i < inventari.productes.size(); i++) {
		
			if(inventari.productes.get(i).getEstat() != false && inventari.productes.get(i).getStock() != 0) {
				System.out.println(inventari.productes.get(i).getCodi() +" - "+ inventari.productes.get(i).getNom()+" - "+ 	inventari.productes.get(i).getPreu() +" - " + inventari.productes.get(i).getIva() + " - " + inventari.productes.get(i).getStock());
			}	
		}
	}
	public  int stockExistent(String codi, Producte inventarii) {
		int stock = 
		stock = agafar(codi).getStock();
		
		return stock;
	}
	public static boolean esborrarCOMPRALINEA(String codi, ArrayList<Producte> llista, ArrayList<Integer> quantitat) {
		boolean existeix = false; int index = 0;
		for(int i = 0; i < llista.size(); i++) {
			if(codi.equalsIgnoreCase(llista.get(i).getCodi())) {
				existeix = true; index = i;
			}
		}
		if(existeix) {
			llista.remove(index);
			quantitat.remove(index);
		}
		
		return existeix;
	}
	public static int codiTrobatLlistaCompra(String codi, ArrayList<Producte> llista) {
		boolean existeix = false; int index = 0;
		for(int i = 0; i < llista.size(); i++) {
			if(codi.equalsIgnoreCase(llista.get(i).getCodi())) {
				existeix = true; index = i;
			}
		}		
		return index;
	}
	

}
