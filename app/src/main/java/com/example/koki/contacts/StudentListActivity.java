package com.example.koki.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class StudentListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        getStudentsList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> studentsList, View view, int i, long l) {
                Student student = (Student) studentsList.getItemAtPosition(i);
                Intent intention = new Intent(StudentListActivity.this, StudentFormActivity.class);
                intention.putExtra("student", student);
                startActivity(intention);
            }
        });
        registerForContextMenu(getStudentsList());

        Button newStudent = (Button) findViewById(R.id.student_list_new_student);
        newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intention = new Intent(StudentListActivity.this, StudentFormActivity.class);
                startActivity(intention);
            }
        });
    }

    private ListView getStudentsList() {
        return (ListView) findViewById(R.id.student_list_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
    }

    private void loadStudents() {
        StudentDAO dao = new StudentDAO(this);
        List<Student> students = dao.listAll();
        dao.close();


        ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, students);
        getStudentsList().setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.equals(getStudentsList())) {

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Student student = (Student) getStudentsList().getItemAtPosition(info.position);
            showContextMenuForStudent(menu, student);
        }
    }

    private void showContextMenuForStudent(ContextMenu menu, final Student student) {
        MenuItem remove = menu.add("Remove");
        remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                StudentDAO dao = new StudentDAO(StudentListActivity.this);
                dao.remove(student);
                dao.close();
                Toast.makeText(StudentListActivity.this, "Removing " + student.getName(), Toast.LENGTH_SHORT).show();
                loadStudents();
                return true;
            }
        });
    }
}
