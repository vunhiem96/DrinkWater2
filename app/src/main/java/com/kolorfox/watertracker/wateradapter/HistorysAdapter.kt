package com.kolorfox.watertracker.wateradapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.model.WaterHistory
import com.kolorfox.watertracker.data.model.WaterMonth
import com.kolorfox.watertracker.data.model.WaterWeek
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.data.WaterDatabase
import kotlinx.android.synthetic.main.dialog_popup_edit.*
import kotlinx.android.synthetic.main.item_historys.view.*
import java.text.SimpleDateFormat
import java.util.*

class HistorysAdapter(
    var context: Context,
    var listHistory: ArrayList<WaterHistory>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dbWater: WaterDatabase? = null
    var check: Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_historys, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dbWater = WaterDatabase(context)
        var data: WaterHistory = listHistory[position]
        var time = data.time
        var ml = data.waterUp
        holder.itemView.tv_time.text = "$time"
        holder.itemView.tv_water_add.text = "+${ml}ml"
        holder.itemView.item_more_rv.setOnClickListener {
            holder.itemView.root_item_history.setCardBackgroundColor((Color.parseColor("#f2f2f2")))
            val intentHide = Intent()
            intentHide.action = "updateViewToDayFragment"

            context.sendBroadcast(intentHide)


            val wrapper = ContextThemeWrapper(context, R.style.CustomPopupTheme)
            val menuBuilder = MenuBuilder(wrapper)
            val inflater = MenuInflater(wrapper)

            inflater.inflate(R.menu.edit_today_item, menuBuilder)
            val optionsMenu =
                MenuPopupHelper(wrapper, menuBuilder, holder.itemView.item_more_rv)
            optionsMenu.setForceShowIcon(true)
            menuBuilder.setCallback(object : MenuBuilder.Callback {
                override fun onMenuItemSelected(menu: MenuBuilder, item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.nav_edit
                        -> {



                            val ok: TextView
                            val rdb100: RadioButton
                            val rdb200: RadioButton
                            val rdb300: RadioButton
                            val rdb400: RadioButton

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

                            rdb100 = mDialogView.findViewById(R.id.rdb_100)
                            rdb200 = mDialogView.findViewById(R.id.rdb_200)
                            rdb300 = mDialogView.findViewById(R.id.rdb_300)
                            rdb400 = mDialogView.findViewById(R.id.rdb_400)

                            val text = holder.itemView.tv_water_add.text
                            val waterOldUp = text.replace("[^\\d.]".toRegex(), "").toInt()

                            if (waterOldUp == 100) {
                                rdb100.isChecked = true
                            } else if (waterOldUp == 200) {
                                rdb200.isChecked = true
                            } else if (waterOldUp == 300) {
                                rdb300.isChecked = true
                            } else if (waterOldUp == 400) {
                                rdb400.isChecked = true
                            }

                            ok = mDialogView.findViewById(R.id.btn_ok)
                            ok.setOnClickListener {
                                listener.onClick(position)
                                val gold = ShareReference.getGoalDrink(context)!!.toInt()

                                val waterDrank =
                                    context?.let { it1 -> ShareReference.getWaterLevel(it1) }
                                var waterNow = waterDrank!! - waterOldUp!!
                                if (waterNow < 0) {
                                    waterNow = 0
                                }
                                if (rdb100.isChecked == true) {

                                    holder.itemView.tv_water_add.text = "+100 ml"
                                    val waterLevelNow = waterNow + 100
                                    context?.let { it1 ->
                                        ShareReference.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(100, it1) }
//                                    }
                                    context?.let { it1 -> ShareReference.setWaterup2(100, it1) }
                                    val id = data.id
                                    val time = data.time.toString()
                                    val waterUp = 100
                                    val history = WaterHistory(id, time, waterUp)
                                    dbWater!!.updateHistory(history)
                                    if (waterLevelNow > gold) {
                                        ShareReference.setCheckFull(true, context)
                                    } else {
                                        ShareReference.setCheckFull(false, context)
                                    }

                                } else if (rdb200.isChecked == true) {

                                    holder.itemView.tv_water_add.text = "+200 ml"
                                    val waterLevelNow = waterNow + 200
                                    context?.let { it1 ->
                                        ShareReference.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(200, it1) }
//                                    }
                                    context?.let { it1 -> ShareReference.setWaterup2(200, it1) }
                                    val id = data.id
                                    val time = data.time.toString()
                                    val waterUp = 200
                                    val history = WaterHistory(id, time, waterUp)
                                    dbWater!!.updateHistory(history)
                                    if (waterLevelNow > gold) {
                                        ShareReference.setCheckFull(true, context)
                                    } else {
                                        ShareReference.setCheckFull(false, context)
                                    }

                                } else if (rdb300.isChecked == true) {

                                    holder.itemView.tv_water_add.text = "+300 ml"
                                    val waterLevelNow = waterNow + 300
                                    context?.let { it1 ->
                                        ShareReference.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(300, it1) }
//                                    }
                                    context?.let { it1 -> ShareReference.setWaterup2(300, it1) }
                                    val id = data.id
                                    val time = data.time.toString()
                                    val waterUp = 300
                                    val history = WaterHistory(id, time, waterUp)
                                    dbWater!!.updateHistory(history)
                                    if (waterLevelNow > gold) {
                                        ShareReference.setCheckFull(true, context)
                                    } else {
                                        ShareReference.setCheckFull(false, context)
                                    }

                                } else if (rdb400.isChecked == true) {

                                    holder.itemView.tv_water_add.text = "+400 ml"
                                    val waterLevelNow = waterNow + 400
                                    context?.let { it1 ->
                                        ShareReference.setWaterlevel(
                                            waterLevelNow,
                                            it1
                                        )
                                    }
//                                    if (position == noti.size-1){
//                                        context?.let { it1 -> AppConfig.setWaterup(400, it1) }
//                                    }

                                    context?.let { it1 -> ShareReference.setWaterup2(400, it1) }
                                    val id = data.id
                                    val time = data.time.toString()
                                    val waterUp = 400
                                    val history = WaterHistory(id, time, waterUp)
                                    dbWater!!.updateHistory(history)
                                    if (waterLevelNow > gold) {
                                        ShareReference.setCheckFull(true, context)
                                    } else {
                                        ShareReference.setCheckFull(false, context)
                                    }

                                }
//                                if (position == noti.size-1) {
////                                    Log.i("position","$position")
////                                    var waterUp = context?.let { it1 -> AppConfig.getWaterup(it1) }
////                                    val intent1 = Intent()
////                                    intent1.action = "textChange2"
////                                    intent1.putExtra("text2", "$waterUp")
////                                    context!!.sendBroadcast(intent1)
//
//                                }


                                val intentChange = Intent()
                                intentChange.action = "updateRecycleview"
                                val waterUp = ShareReference.getWaterup(context)
                                Log.i("waterup", "$waterUp")
                                if (position == listHistory.size - 1) {
                                    intentChange.putExtra("adapterWaterlevel", waterUp)
                                }
                                context.sendBroadcast(intentChange)
                                updateData()
                                mAlertDialog.dismiss()
                            }




                            return true
                        }
                        R.id.nav_delete
                        -> {

//                            if (position == noti.size-1 && noti.size>1) {
//                                var positon2 = position -1
//                                var x = noti[positon2]
////                                var waterup = holder.itemView.tv_water_record_item.text.replace("[^\\d.]".toRegex(), "").toInt()
//                                var waterUp = x.waterUp!!.toInt()
//                               AppConfig.setWaterup(waterUp, context!!)
//                            }
                            val text = holder.itemView.tv_water_add.text
                            val waterOldUp = text.replace("[^\\d.]".toRegex(), "").toInt()
                            val waterLevel = ShareReference.getWaterLevel(context!!)!!.toInt()
                            if (waterLevel >= 100) {
                                val waternow = waterLevel!! - waterOldUp
                                ShareReference.setWaterlevel(waternow, context!!)
                            }
                            val count = ShareReference.getCount(context)!!.toInt()
                            if (count > 0) {
                                val countNow = count - 1
                                ShareReference.setCount(countNow, context)
                                updateData()

                            }
                            val level = ShareReference.getWaterLevel(context)!!.toInt()
                            val gold = ShareReference.getGoalDrink(context)
                            val waterGold = gold!!.replace("[.]".toRegex(), "").toInt()
                            if (level < waterGold) {
                                ShareReference.setCheckFull(false, context)
                            }

                            val id = data.id
                            dbWater!!.deleteOneHistory(id)
                            listHistory.removeAt(position)
                            notifyDataSetChanged()

                            return true
                        }
                        else -> return false
                    }
                }

                override fun onMenuModeChange(menu: MenuBuilder) {}
            })
            optionsMenu.setOnDismissListener {
                holder.itemView.root_item_history.setCardBackgroundColor((Color.parseColor("#ffffff")))
            }
            optionsMenu.show()


        }
    }

    fun updateData() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        if (day == Calendar.MONDAY) {
            Log.i("test26", "monday")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(2, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.TUESDAY) {
            Log.i("test26", "tue")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(3, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.WEDNESDAY) {
            Log.i("test26", "wed")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(4, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.THURSDAY) {
            Log.i("test26", "thur")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(5, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.FRIDAY) {
            Log.i("test26", "fri")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            var count = ShareReference.getCount(context)
            val water =
                WaterWeek(6, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SATURDAY) {
            Log.i("test26", "sar")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(7, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
        if (day == Calendar.SUNDAY) {
            Log.i("test26", "sun")
            val waterDrink2 = ShareReference.getWaterLevel(context)!!.toInt()
            val waterDrink =
                ShareReference.getWaterLevel(context)!!.toFloat()
            val waterGold = ShareReference.getGoalDrink(context)
            val waterGold2 =
                waterGold!!.replace("[.]".toRegex(), "").toFloat()
            val waterCompletion = (waterDrink / waterGold2) * 100
            val waterCompletion2 =
                Math.round(waterCompletion.toDouble()).toInt()
            val count = ShareReference.getCount(context)
            val water =
                WaterWeek(8, waterDrink2, waterCompletion2, count!!)
            dbWater!!.updateWaterWeek(water)
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd")
            val currentDate = sdf.format(c.getTime()).toInt()
            val waterMonth = WaterMonth(
                currentDate,
                waterDrink2,
                waterCompletion2,
                count!!
            )
            dbWater!!.updateWaterMonth(waterMonth)
        }
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    interface ItemClickListener {
        fun onClick(pos: Int)
    }
}