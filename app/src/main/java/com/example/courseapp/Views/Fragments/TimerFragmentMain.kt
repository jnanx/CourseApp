package com.example.courseapp.Views.Fragments

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import com.example.courseapp.R

class TimerFragmentMain : Fragment() {

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

        var isPausedRoshan = true
        var isPausedAegis = true

        roshanMinTimer.setOnChronometerTickListener {
            if (it.base <= SystemClock.elapsedRealtime())
                it.stop()
        }


        roshanMaxTimer.setOnChronometerTickListener {
            if (it.base <= SystemClock.elapsedRealtime())
                it.stop()

        }


        startRoshanBtn.setOnClickListener(){
            if (isPausedRoshan) {
                isPausedRoshan = false
                restartRoshan.visibility = View.VISIBLE
                startRoshanBtn.text = getString(R.string.pauseText)
                roshanMinTimer.base = SystemClock.elapsedRealtime() + minTimeWhenStopped
                roshanMaxTimer.base = SystemClock.elapsedRealtime() + maxTimeWhenStopped
                roshanMinTimer.start()
                roshanMaxTimer.start()
            }
            else {
                isPausedRoshan = true
                restartRoshan.visibility = View.VISIBLE
                startRoshanBtn.text = getString(R.string.playText)
                minTimeWhenStopped = roshanMinTimer.base - SystemClock.elapsedRealtime()
                maxTimeWhenStopped = roshanMaxTimer.base - SystemClock.elapsedRealtime()
                roshanMinTimer.stop()
                roshanMaxTimer.stop()
            }
        }


        restartRoshan.setOnClickListener() {
            isPausedRoshan = true
            startRoshanBtn.text = getString(R.string.playText)
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
            if(isPausedAegis){
                isPausedAegis = false
                restartAegis.visibility = View.VISIBLE
                startAegisBtn.text = getString(R.string.pauseText)
                aegisTimer.base = SystemClock.elapsedRealtime() + timeWhenStoppedAegis
                aegisTimer.start()
            }else{
                isPausedAegis = true
                restartAegis.visibility = View.VISIBLE
                startAegisBtn.text = getString(R.string.playText)
                timeWhenStoppedAegis = aegisTimer.base - SystemClock.elapsedRealtime()
                aegisTimer.stop()
            }
        }

        restartAegis.setOnClickListener(){
            isPausedAegis = true
            startAegisBtn.text = getString(R.string.playText)
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
    }
}