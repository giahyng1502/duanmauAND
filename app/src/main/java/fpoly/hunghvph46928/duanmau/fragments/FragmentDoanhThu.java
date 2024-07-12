package fpoly.hunghvph46928.duanmau.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
public class FragmentDoanhThu extends Fragment {
    Button btnCheck;
    EditText edtTuNgay,edtDenNgay,edtDoanhThu;
    SimpleDateFormat sdf;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCheck = view.findViewById(R.id.btnCheckDoanhThu);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        edtDoanhThu = view.findViewById(R.id.edtDoanhThu);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //format ngày tháng năm
        edtDenNgay.setOnClickListener(v-> {
            // Tạo đối tượng Calendar và lấy ngày, tháng, năm hiện tại
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            // Tạo và hiển thị DatePickerDialog
            DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Tạo đối tượng GregorianCalendar với ngày, tháng, năm đã chọn
                    GregorianCalendar c = new GregorianCalendar(year,month,dayOfMonth);
                    // Định dạng ngày và cập nhật vào EditText
                    edtDenNgay.setText(sdf.format(c.getTime()));
                }
            },year,month,day);
            // Hiển thị DatePickerDialog
            dialog.show();
        });
        edtTuNgay.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog dialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    GregorianCalendar c = new GregorianCalendar(year,month,dayOfMonth);
                    edtTuNgay.setText(sdf.format(c.getTime()));
                }
            },year,month,day);
            dialog.show();
        });
        btnCheck.setOnClickListener(v->{
            String tuNgay = edtTuNgay.getText().toString();
            String denNgay = edtDenNgay.getText().toString();
            int doanhthu = MainActivity.daoPhieuMuon.getDoanhThu(tuNgay,denNgay);
            edtDoanhThu.setText(doanhthu+" VND");
        });
    }
}