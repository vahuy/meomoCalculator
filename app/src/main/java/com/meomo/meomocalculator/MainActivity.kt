package com.meomo.meomocalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.DecimalFormat
import java.text.NumberFormat


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
            editKg.text = null
            editGr.text = null
        }

        editFeePercent.setOnClickListener {
            editFee.text = null
        }

        buttonClear.setOnClickListener {
            editPrice.text = null
            editDiscount.text = null
            editGr.text = null
            editKg.setText("0.1")
            editUSShip.text = null
            editFeePercent.text = null
            editFee.setText("5")
            textDiscoutedResult.text = "0"
            textAfterTaxedResult.text = "0"
            textViewUSVNResult.text = "0"
            textViewVNDResult1.text = "0"
            textViewVNDResult2.text = "0"
            textResult.text = "0"
        }

        textResult.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("result", textResult.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
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

            val usShip: Double = getDouble(editUSShip.text.toString())

            var fee: Double = getDouble(editFee.text.toString())

            /**
             * Calculate
             */
            val rDiscounted = price * (100 - discount) * 0.01
            val feePercent: Double = getDouble(editFeePercent.text.toString()) * rDiscounted * 0.01
            if (feePercent > 0.0) {
                fee = feePercent
            }
            val afterTaxed = rDiscounted + rDiscounted * tax * 0.01
            val weightChargeResult = weightCharge * kg
            val result: Double = (afterTaxed + usShip + fee + weightChargeResult) * rate

            Log.i("afterTaxed", afterTaxed.toString())
            Log.i("usShip", usShip.toString())
            Log.i("fee", fee.toString())
            Log.i("weightChargeResult", fee.toString())

            /**
             * Calculate for compare
             */
            val usShipVND = usShip * rate
            val usvbVND = weightChargeResult * rate
            /**
             * setText
             */
            textDiscoutedResult.text = formatNumberWithDecimal(rDiscounted)
            textAfterTaxedResult.text = formatNumberWithDecimal(afterTaxed)
            textViewVNDResult1.text = formatNumber(usvbVND)
            textViewVNDResult2.text = formatNumber(usShipVND)
            textViewUSVNResult.text = formatNumberWithDecimal(weightChargeResult)
            /**
             * set Result
             */
            textResult.text = formatNumber(result)
        }

        buttonCopy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("result", textResult.text.toString())
            clipboard.setPrimaryClip(clip)
        }
    }

    /**
     * Return x,xxx,xxx format
     */
    private fun formatNumber(stringNumber: Double): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(stringNumber)
    }

    /**
     * Return x,xxx.xx format
     */
    private fun formatNumberWithDecimal(stringNumber: Double): String {
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        return formatter.format(stringNumber)
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
