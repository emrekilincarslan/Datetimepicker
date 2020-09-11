package com.ekilic.datetimeutil

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.NumberPicker
import java.util.*

class TimePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    // state
    var mCurrentHour = 0 // 0-23
    private var mCurrentMinute = 0 // 0-59

    // state
    private val mRealHour // 0-23
            : Int
    private val mRealMinute // 0-59
            : Int
    var mSelectedyear
            : Int
    var mSelectedMonth
            : Int
    var mSelectedDate
            : Int

    // ui components
    private val mHourPicker: NumberPicker
    private val mMinutePicker: NumberPicker

    // callbacks
    private var mOnTimeChangedListener: OnTimeChangedListener? = null

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    interface OnTimeChangedListener {
        /**
         * @param view      The view associated with this listener.
         * @param hourOfDay The current hour.
         * @param minute    The current minute.
         */
        fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int)
    }

    fun limitMinutesIfNeeded(newVal: Int, cal: Calendar) {
        if (mSelectedyear == cal[Calendar.YEAR] &&
            mSelectedMonth == cal[Calendar.MONTH] &&
            mSelectedDate == cal[Calendar.DAY_OF_MONTH]
        ) {
            if (newVal == mRealHour) {
                mMinutePicker.minValue = mRealMinute
                mCurrentMinute = mRealMinute
            } else {
                mMinutePicker.minValue = 0
            }
        } else {
            mMinutePicker.minValue = 0
        }
        mCurrentHour = newVal
        onTimeChanged()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        mMinutePicker.isEnabled = enabled
        mHourPicker.isEnabled = enabled
    }

    /**
     * Used to save / restore state of time picker
     */
    private class SavedState(superState: Parcelable?, val hour: Int, val minute: Int) :
        BaseSavedState(superState) {


        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(hour)
            dest.writeInt(minute)
        }


    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return SavedState(superState, mCurrentHour, mCurrentMinute)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        currentHour = ss.hour
        currentMinute = ss.minute
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     *
     * @param onTimeChangedListener the callback, should not be null.
     */
    fun setOnTimeChangedListener(onTimeChangedListener: OnTimeChangedListener?) {
        mOnTimeChangedListener = onTimeChangedListener
    }
    /**
     * @return The current hour (0-23).
     */
    /**
     * Set the current hour.
     */
    var currentHour: Int
        get() = mCurrentHour
        set(currentHour) {
            mCurrentHour = currentHour
            updateHourDisplay()
        }
    /**
     * @return The current minute.
     */
    /**
     * Set the current minute (0-59).
     */
    var currentMinute: Int
        get() = mCurrentMinute
        set(currentMinute) {
            mCurrentMinute = currentMinute
            updateMinuteDisplay()
        }

    override fun getBaseline(): Int {
        return mHourPicker.baseline
    }

    /**
     * Set the state of the spinners appropriate to the current hour.
     */
    private fun updateHourDisplay() {
        val currentHour = mCurrentHour
        mHourPicker.value = currentHour
        onTimeChanged()
    }

    private fun configureHourPickerRanges() {
        mHourPicker.minValue = mCurrentHour
        mHourPicker.maxValue = 23
        mHourPicker.setFormatter(TWO_DIGIT_FORMATTER)
    }

    private fun onTimeChanged() {
        mOnTimeChangedListener!!.onTimeChanged(this, currentHour, currentMinute)
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private fun updateMinuteDisplay() {
        mMinutePicker.value = mCurrentMinute
        mOnTimeChangedListener?.onTimeChanged(this, currentHour, currentMinute)
    }

    companion object {
        /**
         * A no-op callback used in the constructor to avoid null checks
         * later in the code.
         */
        private val NO_OP_CHANGE_LISTENER: OnTimeChangedListener = object : OnTimeChangedListener {
            override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {}
        }
        val TWO_DIGIT_FORMATTER = NumberPicker.Formatter { value -> String.format("%02d", value) }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(
            R.layout.time_picker_widget,
            this,  // we are the parent
            true
        )

        // hour
        mHourPicker = findViewById<View>(R.id.hour) as NumberPicker
        mHourPicker.wrapSelectorWheel = false;


        // digits of minute
        mMinutePicker = findViewById<View>(R.id.minute) as NumberPicker
        mMinutePicker.wrapSelectorWheel = false;
        mMinutePicker.minValue = 0
        mMinutePicker.maxValue = 59
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER)
        mMinutePicker.setOnValueChangedListener { _, _, newVal ->
            mCurrentMinute = newVal
            onTimeChanged()
        }


        // initialize to current time
        val cal = Calendar.getInstance()
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER)
        mRealHour = cal[Calendar.HOUR_OF_DAY]
        mRealMinute = cal[Calendar.MINUTE]
        mSelectedyear = cal[Calendar.YEAR]
        mSelectedMonth = cal[Calendar.MONTH]
        mSelectedDate = cal[Calendar.DAY_OF_MONTH]
        currentHour = mRealHour
        currentMinute = mRealMinute
        mMinutePicker.minValue = mRealMinute
        mHourPicker.setOnValueChangedListener { _, _, newVal ->
            limitMinutesIfNeeded(newVal, cal)
        }
        configureHourPickerRanges()
        if (!isEnabled) {
            isEnabled = false
        }
    }
}