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
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class AdapterThuThu extends RecyclerView.Adapter<AdapterThuThu.ViewHolder> {
    List<ThuThu> list;
    Context context;

    public AdapterThuThu(List<ThuThu> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_thuthu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThuThu thuThu = list.get(position);
        holder.tvMaTT.setText(thuThu.getMaTT()+"");
        holder.tvMatKhau.setText(thuThu.getMatKhau());
        holder.tvHoten.setText(thuThu.getHoTen());
        holder.ivDel.setOnClickListener(v->delete(position,thuThu.getMaTT()));
        holder.ivUpdate.setOnClickListener(v-> updateTT(thuThu,position));
    }
    public void delete(int posision,String maTT) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (checkLogin(maTT)) {
                    long check = MainActivity.daoThuthu.delete(maTT);
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
                    Toast.makeText(context, "Bạn đang đăng nhập bằng tài khoản này", Toast.LENGTH_SHORT).show();
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
    public boolean checkLogin(String maTT) {
        if (MainActivity.thuThu.getMaTT().equals(maTT)) {
            return false;
        }
        return true;
    }
  public void updateTT(ThuThu thuThu1,int positon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dia_thuthu);
        EditText edtMaTT = dialog.findViewById(R.id.edtMaTT);
        EditText edtMatKhau = dialog.findViewById(R.id.edtMatKhau);
        EditText edtTen = dialog.findViewById(R.id.edtHoten);
        Button btnThem = dialog.findViewById(R.id.btnThem_TT);
        Button btnHuy = dialog.findViewById(R.id.btnHuy_TT);
        edtMaTT.setEnabled(false);
        edtMaTT.setText(thuThu1.getMaTT());
        edtTen.setText(thuThu1.getHoTen());
        edtMatKhau.setText(thuThu1.getMatKhau());
        btnThem.setOnClickListener(v-> {
            String maTT = edtMaTT.getText().toString();
            String matKhau = edtMatKhau.getText().toString();
            String ten = edtTen.getText().toString();
            if (!maTT.isEmpty()|| matKhau.isEmpty()||ten.isEmpty()) {
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(thuThu1.getMaTT());
                    thuThu.setMatKhau(matKhau);
                    thuThu.setHoTen(ten);
                    long check = MainActivity.daoThuthu.update(thuThu);
                    if (check != 0) {
                        list.set(positon,thuThu);
                        dialog.dismiss();
                        notifyItemChanged(positon);
                        Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
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
        TextView tvMaTT,tvHoten,tvMatKhau;
        ImageView ivDel,ivUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTT = itemView.findViewById(R.id.tvMaTT);
            tvHoten = itemView.findViewById(R.id.tvHoten_TT);
            tvMatKhau = itemView.findViewById(R.id.tvMatKhau);

            ivDel = itemView.findViewById(R.id.ivDel_TT);
            ivUpdate = itemView.findViewById(R.id.ivUpdate_TT);
        }
    }

}
