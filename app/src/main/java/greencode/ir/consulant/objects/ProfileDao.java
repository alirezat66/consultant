package greencode.ir.consulant.objects;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ProfileDao {

    @Query("select * from  profile limit 1")
    ProfileInfo getProfile();


    @Insert(onConflict = REPLACE)
    void insert(ProfileInfo profile);


    @Query("DELETE FROM profile")
    public void nukeTable();

    @Update
    void update(ProfileInfo profile);
}
