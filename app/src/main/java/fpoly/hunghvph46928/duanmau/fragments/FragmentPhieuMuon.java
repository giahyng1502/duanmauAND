package fpoly.hunghvph46928.duanmau.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.adapters.AdapterLoaiSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterPhieuMuon;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSpinerLoaiSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSpinerSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSpinerThanhVien;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.PhieuMuon;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;

public class FragmentPhieuMuon extends Fragment {
    List<PhieuMuon> list;
    AdapterPhieuMuon adtPhieuMuon;
    RecyclerView recyclerViewPhieuMuon;
    FloatingActionButton btnAdd;
    List<ThanhVien> itemThanhVien;
    List<Sach> itemSach;
    SearchView searchView;
    int giaThue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.search_PM);
        recyclerViewPhieuMuon = view.findViewById(R.id.recylerViewPhieuMuon);
        btnAdd = view.findViewById(R.id.btnAdd_PM);
        list = MainActivity.daoPhieuMuon.getAll();
        adtPhieuMuon = new AdapterPhieuMuon(list,requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewPhieuMuon.setLayoutManager(linearLayoutManager);

        recyclerViewPhieuMuon.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));

        recyclerViewPhieuMuon.setAdapter(adtPhieuMuon);
        btnAdd.setOnClickListener(v->themPM());
        recyclerViewPhieuMuon.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillterList(newText);
                return true;
            }
        });
    }

    private void fillterList(String newText) {
        List<PhieuMuon> fillterList = new ArrayList<>();
        for (PhieuMuon pm : list) {
            if (String.valueOf(pm.getMaPM()).contains(newText.toLowerCase())) {
                fillterList.add(pm);
            }
        }
        if (fillterList.isEmpty()) {
            Toast.makeText(requireContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {
            adtPhieuMuon.setFillterList(fillterList);
        }
    }

    public void themPM() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_phieumuon);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Spinner spnThanhVien = dialog.findViewById(R.id.spnTenTv);
        Spinner spnSach = dialog.findViewById(R.id.spnTenSach);
        CheckBox chkTrangthai = dialog.findViewById(R.id.chkTrangThai);
        EditText edtGia = dialog.findViewById(R.id.edtTienThue_PM);
        EditText edtNgay = dialog.findViewById(R.id.edtNgayThue_PM);

        EditText edtGioMuon = dialog.findViewById(R.id.edtGioMuon);

        AdapterSpinerSach adapterSpinerSach;
        itemSach = new ArrayList<>();
        itemSach = MainActivity.daoSach.getAll();
        adapterSpinerSach = new AdapterSpinerSach(requireContext(),itemSach);
        spnSach.setAdapter(adapterSpinerSach);

        AdapterSpinerThanhVien adapterSpinerThanhVien;
        itemThanhVien = new ArrayList<>();
        itemThanhVien = MainActivity.daoThanhVien.getAll();
        adapterSpinerThanhVien = new AdapterSpinerThanhVien(requireContext(),itemThanhVien);
        spnThanhVien.setAdapter(adapterSpinerThanhVien);

        edtNgay.setText(currentDate);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                giaThue =  itemSach.get(position).getGiaThue();
                edtGia.setText(giaThue+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtGia.setText(0);
            }
        });

        Button btnLuu,btnHuy;
        btnLuu = dialog.findViewById(R.id.btnLuu_PM);
        btnHuy = dialog.findViewById(R.id.btnHuy_PM);


        btnLuu.setOnClickListener(v->{
            int selectedItemPosition = spnThanhVien.getSelectedItemPosition();
            ThanhVien thanhVien = itemThanhVien.get(selectedItemPosition);
            Sach sach = itemSach.get(spnSach.getSelectedItemPosition());

            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaTV(thanhVien.getID());
            phieuMuon.setTienThue(sach.getGiaThue());
            phieuMuon.setMaSach(sach.getMaSach());
            phieuMuon.setGioMuon(edtGioMuon.getText().toString());
            int check = chkTrangthai.isChecked() ? 0 : 1;
            phieuMuon.setTraSach(check);
            try {
                phieuMuon.setNgay(sdf.parse(currentDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            long checkInsert = MainActivity.daoPhieuMuon.insert(phieuMuon);
                if (checkInsert != -1) {
                    phieuMuon.setMaPM((int) checkInsert);
                    list.add(phieuMuon);
                    adtPhieuMuon.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

}