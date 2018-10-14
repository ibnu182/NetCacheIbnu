package ibnu21.com.retrofitroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertAllDatas(List<DataProjectEntity> dataResponses);

    @Query("SELECT * FROM DataProjectEntity")
    List<DataProjectEntity> allDatas();

    @Query("DELETE FROM DataProjectEntity")
    void deleteDatas();

}
