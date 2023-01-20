package com.example.notes.UI.Fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
;
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.Model.Notes
import com.example.notes.R
import com.example.notes.UI.Adapter.NotesAdapter
import com.example.notes.ViewModel.NotesViewModel
import com.example.notes.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding :FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter : NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val staggeredGridLayoutManager = (StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL))
        binding.rvShowAllNotes.layoutManager=staggeredGridLayoutManager
        viewModel.getNotes().observe(viewLifecycleOwner){
            oldMyNotes = it as ArrayList<Notes>
            adapter = NotesAdapter(requireContext(),it)
            binding.rvShowAllNotes.adapter= adapter
        }

        binding.filterAll.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner){
                oldMyNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),it)
                binding.rvShowAllNotes.adapter=adapter
            }
        }
        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner){
                oldMyNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),it)
                binding.rvShowAllNotes.adapter= adapter
            }
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner){
                oldMyNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),it)
                binding.rvShowAllNotes.adapter= adapter
            }
        }
        binding.filterLow.setOnClickListener {

            viewModel.getLowNotes().observe(viewLifecycleOwner){
                oldMyNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(),it)
                binding.rvShowAllNotes.adapter= adapter
            }
        }


        binding.fbCreate.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment2_to_createFragment2)
        }
        return binding.root
    }


//    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val item = menu.findItem(R.id.app_bar_search)
//        val searchView = item.actionView as SearchView
//        searchView.queryHint = "Enter Notes Here..."
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                notesFiltering(newText)
//                return true
//            }
//        })
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun notesFiltering(newText: String?) {
//        val newFilteredList = arrayListOf<Notes>()
//        for(i in oldMyNotes){
//            if(i.title.contains(newText!!) || i.subTitle.contains(newText)){
//                newFilteredList.add(i)
//            }
//        }
//        adapter.filtering(newFilteredList)
//    }

}