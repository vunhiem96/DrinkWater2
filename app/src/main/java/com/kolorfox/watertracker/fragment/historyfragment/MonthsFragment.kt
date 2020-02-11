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


class MonthsFragment : Fragment() {

    lateinit var listHistoryMonth: ArrayList<Int>
    lateinit var chartMonths: BarChart
    lateinit var waterDatabase: WaterDatabase
    lateinit var tvCompletesMonth: TextView
    lateinit var tvWaterMonth: TextView
    lateinit var tvTBCmonth: TextView
    var textChartMonth: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.fragment_months, container, false)

        chartMonths = view.findViewById(R.id.barChart_month)
        tvWaterMonth = view.findViewById(R.id.tv_ml_months)
        tvCompletesMonth = view.findViewById(R.id.tv_completions_month)
        tvTBCmonth = view.findViewById(R.id.tv_tbc_month)


        getDataChart()

        return view
    }

    private fun getDataChart() {

        waterDatabase =  WaterDatabase(context!!)

        val mot = waterDatabase.getWaterMonth(1).waterdrinkCompletion!!.toInt()
        val hai = waterDatabase.getWaterMonth(2).waterdrinkCompletion!!.toInt()
        val ba = waterDatabase.getWaterMonth(3).waterdrinkCompletion!!.toInt()
        val bon = waterDatabase.getWaterMonth(4).waterdrinkCompletion!!.toInt()
        val nam = waterDatabase.getWaterMonth(5).waterdrinkCompletion!!.toInt()
        val sau = waterDatabase.getWaterMonth(6).waterdrinkCompletion!!.toInt()
        val bay = waterDatabase.getWaterMonth(7).waterdrinkCompletion!!.toInt()
        val tam = waterDatabase.getWaterMonth(8).waterdrinkCompletion!!.toInt()
        val chin = waterDatabase.getWaterMonth(9).waterdrinkCompletion!!.toInt()
        val muoi = waterDatabase.getWaterMonth(10).waterdrinkCompletion!!.toInt()
        val muoi1 = waterDatabase.getWaterMonth(11).waterdrinkCompletion!!.toInt()
        val muoi2 = waterDatabase.getWaterMonth(12).waterdrinkCompletion!!.toInt()
        val muoi3 = waterDatabase.getWaterMonth(13).waterdrinkCompletion!!.toInt()
        val muoi4 = waterDatabase.getWaterMonth(14).waterdrinkCompletion!!.toInt()
        val muoi5 = waterDatabase.getWaterMonth(15).waterdrinkCompletion!!.toInt()
        val muoi6 = waterDatabase.getWaterMonth(16).waterdrinkCompletion!!.toInt()
        val muoi7 = waterDatabase.getWaterMonth(17).waterdrinkCompletion!!.toInt()
        val muoi8 = waterDatabase.getWaterMonth(18).waterdrinkCompletion!!.toInt()
        val muoi9 = waterDatabase.getWaterMonth(19).waterdrinkCompletion!!.toInt()
        val haimuoi = waterDatabase.getWaterMonth(20).waterdrinkCompletion!!.toInt()
        val hai1 = waterDatabase.getWaterMonth(21).waterdrinkCompletion!!.toInt()
        val hai2 = waterDatabase.getWaterMonth(22).waterdrinkCompletion!!.toInt()
        val hai3 = waterDatabase.getWaterMonth(23).waterdrinkCompletion!!.toInt()
        val hai4 = waterDatabase.getWaterMonth(24).waterdrinkCompletion!!.toInt()
        val hai5 = waterDatabase.getWaterMonth(25).waterdrinkCompletion!!.toInt()
        val hai6 = waterDatabase.getWaterMonth(26).waterdrinkCompletion!!.toInt()
        val hai7 = waterDatabase.getWaterMonth(27).waterdrinkCompletion!!.toInt()
        val hai8 = waterDatabase.getWaterMonth(28).waterdrinkCompletion!!.toInt()
        val hai9 = waterDatabase.getWaterMonth(29).waterdrinkCompletion!!.toInt()
        val bamuoi = waterDatabase.getWaterMonth(30).waterdrinkCompletion!!.toInt()
        val ba1 = waterDatabase.getWaterMonth(31).waterdrinkCompletion!!.toInt()


        listHistoryMonth = ArrayList()
        listHistoryMonth.add(mot)
        listHistoryMonth.add(hai)
        listHistoryMonth.add(ba)
        listHistoryMonth.add(bon)
        listHistoryMonth.add(nam)
        listHistoryMonth.add(sau)
        listHistoryMonth.add(bay)
        listHistoryMonth.add(tam)
        listHistoryMonth.add(chin)
        listHistoryMonth.add(muoi)
        listHistoryMonth.add(muoi1)
        listHistoryMonth.add(muoi2)
        listHistoryMonth.add(muoi3)
        listHistoryMonth.add(muoi4)
        listHistoryMonth.add(muoi5)
        listHistoryMonth.add(muoi6)
        listHistoryMonth.add(muoi7)
        listHistoryMonth.add(muoi8)
        listHistoryMonth.add(muoi9)
        listHistoryMonth.add(haimuoi)
        listHistoryMonth.add(hai1)
        listHistoryMonth.add(hai2)
        listHistoryMonth.add(hai3)
        listHistoryMonth.add(hai4)
        listHistoryMonth.add(hai5)
        listHistoryMonth.add(hai6)
        listHistoryMonth.add(hai7)
        listHistoryMonth.add(hai8)
        listHistoryMonth.add(hai9)
        listHistoryMonth.add(bamuoi)
        listHistoryMonth.add(ba1)
        var count = 31
        for (i in 0..30) {
            if (listHistoryMonth[i] == 0) {
                count--
            }
        }
        if (count != 0) {
            var comPletion = 0
            for (i in 0..30) {
                comPletion += listHistoryMonth[i]
            }


            val comPletionTBC = comPletion / count
            tvCompletesMonth.text = "$comPletionTBC%"
            waterDatabase = WaterDatabase(context!!)
            val x = waterDatabase.getAllWaterMonth()
            val waterTBC = x / count
            tvWaterMonth.text = "$waterTBC ml"

            val countDrink = waterDatabase.getAllCountMonth()
            val countTBC = countDrink / count
            tvTBCmonth.text = "$countTBC/Day"
            textChartMonth = "Average completion (%)"
        } else {
            tvTBCmonth.text = "0/Day"
            tvWaterMonth.text = "0 ml"
            tvCompletesMonth.text = "0%"
            textChartMonth = "Average completion (%)"
        }




        chartMonths.setScaleEnabled(false)
        chartMonths.description.isEnabled = false
        chartMonths.setDrawBarShadow(false)
        chartMonths.setDrawValueAboveBar(false)
        chartMonths.setPinchZoom(false)
        chartMonths.setDrawGridBackground(false)
        chartMonths.getAxisLeft().setStartAtZero(true)
        chartMonths.getAxisRight().setEnabled(false)
        val barEntries: ArrayList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(0.toFloat(), mot.toFloat()))
        barEntries.add(BarEntry(1.toFloat(), hai.toFloat()))
        barEntries.add(BarEntry(2.toFloat(), ba.toFloat()))
        barEntries.add(BarEntry(3.toFloat(), bon.toFloat()))
        barEntries.add(BarEntry(4.toFloat(), nam.toFloat()))
        barEntries.add(BarEntry(5.toFloat(), sau.toFloat()))
        barEntries.add(BarEntry(6.toFloat(), bay.toFloat()))
        barEntries.add(BarEntry(7.toFloat(), tam.toFloat()))
        barEntries.add(BarEntry(8.toFloat(), chin.toFloat()))
        barEntries.add(BarEntry(9.toFloat(), muoi.toFloat()))
        barEntries.add(BarEntry(10.toFloat(), muoi1.toFloat()))
        barEntries.add(BarEntry(11.toFloat(), muoi2.toFloat()))
        barEntries.add(BarEntry(12.toFloat(), muoi3.toFloat()))
        barEntries.add(BarEntry(13.toFloat(), muoi4.toFloat()))
        barEntries.add(BarEntry(14.toFloat(), muoi5.toFloat()))
        barEntries.add(BarEntry(15.toFloat(), muoi6.toFloat()))
        barEntries.add(BarEntry(16.toFloat(), muoi7.toFloat()))
        barEntries.add(BarEntry(17.toFloat(), muoi8.toFloat()))
        barEntries.add(BarEntry(18.toFloat(), muoi9.toFloat()))
        barEntries.add(BarEntry(19.toFloat(), haimuoi.toFloat()))
        barEntries.add(BarEntry(20.toFloat(), hai1.toFloat()))
        barEntries.add(BarEntry(21.toFloat(), hai2.toFloat()))
        barEntries.add(BarEntry(22.toFloat(), hai3.toFloat()))
        barEntries.add(BarEntry(23.toFloat(), hai4.toFloat()))
        barEntries.add(BarEntry(24.toFloat(), hai5.toFloat()))
        barEntries.add(BarEntry(25.toFloat(), hai6.toFloat()))
        barEntries.add(BarEntry(26.toFloat(), hai7.toFloat()))
        barEntries.add(BarEntry(27.toFloat(), hai8.toFloat()))
        barEntries.add(BarEntry(28.toFloat(), hai9.toFloat()))
        barEntries.add(BarEntry(29.toFloat(), bamuoi.toFloat()))
        barEntries.add(BarEntry(30.toFloat(), ba1.toFloat()))

        val barDataSet = BarDataSet(barEntries, textChartMonth)
        val data = BarData(barDataSet)

        data.setDrawValues(false)
        data.barWidth = 0.3f
//        setData()
        val weeks = arrayOf(
            "1",
            "",
            "",
            "",
            "",
            "",
            "",
            "8",
            "",
            "",
            "",
            "",
            "",
            "",
            "15",
            "",
            "",
            "",
            "",
            "",
            "",
            "22",
            "",
            "",
            "",
            "",
            "",
            "",
            "29",
            "",
            ""
        )
        chartMonths.data = data
        val xAxis: XAxis = chartMonths.xAxis
        xAxis.textSize = 9F
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setLabelCount(31)
        xAxis.setAvoidFirstLastClipping(true)
        chartMonths.getXAxis().setDrawGridLines(false)
        chartMonths.getAxisLeft().setDrawGridLines(true)
        chartMonths.getAxisRight().setDrawGridLines(true)


    }


    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }
}
