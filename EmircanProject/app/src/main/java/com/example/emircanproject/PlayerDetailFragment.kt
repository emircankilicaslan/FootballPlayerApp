package com.example.emircanproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlayerDetailFragment : Fragment() {

    companion object {
        private const val PLAYER_NAME = "playerName"

        fun newInstance(playerName: String): PlayerDetailFragment {
            val args = Bundle()
            args.putString(PLAYER_NAME, playerName)
            val fragment = PlayerDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var playerNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player_detail, container, false)
        playerNameTextView = view.findViewById(R.id.playerNameTextView)

        // Argümanları al ve görünümde göster
        val playerName = arguments?.getString(PLAYER_NAME)
        playerNameTextView.text = playerName

        return view
    }
}
