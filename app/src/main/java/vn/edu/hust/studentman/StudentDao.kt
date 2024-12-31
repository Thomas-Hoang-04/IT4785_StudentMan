package vn.edu.hust.studentman

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.Query
import androidx.room.Upsert
import vn.edu.hust.studentman.StudentModel

@Dao
interface StudentDao {

    @Upsert
    suspend fun upsertStudent(student: StudentModel)

    @Delete
    suspend fun deleteStudent(student: StudentModel)

    @Query("DELETE from students")
    suspend fun deleteAllStudents()

    @Query("SELECT * FROM students")
    suspend fun getAllStudents(): List<StudentModel>
}