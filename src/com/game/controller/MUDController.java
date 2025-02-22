import java.util.Scanner;
import java.util.List;

public class MUDController {
    private Player player;
    private boolean running;
    private Scanner scanner;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
        this.scanner = new Scanner(System.in);
    }

    public void runGameLoop() {
        System.out.println("Welcome to the MUD! Type 'help' for a list of commands.");
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
    }

    private void handleInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = (parts.length > 1) ? parts[1] : "";

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                move(argument);
                break;
            case "pick":
                if (argument.startsWith("up ")) {
                    pickUp(argument.substring(3));
                } else {
                    System.out.println("Invalid command. Try 'pick up <item>'");
                }
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "quit":
            case "exit":
                running = false;
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Unknown command.");
        }
    }

    private void lookAround() {
        Room currentRoom = player.getCurrentRoom();
        System.out.println(currentRoom.describe());
    }

    private void move(String direction) {
        Room nextRoom = player.getCurrentRoom().getAdjacentRoom(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("You move " + direction + ".");
            lookAround();
        } else {
            System.out.println("You can't go that way!");
        }
    }

    private void pickUp(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        if (item != null) {
            player.addItem(item);
            currentRoom.removeItem(item);
            System.out.println("You pick up the " + itemName + ".");
        } else {
            System.out.println("No item named '" + itemName + "' here!");
        }
    }

    private void checkInventory() {
        List<Item> items = player.getInventory();
        if (items.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look - Describes the current room.");
        System.out.println("move <forward|back|left|right> - Moves in a specified direction if possible.");
        System.out.println("pick up <itemName> - Picks up an item from the ground.");
        System.out.println("inventory - Lists items you are carrying.");
        System.out.println("help - Shows this command list.");
        System.out.println("quit or exit - Ends the game.");
    }

    public static void main(String[] args) {
        Room startRoom = new Room("A small stone chamber", "You are in a dimly lit stone room.");
        Room nextRoom = new Room("A dark hallway", "You are in a narrow, dark hallway.");
        startRoom.setAdjacentRoom("forward", nextRoom);
        startRoom.addItem(new Item("sword"));

        Player player = new Player("Hero", startRoom);
        MUDController controller = new MUDController(player);
        controller.runGameLoop();
    }
}

