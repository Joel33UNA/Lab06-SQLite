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
import com.example.models.Curso
import com.example.models.DatabaseCurso
import com.example.models.Estudiante
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import kotlin.collections.ArrayList

class CrudCursos : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    internal var databaseCurso = DatabaseCurso(this)
    lateinit var lista: RecyclerView
    lateinit var adaptador:RecyclerView_AdapterCursos
    lateinit var curso: Curso
    lateinit var personArg : Estudiante
    var archived = ArrayList<Curso>()
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_cursos)

        this.databaseCurso.insertData(Curso(2, "inge", 2, 3))
        var cursos = this.databaseCurso.readCursos()
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

        getListOfCursos()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition: Int = viewHolder.adapterPosition
                val toPosition: Int = target.adapterPosition

                Collections.swap(databaseCurso.readCursos(), fromPosition, toPosition)

                lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                position = viewHolder.adapterPosition
                archived = databaseCurso.readCursos()

                if(direction == ItemTouchHelper.LEFT){
                    databaseCurso.deleteData(archived[position].id.toString())
                    lista.adapter?.notifyItemRemoved(position)
                    adaptador = RecyclerView_AdapterCursos(databaseCurso.readCursos())
                    lista.adapter = adaptador

                }else{
                    curso = Curso(archived[position].id, archived[position].descripcion, archived[position].creditos, archived[position].idEstudiante)
                    lista.adapter?.notifyItemChanged(position)
                    adaptador = RecyclerView_AdapterCursos(databaseCurso.readCursos())
                    lista.adapter = adaptador
                    val intent = Intent(this@CrudCursos, EditEstudiante::class.java)
                    intent.putExtra("Curso", curso)
                    intent.putExtra("Login", personArg)
                    startActivity(intent)
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                RecyclerViewSwipeDecorator.Builder(this@CrudCursos, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudCursos, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudCursos, R.color.green))
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
            val i = Intent(this@CrudCursos, AddEstudiante::class.java)
            i.putExtra("Login", personArg)
            startActivity(i)
        }
    }

    private fun getListOfCursos() {
        adaptador = RecyclerView_AdapterCursos(databaseCurso.readCursos())
        lista.adapter = adaptador
    }
}