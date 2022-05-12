package com.example.agenda

import android.content.ContentValues
import android.content.Context

class PessoaDAO(var context: Context) {

    var banco: BancoHelper

    init {
        this.banco = BancoHelper(this.context)
    }

    fun insert(pessoa: Pessoa){
        val cv = ContentValues()
        cv.put("nome", pessoa.nome)
        cv.put("idade", pessoa.idade)
        this.banco.writableDatabase.insert("pessoa", null, cv)
    }

    fun select(): ArrayList<Pessoa>{
        var lista = arrayListOf<Pessoa>()
        var colunas = arrayOf("id", "nome", "idade")
        var cursor = this.banco.readableDatabase.query("pessoa", colunas, null, null, null, null, null)
        cursor.moveToFirst()
        for (i in 1 .. cursor.count){
            var id = cursor.getInt(colunas.indexOf("id"))
            var nome = cursor.getString(colunas.indexOf("nome"))
            var idade = cursor.getInt(colunas.indexOf("idade"))
            lista.add(Pessoa(id, nome, idade))
            cursor.moveToNext()
        }
        return lista
    }

    fun count(): Int{
        val sql = "select count(id) from pessoas"
        val cursor = this.banco.readableDatabase.rawQuery(sql, null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    fun find(id: Int): Pessoa?{
        var colunas = arrayOf("id", "nome", "idade")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        var cursor = this.banco.readableDatabase.query("pessoa", colunas, where, pWhere, null, null, null)
        cursor.moveToFirst()

        if(cursor.count == 1){
            var id = cursor.getInt(colunas.indexOf("id"))
            var nome = cursor.getString(colunas.indexOf("nome"))
            var idade = cursor.getInt(colunas.indexOf("idade"))

            return Pessoa(id, nome, idade)
        }
        return null
    }

    fun update(pessoa: Pessoa){
        val where = "id = ?"
        val pWhere = arrayOf(pessoa.id.toString())

        val cv = ContentValues()
        cv.put("nome", pessoa.nome)
        cv.put("idade", pessoa.idade)

        this.banco.writableDatabase.update("pessoa", cv, where, pWhere)
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())

        this.banco.writableDatabase.delete("pessoa", where, pWhere)
    }


}