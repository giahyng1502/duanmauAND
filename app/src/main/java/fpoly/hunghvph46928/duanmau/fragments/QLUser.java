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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.adapters.AdapterThuThu;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class QLUser extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    List<ThuThu> list;
    AdapterThuThu adapterThuThu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ánh xạ
        recyclerView = view.findViewById(R.id.recylerViewUser);
        btnAdd = view.findViewById(R.id.btnAddTT);
        // lấy data từ sqlive
        list = MainActivity.daoThuthu.getAll();
        adapterThuThu = new AdapterThuThu(list,requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterThuThu);
        // scroll để ẩn btnadd
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        btnAdd.setOnClickListener(v->themTT());
    }
    public void themTT() {
        //dialog thêm
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dia_thuthu);
        //ánh xạ
        EditText edtMaTT = dialog.findViewById(R.id.edtMaTT);
        EditText edtMatKhau = dialog.findViewById(R.id.edtMatKhau);
        EditText edtTen = dialog.findViewById(R.id.edtHoten);
        Button btnThem = dialog.findViewById(R.id.btnThem_TT);
        Button btnHuy = dialog.findViewById(R.id.btnHuy_TT);

        btnThem.setOnClickListener(v-> {
            String maTT = edtMaTT.getText().toString();
            String matKhau = edtMatKhau.getText().toString();
            String ten = edtTen.getText().toString();
            if (!maTT.isEmpty()|| matKhau.isEmpty()||ten.isEmpty()) {
                if (checkValue(maTT)) {
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(maTT);
                    thuThu.setMatKhau(matKhau);
                    thuThu.setHoTen(ten);
                    long check = MainActivity.daoThuthu.insert(thuThu);
                    if (check >= 0) {
                        list.add(thuThu);
                        adapterThuThu.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(requireContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Mã thủ thư đã tồn tại", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(requireContext(), "Vui lòng đầy thủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }
    public boolean checkValue(String maTT) {
        // check mã thủ thư nếu tồn tại yêu cầu nhập lại mã thủ thư
        for (ThuThu thuThu : list) {
            if (thuThu.getMaTT().equals(maTT)) {
                return false;
            }
        }
        return true;
    }

}