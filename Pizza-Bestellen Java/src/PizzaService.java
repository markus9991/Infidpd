import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PizzaService {

	static DBManager dbm;
	static Scanner scan;

	public static void main(String[] args) {
		//
		// PizzaService ps=new PizzaService();
		// ArrayList<Pizza> pizzen = null;
		// ArrayList<Kunde> kunden = null;
		// try {
		// pizzen=dbm.readPizzas();
		// ps.listPizzas(pizzen);
		// //Pizza p1=new Pizza(150,"Hawaii",7.5);
		// //dbm.addPizza(p1);
		// kunden=dbm.readKunden();
		// ps.listKunden(kunden);
		// Kunde k2=kunden.get(0);
		// k2.setVorname("Olaf");
		// dbm.updateKunde(k2);
		// kunden=dbm.readKunden();
		// ps.listKunden(kunden);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		//
		// ps.bestellen();

		// try {
		// dbm.createTables();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Pizza p=new Pizza(150,"Hawaii",7.5);
		// Bestellung b=new Bestellung(1000,1000, 100, new java.sql.Date(new
		// Date().getTime()));
		// Bestellzuweisungen bz=new Bestellzuweisungen(1000,150, 2);
		// Kunde k=new
		// Kunde(150,"max","Mustermann","Musterstr",11,"Musterhausen",1111,'m');
		// Rechnung r=new Rechnung(1000, 100.3, new java.sql.Date(new
		// Date().getTime()));
		//
		//
		// try {
		// k.delete();
		// bz.delete();
		// p.delete();
		// b.delete();
		// r.delete();
		//
		// k.insert();
		// p.insert();
		// r.insert();
		// b.insert();
		// bz.insert();
		//
		// for(Bestellung bestellung:dbm.readBestelllungen()){
		// System.out.println(bestellung.toString());
		// }
		// for(Bestellzuweisungen bzw:dbm.readBestellzuweisungen()){
		// System.out.println(bzw.toString());
		// }
		// for(Kunde kun:dbm.readKunden()){
		// System.out.println(kun.toString());
		// }
		// for( Pizza pizz:dbm.readPizzas()){
		// System.out.println(pizz.toString());
		// }
		// for(Rechnung rec:dbm.readRechnungen()){
		// System.out.println(rec.toString());
		// }
		//
		// k.setVorname("Rüdiger");
		// p.setPreis(12.5);
		// r.setRechnungsbetrag(200);
		// b.setKundenNr(120);
		// bz.setAnzahl(5);
		//
		// k.update();
		// p.update();
		// r.update();
		// b.update();
		// bz.update();
		//
		// for(Bestellung bestellung:dbm.readBestelllungen()){
		// System.out.println(bestellung.toString());
		// }
		// for(Bestellzuweisungen bzw:dbm.readBestellzuweisungen()){
		// System.out.println(bzw.toString());
		// }
		// for(Kunde kun:dbm.readKunden()){
		// System.out.println(kun.toString());
		// }
		// for( Pizza pizz:dbm.readPizzas()){
		// System.out.println(pizz.toString());
		// }
		// for(Rechnung rec:dbm.readRechnungen()){
		// System.out.println(rec.toString());
		// }
		//
		// k.delete();
		// bz.delete();
		// p.delete();
		// b.delete();
		// r.delete();
		//
		// for(Bestellung bestellung:dbm.readBestelllungen()){
		// System.out.println(bestellung.toString());
		// }
		// for(Bestellzuweisungen bzw:dbm.readBestellzuweisungen()){
		// System.out.println(bzw.toString());
		// }
		// for(Kunde kun:dbm.readKunden()){
		// System.out.println(kun.toString());
		// }
		// for( Pizza pizz:dbm.readPizzas()){
		// System.out.println(pizz.toString());
		// }
		// for(Rechnung rec:dbm.readRechnungen()){
		// System.out.println(rec.toString());
		// }
		//
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// //testweises einlesen
		// try {
		// for(String s:FileReader.readTxt(6,
		// "C:/Users/Markus/OneDrive/Schule/INFI-DPD/Projekt
		// Pizza/createStatements.txt")){
		// System.out.println(s);
		// }
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		PizzaService ps = new PizzaService();
		boolean ende = false;
		scan = new Scanner(System.in);
		dbm = DBManager.getDBManager("confMySQL.txt");

		try {
			dbm.createTables(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Willkommen!!!");
		do {
			System.out.println("Was möchten Sie tun?");
			System.out.println("Manuell hinzufügen...a");
			System.out.println("Manuell ändern...u");
			System.out.println("Manuell löschen...d");
			System.out.println("Tabelleninhalt zeigen...r");
			System.out.println("bestellen...b");
			System.out.println("beenden...x");
			System.out.print("Ihre Wahl: ");
			String st = scan.nextLine();
			char wahl = Character.toLowerCase(st.charAt(0));

			switch (wahl) {
			case 'a':
				System.out.println("In welcher Tabelle möchten Sie etwas hinzufügen??");
				System.out.println("Kunden...k");
				System.out.println("Pizzas...p");
				System.out.print("Ihre Wahl:");
				String st2 = scan.nextLine();
				char wahl2 = Character.toLowerCase(st2.charAt(0));
				switch (wahl2) {
				case 'k':
					ps.addKunde();
					break;
				case 'p':
					ps.addPizza();
					break;
				}
				break;
			case 'u':
				System.out.println("In welcher Tabelle möchten Sie etwas updaten??");
				System.out.println("Kunden...k");
				System.out.println("Pizzas...p");
				System.out.print("Ihre Wahl:");
				String st4 = scan.nextLine();
				char wahl4 = Character.toLowerCase(st4.charAt(0));
				switch (wahl4) {
				case 'k':
					ps.updateKunde();
					break;
				case 'p':
					ps.updatePizza();
					break;
				}
				break;
			case 'd':
				System.out.println("In welcher Tabelle möchten Sie etwas löschen??");
				System.out.println("Kunden...k");
				System.out.println("Pizzas...p");
				System.out.print("Ihre Wahl:");
				String st3 = scan.nextLine();
				char wahl3 = Character.toLowerCase(st3.charAt(0));
				switch (wahl3) {
				case 'k':
					ps.deleteKunden();
					break;
				case 'p':
					ps.deletePizzas();
					break;
				}
				break;
			case 'r':
				ps.readTables();
				break;
			case 'b':
				ps.bestellen();
				break;
			case 'x':
				ende = true;
				break;
			default:
				System.out.println("Falsche Eingabe!!!");
			}
		} while (!ende);
	}

	public void addPizza() {
		System.out.print("PizzaNr: ");
		int pnr = new Integer(scan.nextLine()).intValue();
		System.out.print("PizzaName: ");
		String name = scan.nextLine();
		System.out.print("Preis: ");
		double p = new Double(scan.nextLine()).doubleValue();
		Pizza piz = new Pizza(pnr, name, p);
		try {
			piz.insert();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public void updatePizza() {
		System.out.print("An welchem Eintrag möchten sie etwas ändern (PizzaNr)? ");
		int pnr = new Integer(scan.nextLine()).intValue();
		System.out.print("PizzaName: ");
		String name = scan.nextLine();
		System.out.print("Preis: ");
		double p = new Double(scan.nextLine()).doubleValue();
		Pizza piz = new Pizza(pnr, name, p);
		try {
			piz.update();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public void deletePizzas() {
		System.out.println("PizzaNr: ");
		int nr2 = new Integer(scan.nextLine()).intValue();
		try {
			ArrayList<Pizza> pizzas = dbm.readPizzas();
			for (Pizza p : pizzas) {
				if (p.getPizzaNr() == nr2) {
					p.delete();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void addKunde() {
		System.out.print("KundenNr: ");
		int nr = new Integer(scan.nextLine()).intValue();
		System.out.print("Vorname: ");
		String vn = scan.nextLine();
		System.out.print("Nachname: ");
		String nn = scan.nextLine();
		System.out.print("Strasse: ");
		String str = scan.nextLine();
		;
		System.out.print("Hausnummer: ");
		int hn = new Integer(scan.nextLine()).intValue();
		System.out.print("Ort: ");
		String ort = scan.nextLine();
		System.out.print("Plz ");
		int plz = new Integer(scan.nextLine()).intValue();
		System.out.print("Geschecht ");
		char ge = scan.nextLine().charAt(0);
		Kunde k = new Kunde(nr, vn, nn, str, hn, ort, plz, ge);
		try {
			k.insert();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Kunde erfolgreich hinzugefügt");

	}

	public void updateKunde() {
		System.out.println("An welchem Eintrag möchten sie etwas ändern (Kundennr)?");
		int nr = new Integer(scan.nextLine()).intValue();
		System.out.print("Vorname: ");
		String vn = scan.nextLine();
		System.out.print("Nachname: ");
		String nn = scan.nextLine();
		System.out.print("Strasse: ");
		String str = scan.nextLine();
		;
		System.out.print("Hausnummer: ");
		int hn = new Integer(scan.nextLine()).intValue();
		System.out.print("Ort: ");
		String ort = scan.nextLine();
		System.out.print("Plz ");
		int plz = new Integer(scan.nextLine()).intValue();
		System.out.print("Geschecht ");
		char ge = scan.nextLine().charAt(0);
		Kunde k = new Kunde(nr, vn, nn, str, hn, ort, plz, ge);
		try {
			k.update();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void deleteKunden() {
		System.out.println("KundenNr: ");
		int nr = new Integer(scan.nextLine()).intValue();
		try {
			ArrayList<Kunde> kunden = dbm.readKunden();
			for (Kunde k : kunden) {
				if (k.getKundenNr() == nr) {
					k.delete();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void readTables() {
		try {
			System.out.println("Bestellungen:\n==============================");
			for (Bestellung bestellung : dbm.readBestelllungen()) {
				System.out.println(bestellung.toString());
			}
			System.out.println("Bestellzuweisungen:\n==============================");
			for (Bestellzuweisungen bzw : dbm.readBestellzuweisungen()) {
				System.out.println(bzw.toString());
			}
			System.out.println("Kunden:\n==============================");
			for (Kunde kun : dbm.readKunden()) {
				System.out.println(kun.toString());
			}
			System.out.println("Pizzas:\n==============================");
			for (Pizza pizz : dbm.readPizzas()) {
				System.out.println(pizz.toString());
			}
			System.out.println("Rechnungen:\n==============================");
			for (Rechnung rec : dbm.readRechnungen()) {
				System.out.println(rec.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bestellen() {
		scan = new Scanner(System.in);
		boolean fertig = false;
		int pnr = 0;
		int anz = 0;
		BestellInventar bi = null;
		bi = new BestellInventar();

		System.out.println("Bestellvorgang eingeleitet:");
		System.out.println("Verfügbare Pizzen werden geladen");
		ArrayList<Pizza> pizzen = null;
		try {
			pizzen = dbm.readPizzas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Kunde (Kundennummer): ");
		int kundenNr = new Integer(scan.nextLine()).intValue();
		do {
			anz = 0;
			pnr = 0;
			System.out.print("PizzaNr: ");
			pnr = new Integer(scan.nextLine()).intValue();
			System.out.print("Anzahl: ");
			anz = new Integer(scan.nextLine()).intValue();
			for (Pizza p : pizzen) {
				if (p.getPizzaNr() == pnr) {
					bi.erhöheAnz(p, anz);
				}
			}

			System.out.print("Möchten Sie weitere Pizzen bestellen [j/n]? ");
			String s = scan.nextLine();
			if (Character.toLowerCase(s.charAt(0)) == 'n')
				fertig = true;
		} while (fertig == false);
		try {
			executeBestellung(kundenNr, bi);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void executeBestellung(int kundennr, BestellInventar bi) throws SQLException {

		Rechnung r = new Rechnung(bi.getTotalPrize(), new java.sql.Date(new Date().getTime()));
		dbm.addRechnung(r);
		Bestellung b = new Bestellung(r.getRechungsNr(), kundennr, new java.sql.Date(new Date().getTime()));
		dbm.addBestellung(b);
		bi.writeInventory(b);
		b.printBestellung();
	}

}
