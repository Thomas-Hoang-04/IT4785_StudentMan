package vn.edu.hust.studentman

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.edu.hust.studentman.StudentModel

@Database(entities = [StudentModel::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {
    abstract val dao: StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                                context.applicationContext,
                                StudentDatabase::class.java,
                                "students"
                            ).fallbackToDestructiveMigration(true).build()
                    .also { INSTANCE = it }
            }
        }
    }
}