package com.modernapps.agecalc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.modernapps.agecalc.databinding.ActivityMainBinding;

import org.joda.time.DateTime;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    DatePickerDialog.OnDateSetListener dateSet1, dateSet2;
    Button dob, today;
    TextView year, agemd, nextDay, nextDm, ymw, hdm;

    @SuppressLint({"NewApi", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        dob = binding.bdob;
        today = binding.btoday;

        year = binding.year;
        agemd = binding.agemd;
        nextDay = binding.nbd;
        nextDm = binding.nmd;
        ymw = binding.ymw;
        hdm = binding.dhm;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        String date = sdf.format( android.icu.util.Calendar.getInstance().getTime());
        today.setText(date);

        dob.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSet1,
                    year, month, day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                    Color.TRANSPARENT));

            datePickerDialog.show();

        });

        dateSet1 = (datePicker, year1, month1, day1) -> {

            month1++;
            String date1 = day1 + "/" + month1 + "/" + year1;
            dob.setText(date1);

                calculate();
        };

        today.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSet2,
                    year,
                    month,
                    day);

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                    Color.TRANSPARENT));

            datePickerDialog.show();

        });
        dateSet2 = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month++;
                String date = day + "/" + month + "/" + year;
                today.setText(date);

            }
        };
    }



//        @SuppressLint("NewApi")
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        void calculat () throws ParseException {
//
//
//                String sDate = dob.getText().toString();
//                String eDate = today.getText().toString();
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" dd/MM/yyyy");
//
//
//                Date date1 = simpleDateFormat.parse(sDate);
//                Date date2 = simpleDateFormat.parse(eDate);
//
//                long d =(date1.getDay()-date2.getDay());

//                DateTime startDate = new DateTime(date1);
//                DateTime endDate = new DateTime(date2);
//
//                int years = Years.yearsBetween(startDate, endDate).getYears();

//                year.setText(Math.toIntExact((Long) d));}


////                long startDate = date1.getTime();
////                long endDate = date2.getTime();
//
//
////                period = new Period(startDate, endDate ,PeriodType.yearMonthDay());
////
////
////
//////                if (startDate <= endDate) {
////                    Period period;
////                   @SuppressLint({"NewApi", "LocalSuppress"}) int years = period.getYears();
////                    @SuppressLint({"NewApi", "LocalSuppress"}) int months = period.getMonths();
////                    @SuppressLint({"NewApi", "LocalSuppress"}) int days = period.getDays();
//
////                } else {
////                    Toast.makeText(getApplicationContext(), "date of birth must less than today", Toast.LENGTH_SHORT).show();
////                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }



 /*

    public void getDateDiff(View view) {
        String sDate = dob.getText().toString();
        String eDate = today.getText().toString();

    org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(Locale.US);
    DateTime startDate =null;
    DateTime endDate = null;

    // expected output 23...2....29

    try {
        startDate = formatter.parseDateTime(sDate);
        endDate = formatter.parseDateTime(eDate);

        Period period = new Period(startDate, endDate ,PeriodType.yearMonthDay());

    } catch (Exception e) {
        e.printStackTrace();
    }
}*/

                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void calculate () {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.US);

                    String sDate = dob.getText().toString();
                    String eDate = today.getText().toString();

                    try {
                        LocalDate startDate = LocalDate.parse(sDate,formatter);

                        LocalDate endDate = LocalDate.parse(eDate, formatter);


                     int y =  endDate.getYear() - startDate.getYear() ;
                        Period period = Period.between(startDate, endDate);
//                        String y = String.valueOf(period.get(ChronoUnit.YEARS));
                        String m = String.valueOf(period.getMonths());
                       String d = String.valueOf(period.getDays());

                       year.setText(y + " " + "Years");
                       agemd.setText(m + "  " + "Months" + "  " + "|" + "  " + d + "  " + "Days");

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "try agin", Toast.LENGTH_SHORT).show();
                    }
                }}
