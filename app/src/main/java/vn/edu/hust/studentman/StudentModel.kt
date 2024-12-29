package vn.edu.hust.studentman

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentModel(
    val studentName: String,
    val studentId: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)