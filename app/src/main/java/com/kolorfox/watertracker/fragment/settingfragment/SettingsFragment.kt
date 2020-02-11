package com.kolorfox.watertracker.fragment.settingfragment


import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import com.kolorfox.watertracker.ui.privacypolice.PrivacyActivity
import com.kolorfox.watertracker.utils.newApp
import com.kolorfox.watertracker.utils.shareApp
import kotlinx.android.synthetic.main.activity_users_data.*
import kotlinx.android.synthetic.main.dialog_reminder_mode.*
import kotlinx.android.synthetic.main.dialog_seldect.*
import kotlinx.android.synthetic.main.dialog_select_gender.*
import kotlinx.android.synthetic.main.dialog_select_time.*
import kotlinx.android.synthetic.main.dialog_setdailywater.*
import java.text.DecimalFormat


class SettingsFragment : Fragment() {

    lateinit var tvGenderSetting: TextView
    lateinit var tvGoal: TextView
    lateinit var tvWeight: TextView
    lateinit var tvWakeUp: TextView
    lateinit var tvBedTime: TextView
    lateinit var tvInterval: TextView
    lateinit var tvReminderMode: TextView
    lateinit var layoutReminderMode: LinearLayout
    lateinit var allowReminder: Switch
    lateinit var layoutIntervalTime: LinearLayout
    lateinit var tgbtnRemiberOff: ToggleButton
    lateinit var tgbtnReminderVibrate: ToggleButton
    lateinit var tgbtnReminderRing: ToggleButton
    lateinit var layoutGender: LinearLayout
    lateinit var layoutGoal: LinearLayout
    lateinit var layoutWeight: LinearLayout
    lateinit var layoutWakeup: LinearLayout
    lateinit var layoutBedtime: LinearLayout
    lateinit var layoutShare: LinearLayout
    lateinit var layoutFeedback: LinearLayout
    lateinit var layoutAppDevelop: LinearLayout
    lateinit var layoutPrivacy: LinearLayout

