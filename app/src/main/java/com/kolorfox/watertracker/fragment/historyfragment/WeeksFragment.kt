package com.kolorfox.watertracker.fragment.historyfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter

import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.WaterDatabase

/**
 * A simple [Fragment] subclass.
 */
class WeeksFragment : Fragment() {
    lateinit var tvWater: TextView
    lateinit var tvCompletes: TextView
    lateinit var chartWeek: BarChart
    lateinit var tvTBC: TextView
    var textChart: String = ""
    lateinit var waterDatabase:WaterDatabase
    lateinit var listHistory: ArrayList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =  inflater.inflate(R.layout.fragment_weeks, container, false)

        tvWater = view.findViewById(R.id.tv_ml_weeks)
        tvCompletes = view.findViewById(R.id.tv_completions)
        tvTBC = view.findViewById(R.id.tv_tbc)
        chartWeek = view.findViewById(R.id.barChart_weeks)

        getDataChart()
        return view
    }

    private fun getDataChart() {

        waterDatabase = WaterDatabase(context!!)

        val hai = waterDatabase.getWaterDay(2)
        val ba = waterDatabase.getWaterDay(3)
        val tu = waterDatabase.getWaterDay(4)
        val nam = waterDatabase.getWaterDay(5)
        val sau = waterDatabase.getWaterDay(6)
        val bay = waterDatabase.getWaterDay(7)
        val cn = waterDatabase.getWaterDay(8)
//        val x = nam.waterdrinkCompletion
//        val y = sau.waterdrinkCompletion


        val haiml = hai.waterdrink
        val baml = ba.waterdrink
        val tuml = tu.waterdrink
        val namml = nam.waterdrink
        val sauml = sau.waterdrink
        val bayml = bay.waterdrink
        val cnml = cn.waterdrink
        listHistory = ArrayList()
        listHistory.add(haiml!!)
        listHistory.add(baml!!)
        listHistory.add(tuml!!)
        listHistory.add(namml!!)
        listHistory.add(sauml!!)
        listHistory.add(bayml!!)
        listHistory.add(cnml!!)

        var count = 7
        for (i in 0..6) {
            if (listHistory[i] == 0) {
                count--
            }
        }
        val haicount = hai.waterDrinkCount
        val bacount = ba.waterDrinkCount
        val tucount = tu.waterDrinkCount
        val namcount = nam.waterDrinkCount
        val saucount = sau.waterDrinkCount
        val baycount = bay.waterDrinkCount
        val cncount = cn.waterDrinkCount

        val haiCom = hai.waterdrinkCompletion
        val baCom = ba.waterdrinkCompletion
        val tuCom = tu.waterdrinkCompletion
        val namCom = nam.waterdrinkCompletion
        val sauCom = sau.waterdrinkCompletion
        val bayCom = bay.waterdrinkCompletion
        val cnCom = cn.waterdrinkCompletion
        if (count != 0) {

            val countTBC =
                (haicount + bacount + tucount + namcount + saucount + baycount + cncount) / count
            val waterTBC = (haiml + baml + tuml + namml + sauml + bayml + cnml) / count
            val completeTBC =
                (haiCom!! + baCom!! + tuCom!! + namCom!! + sauCom!! + bayCom!! + cnCom!!) / count
            tvTBC.text = "$countTBC/Day"
            tvWater.text = "$waterTBC ml"
            if (completeTBC > 0) {
                tvCompletes.text = "$completeTBC%"
            } else {
                tvCompletes.text = "0%"
            }
            textChart = "Average completion (%)"
        } else {
            tvTBC.text = "0/Day"
            tvWater.text = "0 ml"
            tvCompletes.text = "0 %"
            textChart = "Average completion (%)"
        }


        chartWeek.setScaleEnabled(false)
        chartWeek.isDragEnabled = true
        chartWeek.description.isEnabled = false
        chartWeek.setDrawBarShadow(false)
        chartWeek.setDrawValueAboveBar(false)
        chartWeek.setPinchZoom(false)
        chartWeek.setDrawGridBackground(false)
        chartWeek.getAxisLeft().setStartAtZero(true)
        chartWeek.getAxisRight().setEnabled(false)

        val barEntries: ArrayList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(0.toFloat(), hai.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(1.toFloat(), ba.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(2.toFloat(), tu.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(3.toFloat(), nam.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(4.toFloat(), sau.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(5.toFloat(), bay.waterdrinkCompletion!!.toFloat()))
        barEntries.add(BarEntry(6.toFloat(), cn.waterdrinkCompletion!!.toFloat()))

        val barDataSet = BarDataSet(barEntries, textChart)
        val data = BarData(barDataSet)
        data.barWidth = 0.5f
        data.setDrawValues(false)
//        setData()
        val weeks = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sar", "Sun")
        chartWeek.data = data
        val xAxis: XAxis = chartWeek.xAxis
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1.toFloat()
        chartWeek.data.isHighlightEnabled = false
        chartWeek.invalidate()
        chartWeek.getXAxis().setDrawGridLines(false)
        chartWeek.getAxisLeft().setDrawGridLines(true)
        chartWeek.getAxisRight().setDrawGridLines(true)


    }


    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }
}


