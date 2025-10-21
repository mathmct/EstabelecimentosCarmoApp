package com.example.estabelecimentoscarmo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.estabelecimentoscarmo.databinding.ItemEstabelecimentoBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento

class EstabelecimentoAdapter(
    private val context: Context,
    private val lista: List<Estabelecimento>
) : ArrayAdapter<Estabelecimento>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemEstabelecimentoBinding
        val itemView: View

        if (convertView == null) {
            binding = ItemEstabelecimentoBinding.inflate(LayoutInflater.from(context), parent, false)
            itemView = binding.root
            itemView.tag = binding
        } else {
            itemView = convertView
            binding = itemView.tag as ItemEstabelecimentoBinding
        }

        val est = lista[position]

        binding.imgFoto.setImageResource(est.foto)
        binding.tvNome.text = est.nome
        binding.tvCategoria.text = est.categoria

        return itemView
    }
}
