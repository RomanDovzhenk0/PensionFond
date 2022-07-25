package Server.Models;

public class Position{
    public int id;
    public String userLogin;
    public String workLogin;
    public String name;
    public String acceptedAt;
    public String firedAt;

    public Position(int id, String userLogin, String workLogin, String name, String acceptedAt, String firedAt) {
        this.id = id;
        this.userLogin = userLogin;
        this.workLogin = workLogin;
        this.name = name;
        this.acceptedAt = acceptedAt;
        this.firedAt = firedAt;
    }
    public Position(String userLogin, String workLogin, String name, String acceptedAt, String firedAt) {
        this.userLogin = userLogin;
        this.workLogin = workLogin;
        this.name = name;
        this.acceptedAt = acceptedAt;
        this.firedAt = firedAt;
    }

    public void setFiredAt(String firedAt) {
        this.firedAt = firedAt;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", workLogin='" + workLogin + '\'' +
                ", name='" + name + '\'' +
                ", acceptedAt='" + acceptedAt + '\'' +
                ", firedAt='" + firedAt + '\'' +
                '}';
    }
}