package com.kolorfox.watertracker.fragment.weightfragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import com.kolorfox.watertracker.ui.settingsweight.SetHeightTargetActivity
import com.kolorfox.watertracker.ui.watertrackermain.SetWeightsActivity
import java.text.DecimalFormat
import java.util.*


class WeightsFragment : Fragment() {

    lateinit var tvTargets:TextView
    lateinit var tvWeights:TextView
    lateinit var waterDatabase: WaterDatabase
    lateinit var mChart: BarChart
    lateinit var tvReminders:TextView
    lateinit var tvBMIweight:TextView
    lateinit var setHeight:ImageView
    lateinit var tvAddWeight:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_weights, container, false)

        mChart = view.findViewById(R.id.barCharts)
        tvWeights = view.findViewById(R.id.tv_weight_fragment)
        tvTargets = view.findViewById(R.id.tv_target_weights)
        tvReminders = view.findViewById(R.id.tv_remain_weights)
        tvBMIweight = view.findViewById(R.id.tv_BMI)
        setHeight = view.findViewById(R.id.img_set_height)
        tvAddWeight = view.findViewById(R.id.tv_add_weight)


        createChart()
        getData()
        setHeightActivity()
        setWeightActivity()

        return view
    }

    private fun setWeightActivity() {
        tvAddWeight.setOnClickListener {
        val intent = Intent(context, SetWeightsActivity::class.java)
        var text = tvWeights.text.toString()
            Log.i("khkhkhk", text)
        intent.putExtra("weight", text)
        startActivity(intent)
    }
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun setHeightActivity() {
        setHeight.setOnClickListener {
            val intent = Intent(context, SetHeightTargetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        var weight = context?.let { ShareReference.getWeights(it) }
        var weightText = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()

        tvWeights.text = weight.toString()

        var weightBMI = weight!!.replace("[^\\d.]".toRegex(), "").toFloat()
        var height = context?.let { ShareReference.getHeight(it) }
        var height2 = height!!.replace("[^\\d.]".toRegex(), "").toFloat()


        var heightMBI = height2 * 0.01

        var xx = weightBMI / (heightMBI * heightMBI)
        val twoDForm = DecimalFormat("#.#")
//        var BMI = java.lang.Double.valueOf(twoDForm.format(xx))
        var z = xx * 100
        var BMI2 = Math.round(z)
        var BMI: Float = (BMI2 / 100f)

        var heightLe = height2 - 100
        var targetWeight = ((heightLe * 9) / 10)
        var taget1 = targetWeight * 10
        var taget2 = Math.round(taget1)
        var targetWeight2 = taget2 / 10
        val twoDForm2 = DecimalFormat("#.#")
//
//        var targetWeight2 = java.lang.Double.valueOf(twoDForm2.format(targetWeight))

        tvBMIweight.text = "$BMI"
        var checkWeight = context?.let { ShareReference.getTargetWeight(it) }
        if (checkWeight == "") {
            tvTargets.text = "$targetWeight2 kg"
            context?.let { ShareReference.setTargetWeight("$targetWeight2", it) }
            Log.i("vcvccv", "$targetWeight2")
            Log.i("vcvccv", "$checkWeight")
            var remind = weightText - targetWeight2
            var remindxx = Math.abs(remind)
            var z = remindxx * 100
            var reminder4 = Math.round(z)
            var reminder3 = (reminder4 / 100f)

            tvReminders.text = "$reminder3 kg"
        } else {
            tvTargets.text = "$checkWeight kg"
            var target = checkWeight!!.toFloat()
            var remind = weightText - target
            var remind2 = Math.abs(remind)
            var z = remind2 * 100
            var reminder3 = Math.round(z)
            var reminder4 = (reminder3 / 100f)
//            var remind3 = java.lang.Double.valueOf(twoDForm2.format(remind2))
            tvReminders.text = "$reminder4 kg"
        }


    }


    private fun createChart() {
        waterDatabase = context?.let { WaterDatabase(it) }!!
        mChart.description.isEnabled = false
        mChart.setPinchZoom(false)
        mChart.setDrawGridBackground(false)
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(false)
        mChart.getAxisRight().setEnabled(false)
        mChart.getAxisLeft().setStartAtZero(true)



        val barEntries: ArrayList<BarEntry> = ArrayList()

        barEntries.add(BarEntry(0.toFloat(), waterDatabase.getWeightDay(2).weight!!.toFloat()))
        barEntries.add(BarEntry(1.toFloat(), waterDatabase.getWeightDay(3).weight!!.toFloat()))
        barEntries.add(BarEntry(2.toFloat(), waterDatabase.getWeightDay(4).weight!!.toFloat()))
        barEntries.add(BarEntry(3.toFloat(), waterDatabase.getWeightDay(5).weight!!.toFloat()))
        barEntries.add(BarEntry(4.toFloat(), waterDatabase.getWeightDay(6).weight!!.toFloat()))
        barEntries.add(BarEntry(5.toFloat(), waterDatabase.getWeightDay(7).weight!!.toFloat()))
        barEntries.add(BarEntry(6.toFloat(), waterDatabase.getWeightDay(8).weight!!.toFloat()))


        val barDataSet = BarDataSet(barEntries, "Weight (Kg)")
        val data = BarData(barDataSet)
        data.barWidth = 0.5f
        data.setDrawValues(false)

        val weeks = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sar", "Sun")
        var dataSets: ArrayList<BarDataSet> = ArrayList()
        dataSets.add(barDataSet)

        data.barWidth = 0.5f
        data.setDrawValues(false)

        mChart.data= data

        mChart.getXAxis().setDrawGridLines(false)
        mChart.getAxisLeft().setDrawGridLines(true)
        mChart.getAxisRight().setDrawGridLines(true)

        mChart.data.isHighlightEnabled = false
        val xAxis: XAxis = mChart.xAxis
        xAxis.valueFormatter = MyXAxisValueFormater(weeks)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    inner class MyXAxisValueFormater(private val mValues: Array<String>) :
        IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return mValues[value.toInt()]
        }
    }




}
