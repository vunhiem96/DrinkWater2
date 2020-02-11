package com.kolorfox.watertracker.ui.settingsweight

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kolorfox.watertracker.R
import com.kolorfox.watertracker.data.ShareReference
import kotlinx.android.synthetic.main.activity_setting_weight.*
import kotlinx.android.synthetic.main.activity_setting_weight.checkbox
import kotlinx.android.synthetic.main.dialog2.*

class SetHeightTargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_weight)
        getData()
        backActivity()
        setTarget()
        setsHeight()
        setWeightTracker()

    }

    private fun setWeightTracker() {
        checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true) {
                ShareReference.setReminder2(true, this)
            } else {
                ShareReference.setReminder2(false, this)
            }
        })

    }

    private fun setTarget() {
        ln_target.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
            val alertDialog = mBuilder.show()
            val targetFloat = tv_target.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
            val edtKg = alertDialog.edt_daily_gold
            edtKg.setText(targetFloat.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg.setSelection(edtKg.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg.setOnFocusChangeListener(onFocusChangeListener)

            edtKg.setOnClickListener {
                edtKg.setSelection(edtKg.getText().length)
            }

            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.tv_title_dialog2.text = "Target weight"
            alertDialog.btn_accept_daily.setOnClickListener {

                val textKg: String = edtKg!!.text.toString()
                if (textKg != "") {
                    if (textKg == ".") {
                        alertDialog.tv_title_dialog2.text = "Invalid weight"
                        val handler = Handler()
                        handler.postDelayed({
                            alertDialog.tv_title_dialog2.text = "Target weight"
                            edtKg.text = null
                        }, 3000)
                    } else {
                        val kgFloat = textKg.toFloat()
                        if (10 <= kgFloat && kgFloat <= 200) {
                            tv_target.text = "$textKg kg"
                            alertDialog.dismiss()
                            val target = tv_target.text.toString()
                            val targetFloats = target.replace("[^\\d.]".toRegex(), "").toFloat()
                            ShareReference.setTargetWeight("$targetFloats", this)
                        } else {
                            alertDialog.tv_title_dialog2.text = "Invalid weight"
                            val handler = Handler()
                            handler.postDelayed({
                                alertDialog.tv_title_dialog2.text = "Target weight"
                                edtKg.text = null
                            }, 3000)
                        }
                    }
                } else {
                    tv_target.text = "$targetFloat kg"
                    alertDialog.dismiss()
                    val target = tv_target.text.toString()
                    val targetFloats = target!!.replace("[^\\d.]".toRegex(), "").toFloat()
                    ShareReference.setTargetWeight("$targetFloats", this)
                }

            }
        }
    }

    private fun setsHeight() {
        ln_height.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog2, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val height = tv_height.text!!.replace("[^\\d.]".toRegex(), "").toFloat()
            var edtKg = mAlertDialog.edt_daily_gold

            edtKg.setText(height.toString())

            val onFocusChangeListener = View.OnFocusChangeListener { view, b ->
                if (b) {
                    val handler = Handler()
                    handler.postDelayed({
                        edtKg.setSelection(edtKg.getText().length)
                    }, 100)
                }
                return@OnFocusChangeListener
            }
            edtKg.setOnFocusChangeListener(onFocusChangeListener)

            edtKg.setOnClickListener {
                edtKg.setSelection(edtKg.getText().length)
            }

            mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mAlertDialog.tv_title_dialog2.text = "My height"
            mAlertDialog.tv_ml2.text = "cm"



            mAlertDialog.btn_accept_daily.setOnClickListener {
                edtKg = mAlertDialog.edt_daily_gold
                val textCm: String = edtKg!!.text.toString()
                if (textCm != "") {
                    if (textCm == ".") {
                        mAlertDialog.tv_title_dialog2.text = "Invalid height"
                        val handler = Handler()
                        handler.postDelayed({
                            mAlertDialog.tv_title_dialog2.text = "My height"
                            edtKg!!.text = null
                        }, 4000)
                    } else {
                        val cm = textCm.toFloat()
                        if (30 < cm && cm < 250) {
                            tv_height.text = "$textCm cm"
                            val heightss = tv_height.text.toString()
                            val heightFloat = heightss!!.replace("[^\\d.]".toRegex(), "").toFloat()
                            ShareReference.setHeight("$heightFloat", this)
                            mAlertDialog.dismiss()
                        } else {
                            mAlertDialog.tv_title_dialog2.text = "Invalid height"
                            val handler = Handler()
                            handler.postDelayed({
                                mAlertDialog.tv_title_dialog2.text = "My height"
                                edtKg!!.text = null
                            }, 4000)
                        }

                    }

                } else {
//                    tv_height.text = "$height cm"
//                    val heights = tv_height.text.toString()
//                    val heightFloat = heights!!.replace("[^\\d.]".toRegex(), "").toFloat()
//                    ShareReference.setHeight("$heightFloat", this)
//                    mAlertDialog.dismiss()
                    mAlertDialog.tv_title_dialog2.text = "Invalid height"
                    val heights = tv_height.text.toString()
                    val handler = Handler()
                    handler.postDelayed({
                        mAlertDialog.tv_title_dialog2.text = "My height"
                        edtKg.setText(heights)
                    }, 4000)
                }

            }
        }
    }

    private fun backActivity() {
        img_back.setOnClickListener {
            finish()
        }
    }

    private fun getData() {
        val height = ShareReference.getHeight(this)
        tv_height.text = "$height cm"
        val targetWeight = ShareReference.getTargetWeight(this)
        tv_target.text = "$targetWeight kg"
        val check = ShareReference.getReminder2(this)
        checkbox.isChecked= check!!
    }


}
