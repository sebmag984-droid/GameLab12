
import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Room[] exits;
    private Item item;
    private String defaultDescription;

    public Room(String name, String defaultDescription) {
        this.name = name;
        this.defaultDescription = defaultDescription;
        exits = new Room[6];
        item = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        String description =
                Game.roomDescriptions.get(name);

        if (description != null) {
            return description;
        }

        return defaultDescription;
    }

    public void setDescription(String description) {
        defaultDescription = description;
    }

    public String toString() {
        return name + "\n" + getDescription();
    }

    public void addExit(Room room, int direction) {
        exits[direction] = room;
    }

    public Room getExit(int direction) {
        return exits[direction];
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public boolean removeItem(String itemName) {
        if (item != null &&
                item.getName().equalsIgnoreCase(itemName)) {
            item = null;
            return true;
        }

        return false;
    }

    public Item getItem(String itemName) {
        if (item != null &&
                item.getName().equalsIgnoreCase(itemName)) {
            return item;
        }

        return null;
    }
}
