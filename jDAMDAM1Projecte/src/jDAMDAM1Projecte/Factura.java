package jDAMDAM1Projecte;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
public class Factura {
	
	protected int nFactura;
	protected Client aux;
	protected LocalDate factura;
	protected LineaFactura linea [];
	
	protected ArrayList<Factura> factures = new ArrayList<Factura>();
	//
	public Factura(int nFactura, Client aux, LocalDate date, LineaFactura linea []) {
		this.nFactura = nFactura; this.aux = aux; this.factura = date; this.linea =  linea;
	}
	public Factura() {
		
	}
	//MÈTODES
		//GETTERS
	public int getFactura() {
		return this.nFactura;
	}
	public Client getClient() {
		return this.aux;
	}
	public LocalDate getLocalDate() {
		return this.factura;
	}
		//SETTERS
	public void setFactura(int factura) {
		this.nFactura = factura;
	}
	public void setClient(Client e) {
		this.aux = e;
	}
	public void setLocalDate(LocalDate date) {
		this.factura = date;
	}
	public void afegir(Factura factura) {
		factura.factures.add(factura);
	}
	public void mostrarFactura(Connection con,Statement stmt,int nFactura, Client aux, LineaFactura linea []) throws SQLException {
		System.out.println("["+this.getFactura()+"]\t\t\t" + this.getLocalDate());
		String espacis="--------------------------------------------------";
		System.out.println("\tDescripció\t\tTotal");
		System.out.println("\t----------\t\t-----"); double total =0;
		ArrayList<Integer> agafarIVA = new ArrayList<Integer>();
		for(int i = 0; i < linea.length;i++) {
			ResultSet rs = stmt.executeQuery("select nom,iva from producte where codi_producte='"+ linea[i].getCodi()+"';");
			String nom = "";
			
			if(rs.next()) { 
				nom = rs.getString("nom");
				nom=String.format("%-30s", nom);
				//agafarIVA.add(rs.getInt("iva")); //CALCUL TOTAL
				double ivaCalcul =  (double)rs.getInt("iva")/100;
				total = ((linea[i].getPreu() + ivaCalcul) * linea[i].getQuantitat())   + total;
			}

			System.out.println(linea[i].getnLinea() +" -"+nom +""+linea[i].getPreu()+"€ x "+linea[i].getQuantitat());
			
		}
		System.out.println(espacis);
		
		String texte = "TOTAL EUROS.....:";
		texte= String.format("%-33s", texte);
		//
		System.out.println(texte+""+total);
		System.out.println("Nm d'articles" + linea.length+"\nCompra realitzada en: Botiga Vertual\nTipus de pagament: Targeta");
		//IVA
		
	}
	public void agafarFactura(Connection con, Factura factura) throws SQLException {
		Statement declaracio=con.createStatement(); //no hi posem paràmetres perque no retorna cap RecordSet.
        declaracio.executeUpdate("INSERT INTO factura VALUES ('"+factura.getFactura()+"','"+factura.getClient().getDni()+"','"+factura.getLocalDate()+"');");
       
	}
	

}
