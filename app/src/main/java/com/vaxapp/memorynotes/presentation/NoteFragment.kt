package com.vaxapp.memorynotes.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.vaxapp.core.data.Note
import com.vaxapp.memorynotes.R
import com.vaxapp.memorynotes.framework.NoteViewModel

class NoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var titleEdit: EditText
    private lateinit var contentEdit: EditText
    private var currentNote: Note = Note("", "", 0L, 0L)
    private var noteId = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenuProvider()
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        titleEdit = editText(view, R.id.titleView)
        contentEdit = editText(view, R.id.contentView)
        view.findViewById<View>(R.id.checkButton).setOnClickListener {
            if (containsText(titleEdit) || containsText(contentEdit)) {
                val time = System.currentTimeMillis()
                currentNote.title = titleEdit.text.toString()
                currentNote.content = contentEdit.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun addMenuProvider() {
        // Add options menu to the toolbar instead of setHasOptionsMenu
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.delete -> {
                        if (context != null && noteId != 0L) {
                            AlertDialog.Builder(context!!)
                                .setTitle("Delete note")
                                .setMessage("are you sure")
                                .setPositiveButton("Yes") { _, _ ->
                                    viewModel.deleteNote(currentNote)
                                }
                                .setNegativeButton("Cancel") { _, _ ->
                                }
                                .create()
                                .show()
                        }

                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(titleEdit).popBackStack()
            } else {
                Toast.makeText(
                    activity,
                    "Something went wrong, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            note?.let {
                currentNote = it
                titleEdit.setText(it.title, TextView.BufferType.EDITABLE)
                contentEdit.setText(it.content, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun containsText(editText: EditText) = editText.text.toString() != ""

    private fun editText(view: View, resId: Int): EditText = view.findViewById(resId)

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(titleEdit.windowToken, 0)
    }
}
