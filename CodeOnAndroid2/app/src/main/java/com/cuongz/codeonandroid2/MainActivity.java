package com.cuongz.codeonandroid2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    HashMap<String, Integer> map = new HashMap<>();
    EditText textCode;
    private static final int PERMISSION_REQUEST_CODE = 100;

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

                int i = 0;
                int startIndex = 0;
//                for(int i = 0 ; i < split.length ; i++){
                while(i < split.length){
                    String s = split[i];

//                    if(s.equals("{")){
//                        editable.append("}");
////                        Log.w("before", "sdfsd");
////                        s = "{}";
//////                        editable.replace(0, editable.length(), string);
////                        Log.w("after", "fgsg");
//////                        Log.w("string", string);
//////                        string = editable.toString();
//
//                    }
                    if(map.containsKey(s)){

                        int index = string.indexOf(s, startIndex);
                        int color = map.get(s);
                        editable.setSpan(new ForegroundColorSpan(color),
                                index,
                                index + s.length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        startIndex = index + s.length();
                    }
                    else if(!s.isEmpty()){
                        if(s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"'){
                            int index = string.indexOf(s, startIndex);
                            editable.setSpan(new ForegroundColorSpan(Color.GREEN),
                                    index,
                                    index + s.length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            startIndex = index + s.length();
                        }else{
                            int index = string.indexOf(s, startIndex);
                            editable.setSpan(new ForegroundColorSpan(Color.WHITE),
                                    index,
                                    index + s.length(),
                                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            startIndex = index + s.length();
                        }

                    }
                    else if(!map.containsKey(s)){
                        int index = string.indexOf(s, startIndex);
                        editable.setSpan(new ForegroundColorSpan(Color.WHITE),
                                index,
                                index + s.length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        startIndex = index + s.length();
                    }

                    Log.w("number", string);
                    i++;
                }
                //
            }
        });
    }

    public void inputColor(){
        List<String> KEYWORD = new ArrayList<>(Arrays.asList("abstract", "and",
                "arguments", "assert", "break", "case",
                "catch", "char", "class", "const",
                "continue", "default", "def", "in",
                "init", "delete", "do", "dynamic",
                "type", "if", "else", "elif",
                "enum", "extend", "false", "final",
                "for", "from", "function", "get",
                "go", "goto", "interface", "local",
                "map", "namespace", "new", "null",
                "or", "override", "package", "prefix",
                "print", "private", "protected", "public",
                "return", "sizeof", "static", "struct",
                "switch", "this", "true", "try",
                "void"));

        for(int i = 0; i < KEYWORD.size(); i++){
            map.put(KEYWORD.get(i), Color.CYAN);
        }

        List<String> TYPE = new ArrayList<>(Arrays.asList("int", "long",
                "float", "String", "char", "double"));

        for(int i = 0; i < TYPE.size(); i++){
            map.put(TYPE.get(i), Color.RED);
        }


    }
    public void saveFile(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.save_file, null);
        final EditText fileName = v.findViewById(R.id.fileName);

        builder.setView(inflater.inflate(R.layout.save_file, null))
                .setTitle("Save file")
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isExternalStorageWritable()){
                            if (checkPermission()){
                                File sdcard = Environment.getExternalStorageDirectory();
                                File dir = new File(sdcard.getAbsolutePath() + "/src/");
                                Log.w("A", "correct here");
                                dir.mkdirs();
                                File file = new File(dir, fileName.getText().toString());
                                Log.w("FN",fileName.getText().toString());
                                Log.w("B", "correct here");
                                FileOutputStream os;
                                try{
                                    os = new FileOutputStream(file, true);
                                    os.write(textCode.getText().toString().getBytes());
                                    os.flush();
                                    os.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }else{
                                requestPermission();
                            }
                        }else{
                            Log.w("status", "can't write on storage");
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }

    public boolean isExternalStorageWritable(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            Log.i("State", "Yes, it is writable!");
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPermission(){
        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to create files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

//    public File writeFileExternalStorage(String fileName, String content) {
//
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
//        if (!file.mkdirs()) {
//            file.mkdirs();
//        }
//
////        //Text of the Document
////
////        //Checking the availability state of the External Storage.
////        String state = Environment.getExternalStorageState();
////        if (!Environment.MEDIA_MOUNTED.equals(state)) {
////
////            //If it isn't mounted - we can't write into it.
////            return;
////        }
//
//
//        //This point and below is responsible for the write operation
//        FileOutputStream outputStream;
//        try {
//            file.createNewFile();
//            //second argument of FileOutputStream constructor indicates whether
//            //to append or create new file if one exists
//            outputStream = new FileOutputStream(file, true);
//
//            outputStream.write(content.getBytes());
//            outputStream.flush();
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return file;
//    }

}
