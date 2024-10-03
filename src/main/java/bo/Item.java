package bo;

public class Item {
    private final int id;
    private String name;
    private final ItemType type;
    private final ItemColour colour;
    private int price;
    private int quantity;
    private String description;

    protected Item(int id, String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ItemType getType() {
        return this.type;
    }

    public ItemColour getColour() {
        return this.colour;
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum ItemColour {
        RED("Red"), BLUE("Blue"), GREEN("Green"), YELLOW("Yellow"), BLACK("Black");

        private final String colour;

        ItemColour(String colour) {
            this.colour = colour;
        }

        public String getColour() {
            return this.colour;
        }
    }

    public enum ItemType {
        SURFBOARD("Surfboard"), TOWELS("Towels");

        private final String type;

        ItemType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }
}
