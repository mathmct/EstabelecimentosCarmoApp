package com.example.estabelecimentoscarmo.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estabelecimentoscarmo.adapter.EstabelecimentoAdapter
import com.example.estabelecimentoscarmo.data.AppDatabase
import com.example.estabelecimentoscarmo.data.EstabelecimentoRepository
import com.example.estabelecimentoscarmo.databinding.ActivityMainBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento
import com.example.estabelecimentoscarmo.ui.cadastro.CadastroActivity
import com.example.estabelecimentoscarmo.ui.detalhes.DetalheEstabelecimentoActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var repo: EstabelecimentoRepository
    private lateinit var adapter: EstabelecimentoAdapter

    private val launcherCadastro =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            carregarLista()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val db = AppDatabase.getInstance(this)
        repo = EstabelecimentoRepository(db.estabelecimentoDao())

        adapter = EstabelecimentoAdapter(emptyList()) { abrirDetalhes(it) }

        bind.rvLista.layoutManager = LinearLayoutManager(this)
        bind.rvLista.adapter = adapter

        bind.btnAdd.setOnClickListener {
            launcherCadastro.launch(Intent(this, CadastroActivity::class.java))
        }

        bind.edtFiltro.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filtrarLista(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        carregarLista()
    }

    private fun carregarLista() {
        lifecycleScope.launch(Dispatchers.IO) {
            val lista = repo.listarTodos()
            withContext(Dispatchers.Main) { adapter.atualizar(lista) }
        }
    }

    private fun filtrarLista(texto: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val lista = if (texto.isBlank()) repo.listarTodos()
            else repo.filtrarPorNome(texto)

            withContext(Dispatchers.Main) {
                adapter.atualizar(lista)
            }
        }
    }

    private fun abrirDetalhes(item: Estabelecimento) {
        val intent = Intent(this, DetalheEstabelecimentoActivity::class.java)
        intent.putExtra("item", item)
        startActivity(intent)
    }
}
