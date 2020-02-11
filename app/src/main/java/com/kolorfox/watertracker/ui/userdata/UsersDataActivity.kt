package com.kolorfox.watertracker.ui.userdata

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.ui.goalwater.WaterGoalActivity
import kotlinx.android.synthetic.main.activity_users_data.*
import kotlinx.android.synthetic.main.dialog_seldect.*
import kotlinx.android.synthetic.main.dialog_select_gender.*
import kotlinx.android.synthetic.main.dialog_select_gender.btn_accept
import kotlinx.android.synthetic.main.dialog_select_time.*

class UsersDataActivity : AppCompatActivity() {
    var check = 0


    lateinit var btnOk: Button
    var hhWakeUp: Int = 7
    var mmWakeUp: Int = 0
    var hhBedTime: Int = 22
    var mmBedTime: Int = 0
    private var tgMale: ToggleButton? = null
    private var tgFemale: ToggleButton? = null
    private var edtDialog: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_data)
        loadData()
        selectGender()
        setWeight()
        setHight()
        setWakeUpTime()
        setBedTimeUser()
        setIntervalTimeUser()
        nextActivity()

    }

    private fun loadData() {
        ShareReference.setWakeUpTime("07:00", this)
        ShareReference.setBedTimes("22:00", this)

    }

    private fun nextActivity() {
        btn_next_user.setOnClickListener {
            ShareReference.setGender(tv_gender.text.toString(), this)
            val weight = tv_kg.text.toString()!!.replace("[^\\d.]".toRegex(), "")
            ShareReference.setWeights(weight, this)
            val height = tv_cm_height.text.toString()!!.replace("[^\\d.]".toRegex(), "")
            ShareReference.setHeight(height, this)
            ShareReference.setWakeUpTime(tv_wakeup_time_user.text.toString(), this)
            ShareReference.setBedTimes(tv_bed_time_user.text.toString(), this)
            if (check == 0) {
                ShareReference.setIntervalTimes("60 min", this)
            }


            val intent = Intent(this, WaterGoalActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setIntervalTimeUser() {
        tv_interval_time_user_title.setOnClickListener {
            selectIntervalChild()
        }
        rl_set_intervaltime.setOnClickListener {
            selectIntervalChild()
        }

    }

    fun selectIntervalChild() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_interval_select, null)

        val tgbtn30: ToggleButton = dialogView.findViewById(R.id.tgbtn_30)
        val tgbtn1: ToggleButton = dialogView.findViewById(R.id.tgbtn_1)
        val tgbtn130: ToggleButton = dialogView.findViewById(R.id.tgbtn_130)
        val tgbtn2: ToggleButton = dialogView.findViewById(R.id.tgbtn_2)
//            var tgbtn3: ToggleButton = mDialogView.findViewById(R.id.tgbtn_3)

        val rl_30: RelativeLayout = dialogView.findViewById(R.id.rl_30)
        val rl_1: RelativeLayout = dialogView.findViewById(R.id.rl_1)
        val rl_130: RelativeLayout = dialogView.findViewById(R.id.rl_130)
        val rl_2: RelativeLayout = dialogView.findViewById(R.id.rl_2)
//            var rl_3: RelativeLayout = mDialogView.findViewById(R.id.rl_3)

        btnOk = dialogView.findViewById(R.id.btn_accept_interval_time)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = mBuilder.show()
        val time = ShareReference.getIntervalTime(this)
        if (time == "30 min") {
            tgbtn30.isChecked = true
            tgbtn1.isChecked = false
            tgbtn130.isChecked = false
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false

        } else if (time == "60 min") {
            tgbtn30.isChecked = false
            tgbtn1.isChecked = true
            tgbtn130.isChecked = false
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false

        } else if (time == "90 min") {
            tgbtn30.isChecked = false
            tgbtn1.isChecked = false
            tgbtn130.isChecked = true
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false
        } else if (time == "120 min") {
            tgbtn30.isChecked = false
            tgbtn1.isChecked = false
            tgbtn130.isChecked = false
            tgbtn2.isChecked = true
//                tgbtn3.isChecked = false

        }
//            else if (time == "180 min") {
//                tgbtn30.isChecked = false
//                tgbtn1.isChecked = false
//                tgbtn130.FisChecked = false
//                tgbtn2.isChecked = false
//                tgbtn3.isChecked = true
//            }
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        rl_30.setOnClickListener {
            check = 1
//                tv_time_interval.text = "30 min"
            tgbtn30.isChecked = true
            tgbtn1.isChecked = false
            tgbtn130.isChecked = false
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false
//                AppConfig.setIntervalTime("30 min", this)
        }
        rl_1.setOnClickListener {
            check = 2

            tgbtn30.isChecked = false
            tgbtn1.isChecked = true
            tgbtn130.isChecked = false
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false

        }
        rl_130.setOnClickListener {
            check = 3

            tgbtn30.isChecked = false
            tgbtn1.isChecked = false
            tgbtn130.isChecked = true
            tgbtn2.isChecked = false
//                tgbtn3.isChecked = false

        }
        rl_2.setOnClickListener {
            check = 4

            tgbtn30.isChecked = false
            tgbtn1.isChecked = false
            tgbtn130.isChecked = false
            tgbtn2.isChecked = true
//                tgbtn3.isChecked = false
        }
//            rl_3.setOnClickListener {
//                temp = 5
//
//                tgbtn30.isChecked = false
//                tgbtn1.isChecked = false
//                tgbtn130.isChecked = false
//                tgbtn2.isChecked = false
//                tgbtn3.isChecked = true
//
//            }
        btnOk.setOnClickListener {
            if (check == 1) {
                tv_interval_time.text = "30 min"
                ShareReference.setIntervalTimes("30 min", this)
            } else if (check == 2) {
                tv_interval_time.text = "1 hour"
                ShareReference.setIntervalTimes("60 min", this)
            } else if (check == 3) {
                tv_interval_time.text = "90 min"
                ShareReference.setIntervalTimes("90 min", this)
            } else if (check == 4) {
                ShareReference.setIntervalTimes("120 min", this)
                tv_interval_time.text = "2 hours"
            }
//                else if (temp == 5) {
//                    AppConfig.setIntervalTime("180 min", this)
//                    tv_time_interval.text = "3 hour"
//                }
            alertDialog.dismiss()
        }

    }

    private fun setBedTimeUser() {
        tv_bedtime_title.setOnClickListener {
            setBebTimeChild()
        }
        rl_set_bedtime.setOnClickListener {
            setBebTimeChild()
        }

    }

    fun setBebTimeChild() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_time, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = mBuilder.show()


        alertDialog.tv_title_dialog.text = "Bed time"

        val currentString = ShareReference.getBedTimes(this)
        val separated =
            currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val x = separated[0].toInt()// this will contain "Fruit"
        val y = separated[1].toInt()
        alertDialog.number_picker.value = x
        alertDialog.number_picker2.value = y
        alertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
        alertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.number_picker.setOnValueChangedListener(object :
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
                    hhBedTime = newVal
                } else {
                    hhBedTime = oldVal
                }
            }


        })
        alertDialog.number_picker2.setOnValueChangedListener(object :
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
                    mmBedTime = newVal
                } else {
                    mmBedTime = oldVal
                }
            }

        })

        alertDialog.btn_accept_time.setOnClickListener {
            if (hhBedTime < 10 && mmBedTime < 10) {
                tv_bed_time_user.text = "0$hhBedTime:0$mmBedTime"
            } else if (hhBedTime < 10 && mmBedTime > 9) {
                tv_bed_time_user.text = "0$hhBedTime:$mmBedTime"
            } else if (hhBedTime > 10 && mmBedTime < 10) {
                tv_bed_time_user.text = "$hhBedTime:0$mmBedTime"
            } else {
                tv_bed_time_user.text = "$hhBedTime:$mmBedTime"
            }
            val bedtime = tv_bed_time_user.text.toString()
            ShareReference.setBedTimes(bedtime, this)
            alertDialog.dismiss()
        }

    }

    private fun setWakeUpTime() {
        rl_set_wakeup.setOnClickListener {
            setWakeUpChild()
        }
        tv_wakeup_title.setOnClickListener {
            setWakeUpChild()
        }
    }

    fun setWakeUpChild() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_time, null)

        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = mBuilder.show()

        val currentString = ShareReference.getWakeUpTime(this)
        val separated =
            currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val x = separated[0].toInt()// this will contain "Fruit"
        val y = separated[1].toInt()

        alertDialog.number_picker.value = x
        alertDialog.number_picker2.value = y
        alertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
        alertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.number_picker.setOnValueChangedListener(object :
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
                    hhWakeUp = newVal
                } else {
                    hhWakeUp = oldVal
                }
            }


        })
        alertDialog.number_picker2.setOnValueChangedListener(object :
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
                    mmWakeUp = newVal
                } else {
                    mmWakeUp = oldVal
                }
            }

        })

        alertDialog.btn_accept_time.setOnClickListener {
            if (hhWakeUp < 10 && mmWakeUp < 10) {
                tv_wakeup_time_user.text = "0$hhWakeUp:0$mmWakeUp"
            } else if (hhWakeUp < 10 && mmWakeUp > 9) {
                tv_wakeup_time_user.text = "0$hhWakeUp:$mmWakeUp"
            } else if (hhWakeUp > 10 && mmWakeUp < 10) {
                tv_wakeup_time_user.text = "$hhWakeUp:0$mmWakeUp"
            } else {
                tv_wakeup_time_user.text = "$hhWakeUp:$mmWakeUp"
            }
            val wakeUpTime = tv_wakeup_time_user.text.toString()
            ShareReference.setWakeUpTime(wakeUpTime, this)
            alertDialog.dismiss()
        }
    }

    private fun setHight() {
        rl_setheight2.setOnClickListener {
            setHeightChild()
        }
        rl_set_height.setOnClickListener {
            setHeightChild()
        }

    }

    fun setHeightChild() {

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_seldect, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = mBuilder.show()
        val height = tv_cm_height.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val height2 = height.toString()

        val separated =
            height2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val x = separated[0].toInt()// this will contain "Fruit"
        val y = separated[1].toInt()


        edtDialog = alertDialog.edt_kg
        if(y == 0){
            edtDialog!!.setText(x.toString())
        }else{
            edtDialog!!.setText(height.toString())
        }




        val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                val handler = Handler()
                handler.postDelayed({
                    edtDialog!!.setSelection(edtDialog!!.getText().length)
                }, 100)
            }
            return@OnFocusChangeListener
        }
        edtDialog!!.setOnFocusChangeListener(onFocusChangeListener)

        edtDialog!!.setOnClickListener {
            edtDialog!!.setSelection(edtDialog!!.getText().length)
        }

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.tv_title_dialog3.text = "My height"
        alertDialog.tv_cm.text = "cm"



        alertDialog.btn_accept.setOnClickListener {
            edtDialog = alertDialog.edt_kg
            val textCm: String = edtDialog!!.text.toString()
            if (textCm != "") {
                if (textCm == ".") {
                    alertDialog.tv_title_dialog3.text = "Invalid height"
                    val handler = Handler()
                    handler.postDelayed({
                        alertDialog.tv_title_dialog3.text = "My height"
                        edtDialog!!.text = null
                    }, 500)
                } else {
                    val heightFloadt = textCm.toFloat()
                    if (heightFloadt > 30 && heightFloadt < 250) {
                        tv_cm_height.text = "$textCm"
                        alertDialog.dismiss()
                    } else {
                        alertDialog.tv_title_dialog3.text = "Invalid height"
                        val handler = Handler()
                        handler.postDelayed({
                            alertDialog.tv_title_dialog3.text = "My height"
                            edtDialog!!.text = null
                        }, 500)
                    }
                }
            } else {
                tv_cm_height.text = "$height"
                alertDialog.dismiss()
            }

        }

    }

    private fun setWeight() {
        rl_set_weight.setOnClickListener {
            setWeightChild()
        }
        tv_weight_title.setOnClickListener {
            setWeightChild()
        }

    }

    fun setWeightChild() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_seldect, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = mBuilder.show()
        val textkg = tv_kg.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
        val textkg2 = textkg.toString()

        val separated =
            textkg2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val x = separated[0].toInt()// this will contain "Fruit"
        val y = separated[1].toInt()


        edtDialog = alertDialog.edt_kg
        if(y == 0){
            edtDialog!!.setText(x.toString())
        }else{
            edtDialog!!.setText(textkg.toString())
        }






        val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                val handler = Handler()
                handler.postDelayed({
                    edtDialog!!.setSelection(edtDialog!!.getText().length)
                }, 100)
            }
            return@OnFocusChangeListener
        }
        edtDialog!!.setOnFocusChangeListener(onFocusChangeListener)

        edtDialog!!.setOnClickListener {
            edtDialog!!.setSelection(edtDialog!!.getText().length)
        }
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.btn_accept.setOnClickListener {

            val textKg: String = edtDialog!!.text.toString()
            var textKg2: String = edtDialog!!.text.toString().trim()
            if (textKg != "") {
                if (textKg == ".") {
                    alertDialog.tv_title_dialog3.text = "Invalid weight"
                    val handler = Handler()
                    handler.postDelayed({
                        alertDialog.tv_title_dialog3.text = "My weight"
                        edtDialog!!.text = null
                    }, 500)
                } else {
                    val textKgFloat = textKg.toFloat()
                    if (10 <= textKgFloat && textKgFloat <= 200) {
                        val string = "$textKg"
                        val string2 = string[string.length-1].toString()
                        if(string2 == "."){
                            val textkgx = string.replace("[^\\d.]".toRegex(), "").toFloat()
                            val textkgxx = textkgx.toString()

                            val separated3 =
                                textkgxx.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            val chuoi1 = separated3[0].toInt()// this will contain "Fruit"
                            val chuoi2 = separated3[1].toInt()
                            if(chuoi2==0){
                                tv_kg.text = "$chuoi1"

                            }else{

                            }


                        }else{
                            tv_kg.text = "$textKg"
                        }

                        alertDialog.dismiss()
                    } else {
                        alertDialog.tv_title_dialog3.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            alertDialog.tv_title_dialog3.text = "My weight"
                            edtDialog!!.text = null
                        }, 500)
                    }
                }
            } else {
                tv_kg.text = "$textkg"
                alertDialog.dismiss()
            }

        }
    }

    private fun selectGender() {
        rl_gender.setOnClickListener {
            selectGenderChild()
        }
        tv_gender_title.setOnClickListener {
            selectGenderChild()
        }
    }

    fun selectGenderChild() {
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_select_gender, null)


        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tgMale = mAlertDialog.tgbtn_male
        tgFemale = mAlertDialog.tgbtn_female
        val textGender = tv_gender.text
        if (textGender == "Male") {
            tgMale!!.isChecked = true
            tgFemale!!.isChecked = false

        } else {
            tgMale!!.isChecked = false
            tgFemale!!.isChecked = true

        }


        mAlertDialog.rl_male.setOnClickListener {
            tgMale!!.isChecked = true
            tgFemale!!.isChecked = false


        }
        mAlertDialog.rl_female.setOnClickListener {
            tgMale!!.isChecked = false
            tgFemale!!.isChecked = true

        }
        mAlertDialog.btn_accept.setOnClickListener {
            val check = tgMale!!.isChecked
            if (check == true) {
                tv_gender.text = "Male"
            } else {
                tv_gender.text = "Female"
            }
            mAlertDialog.dismiss()
        }
    }
}
