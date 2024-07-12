package fpoly.hunghvph46928.duanmau.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;

public class AdapterThanhVien extends RecyclerView.Adapter<AdapterThanhVien.ViewHolder> {
    List<ThanhVien> list;
    Context context;

    public AdapterThanhVien(List<ThanhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_thanhvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.tvMaTV.setText(thanhVien.getID()+"");
        holder.tvTenTV.setText(thanhVien.getHoTen());
        holder.tvNamsinh.setText(thanhVien.getNamSinh());
        holder.ivDel.setOnClickListener(v->delete(position,thanhVien.getID()));
        holder.ivUpdate.setOnClickListener(v-> UpdateThanhVien(thanhVien,position));
    }
    public void delete(int posision,int maTV) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (MainActivity.daoPhieuMuon.checkThanhVien(maTV)) {
                long check = MainActivity.daoThanhVien.delete(maTV);
                if (check > 0) {
                    list.remove(posision);
                    notifyItemRemoved(posision);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                }else {
                    Toast.makeText(context, "Vui lòng xóa hết phiếu mượn tồn tại mã thanh viên này", Toast.LENGTH_SHORT).show();
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
    public void UpdateThanhVien(ThanhVien thanhVien,int positon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_thanhvien);
        EditText edtTenThanhVien = dialog.findViewById(R.id.edtTenThanhvien);
        EditText edtNamsinh = dialog.findViewById(R.id.edtNamsinh);
        Button btnThem = dialog.findViewById(R.id.btnThem_TV);
        Button btnHuy = dialog.findViewById(R.id.btnHuy_TV);
        edtTenThanhVien.setText(thanhVien.getHoTen());
        edtNamsinh.setText(thanhVien.getNamSinh());
        btnThem.setOnClickListener(v-> {
            String tenThanhVien = edtTenThanhVien.getText().toString();
            String namsinh = edtNamsinh.getText().toString();
            if (!tenThanhVien.isEmpty()) {
                thanhVien.setHoTen(tenThanhVien);
                thanhVien.setNamSinh(namsinh);
                long check = MainActivity.daoThanhVien.update(thanhVien);
                if (check > 0) {
                    list.set(positon,thanhVien);
                    notifyItemChanged(positon);
                    dialog.dismiss();
                    Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(context, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
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
        TextView tvMaTV,tvTenTV,tvNamsinh;
        ImageView ivDel,ivUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvTenTV = itemView.findViewById(R.id.tvHoten);
            tvNamsinh = itemView.findViewById(R.id.tvNamsinh);
            ivDel = itemView.findViewById(R.id.ivDel_TV);
            ivUpdate = itemView.findViewById(R.id.ivUpdate_TV);
        }
    }

}
