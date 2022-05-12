package com.example.agenda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var lvPessoas: ListView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var lista: ArrayList<Pessoa>
    private lateinit var dao: PessoaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = PessoaDAO(this)
        this.lista = arrayListOf()

        this.lvPessoas = findViewById(R.id.lvMainPessoas)
        this.fabAdd = findViewById(R.id.fabMainAdd)

        this.lvPessoas.adapter = ArrayAdapter<Pessoa>(this, android.R.layout.simple_list_item_1, this.dao.select())

        val formResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // executar quando vier a resposta
            if (it.resultCode == RESULT_OK){
                val pessoa = it.data?.getSerializableExtra("PESSOA") as Pessoa
                this.lista.add(pessoa)
                val pessoas = this.dao.select()
                (this.lvPessoas.adapter as ArrayAdapter<Pessoa>).add(pessoa)
            }
        }

        this.fabAdd.setOnClickListener{
            val intent = Intent(this, FormActivity::class.java)
            formResult.launch(intent)
        }
    }

}