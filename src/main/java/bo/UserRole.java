package bo;

public enum UserRole {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    STAFF("Staff");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
