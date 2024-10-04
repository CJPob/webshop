package bo;

public enum ItemType {
    SURFBOARD("Surfboard"),
    TOWEL("Towel"),
    WETSUIT("Wetsuit"),
    SUNSCREEN("Sunscreen");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}