package engine.descriptors;

public enum UserRoles {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;
    UserRoles(String role) {
        this.role = role;
    }
    public String getAsString() {
        return role;
    }
}
