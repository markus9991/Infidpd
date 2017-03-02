import java.sql.SQLException;

public class Kunde implements DBClass{
	private DBManager dbm;
	private int kundenNr;
	private String vorname;
	private String nachname;
	private String strasse;
	private int hausnummer;
	private String ort;
	private int plz;
	private char geschlecht;
	public int getKundenNr() {
		return kundenNr;
	}
	public void setKundenNr(int kundenNr) {
		this.kundenNr = kundenNr;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getStrasse() {
		return strasse;
	}
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
	public int getHausnummer() {
		return hausnummer;
	}
	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public int getPlz() {
		return plz;
	}
	public void setPlz(int plz) {
		this.plz = plz;
	}
	public char getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(char geschlecht) {
		this.geschlecht = geschlecht;
	}
	public Kunde(int kundenNr, String vorname, String nachname, String strasse, int hausnummer, String ort, int plz,
			char geschlecht) {
		super();
		dbm=DBManager.getDBManager();
		this.kundenNr = kundenNr;
		this.vorname = vorname;
		this.nachname = nachname;
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.ort = ort;
		this.plz = plz;
		this.geschlecht = geschlecht;
	}
	@Override
	public void insert() throws SQLException {
		dbm.addKunde(this);
		
	}
	@Override
	public void delete() throws SQLException {
		dbm.deleteKunde(this);
		
	}
	@Override
	public void update() throws SQLException {
		dbm.updateKunde(this);
		
	}
	
	public String toString(){
		return "Name: "+this.getVorname()+" "+this.getNachname()+"("+this.getGeschlecht()+")"+"\nLieferadresse:\t "+this.getStrasse()+" "+this.getHausnummer()+" | "+this.getPlz()+" "+this.getOrt();
	}
	
}
