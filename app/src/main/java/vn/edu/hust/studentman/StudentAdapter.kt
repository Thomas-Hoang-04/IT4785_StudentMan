package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
  private val students: MutableList<StudentModel>,
  private val context: Context
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    private val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    private val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    private val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
      .setIcon(R.drawable.baseline_warning_amber_24)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete this student?")
      .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

    fun bind(student: StudentModel) {
      textStudentName.text = student.studentName
      textStudentId.text = student.studentId

      val dialog: AlertDialog = builder.setPositiveButton("Yes") { dialog, _ ->
        val pos: Int = adapterPosition
        if (pos != RecyclerView.NO_POSITION) {
          removeStudent(pos)
        }
        dialog.dismiss()
        Snackbar.make(itemView, "Student deleted", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                students.add(pos, student)
                notifyItemInserted(pos)
            }.show()
      }.create()

      imageEdit.setOnClickListener {
        CustomDialog(context, student.studentName, student.studentId).showCustomDialog { dialog ->
          val name = dialog.findViewById<EditText>(R.id.edit_student_name).text.toString()
          val id = dialog.findViewById<EditText>(R.id.edit_student_id).text.toString()
          students[adapterPosition] = StudentModel(name, id)
          notifyItemChanged(adapterPosition)
          dialog.dismiss()
        }
      }

      imageRemove.setOnClickListener {
        dialog.show()
      }
    }
  }

  private fun removeStudent(position: Int) {
    if (position < 0 || position >= students.size) return
    students.removeAt(position)
    notifyItemRemoved(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
      R.layout.layout_student_item,
      parent, false
    )
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.bind(student)
  }
}