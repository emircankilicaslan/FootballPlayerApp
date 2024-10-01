package com.example.emircanproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class PlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)

        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val editText = view.findViewById<TextInputEditText>(R.id.editText)

        submitButton.setOnClickListener {
            val playerName = editText.text.toString()
            val playerDetailFragment = PlayerDetailFragment.newInstance(playerName)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, playerDetailFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}

