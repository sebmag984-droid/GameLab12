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
    private static Room currentRoom = World.buildWorld();
    private static ArrayList<Item> inventory = new ArrayList<Item>();

    public static HashMap<String, String> roomDescriptions =
            new HashMap<String, String>();

    private static Consumer<String> guiOutput;
    private static final String SAVE_FILE = "blackwood_save.dat";

    public static void print(Object obj) {
        String message = String.valueOf(obj);
        System.out.println(message);

        if (guiOutput != null) {
            guiOutput.accept(message);
        }
    }

    public static void setGuiOutput(Consumer<String> output) {
        guiOutput = output;
    }

  
    public static void loadRoomDescriptions() {
        roomDescriptions.clear();

        try {
            Scanner fileScanner =
                    new Scanner(new File("roomDescriptions.txt"));

            while (fileScanner.hasNextLine()) {
                String roomName = fileScanner.nextLine().trim();

                if (roomName.length() == 0) {
                    continue;
                }

                String description = "";

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();

                    if (line.trim().equals("#")) {
                        break;
                    }

                    if (description.length() > 0) {
                        description += "\n";
                    }

                    description += line;
                }

                roomDescriptions.put(roomName, description);
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            print("Warning: roomDescriptions.txt was not found.");
        }
    }

   
    public static void move(String direction) {
        int number;

        if (direction.equals("east")) {
            number = 0;
        } else if (direction.equals("west")) {
            number = 1;
        } else if (direction.equals("north")) {
            number = 2;
        } else if (direction.equals("south")) {
            number = 3;
        } else if (direction.equals("up")) {
            number = 4;
        } else if (direction.equals("down")) {
            number = 5;
        } else {
            print("That is not a valid direction.");
            return;
        }

        Room nextRoom = currentRoom.getExit(number);

        if (nextRoom == null) {
            print("You cannot go that way.");
        } else {
            currentRoom = nextRoom;
            look();
        }
    }

   
    public static void takeItem(String itemName) {
        Item item = currentRoom.getItem();

        if (item == null) {
            print("There is nothing here to take.");
            return;
        }

        if (!itemName.equalsIgnoreCase(item.getName())) {
            print("You do not see a " + itemName + " here.");
            return;
        }

        if (item.isHeavy()) {
            print("That item is too heavy to carry.");
            return;
        }

        inventory.add(item);
        currentRoom.setItem(null);

        print("You picked up the " + item.getName() + ".");
    }

 
    public static void showInventory() {
        if (inventory.isEmpty()) {
            print("Your inventory is empty.");
            return;
        }

        print("Inventory:");

        for (Item item : inventory) {
            print("- " + item.getName());
        }
    }

 
    public static void look() {
        print(currentRoom);

        if (currentRoom.getItem() != null) {
            print("You notice: " + currentRoom.getItem().getName());
        } else {
            print("There are no items to pick up in this room.");
        }
    }

    public static void showHelp() {
        print("Commands:");
        print("north, south, east, west, up, down");
        print("go <direction>");
        print("look");
        print("take <item>");
        print("inventory");
        print("save");
        print("load");
        print("help");
        print("exit");
    }


    public static void saveGame() {
        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(SAVE_FILE));

            output.writeObject(
                    new GameState(currentRoom, inventory));

            output.close();
            print("Blackwood Mansion game saved.");

        } catch (IOException e) {
            print("Could not save the game: " + e.getMessage());
        }
    }

    public static void loadGame() {
        try {
            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(SAVE_FILE));

            GameState state = (GameState) input.readObject();
            input.close();

            currentRoom = state.currentRoom;
            inventory = state.inventory;

            print("Saved game loaded.");
            look();

        } catch (FileNotFoundException e) {
            print("No saved game found. Use save first.");

        } catch (IOException e) {
            print("Could not load the game: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            print("The saved game could not be read.");
        }
    }

    private static class GameState
            implements java.io.Serializable {

        private static final long serialVersionUID = 1L;

        private Room currentRoom;
        private ArrayList<Item> inventory;

        public GameState(
                Room currentRoom,
                ArrayList<Item> inventory) {

            this.currentRoom = currentRoom;
            this.inventory = inventory;
        }
    }

  
    public static void processCommand(String rawCommand) {
        if (rawCommand == null) {
            return;
        }

        String command = rawCommand.trim().toLowerCase();

        if (command.length() == 0) {
            return;
        }

        if (command.startsWith("go ")) {
            move(command.substring(3).trim());

        } else if (command.startsWith("take ")) {
            takeItem(command.substring(5).trim());

        } else {
            switch (command) {
                case "east":
                case "west":
                case "north":
                case "south":
                case "up":
                case "down":
                    move(command);
                    break;

                case "look":
                    look();
                    break;

                case "inventory":
                case "i":
                    showInventory();
                    break;

                case "help":
                    showHelp();
                    break;

                case "save":
                    saveGame();
                    break;

                case "load":
                    loadGame();
                    break;

                case "exit":
                    print("You leave Blackwood Mansion for now.");
                    break;

                default:
                    print("I do not understand that command. Type help.");
            }
        }
    }

    public static void processGUICommand(String command) {
        processCommand(command);
    }

  
    public static void runGame() {
        loadRoomDescriptions();

        Scanner input = new Scanner(System.in);
        String command = "";

        print("Welcome to Blackwood Mansion!");
        look();

        while (!command.equals("exit")) {
            print("Enter a command:");
            command = input.nextLine().trim().toLowerCase();
            processCommand(command);
        }

        input.close();
    }

   
    public static void runGameGUI() {
        loadRoomDescriptions();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameGUI();
            }
        });
    }

    public static String getCurrentRoomText() {
        return currentRoom.toString();
    }

    public static void main(String[] args) {
        runGameGUI();
    }
}
