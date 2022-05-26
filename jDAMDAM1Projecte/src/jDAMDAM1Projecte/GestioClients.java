package jDAMDAM1Projecte;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;



import java.time.LocalDate;
public class GestioClients {

	public static void gestioClient() {
		//COMPRAR I TREUE Modificaraltre
		Scanner lector = new Scanner(System.in);
		Client inventari = new Client();
		String usuariPermanent="";
		boolean open = true; char ver;
		try
        {
        	Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/factura","postgres","972325248lorenZ");
            Statement stmt=con.createStatement(0,ResultSet.CONCUR_UPDATABLE);
            do {
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
            	System.out.println("\t**Benvolgut a [GestioClients]\n\tSeleccioni gestió:");
            	System.out.println("\t\t 1*Alta Client\n\t\t 2*Modificació Dades Client\n\t\t 3*Entrar com Usuari\n\t\t 4*Sortir com Usuari\n\t\t 5*Esborrar Client \n\t\t 6*Sortir");
            	String entrar = lector.nextLine();
            	switch(entrar) {
            	case"1"://ALTA CLIENT
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
            		break;
            	case"2"://MODIFICACIO CLIENT
            		System.out.println("**--MODIFICAR CLIENT--**");
					System.out.println("**VERIFICACIO: Vols modificar un client?[y/n]");
					ver = lector.nextLine().toLowerCase().charAt(0);
					if(ver == 'y') {
						System.out.println("**FORMA: 1*Usuari");
						String forma = lector.nextLine();
						if(forma.equalsIgnoreCase("1") && usuariPermanent!="") {
							jDAMDAM1Projecte.Funcions.modificarDades(con, stmt, usuariPermanent,inventari);
						}
						/*else if(forma.equalsIgnoreCase("2")) {
							System.out.println("Introduïr dni");
							String dni = lector.nextLine();
							ResultSet rs= stmt.executeQuery("select dni from client where dni='"+dni+"';");
							if(rs.next()) {
								System.out.println("Introdueixi la seva contrasenya");
								String contrasenya = lector.nextLine();
								rs = stmt.executeQuery("select contrasenya from client where dni='"+dni+"' and contrasenya ='" +contrasenya+"';");
								while(!(rs.next())) {
									System.out.println("**ERROR DE VALIDACIÓ**\nIntrodueixi la seva contrasenya");
									contrasenya = lector.nextLine();
									rs = stmt.executeQuery("select contrasenya from client where dni='"+dni+"' and contrasenya ='" +contrasenya+"';");
								}
								jDAMDAM1Projecte.Funcions.modificarDades(con, stmt, dni,inventari);
								
							}
							else System.out.println("**El DNI "+ dni +" no esta introduït a la base de dades.");	
						}*/
					}
					else System.out.println("*--SORTINT DE [MODIFICAR CLIENT]");
            		
            		break;
            	case"3"://ENTRAR COM USUARI
            		System.out.println("**--ENTRAR COM USUARI--**");
					System.out.println("**VERIFICACIO: Vols entrar com client?[y/n]");
					ver = lector.nextLine().toLowerCase().charAt(0);
					if(ver == 'y') {
						System.out.println("Introduïr dni");
						String dni = lector.nextLine(); //SI INTENTS ES 5 SURT AUTOMATIC
						ResultSet rs = stmt.executeQuery("select dni from client where dni='"+dni+"';"); boolean correcte = false;
						while(!(rs.next())) {
							System.out.println("Introduïr dni");
							dni = lector.nextLine();
							rs = stmt.executeQuery("select dni from client where dni='"+dni+"';");	
						}
						System.out.println("Introduïr la contrasenya");
						String contrasenya = lector.nextLine();
						rs = stmt.executeQuery("select contrasenya from client where contrasenya='"+contrasenya+"' and dni='"+dni+"';");
						while(!(rs.next())) {
							System.out.println("Introduïr la contrasenya");
							contrasenya = lector.nextLine();
						}
						usuariPermanent = dni;
						rs = stmt.executeQuery("select nom from client where contrasenya='"+contrasenya+"' and dni='"+dni+"';");
						if(rs.next()) {
							System.out.println("\n\t**Has entrat com " + rs.getString("nom")+"**\n\n");
						}
						
					}
					else System.out.println("*--SORTINT DE [ENTRAR COM USUARI]");
            		break;
            	case"4"://SORTIR COM USUARI
            		System.out.println("**VERIFICACIO: Segur que vols tancar la sessió?[y/n]");
            		ver = lector.nextLine().toLowerCase().charAt(0);
            		if(ver == 'y') {
            			usuariPermanent = "";
            			System.out.println("**Sessió tancada correctament.");
            		}
            		break;
            	case"5":
            		System.out.println("**--ESBORRAR USUARI--**");
					System.out.println("**VERIFICACIO: Vols esborrar un usuari?[y/n]");
					ver = lector.nextLine().toLowerCase().charAt(0);
					if(ver == 'y') {
						System.out.println("Seleccioni com vol entrar: 1*Usuari\n\t\t\t\t 2*Administrador");
						String usuari = lector.nextLine();
						if(usuari.equalsIgnoreCase("1")||usuari.equalsIgnoreCase("2")) {
							if(usuari.equalsIgnoreCase("1")) {
								System.out.println("Introduïr dni");
								String dni = lector.nextLine(); //SI INTENTS ES 5 SURT AUTOMATIC
								ResultSet rs = stmt.executeQuery("select dni from client where dni='"+dni+"';"); 
								while(!(rs.next())) {
									System.out.println("Introduïr dni");
									dni = lector.nextLine();
									rs = stmt.executeQuery("select dni from client where dni='"+dni+"';");	
								}
								System.out.println("Introduïr la contrasenya");
								String contrasenya = lector.nextLine();
								rs = stmt.executeQuery("select contrasenya from client where contrasenya='"+contrasenya+"' and dni='"+dni+"';");
								while(!(rs.next())) {
									System.out.println("Introduïr la contrasenya");
									contrasenya = lector.nextLine();
								}
								System.out.println("**VERIFICACIÓ: Segur que vols desactivar el teu usuari?[y/n]");
								char v = lector.nextLine().toLowerCase().charAt(0);
								if(v == 'y') {
									Statement declaracio=con.createStatement();
									declaracio.executeUpdate("UPDATE client set actiu='"+false+"' where dni='"+dni+"';");
									System.out.println("**COMPTA DESACTIVADA AMB ÉXIT**");
								}
								
							}
							else {
								System.out.println("Introduïr dni");
								String adm = lector.nextLine(); //SI INTENTS ES 5 SURT AUTOMATIC
								ResultSet rs = stmt.executeQuery("select codi from client where codi='"+adm+"';");
								while(!(rs.next())) {
									System.out.println("Introduïr codi");
									adm = lector.nextLine();
									rs = stmt.executeQuery("select  from adm where codi='"+adm+"';");	
								}
								System.out.println("Introduïr la contrasenya");
								String contrasenya = lector.nextLine();
								rs = stmt.executeQuery("select contrasenya from adm where contrasenya='"+contrasenya+"' and codi='"+adm+"';");
								while(!(rs.next())) {
									System.out.println("Introduïr la contrasenya");
									contrasenya = lector.nextLine();
								}
								//PREGUNTAR USUARI A DESACTIVAR
								System.out.println("**VERIFICACIÓ: Segur que vols desactivar un usuari?[y/n]");
								char v = lector.nextLine().toLowerCase().charAt(0);
								if(v == 'y') {
									System.out.println("Introdueix el dni"); //VERIFICAR
									String dni = lector.nextLine();
									rs = stmt.executeQuery("select dni from client where dni='"+dni+"';");
									while(!(rs.next())) {
										System.out.println("Introduïr la contrasenya");
										contrasenya = lector.nextLine();
									}
									Statement declaracio=con.createStatement();
									declaracio.executeUpdate("UPDATE client set actiu='"+false+"' where dni='"+dni+"';");
									System.out.println("**COMPTA DESACTIVADA AMB ÉXIT**");
									inventari.baixa(dni); //LA BAIXA EN EL OBJECTE
								}
							}
						}
						else System.out.println("*--SORTINT DE [ESBORRAR USUARI]");
					}
            	break;
            	case"6": //SORTIR
            		open = false;
            	}
            }
            while(open);
             
        }
		catch(Exception e) {
			System.out.println("ERROR EN GESTIO CLIENT");
		}
		//--> AQUI VA EL }
	}

}
