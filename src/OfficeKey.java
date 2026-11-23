

public class OfficeKey extends Item {

    public OfficeKey(String name) {
        super(name);
    }

    @Override
    public void use() {

        for (Room room : Room.rooms) {

            if (room.getName().equalsIgnoreCase("Office")) {

                if (room.isLocked()) {
                    room.setLocked(false);
                    Game.print("You unlock the Office with the office key!");
                } else {
                    Game.print("The Office is already unlocked.");
                }

                return;
            }
        }

        Game.print("You cannot find the Office.");
    }
}
