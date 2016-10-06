package com.fuzzyapps.conferenciaepiscopalboliviana;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class evento_comentarios extends AppCompatActivity {
    private EditText messageInput;
    private TextView messageTextCount;
    private Button messageSendButton;
    private boolean send = false;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            int cantidad = 255 - Integer.parseInt(String.valueOf(s.length())+"");
            messageTextCount.setText(cantidad+"");
            if(cantidad >= 0){
                send = true;
            }else {
                send = false;
            }
        }
        public void afterTextChanged(Editable s) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evento_comentarios);
        messageTextCount = (TextView) findViewById(R.id.messageTextCount);
        messageInput = (EditText) findViewById(R.id.messageInput);
        messageSendButton = (Button) findViewById(R.id.messageSendButton);
        messageInput.addTextChangedListener(mTextEditorWatcher);
        messageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageInput.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"No hay texto.",Toast.LENGTH_SHORT).show();
                }else{
                    if(send){
                        Toast.makeText(getApplicationContext(),""+messageInput.getText().toString(),Toast.LENGTH_SHORT).show();
                        messageInput.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(),"Mensaje muy largo.",Toast.LENGTH_SHORT).show();
                    }
                }
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                messageInput.clearFocus();
            }
        });
    }
}
