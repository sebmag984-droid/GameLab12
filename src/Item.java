import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

    private String name;
    private boolean heavy;

    public Item(String name) {
        this(name, false);
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
        return heavy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    public String toString() {
        return name;
    }
}



	

