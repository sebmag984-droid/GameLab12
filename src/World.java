
public class World {


    public static Room buildWorld() {

        Room entranceHall = new Room( "Entrance Hall", "The entrance hall of Blackwood Mansion is silent.");

        Room livingRoom = new Room( "Living Room", "A faded living room filled with old furniture.");

        Room library = new Room("Library", "Tall shelves of dusty books line the walls.");

        Room kitchen = new Room("Kitchen", "An abandoned kitchen smells faintly of smoke.");

        Room bedroom = new Room("Bedroom", "An old four-poster bed stands beneath torn curtains.");

        Room attic = new Room("Attic","Dusty trunks and broken furniture fill the attic.");

        Room basement = new Room("Basement", "A cold stone basement disappears into the shadows.");

        Room study = new Room("Study","A desk and scattered papers fill the family study.");

        entranceHall.addExit(livingRoom, 0);
        entranceHall.addExit(study, 1);
        entranceHall.addExit(library, 2);
        entranceHall.addExit(kitchen, 3);

        livingRoom.addExit(entranceHall, 1);
        livingRoom.addExit(bedroom, 2);
        livingRoom.addExit(kitchen, 0);

        library.addExit(entranceHall, 3);
        library.addExit(attic, 4);
        library.addExit(attic, 1);

        kitchen.addExit(entranceHall, 2);
        kitchen.addExit(livingRoom, 1);

        bedroom.addExit(livingRoom, 3);
        bedroom.addExit(attic, 4);

        attic.addExit(bedroom, 5);
        attic.addExit(library, 1);

        study.addExit(entranceHall, 0);
        study.addExit(basement, 5);

        basement.addExit(study, 4);


        Item oldKey = new Item("old key");
        Item familyJournal = new Item("family journal");
        Item atticNotebook = new Item("attic notebook");
        Item codedNote = new Item("coded note");
        Item heavyChest = new Item("heavy chest", true);
        
        
        livingRoom.addItem(oldKey);

        library.addItem(familyJournal);
        attic.addItem(atticNotebook);

        study.addItem(codedNote);

        basement.addItem(heavyChest);

        return entranceHall;
    }
}
