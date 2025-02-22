import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> adjacentRooms;
    private List<Item> items;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.adjacentRooms = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public String describe() {
        StringBuilder desc = new StringBuilder(name + "\n" + description + "\nItems here: ");
        desc.append(items.isEmpty() ? "none" : items.stream().map(Item::getName).toList());
        return desc.toString();
    }

    public void setAdjacentRoom(String direction, Room room) {
        adjacentRooms.put(direction, room);
    }

    public Room getAdjacentRoom(String direction) {
        return adjacentRooms.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getItem(String name) {
        return items.stream().filter(i -> i.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

