package com.kolorfox.watertracker.ui.watertrackermain

import android.os.Bundle
import android.os.Handler
import android.widget.CalendarView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.model.Weight
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import kotlinx.android.synthetic.main.activity_set_weight.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class SetWeightsActivity : AppCompatActivity() {


    var weightkg: Int? = 0
    var kg: Int = 80
    var kgDouble: Int = 0
    var weight: String = "80"
    lateinit var db: WaterDatabase
    lateinit var widget: CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_weight)
        val c = Calendar.getInstance()
        val sdf2 = SimpleDateFormat("dd")
        weightkg = sdf2.format(c.getTime()).toInt()
        widget = findViewById(R.id.calendarView)
        getData()
        backActivity()
        saveWeight()

    }

    private fun saveWeight() {
        widget.setOnDateChangeListener { view, year, month, dayOfMonth ->
            weightkg = dayOfMonth
        }

        btn_save.setOnClickListener {
            val c = Calendar.getInstance()
            val sdf2 = SimpleDateFormat("dd")
            val currentDate2 = sdf2.format(c.getTime()).toInt()
            if (weightkg == currentDate2) {

                if (kgDouble == 0) {
                    weight = "$kg"
                } else {
                    weight = "$kg.$kgDouble"
                }
                if (kg == 200) {
                    weight = "200"
                }
                ShareReference.setWeights(weight, this)
                val check = ShareReference.getCheckDailyGold(this)
                val y = weight.toFloat()
                val ml = y / 0.03
                val twoDForm = DecimalFormat("#")
                val xx = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
                if (check == true) {
                    ShareReference.setGoalDrink("$xx", this)
                }
                val month = weightkg!!.toInt()
                db.updateWeightMonth(Weight(month, y))

            } else {
                if (kgDouble == 0) {
                    weight = "$kg"
                } else {
                    weight = "$kg.$kgDouble"
                }
                if (kg == 200) {
                    weight = "200"
                }
                val y = weight.toFloat()
                val month = weightkg!!.toInt()
                db.updateWeightMonth(Weight(month, y))
            }
            finish()
        }
    }

    private fun backActivity() {
        back.setOnClickListener {
            finish()
        }
    }


    private fun getData() {
        db = WaterDatabase(this)
        val weight = intent.getStringExtra("weight")
        val weightFloat = weight.toFloat()
        kg = weightFloat.toInt()
        kgDouble = (((weightFloat - kg) * 10).roundToInt())


        number_picker2_weight.value = kgDouble
        number_picker_weighr.value = kg

        number_picker_weighr.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener,
            com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {

            }

            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                if (oldVal != newVal) {
                    kg = newVal

                } else {
                    kg = oldVal

                }
            }


        })
        val h = Handler()
        h.post(
            object : Runnable {
                override fun run() {
                    if (kg == 200) {
                        number_picker2_weight.isClickable = false
                        number_picker2_weight.maxValue = 0
                        number_picker2_weight.minValue = 0
                    } else {
                        number_picker2_weight.isClickable = true
                        number_picker2_weight.maxValue = 9
                        number_picker2_weight.minValue = 0
                    }
                    h.postDelayed(this, 300)

                }
            })


        number_picker2_weight.setOnValueChangedListener(object :
            NumberPicker.OnValueChangeListener,
            com.shawnlin.numberpicker.NumberPicker.OnValueChangeListener {
            override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onValueChange(
                picker: com.shawnlin.numberpicker.NumberPicker?,
                oldVal: Int,
                newVal: Int
            ) {
                if (oldVal != newVal) {
                    kgDouble = newVal
                } else {
                    kgDouble = oldVal
                }
            }


        })

    }
}




