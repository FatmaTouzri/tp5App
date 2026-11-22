package com.isetr.tp5app.ui.ajout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.isetr.tp5app.R
import com.isetr.tp5app.databinding.FragmentAjoutBinding
import androidx.fragment.app.activityViewModels
import com.isetr.tp5app.EtudiantApplication
import com.isetr.tp5app.data.model.Etudiant
import com.isetr.tp5app.viewModel.EtudiantViewModel
import com.isetr.tp5app.viewModel.EtudiantViewModelFactory

class AjoutFragment : Fragment() {

    private var _binding: FragmentAjoutBinding? = null
    private val etudiantViewModel: EtudiantViewModel by activityViewModels {
        val application = requireActivity().application as EtudiantApplication
        EtudiantViewModelFactory(application.repository)
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEnregistrer.setOnClickListener {
            ajouterEtudiant()
        }
    }

    private fun ajouterEtudiant() {
        val id = binding.etIdentifiant.text.toString().toIntOrNull()
            ?: run {
                Toast.makeText(requireContext(), "Identifiant invalide", Toast.LENGTH_SHORT).show()
                return
            }
        val email = binding.etEmail.text.toString().trim()
        val classe = binding.etClasse.text.toString().trim()


        if (email.isEmpty() || classe.isEmpty()) {
            Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        val nouvelEtudiant = Etudiant(id, classe, email)
        etudiantViewModel.addEtudiant(nouvelEtudiant)

        Toast.makeText(requireContext(), "Étudiant ajouté", Toast.LENGTH_SHORT).show()
        binding.etIdentifiant.text.clear()
        binding.etEmail.text.clear()
        binding.etClasse.text.clear()


        findNavController().navigate(R.id.action_ajout_to_liste)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}