    var hhwakeUp: Int = 0
    var mmwakeUp: Int = 0
    var hhbedTime: Int = 0
    var mmbedTime: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)

        tvGenderSetting = view.findViewById(R.id.tv_gender_settings)
        tvGoal = view.findViewById(R.id.tv_goal_settings)
        tvWeight = view.findViewById(R.id.tv_weight_settings)
        tvWakeUp = view.findViewById(R.id.tv_wakeup_time_settings)
        tvBedTime = view.findViewById(R.id.tv_bedtime_settings)
        tvInterval = view.findViewById(R.id.tv_interval_settings)
        tvReminderMode = view.findViewById(R.id.tv_reminder_mode)
        layoutReminderMode = view.findViewById(R.id.ln_reminder_mode)
        allowReminder = view.findViewById(R.id.check_reminder)
        layoutIntervalTime = view.findViewById(R.id.ln_interval_time_settings)
        layoutGender = view.findViewById(R.id.ln_genger_settings)
        layoutGoal = view.findViewById(R.id.ln_gold)
        layoutWeight = view.findViewById(R.id.ln_set_weight)
        layoutWakeup = view.findViewById(R.id.ln_set_wakeup)
        layoutBedtime = view.findViewById(R.id.ln_set_bedtime)
        layoutShare = view.findViewById(R.id.ln_share_settings)
        layoutFeedback = view.findViewById(R.id.ln_feed_back_setting)
        layoutAppDevelop = view.findViewById(R.id.ln_develop_settings)
        layoutPrivacy = view.findViewById(R.id.ln_privacy_setting)

        getData()
        allowReminder()
        selectIntervalTime()
        selecteReminderMode()
        selectGender()
        setDailyGoal()
        setWeight()
        setTimeWakeUp()
        setBedTime()
        shareApplication()
        feedBack()
        appDevelop()
        privacyAcivity()


        return view
    }

    private fun privacyAcivity() {
        layoutPrivacy.setOnClickListener {
            val intent = Intent(context, PrivacyActivity::class.java)
            context!!.startActivity(intent)
        }
    }

    private fun appDevelop() {
        layoutAppDevelop.setOnClickListener {
            val navatech = getString(R.string.id_developer)
            newApp(navatech)
        }
    }

    private fun feedBack() {
        layoutFeedback.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + context!!.packageName)
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context!!.packageName)
                    )
                )
            }
        }
    }

    private fun shareApplication() {
        layoutShare.setOnClickListener {
            shareApp()
        }
    }

    private fun setBedTime() {

        layoutBedtime.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_select_time, null)
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()

            val currentString = context?.let { it1 -> ShareReference.getBedTimes(it1) }
            val separated =
                currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val x = separated[0].toInt()// this will contain "Fruit"
            val y = separated[1].toInt()

            mAlertDialog.tv_title_dialog.text = "Bed time"
            mAlertDialog.number_picker.value = x
            mAlertDialog.number_picker2.value = y
            hhbedTime = x
            mmbedTime = y

            mAlertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.number_picker.setOnValueChangedListener(object :
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
                        hhbedTime = newVal
                    } else {
                        hhbedTime = oldVal
                    }
                }


            })
            mAlertDialog.number_picker2.setOnValueChangedListener(object :
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
                        mmbedTime = newVal
                    } else {
                        mmbedTime = oldVal
                    }
                }

            })

            mAlertDialog.btn_accept_time.setOnClickListener {

                if (hhbedTime < 10 && mmbedTime < 10) {
                    tvBedTime.text = "0$hhbedTime:0$mmbedTime"
                } else if (hhbedTime < 10 && mmbedTime > 9) {
                    tvBedTime.text = "0$hhbedTime:$mmbedTime"
                } else if (hhbedTime > 10 && mmbedTime < 10) {
                    tvBedTime.text = "$hhbedTime:0$mmbedTime"
                } else {
                    tvBedTime.text = "$hhbedTime:$mmbedTime"
                }

                ShareReference.setBedTimes(tvBedTime.text.toString(), context!!)

                mAlertDialog.dismiss()
            }
        }
    }

    private fun setTimeWakeUp() {
        layoutWakeup.setOnClickListener {

            val mDialogView =
                LayoutInflater.from(context)
                    .inflate(R.layout.dialog_select_time, null)


            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()


            val currentString = context?.let { it1 -> ShareReference.getWakeUpTime(it1) }
            val separated =
                currentString!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val x = separated[0].toInt()// this will contain "Fruit"
            val y = separated[1].toInt()
            mAlertDialog.number_picker.value = x
            mAlertDialog.number_picker2.value = y
            hhwakeUp = x
            mmwakeUp = y
            mAlertDialog.number_picker.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.number_picker2.setFormatter(R.string.number_picker_formatter)
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.number_picker.setOnValueChangedListener(object :
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
                        hhwakeUp = newVal
                    } else {
                        hhwakeUp = oldVal
                    }
                }


            })
            mAlertDialog.number_picker2.setOnValueChangedListener(object :
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
                        mmwakeUp = newVal
                    } else {
                        mmwakeUp = oldVal
                    }
                }

            })

            mAlertDialog.btn_accept_time.setOnClickListener {
                if (hhwakeUp == 0 && mmwakeUp == 0) {
                    tvWakeUp.text = "$x:$y"
                }
                if (hhwakeUp < 10 && mmwakeUp < 10) {
                    tvWakeUp.text = "0$hhwakeUp:0$mmwakeUp"
                } else if (hhwakeUp < 10 && mmwakeUp > 9) {
                    tvWakeUp.text = "0$hhwakeUp:$mmwakeUp"
                } else if (hhwakeUp > 10 && mmwakeUp < 10) {
                    tvWakeUp.text = "$hhwakeUp:0$mmwakeUp"
                } else {
                    tvWakeUp.text = "$hhwakeUp:$mmwakeUp"
                }

                ShareReference.setWakeUpTime(tvWakeUp.text.toString(), context!!)

                mAlertDialog.dismiss()
            }
        }
    }

    private fun setWeight() {

        layoutWeight.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_seldect, null)

            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(dialogView)
            }
            val alertDialog = mBuilder!!.show()



            val weight =
                context?.let { it1 -> ShareReference.getWeights(it1) }!!.replace(
                    "[^\\d.]".toRegex(),
                    ""
                )
                    .toFloat()
            var edtKg: EditText = dialogView.findViewById(R.id.edt_kg)





            val weight2 = weight.toString()

            val separated =
                weight2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val x = separated[0].toInt()// this will contain "Fruit"
            val y = separated[1].toInt()


            edtKg = alertDialog.edt_kg
            if(y == 0){
                edtKg!!.setText(x.toString())
            }else{
                edtKg!!.setText(weight.toString())
            }

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg.setSelection(edtKg.getText().length)
                    }, 50)
                }
                return@OnFocusChangeListener
            }
            edtKg.setOnFocusChangeListener(onFocusChangeListener)

            edtKg.setOnClickListener {
                edtKg.setSelection(edtKg.getText().length)
            }






            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnAccept: Button = dialogView.findViewById(R.id.btn_accept)
            btnAccept.setOnClickListener {
                val textKg: String = edtKg!!.text.toString()

                if (textKg != "") {
                    if (textKg == ".") {
                        alertDialog.tv_title_dialog3.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            alertDialog.tv_title_dialog3.text = "My weight"
                            edtKg.text = null
                        }, 1500)
                    } else {
                        val weight = textKg.toFloat()
                        if (10 <= weight && weight <= 200) {
                            tvWeight.text = "$textKg kg"
                            val weightString = tvWeight.text.toString()
                            val weightFloat =
                                weightString!!.replace("[^\\d.]".toRegex(), "").toFloat()

                            val weight3 = weightFloat.toString()

                            val string =
                                weight3.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            var a = string[0].toInt()// this will contain "Fruit"
                            var b = string[1].toInt()

                            if(b==0){
                                context?.let { it1 -> ShareReference.setWeights("$a", it1) }
                            }else{
                                context?.let { it1 -> ShareReference.setWeights("$weightFloat", it1) }
                            }


                            val ml = weightFloat / 0.03
                            val twoDForm = DecimalFormat("#")
                            val weightNew = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
                            ShareReference.setGoalDrink("$weightNew", context!!)
                            getData()
                            alertDialog.dismiss()
                        } else {
                            alertDialog.tv_title_dialog3.text = "Invalid weight"
                            val handler = Handler()
                            handler.postDelayed({
                                alertDialog.tv_title_dialog3.text = "My weight"
                                edtKg.text = null
                            }, 1500)
                        }

                    }
                } else {
//                    tvWeight.text = "$weight kg"
//                    val weightString = tvWeight.text.toString()
//                    val weightFloat = weightString!!.replace("[^\\d.]".toRegex(), "").toFloat()
//                    context?.let { it1 -> ShareReference.setWeights("$weightFloat", it1) }
//                    val ml = weightFloat / 0.03
//                    val twoDForm = DecimalFormat("#")
//                    val weightNew = java.lang.Double.valueOf(twoDForm.format(ml)).toInt()
//                    ShareReference.setGoalDrink("$weightNew", context!!)
//                    alertDialog.dismiss()
                    alertDialog.tv_title_dialog3.text = "Invalid weight"
                    val handler = Handler()
                    handler.postDelayed({
                        alertDialog.tv_title_dialog3.text = "My weight"
                        val weightString = tvWeight.text.toString()
                        edtKg.setText(weightString)
                    }, 1500)
                }


            }
        }
    }

    private fun setDailyGoal() {
        layoutGoal.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_setdailywater, null)

            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            val goal = context?.let { it1 -> ShareReference.getGoalDrink(it1) }!!.replace(
                "[^\\d.]".toRegex(),
                ""
            ).toInt()
            var edtMl: EditText = mDialogView.findViewById(R.id.edt_daily_gold)
            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtMl.setSelection(edtMl.getText().length)
                    }, 50)
                }
                return@OnFocusChangeListener
            }
            edtMl.setOnFocusChangeListener(onFocusChangeListener)

            edtMl.setOnClickListener {
                edtMl.setSelection(edtMl.getText().length)
            }

            edtMl = mAlertDialog.edt_daily_gold
            edtMl.setText(goal.toString())


            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val btnAccept: Button = mDialogView.findViewById(R.id.btn_accept_daily)
            btnAccept.setOnClickListener {

                val textMl: String = edtMl!!.text.toString()
                if (textMl == "") {

                    mAlertDialog.tv_title_dialog2.text = "Invalid Daily Goal"
                    val handler = Handler()
                    handler.postDelayed({
                        mAlertDialog.tv_title_dialog2.text = "Daily Goal"
                        val waterold = goal.toString()
                        edtMl.setText(waterold)
                        edtMl.setSelection(edtMl.getText().length)


                    }, 1500)
                } else {
                    val dailygold = Integer.parseInt(textMl)
                    if (dailygold >= 100 && dailygold <= 6667) {

                        val goalOld = ShareReference.getWaterLevel(context!!)!!.toInt()
                        ShareReference.setDailyGold(false, context!!)
                        tvGoal.text = "$textMl ml"
                        val tvGoal = tvGoal.text.toString()
                        val tvGoalInt = tvGoal!!.replace("[^\\d.]".toRegex(), "").toInt()
                        context?.let { it1 -> ShareReference.setGoalDrink("$tvGoalInt", it1) }

                        if(tvGoalInt > goalOld){
                            ShareReference.setCheckFull(false, context!!)
                        }
                        mAlertDialog.dismiss()
                    }

                    if (dailygold < 100 || dailygold > 4000) {
                        mAlertDialog.tv_title_dialog2.text = "Invalid Daily Goal"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog2.text = "Daily Goal"
                            val waterold = goal.toString()
                            edtMl.setText(waterold)
                            edtMl.setSelection(edtMl.getText().length)


                        }, 1500)
                    }
                }


            }
        }
    }

    private fun selectGender() {
        layoutGender.setOnClickListener {
            val btnAccept: Button
            val dialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_select_gender, null)
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(dialogView)
            }
            val alertDialog = mBuilder!!.show()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            btnAccept = dialogView.findViewById(R.id.btn_accept)

            val tgbtnMale: ToggleButton = dialogView.findViewById(R.id.tgbtn_male)
            val tgbtnFemale: ToggleButton = dialogView.findViewById(R.id.tgbtn_female)
            val gender = tvGenderSetting.text
            if (gender == "Male") {
                tgbtnMale.isChecked = true
                tgbtnFemale.isChecked = false

            } else {
                tgbtnMale.isChecked = false
                tgbtnFemale.isChecked = true

            }
            alertDialog.rl_male.setOnClickListener {
                tgbtnMale.isChecked = true
                tgbtnFemale.isChecked = false


            }
            alertDialog.rl_female.setOnClickListener {
                tgbtnMale.isChecked = false
                tgbtnFemale.isChecked = true


            }
            btnAccept.setOnClickListener {
                val x = tgbtnMale.isChecked
                if (x == true) {
                    ShareReference.setGender("Male", context!!)
                    tvGenderSetting.text = "Male"
                } else {
                    ShareReference.setGender("Female", context!!)
                    tvGenderSetting.text = "Female"
                }
                alertDialog.dismiss()
            }
        }
    }

    private fun selecteReminderMode() {
        layoutReminderMode.setOnClickListener {

            val btnAccept: Button
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.dialog_reminder_mode, null)
            val mBuilder = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setView(mDialogView)
            }

            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tgbtnRemiberOff = mDialogView.findViewById(R.id.tgbtn_reminderoff)
            tgbtnReminderVibrate = mDialogView.findViewById(R.id.tgbtn_reminder_vibrate)
            tgbtnReminderRing = mDialogView.findViewById(R.id.tgbtn_reminderring)
            val z = context?.let { ShareReference.getRemiderMode(it) }
            if (z == 0) {
                tgbtnRemiberOff.isChecked = true
                tgbtnReminderVibrate.isChecked = false
                tgbtnReminderRing.isChecked = false
            }
            if (z == 1) {
                tgbtnRemiberOff.isChecked = false
                tgbtnReminderVibrate.isChecked = true
                tgbtnReminderRing.isChecked = false
            }
            if (z == 2) {
                tgbtnRemiberOff.isChecked = false
                tgbtnReminderVibrate.isChecked = false
                tgbtnReminderRing.isChecked = true
            }
            btnAccept = mDialogView.findViewById(R.id.btn_accept)

            var temp = ShareReference.getRemiderMode(context!!)
            mAlertDialog.rl_reminderoff.setOnClickListener {
                temp = 0
                tgbtnRemiberOff.isChecked = true
                tgbtnReminderVibrate.isChecked = false
                tgbtnReminderRing.isChecked = false


            }
            mAlertDialog.rl_reminder_vibrate.setOnClickListener {
                temp = 1
                tgbtnRemiberOff.isChecked = false
                tgbtnReminderVibrate.isChecked = true
                tgbtnReminderRing.isChecked = false

            }
            mAlertDialog.rl_reminder_ring.setOnClickListener {
                temp = 2
                tgbtnRemiberOff.isChecked = false
                tgbtnReminderVibrate.isChecked = false
                tgbtnReminderRing.isChecked = true

            }
            btnAccept.setOnClickListener {
                if (tgbtnRemiberOff.isChecked == true) {
                    tvReminderMode.text = "Reminder off"
                    context?.let { it1 -> ShareReference.setRemiderMode(0, it1) }
                } else if (tgbtnReminderVibrate.isChecked == true) {
                    tvReminderMode.text = "Vibrate only"
                    context?.let { it1 -> ShareReference.setRemiderMode(1, it1) }
                } else if (tgbtnReminderRing.isChecked == true) {
                    tvReminderMode.text = "Ringtone with vibrate"
                    context?.let { it1 -> ShareReference.setRemiderMode(2, it1) }
                }
                mAlertDialog.dismiss()
            }

        }
    }

    private fun selectIntervalTime() {
        layoutIntervalTime.setOnClickListener {
            val dialogView =
                LayoutInflater.from(context!!).inflate(R.layout.dialog_interval_select, null)

            val tgBtn30min: ToggleButton = dialogView.findViewById(R.id.tgbtn_30)
            val tgBtn60min: ToggleButton = dialogView.findViewById(R.id.tgbtn_1)
            val tgBtn90min: ToggleButton = dialogView.findViewById(R.id.tgbtn_130)
            val tgBtn120min: ToggleButton = dialogView.findViewById(R.id.tgbtn_2)


            val rl_30min: RelativeLayout = dialogView.findViewById(R.id.rl_30)
            val rl_60min: RelativeLayout = dialogView.findViewById(R.id.rl_1)
            val rl_90min: RelativeLayout = dialogView.findViewById(R.id.rl_130)
            val rl_120min: RelativeLayout = dialogView.findViewById(R.id.rl_2)

            val btnAccept: Button = dialogView.findViewById(R.id.btn_accept_interval_time)
            val mBuilder = AlertDialog.Builder(context!!)
                .setView(dialogView)
            val alertDialog = mBuilder.show()
            val time = ShareReference.getIntervalTime(context!!)
            if (time == "30 min") {
                tgBtn30min.isChecked = true
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = false


            } else if (time == "60 min") {
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = true
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = false


            } else if (time == "90 min") {
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = true
                tgBtn120min.isChecked = false

            } else if (time == "120 min") {
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = true


            }

            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var temporary = 0
            rl_30min.setOnClickListener {
                temporary = 1
                tgBtn30min.isChecked = true
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = false
            }
            rl_60min.setOnClickListener {
                temporary = 2
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = true
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = false
            }
            rl_90min.setOnClickListener {
                temporary = 3
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = true
                tgBtn120min.isChecked = false

            }
            rl_120min.setOnClickListener {
                temporary = 4
                tgBtn30min.isChecked = false
                tgBtn60min.isChecked = false
                tgBtn90min.isChecked = false
                tgBtn120min.isChecked = true

            }

            btnAccept.setOnClickListener {
                if (temporary == 1) {
                    tvInterval.text = "30 min"
                    ShareReference.setIntervalTimes("30 min", context!!)
                    val time = ShareReference.getTimeNextDrink30(context!!).toString()
                    ShareReference.setTimeNextDrink(time, context!!)
                } else if (temporary == 2) {
                    tvInterval.text = "1 hour"
                    ShareReference.setIntervalTimes("60 min", context!!)
                    val time = ShareReference.getTimeNextDrink60(context!!).toString()
                    ShareReference.setTimeNextDrink(time, context!!)
                } else if (temporary == 3) {
                    val time = ShareReference.getTimeNextDrink90(context!!).toString()
                    ShareReference.setTimeNextDrink(time, context!!)
                    tvInterval.text = "90 min"
                    ShareReference.setIntervalTimes("90 min", context!!)
                } else if (temporary == 4) {
                    val time = ShareReference.getTimeNextDrink120(context!!).toString()
                    ShareReference.setTimeNextDrink(time, context!!)
                    ShareReference.setIntervalTimes("120 min", context!!)
                    tvInterval.text = "2 hours"
                }
                alertDialog.dismiss()
            }
        }
    }

    private fun allowReminder() {
        allowReminder.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) {
                layoutReminderMode.isClickable = true
                tvReminderMode.setTextColor(Color.parseColor("#464646"))
                context?.let { it1 -> ShareReference.setReminder(true, it1) }
            } else {
                layoutReminderMode.isClickable = false
                context?.let { it1 -> ShareReference.setReminder(false, it1) }
                tvReminderMode.setTextColor(Color.parseColor("#B1AFAF"))

            }
        })
    }

    override fun onStart() {
        super.onStart()
        getData()

    }

    private fun getData() {
        val gender = ShareReference.getGender(context!!)
        val goal = ShareReference.getGoalDrink(context!!)
        val weight = ShareReference.getWeights(context!!)
        val wakeUp = ShareReference.getWakeUpTime(context!!)
        val bedTime = ShareReference.getBedTimes(context!!)
        val mode = ShareReference.getRemiderMode(context!!)

        if (mode == 0) {
            tvReminderMode.text = "Reminder off"
        }
        if (mode == 1) {
            tvReminderMode.text = "Vibrate only"
        }
        if (mode == 2) {
            tvReminderMode.text = "Ringtone with vibrate"
        }



        allowReminder.isChecked = ShareReference.getReminder(context!!)!!

        var reminder = ShareReference.getReminder(context!!)
        if (reminder == true) {
            layoutReminderMode.isClickable = true
            tvReminderMode.setTextColor(Color.parseColor("#000000"))
        }
        if (reminder == false) {
//            val h = Handler()
//            h.post(
//                object : Runnable {
//                    override fun run() {
                        layoutReminderMode.isClickable = false
//                        h.postDelayed(this, 500)

//                    }
//                })
            tvReminderMode.setTextColor(Color.parseColor("#B1AFAF"))
        }

        val timeInterval = ShareReference.getIntervalTime(context!!)
        if (timeInterval == "30 min") {
            tvInterval.text = "30 min"
        } else if (timeInterval == "60 min") {
            tvInterval.text = "1 hour"
        } else if (timeInterval == "90 min") {
            tvInterval.text = "90 min"
        } else if (timeInterval == "120 min") {
            tvInterval.text = "2 hour"
        } else if (timeInterval == "180 min") {
            tvInterval.text = "3 hour"
        }


        tvGenderSetting.text = "$gender"
        tvGoal.text = "$goal ml"
        tvWeight.text = "$weight kg"
        tvWakeUp.text = "$wakeUp"
        tvBedTime.text = "$bedTime"

    }


}
