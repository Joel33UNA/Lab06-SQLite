package com.example.activities

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.models.DatabaseHelper
import com.example.models.Estudiante
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class CrudEstudiantes : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    internal var databaseHelper = DatabaseHelper(this)
    lateinit var lista: RecyclerView
    lateinit var adaptador:RecyclerView_Adapter
    lateinit var estudiante: Estudiante
    lateinit var personArg : Estudiante
    var archived = ArrayList<Estudiante>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_estudiantes)

        personArg =  intent.getSerializableExtra("Login") as Estudiante

        val searchIcon = findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)


        val cancelIcon = findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)


        val textView = findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        lista = findViewById(R.id.lista)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

        findViewById<SearchView>(R.id.person_search).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador.filter.filter(newText)
                return false
            }
        })

        getListOfPersons()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val fromPosition: Int = viewHolder.adapterPosition
                        val toPosition: Int = target.adapterPosition

                        Collections.swap(databaseHelper.readEstudiantes(), fromPosition, toPosition)

                        lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        position = viewHolder.adapterPosition
                        archived = databaseHelper.readEstudiantes()

                        if(direction == ItemTouchHelper.LEFT){
                            databaseHelper.deleteEstudiante(archived[position].id.toString())
                            lista.adapter?.notifyItemRemoved(position)
                            adaptador = RecyclerView_Adapter(databaseHelper.readEstudiantes())
                            lista.adapter = adaptador

                        }else{
                            estudiante = Estudiante(archived[position].id, archived[position].nombre, archived[position].clave, archived[position].apellido, archived[position].edad)
                            lista.adapter?.notifyItemChanged(position)
                            adaptador = RecyclerView_Adapter(databaseHelper.readEstudiantes())
                            lista.adapter = adaptador
                            val intent = Intent(this@CrudEstudiantes, EditEstudiante::class.java)
                            intent.putExtra("Estudiante", estudiante)
                            intent.putExtra("Login", personArg)
                            startActivity(intent)
                        }
                    }

                    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                        RecyclerViewSwipeDecorator.Builder(this@CrudEstudiantes, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudEstudiantes, R.color.red))
                            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudEstudiantes, R.color.green))
                            .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                            .create()
                            .decorate()
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    }

                }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(lista)



        val add: FloatingActionButton = findViewById(R.id.add)
        add.setOnClickListener {
            val i = Intent(this@CrudEstudiantes, AddEstudiante::class.java)
            i.putExtra("Login", personArg)
            startActivity(i)
        }
    }

    private fun getListOfPersons() {
        adaptador = RecyclerView_Adapter(databaseHelper.readEstudiantes())
        lista.adapter = adaptador
    }
}