package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn0.setOnClickListener { appendOnClick(true, "0") }
        btn1.setOnClickListener { appendOnClick(true, "1") }
        btn2.setOnClickListener { appendOnClick(true, "2") }
        btn3.setOnClickListener { appendOnClick(true, "3") }
        btn4.setOnClickListener { appendOnClick(true, "4") }
        btn5.setOnClickListener { appendOnClick(true, "5") }
        btn6.setOnClickListener { appendOnClick(true, "6") }
        btn7.setOnClickListener { appendOnClick(true, "7") }
        btn8.setOnClickListener { appendOnClick(true, "8") }
        btn9.setOnClickListener { appendOnClick(true, "9") }
        btnDot.setOnClickListener { appendOnClick(true, ".") }

        btnPlus.setOnClickListener { appendOnClick(false, "+") }
        btnMinus.setOnClickListener { appendOnClick(false, "-") }
        btnMultiply.setOnClickListener { appendOnClick(false, "*") }
        btnDivide.setOnClickListener { appendOnClick(false, "/") }

        btnClear.setOnClickListener {
            clear()
        }

        btnEqual.setOnClickListener {
            calculate()
            isCalculated = true
        }


    }
    var isCalculated=false

    private fun appendOnClick(clear: Boolean, string: String) {
        if (clear) {
            output.text = ""
            input.append(string)
        } else {
            if (isCalculated){
                input.text = ""
                isCalculated = false
            }
            input.append(output.text)
            input.append(string)
            output.text = ""
        }
    }

    private fun clear() {
        isCalculated = false
        input.text = ""
        output.text = ""

    }
    private fun calculateMathLogic(input:String):Double{
        try {
            if (input.indexOfAny(charArrayOf('-', '+', '*', '/')) < 0) {
                return input.toDouble()
            }
            var result: Double = 0.0

            when {
                input.indexOf("+") > 0 -> {
                    var splitByPlus = input.split('+')
                    result = calculateMathLogic(splitByPlus[0]) + calculateMathLogic(splitByPlus[1])
                }
                input.indexOf("-") > 0 -> {
                    var splitByMinus = input.split('-')
                    result = calculateMathLogic(splitByMinus[0]) - calculateMathLogic(splitByMinus[1])
                }
                input.indexOf("*") > 0 -> {
                    var splitByMult = input.split('*')
                    result = calculateMathLogic(splitByMult[0]) * calculateMathLogic(splitByMult[1])
                }
                input.indexOf("/") > 0 -> {
                    var splitByDiv = input.split('/')
                    var right = calculateMathLogic(splitByDiv[1])
                    if(right == 0.0){
                        throw Exception("Divided by zero")
                    }
                    result = calculateMathLogic(splitByDiv[0]) / right
                }
            }
            return result
        }catch (e: Exception){
            throw Exception("Please write correct expression")
        }
    }

    private fun calculate() {
        try {
            output.text = calculateMathLogic(input.text.toString()).toString()

        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }
}