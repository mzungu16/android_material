package com.gleb.android_material.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gleb.android_material.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<TextInputEditText>(R.id.input_edit_text)
        view.findViewById<TextInputLayout>(R.id.input_layout).setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${editText.text.toString()}")
            })
        }
        with(view){
            findViewById<TextView>(R.id.bottom_sheet_description_header).text = param1
            findViewById<TextView>(R.id.bottom_sheet_description).text = param2
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?, param2: String?) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}