package jDAMDAM1Projecte;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LineaFactura {
	
		protected int nfactura;
		protected int nlinea;
		protected String codi_producte;
		protected int preu;
		protected int quantitat;
		protected ArrayList<LineaFactura> linees = new ArrayList<LineaFactura>();
		
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
		public void afegir(LineaFactura factura) {
			factura.linees.add(factura);
		}
		public LineaFactura agafar(int factura) {
			int k = 0;
			while(k < linees.size() && (!(linees.get(k).getnFactura() == nfactura))){
				k++;
			}
			if(k < linees.size()) {
				return linees.get(k);
			}
			else return null;	
		}
		public void afegirLinea(Connection con, LineaFactura linea) throws SQLException {
		Statement declaracio=con.createStatement();
		
			 declaracio.executeUpdate("INSERT INTO linea VALUES('"+linea.getnFactura()+"','"+linea.getnLinea()+"','"+
				 		linea.getCodi()+"','"+linea.getQuantitat()+"');");
		
		
	          
		}
}
