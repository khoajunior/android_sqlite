package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.StudentAdapter;
import com.example.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button button;
    private Button button2;
    private Button button3;
    private List<Student> studentList;
    private Student pstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       DBManager dbManager = new DBManager(this);
//        Student student = new Student(1,"khoa");
//        Student student2 = new Student(2,"khoa2");
//        Student student3 = new Student(3,"khoa3");
//        Student student4 = new Student(4,"khoa4");
//        Student student5 = new Student(5,"khoa5");
//        dbManager.addStudent(student);
//        dbManager.addStudent(student2);
//        dbManager.addStudent(student3);
//        dbManager.addStudent(student4);
//        dbManager.addStudent(student5);
//       dbManager.getAllStudent();

//       textView = findViewById(R.id.txtiew);
//       textView.setText(dbManager.getAllStudent().toString());

        studentList = new ArrayList<>();
        studentList = dbManager.getAllStudent();
        recyclerView = findViewById(R.id.reclyeview);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        editText = findViewById(R.id.editTextTextPersonName);


        button2.setEnabled(false);

        StudentAdapter studentAdapter = new StudentAdapter(studentList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(studentAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Student student5 = new Student(random.nextInt(),editText.getText().toString());
                studentList.add(student5);
                dbManager.addStudent(student5);
                studentAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Student student = studentList.get(position);
                        editText.setText(student.getName());
                        pstudent = student;
                        button2.setEnabled(true);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentList.remove(pstudent);
                dbManager.deleteStudent(pstudent.getId());
                studentAdapter.notifyDataSetChanged();
                button2.setEnabled(false);
                editText.setText("");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setEnabled(false);
                editText.setText("");
                pstudent = new Student();
            }
        });
    }
}