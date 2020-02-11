package com.kolorfox.watertracker.fragment.historyfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView

import com.kolorfox.watertracker.R
import kotlinx.android.synthetic.main.fragment_historys.*


class HistorysFragment : Fragment() {
    lateinit var todayOn:CardView
    lateinit var todayOff:CardView
    lateinit var weekOn:CardView
    lateinit var weekOff:CardView
    lateinit var monthOn:CardView
    lateinit var monthOff:CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_historys, container, false)

        todayOn = view.findViewById(R.id.today_on)
        todayOff = view.findViewById(R.id.today_off)
        weekOn = view.findViewById(R.id.week_on)
        weekOff = view.findViewById(R.id.week_off)
        monthOn = view.findViewById(R.id.month_on)
        monthOff = view.findViewById(R.id.month_off)

        addDefaultFragment()
        loadToDayFragment()
        loadWeekFragment()
        loadMonthFragment()

        return view
    }

    private fun addDefaultFragment() {
        val frag = TodaysFragment()
        insertFragment(frag)
    }

    private fun loadMonthFragment() {
        monthOff.setOnClickListener {

            val frag = MonthsFragment()
            insertFragment(frag)

            todayOn.visibility= View.INVISIBLE
            todayOff.visibility = View.VISIBLE
            weekOn.visibility = View.INVISIBLE
            weekOff.visibility = View.VISIBLE
            monthOff.visibility =View.INVISIBLE
            monthOn.visibility = View.VISIBLE
        }
        monthOn.setOnClickListener {

            val frag = MonthsFragment()
            insertFragment(frag)

            todayOn.visibility= View.INVISIBLE
            todayOff.visibility = View.VISIBLE
            weekOn.visibility = View.INVISIBLE
            weekOff.visibility = View.VISIBLE
            monthOff.visibility =View.INVISIBLE
            monthOn.visibility = View.VISIBLE
        }
    }

    private fun loadWeekFragment() {
        weekOff.setOnClickListener {

            val frag = WeeksFragment()
            insertFragment(frag)

            todayOn.visibility= View.INVISIBLE
            todayOff.visibility = View.VISIBLE
            weekOn.visibility = View.VISIBLE
            weekOff.visibility = View.INVISIBLE
            monthOff.visibility =View.VISIBLE
            monthOn.visibility = View.INVISIBLE
        }
        weekOn.setOnClickListener {

            val frag = WeeksFragment()
            insertFragment(frag)

            todayOn.visibility= View.INVISIBLE
            todayOff.visibility = View.VISIBLE
            weekOn.visibility = View.VISIBLE
            weekOff.visibility = View.INVISIBLE
            monthOff.visibility =View.VISIBLE
            monthOn.visibility = View.INVISIBLE
        }
    }

    private fun loadToDayFragment() {
        todayOn.setOnClickListener {

            val frag = TodaysFragment()
            insertFragment(frag)

            todayOn.visibility= View.VISIBLE
            todayOff.visibility = View.INVISIBLE
            weekOn.visibility = View.INVISIBLE
            weekOff.visibility = View.VISIBLE
            monthOff.visibility =View.VISIBLE
            monthOn.visibility = View.INVISIBLE
        }
        todayOff.setOnClickListener {

            val frag = TodaysFragment()
            insertFragment(frag)

            todayOn.visibility= View.VISIBLE
            todayOff.visibility = View.INVISIBLE
            weekOn.visibility = View.INVISIBLE
            weekOff.visibility = View.VISIBLE
            monthOff.visibility =View.VISIBLE
            monthOn.visibility = View.INVISIBLE
        }
    }


    private fun insertFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()
            ?.replace(
                com.kolorfox.watertracker.R.id.container_History,
                fragment,
                fragment.javaClass.getSimpleName()
            )
            ?.commit()
    }
}
