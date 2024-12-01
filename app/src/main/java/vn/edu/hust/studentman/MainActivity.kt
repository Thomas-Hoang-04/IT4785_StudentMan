package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

  private lateinit var root: ConstraintLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    root = findViewById(R.id.main)
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
}