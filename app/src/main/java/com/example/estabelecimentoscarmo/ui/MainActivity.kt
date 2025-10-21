package com.example.estabelecimentoscarmo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.estabelecimentoscarmo.R
import com.example.estabelecimentoscarmo.adapter.EstabelecimentoAdapter
import com.example.estabelecimentoscarmo.databinding.ActivityMainBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var estabelecimentos: List<Estabelecimento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()
        setupViews()
        setupListeners()
    }

    private fun loadData() {
        estabelecimentos = listOf(
            Estabelecimento(
                R.drawable.habitus,
                "Habitus Academia",
                getString(R.string.categoria_academia),
                "Av. 7 de Setembro, 1142 - Jardim do Carmo, Araraquara - SP",
                "https://habitusacademia.com.br",
                "(16)333328202",
                "6R29+67 Araraquara, State of São Paulo"
            ),
            Estabelecimento(
                R.drawable.larosa,
                "La Rosa - Cantina Italiana",
                getString(R.string.categoria_restaurante),
                "R. Pedro Álvares Cabral, 1139 - Centro, Araraquara - SP, 14800-210",
                "http://cantinalarosa.com.br/",
                "(16)33979007",
                "6R29+C8 Centro, Araraquara, State of São Paulo"
            ),
            Estabelecimento(
                R.drawable.eobicho,
                "É o Bicho - Clínica Veterinária",
                getString(R.string.categoria_veterinaria),
                "Av. 15 de Novembro, 1177 - Carmo, Araraquara - SP, 14801-030",
                "https://pt-br.facebook.com/eobichoararaquara/",
                "(16)996240360",
                "6R29+F4 Araraquara, State of São Paulo"
            ),
            Estabelecimento(
                R.drawable.semprevale,
                "Sempre Vale Supermercados",
                getString(R.string.categoria_supermercado),
                "R. Imac. Conceição, 1511 - Jardim do Carmo, Araraquara - SP, 14801-400",
                "http://www.semprevale.com.br/",
                "(16)33347828",
                "6R28+HC Araraquara, State of São Paulo"
            ),
            Estabelecimento(
                R.drawable.boteco,
                "Boteco Pé na Cova",
                getString(R.string.categoria_bar),
                "Av. Brasil, 1037 - Centro, Araraquara - SP, 14801-050",
                "https://botecopenacova.com.br/",
                "(16)34614080",
                "6R39+CG Centro, Araraquara, State of São Paulo"
            ),
            Estabelecimento(
                R.drawable.mrjames,
                "Mr James - Escola de Inglês",
                getString(R.string.categoria_escola),
                "Rua Comendador Pedro Morganti, 1449 - Centro, Araraquara - SP, 14801-395",
                "http://www.misterjames.com.br/",
                "(16)988348888",
                "6R28+CV Centro, Araraquara, State of São Paulo"
            )
        ).sortedBy { it.nome }
    }

    private fun setupViews() {
        val adapter = EstabelecimentoAdapter(this, estabelecimentos)
        binding.listViewContatos.adapter = adapter
    }

    private fun setupListeners() {
        binding.listViewContatos.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetalheEstabelecimentoActivity::class.java)
            intent.putExtra("estabelecimento", estabelecimentos[position])
            startActivity(intent)
        }
    }
}
