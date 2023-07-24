package com.example.courseapp.Views.Fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.courseapp.Adapter.PlayerAdapter
import com.example.courseapp.R
import com.example.courseapp.Room.MatchDataDatabase
import com.example.courseapp.VeiwModels.SearchFragmentViewModel
import com.example.courseapp.SearchMode
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private val viewModel: SearchFragmentViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)


        val allinfocard = view.findViewById<CardView>(R.id.allinfo)
        val gameId = view.findViewById<EditText>(R.id.gameIdText)
        val direText = view.findViewById<TextView>(R.id.dire)
        val radiantText = view.findViewById<TextView>(R.id.radiant)
        val direWLText = view.findViewById<TextView>(R.id.direWL)
        val radiantWLText = view.findViewById<TextView>(R.id.radiantWL)
        val durationText = view.findViewById<TextView>(R.id.durationText)
        val direScoreText = view.findViewById<TextView>(R.id.direScore)
        val radiantScoreText = view.findViewById<TextView>(R.id.radiantScore)

        val radiantRecyclerView = view.findViewById<RecyclerView>(R.id.radiantHeroList)
        val direRecyclerView = view.findViewById<RecyclerView>(R.id.direHeroList)

        val db = Room.databaseBuilder(
            requireContext(),
            MatchDataDatabase::class.java, "matches"
        ).build()

        viewModel.setDb(db)

        view.findViewById<Button>(R.id.findByIdButton).setOnClickListener {

            if (gameId.text.isNullOrBlank()) {
                Toast.makeText(activity, "Введите ID игры", Toast.LENGTH_SHORT).show()
            } else {
//                allinfocard.visibility = View.VISIBLE
                Log.d("DOTAAPI", "Click called")

                val matchId = gameId.text.toString().toLongOrNull() ?: 0

                viewModel.getMatchData(matchId)

            }
        }

        radiantRecyclerView.layoutManager = LinearLayoutManager(context)
        radiantRecyclerView.setHasFixedSize(false)
        val radiantAdapter = PlayerAdapter(viewModel.radiantPlayersStateFlow)
        radiantRecyclerView.adapter = radiantAdapter

        lifecycleScope.launch {
            viewModel.radiantPlayersStateFlow.collect {
                radiantAdapter.notifyDataSetChanged()
            }
        }

        direRecyclerView.layoutManager = LinearLayoutManager(context)
        direRecyclerView.setHasFixedSize(false)
        val direAdapter = PlayerAdapter(viewModel.direPlayersStateFlow)
        direRecyclerView.adapter = direAdapter

        lifecycleScope.launch{
            viewModel.direPlayersStateFlow.collect{
                direAdapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch{
            viewModel.matchDataStateFlow.collect{
                if (it != null){
                    it.duration?.let { duration ->
                        durationText.text =
                            "${resources.getString(R.string.duration)}: " +
                                    if (duration / 60 < 10) "0${duration / 60}" else "${duration / 60}" + ":" + if (duration % 60 < 10) "0${duration % 60}" else "${duration % 60}"
                    }
                    direScoreText.text = "${it.dire_score}"
                    radiantScoreText.text = "${it.radiant_score}"
                    it.radiant_win?.let { win ->
                        if (!win) {
                            direText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantText.setTextColor(Color.parseColor("#8C3131"))
                            radiantWLText.text = resources.getString(R.string.lose)
                            direWLText.text = resources.getString(R.string.win)
                            direScoreText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantScoreText.setTextColor(Color.parseColor("#8C3131"))
                        } else {
                            direText.setTextColor(Color.parseColor("#8C3131"))
                            radiantText.setTextColor(Color.parseColor("#4D8B31"))
                            radiantWLText.text = resources.getString(R.string.win)
                            direWLText.text = resources.getString(R.string.lose)
                            direScoreText.setTextColor(Color.parseColor("#8C3131"))
                            radiantScoreText.setTextColor(Color.parseColor("#4D8B31"))
                        }
                    }
                    allinfocard.visibility = View.VISIBLE
                //txtUser.movementMethod = ScrollingMovementMethod()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.searchModeStateFlow.collect{
                val str: String = when(it){

                    SearchMode.NONE -> "NONE"
                    SearchMode.ERROR -> "ERROR"
                    SearchMode.LOADING -> "LOADING"
                    SearchMode.LOADED -> "LOADED"
                }
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
            }
        }

        return view

    }
}


