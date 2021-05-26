package com.example.evsherpa.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.MainActivity;
import com.example.evsherpa.PreferenceRegistrationActivity;
import com.example.evsherpa.R;
import com.example.evsherpa.ui.signUp.SignUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

    private Button btn_pass;

    private EditText usernameEditText;
    private EditText passwordEditText;

    private TextView usernameCheckText;
    private TextView passwordCheckText;

    private Button loginButton;

    private Button btnSignUpNormal;
    private Button btnSignUpGoogle;
    private Button btnSignUpKakao;

    private boolean is_username_input;
    private boolean is_password_input;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private static final int REQ_SIGN_GOOGLE=100;

    private ISessionCallback mSessionCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getAppKeyHash();

        mSessionCallback=new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                       //로그인 실패
                        Toast.makeText(LoginActivity.this, "로그인 도중 오류 발생", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //session 단힘
                        Toast.makeText(LoginActivity.this, "세션이 닫힘..다시 시도", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        //로그인 성공.
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("nickname",result.getKakaoAccount().getProfile().getNickname());
                        intent.putExtra("email",result.getKakaoAccount().getEmail());
                        //나이,성별 다 받을수 있게 설정은 되어 있으나 구글 로그인과의 형평성 고려하여 2개만 받을 예정..
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "kakao login", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }

        };
        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();


        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        auth=FirebaseAuth.getInstance();
        initElements();

    }

    private void initElements() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        usernameCheckText = findViewById(R.id.txt_email_check);
        passwordCheckText = findViewById(R.id.txt_password_check);

        loginButton = findViewById(R.id.login);
        btnSignUpNormal = findViewById(R.id.btn_sign_up_normal);
        btnSignUpGoogle = findViewById(R.id.btn_sign_up_google);

        is_username_input = false;
        is_password_input = false;

        btn_pass = findViewById(R.id.btn_pass);
        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!passwordEditText.getText().toString().isEmpty()) {
                    passwordCheckText.setVisibility(View.INVISIBLE);
                    is_password_input = true;
                } else {
                    passwordCheckText.setVisibility(View.VISIBLE);
                    is_password_input = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(is_password_input && is_username_input);
//                Log.d("test","password ");
            }
        });
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!usernameEditText.getText().toString().contains("@")) {
                    usernameCheckText.setVisibility(View.VISIBLE);
                    is_username_input = true;
                } else {
                    usernameCheckText.setVisibility(View.INVISIBLE);
                    is_username_input = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButton.setEnabled(is_password_input && is_username_input);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            //boolean success=jsonObject.getBoolean("success");
                            // 원래는 위의 방식으로 진행하는게 맞으나 서버와 연동이 안되서 자체적으로 success처리!
//                            JSONObject jsonObject=new JSONObject("{\"success\":true}"); // 추후제거
                            boolean success = true; //TODO: 서버와 연결시 해당 위치 주석처리

                            if (success) {


                                Toast.makeText(getApplicationContext(), "로그인 성공.." + email + " and " + password, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                //TODO: 서버와 연결시 되는지 프로필 내용 전체 받아오는지 확인하기.(실제 실행시..밑부분 예시는 주석처리하기)
                                /*
                                String email=jsonObject.getString("email");
                                String nickName=jsonObject.getString("nickname");
                                String homeAddr=jsonObject.getString("homeAddr");
                                String workplaceAddr=jsonObject.getString("workplaceAddr");
                                String carName=jsonObject.getString("carName");
                                String age=jsonObject.getString("age");
                                */

                                String email = "rlacksals96@gmail.com";
                                String nickName = "dogy master";
                                String homeAddr = "서울시 강남구 대치동";
                                String workplaceAddr = "경기도 성남시 분당구 판교역로";
                                String carName = "tesla model 3";
                                String age = "25";


                                String profileStr = loadJSON();
                                JSONObject profile = new JSONObject(profileStr);
                                profile.put("email", email);
                                profile.put("nickname", nickName);
                                profile.put("homeAddr", homeAddr);
                                profile.put("workplaceAddr", workplaceAddr);
                                profile.put("carName", carName);
                                profile.put("age", age);
                                try {
                                    FileOutputStream fos = openFileOutput("profile.json", Context.MODE_PRIVATE);
                                    String tmp = profile.toString();
                                    byte[] result = tmp.getBytes();
                                    fos.write(result);
                                } catch (IOException fe) {
                                    fe.printStackTrace();
                                }


                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 volley를 이용해서 요청을 함.
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                //temporary... 구현후 삭제..
//                Toast.makeText(LoginActivity.this,"login clicked",Toast.LENGTH_SHORT).show();

            }
        });

        btnSignUpNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignUpGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=googleSignInClient.getSignInIntent();
                startActivityForResult(intent,REQ_SIGN_GOOGLE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_SIGN_GOOGLE){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                GoogleSignInAccount account=task.getResult();
                resultGoogleLogin(account);
            }
            else{
                Toast.makeText(this, "구글 서버의 문제로 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //firebase에 credential 저장(related to google login)
    private void resultGoogleLogin(GoogleSignInAccount account) {
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "google login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("email",account.getEmail());
                            intent.putExtra("nickname",account.getDisplayName());
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /*
        public void getProfileFromServer() {

            String url="/account/update";
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        success=response.getBoolean("success");
                        if(success){
                            email=response.getString("email");
                            nickname=response.getString("nickname");
                            homeAddr=response.getString("homeAddr");
                            workplaceAddr=response.getString("workplaceAddr");
                            carName=response.getString("carName");
                            age=response.getString("age");
                            String profileStr=loadJSON();
                            JSONObject localProfile=new JSONObject(profileStr);
                            localProfile.put("email",email);
                            localProfile.put("nickname",nickname);
                            localProfile.put("homeAddr",homeAddr);
                            localProfile.put("workplaceAddr",workplaceAddr);
                            localProfile.put("carName",carName);
                            localProfile.put("age",age);


                            try{
                                FileOutputStream fos=getActivity().openFileOutput("profile.json", Context.MODE_PRIVATE);
                                String tmp=localProfile.toString();
                                byte[] result=tmp.getBytes();
                                fos.write(result);
                            } catch(IOException fe){
                                fe.printStackTrace();
                            }
                        }
                        else{
                            //호출실패
                            Toast.makeText(getContext(),"서버로부터 프로필을 받아오지 못했습니다",Toast.LENGTH_SHORT).show();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            jsonObjectRequest.setTag("HOME FRAGMENT");
            queue.add(jsonObjectRequest);

        }
    */
    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public String loadJSON() {
        String json = null;
        FileInputStream fis;
        StringBuilder sb;
        try {
            fis = openFileInput("profile.json");
            InputStreamReader isr = new InputStreamReader(fis);

            BufferedReader br = new BufferedReader(isr);
            sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
    //kakao login시 필요한 해시키를 얻는 메소드이다.
    private void getAppKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
    }
}