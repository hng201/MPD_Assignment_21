package org.me.gcu.equakestartercode.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.me.gcu.equakestartercode.R;

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


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == etDate){
            dpd.show();
        }
        else if (v == etStartDate){
            dpd.show();
        }
        else if (v == etEndDate) {
            dpd.show();
        }
    }


}
