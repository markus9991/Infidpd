import java.sql.SQLException;
import java.util.Date;

public class Rechnung implements DBClass{
	private int rechnungsNr;
	private double rechnungsbetrag;
	private Date datum;
	private DBManager dbm;

	public Rechnung(double rechnungsbetrag, Date datum) {
		super();
		dbm = DBManager.getDBManager();
		int nr = 0;
		try {
			nr = dbm.getRechnungsnummer();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.rechnungsNr = nr + 1000;
		this.rechnungsbetrag = rechnungsbetrag;
		this.datum = datum;
	}
	
	public Rechnung(int recnhungnr,double rechnungsbetrag, Date datum) {
		dbm = DBManager.getDBManager();
		this.rechnungsNr = recnhungnr;
		this.rechnungsbetrag = rechnungsbetrag;
		this.datum = datum;
	}

	public int getRechungsNr() {
		return rechnungsNr;
	}

	public void setRechungsNr(int rechnungsNr) {
		this.rechnungsNr = rechnungsNr;
	}

	public double getRechnungsbetrag() {
		return rechnungsbetrag;
	}

	public void setRechnungsbetrag(double rechnungsbetrag) {
		this.rechnungsbetrag = rechnungsbetrag;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public void printRechnungsdaten(){	
		System.out.println("Rechnung wird geladen:");
		System.out.println("=========================================");
		System.out.println(this.toString());
	}

	@Override
	public void insert() throws SQLException {
		dbm.addRechnung(this);
	}

	@Override
	public void delete() throws SQLException {
		dbm.deleteRechnung(this);
		
	}

	@Override
	public void update() throws SQLException {
		dbm.updateRechnung(this);
		
	}

	public String toString(){
		return "Rechnungsnummer: "+this.getRechungsNr()+"\nRechnungsbetrag: "+this.getRechnungsbetrag()+"\nDatum: "+this.getDatum();	
	}
	
}
