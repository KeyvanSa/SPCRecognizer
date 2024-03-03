package ir.balutapp.spcrecognizer;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    SPCRecognizer spc;
    EditText et;
    TextView tv;
    Button buttonNext,buttonPrevious;

    int position=1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.mainEditText);
        tv=findViewById(R.id.mainTextView);
        buttonPrevious=findViewById(R.id.buttonPrevious);
        buttonNext=findViewById(R.id.buttonNext);

        spc =new SPCRecognizer(tv);

        spc.setFindHashtag(true);
        spc.setclickHashtag(true);
        spc.setColorTAG(Color.parseColor("#7cedd8"));

        spc.setFindMention(true);
        spc.setclickMention(true);
        spc.setColorMENTION(Color.parseColor("#000cea"));

        spc.setfindMail(true);
        spc.setclickMail(true);
        spc.setColorMAIL(Color.parseColor("#68cd5e"));

        spc.setFindUrl(true);
        spc.setclickUrl(true);
        spc.setColorURL(Color.parseColor("#14de08"));

        spc.setFindPhone(true);
        spc.setclickPhone(true);
        spc.setColorPHONE(Color.parseColor("#269497"));

        spc.setClickListener(model -> {
            Toast.makeText(MainActivity.this,model.getText()+ " is a "+
                    model.getSymbol() , Toast.LENGTH_SHORT).show();
        });

        spc.check(tv);

        et.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4){}
            @Override
            public void afterTextChanged(Editable p1){}
            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
            {
                buttonPrevious.setVisibility(View.GONE);
                buttonNext.setVisibility(View.GONE);

                if(p1.length()==0){
                    spc.check(tv);
                    position=1;
                } else {
                    int i=spc.findWord(p1.toString(),position);
                    if(i!=0){
                        buttonPrevious.setVisibility(View.VISIBLE);
                        buttonNext.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        buttonPrevious.setOnClickListener(view -> {
            if(position>1)
                position--;
            else position=spc.findWord(et.getText().toString(),position);
            spc.findWord(et.getText().toString(),position);
        });

        buttonNext.setOnClickListener(view -> {
            if(position<spc.findWord(et.getText().toString(),position))
                position++;
            else position=1;
            spc.findWord(et.getText().toString(),position);
        });

    }
}