package computomovil.fmat.lalo.databasestudents;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;

import computomovil.fmat.lalo.databasestudents.Services.StudentService;
import computomovil.fmat.lalo.databasestudents.database.StudentDataSource;
import computomovil.fmat.lalo.databasestudents.model.Student;

public class MainActivity extends ListActivity {

    private StudentService stdService;
    StudentDataSource alumnoDS;

    private void setComponentsForWorking() {
        try {
            alumnoDS.open();
            stdService.setStudents(alumnoDS.getAllAlumnos());
            alumnoDS.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stdService.getAllMatricesRegistered()
        );
        this.setListAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stdService = new StudentService();
        alumnoDS = new StudentDataSource(getApplicationContext());
        setComponentsForWorking();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setComponentsForWorking();
    }

    public void addStudent(View v) {
        Student std = new Student();
        Intent intent = new Intent(getApplicationContext(), FormStudent.class);
        intent.putExtra("student", std);
        intent.putExtra("adding", 1);
        startActivity(intent);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Student studentSelected = stdService.getByMatrix(item);

        Intent intent = new Intent(getApplicationContext(), FormStudent.class);
        intent.putExtra("student", studentSelected);
        intent.putExtra("adding", 0);

        startActivity(intent);

    }
}
