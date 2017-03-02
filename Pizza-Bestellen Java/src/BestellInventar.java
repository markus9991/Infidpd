import java.sql.SQLException;
import java.util.ArrayList;

public class BestellInventar {

	private ArrayList<Pizza> pizzen;
	private int[] anz;

	public BestellInventar() {
		DBManager dbm = DBManager.getDBManager();
		try {
			pizzen = dbm.readPizzas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		anz = new int[pizzen.size()];
	}

	public void erhöheAnz(Pizza pi, int anz) {
		for (int i=0;i<pizzen.size();i++) {
			if (pizzen.get(i).equals(pi)) {
				this.anz[i] += anz;
			}
		}
	}

	public double getTotalPrize() {
		double total = 0;
		for (int i = 0; i < anz.length; i++) {
			total += anz[i] * pizzen.get(i).getPreis();
		}
		return total;
	}

	public void writeInventory(Bestellung b) throws SQLException {
		DBManager dbm = DBManager.getDBManager();
		for (int i = 0; i < anz.length; i++) {
			if (anz[i] != 0) {	
					Bestellzuweisungen bez = new Bestellzuweisungen(b.getBestellNr(), pizzen.get(i).getPizzaNr(),anz[i]);
					dbm.addBestellzuweisung(bez);				
			}
		}

	}

}
