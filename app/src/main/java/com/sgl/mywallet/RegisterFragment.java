package com.sgl.mywallet;


import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private Context context;
    private EditText fullName;
    private EditText password;
    private EditText email;
    private EditText rePassword;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        password = v.findViewById(R.id.et_password);
        email = v.findViewById(R.id.et_email);
        fullName = v.findViewById(R.id.et_name);
        rePassword = v.findViewById(R.id.et_repassword);

        v.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(rePassword.getText().toString())) {
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setProperty("name", fullName.getText().toString());
                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("str", "Hi, " + response.getProperty("name"));
                            startActivity(intent);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            resetInputs();
                            Log.e("ERROR", "RegisterFault: " + fault.getMessage());
                        }
                    });
                } else {
                    resetInputs();
                }
            }
        });
        return v;
    }

    private void resetInputs() {
        fullName.setText("");
        password.setText("");
        rePassword.setText("");
        email.setText("");
    }

}
