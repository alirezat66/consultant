package greencode.ir.consulant.objects;

import greencode.ir.consulant.utils.ToJsonClass;

public class PersonalInfo extends ToJsonClass{

    public PersonalInfo(String firstName, String lastName, long birthDate, String sex, String nationalCode, String email, String userImg, int bankId, String shabaNumber,String phoneNumber,String shParvane) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.nationalCode = nationalCode;
        this.email = email;
        this.userImg = userImg;
        this.bankId = bankId;
        this.shabaNumber = shabaNumber;
        this.phoneNumber = phoneNumber;
        this.shParvane = shParvane;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getShParvane() {
        return shParvane;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getEmail() {
        return email;
    }

    public String getUserImg() {
        return userImg;
    }

    public int getBankId() {
        return bankId;
    }

    public String getShabaNumber() {
        return shabaNumber;
    }

    String firstName;
    String lastName;
    long birthDate;
    String sex;
    String nationalCode;
    String email;
    String userImg;
    int bankId;
    String shabaNumber;
    String phoneNumber;
    String shParvane;
}
