package Server.Models;

public class User
{
    public String login;
    public String password_hash;
    public String fullname;
    public String birthDate;
    public String typeOfAccount;
    public String email;
    public int pension;
    public String address;
    public String pensionNumber;
    public String typeOfPension;
    public boolean isPensioner;
    public String sex;
    public String nextChangeDate;
    public String passSeries;
    public String passId;

    public User(String login,
                String password_hash,
                String fullname,
                String birthDate,
                String typeOfAccount,
                String email,
                int pension,
                String address,
                String pensionNumber,
                String typeOfPension,
                boolean isPensioner,
                String sex,
                String nextChangeDate,
                String passSeries,
                String passId)
    {
        this.login = login;
        this.password_hash = password_hash;
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.typeOfAccount = typeOfAccount;
        this.email = email;
        this.pension = pension;
        this.address = address;
        this.pensionNumber = pensionNumber;
        this.typeOfPension = typeOfPension;
        this.isPensioner = isPensioner;
        this.sex = sex;
        this.nextChangeDate = nextChangeDate;
        this.passSeries = passSeries;
        this.passId = passId;
    }

    public void setPensioner(boolean pensioner) {
        isPensioner = pensioner;
    }

    @Override
    public String toString() {
        return "{" +
                "login='" + login + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", fullname='" + fullname + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", typeOfAccount='" + typeOfAccount + '\'' +
                ", email='" + email + '\'' +
                ", pension=" + pension +
                ", address='" + address + '\'' +
                ", pensionNumber='" + pensionNumber + '\'' +
                ", typeOfPension='" + typeOfPension + '\'' +
                ", isPensioner=" + isPensioner +
                ", sex='" + sex + '\'' +
                ", nextChangeDate='" + nextChangeDate + '\'' +
                ", passSeries='" + passSeries + '\'' +
                ", passId='" + passId + '\'' +
                '}';
    }
}