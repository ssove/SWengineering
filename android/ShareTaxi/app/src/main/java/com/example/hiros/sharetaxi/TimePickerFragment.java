package com.example.hiros.sharetaxi;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment
                            implements TimePickerDialog.OnTimeSetListener {

    private OnTimePickerSetListener onTimePickerSetListener;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        // Do something with the time chosen by the user
        onTimePickerSetListener.onTimePickerSet(hourOfDay, minute);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Calendar mCalendar = Calendar.getInstance();
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int min = mCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                getContext(), this, hour, min, DateFormat.is24HourFormat(getContext())
        );
        return mTimePickerDialog;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof OnTimePickerSetListener)
        {
            onTimePickerSetListener = (OnTimePickerSetListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimePickerSetListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        onTimePickerSetListener = null;
    }
}
