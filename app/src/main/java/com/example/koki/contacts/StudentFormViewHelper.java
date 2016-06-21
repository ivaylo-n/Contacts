package com.example.koki.contacts;


import android.app.Activity;
import android.widget.EditText;
import android.widget.RatingBar;

public class StudentFormViewHelper {
    private final Activity activity;

    public StudentFormViewHelper(Activity activity) {
        this.activity = activity;
    }

    private String getName() {
        return getTextFieldValue(R.id.student_form_name);
    }

    private String getTextFieldValue(int fieldId) {
        EditText field = (EditText) activity.findViewById(fieldId);
        String value = field.getText().toString();
        return value;
    }

    private String getAddress() {
        return getTextFieldValue(R.id.student_form_address);
    }

    private String getPhoneNumber() {
        return getTextFieldValue(R.id.student_form_phone_number);
    }

    public Student createAStudent() {
        return new Student(getName(),getAddress(),getPhoneNumber(), getWebsite(), getEmail(), getGrading());
    }

    private double getGrading() {
       RatingBar rating = (RatingBar) activity.findViewById(R.id.student_form_grading);
        return rating.getRating();
    }

    private String getWebsite() {
        return getTextFieldValue(R.id.student_form_website);
    }

    private String getEmail() {
        return getTextFieldValue(R.id.student_form_email);
    }


    public void fillInTheForm(Student student) {
       fill(R.id.student_form_name,student.getName());
        fill(R.id.student_form_phone_number,student.getPhoneNumber());
        fill(R.id.student_form_address,student.getAddress());
        fill(R.id.student_form_email,student.getEmail());
        fill(R.id.student_form_website,student.getWebsite());
        RatingBar rating = (RatingBar) activity.findViewById(R.id.student_form_grading);
        rating.setRating((float) student.getGrading());
    }

    private void fill(int id, String value) {
        EditText field = (EditText) activity.findViewById(id);
        field.setText(value);
    }
}
