
import javax.swing.JOptionPane;
import java.io.Serializable;

public class NPC implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public NPC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void say(String dialog) {
        Game.print(name.toUpperCase());
        Game.print(dialog);
    }

    public void talk() {
        say("You can't talk to " + name + ".");
    }

    public void give(Item item) {
        say("I don't want the " + item.getName() + ".");
    
    }
    
    
    public int getResponse(String[] responses) {

        for (int i = 0; i < responses.length; i++) {
            Game.print((i + 1) + ") " + responses[i]);
        }

        String input = JOptionPane.showInputDialog(null, "Choose an option:");

        if (input == null) {
            return 0;
        }

        try {
            int choice = Integer.parseInt(input);

            if (choice >= 1 && choice <= responses.length) {
                return choice;
            }

        } catch (NumberFormatException e) {

     }
        return 0;
    }
}

