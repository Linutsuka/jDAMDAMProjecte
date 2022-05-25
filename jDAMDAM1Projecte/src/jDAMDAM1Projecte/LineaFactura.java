package jDAMDAM1Projecte;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LineaFactura {
	
		protected int nfactura;
		protected int nlinea;
		protected String codi_producte;
		protected int preu;
		protected int quantitat;
		
		public LineaFactura(int factura, int nlinea, String codi, int preu, int quantitat) {
			this.nfactura = factura; this.nlinea = nlinea; this.codi_producte = codi; this.preu = preu; this.quantitat = quantitat;
		}
		public LineaFactura() {
			
		}
		//MÈTODES
		
			//GETTERS
		public int getnFactura() {
			return this.nfactura;
		}
		public int getnLinea() {
			return this.nlinea;
		}
		public String getCodi() {
			return this.codi_producte;
		}
		public int getPreu() {
			return this.preu;
		}
		public int getQuantitat() {
			return this.quantitat;
		}
			//SETTERS
		public void setFactura(int factura) {
			this.nfactura = factura;
		}
		public void setPreu(int preu) {
			this.preu =preu;
		}
		public void setQuantitat(int quantitat) {
			this.quantitat = quantitat;
		}
		public void afegirLinea(Connection con, LineaFactura linea []) throws SQLException {
		Statement declaracio=con.createStatement();
		for(int i = 0; i < linea.length; i++) {
			 declaracio.executeUpdate("INSERT INTO linea VALUES('"+linea[i].getnFactura()+"','"+linea[i].getnLinea()+"','"+
				 		linea[i].getCodi()+"','"+linea[i].getQuantitat()+"');");
		}
		
	          
		}
}
