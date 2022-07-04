package com.example.todogruppo.Focus

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.todogruppo.databinding.FragmentFocusBinding
import java.util.*


class FocusFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentFocusBinding

    //dichiarazione variabili
    private lateinit var mEditTextInput: EditText
    private lateinit var mTextViewCountDown: TextView
    private lateinit var mButtonSet: Button
    private lateinit var mButtonStartPause: Button
    private lateinit var mButtonReset: Button
    private lateinit var mCountDownTimer: CountDownTimer
    private var mTimerRunning = false
    private var mStartTimeInMillis: Long = 0
    private var mTimeLeftInMillis: Long = 0
    private var mEndTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_focus, container, false)
        binding = FragmentFocusBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inizializzo variabili
        mEditTextInput = binding.editTextInput
        mTextViewCountDown = binding.textViewCountdown
        mButtonSet = binding.buttonSet
        mButtonStartPause = binding.buttonStartPause
        mButtonReset = binding.buttonReset

        //bottone per settare i minuti
        mButtonSet.setOnClickListener {
            val input = mEditTextInput.getText().toString()
            if (input.length == 0) {
                Log.d("error", "campo vuoto")
            }

            val millisInput = input.toLong() * 60000
            if (millisInput == 0L) {
                Log.d("error", "inserisci un campo positivo")
            }

            setTime(millisInput)
            mEditTextInput.setText("")
        }

        //bottone start/pause
        mButtonStartPause.setOnClickListener {
            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        //bottone reset
        mButtonReset.setOnClickListener {
            resetTimer()
        }
    }

    //setta il tempo
    private fun setTime(milliseconds: Long) {
        mStartTimeInMillis = milliseconds
        resetTimer()
    }

    //avvia il timer
    private fun startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                updateWatchInterface()
            }
        }.start()

        mTimerRunning = true
        updateWatchInterface()
    }

    //pausa timer
    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        updateWatchInterface()
    }

    //resetta il timer
    private fun resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis
        updateCountDownText()
        updateWatchInterface()
    }

    //aggiorna il contatore
    private fun updateCountDownText() {
        val hours = (mTimeLeftInMillis / 1000).toInt() / 3600
        val minutes = (mTimeLeftInMillis / 1000 % 3600).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String
        timeLeftFormatted = if (hours > 0) {
            String.format(
                Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, seconds
            )
        } else {
            String.format(
                Locale.getDefault(),
                "%02d:%02d", minutes, seconds
            )
        }
        mTextViewCountDown!!.text = timeLeftFormatted
    }

    //aggiorna l'interfaccia
    private fun updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput!!.visibility = View.INVISIBLE
            mButtonSet!!.visibility = View.INVISIBLE
            mButtonReset!!.visibility = View.INVISIBLE
            mButtonStartPause!!.text = "PAUSE"
        } else {
            mEditTextInput!!.visibility = View.VISIBLE
            mButtonSet!!.visibility = View.VISIBLE
            mButtonStartPause!!.text = "START"
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause!!.visibility = View.INVISIBLE
            } else {
                mButtonStartPause!!.visibility = View.VISIBLE
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset!!.visibility = View.VISIBLE
            } else {
                mButtonReset!!.visibility = View.INVISIBLE
            }
        }
    }

    //stoppa contatore
    override fun onStop() {
        super.onStop()

        val prefs = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong("startTimeInMillis", mStartTimeInMillis)
        editor.putLong("millisLeft", mTimeLeftInMillis)
        editor.putBoolean("timerRunning", mTimerRunning)
        editor.putLong("endTime", mEndTime)
        editor.apply()
    }

    //avvia contatore
    override fun onStart() {
        super.onStart()
        val prefs = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000)
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis)
        mTimerRunning = prefs.getBoolean("timerRunning", false)
        updateCountDownText()
        updateWatchInterface()
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0)
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis()
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0
                mTimerRunning = false
                updateCountDownText()
                updateWatchInterface()
            } else {
                startTimer()
            }
        }
    }

}