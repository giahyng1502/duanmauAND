package fpoly.hunghvph46928.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fpoly.hunghvph46928.duanmau.dao.DAOThuThu;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername,edtPassword;
    CheckBox chkRemember;
    Button btnLogin,btnCencel;
    DAOThuThu thuthuDao;
    List<ThuThu> listAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        thuthuDao = new DAOThuThu(this);
        listAccount = thuthuDao.getAll();
        btnLogin.setOnClickListener(v-> login());
        setChkRemember();
        btnCencel.setOnClickListener(v->{
            edtUsername.setText("");
            edtPassword.setText("");
        });
    }

    private void anhXa() {
        edtUsername = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnCencel = findViewById(R.id.btnCencel);
    }
    private void login() {
        String user = edtUsername.getText().toString().trim();
        String pass =  edtPassword.getText().toString().trim();
        boolean check = chkRemember.isChecked();
        if (thuthuDao.checkLogin(user,pass)) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("user",user);
            rememberUser(user,pass,check);
            Toast.makeText(this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else if (user.isEmpty()|| pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }
    private void setChkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        //Tạo một đối tượng SharedPreferences có tên là "USER_FILE".
        // Đối tượng này sẽ được sử dụng để lưu trữ các thông tin người dùng.
        edtUsername.setText(sharedPreferences.getString("USER",""));
        //Lấy giá trị của chuỗi có key là "USER" từ SharedPreferences và đặt nó vào EditText edtUsername.
        // Nếu không tìm thấy giá trị, sử dụng chuỗi rỗng mặc định.
        edtPassword.setText(sharedPreferences.getString("PASS",""));
        chkRemember.setChecked(sharedPreferences.getBoolean("REMEMBER",false));
    }
    private void rememberUser(String user,String pass,boolean status) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // Tạo một đối tượng Editor để thực hiện chỉnh sửa SharedPreferences.
        if (status == false) {
            editor.clear();
            // nếu người dùng không nhấn chkremember thì clear toàn bộ dự liệu
        } else {
            editor.putString("USER",user);
            editor.putString("PASS",pass);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
        // lưu lại dữ liệu
    }
}