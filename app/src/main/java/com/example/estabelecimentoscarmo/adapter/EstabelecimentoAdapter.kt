package com.example.estabelecimentoscarmo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.estabelecimentoscarmo.databinding.ItemEstabelecimentoBinding
import com.example.estabelecimentoscarmo.model.Estabelecimento

class EstabelecimentoAdapter(
    private var estabelecimentos: List<Estabelecimento>,
    private val onClick: (Estabelecimento) -> Unit
) : RecyclerView.Adapter<EstabelecimentoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEstabelecimentoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(estabelecimentos[position])
    }

    override fun getItemCount(): Int = estabelecimentos.size

    fun atualizar(novaLista: List<Estabelecimento>) {
        this.estabelecimentos = novaLista
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemEstabelecimentoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(est: Estabelecimento) {

            try {
                binding.imgFoto.setImageURI(est.fotoUri.toUri())
            } catch (e: SecurityException) {
                binding.imgFoto.setImageResource(android.R.drawable.ic_menu_report_image)
            }

            binding.tvNome.text = est.nome
            binding.tvCategoria.text = est.categoria

            binding.root.setOnClickListener {
                onClick(est)
            }
        }

    }
}
