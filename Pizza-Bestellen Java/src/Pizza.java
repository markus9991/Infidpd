import java.sql.SQLException;

public class Pizza implements DBClass{
	private DBManager dbm;
	private int pizzaNr;
	private String name;
	private double preis;

	public int getPizzaNr() {
		return pizzaNr;
	}

	public void setPizzaNr(int pizzaNr) {
		this.pizzaNr = pizzaNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public Pizza(int pizzaNr, String name, double preis) {
		super();
		dbm=DBManager.getDBManager();
		this.pizzaNr = pizzaNr;
		this.name = name;
		this.preis = preis;
	}

	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Pizza p = (Pizza) obj;
		return this.getPizzaNr() == p.getPizzaNr() && this.getName().equals(p.getName())
				&& this.getPreis() == p.getPreis();

	}

	@Override
	public void insert() throws SQLException {
		dbm.addPizza(this);
		
	}

	@Override
	public void delete() throws SQLException {
		dbm.deletePizza(this);
		
	}

	@Override
	public void update() throws SQLException {
		dbm.updatePizza(this);
		
	}

	public String toString(){
		return "Pizza Nummer: "+this.getPizzaNr()+"\nBezeichnung: "+this.getName()+"\nPreis: "+this.getPreis();
	}

}
