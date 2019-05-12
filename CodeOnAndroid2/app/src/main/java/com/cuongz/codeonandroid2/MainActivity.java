package com.cuongz.codeonandroid2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    HashMap<String, Integer> map = new HashMap<>();
    EditText textCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCode = findViewById(R.id.textCode);

        inputColor();

        textCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String string = editable.toString();
                String[] split = string.split("\\s");

                int startIndex = 0;
                for(int i = 0 ; i < split.length ; i++){
                    String s = split[i];
                    if(map.containsKey(s)){

                        int index = string.indexOf(s, startIndex);
                        int color = map.get(s);
                        editable.setSpan(new ForegroundColorSpan(color),
                                index,
                                index + s.length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        startIndex = index + s.length();
                    }

                }
            }
        });
    }

    public void inputColor(){
        map.put("abstract", Color.CYAN); map.put("and", Color.CYAN);
        map.put("arguments", Color.CYAN); map.put("assert", Color.CYAN);
//        map.put("associativity", Color.CYAN); map.put("auto", Color.CYAN);
        map.put("break", Color.CYAN); map.put("case", Color.CYAN);
        map.put("catch", Color.CYAN); map.put("char", Color.CYAN);
        map.put("class", Color.CYAN); map.put("const", Color.CYAN);
        map.put("continue", Color.CYAN); map.put("default", Color.CYAN);
        map.put("def", Color.CYAN); map.put("in", Color.CYAN);
        map.put("init", Color.CYAN); map.put("delete", Color.CYAN);
        map.put("do", Color.CYAN); map.put("dynamic", Color.CYAN);
        map.put("type", Color.CYAN); map.put("if", Color.CYAN);
        map.put("else", Color.CYAN); map.put("elif", Color.CYAN);
        map.put("enum", Color.CYAN); map.put("extend", Color.CYAN);
        map.put("false", Color.CYAN); map.put("final", Color.CYAN);
        map.put("for", Color.CYAN); map.put("from", Color.CYAN);
        map.put("function", Color.CYAN); map.put("get", Color.CYAN);
        map.put("go", Color.CYAN); map.put("goto", Color.CYAN);
        map.put("interface", Color.CYAN); map.put("lazy", Color.CYAN);
        map.put("local", Color.CYAN); map.put("map", Color.CYAN);
        map.put("namespace", Color.CYAN); map.put("new", Color.CYAN);
        map.put("null", Color.CYAN); map.put("NULL", Color.CYAN);
        map.put("or", Color.CYAN); map.put("override", Color.CYAN);
        map.put("package", Color.CYAN); map.put("prefix", Color.CYAN);
        map.put("print", Color.CYAN); map.put("private", Color.CYAN);
        map.put("protected", Color.CYAN); map.put("public", Color.CYAN);
        map.put("return", Color.CYAN); map.put("sizeof", Color.CYAN);
        map.put("static", Color.CYAN); map.put("struct", Color.CYAN);
        map.put("switch", Color.CYAN); map.put("this", Color.CYAN);
        map.put("true", Color.CYAN); map.put("try", Color.CYAN);
        map.put("void", Color.CYAN);
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put(); map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put(); map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
//        map.put();
        map.put("void", Color.BLUE); map.put("String", Color.RED);
    }


}
