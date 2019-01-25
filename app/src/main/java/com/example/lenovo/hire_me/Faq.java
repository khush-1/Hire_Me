package com.example.lenovo.hire_me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Faq extends AppCompatActivity {

    ImageButton btn1,btn2,btn3,btn4;
    TextView Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        btn1=(ImageButton)findViewById(R.id.btn1);
        btn2=(ImageButton)findViewById(R.id.btn2);
        btn3=(ImageButton)findViewById(R.id.btn3);
        btn4=(ImageButton)findViewById(R.id.btn4);
        Answer=(TextView)findViewById(R.id.Answer);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer.setText("Ans:Yes, core engineering streams can apply for software jobs.\n" +
                        "Most companies skill and train their freshers in order to prepare\n" +
                        "them for their jobs.\n" +
                        "However, if you are keen on a software job as a backup career\n" +
                        "then ensure that you have basic technical skills and knowledge\n" +
                        "about the latest software and technologies. Remember, you will\n" +
                        "be competing against students who are specialising in these\n" +
                        "subjects, so you need to be as accomplished as them.");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer.setText("Ans:Yes, if students have internship experience on their\n" +
                        "resume it speaks volumes about their initiative and internal\n" +
                        "drive. If you have done an internship it means that you have put\n" +
                        "in those hours, and if you have done an internship alongside\n" +
                        "your college it has more impact.\n" +
                        "This is because it means that you have been able to balance\n" +
                        "college and work. It is the perfect opportunity to showcase your\n" +
                        "skills and talent.");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer.setText("Ans:No, that’s not the case. Recruiters look at internship only\n" +
                        "through as a point of reference for work experience. They want\n" +
                        "to know how much a student has learned during an internship.\n" +
                        "What was their work profile and how much did they accomplish.\n" +
                        "They understand that most internships in India are unpaid and it\n" +
                        "is not a parameter for hiring.");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer.setText("Ans:The third year of engineering or the year prior to\n" +
                        "graduation is perfect for starting preparation for your campus\n" +
                        "\n" +
                        "placements. It’s the best time to understand what is required for\n" +
                        "placements and you can even take your seniors’ help since they\n" +
                        "will be on campus and going through placements themselves.");
            }
        });
    }
}
