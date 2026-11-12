package com.isetr.tp5app.ui.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isetr.tp5app.databinding.FragmentListeBinding
import com.isetr.tp5app.model.Etudiant
import androidx.fragment.app.activityViewModels
import com.isetr.tp5app.viewModel.EtudiantViewModel
import androidx.navigation.fragment.findNavController
import com.isetr.tp5app.R

class ListeFragment : Fragment() {
    private lateinit var etudiantAdapter: EtudAdapter
    private lateinit var etudiantList: MutableList<Etudiant>
    private var _binding: FragmentListeBinding? = null
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textListe
        textView.text = "Liste des Etudiants"
         return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillRecyclerView()

        etudiantViewModel.etudiants.observe(viewLifecycleOwner) { nouvelleListe ->
            etudiantAdapter.updateList(nouvelleListe)
        }
    }

    private fun fillRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        etudiantAdapter = EtudAdapter(
            mutableListOf(),
            onItemClicked = { etudiant ->
                etudiantViewModel.selectEtudiant(etudiant)
                findNavController().navigate(R.id.action_liste_to_detail)
            }
        )
        recyclerView.adapter = etudiantAdapter
    }
}