/*
 * Copyright (C) 2007 The Android Open Source Project
 * Copyright (C) 2013 Ivan Kovac  navratnanos@gmail.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ekilic.datetimeutil

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.CalendarView
import java.text.DateFormat
import java.util.*


/**
 * A dialog that prompts the user for the time of day using a [TimePicker].
 */
class DateTimePickDialog(
    context: Context,
    theme: Int,
    callBack: OnTimeSetListener?,
    hourOfDay: Int, minute: Int
) : AlertDialog(context, theme), TimePicker.OnTimeChangedListener,
    View.OnClickListener {
    /**
     * The callback interface used to indicate the user is done filling in
     * the time (they clicked on the 'Set' button).
     */
    interface OnTimeSetListener {
        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute The minute that was set.
         */
        fun onTimeSet(
            view: TimePicker?,
            hourOfDay: Int,
            minute: Int,
            year: Int,
            month: Int,
            day: Int
        )
    }

    private var selectedYear: Int=0
    private var selectedDate: Int=0
    private var selectedMonth: Int=0

    private var mButtonCancel: Button
    private var mButtonDone: Button
    private var mCalendarView: CalendarView
    private val mTimePicker: TimePicker
    private val mCallback: OnTimeSetListener?
    private val mDateFormat: DateFormat
    private var mInitialHourOfDay: Int
    private var mInitialMinute: Int

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     */
    constructor(
        context: Context,
        callBack: OnTimeSetListener?,
        hourOfDay: Int, minute: Int
    ) : this(
        context, 0,
        callBack, hourOfDay, minute
    )


    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }

    override fun onSaveInstanceState(): Bundle {
        val state = super.onSaveInstanceState()
        state.putInt(HOUR, mTimePicker.currentHour)
        state.putInt(MINUTE, mTimePicker.currentMinute)
        return state
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val hour = savedInstanceState.getInt(HOUR)
        val minute = savedInstanceState.getInt(MINUTE)
        mTimePicker.currentHour = hour
        mTimePicker.currentMinute = minute
        mTimePicker.setOnTimeChangedListener(this)
    }

    companion object {
        private const val HOUR = "hour"
        private const val MINUTE = "minute"
    }


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val calendar = Calendar.getInstance()
        mCallback = callBack
        mInitialHourOfDay = hourOfDay
        mInitialMinute = minute
        mDateFormat = android.text.format.DateFormat.getTimeFormat(context)
        selectedDate=calendar.get(Calendar.DAY_OF_MONTH)
        selectedMonth=calendar.get(Calendar.MONTH)
        selectedYear=calendar.get(Calendar.YEAR)
        val view: View = View.inflate(context,R.layout.time_picker_dialog ,null)

        setView(view)
        mTimePicker = view.findViewById<View>(R.id.timePicker) as TimePicker
        mCalendarView = view.findViewById<View>(R.id.mycalendarview) as CalendarView
        mCalendarView.minDate = calendar.timeInMillis

        mCalendarView.setOnDateChangeListener { _, year, month, date ->
            selectedYear = year
            selectedDate = date
            selectedMonth = month
            mTimePicker.mSelectedyear = year
            mTimePicker.mSelectedMonth = month
            mTimePicker.mSelectedDate = date
            mTimePicker.limitMinutesIfNeeded(mTimePicker.mCurrentHour,calendar)
        }


        mButtonDone = view.findViewById<View>(R.id.btn_done) as Button
        mButtonCancel = view.findViewById<View>(R.id.btn_cancel) as Button

        mButtonDone.setOnClickListener(this)
        mButtonCancel.setOnClickListener { dismiss() }

        // initialize state
        mTimePicker.currentHour = mInitialHourOfDay
        mTimePicker.currentMinute = mInitialMinute
        mTimePicker.setOnTimeChangedListener(this)

    }



    override fun onClick(_btn: View?) {
        if (mCallback != null) {
            mTimePicker.clearFocus()
            mCallback.onTimeSet(
                mTimePicker,
                mTimePicker.currentHour,
                mTimePicker.currentMinute,
               selectedYear,
                selectedMonth,
                selectedDate
            )
        }

        dismiss()
    }
}