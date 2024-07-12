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
import fpoly.hunghvph46928.duanmau.adapters.AdapterLoaiSach;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;


public class Fragment_QLLoaiSach extends Fragment {
    List<LoaiSach> list;
    AdapterLoaiSach adtLoaiSach;
    RecyclerView recyclerViewLoaiSach;
    FloatingActionButton btnAdd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__q_l_loai_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewLoaiSach = view.findViewById(R.id.recylerViewLoaiSach);
        btnAdd = view.findViewById(R.id.btnAdd);
        list = MainActivity.daoLoaiSach.getAll();
        adtLoaiSach = new AdapterLoaiSach(list,requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewLoaiSach.setLayoutManager(linearLayoutManager);
        recyclerViewLoaiSach.setAdapter(adtLoaiSach);
        recyclerViewLoaiSach.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        btnAdd.setOnClickListener(v-> themLoaiSach());
    }
    public void themLoaiSach() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_loaisach);
        EditText edtTenLoai = dialog.findViewById(R.id.edtTenLoai);
        Button btnThem = dialog.findViewById(R.id.btnThem);

        btnThem.setOnClickListener(v-> {
            String tenLoai = edtTenLoai.getText().toString();
            if (!tenLoai.isEmpty()) {
                if (checkTenLoai(tenLoai)) {
                    LoaiSach loaiSach = new LoaiSach();
                    loaiSach.setTenLoai(tenLoai);
                    long check = MainActivity.daoLoaiSach.insert(loaiSach);
                    if (check != -1) {
                        loaiSach.setMaLoai((int) check);
                        list.add(loaiSach);
                        adtLoaiSach.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(requireContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            } else {
                Toast.makeText(requireContext(), "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
            }

        });
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }
        public boolean checkTenLoai(String tenLoai) {
            for (LoaiSach loaiSach : list) {
                if (loaiSach.getTenLoai().equalsIgnoreCase(tenLoai)) {
                    Toast.makeText(requireContext(), "Tên loại sách đã tồn tại", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;
        }
}