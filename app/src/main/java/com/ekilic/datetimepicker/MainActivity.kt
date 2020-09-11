package com.ekilic.datetimepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ekilic.datetimeutil.DateTimePickDialog
import com.ekilic.datetimeutil.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    fun showPicker(@Suppress("UNUSED_PARAMETER")_v: View?) {
         val now = Calendar.getInstance()
            val mTimePicker = DateTimePickDialog(
                this, object : DateTimePickDialog.OnTimeSetListener {
                    override fun onTimeSet(
                        view: TimePicker?,
                        hourOfDay: Int,
                        minute: Int,
                        year: Int,
                        month: Int,
                        day: Int
                    ) {
                       time.text=  getString(R.string.propertime, day, month, year, hourOfDay, minute)
                    }
                },
                now[Calendar.HOUR_OF_DAY],
                now[Calendar.MINUTE]
            )
            mTimePicker.show()

    }


}