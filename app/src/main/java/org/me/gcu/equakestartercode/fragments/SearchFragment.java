package org.me.gcu.equakestartercode.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.me.gcu.equakestartercode.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Shirley Ng S1626790
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private EditText etDate;
    private EditText etStartDate;
    private EditText etEndDate;
    private Button btnSearchByDate;
    private Button btnSearchByDateRange;
    private DatePickerDialog dpd;
    private Calendar cal;
    private Date date;
    private Date startDate;
    private Date endDate;
    private Boolean dateSelected;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etDate = view.findViewById(R.id.etDate);
        etStartDate = view.findViewById(R.id.etStartDate);
        etEndDate = view.findViewById(R.id.etEndDate);
        btnSearchByDate = view.findViewById(R.id.btnSearchByDate);
        btnSearchByDateRange = view.findViewById(R.id.btnSearchByDateRange);

        etDate.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        btnSearchByDate.setOnClickListener(this);
        btnSearchByDateRange.setOnClickListener(this);

        dpd = new DatePickerDialog(getContext(), 0);

        dateSelected = false;

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDate(EditText et){
        dpd.show();
        dpd.setOnDateSetListener( new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                dateSelected = true;
            }
        });

        dpd.setOnDismissListener( new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Date temp = cal.getTime();
                if (et == etDate) {
                    date = temp;
                } else if (etEndDate == etStartDate) {
                    startDate = temp;
                } else {
                    endDate = temp;
                }
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = sdf.format(temp);
                et.setText(strDate);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == etDate){
            setDate(etDate);

        }
        else if (v == etStartDate){
            setDate(etStartDate);
        }
        else if (v == etEndDate) {
            setDate(etEndDate);
        }
    }


}
