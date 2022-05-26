package jDAMDAM1Projecte;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class ProgramaEmpresa {

	public static void main(String[] args) {
		Scanner lector = new Scanner(System.in); //EL CODI QUE ET DEMANA ES EL CODI DE LA TAULA ADMINISTRADORS, EL TEU CODI ES [mias] AMB CONTRANSEYA [adm]
		Producte inventari = new Producte();
		Client inventarii = new Client();
		String usuariPermanent="";
		boolean open = true; char ver; boolean openP = true;
		try
        {
        	Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/factura","postgres","972325248lorenZ");
            Statement stmt=con.createStatement(0,ResultSet.CONCUR_UPDATABLE);
            
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
            jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventarii);
            
            do {
            	System.out.println("\t**Benvolgut a [GestioAdministradors]\n**Introdueixi el seu codi per continuar:");
            	String codi = lector.nextLine().toLowerCase();
            	ResultSet rs = stmt.executeQuery("select codi from adm where codi='"+codi+"';");
            	//System.out.println("\t**Benvolgut a [GestioProducte]\n\tSeleccioni gestió:");
            	while(!(rs.next())) {
            		System.out.println("\t**Benvolgut a [GestioProducte]\n\t**Introdueixi el seu codi per continuar:");
                	 codi = lector.nextLine();
                	 rs = stmt.executeQuery("select codi from adm where codi='"+codi+"';");
            	}
            	
            		do {
            			System.out.println("\t**Benvolgut a [GestioAdministradors]\n\tSeleccioni gestió:");
            			System.out.println("\t\t 1*Alta Producte\n\t\t 2*Modificar Producte \n\t\t 3*Baixa Producte\n\t\t 4*Canviar d'usuari\n\t\t 5*Veure client\n\t\t 6*Veure clients\n\t\t 7*Sortir");
            			String entrar = lector.nextLine();
            			switch(entrar) {
            				case"1": //ALTA PRODUCTE
            					System.out.println("**--ALTA PRODUCTE--**");
            					System.out.println("**VERIFICACIO: Vols crear un producte?[y/n]");
            					ver = lector.nextLine().toLowerCase().charAt(0);
            					if(ver == 'y') {
            						System.out.println("Introduïr codi del producte");
        							String codiP = lector.nextLine();
        							if(codiP.length() >4) {
        								codiP = codiP.substring(0,4); System.out.println("CODI REDUÏT : "+ codiP);
        							}
        							rs=stmt.executeQuery("select codi_producte from producte where codi_producte='"+codiP+"';");
        							if((rs.next())) {
        								System.out.println("CODI DEL PRODUCTE DUPLICAT **--SORTINT DEL PROGRAMA--**");
        							}
        							else {
        								System.out.println("Introdueixi nom del producte");
        								String nom = lector.nextLine();
        								nom = nom.toUpperCase().charAt(0) + nom.substring(1, nom.length()).toLowerCase();
        								if(nom.length() > 50) {
        									nom = nom.substring(0,49); System.out.println("NOM REDUÏT: "+ nom);
        								}
        								System.out.println("Introdueixi stock");
        								int stock = 0;
        								boolean funciona = lector.hasNextInt();	 //TRUE SI ES INT .  FALSE SI ! ES INT
        								if(funciona) { //SI ES TRUE ENTRA
        									stock = lector.nextInt(); lector.nextLine(); //LI POSA VALOR NETEJA BUFFER
        								}
        								else {
        										while((!funciona)) { //ENTRA SI ES FALS
        											lector.nextLine(); 		System.out.println("Introduexi preu producte"); //NETEJA EL BUFFER I PREGUNTA
        											funciona = lector.hasNextInt(); //AGAFA EL NUMERO
        											if(funciona) { // SI FUNCIONA ENTRA I POSA EL VALOR NETEJA BUFFER
        												stock = lector.nextInt(); lector.nextLine();
        											}
        										}
        								}
        								//
        								System.out.println("Introdueixi un preu");
        								int preu = 0;
        								boolean funcina = lector.hasNextInt();	 //TRUE SI ES INT .  FALSE SI ! ES INT
        								if(funciona) { //SI ES TRUE ENTRA
        									preu = lector.nextInt(); lector.nextLine(); //LI POSA VALOR NETEJA BUFFER
        								}
        								else {
        										while((!funciona)) { //ENTRA SI ES FALS
        											lector.nextLine(); 		System.out.println("Introduexi preu producte"); //NETEJA EL BUFFER I PREGUNTA
        											funciona = lector.hasNextInt(); //AGAFA EL NUMERO
        											if(funciona) { // SI FUNCIONA ENTRA I POSA EL VALOR NETEJA BUFFER
        												preu = lector.nextInt(); lector.nextLine();
        											}
        										}
        								}
        								//
        								rs = stmt.executeQuery("select tipus from tipus");
        								String tipusTabla [] = new String[13]; int i = 0;
        								while(rs.next()) {
        									String t = rs.getString("tipus");tipusTabla[i] = t;
        									System.out.println(i+ " - " +t);
        									
        											i++;
        								}
        								System.out.println("Seleccioni tipus:");
        								int tip = 0;
        								funcina = lector.hasNextInt();	 //TRUE SI ES INT .  FALSE SI ! ES INT
        								if(funciona) { //SI ES TRUE ENTRA
        									preu = lector.nextInt(); lector.nextLine(); //LI POSA VALOR NETEJA BUFFER
        								}
        								else {
        										while((!funciona)) { //ENTRA SI ES FALS
        											lector.nextLine(); 		System.out.println("Introduexi preu producte"); //NETEJA EL BUFFER I PREGUNTA
        											funciona = lector.hasNextInt(); //AGAFA EL NUMERO
        											if(funciona) { // SI FUNCIONA ENTRA I POSA EL VALOR NETEJA BUFFER
        												preu = lector.nextInt(); lector.nextLine();
        											}
        										}
        								}
        								rs = stmt.executeQuery("select iva from tipus where tipus='"+tipusTabla[tip]+"';"); int iva=0;
        								if(rs.next()) {
        									iva = rs.getInt("iva");
        								}
        								//////////
        								System.out.println("PRODUCTE introduït: " + codiP + " NOM: " + nom +" PREU: "+ preu +" STOCK: "+stock +" IVA: " +iva);
        								Producte aux = new Producte(codiP,nom,preu,stock,iva,true);
        								inventari.afegir(aux);
        								inventari.afegirProducteBD(con, aux);
        							}
            					}
            					break;
            				case"2": //MODIFICAR PRODUCTE
            					System.out.println("**--MODIFICAR PRODUCTE--**");
            					System.out.println("**VERIFICACIO: Vols modificar un producte?[y/n]");
            					ver = lector.nextLine().toLowerCase().charAt(0);
            					if(ver == 'y') {
            						System.out.println("Introdueixi el codi del producte");
            						String codiP = lector.nextLine();
            						rs = stmt.executeQuery("select codi_producte from producte where codi_producte='"+codiP+"';");
            						while(!(rs.next())) {
            							System.out.println("Introdueixi el codi del producte");
                						 codiP = lector.nextLine();
                						 rs = stmt.executeQuery("select codi_producte from producte where codi_producte='"+codiP+"';");
            						}
            						
            						System.out.println("Seleccioni el que vol modificar : 1*Stock\n\t\t\t\t  2*Preu \n\t\t\t\t  3*IVA");
            						String opcio = lector.nextLine();
            						if(!(opcio.equals("1") || opcio.equals("2") || opcio.equals("3"))) {
            							System.out.println("*"+ opcio +" no trobada");
            						}
            						switch(opcio) {
            						case"1"://STOCK
            								System.out.println("Introduir nou stock");
            								int stock = 0;
            								stock = jDAMDAM1Projecte.Funcions.verifyNumberFrase(stock,"Introdueixi nou stock");
            								inventari.modificarStock(con, codiP, stock);
            								System.out.println("**PRODUCTE MODIFICAT AMB EXIT**");
            							break;
            						case"2": //PREU
            								System.out.println("Introduir nou preu");
            								int preu = 0;
            								preu = jDAMDAM1Projecte.Funcions.verifyNumberFrase(preu, "Introduir nou preu");
            								inventari.modificarPreu(con, codiP, preu);
            								System.out.println("**PRODUCTE MODIFICAT AMB EXIT**");
            							break;
            						case"3"://IVA
            							System.out.println("Introduir nou IVA");
            							int iva = 0;
            							iva = jDAMDAM1Projecte.Funcions.verifyNumberFrase(iva, "Introduir nou IVA");
            							inventari.modificarIVA(con, codiP, iva);
            							System.out.println("**PRODUCTE MODIFICAT AMB EXIT**");
            							break;
            						case"4"://SORTIR;
            						}
            					}
            					break;
            				case"3":  //BAIXA PRODUCTE
            					System.out.println("**--ESBORRAR PRODUCTE--**");
            					System.out.println("**VERIFICACIO: Vols donar de baixa un producte?[y/n]");
            					ver = lector.nextLine().toLowerCase().charAt(0);
            					if(ver == 'y') {
            						System.out.println("Introdueixi el codi del producte");
            						String codiP = lector.nextLine();
            						rs = stmt.executeQuery("select codi_producte from producte where codi_producte='"+codiP+"';");
            						while(!(rs.next())) {
            							System.out.println("Introdueixi el codi del producte");
                						 codiP = lector.nextLine();
                						 rs = stmt.executeQuery("select codi_producte from producte where codi_producte='"+codiP+"';");
            						}
            						System.out.println("**PRODUCTE" + codiP+" ESBORRAT AMB ÈXIT");
            						inventari.baixaProducte(con, codiP);
            					}
            					break;
            				case"4": //CANVIAR USUARI
            					openP=false;
            						
            					break;
            				case"5"://VEURE DADES 1 CLIENT
            					System.out.println("**--VEURE CLIENT--**");
            					System.out.println("**VERIFICACIO: Vols veure un client?[y/n]");
            					ver = lector.nextLine().toLowerCase().charAt(0);
            					if(ver == 'y') {
            						System.out.println("Introdueix DNI del client");
            						String dni = lector.nextLine();
            						rs = stmt.executeQuery("select dni from client where dni='"+dni+"';");
        							while(!(rs.next())) {
        								System.out.println("Introdueix DNI del client"); //LO PREGUNTARA HASTA QUE LO SEA
        								dni = lector.nextLine();
        								stmt.executeQuery("select dni from client where dni='"+dni+"';");
        							}
        							inventarii.veureClient(dni);
            							
            						
            					}
            					break;
            				case"6"://VEURE CLIENTS
            					System.out.println("**--VEURE CLIENT--**");
            					System.out.println("**VERIFICACIO: Vols veure els clients registrats?[y/n]");
            					ver = lector.nextLine().toLowerCase().charAt(0);
            					if(ver == 'y') {
            						inventarii.veureClients(inventarii.clients);System.out.println("");
            					}
            					break;
            				case"7": //SORTIR
            					openP = false;
            					open = false;
            					break;
            			}
            		}
            		while(openP);

            }
            while(open);
        }
		catch(Exception e) {
			System.out.println("ERROR PRODUCTE");
		}

	}

}
