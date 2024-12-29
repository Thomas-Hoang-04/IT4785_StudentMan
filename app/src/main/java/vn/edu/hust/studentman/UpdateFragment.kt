package vn.edu.hust.studentman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {
    private var name: String = ""
    private var id: String = ""
    private var state: String = "add"
    private var pos: Int = -1

    private val studentAdapter = Students.adapter

    private lateinit var db: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name") ?: name
            id = it.getString("id") ?: id
            state = it.getString("state") ?: state
            pos = it.getInt("position", -1)
        }

        db = StudentDatabase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (name.isNotBlank()) {
            view.findViewById<EditText>(R.id.edit_student_name).setText(name)
        }

        if (id.isNotBlank()) {
            view.findViewById<EditText>(R.id.edit_student_id).setText(id)
        }

        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.btn_update).text = if (state == "add") "Add" else "Update"

        view.findViewById<Button>(R.id.btn_update).setOnClickListener {
            val newName = view.findViewById<EditText>(R.id.edit_student_name).text.toString()
            val newID = view.findViewById<EditText>(R.id.edit_student_id).text.toString()
            val target = StudentModel(studentName = newName, studentId = newID)
            if (state == "add") {
                Students.list.add(target)
                studentAdapter.notifyItemInserted(Students.list.size - 1)
                lifecycleScope.launch {
                    db.dao.upsertStudent(target)
                }
            } else if (state == "update") {
                if (pos != RecyclerView.NO_POSITION) {
                    Students.list[pos] = target
                    studentAdapter.notifyItemChanged(pos)
                    lifecycleScope.launch {
                        db.dao.upsertStudent(target)
                    }
                }
            }
            findNavController().popBackStack()
        }
    }
}