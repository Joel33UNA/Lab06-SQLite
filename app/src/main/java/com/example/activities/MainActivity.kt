package com.example.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.models.DatabaseHelper
import com.example.models.Estudiante
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal var databaseHelper = DatabaseHelper(this)
    lateinit var personArg : Estudiante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_menu.setNavigationItemSelectedListener(this)

        var bundle = intent.extras
        personArg =  bundle?.getSerializable("Login") as Estudiante

        val navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val header = navigationView?.getHeaderView(0)
        header?.findViewById<TextView>(R.id.nav_header_nombre)?.text = personArg.nombre

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_item_cursos -> {
                val i = Intent(this, CrudCursos::class.java)
                i.putExtra("Login", personArg)
                startActivity(i)
            }
            R.id.nav_item_pesonas -> {
                val i = Intent(this, CrudEstudiantes::class.java)
                i.putExtra("Login", personArg)
                startActivity(i)
            }
            R.id.nav_item_miscursos -> {
                val i = Intent(this, MisCursos::class.java)
                i.putExtra("Login", personArg)
                startActivity(i)
            }
            R.id.nav_item_logout ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    private fun setToolbarTitle(title:String){
        supportActionBar?.title = title
    }
    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }


}