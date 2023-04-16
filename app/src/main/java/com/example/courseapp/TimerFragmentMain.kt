package com.example.courseapp

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragmentMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerFragmentMain : Fragment() {
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

    private lateinit var roshanMinTimer: Chronometer
    private lateinit var roshanMaxTimer: Chronometer
    private lateinit var startRoshanBtn: Button
    private lateinit var restartRoshan: Button

    private lateinit var aegisTimer: Chronometer
    private lateinit var startAegisBtn: Button
    private lateinit var restartAegis: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_timer_main, container, false)

        startRoshanBtn = view.findViewById(R.id.pauseRoshan)
        restartRoshan = view.findViewById(R.id.restartRoshan)

        roshanMinTimer = view.findViewById(R.id.roshanMinTime)
        roshanMaxTimer = view.findViewById(R.id.roshanMaxTime)


        var minTimeWhenStopped: Long = MIN_ROSHAN_TIME
        var maxTimeWhenStopped: Long = MAX_ROSHAN_TIME

        var isPaused = true

//        roshanMinTimer.base = SystemClock.elapsedRealtime() + 480000
//        roshanMaxTimer.base = SystemClock.elapsedRealtime() + 660000



        roshanMinTimer.setOnChronometerTickListener {
            if (it.base <= SystemClock.elapsedRealtime())
                it.stop()
        }


        roshanMaxTimer.setOnChronometerTickListener {
            if (it.base <= SystemClock.elapsedRealtime())
                it.stop()

        }


        startRoshanBtn.setOnClickListener(){
            if (isPaused) {
                isPaused = false
                restartRoshan.visibility = View.VISIBLE
                startRoshanBtn.text = "Пауза"
                roshanMinTimer.base = SystemClock.elapsedRealtime() + minTimeWhenStopped
                roshanMaxTimer.base = SystemClock.elapsedRealtime() + maxTimeWhenStopped
                roshanMinTimer.start()
                roshanMaxTimer.start()
            }
            else {
                isPaused = true
                restartRoshan.visibility = View.VISIBLE
                startRoshanBtn.text = "Пуск"
                minTimeWhenStopped = roshanMinTimer.base - SystemClock.elapsedRealtime()
                maxTimeWhenStopped = roshanMaxTimer.base - SystemClock.elapsedRealtime()
                roshanMinTimer.stop()
                roshanMaxTimer.stop()
            }
        }


        restartRoshan.setOnClickListener() {
            isPaused = true
            startRoshanBtn.text = "Пуск"
            restartRoshan.visibility = View.INVISIBLE
            roshanMinTimer.base = SystemClock.elapsedRealtime() + MIN_ROSHAN_TIME
            roshanMaxTimer.base = SystemClock.elapsedRealtime() + MAX_ROSHAN_TIME

            minTimeWhenStopped = MIN_ROSHAN_TIME
            maxTimeWhenStopped = MAX_ROSHAN_TIME

            roshanMaxTimer.stop()
            roshanMinTimer.stop()

        }


        aegisTimer = view.findViewById(R.id.aegisTime)
        restartAegis = view.findViewById(R.id.restartAegis)
        startAegisBtn = view.findViewById(R.id.startAegis)

        var timeWhenStoppedAegis = AEGIS_TIME

        aegisTimer.setOnChronometerTickListener {
            if (it.base <= SystemClock.elapsedRealtime())
                it.stop()
        }

        startAegisBtn.setOnClickListener(){
            if(isPaused){
                isPaused = false
                restartAegis.visibility = View.VISIBLE
                startAegisBtn.text = "Пауза"
                aegisTimer.base = SystemClock.elapsedRealtime() + timeWhenStoppedAegis
                aegisTimer.start()
            }else{
                isPaused = true
                restartAegis.visibility = View.VISIBLE
                startAegisBtn.text = "Пуск"
                timeWhenStoppedAegis = aegisTimer.base - SystemClock.elapsedRealtime()
                aegisTimer.stop()
            }
        }

        restartAegis.setOnClickListener(){
            isPaused = true
            startAegisBtn.text = "Пуск"
            restartAegis.visibility = View.INVISIBLE
            aegisTimer.base = SystemClock.elapsedRealtime() + AEGIS_TIME
            timeWhenStoppedAegis = AEGIS_TIME
            aegisTimer.stop()
        }

        return view


    }

    companion object {
        const val SECOND: Long = 1000
        const val MINUTE: Long = 60* SECOND
        const val MIN_ROSHAN_TIME: Long = 8 * MINUTE
        const val MAX_ROSHAN_TIME: Long = 11 * MINUTE

        const val AEGIS_TIME: Long = 5 * MINUTE
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimerFragmentMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimerFragmentMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}