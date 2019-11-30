package com.meomo.meomocalculator

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        editGr.setOnClickListener {
            val gram: Double = getDouble(editGr.text.toString())
            val kg = gram * 0.001
            editKg.setText(kg.toString())
        }

        editKg.setOnClickListener {
            editGr.text = null
        }

        buttonCalculate.setOnClickListener {

            /***
             * get value
             */
            val rate: Double = getDouble(editRate.text.toString())
            val price: Double = getDouble(editPrice.text.toString())
            val discount: Double = getDouble(editDiscount.text.toString())
            val tax: Double = getDouble(editTax.text.toString())
            val kg: Double = getDouble(editKg.text.toString())
            val weightCharge: Double = getDouble(editWeightCharge.text.toString())
//            val discounted: Double = editDiscounted.text.toString().toDouble()
//            val gr: Double = editGr.text.toString().toDouble()





            val usvn: Double = getDouble(editUSVN.text.toString())
            val usShip: Double = getDouble(editUSShip.text.toString())

            val fee: Double = getDouble(editFee.text.toString())

            /**
             * Calculate
             */
            val rDiscounted = price * (100 - discount)*0.01
            val afterTaxed = price + price * tax *0.01
            val weightChargeResult = weightCharge * kg
            val result: Double = (afterTaxed + usvn + usShip + fee + weightChargeResult) * rate

            Log.i("afterTaxed", afterTaxed.toString())
            Log.i("usvn", usvn.toString())
            Log.i("usShip", usShip.toString())
            Log.i("fee", fee.toString())
            Log.i("weightChargeResult", fee.toString())

            /**
             * Calculate for compare
             */
            val usvbVND = usvn * rate
            val usShipVND = usShip * rate

            /**
             * setText
             */
            textDiscoutedResult.text = rDiscounted.toString()
            textAfterTaxedResult.text = afterTaxed.toString()
            textViewVNDResult1.text = usvbVND.toString()
            textViewVNDResult2.text = usShipVND.toString()

            /**
             * set Result
             */
            textResult.text = result.toLong().toString()
        }

        buttonCopy.setOnClickListener {
            val value: String = textResult.toString()
            Log.i("MainActivity", "Copy")
            Toast.makeText(this, value, Toast.LENGTH_LONG).show()
        }
    }

    private fun getDouble(string: String): Double {
        var result: Double? = string.toDoubleOrNull()
        if (result == null) result = 0.0
        return result
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
