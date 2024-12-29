package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

  private lateinit var root: ConstraintLayout

  private lateinit var db: StudentDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    root = findViewById(R.id.main)
    db = StudentDatabase.getInstance(this)

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        processData()
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_options, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add -> {
        findNavController(R.id.fragment_view).navigate(R.id.updateFragment, bundleOf("state" to "add"))
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycleScope.launch {
      db.dao.deleteAllStudents()
      db.close()
    }
  }

   private suspend fun processData() {
      Students.refList.forEach {
        db.dao.upsertStudent(it)
      }
     Students.list.clear()
     Students.list.addAll(db.dao.getAllStudents())
  }
}