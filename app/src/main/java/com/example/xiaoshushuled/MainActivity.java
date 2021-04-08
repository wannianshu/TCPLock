package com.example.xiaoshushuled;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ApplicationUtil applicationUtil;
    EditText editIp;
    EditText editPort;
    Button btnSubmitTcp;
    PwdEditText pwdInput;
    Button btnClearInput;
    Button btnInput;
    PwdEditText pwdOne;
    PwdEditText pwdTow;
    Button btnUpdatePwd;
    String IP = "";
    String Port = "";
    String inputPwd = "";
    String updateOne = "";
    String updateTow = "";
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_main);
        applicationUtil = (ApplicationUtil)this.getApplication();
        bindingView();//绑定控件
        bindingListener();//绑定事件
    }
    //绑定控件
    public void bindingView(){
        editIp = findViewById(R.id.edit_Ip);
        editPort = findViewById(R.id.edit_Port);
        btnSubmitTcp = findViewById(R.id.btn_SubmitTcp);
        pwdInput = findViewById(R.id.pwd_Input);
        btnClearInput = findViewById(R.id.btn_ClearPwd);
        btnInput = findViewById(R.id.btn_Input);
        pwdOne = findViewById(R.id.pwd_one);
        pwdTow = findViewById(R.id.pwd_two);
        btnUpdatePwd = findViewById(R.id.btn_updatePwd);
    }
    //绑定监听控件
    public void bindingListener(){
        btnSubmitTcp.setOnClickListener(this);
        btnClearInput.setOnClickListener(this);
        btnInput.setOnClickListener(this);
        btnUpdatePwd.setOnClickListener(this);
        pwdInput.setOnTextChangeListener(new PwdEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                inputPwd = pwd;
            }
        });
        pwdOne.setOnTextChangeListener(new PwdEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                updateOne = pwd;
            }
        });
        pwdTow.setOnTextChangeListener(new PwdEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                updateTow = pwd;
            }
        });
    }
    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_SubmitTcp://建立连接
                if(!editIp.getText().toString().trim().equals("")){
                    IP = editIp.getText().toString().trim();
                }else {
                    Toast.makeText(this,"请输入IP",Toast.LENGTH_SHORT).show();
                }
                if(!editPort.getText().toString().trim().equals("")){
                    Port = editPort.getText().toString().trim();
                }else {
                    Toast.makeText(this,"请输入端口号",Toast.LENGTH_SHORT).show();
                }
                applicationUtil.init(IP,Port,this);
                hideSoftKeyboard(this);
                break;
            case R.id.btn_ClearPwd:
                pwdInput.clearText();
                break;
            case R.id.btn_Input://进行开锁
                if(!applicationUtil.isTcpIs()){
                    Toast.makeText(this,"服务器未连接",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPwd.equals("")){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                String s = "Key" + inputPwd;
                applicationUtil.SendM(s);
                Toast.makeText(this,"发送成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_updatePwd://修改密码
                if(!applicationUtil.isTcpIs()){
                    Toast.makeText(this,"服务器未连接",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!updateOne.equals(updateTow)){
                    Toast.makeText(this,"新密码输入不一致",Toast.LENGTH_SHORT).show();
                }else {
                    if(updateOne.equals("")){
                        Toast.makeText(this,"新密码为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String me = "update" + updateOne;
                    applicationUtil.SendM(me);
                    Toast.makeText(this,"修改信息已发送",Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }
}