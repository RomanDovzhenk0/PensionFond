package Server.Models;

public class Work {
    public String name;
    public String passwordHash;
    public String foundedAt;
    public Work(String name, String passwordHash, String foundedAt) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.foundedAt = foundedAt;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", foundedAt='" + foundedAt + '\'' +
                '}';
    }
}