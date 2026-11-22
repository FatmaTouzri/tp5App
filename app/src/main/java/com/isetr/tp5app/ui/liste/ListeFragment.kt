package com.isetr.tp5app.ui.liste

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.isetr.tp5app.databinding.FragmentListeBinding
import com.isetr.tp5app.data.model.Etudiant
import androidx.fragment.app.activityViewModels
import com.isetr.tp5app.viewModel.EtudiantViewModel
import androidx.navigation.fragment.findNavController
import com.isetr.tp5app.EtudiantApplication
import com.isetr.tp5app.R
import com.isetr.tp5app.viewModel.EtudiantViewModelFactory
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle


class ListeFragment : Fragment() {
    private lateinit var etudiantAdapter: EtudAdapter
    private lateinit var etudiantList: MutableList<Etudiant>
    private var _binding: FragmentListeBinding? = null
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
            etudiantViewModel.etudiantsSearchResults.observe(viewLifecycleOwner) { resultats ->
                etudiantAdapter.updateList(resultats)
            }

            requireActivity().addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.main, menu)

                    val searchItem = menu.findItem(R.id.action_search)
                    val searchView = searchItem.actionView as SearchView
                    searchView.queryHint = "Rechercher par classe"

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            etudiantViewModel.setSearchQuery(query.orEmpty())
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            etudiantViewModel.setSearchQuery(newText.orEmpty())
                            return true
                        }
                    })

                    searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                        override fun onMenuItemActionExpand(item: MenuItem): Boolean = true
                        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                            etudiantViewModel.setSearchQuery("")
                            return true
                        }
                    })
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        }
    }
    private fun confirmerSuppression(etudiant: Etudiant) {
        AlertDialog.Builder(requireContext())
            .setTitle("Supprimer l'étudiant")
            .setMessage("Voulez-vous vraiment supprimer ${etudiant.email} ?")
            .setPositiveButton("Supprimer") { _, _ ->
                etudiantViewModel.deleteEtudiant(etudiant)
                Toast.makeText(requireContext(), "Étudiant supprimé", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Annuler", null)
            .show()
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
            },
            onItemLongClicked = { etudiant ->
                confirmerSuppression(etudiant)
                true
            },
            onItemUpdate = { etudiant ->
                afficherDialogueModification(etudiant)
            }
        )
        recyclerView.adapter = etudiantAdapter
    }

    private fun afficherDialogueModification(etudiant: Etudiant) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_edit_details, null)


        val etEmail = dialogView.findViewById<EditText>(R.id.etDialogEmail)
        val etClasse = dialogView.findViewById<EditText>(R.id.etDialogClasse)


        etEmail.setText(etudiant.email)
        etClasse.setText(etudiant.classe)

        AlertDialog.Builder(requireContext())
            .setTitle("Modifier les détails de ${etudiant.identifiant}")
            .setView(dialogView)
            .setPositiveButton("Sauvegarder") { _, _ ->
                val nouvelEmail = etEmail.text.toString().trim()
                val nouvelleClasse = etClasse.text.toString().trim()

                if (nouvelEmail.isEmpty() || nouvelleClasse.isEmpty()) {
                    Toast.makeText(requireContext(), "Champs vides : modification annulée", Toast.LENGTH_SHORT).show()
                } else {
                    val etudiantModifie = etudiant.copy(email = nouvelEmail, classe = nouvelleClasse)
                    etudiantViewModel.updateEtudiant(etudiantModifie)
                    Toast.makeText(requireContext(), "Étudiant modifié", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }


}