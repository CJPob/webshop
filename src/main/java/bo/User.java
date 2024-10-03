package bo;
import java.util.List;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String password;
    private String username;
    private UserRole userRole;
    private Cart cart;
    private List<Order> orders;

    protected User(int id, String name, String password, String username, UserRole userRole, Cart cart, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.password = password;  // store hashed maybe?
        this.username = username;
        this.userRole = userRole;
        this.cart = null;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getCartId() {
        return cart.getCartId();
    }

    public void setCartId(int cartId) {
        this.cart.setCartId(cartId);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

   // public void setOrders(List<Order> orders) { this.orders = orders; }

    public enum UserRole {
        ADMIN("Admin"),  CUSTOMER("Customer"),  STAFF("Staff");

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
}
