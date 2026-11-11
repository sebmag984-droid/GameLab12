import java.io.Serializable;


public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean heavy;
	

	public Item(String name) {
		this.name = name;
		this.heavy = false;
	}	
	
	public Item(String name, boolean heavy) {
		this.name = name;
		this.heavy = heavy;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public boolean isHeavy() {
		return false;
	}
	
	public void open() {
		Game.print("You cannot open the " + name + ".");
	}

	  
	public void close() {
		Game.print("You cannot close the " + name + ".");
	}

	    
	public void read() {
		Game.print("You cannot read the " + name + ".");
	}

		
	@Override
	public String toString() {
		return name;
	}
	
	public void use() {
		Game.print("You cant use this item");
	}
}








	

