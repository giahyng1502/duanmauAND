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

public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.ViewHolder> {
    List<LoaiSach> list;
    Context context;

    public AdapterLoaiSach(List<LoaiSach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_loaisach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.tvMaloai.setText(loaiSach.getMaLoai()+"");
        holder.tvTenLoai.setText(loaiSach.getTenLoai());
        holder.ivDel.setOnClickListener(v->delete(position,loaiSach.getMaLoai()));
        holder.ivUpdate.setOnClickListener(v-> themLoaiSach(loaiSach,position));
    }
    public void delete(int posision,int maLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (MainActivity.daoSach.checkLoaiSach(maLoai)) {
                long check = MainActivity.daoLoaiSach.delete(maLoai);
                if (check > 0) {
                    list.remove(posision);
                    notifyItemRemoved(posision);
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } else {
                    Toast.makeText(context, "Vui lòng xóa hết sách thuộc loại sách này", Toast.LENGTH_SHORT).show();
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
    public void themLoaiSach(LoaiSach loaiSach,int positon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_update);
        EditText edtTenLoai = dialog.findViewById(R.id.edt_UD_TenLoai);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Button btnCancel = dialog.findViewById(R.id.btn_UD_Huy);

        edtTenLoai.setText(loaiSach.getTenLoai());
        btnUpdate.setOnClickListener(v-> {
            String tenLoai = edtTenLoai.getText().toString();
            if (!tenLoai.isEmpty()) {
                if (checkTenLoai(tenLoai)) {
                    loaiSach.setTenLoai(tenLoai);
                    loaiSach.setMaLoai(loaiSach.getMaLoai());
                    long check = MainActivity.daoLoaiSach.update(loaiSach);
                    if (check > 0) {
                        list.set(positon,loaiSach);
                        notifyItemChanged(positon);
                        dialog.dismiss();
                        Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                Toast.makeText(context, "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
            }

        });
        btnCancel.setOnClickListener(v->dialog.dismiss());
        dialog.show();
    }

    public boolean checkTenLoai(String tenLoai) {
        for (LoaiSach loaiSach : list) {
            if (loaiSach.getTenLoai().equalsIgnoreCase(tenLoai)) {
                Toast.makeText(context, "Tên loại sách đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaloai,tvTenLoai;
        ImageView ivDel,ivUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaloai = itemView.findViewById(R.id.tvMaloai);
            tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivUpdate = itemView.findViewById(R.id.ivUpdate);
        }
    }

}
