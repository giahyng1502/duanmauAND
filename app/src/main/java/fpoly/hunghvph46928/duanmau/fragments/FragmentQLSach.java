package fpoly.hunghvph46928.duanmau.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSpinerLoaiSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterThanhVien;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;


public class FragmentQLSach extends Fragment {
    RecyclerView recyclerViewSach;
    List<Sach> list;
    AdapterSach adapterSach;
    FloatingActionButton btnAdd;


    Sach itemSach;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd = view.findViewById(R.id.btnAdd_Sach);
        recyclerViewSach = view.findViewById(R.id.recylerViewSach);
        list = MainActivity.daoSach.getAll();
        adapterSach = new AdapterSach(list,requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewSach.setLayoutManager(linearLayoutManager);
        recyclerViewSach.setAdapter(adapterSach);
        recyclerViewSach.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        itemSach = new Sach();
        btnAdd.setOnClickListener(v->themSach());
    }
    public void themSach() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_sach);
        EditText edtTensach,edtGiaSach;
        TextView tvMasach;
        Spinner spnLoaisach = dialog.findViewById(R.id.spLoaisach);
        AdapterSpinerLoaiSach adapterSpinerLoaiSach;
        List<LoaiSach> items;
        items = MainActivity.daoLoaiSach.getAll();
        adapterSpinerLoaiSach = new AdapterSpinerLoaiSach(requireContext(),items);

        Button btnLuu,btnHuy;
        tvMasach = dialog.findViewById(R.id.tvMasach_Sach);
        edtTensach = dialog.findViewById(R.id.edtTensach);
        edtGiaSach = dialog.findViewById(R.id.edtGiaThue);
        btnLuu = dialog.findViewById(R.id.btnLuu_sach);
        btnHuy = dialog.findViewById(R.id.btnHuy_Sach);

        spnLoaisach.setAdapter(adapterSpinerLoaiSach);

        btnLuu.setOnClickListener(v->{
            String tenSach = edtTensach.getText().toString();
            String gia = edtGiaSach.getText().toString();
            int giaSach = 0;
            if (!gia.isEmpty()) {
                giaSach = Integer.parseInt(gia);
            }
            int selectedItemPosition = spnLoaisach.getSelectedItemPosition();
            LoaiSach loaiSach = items.get(selectedItemPosition);
            int maLoai = loaiSach.getMaLoai();
            Sach sach = new Sach();
            sach.setTenSach(tenSach);
            sach.setGiaThue(giaSach);
            sach.setMaLoai(maLoai);
            if (tenSach.isEmpty()||giaSach < 0|| selectedItemPosition <0) {
                Toast.makeText(requireContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                long check = MainActivity.daoSach.insert(sach);
                if (check != -1) {
                    sach.setMaSach((int) check);
                    list.add(sach);
                    adapterSach.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }
}