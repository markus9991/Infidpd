import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Bestellung implements DBClass{
	
	private int bestellNr;
	private int rechnungsnummer;
	private int kundenNr;
	private Date datum;
	private DBManager dbm;
	
	public Bestellung( int rechnungsnummer, int kundenNr, Date datum) {
		dbm = DBManager.getDBManager();
		int nr=0;
		try {
			nr=dbm.getBestellnummer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.bestellNr = nr + 1;
		this.rechnungsnummer=rechnungsnummer;
		this.kundenNr = kundenNr;
		this.datum = datum;
	}
	
	public Bestellung(int bestellnr, int rechnungsnummer, int kundenNr, Date datum) {
		dbm = DBManager.getDBManager();
		this.bestellNr = bestellnr;
		this.rechnungsnummer=rechnungsnummer;
		this.kundenNr = kundenNr;
		this.datum = datum;
	}

	public int getBestellNr() {
		return bestellNr;
	}

	public void setBestellNr(int bestellNr) {
		this.bestellNr = bestellNr;
	}

	public int getRechnungsnummer() {
		return this.rechnungsnummer;
	}

	public int getKundenNr() {
		return kundenNr;
	}

	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public void printBestellung() throws SQLException {
		ArrayList<String> zuws=null;
		
		System.out.println("Bestellung wird gedruckt:");
		System.out.println("=========================================");
		System.out.println("Folgende Pizzen wurden bestellt:");
		try {
			zuws=dbm.readZuweisungenFromBestellung(bestellNr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String b : zuws) {
			System.out.println(b);
		}
		Rechnung r=dbm.readRechnung(rechnungsnummer);
		r.printRechnungsdaten();
		
	}

	@Override
	public void insert() throws SQLException {
		dbm.addBestellung(this);
		
	}

	@Override
	public void delete() throws SQLException {
		dbm.deleteBestellung(this);
		
	}

	@Override
	public void update() throws SQLException {
		dbm.updateBestellung(this);
		
	}

	public String toString(){
		return "BestellNr: "+this.bestellNr+"\n"+"Rechungsnummer: "+this.rechnungsnummer+"\n"+"Kundennr: "+this.kundenNr+"\n"+"Datum: "+this.datum.toString() ;
	}

}
