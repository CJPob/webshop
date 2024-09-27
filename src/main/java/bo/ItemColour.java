package bo;

public enum ItemColour {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow"),
    BLACK("Black");

    private final String colour;

    ItemColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }
}
