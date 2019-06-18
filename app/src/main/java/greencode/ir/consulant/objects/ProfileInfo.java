package greencode.ir.consulant.objects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "profile")
public class ProfileInfo {
    String firstName;
    String lastName;
    @PrimaryKey(autoGenerate = false)
    int userId;
    String mobile;
    String email;
    String sex;
    long birthDate;
    String nationalCode;
    int bankId;
    String shabaNumber;
    String userImg;
    long timeOfCheck;
    int active;

    public String getFName() {
        return firstName;
    }

    public String getLName() {
        return lastName;
    }

    public int getId() {
        return userId;
    }

    public String getMob() {
        return mobile;
    }

    public String getMail() {
        return email;
    }

    public String getGender() {
        return sex;
    }

    public long getBDate() {
        return birthDate;
    }

    public String getNCode() {
        return nationalCode;
    }

    public int getBId() {
        return bankId;
    }

    public String getShaba() {
        return shabaNumber;
    }

    public String getUImg() {
        return userImg;
    }
    public int getAct(){
     return active;
    }
    public long getTimeCheck() {
        return timeOfCheck;
    }
}
