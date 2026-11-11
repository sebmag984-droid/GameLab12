import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static ArrayList<Room> rooms = new ArrayList<Room>();

	private String name;
	private String description;
	private Room[] exits;
	private HashMap<String, Item> items;
	private boolean locked;

	public Room(String name, String description) {
		this.name = name;
		this.description = description;
		exits = new Room[6];
		items = new HashMap<String, Item>();
		
		rooms.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Game.roomDescriptions.get(name);
	}

	public void setDescription(String description) {
		Game.roomDescriptions.put(name, description);
	}

	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
		
	
	public String toString() {
		String description = Game.roomDescriptions.get(name);

		if (description == null) {
			description = "No description available.";
		}

		return name + "\n" + description;
	}

	public void addExit(Room room, int direction) {
		exits[direction] = room;
	}

	public Room getExit(int direction) {
		return exits[direction];
	}

	public void addItem(Item item) {
		items.put(item.getName(), item);
	}

	public void setItem(Item item) {
		addItem(item);
	}

	
	public Item getItem(String name) {
		return items.get(name);
	}

	public Item removeItem(String name) {
		return items.remove(name);
	}
	}

