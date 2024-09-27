package bo;

public enum ItemType {
    SURFBOARD("Surfboard"),
    TOWELS("Towels");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
