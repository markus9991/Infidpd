import java.sql.SQLException;

public class Bestellzuweisungen implements DBClass {
	private DBManager dbm;
	private int bestellNr;
	private int pizzaNr;
	private int anzahl;
	
	public int getBestellNr() {
		return bestellNr;
	}
	public void setBestellNr(int bestellNr) {
		this.bestellNr = bestellNr;
	}
	public int getPizzaNr() {
		return pizzaNr;
	}
	public void setPizzaNr(int pizzaNr) {
		this.pizzaNr = pizzaNr;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	public Bestellzuweisungen(int bestellNr, int pizzaNr, int anzahl) {
		super();
		dbm=DBManager.getDBManager();
		this.bestellNr = bestellNr;
		this.pizzaNr = pizzaNr;
		this.anzahl=anzahl;
	}
	public int getAnzahl() {
		return this.anzahl;
	}
	@Override
	public void insert() throws SQLException {
		dbm.addBestellzuweisung(this);
		
	}
	@Override
	public void delete() throws SQLException {
		dbm.deleteBestellzuweisung(this);
		
	}
	@Override
	public void update() throws SQLException {
		dbm.updateBestellzuweisung(this);
	}
	
	public String toString(){
		return "BestellNr: "+this.bestellNr+"\nPizzaNr: "+this.pizzaNr+"\nAnzahl: "+this.anzahl;
	}
	
}
