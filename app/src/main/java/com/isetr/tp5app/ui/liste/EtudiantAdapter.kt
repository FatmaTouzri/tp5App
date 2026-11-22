package com.isetr.tp5app.ui.liste

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.tp5app.data.model.Etudiant
import com.isetr.tp5app.databinding.ItemEtudBinding

class EtudAdapter(
    private val etudiants: MutableList<Etudiant>,
    private val onItemClicked: (Etudiant) -> Unit,
    private val onItemLongClicked: (Etudiant) -> Boolean = { false },
    private val onItemUpdate: (Etudiant) -> Unit = {}      // nouveau
) : RecyclerView.Adapter<EtudAdapter.EtudiantViewHolder>() {

    inner class EtudiantViewHolder(private val binding: ItemEtudBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(etudiant: Etudiant) {
            binding.tvId.text = "ID : ${etudiant.identifiant}"
            binding.tvMail.text = "Mail : ${etudiant.email}"
            binding.tvClasse.text = "Classe : ${etudiant.classe}"

            binding.root.setOnClickListener { onItemClicked(etudiant) }
            binding.root.setOnLongClickListener { onItemLongClicked(etudiant) }
            binding.btnUpdate.setOnClickListener { onItemUpdate(etudiant) }   // nouveau
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val binding = ItemEtudBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EtudiantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        holder.bind(etudiants[position])
    }

    override fun getItemCount() = etudiants.size

    fun updateList(newList: List<Etudiant>) {
        etudiants.clear()
        etudiants.addAll(newList)
        notifyDataSetChanged()
    }
}