package com.example.dayout_organizer.ui.fragments.trips.CreateTrip;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.dayout_organizer.R;
import com.example.dayout_organizer.config.AppConstants;
import com.example.dayout_organizer.helpers.view.FN;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dayout_organizer.config.AppConstants.MAIN_FRC;


public class CreateTripFragment extends Fragment {


    View view;
    @BindView(R.id.title)
    TextInputEditText title;
    @BindView(R.id.title_textlayout)
    TextInputLayout titleTextlayout;
    @BindView(R.id.description)
    TextInputEditText description;
    @BindView(R.id.description_textlayout)
    TextInputLayout descriptionTextlayout;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.price)
    TextInputEditText price;
    @BindView(R.id.price_textlayout)
    TextInputLayout priceTextlayout;
    @BindView(R.id.next_btn)
    Button nextButton;


    String startDateValue;
    String endDateValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        startDate.setOnClickListener(onStartDateClicked);
        endDate.setOnClickListener(onEndDateClicked);
        nextButton.setOnClickListener(onNextClicked);
    }


    private final View.OnClickListener onStartDateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDateTimePickerDialog(1);
        }
    };

    private final View.OnClickListener onEndDateClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDateTimePickerDialog(2);
        }
    };


    private final View.OnClickListener onNextClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //  if (checkInfo())
            FN.addFixedNameFadeFragment(MAIN_FRC,requireActivity(),new CreateTripPlaceFragment());
        }
    };


    private void openDateTimePickerDialog(int type){

            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), R.style.MaterialCalendarTheme, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {


                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            c.set(Calendar.MINUTE,minute);

                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                            if (type == 1){
                                startDate.setText(simpleDateFormat.format(c.getTime()));
                                startDateValue = "data";
                            }
                            else {
                                endDate.setText(simpleDateFormat.format(c.getTime()));
                                endDateValue = "data";
                            }

                        }
                    };
                    new TimePickerDialog(requireContext(),timeSetListener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false).show();

                }
            }, mYear, mMonth, mDay);

            datePickerDialog.show();

            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setText("Ok");
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setText("Cancel");
    }

    private boolean checkInfo(){
        boolean ok = true;

        if (title.getText().toString().isEmpty()){
            titleTextlayout.setError("the filed is required");
            titleTextlayout.setErrorEnabled(true);
            ok = false;
        }
        else titleTextlayout.setErrorEnabled(false);


        if (description.getText().toString().isEmpty()){
            descriptionTextlayout.setError("the filed is required");
            descriptionTextlayout.setErrorEnabled(true);
            ok = false;
        }
        else descriptionTextlayout.setErrorEnabled(false);


        if (price.getText().toString().isEmpty()){
            priceTextlayout.setError("the filed is required");
            priceTextlayout.setErrorEnabled(true);
            ok = false;
        }
        else if (price.getText().toString().equals("0")){
            priceTextlayout.setError("the filed must be greater than zero");
            priceTextlayout.setErrorEnabled(true);
            ok = false;
        }

        return ok;
    }
}