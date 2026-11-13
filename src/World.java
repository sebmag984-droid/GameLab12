
public class World {
	
	public static Room buildWorld() {
		Room livingRoom = new Room("Living Room", "A small living room with couch and coffe table");
		Room office = new Room("Office", "A room with a desk and a computer");
		Room kitchen = new Room("Kitchen", "A kitchen with a refrigerartor and stove");
		Room bedroom = new Room("Bedroom", "A room with a bed and dresser");
		Room attic = new Room("Attic", "A dusty attic with boxes and old belongings");
		Room garage = new Room("Garage", "A garage filled with tools and equipment");

	
		livingRoom.addExit(kitchen, 0);
		livingRoom.addExit(office, 1);
		livingRoom.addExit(bedroom, 2);
		
		office.addExit(livingRoom, 0);
		office.addExit(garage, 3);
		
		kitchen.addExit(livingRoom, 1);
		
		bedroom.addExit(livingRoom, 3);
		bedroom.addExit(attic, 4);
	
		attic.addExit(bedroom, 5);
	
		garage.addExit(office, 2);

		
		
		office.setLocked(true);
		attic.setLocked(true);
		garage.setLocked(true);
		
		
		
		Item tool = new Item("tool");
		Item officekey = new Item("officekey");
		Item notebook = new Item("notebook");
		Item password = new Item("password");

		Item musicbox = new Item("musicbox");
		Item flashlight = new Item("flashlight");
		Item knife = new Item("knife");
		Item orb = new Item("orb");

		Item couch = new Item("couch", true);
		Item bed = new Item("bed", true);

		
		livingRoom.addItem(tool);
		livingRoom.addItem(musicbox);
		livingRoom.addItem(couch);

		bedroom.addItem(officekey);
		bedroom.addItem(bed);

		attic.addItem(notebook);
		attic.addItem(password);

		office.addItem(new Item("computer"));

		kitchen.addItem(flashlight);
		kitchen.addItem(knife);
		kitchen.addItem(orb);

		Bobo bobo = new Bobo();
		livingRoom.setNPC(bobo);
			
		
		return livingRoom;
	}
	}