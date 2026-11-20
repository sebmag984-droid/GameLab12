
public class Bobo extends NPC {
	
	private int convoNumber;

	public Bobo()
    {
        super("Bobo");
        convoNumber = 0;
    }

    public void talk() {
    	
    	if (convoNumber == 0)
    	{
    		conversation0();
        }
        else if (convoNumber == 1)
        {
            conversation1();
        }
        else if (convoNumber == 2)
        {
            conversation2();
        }
        else if (convoNumber == 3)
        {
            conversation3();
        }
        else if (convoNumber == 4)
        {
            conversation4();
        }
        else {
            say("Thanks for helping me! Bye!");
        }
    }

    private void conversation0() {
    	
        say("Hello! How may I help you today?");

        int option = getChoice("1) Got any gum?\n" + "2) You can't help me. I'm here to help you.\n" +"3) Can I have 50 cents?");

        switch(option)
        {
            case 1:
                say("All we have left is pineapple-flavored gum. " + "Oh, wait. Never mind. I'm chewing the last piece.");
                convoNumber = 1;
                break;

            case 2:
                say("Ooh! Nobody's ever helped me before. Thanks!");
                convoNumber = 2;
                break;

            case 3:
                say("No.");
                convoNumber = 3;
                break;

            default:
                say("Uhhh...what?");
        }
    }

    private void conversation1()
    {
        say("I wish I had more gum. What can you do for me?");

        int option = getChoice("1) I'll find you some gum.\n" + "2) Maybe you should try a different flavor.\n" + "3) Never mind."
        );

        switch(option)
        {
            case 1:
                say("Really? Thank you! I'll be waiting!");
                convoNumber = 5;
                break;

            case 2:
                say("Hmm...maybe you're right.");
                convoNumber = 6;
                break;

            case 3:
                say("Okay. Goodbye!");
                convoNumber = 7;
                break;

            default:
                say("I don't understand.");
        }
    }

    private void conversation2()
    {
        say("Are you going to help me now?");

        int option = getChoice("1) I'm going to teach you Spanish: Me llamo Bobo.\n" + "2) I'm giving you a new name: Peaches.\n" + "3) No. I was kidding before. You're beyond help."
        );

        switch(option)
        {
            case 1:
                say("Me llamo Bobo. Yay! I speak Spanish now!");
                convoNumber = 4;
                break;

            case 2:
                setName("Peaches");
                say("Oooh! I feel fuzzy...");
                convoNumber = 4;
                break;

            case 3:
                say("Oh. That's not very nice.");
                convoNumber = 7;
                break;

            default:
                say("Uhhh...what?");
        }
    }

    private void conversation3()
    {
        say("You want 50 cents? I don't even have any money!");
        convoNumber = 7;
    }

    private void conversation4()
    {
        say("Thanks for helping me! Bye!");
    }

    private void conversation5()
    {
        say("You're going to find me gum? Thanks!");
        convoNumber = 7;
    }

    private void conversation6()
    {
        say("Maybe I'll try a different flavor someday.");
        convoNumber = 7;
    }

    private void conversation7()
    {
        say("Thanks for talking to me. Bye!");
    }

    private int getChoice(String choices)
    {
        Game.print(choices);

        String answer = javax.swing.JOptionPane.showInputDialog("Choose an option:");

        if (answer == null)
        {
            return 0;
        }

        try {
            return Integer.parseInt(answer);
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
}
