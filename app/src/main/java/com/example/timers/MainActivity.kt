package com.example.timers

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.time.AbstractLongTimeSource
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {
    var isRunning = false

    object RealtimeMonotonicTimeSource : AbstractLongTimeSource(DurationUnit.NANOSECONDS) {
        override fun read(): Long = SystemClock.elapsedRealtimeNanos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun handleStopWatchStart(view: View) {
        val stopWatchHours = findViewById<TextView>(R.id.stopW_hours)
        val stopWatchMins = findViewById<TextView>(R.id.stopW_mins)
        val stopWatchSecs = findViewById<TextView>(R.id.stopW_secs)

        val stopWatchButton = findViewById<TextView>(R.id.stopW_start)
        var elapsedHours = 0
        var elapsedMins = 0
        var elapsedSecs = 0
        var elapsedMillis = 0


        if (stopWatchButton.text == "Start") {
            stopWatchButton.text = "Stop"
            isRunning = true
            stopWatchButton.setBackgroundColor(getColor(R.color.red))
            elapsedSecs = (System.currentTimeMillis().toInt()) / 1000
            stopWatchSecs.text = elapsedSecs.toString()


                var elapsed: Duration = RealtimeMonotonicTimeSource.measureTime {
                    Thread.sleep(1000)
                }
                stopWatchSecs.text = elapsed.inWholeSeconds.toString()
                stopWatchMins.text = (elapsed / 60).inWholeMinutes.toString()
                stopWatchHours.text = (elapsed / 3600).inWholeHours.toString()


            /*while (stopWatchHours.text != "Start") {
                elapsedHours = 15
            }*/
            //elapsedHours = 15
            //stopWatchHours.text = elapsedHours.toString()
        } else {
            stopWatchButton.text = "Start"
            stopWatchButton.setBackgroundColor(getColor(R.color.brightTeal))
            elapsedHours += elapsedHours
        }

        //stopWatchHours.text = (System.currentTimeMillis() - elapsedHours).toString()
    }
}