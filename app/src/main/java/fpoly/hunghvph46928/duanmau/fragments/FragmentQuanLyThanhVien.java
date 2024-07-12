package fpoly.hunghvph46928.duanmau.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.adapters.AdapterThanhVien;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;


public class FragmentQuanLyThanhVien extends Fragment {
    RecyclerView recyclerViewThanhVien;
    List<ThanhVien> list;
    AdapterThanhVien adapterThanhVien;
    FloatingActionButton btnAdd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_thanh_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ánh xạ
        btnAdd = view.findViewById(R.id.btnAdd_TV);
        recyclerViewThanhVien = view.findViewById(R.id.recylerViewThanhVien);
        //lấy data từ sqlive
        list = MainActivity.daoThanhVien.getAll();
        adapterThanhVien = new AdapterThanhVien(list,requireContext());
        //xét chiều rycelerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewThanhVien.setLayoutManager(linearLayoutManager);
        recyclerViewThanhVien.setAdapter(adapterThanhVien);
        recyclerViewThanhVien.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    btnAdd.hide();
                } else if (dy < 0) {
                    btnAdd.show();
                }
            }
        });
        btnAdd.setOnClickListener(v-> themThanhVien());

    }
    public void themThanhVien() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_thanhvien);
        // ánh xạ
        EditText edtTenThanhVien = dialog.findViewById(R.id.edtTenThanhvien);
        EditText edtNamsinh = dialog.findViewById(R.id.edtNamsinh);
        Button btnThem = dialog.findViewById(R.id.btnThem_TV);
        Button btnHuy = dialog.findViewById(R.id.btnHuy_TV);

        btnThem.setOnClickListener(v-> {
            String tenThanhVien = edtTenThanhVien.getText().toString();
            String namsinh = edtNamsinh.getText().toString();
            if (!tenThanhVien.isEmpty()&& !namsinh.isEmpty()) {
                if (Integer.parseInt(namsinh) > 1900 && Integer.parseInt(namsinh) < 2024){
                    ThanhVien thanhVien = new ThanhVien();
                    thanhVien.setHoTen(tenThanhVien);
                    thanhVien.setNamSinh(namsinh);
                    long check = MainActivity.daoThanhVien.insert(thanhVien);
                    //thêm thành viên vào database
                    if (check != -1) {
                        thanhVien.setID((int) check);
                        list.add(thanhVien);
                        adapterThanhVien.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(requireContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireContext(), "Năm Sinh Phải Lớn 1900 Và Nhỏ Hơn 2024", Toast.LENGTH_SHORT).show();
                }
                     
            } else {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

}