package com.example.notes.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

        val staggeredGridLayoutManager = (StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL))
        binding.rvShowAllNotes.layoutManager=staggeredGridLayoutManager

        viewModel.getNotes().observe(viewLifecycleOwner){
            binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
        }

        binding.filterAll.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner){
                binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
            }
        }
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner){
                binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
            }
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner){
                binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
            }
        }
        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner){
                binding.rvShowAllNotes.adapter=NotesAdapter(requireContext(),it)
            }
        }


        binding.fbCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment2_to_createFragment2)
        }
        return binding.root
    }
}