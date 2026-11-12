package com.isetr.tp5app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.tp5app.databinding.FragmentDetailBinding
import com.isetr.tp5app.viewModel.EtudiantViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val etudiantViewModel: EtudiantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etudiantViewModel.etudiantSelectionne.observe(viewLifecycleOwner) { etudiant ->
            if (etudiant != null) {
                binding.tvDetailId.text = "ID : ${etudiant.identifiant}"
                binding.tvDetailEmail.text = "Mail : ${etudiant.email}"
                binding.tvDetailClasse.text = "Classe : ${etudiant.classe}"

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}