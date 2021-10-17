package com.example.covid_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.Boletim
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    var lista = arrayListOf<Boletim>()
    var adapter = ListAdapter(lista) {
        Log.d("clicado", it.toString())
        handleDetails(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        readJson(this)
    }

    fun readJson(context: Context){
        var json: String?=null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
            var jsonArray = JSONArray(json)
            for (i in 0 .. jsonArray.length()-1){
                var js = jsonArray.getJSONObject(i)
                lista.add(Boletim(
                    suspeitos = js.getInt("Suspeitos"),
                    confirmados = js.getInt("Confirmados"),
                    descartados = js.getInt("Descartados"),
                    monitoramento = js.getInt("Monitoramento"),
                    curados = js.getInt("Curados"),
                    sDomiciliar = js.getInt("Sdomiciliar"),
                    sHospitalar = js.getInt("Chospitalar"),
                    mortes = js.getInt("mortes"),
                    data = formatDate(js.getString("boletim")),
                    hora = formatHour(js.getString("boletim"))
                ))
            }
        }
        catch (e : IOException){
            Log.e("Erro", "Impossivel ler JSON")
        }
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun handleDetails(boletim: Boletim) {
        startActivity(
            Intent(this, DetailsActivity::class.java).apply {
                putExtra("boletim", boletim)
            }
        )
    }

    fun formatDate(data: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        val localDateTime = LocalDateTime.parse(data, inputFormatter);
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return formatter.format(localDateTime)
    }

    fun formatHour(data: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        val localDateTime = LocalDateTime.parse(data, inputFormatter);
        var formatter = DateTimeFormatter.ofPattern("HH:mm")
        return formatter.format(localDateTime)
    }
}