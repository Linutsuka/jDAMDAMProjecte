package jDAMDAM1Projecte;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Funcions {

	public static boolean verifyDNI(String dni) {
		boolean dniPassed= true; 
		for(int i = 0; i < dni.length();i++) {
			if(dni.length()==9) {
				if(i < 8) {
					if(!(Character.isDigit(dni.charAt(i)))) {	
						dniPassed=false;
					}
				}
				else if(  i == 8) {
						if(!(Character.isLetter(dni.charAt(i)))){
							dniPassed=false;
						}
				}
			}
			else if(!(dni.length()==9)) {
				dniPassed=false;
			}
		}
		if(dniPassed) {
			System.out.println("DNI acceptat");
		}
		else System.out.println("DNI denegat");
		return dniPassed;
	}
	public static boolean verifyMail(String mail) {

			boolean valid=true;
			
			String[] emailSplit=mail.split("@");
			
			if(!(emailSplit.length==2)) valid=false;
			else {
				String[] emailSplit2=emailSplit[1].split("\\.");			
				if(emailSplit2.length<2) valid=false;
			}
							
			return valid;
		
	}
	public static boolean verifyTelefon(String telefon) {
		boolean telPassed = true;
		if(telefon.length()==9) {
			for(int i = 0; i < telefon.length();i++) {
				if(!(Character.isDigit(telefon.charAt(i)))) {
					telPassed =  false;
				}	
			}
		}
		else telPassed = false;
		return telPassed;
	}
	public static int verifyNumberFrase(int number, String frase) {
		Scanner lector = new Scanner(System.in);
		boolean funciona = lector.hasNextInt();	 //TRUE SI ES INT .  FALSE SI ! ES INT
		if(funciona) { //SI ES TRUE ENTRA
			number = lector.nextInt(); lector.nextLine(); //LI POSA VALOR NETEJA BUFFER
		}
		else {
				while((!funciona)) { //ENTRA SI ES FALS
					lector.nextLine(); 		System.out.println(frase); //NETEJA EL BUFFER I PREGUNTA
					funciona = lector.hasNextInt(); //AGAFA EL NUMERO
					if(funciona) { // SI FUNCIONA ENTRA I POSA EL VALOR NETEJA BUFFER
						number = lector.nextInt(); lector.nextLine();
					}
				}
		}
		return number;
	}
	public static Producte inventariRestore(Statement stmt, Producte inventari) throws SQLException {
		  ResultSet rr=stmt.executeQuery("select codi_producte,nom,stock,preu,iva,actiu from producte;");
          while(rr.next()) {
          	String c = rr.getString("codi_producte"); String n = rr.getString("nom"); int s  = rr.getInt("stock");
          	int p = rr.getInt("preu"); int i = rr.getInt("iva"); boolean a = rr.getBoolean("actiu");
          	Producte aux = new Producte(c,n,s,p,i,a);
          	inventari.afegir(aux);
          	if(!(inventari.productes.contains(aux))) {
      			inventari.afegir(aux);
      		}
          }
          return inventari;
	}
	public static LineaFactura  inventariRestore(Statement stmt,LineaFactura inventari) throws SQLException {
		  ResultSet rr=stmt.executeQuery("select nfactura,nlinea,codi_producte,quantitat from linea;");
          while(rr.next()) {
          	int c = rr.getInt("nfactura"); int n = rr.getInt("nlinea"); String s  = rr.getString("codi_producte");
          	int p = rr.getInt("quantitat");
          	ResultSet rs = stmt.executeQuery("select preu from producte where codi_producte='"+s+"';"); int preu=0;
          	if(rs.next()) {
          		preu = rs.getInt("preu");
          	}
          	LineaFactura aux = new LineaFactura(c,n,s,p,preu);
          	inventari.afegir(aux);
          	if(!(inventari.linees.contains(aux))) {
      			inventari.afegir(aux);
      		}
          }
          return inventari;
	}
	public static Client inventariRestore(Statement stmt,Client inventari) throws SQLException {
		ResultSet rr = stmt.executeQuery("select dni,nom,telefon,correu,adreca,contrasenya,actiu from client;");
    	while((rr.next())) {
    		String d = rr.getString("dni"); String n = rr.getString("nom"); String t = rr.getString("telefon");
    		String m = rr.getString("correu"); String a = rr.getString("adreca"); String c = rr.getString("contrasenya");
    		boolean aA = rr.getBoolean("actiu");
    		Client aux = new Client(d,n,t,m,a,c,aA);
    		if(!(inventari.clients.contains(aux))) {
    			inventari.afegir(aux);
    		}
    	}
    	return inventari;
	}
	public static Factura inventariRestore(Statement stmt,Factura inventari) throws SQLException {
		ResultSet rr = stmt.executeQuery("select nfactura,dni,data from factura;");
		while((rr.next())) {
    		int d = rr.getInt("nfactura"); String n = rr.getString("dni");
    		String dataa = rr.getDate("data").toString();
			String[] separador = dataa.split("-");
			int year = Integer.parseInt(separador[0]);
			int month = Integer.parseInt(separador[1]);
			int dayOfMonth = Integer.parseInt(separador[2]);
			LocalDate dia = LocalDate.of(year, month, dayOfMonth);
			Client au = new Client();
			au = au.agafar(n);
    		Factura aux = new Factura(d,au,dia);
    		if(!(inventari.factures.contains(aux))) {
    			inventari.afegir(aux);
    		}
    	}
    	return inventari;
	}
	/*public static Factura inventariRestore(Statement stmt,Factura inventari,Client inv) throws SQLException {
		ResultSet rr = stmt.executeQuery("select nfactura,dni,data from factura;");
		while((rr.next())) {
			int n = rr.getInt("factura"); String d = rr.getString("dni"); int num=0;
			ResultSet re = stmt.executeQuery("select nfactura,nlinea,codi_producte,quantitat where nfactura='"+n+"';");
			while(re.next()) {
				num++;
			}
			ResultSet ri = stmt.executeQuery("select nfactura,nlinea,codi_producte,quantitat where nfactura='"+n+"';");
			int k = 0;
			LineaFactura linea[] = new LineaFactura[num];
			while(ri.next()) {
				int nn = ri.getInt("nfactura");  int ni = ri.getInt("nilinea"); String cc = ri.getString("codi_producte");
				int qnt = ri.getInt("quantitat");
				ResultSet a = stmt.executeQuery("select preu from producte where codi_producte='"+cc+"';");
				int preu=0;
				if(a.next()) {
					preu = a.getInt("preu");
				}
				LineaFactura aux= new LineaFactura(nn,ni,cc,preu, qnt);
				linea[k] = aux;
				k++;
			}
			String dataa = rr.getDate("data").toString();
			String[] separador = dataa.split("-");
			int year = Integer.parseInt(separador[0]);
			int month = Integer.parseInt(separador[1]);
			int dayOfMonth = Integer.parseInt(separador[2]);
			LocalDate dia = LocalDate.of(year, month, dayOfMonth);
			Client cl = inv.agafar(d);
			Factura aux = new Factura(n,cl,dia,linea);
			if(!(inventari.factures.contains(aux))) {
    			inventari.afegir(aux);
    		}
		}
		return inventari;
	}*/
	public static void modificarDades(Connection con,Statement stmt, String usuariPermanent,Client inventari) throws SQLException {
		Scanner lector = new Scanner(System.in);
		
		
		System.out.println("Seleccioni el que vol modificar : 1*Correu\n\t\t\t\t  2*Telefon \n\t\t\t\t  3*Adreca");
		String modificar = lector.nextLine(); 
		
		while(!(modificar.equalsIgnoreCase("1") || modificar.equalsIgnoreCase("2") || modificar.equalsIgnoreCase("3"))) {
			System.out.println("Seleccioni el que vol modificar : 1*Correu\n\t\t\t\t  2*Telefon \n\t\t\t\t  3*Adreça");
			modificar = lector.nextLine();
			}
		
			if(modificar.equalsIgnoreCase("1")) {
				ResultSet rs = stmt.executeQuery("SELECT correu from client where dni='"+usuariPermanent+"';");
				if(rs.next()) {
					String rCorreu= rs.getString("correu");
					System.out.println("**El seu CORREU actual es :"+ rCorreu); //POSAR SELECT
					System.out.println("**VERIFICACIO: Vols canviar el seu correu?[y/n]");
					char v = lector.nextLine().toLowerCase().charAt(0);
					if(v == 'y') {
						System.out.println("Introdueixi el seu correu");
						rCorreu = lector.nextLine();
						while(!(jDAMDAM1Projecte.Funcions.verifyMail(rCorreu))) {
							System.out.println("Introdueixi el seu correu");
							rCorreu = lector.nextLine();
						}
						Statement declaracio=con.createStatement();
						declaracio.executeUpdate("UPDATE client set correu='"+rCorreu+"' where dni='"+usuariPermanent+"';");
						System.out.println("**CORREU CANVIAT AMB ÉXIT");
		
						
						inventari.modificarCorreu(usuariPermanent, rCorreu);
						
					}
				}
				
			}
			else if(modificar.equalsIgnoreCase("2")) {
				ResultSet rs = stmt.executeQuery("SELECT telefon from client where dni='"+usuariPermanent+"';");
				String rTelefon = rs.getString("telefon");
				System.out.println("**El seu TELEFON actual es :"+rTelefon); //POSAR SELECT
				System.out.println("**VERIFICACIO: Vols canviar el seu telefon?[y/n]");
				char v = lector.nextLine().toLowerCase().charAt(0);
				if(v == 'y') {
					System.out.println("Introdueixi el seu nou telefon :");
					rTelefon = lector.nextLine();
					while(!(jDAMDAM1Projecte.Funcions.verifyTelefon(rTelefon))) {
						System.out.println("Introdueixi el seu nou telefon :");
						rTelefon = lector.nextLine();
					}
					Statement declaracio=con.createStatement();
					declaracio.executeUpdate("UPDATE client set telefon='"+rTelefon+"' where dni='"+usuariPermanent+"';");
					System.out.println("**TELEFON CANVIAT AMB ÉXIT");
					
					inventari.modificarTelefon(usuariPermanent, rTelefon);
				}
			}
			else {
				ResultSet rs = stmt.executeQuery("SELECT adreca from client where dni='"+usuariPermanent+"';");
				String rAdreca = rs.getString("adreca");
				System.out.println("**El seu ADREÇA actual es :"+rAdreca); //POSAR SELECT
				System.out.println("**VERIFICACIO: Vols canviar la seva adreça?[y/n]");
				char v = lector.nextLine().toLowerCase().charAt(0);
				if(v == 'y') {
					System.out.println("Introdueixi la seva adreça");
					rAdreca = lector.nextLine();
					while(!(jDAMDAM1Projecte.Funcions.verifyMail(rAdreca))) {
						System.out.println("Introdueixi la seva adreça");
						rAdreca = lector.nextLine();
					}
					Statement declaracio=con.createStatement();
					declaracio.executeUpdate("UPDATE client set adreca='"+rAdreca+"' where dni='"+usuariPermanent+"';");
					System.out.println("**ADREÇA CANVIADA AMB ÉXIT");
					
					inventari.modificarMail(usuariPermanent, rAdreca);
				}
			}
		}
		public static void  altaClient(Connection con,Statement stmt,Client inventari) throws SQLException {
			Scanner lector = new Scanner(System.in);
			char ver;
			System.out.println("**--ALTA CLIENT--**");
			System.out.println("**VERIFICACIO: Vols crear un client?[y/n]");
			ver = lector.nextLine().toLowerCase().charAt(0);
			if(ver == 'y') {
				System.out.println("Introdueix DNI");
				String dni = lector.nextLine();
					while(!(jDAMDAM1Projecte.Funcions.verifyDNI(dni))) { //VERIFICO SI ES UN DNI 9 NUMEROS Y 1 LETRA
						System.out.println("Introdueix DNI"); //LO PREGUNTARA HASTA QUE LO SEA
						dni = lector.nextLine();
					}
				ResultSet rs = stmt.executeQuery("select dni from client where dni='"+dni+"';");
					while((rs.next())) {
						System.out.println("Introdueix DNI"); //LO PREGUNTARA HASTA QUE LO SEA
						dni = lector.nextLine();
						stmt.executeQuery("select dni from client where dni='"+dni+"';");
					}
				System.out.println("Introdueix NOM");
				String nom = lector.nextLine();
				nom = nom.toUpperCase().charAt(0) + nom.substring(1, nom.length()).toLowerCase(); //COGE LA PALABRA Y PONE EL PRIMER CHARACTER EN MAYUSUCLA
				if(nom.length()>49) { //SI ELNOMBRE ES MAYOR A 50 LO RECORTA, PARA EVITAR PROBLEMAS EN LA BD
					nom = nom.substring(0,49);
					System.out.println("**NOM introduüit reduït :["+nom+"]");
				}
				System.out.println("Introdueixi TELEFON");
				String telefon = lector.nextLine();
				while(!(jDAMDAM1Projecte.Funcions.verifyTelefon(telefon))) {
					System.out.println("**ERROR DE VALIDACIÒ**");
					System.out.println("Introdueixi TELEFON");
					telefon = lector.nextLine();
				}
				System.out.println("Introdueixi MAIL");
				String mail = lector.nextLine();
				while(!(jDAMDAM1Projecte.Funcions.verifyMail(mail))) {
					System.out.println("**ERROR DE VALIDACIÒ**");
					System.out.println("Introdueixi MAIL");
					mail = lector.nextLine();
				}
				System.out.println("Introdueixi ADREÇA");
				String adreca = lector.nextLine();
				boolean actiu = true;
				boolean correcte = false;
				String contrasenya ="";
				while(!(correcte)) {
					System.out.println("Introdueixi CONTRANSENYA per l'usuari");
					 contrasenya = lector.nextLine();
						if(contrasenya.length() >= 50) {
							System.out.println("**ERROR DE VALIDACIÓ**\nAVIS:La contrasenya ha de MÀXIM 50 càracters.");
						}
						else {
							System.out.println("USUARI CREAT AMB CREDENCIALS :\n\t" + dni +" - " + nom +" - "+ telefon +" - "+mail +" - " + adreca+"\n\n");
							correcte = true;
						}
				}
				Client aux = new Client(dni,nom,telefon,mail,adreca,contrasenya,actiu); inventari.afegir(aux);
				Statement declaracio=con.createStatement();
				declaracio.executeUpdate("INSERT INTO client VALUES ('"+dni+"','"+nom+"','"+mail+"','"+telefon+"','"+adreca+"','"+contrasenya+"',"+actiu+");");
		
			}
			else System.out.println("*--SORTINT DE [ALTA CLIENT]");
			
		}
	}
	
	

