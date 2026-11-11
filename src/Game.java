import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;
import javax.swing.SwingUtilities;

public class Game {

    public static HashMap<String, String> roomDescriptions = new HashMap<String, String>();

    private static Room currentRoom = World.buildWorld();

    private static ArrayList<Item> inventory = new ArrayList<Item>();

    private static Consumer<String> guiOutput;
    private static final String SAVE_FILE = "blackwood_save.dat";

    public static void print(Object obj) {
        String message = String.valueOf(obj);
        System.out.println(message);

        if (GUIOutput != null) {
            GUIOutput.accept(message);
        }
    }

    public static void setGuiOutput(Consumer<String> output) {
        GUIOutput = output;
    }

  
    public static void addInventoryItem(Item item) {
        inventory.add(item);
    }


    public static boolean hasItem(String itemName) {

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }

        return false;
    }

    // Get the current room
    public static Room getCurrentRoom() {
        return currentRoom;
    }

    // Find an item in the room or inventory
    public static Item findItem(String name) {

        Item item = currentRoom.getItem(name);

        if (item != null) {
            return item;
        }

        for (Item inventoryItem : inventory) {
            if (inventoryItem.getName().equalsIgnoreCase(name)) {
                return inventoryItem;
            }
        }

        return null;
    }

    // Process open, close, and read commands
    public static void processItemCommand(String action, String name) {

        Item item = findItem(name);

        if (item == null) {
            print("You cannot find that item.");
            return;
        }

        if (action.equals("open")) {
            item.open();
        }
        else if (action.equals("close")) {
            item.close();
        }
        else if (action.equals("read")) {
            item.read();
        }
    }

    // Load room descriptions
    public static void loadRoomDescriptions() {

        try {
            Scanner fileInput = new Scanner(
                    new File("roomDescriptions.txt"));

            while (fileInput.hasNextLine()) {

                String roomName = fileInput.nextLine().trim();

                if (roomName.isEmpty()) {
                    continue;
                }

                String description = "";

                while (fileInput.hasNextLine()) {

                    String line = fileInput.nextLine();

                    if (line.trim().equals("#")) {
                        break;
                    }

                    description += line + "\n";
                }

                roomDescriptions.put(roomName, description);
            }

            fileInput.close();

            print("Room descriptions loaded successfully.");

        } catch (FileNotFoundException e) {
            print("Could not find roomDescriptions.txt");
        }
    }

    // Move between rooms
    public static void move(String direction) {

        int number;

        if (direction.equals("east")) {
            number = 0;
        }
        else if (direction.equals("west")) {
            number = 1;
        }
        else if (direction.equals("north")) {
            number = 2;
        }
        else if (direction.equals("south")) {
            number = 3;
        }
        else if (direction.equals("up")) {
            number = 4;
        }
        else if (direction.equals("down")) {
            number = 5;
        }
        else {
            print("That is not a valid direction.");
            return;
        }

        Room nextRoom = currentRoom.getExit(number);

        if (nextRoom == null) {
            print("You can't go that way.");
        }
        else if (nextRoom.isLocked()) {
            print("The door is locked. You cannot enter this room yet.");
        }
        else {
            currentRoom = nextRoom;
        }
    }

    // Take an item
    public static void takeItem(String name) {

        Item item = currentRoom.getItem(name);

        if (item == null) {
            print("There is nothing to take.");
            return;
        }

        if (item.isHeavy()) {
            print("You can't lift it.");
            return;
        }

        currentRoom.removeItem(name);
        inventory.add(item);

        print("You take the " + name + " and carry it with you!");
    }

    // Use an item
    public static void useItem(String name) {

        Item item = null;

        for (Item currentItem : inventory) {

            if (currentItem.getName().equalsIgnoreCase(name)) {
                item = currentItem;
                break;
            }
        }

        if (item == null) {
            print("You are not carrying that item.");
            return;
        }

        item.use();
    }

    // Show inventory
    public static void showInventory() {

        if (inventory.isEmpty()) {
            print("You are carrying nothing.");
            return;
        }

        print("You are carrying:");

        for (Item item : inventory) {
            print(item);
        }
    }

    // Save game
    public static void saveGame() {

        try {
            File saveFile = new File("save0.sav");

            ObjectOutputStream outStream =
                    new ObjectOutputStream(
                            new FileOutputStream(saveFile));

            outStream.writeObject(currentRoom);
            outStream.writeObject(inventory);
            outStream.writeObject(Room.rooms);

            outStream.close();

            print("Game saved successfully.");

        } catch (IOException e) {
            print("Error saving game: " + e.getMessage());
        }
    }

    // Load game
    @SuppressWarnings("unchecked")
    public static void loadGame() {

        File saveFile = new File("save0.sav");

        if (!saveFile.exists()) {
            print("No saved game was found.");
            return;
        }

        try {
            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(saveFile));

            currentRoom = (Room) in.readObject();

            inventory = (ArrayList<Item>) in.readObject();

            Room.rooms = (ArrayList<Room>) in.readObject();

            in.close();

            print("Game loaded successfully.");
            print("You are in " + currentRoom.getName());
            print(currentRoom);

        } catch (IOException | ClassNotFoundException e) {
            print("Error loading game: " + e.getMessage());
        }
    }

    // Start GUI game
    public static void runGameGUI() {

        print("Welcome to Blackwood Mansion!");

        print("Type north, south, east, west, up, or down to move.");
        print("Type look to see your current room.");
        print("Type take followed by an item name to pick it up.");
        print("Type use followed by an item name to use it.");
        print("Type open, close, or read followed by an item name.");
        print("Type i to check your inventory.");
        print("Type save to save your game.");
        print("Type load to load your game.");

        print("");

        print(currentRoom);
    }

    // Process GUI commands
    public static void processGUICommand(String command) {

        command = command.trim().toLowerCase();

        if (command.isEmpty()) {
            return;
        }

        String[] words = command.split(" ", 2);

        switch (words[0]) {

            case "north":
            case "south":
            case "east":
            case "west":
            case "up":
            case "down":
                move(words[0]);
                print(currentRoom);
                break;

            case "go":
                if (words.length > 1) {
                    move(words[1].trim());
                    print(currentRoom);
                }
                else {
                    print("Go where?");
                }
                break;

            case "look":
                print(currentRoom);
                break;

            case "take":
                if (words.length > 1) {
                    takeItem(words[1].trim());
                }
                else {
                    print("Take what?");
                }
                break;

            case "use":
                if (words.length > 1) {
                    useItem(words[1].trim());
                }
                else {
                    print("Use what?");
                }
                break;

            // Lab 12 commands
            case "open":
                if (words.length > 1) {
                    processItemCommand("open", words[1].trim());
                }
                else {
                    print("Open what?");
                }
                break;

            case "close":
                if (words.length > 1) {
                    processItemCommand("close", words[1].trim());
                }
                else {
                    print("Close what?");
                }
                break;

            case "read":
                if (words.length > 1) {
                    processItemCommand("read", words[1].trim());
                }
                else {
                    print("Read what?");
                }
                break;

            case "i":
            case "inventory":
                showInventory();
                break;

            case "save":
                saveGame();
                break;

            case "load":
                loadGame();
                break;

            case "help":
                print("Available commands:");
                print("north, south, east, west, up, down");
                print("go direction");
                print("look");
                print("take item");
                print("use item");
                print("open item");
                print("close item");
                print("read item");
                print("i or inventory");
                print("save");
                print("load");
                print("exit or quit");
                break;

            case "exit":
            case "quit":
                print("Goodbye!");
                break;

            default:
                print("I don't understand that command.");
                print("Type help to see the available commands.");
                break;
        }
    }

    // Console game
    public static void runGame() {

        Scanner input = new Scanner(System.in);

        String command;

        print("Welcome to Blackwood Mansion!");
        print("Type help to see all available commands.");

        do {
            print(currentRoom);

            System.out.print("Enter command: ");

            command = input.nextLine().trim().toLowerCase();

            if (command.isEmpty()) {
                continue;
            }

            processGUICommand(command);

        } while (!command.equals("exit") &&
                 !command.equals("quit"));
    }

  

    public static void main(String[] args) {
    	runGameGUI();
    }
}
