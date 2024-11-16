package vn.edu.hust.studentman

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class CustomDialog(
    private val context: Context,
    private val name: String = "",
    private val id: String = "",
) {
    fun showCustomDialog(update: (dialog: Dialog) -> Unit) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_update_student)
        val editName = dialog.findViewById<EditText>(R.id.edit_student_name)
        val editId = dialog.findViewById<EditText>(R.id.edit_student_id)
        if (name.isNotEmpty()) {
            editName.setText(name)
        }
        if (id.isNotEmpty()) {
            editId.setText(id)
        }
        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_update).setOnClickListener {
            update(dialog)
            dialog.dismiss()
        }
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }
}