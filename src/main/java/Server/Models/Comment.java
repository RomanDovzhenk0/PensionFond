package Server.Models;

public class Comment
{
    public int id;
    public String userLogin;
    public String comment;
    public String createdAt;

    public Comment(String userLogin, String comment, String createdAt)
    {
        this.userLogin = userLogin;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", comment='" + comment + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}