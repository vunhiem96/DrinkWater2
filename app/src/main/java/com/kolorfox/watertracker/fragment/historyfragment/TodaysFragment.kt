package com.kolorfox.watertracker.fragment.historyfragment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.wateradapter.HistorysAdapter
import com.kolorfox.watertracker.data.model.WaterHistory
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import kotlinx.android.synthetic.main.dialog_popup_edit.*
import kotlinx.android.synthetic.main.fragment_todays.*


class TodaysFragment : Fragment() {

    lateinit var rootLayout: LinearLayout
    lateinit var popUp: RelativeLayout
    lateinit var moreLayout: FrameLayout
    lateinit var rvHistorys: RecyclerView
    lateinit var tvWaterNexts: TextView
    lateinit var tvTimeWaterNexts: TextView
    lateinit var rlRecords: RelativeLayout
    lateinit var adapters: HistorysAdapter
    lateinit var dbWater: WaterDatabase
    lateinit var rlNoHistory: RelativeLayout
    var listHistorys: ArrayList<WaterHistory> = ArrayList()
    var check: Boolean = false
    var waterLevel = 0
    var waterGold = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_todays, container, false)
        rvHistorys = view.findViewById(R.id.rv_historys)
        tvTimeWaterNexts = view.findViewById(R.id.tv_time_next)
        tvWaterNexts = view.findViewById(R.id.tv_water_nexts)
        moreLayout = view.findViewById(R.id.item_more)
        popUp = view.findViewById(R.id.popup)
        rootLayout = view.findViewById(R.id.root)

        getData()
        createPopup()
        createRecycleView()


        return view
    }

    private fun createPopup() {
        moreLayout.setOnClickListener {
            if (check == false) {
                item_more.setBackgroundColor(Color.parseColor("#f2f2f2"))
                card.setCardBackgroundColor(Color.parseColor("#f2f2f2"))
                popUp.visibility = View.VISIBLE
                check = true
            } else {
                item_more.setBackgroundColor(Color.parseColor("#ffffff"))
                card.setCardBackgroundColor(Color.parseColor("#ffffff"))
                popUp.visibility = View.INVISIBLE
                check = false
            }
//            val wrapper = ContextThemeWrapper(context, R.style.CustomPopupTheme)
//            val menuBuilder = MenuBuilder(wrapper)
//            val inflater = MenuInflater(wrapper)
//
//            inflater.inflate(R.menu.edit_today_item, menuBuilder)
//            val optionsMenu =
//                MenuPopupHelper(wrapper, menuBuilder, moreLayout)
//            optionsMenu.setForceShowIcon(true)
//            menuBuilder.setCallback(object : MenuBuilder.Callback {
//                override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
//                    when (item.itemId) {
//                        R.id.nav_edit
//                            // Handle option1 Click
//                        -> {
//                            return true
//                        }
//                        else -> return false
//                    }
//                }
//
//                override fun onMenuModeChange(menu: MenuBuilder) {}
//            })
//
//            optionsMenu.show()
//

        }
        rootLayout.setOnClickListener {
            item_more.setBackgroundColor(Color.parseColor("#ffffff"))
            card.setCardBackgroundColor(Color.parseColor("#ffffff"))
            popUp.visibility = View.INVISIBLE
            check = false
        }
        popUp.setOnClickListener {
            card.setCardBackgroundColor(Color.parseColor("#ffffff"))
            popUp.visibility = View.INVISIBLE
            check = false

            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_popup_edit, null)

            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()


            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mAlertDialog.remove_popup.setOnClickListener {
                mAlertDialog.dismiss()
            }
            val waterUpString = tvWaterNexts.text
            val waterUpInt = waterUpString!!.replace("[^\\d.]".toRegex(), "").toInt()

            if(waterUpInt == 100){
                mAlertDialog.rdb_100.isChecked= true
            }
            if(waterUpInt == 200){
                mAlertDialog.rdb_200.isChecked= true
            }

            if(waterUpInt == 300){
                mAlertDialog.rdb_300.isChecked= true
            }

            if(waterUpInt == 400){
                mAlertDialog.rdb_400.isChecked= true
            }

            mAlertDialog.btn_ok.setOnClickListener {
                if(mAlertDialog.rdb_100.isChecked == true){
                    ShareReference.setWaterUp(100, context!!)
                    val intentWaterChoose = Intent()
                    intentWaterChoose.action = "imgChange"
                    intentWaterChoose.putExtra("img", 1)
                    context!!.sendBroadcast(intentWaterChoose)
                    tvWaterNexts.text="100ml"
                }
                if(mAlertDialog.rdb_200.isChecked == true){
                    ShareReference.setWaterUp(200, context!!)
                    val intentWaterChoose = Intent()
                    intentWaterChoose.action = "imgChange"
                    intentWaterChoose.putExtra("img", 2)
                    context!!.sendBroadcast(intentWaterChoose)
                    tvWaterNexts.text="200ml"
                }
                if(mAlertDialog.rdb_300.isChecked == true){
                    ShareReference.setWaterUp(300, context!!)
                    val intentWaterChoose = Intent()
                    intentWaterChoose.action = "imgChange"
                    intentWaterChoose.putExtra("img", 3)
                    context!!.sendBroadcast(intentWaterChoose)
                    tvWaterNexts.text="300ml"
                }
                if(mAlertDialog.rdb_400.isChecked == true){
                    ShareReference.setWaterUp(400, context!!)
                    val intentWaterChoose = Intent()
                    intentWaterChoose.action = "imgChange"
                    intentWaterChoose.putExtra("img", 4)
                    context!!.sendBroadcast(intentWaterChoose)
                    tvWaterNexts.text="400ml"
                }
                mAlertDialog.dismiss()
            }






        }

    }

    private fun getData() {
        val waterUp = ShareReference.getWaterup(context!!)
        tvTimeWaterNexts.text = context?.let { ShareReference.getTimeNextDrink(it) }
        tvWaterNexts.text = "+${waterUp}ml"

    }

    override fun onResume() {
        super.onResume()
        context!!.registerReceiver(receiverUpdateRV, IntentFilter("updateRecycleview"))
        context!!.registerReceiver(receiverUpdateRV, IntentFilter("updateViewToDayFragment"))
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(receiverUpdateRV)
    }

    internal var receiverUpdateRV: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "updateRecycleview") {
                createRecycleView()
            }
            if (intent.action == "updateViewToDayFragment"){
                popUp.visibility = View.INVISIBLE
                check = false
                item_more.setBackgroundColor(Color.parseColor("#ffffff"))
                card.setCardBackgroundColor(Color.parseColor("#ffffff"))

            }
        }
    }


    private fun createRecycleView() {
        dbWater = WaterDatabase(context!!)
        listHistorys = dbWater.allHistory as ArrayList<WaterHistory>
        rvHistorys.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        adapters =
            HistorysAdapter(context!!, listHistorys, object : HistorysAdapter.ItemClickListener {
                override fun onClick(pos: Int) {
                    createRecycleView()
                }
            })
        rvHistorys.adapter = adapters
        rvHistorys.scrollToPosition(listHistorys.size - 1)
        adapters.notifyItemInserted(listHistorys.size - 1)
    }


}
