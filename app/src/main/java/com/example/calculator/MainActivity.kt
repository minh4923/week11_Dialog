package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.calculator.data.constants.State

class MainActivity : ComponentActivity() {
    private var operation : Char? = null
    private var result : Int = 0
    private lateinit var tvResult: TextView
    private var state: State = State.INITIALIZING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        tvResult = findViewById(R.id.textView)

        findViewById<Button>(R.id.button0).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button1).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button2).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button3).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button4).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button5).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button6).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button7).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button8).setOnClickListener { view -> numberButtonOnClick(view) }
        findViewById<Button>(R.id.button9).setOnClickListener { view -> numberButtonOnClick(view) }

        findViewById<Button>(R.id.button_c).setOnClickListener{_ -> clearButtonOnClick()}
        findViewById<Button>(R.id.button_ce).setOnClickListener{_ -> clearEntryButtonOnClick()}

        findViewById<Button>(R.id.button_plus).setOnClickListener{view -> operationButtonOnClick(view)}
        findViewById<Button>(R.id.button_minus).setOnClickListener{view -> operationButtonOnClick(view)}
        findViewById<Button>(R.id.button_mult).setOnClickListener{view -> operationButtonOnClick(view)}
        findViewById<Button>(R.id.button_divide).setOnClickListener{view -> operationButtonOnClick(view)}

        findViewById<Button>(R.id.button_equal).setOnClickListener{_ -> equalButtonOnClick()}
    }

    private fun numberButtonOnClick(view: View) {
        val num: Int = when (view.id) {
            R.id.button0 -> 0;
            R.id.button1 -> 1;
            R.id.button2 -> 2;
            R.id.button3 -> 3;
            R.id.button4 -> 4;
            R.id.button5 -> 5;
            R.id.button6 -> 6;
            R.id.button7 -> 7;
            R.id.button8 -> 8;
            R.id.button9 -> 9;
            else -> 0;
        }

        if (state == State.INPUTTING) {
            tvResult.text = getString(R.string.tv_result_text, tvResult.text, num)
        } else if (state == State.INITIALIZING || state == State.CALCULATING) {
            tvResult.text = num.toString()
        }
        state = State.INPUTTING
    }

    private fun clearButtonOnClick() {
        operation = null
        result = 0
        tvResult.text = "0"
        state = State.INITIALIZING
    }

    private fun clearEntryButtonOnClick() {
        if (state == State.CALCULATING) {
            operation = null
        }
        state = State.INITIALIZING
        tvResult.text = "0"
    }

    private fun operationButtonOnClick(view: View) {
        if (state == State.INITIALIZING || state == State.CALCULATING) return

        calculating()

        operation = when(view.id) {
            R.id.button_plus -> '+'
            R.id.button_minus -> '-'
            R.id.button_mult -> '*'
            R.id.button_divide -> '/'
            else -> null
        }

        state = State.CALCULATING
    }

    private fun equalButtonOnClick() {
        calculating()

        operation = null
        state = State.INITIALIZING
        result = 0
    }

    private fun calculating() {
        val currNum : Int = tvResult.text.toString().toIntOrNull() ?: return

        result = when(operation) {
            '+' -> result + currNum
            '-' -> result - currNum
            '*' -> result * currNum
            '/' -> result / currNum
            else -> currNum
        }

        tvResult.text = result.toString()
    }
}