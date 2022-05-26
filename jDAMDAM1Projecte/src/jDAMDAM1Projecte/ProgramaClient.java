package jDAMDAM1Projecte;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
public class ProgramaClient {

	public static void main(String[] args) {
		Scanner lector = new Scanner(System.in);
		Client inventari = new Client();
		Producte inventarii = new Producte(); String usuari ="";
		Factura inventariii = new Factura();
		LineaFactura inventariL = new LineaFactura();
		
		boolean open = true;
		try {
			Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/factura","postgres","972325248lorenZ");
            Statement stmt=con.createStatement(0,ResultSet.CONCUR_UPDATABLE);
            do {
				jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventari);
				jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventarii);
				jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventariii);
				//jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventariL);
				System.out.println("\t\t**Benvolgut a La Botiga Virtual**");
				
				System.out.println("Introdueixi el seu dni per continuar");
				 usuari = lector.nextLine();
				ResultSet rs = stmt.executeQuery("select dni from client where dni='"+usuari+"';");
				char v='p';
				String texte1= "**ERROR DE VALIDACIÓ: " + usuari +"No trobat a la base de dades";
				String texte2= "Tornar a vàlidar? Si no es vàlida es rediccionarà a [Alta Usuari] [y/n]";
				while(!(rs.next() && v != 'n')) {
					System.out.println(texte1);
					System.out.println(texte2);
					v = lector.nextLine().toLowerCase().charAt(0);
					if(v == 'y') {
						System.out.println("Introdueixi el seu dni per continuar");
						usuari = lector.nextLine();
						rs = stmt.executeQuery("select dni from client where dni='"+usuari+"';");
					}
					else if(v == 'n') {
						usuari = jDAMDAM1Projecte.Funcions.altaClient(con, stmt, inventari);
					System.out.println("d");
					 rs = stmt.executeQuery("select dni from client where dni='"+usuari+"';");
					 texte1 = "El seu dni s'ha vàlidat a la base de dades:";
					 texte2="Si vol validar-se clickar lletra [y]\nSi vol crear un altre un altre usuari cliclar[n]";
						
					}
				}
				System.out.println("a");
				//FALTA ENVIAR A ALTAUSUARI
				System.out.println("Introdueixi la seva contrasenya");
				String cont = lector.nextLine();
				rs = stmt.executeQuery("select contrasenya from client where dni='"+usuari+"' and contrasenya='"+cont+"';");
				while(!(rs.next())) {
					System.out.println("**ERROR DE VALIDACIÓ: " + cont +"No coincident");
					System.out.println("Introdueixi la seva contrasenya");
					 cont = lector.nextLine();
				}
				boolean openP =true;
				do {
						System.out.println("Seleccioni una opció:    1*Veure Productes \n\t\t\t 2*Comprar Producte\n\t\t\t 4*Configuració\n\t\t 5*Sortir usuari\n\t\t 6*Sortir");
						String entrar = lector.nextLine();
						switch(entrar) {
						case "1": //VEURE PRODUCTE
							jDAMDAM1Projecte.Producte.veureProductes(inventarii);
							System.out.println();
							break;
						case "2": //COMPRAR PRODUCTE
							ArrayList<Producte> carret = new ArrayList<Producte>();
							ArrayList<Integer> quantitat = new ArrayList<Integer>();
							System.out.println("**COMPRAR PRODUCTE**");
							System.out.println("Per finalitzar la seva compra prengui la [s]");
							System.out.println("Per visualitzar els productes prengui la[p]");
							System.out.println("Per continuar amb la seva compra prengui la n [n]");
							char ver = lector.nextLine().toLowerCase().charAt(0);
							while(!(ver == 's')) {
								System.out.println("Seleccioni operació: [p]-[n]-[s]");
								char opcio = lector.nextLine().toLowerCase().charAt(0);
								if(opcio == 'p') {
									jDAMDAM1Projecte.Producte.veureProductes(inventarii);
									System.out.println();
								}
								else if(opcio == 'n') {
									System.out.println("Per sortir del apartat compra prengui la lletra [s]");
									System.out.println("Introdueixi qualsevol lletra per començar");
									String cO = lector.nextLine().toLowerCase();int element = 0;
									
									while(!(cO.equalsIgnoreCase("s"))) {
										System.out.println("ELEMENTS A  LA LLISTA:" + carret.size());
										System.out.println("Introdueixi codi del producte");
										 String num = lector.nextLine();
										 cO = num;
										rs = stmt.executeQuery("select codi_producte,stock from producte where codi_producte='"+num+"' and stock !=0 and actiu !=false;");
										if(rs.next()) {
											int stock = rs.getInt("stock");
											while(stock > 0 && !(cO.equals("c"))) {
										
											System.out.println("Introdueixi quantitat;  Quantitat màxima :" + stock );
											int qnt = 0;
											 qnt = jDAMDAM1Projecte.Funcions.verifyNumberFrase(qnt, "Introdueixi quantitat;  Quantitat màxima :\" + stock ");
											 stock = stock-qnt;  cO = "c"; element++;  quantitat.add(qnt);
												jDAMDAM1Projecte.Funcions.inventariRestore(stmt, inventarii); //ACTUALIZAR ELS PRODUCTES QUE TENIM
											 Producte e = inventarii.agafar(num);
											 carret.add(e);
											 if(stock < 0) {
												 	System.out.println("La seva compra no es pot efecturar - stock insuficient");
												 	System.out.println("Si vol cancelar aquesta linea pren la lletra [c]");
												 	cO = lector.nextLine().toLowerCase();
											 }
											}
										}
										else {
											System.out.println("Codi " + num +" no cercat o sense stock");
										}
										
									}
								}
								else if(opcio == 's') {
									ver = 's';
								}
								if(carret.size()>=1) {
									System.out.println("En el seu carret hi ha els elements següents");
									for(int i = 0; i < carret.size();i++) {
										System.out.println(carret.get(i).getCodi() +" - "+carret.get(i).getNom() +" - PREU" + carret.get(i).getPreu() +" - QUANTITAT"+ quantitat.get(i));
									}
									char fin = 'l';
									//lector.nextLine();
									//System.out.println("Vol esborrar o canviar canviar alguna linea\nEsborrar[e]\nQuanitat?[q]\nComprar[d]");
									//fin = lector.nextLine().toLowerCase().charAt(0);
									while(!(fin == 'c')) {
										System.out.println("Vol esborrar o canviar canviar alguna linea\nEsborrar[e]\nQuanitat?\n[q]\nComprar[d]");
										fin = lector.nextLine().toLowerCase().charAt(0);
										if(fin == 'e') {
											System.out.println("Introdueixi codi per esborrar");;
											String codi = lector.nextLine();
											if(jDAMDAM1Projecte.Producte.esborrarCOMPRALINEA(codi, carret,quantitat)) {
												System.out.println("**Producte esborrat de la llista**"); //preguntar si vol veure la llista actual
											}
											else System.out.println("Codi no cercat a la llista de la compra");
										}
										else if(fin == 'q') { //PRDUCTE CANVIAR QUANTITAT
											System.out.println("Introdueixi codi per canviar quantitat");
											String codi = lector.nextLine();
											if(jDAMDAM1Projecte.Producte.codiTrobatLlistaCompra(codi, carret)>-1) {
												int index = jDAMDAM1Projecte.Producte.codiTrobatLlistaCompra(codi, carret);
												System.out.println("Quantitat passada : " +quantitat.get(index) );
												System.out.println("Introdueix nova quantitat");
												int qnt = 0;
												qnt = jDAMDAM1Projecte.Funcions.verifyNumberFrase(qnt,"Introdueix nova quantitat");
												rs = stmt.executeQuery("select codi_producte,stock from producte where codi_producte='"+codi+"' and stock !=0 and actiu !=false;");
												if(rs.next()) {
													int stock = rs.getInt("stock"); stock = stock-qnt;
													if(stock < 0) {
														System.out.println("**ERROR: No hi ha suficient stock");
														
													}
													else {
														quantitat.set(index, qnt);
														System.out.println("*Linea canviada amb èxit*\n" + codi + " - " +qnt);
													}
												}
												
											}
										}
										/////////COMPRAR
										else if(fin == 'd') {
											System.out.println("\n\t\t**FACTURA**");
											int nFactura=0; int nCops = carret.size();int k=0; LineaFactura linea[] = new LineaFactura[nCops];
											rs = stmt.executeQuery("select nfactura from factura");
											while( k < nCops) {
												while(rs.next()) {
													nFactura = rs.getRow(); //COGE EL ULTIMO VALOR DEL SELECT OSEA EL NUMERO DE FILAS Q HAY
												}
												String codiP = carret.get(k).getCodi();
												int preu = carret.get(k).getPreu();
												int qnt = quantitat.get(k);
												LineaFactura aux = new LineaFactura(nFactura,k,codiP,preu,qnt);
												
												linea[k]=aux; 
												k++;
												
											}
											/////
											Client usuariI = inventari.agafar(usuari);
											LocalDate avui = LocalDate.now();
											//AFEGIR A LA BD
											Factura facturaNew = new Factura(nFactura,usuariI,avui,linea);
											
											facturaNew.agafarFactura(con, facturaNew);
											Statement declaracio=con.createStatement();
											
											for(int i = 0; i < linea.length;i++) {
												linea[i].afegirLinea(con, linea[i]);
												System.out.println(i);
											
											}
											
											//mostrar factura
											facturaNew.mostrarFactura(con,stmt,nFactura, usuariI, linea);
											inventariii.afegir(facturaNew);
											fin = 'c';
											ver = 's';
											
										}
									}
									
									
								}
							}
							break;
						case"3": //VEURE FACTURES
							System.out.println("**--VEURE FACTURES--**");
        					System.out.println("**VERIFICACIO: Vols veure una factura?[y/n]");
        					ver = lector.nextLine().toLowerCase().charAt(0);
        					if(ver == 'y') {
        						System.out.println("**El teu historial de factures es aquest :");
        						rs = stmt.executeQuery("select nfactura,dni,data from factura where dni='"+usuari+"';");
        						
        							System.out.println("Factura\t\tData");
        							while(rs.next()) {
            							System.out.println(rs.getInt("nfactura")+"\t\t"+rs.getDate("data"));
            						}
        						System.out.println("Seleccioni una de les factures. Per cancelar [9999]"); //ACABAR
        						int fact = 0;
        						fact = jDAMDAM1Projecte.Funcions.verifyNumberFrase(fact, "Seleccioni una de les factures");
        						rs = stmt.executeQuery("select nfactura,nlinea,codi_producte,quantitat from linea where nfactura='"+fact+"';");
        						int cd=0;
        						while(rs.next()) {
        							
        							
        							/*LineaFactura aux = inventariL.agafar(fact);
        							Factura a = new Factura();
        							int u=0;
        							for(int i = 0;i < aux.linees.size();i++) {
        								if(fact == aux.linees.get(i).getnFactura()) {
        									u++;
        								}
        							}
        							LineaFactura taula[]= new LineaFactura[u];
        							for(int i = 0;i < aux.linees.size();i++) {
        								if(fact == aux.linees.get(i).getnFactura()) {
        									taula[i] = aux.linees.get(i).agafar(fact);
        								}
        							}
        							a.mostrarFactura(con, stmt, fact, inventari, taula);
        							*/
        						}
        						
        						//acabar
        					}
        					else {
        						System.out.println("**SORTINT DE [VEURE FACTURES]");
        					}
							break;
						case"4":
							jDAMDAM1Projecte.GestioClient.gestioClient();
							break;
						case"5":
							openP = false;
							break;
						case "6":
							openP =false;
							open =false;
							break;
						}
				}
				while(openP);
            }
            while(open);
		}
		catch(Exception e) {
				System.out.println("**ERROR EN ENTRADA BOTIGA**");
		}
	}

}
