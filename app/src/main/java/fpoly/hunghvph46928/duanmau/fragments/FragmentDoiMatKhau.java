package fpoly.hunghvph46928.duanmau.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.hunghvph46928.duanmau.LoginActivity;
import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.models.ThuThu;


public class FragmentDoiMatKhau extends Fragment {
    EditText edtNewpass,edtOldPass,edtNhapLai;
    Button btnThayDoi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi__mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ánh xạ
        edtNewpass = view.findViewById(R.id.edtNewPass);
        edtOldPass = view.findViewById(R.id.edtOldPass);
        edtNhapLai = view.findViewById(R.id.edtNhapLai);
        btnThayDoi = view.findViewById(R.id.btnThaydoi);

        btnThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePass();
            }
        });
    }
    private void updatePass() {
        ThuThu thuThu = MainActivity.thuThu;
        String oldPass = edtOldPass.getText().toString();
        String passNew = edtNewpass.getText().toString();
        String nhapLai = edtNhapLai.getText().toString();
        if (oldPass.isEmpty() || passNew.isEmpty() || nhapLai.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {

            if (oldPass.equals(thuThu.getMatKhau())) {
                if (passNew.equals(nhapLai)) {

                    long check = MainActivity.daoThuthu.updatePass(thuThu.getMaTT(), passNew);
                    if (check > 0) {
                        startActivity(new Intent(requireContext(), LoginActivity.class));
                        Toast.makeText(requireContext(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}