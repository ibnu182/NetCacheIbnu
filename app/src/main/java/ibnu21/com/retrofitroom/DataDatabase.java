package ibnu21.com.retrofitroom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = DataProjectEntity.class, version = 1, exportSchema = false)
public abstract class DataDatabase extends RoomDatabase{

     public abstract DaoAccess getDaoAccess();

}
