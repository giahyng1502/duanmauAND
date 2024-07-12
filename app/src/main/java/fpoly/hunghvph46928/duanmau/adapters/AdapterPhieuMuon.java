package fpoly.hunghvph46928.duanmau.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.PhieuMuon;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class AdapterPhieuMuon extends RecyclerView.Adapter<AdapterPhieuMuon.ViewHolder> {
    List<PhieuMuon> list;
    Context context;
    SimpleDateFormat sdf;

    public AdapterPhieuMuon(List<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void setFillterList(List<PhieuMuon> fillterList) {
        this.list = fillterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_phieumuon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        ThanhVien thanhVien = MainActivity.daoThanhVien.getID(phieuMuon.getMaTV());
//        ThuThu thuThu = MainActivity.daoThuthu.getID(phieuMuon.getMaTT());
        Sach sach = MainActivity.daoSach.getID(phieuMuon.getMaSach());
        holder.tvMaPm.setText(phieuMuon.getMaPM()+"");
        holder.tvThanhVien.setText(thanhVien.getHoTen());
        holder.tvTenSach.setText(sach.getTenSach());
        if (sach.getGiaThue() > 50000) {
            holder.tvTenSach.setTextColor(Color.RED);
        } else {
            holder.tvTenSach.setTextColor(Color.BLUE);
        }
        holder.tvGiaThue.setText(sach.getGiaThue()+"");

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.tvNgaythue.setText(sdf.format(phieuMuon.getNgay()));
        if (phieuMuon.getTraSach() == 0) {
            holder.tvCheck.setText("Đã trả sách");
        } else {
            holder.tvCheck.setText("Chưa trả sách");
        }
        holder.tvGioMuon.setText(phieuMuon.getGioMuon());
        holder.ivDel.setOnClickListener(v->delete(position,phieuMuon.getMaPM()));
        holder.ivUpdate.setOnClickListener(v->update(phieuMuon,position));
    }
    public void delete(int posision,int maPm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long check = MainActivity.daoPhieuMuon.delete(maPm);
                if (check > 0) {
                    list.remove(posision);
                    notifyItemRemoved(posision);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public void update(PhieuMuon phieuMuon,int positon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phieumuon);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Spinner spnThanhVien = dialog.findViewById(R.id.spnTenTv);
        Spinner spnSach = dialog.findViewById(R.id.spnTenSach);
        CheckBox chkTrangthai = dialog.findViewById(R.id.chkTrangThai);
        EditText edtGia = dialog.findViewById(R.id.edtTienThue_PM);
        EditText edtNgay = dialog.findViewById(R.id.edtNgayThue_PM);
        TextView tvMapm  = dialog.findViewById(R.id.tvMaPm_PM);
        EditText edtgioMuon = dialog.findViewById(R.id.edtGioMuon);
        tvMapm.setText(phieuMuon.getMaPM()+"");


        AdapterSpinerSach adapterSpinerSach;
        List<Sach> itemSach;
        itemSach = MainActivity.daoSach.getAll();
        adapterSpinerSach = new AdapterSpinerSach(context,itemSach);
        spnSach.setAdapter(adapterSpinerSach);
        int indexSach = 0;
        for (Sach s:itemSach) {
            if (s.getMaSach() == phieuMuon.getMaSach() ) {
                break;
            } else indexSach++;
        }
        spnSach.setSelection(indexSach);
        edtgioMuon.setText(phieuMuon.getGioMuon());
        AdapterSpinerThanhVien adapterSpinerThanhVien;
        List<ThanhVien> itemThanhVien;
        itemThanhVien = MainActivity.daoThanhVien.getAll();
        adapterSpinerThanhVien = new AdapterSpinerThanhVien(context,itemThanhVien);
        spnThanhVien.setAdapter(adapterSpinerThanhVien);
        int indexThanhVien = 0;
        for (ThanhVien tv :itemThanhVien) {
            if (tv.getID() == phieuMuon.getMaTV() ) {
                break;
            } else indexThanhVien++;
        }
        spnThanhVien.setSelection(indexThanhVien);
        edtNgay.setText(currentDate);

        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sach sach =  itemSach.get(position);
                edtGia.setText(sach.getGiaThue()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtGia.setText(0);
            }
        });
        if (phieuMuon.getTraSach() == 0) {
            chkTrangthai.setChecked(true);
        } else chkTrangthai.setChecked(false);

        Button btnLuu,btnHuy;
        btnLuu = dialog.findViewById(R.id.btnLuu_PM);
        btnHuy = dialog.findViewById(R.id.btnHuy_PM);


        btnLuu.setOnClickListener(v->{
            int selectedItemPosition = spnThanhVien.getSelectedItemPosition();
            ThanhVien thanhVien1 = itemThanhVien.get(selectedItemPosition);
            Sach sach1 = itemSach.get(spnSach.getSelectedItemPosition());

            PhieuMuon phieuMuon2 = new PhieuMuon();
            phieuMuon2.setMaPM(phieuMuon.getMaPM());
            phieuMuon2.setMaTV(thanhVien1.getID());
            phieuMuon2.setTienThue(sach1.getGiaThue());
            phieuMuon2.setMaSach(sach1.getMaSach());
            phieuMuon2.setGioMuon(edtgioMuon.getText().toString());
            int check = chkTrangthai.isChecked() ? 0 : 1;
            phieuMuon2.setTraSach(check);
            try {
                phieuMuon2.setNgay(sdf.parse(currentDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            long checkInsert = MainActivity.daoPhieuMuon.update(phieuMuon2);
            if (checkInsert > 0) {
                list.set(positon,phieuMuon2);
                notifyItemChanged(positon);
                dialog.dismiss();
                Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Update mới thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPm,tvThanhVien,tvTenSach,tvTienThue,tvCheck,tvGiaThue,tvNgaythue,tvGioMuon;
        ImageView ivDel,ivUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPm = itemView.findViewById(R.id.tvMaPM);
            tvGioMuon = itemView.findViewById(R.id.tvGioMuon);
            tvNgaythue = itemView.findViewById(R.id.tvNgaythue);
            tvThanhVien = itemView.findViewById(R.id.tvThanhVien);
            tvTenSach = itemView.findViewById(R.id.tvTensach_Muon);
            tvTienThue = itemView.findViewById(R.id.tvTienThue_Muon);
            tvCheck = itemView.findViewById(R.id.tvTrangThai);
            tvGiaThue = itemView.findViewById(R.id.tvTienThue_Muon);
            ivDel = itemView.findViewById(R.id.ivDel_Muon);
            ivUpdate = itemView.findViewById(R.id.ivUpdate_Muon);
        }
    }

}
