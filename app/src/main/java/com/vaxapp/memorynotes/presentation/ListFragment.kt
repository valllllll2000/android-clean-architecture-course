package com.vaxapp.memorynotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaxapp.memorynotes.R
import com.vaxapp.memorynotes.framework.ListViewModel

class ListFragment : Fragment(), ListAction {

    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel
    private lateinit var loadingView: ProgressBar
    private lateinit var notesListView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingView = view.findViewById(R.id.loadingView)
        notesListView = view.findViewById(R.id.notesListView)
        notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }
        view.findViewById<View>(R.id.addNote).setOnClickListener {
            goToNoteDetails()
        }

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { noteList ->
            loadingView.visibility = View.GONE
            notesListAdapter.updateNotes(noteList.sortedBy { it.updateTime })
        }
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action: NavDirections = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        Navigation.findNavController(requireView().findViewById(R.id.notesListView)).navigate(action)
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }
}
