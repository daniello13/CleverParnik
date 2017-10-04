package ua.student.program;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TableActivity extends AppCompatActivity {
    EditText login_a;
    EditText parol_a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        login_a =(EditText) findViewById(R.id.login);
        parol_a = (EditText) findViewById(R.id.parol);
    }
    /*
    Обработчик события нажатия на клавишу
     */
    public void log_in(View view){
        String login_text = login_a.getText().toString();
        String parol_text = parol_a.getText().toString();
        if(login_text.equals("1")&& parol_text.equals("1")) {
            Intent intent = new Intent(TableActivity.this, temperature_download.class);
            startActivity(intent);
        }
        if(login_text.equals("")&& parol_text.equals("")) {
            Intent intent2 = new Intent(TableActivity.this, temperature_download_user.class);
            startActivity(intent2);
        }
    }
    public void info(View view)
    {
        Intent intent1 = new Intent(TableActivity.this, Info.class);
        startActivity(intent1);
    }
}