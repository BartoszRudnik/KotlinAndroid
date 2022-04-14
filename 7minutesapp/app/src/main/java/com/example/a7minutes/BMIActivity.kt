package com.example.a7minutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutes.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if (currentVisibleView == METRIC_UNITS_VIEW) {
                if (validateMetricUnits()) {
                    displayBMIResult(calculateMetricUnits())
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                if (validateUsUnits()) {
                    displayBMIResult(calculateUsUnits())
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun calculateMetricUnits(): Float {
        val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
        val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

        return weight / (height * height)
    }

    private  fun calculateUsUnits(): Float {
        val height: Float = binding?.etMetricUnitHeightInch?.text.toString().toFloat() + binding?.etMetricUnitHeightFeet?.text.toString().toFloat() * 12
        val weight: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

        return weight / (height * height)
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.tilMetricUnitHeightFeet?.visibility = View.GONE

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW

        binding?.tilMetricUnitHeight?.visibility = View.GONE
        binding?.tilMetricUnitWeight?.visibility = View.GONE

        binding?.etMetricUnitHeightFeet?.text!!.clear()
        binding?.etMetricUnitHeightInch?.text!!.clear()

        binding?.tilMetricUnitHeightFeet?.visibility = View.VISIBLE

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float) {
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription =
                "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription =
                "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }


        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricUnitHeightFeet?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitHeightInch?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
}