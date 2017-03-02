import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
	private int CONFLINES=12;
	private String driver;
	private String url;
	private String user;
	private String pw;
	
	private static DBManager instance;
	private Connection c;
	private String[] confs;
	private String[] statements;
	
	private void getConfiguration(String path) throws  FileNotFoundException{
		confs=FileReader.readTxt(CONFLINES,path);
		driver = confs[0];
		url=confs[1];
		user=confs[2];
		pw=confs[3];
		statements=FileReader.readTxt(new Integer(confs[7]).intValue(), confs[6]);
	}
	
	private DBManager(String path) throws SQLException, ClassNotFoundException, FileNotFoundException{
		getConfiguration(path);
		Class.forName(driver);
		if(driver.equals("org.sqlite.JDBC")){
			c =DriverManager.getConnection(url);
		}
		else if(driver.equals("com.mysql.jdbc.Driver")){
			c =DriverManager.getConnection(url,user,pw);
		}
		else{
		System.out.println("Driver nicht erkannt");	
		}
		
		System.out.println("Connection erfolgreich hergestellt.");
		System.out.println("Datenbanktyp: "+driver);
		 
	}
	
	public void createTables(boolean filldefdata) throws FileNotFoundException, SQLException{
		int lines=Integer.parseInt(confs[5]);
		String[] createstatements=FileReader.readTxt(lines,confs[4]);
		PreparedStatement pstmt= null;
		for(int i=0;i<lines;i++){
			pstmt=c.prepareStatement(createstatements[i]);
			pstmt.execute();	
		}
		if(filldefdata){
			fillupTables();
		}
		pstmt.close();
	}
	
	private void fillupTables() throws FileNotFoundException, SQLException{
		String[] strings=FileReader.readTxt(Integer.parseInt(confs[9]), confs[8]);
		for (String string : strings) {
			String[] s=string.split(",");
			Kunde k=new Kunde(new Integer(s[0]).intValue(), s[1], s[2], s[3], new Integer(s[4]).intValue(), s[5], new Integer(s[6]).intValue(), s[7].charAt(0));
			k.insert();
		}
		
		strings=FileReader.readTxt(Integer.parseInt(confs[11]), confs[10]);
		for (String string : strings) {
			String[] s=string.split(",");
			Pizza p=new Pizza(new Integer(s[0]).intValue(),s[1],Double.parseDouble(s[2]));
			p.insert();
		}
	}
	
	public static DBManager getDBManager(String path){
		if(instance==null)
			try {
				instance=new DBManager(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return instance;
	}
	
	public static DBManager getDBManager(){	
		return instance;
	}

	public void addBestellung(Bestellung b) throws SQLException{
		String sql=statements[0];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, b.getBestellNr());
		pstmt.setInt(2, b.getRechnungsnummer());
		pstmt.setInt(3, b.getKundenNr());
		pstmt.setDate(4, (Date) b.getDatum());
		pstmt.execute();
		pstmt.close();
	}

	public void addBestellzuweisung(Bestellzuweisungen b) throws SQLException{
		String sql=statements[1];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, b.getBestellNr());
		pstmt.setInt(2, b.getPizzaNr());
		pstmt.setInt(3, b.getAnzahl());
		pstmt.execute();
		pstmt.close();
	}
	
	public void addKunde(Kunde k) throws SQLException{
		String sql=statements[2];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, k.getKundenNr());
		pstmt.setString(2, k.getVorname());
		pstmt.setString(3, k.getNachname());
		pstmt.setString(4, k.getStrasse());
		pstmt.setInt(5, k.getHausnummer());
		pstmt.setString(6, k.getOrt());
		pstmt.setInt(7, k.getPlz());
		pstmt.setString(8, Character.toString(k.getGeschlecht()));
		pstmt.execute();
		pstmt.close();
	}

	public void addPizza(Pizza pizza) throws SQLException{
		String sql=statements[3];
		PreparedStatement pstmt=c.prepareStatement(sql);
			pstmt.setInt(1, pizza.getPizzaNr());
			pstmt.setString(2, pizza.getName());
			pstmt.setDouble(3, pizza.getPreis());
			pstmt.execute();
			pstmt.close();
		
	}

	public void addRechnung(Rechnung r) throws SQLException{
		String sql=statements[4];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, r.getRechungsNr());
		pstmt.setDouble(2, r.getRechnungsbetrag());
		pstmt.setDate(3, (Date)r.getDatum());
		pstmt.execute();
		pstmt.close();
	}

	public void deleteBestellung(Bestellung b) throws SQLException{
		String sql=statements[5];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, b.getBestellNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deleteBestellzuweisung(Bestellzuweisungen bz) throws SQLException{
		String sql=statements[6];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, bz.getBestellNr());
		stmt.setInt(2, bz.getPizzaNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deleteKunde(Kunde k) throws SQLException{
		String sql=statements[7];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, k.getKundenNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deletePizza(Pizza p) throws SQLException{
		String sql=statements[8];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, p.getPizzaNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deleteRechnung(Rechnung r) throws SQLException{
		String sql=statements[9];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, r.getRechungsNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public ArrayList<Bestellung> readBestelllungen() throws SQLException{
		ArrayList<Bestellung> bestellungen=new ArrayList<Bestellung>();
		String sql=statements[10];
		PreparedStatement stmt= c.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			int nrB=rs.getInt("BestellNr");
			int nrR=rs.getInt("RechnungsNr");
			int nrK=rs.getInt("KundenNr");
			Date date=rs.getDate("Datum");
			bestellungen.add(new Bestellung(nrB,nrR,nrK,date));
		}
		rs.close();
		stmt.close();
		return bestellungen;
	}

	public ArrayList<Bestellzuweisungen> readBestellzuweisungen(int bestellNr) throws SQLException{
		ArrayList<Bestellzuweisungen> zuweisungen=new ArrayList<Bestellzuweisungen>();
		String sql="SELECT * FROM Bestellzuweisungen WHERE BestellNr = ?";
		PreparedStatement stmt= c.prepareStatement(sql);
		stmt.setInt(1, bestellNr);
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			int nrB=rs.getInt("BestellNr");
			int nrP=rs.getInt("PizzaNr");
			int nrA=rs.getInt("Anzahl");
			zuweisungen.add(new Bestellzuweisungen(nrB, nrP,nrA));
		}
		rs.close();
		stmt.close();
		return zuweisungen;
	}
	
	public ArrayList<Bestellzuweisungen> readBestellzuweisungen() throws SQLException {
		ArrayList<Bestellzuweisungen> zuweisungen=new ArrayList<Bestellzuweisungen>();
		String sql=statements[12];
		PreparedStatement stmt= c.prepareStatement(sql);

		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			int nrB=rs.getInt("BestellNr");
			int nrP=rs.getInt("PizzaNr");
			int nrA=rs.getInt("Anzahl");
			zuweisungen.add(new Bestellzuweisungen(nrB, nrP,nrA));
		}
		rs.close();
		stmt.close();
		return zuweisungen;
	}

	public ArrayList<Kunde> readKunden() throws SQLException{
		ArrayList<Kunde> kunden=new ArrayList<Kunde>();
		String sql=statements[13];
		PreparedStatement stmt= c.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			int kn=rs.getInt("KundenNr");
			String vn=rs.getString("Vorname");
			String nn=rs.getString("Nachname");
			String str=rs.getString("Strasse");
			int hn=rs.getInt("Hausnummer");
			String ort=rs.getString("Ort");
			int plz=rs.getInt("PLZ");
			char ges=rs.getString("Geschlecht").charAt(0);
			kunden.add(new Kunde(kn,vn,nn,str,hn,ort,plz,ges));
		}
		rs.close();
		stmt.close();
		return kunden;
	}

	public ArrayList<Pizza> readPizzas() throws SQLException{
		ArrayList<Pizza> pizzen=new ArrayList<Pizza>();
		String sql=statements[14];
		PreparedStatement stmt=c.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			int nr=rs.getInt("PizzaNr");
			String name=rs.getString("Name");
			double preis=rs.getDouble("Preis");
			pizzen.add(new Pizza(nr,name,preis));
		}
		rs.close();
		stmt.close();
		return pizzen;
	}
	
	public ArrayList<Rechnung> readRechnungen() throws SQLException{
		ArrayList<Rechnung> rechnungen =new ArrayList<Rechnung>();
		String sql=statements[15];
		PreparedStatement stmt= c.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		int nr= rs.getInt("RechnungsNr");
		double bet= rs.getDouble("Rechnungsbetrag");
		Date date=rs.getDate("Datum");
		
		rechnungen.add(new Rechnung(nr, bet, date));	
		}
		rs.close();
		stmt.close();
		return rechnungen;
	}
	
	public Rechnung readRechnung(int rnr) throws SQLException{
		String sql=statements[16];
		PreparedStatement stmt= c.prepareStatement(sql);
		stmt.setInt(1, rnr);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int nr= rs.getInt("RechnungsNr");
		double bet= rs.getDouble("Rechnungsbetrag");
		Date date=rs.getDate("Datum");
		rs.close();
		stmt.close();
		return new Rechnung(nr, bet, date);	
	}

	public void updateBestellung(Bestellung b) throws SQLException{
		String sql=statements[17];
		PreparedStatement pstmt=c.prepareStatement(sql);
			pstmt.setInt(1, b.getRechnungsnummer());
			pstmt.setInt(2, b.getKundenNr());
			System.out.println(b.getKundenNr());
			pstmt.setDate(3,  (Date) b.getDatum());
			pstmt.setInt(4, b.getBestellNr());
			System.out.println(b.getBestellNr());
			pstmt.execute();
			pstmt.close();
	}

	public void updateBestellzuweisung(Bestellzuweisungen b) throws SQLException{
		String sql=statements[18];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, b.getBestellNr());
		pstmt.setInt(2, b.getPizzaNr());
		pstmt.setInt(3, b.getAnzahl());
		pstmt.setInt(4, b.getBestellNr());
		pstmt.setInt(5, b.getPizzaNr());
		pstmt.execute();
		pstmt.close();
	}
	
	public void updateKunde(Kunde k) throws SQLException{
		String sql=statements[19];
		PreparedStatement stmt =c.prepareStatement(sql);
		stmt.setInt(1, k.getKundenNr());
		stmt.setString(2, k.getVorname());
		stmt.setString(3, k.getNachname());
		stmt.setString(4, k.getStrasse());
		stmt.setInt(5, k.getHausnummer());
		stmt.setString(6, k.getOrt());
		stmt.setInt(7, k.getPlz());
		stmt.setString(8, String.valueOf( k.getGeschlecht()));
		stmt.setInt(9, k.getKundenNr());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void updatePizza(Pizza pizza) throws SQLException{
		String sql=statements[20];
		PreparedStatement pstmt=c.prepareStatement(sql);
			pstmt.setInt(1, pizza.getPizzaNr());
			pstmt.setString(2, pizza.getName());
			pstmt.setDouble(3, pizza.getPreis());
			pstmt.setInt(4, pizza.getPizzaNr());
			pstmt.execute();
			pstmt.close();
		
	}

	public void updateRechnung(Rechnung r) throws SQLException{
		String sql=statements[21];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, r.getRechungsNr());
		pstmt.setDouble(2, r.getRechnungsbetrag());
		pstmt.setDate(3, (Date)r.getDatum());
		pstmt.setInt(4, r.getRechungsNr());
		pstmt.execute();
		pstmt.close();
	}
	
	public int getBestellnummer() throws SQLException{
		//kein Prepared Statement nötig, da kein eingabe
		String sql=statements[22];
		Statement stmt= c.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		rs.next();
		int nr= rs.getInt(1);
		stmt.close();
		rs.close();
		return nr;	
	}
	
	public int getRechnungsnummer() throws SQLException{
		//kein Prepared Statement nötig, da kein eingabe
		String sql=statements[23];
		Statement stmt= c.createStatement();
		ResultSet rs=stmt.executeQuery(sql);
		rs.next();
		int nr= rs.getInt(1);
		stmt.close();
		return nr;	
	}
	
	public ArrayList<String> readZuweisungenFromBestellung(int BEstellNr) throws SQLException{
		ArrayList<String>zw=new ArrayList<String>();
		String sql=statements[24];
		PreparedStatement pstmt=c.prepareStatement(sql);
		pstmt.setInt(1, BEstellNr);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
		String s=rs.getString("Name")+"\t\t"+rs.getInt("Anzahl");
		zw.add(s);
		}
		
		pstmt.close();
		rs.close();
		return zw;
	}

	
}