package com.isetr.tp5app.ui.liste

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.tp5app.databinding.ItemEtudBinding
import com.isetr.tp5app.model.Etudiant

class EtudAdapter(private val etudiants: MutableList<Etudiant>,
                  private val onItemClicked: (Etudiant) -> Unit,
                  private val onItemLongClicked : (Etudiant) -> Boolean = {false}
): RecyclerView.Adapter<EtudAdapter.EtudiantViewHolder>() {

    inner class EtudiantViewHolder(private val binding: ItemEtudBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(etudiant: Etudiant) {
            binding.tvId.text = "ID : ${etudiant.identifiant}"
            binding.tvMail.text = "Mail : ${etudiant.email}"
            binding.tvClasse.text = "Classe : ${etudiant.classe}"

            binding.root.setOnClickListener {
                onItemClicked(etudiant)
            }
            binding.root.setOnLongClickListener {
                onItemLongClicked(etudiant)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            EtudiantViewHolder {
        val binding = ItemEtudBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        return EtudiantViewHolder(binding)
    }
    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        holder.bind(etudiants[position])
    }
    override fun getItemCount() = etudiants.size

    fun trierLesDonnees(actionType: String) {
        when (actionType) {
            "SORT_NAME" -> {
                etudiants.sortBy { e -> e.identifiant }

            }
            "SORT_CLASS" -> {
                etudiants.sortBy { e -> e.classe }
            }
        }

        notifyDataSetChanged()
    }

    fun deleteEtudiant(etudiant : Etudiant){
        val index = etudiants.indexOf(etudiant)
        if(index != -1){
            etudiants.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateList(newList: List<Etudiant>) {
        etudiants.clear()
        etudiants.addAll(newList)
        notifyDataSetChanged()
    }
}