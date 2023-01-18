package com.example.notes.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notes.R
import com.example.notes.UI.Adapter.NotesAdapter
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding :com.example.notes.databinding.FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModel.getNotes().observe(viewLifecycleOwner){
            binding.rvShowAllNotes.layoutManager = GridLayoutManager(requireContext(),2)
            binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
        }
        binding.fbCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment2_to_createFragment2)
        }
        return binding.root
    }
}