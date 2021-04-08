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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.me.gcu.equakestartercode.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Button btnHome;
    private RadioButton rbtnDate;
    private RadioButton rbtnDateRange;
    private DatePickerDialog dpd;
    private Calendar cal;
    private ViewSwitcher viewSwitcher;
    private boolean byDate;
    private RadioGroup radioGroup;
    private TextView tvError;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etDate = view.findViewById(R.id.etDate);
        etStartDate = view.findViewById(R.id.etStartDate);
        etEndDate = view.findViewById(R.id.etEndDate);
        btnSearchByDate = view.findViewById(R.id.btnSearchByDate);
        btnSearchByDateRange = view.findViewById(R.id.btnSearchByDateRange);
        btnHome = view.findViewById(R.id.btnHome);
        rbtnDate = view.findViewById(R.id.rbtnDate);
        rbtnDateRange = view.findViewById(R.id.rbtnDateRange);
        tvError = view.findViewById(R.id.tvError);

        etDate.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        btnSearchByDate.setOnClickListener(this);
        btnSearchByDateRange.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        radioGroup = view.findViewById(R.id.rgSearch);

        rbtnDate.toggle();
        rbtnDate.setOnClickListener(this);
        rbtnDateRange.setOnClickListener(this);

        radioGroup.setEnabled(true);
        viewSwitcher = view.findViewById(R.id.viewSwitcher);
        byDate = true;

        dpd = new DatePickerDialog(getContext(), 0);

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
            }
        });

        dpd.setOnDismissListener( new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Date temp = cal.getTime();
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
        else if (v == btnHome) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            // Replace current fragment with new fragment
            transaction.replace(R.id.frameLayout, homeFragment);
            transaction.commit();
        }
        else if (v == btnSearchByDate){
            if (etDate.getText().toString().equals("")){
                tvError.setText("Error: No Date Selected for Search");
            }
            else {
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                ArrayList<String> dates = new ArrayList<>();
                dates.add(etDate.getText().toString());
                searchResultFragment.setDates(dates);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                // Replace current fragment with new fragment
                transaction.replace(R.id.frameLayout, searchResultFragment);
                transaction.commit();
            }
        }
        else if (v == btnSearchByDateRange){
            if (etStartDate.getText().toString().equals("") || etEndDate.getText().toString().equals("")){
                tvError.setText("Error: Missing Date(s) for Date Range Search");
            }
            else {
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                ArrayList<String> dates = new ArrayList<>();
                dates.add(etStartDate.getText().toString());
                dates.add(etEndDate.getText().toString());
                searchResultFragment.setDates(dates);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                // Replace current fragment with new fragment
                transaction.replace(R.id.frameLayout, searchResultFragment);
                transaction.commit();
            }
        }
        else if (rbtnDate.isChecked()){
            if (!byDate){
                viewSwitcher.showNext();
                byDate = true;
            }
        }
        else if (rbtnDateRange.isChecked()){
            if (byDate){
                viewSwitcher.showNext();
                byDate = false;
            }
        }
    }


}
