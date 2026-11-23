

public class Tool extends Item {

    public Tool(String name) {
        super(name);
    }

    @Override
    public void use() {

        for (Room room : Room.rooms) {

            if (room.getName().equalsIgnoreCase("Garage")) {

                if (room.isLocked()) {
                    room.setLocked(false);
                    Game.print("You use the tool to unlock the Garage!");
                } else {
                    Game.print("The Garage is already unlocked.");
                }

                return;
            }
        }

        Game.print("You cannot find the Garage.");
    }
}
