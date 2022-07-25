package Server.Models;

public class Salary{
    public int id;
    public int posId;
    public int salary;
    public String receivedAt;
    public String coefficient;
    public int taxes;
    public boolean isTaxesPaid;

    public Salary(int posId, int salary, String receivedAt, String coefficient, int taxes, boolean isTaxesPaid) {
        this.posId = posId;
        this.salary = salary;
        this.receivedAt = receivedAt;
        this.coefficient = coefficient;
        this.taxes = taxes;
        this.isTaxesPaid = isTaxesPaid;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", posId=" + posId +
                ", salary=" + salary +
                ", receivedAt='" + receivedAt + '\'' +
                ", coefficient='" + coefficient + '\'' +
                ", taxes=" + taxes +
                ", isTaxesPaid=" + isTaxesPaid +
                '}';
    }
}