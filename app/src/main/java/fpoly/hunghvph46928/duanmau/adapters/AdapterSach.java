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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.dao.DAOThuThu;
import fpoly.hunghvph46928.duanmau.fragments.FragmentQLSach;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.ThanhVien;

public class AdapterSach extends RecyclerView.Adapter<AdapterSach.ViewHolder> {
    List<Sach> list;
    Context context;

    public AdapterSach(List<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = list.get(position);
        LoaiSach loaiSach = MainActivity.daoLoaiSach.getID(sach.getMaLoai());
        holder.tvTheloai.setText(loaiSach.getTenLoai());
        holder.tvMasach.setText(sach.getMaSach()+"");
        holder.tvTensach.setText(sach.getTenSach());
        holder.tvGiathue.setText(sach.getGiaThue()+"");
        holder.ivDel.setOnClickListener(v->delete(position,sach.getMaSach()));
        holder.ivUpdate.setOnClickListener(v-> updateSach(sach,position));
    }
    public void delete(int posision,int maSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (MainActivity.daoPhieuMuon.checkSach(maSach)) {

                long check = MainActivity.daoSach.delete(maSach);
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
                    Toast.makeText(context, "Vui lòng xóa hết phiếu mượn tồn tại mã sách này", Toast.LENGTH_SHORT).show();
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
    public void updateSach(Sach sach1,int positon) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sach);
        EditText edtTensach,edtGiaSach;
        TextView tvMasach;
        Spinner spnLoaisach = dialog.findViewById(R.id.spLoaisach);
        AdapterSpinerLoaiSach adapterSpinerLoaiSach;
        List<LoaiSach> items;
        items = MainActivity.daoLoaiSach.getAll();
        adapterSpinerLoaiSach = new AdapterSpinerLoaiSach(context,items);
        spnLoaisach.setAdapter(adapterSpinerLoaiSach);

        Button btnLuu,btnHuy;
        tvMasach = dialog.findViewById(R.id.tvMasach_Sach);
        edtTensach = dialog.findViewById(R.id.edtTensach);
        edtGiaSach = dialog.findViewById(R.id.edtGiaThue);
        btnLuu = dialog.findViewById(R.id.btnLuu_sach);
        btnHuy = dialog.findViewById(R.id.btnHuy_Sach);

            tvMasach.setText(sach1.getMaSach() + "");
            edtGiaSach.setText(sach1.getGiaThue() + "");
            edtTensach.setText(sach1.getTenSach());
            int index = 0;
            for (LoaiSach loaiSach : items) {
                if (loaiSach.getMaLoai() == sach1.getMaLoai()) {
                    break;
                } else {
                    index++;
                }
            }
            spnLoaisach.setSelection(index);

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
            sach.setMaSach(sach1.getMaSach());
            if (tenSach.isEmpty()||giaSach < 0|| selectedItemPosition <0) {
                Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                long checkupdate = MainActivity.daoSach.update(sach);
                if (checkupdate != 0) {
                    list.set(positon,sach);
                    notifyItemChanged(positon);
                    dialog.dismiss();
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                }
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
        TextView tvMasach,tvTensach,tvTheloai,tvGiathue;
        ImageView ivDel,ivUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMasach = itemView.findViewById(R.id.tvMaSach);
            tvTensach = itemView.findViewById(R.id.tvTenSach);
            tvTheloai = itemView.findViewById(R.id.tvTenLoaiSach);
            tvGiathue = itemView.findViewById(R.id.tvGiaThue);
            ivDel = itemView.findViewById(R.id.ivDel_Sach);
            ivUpdate = itemView.findViewById(R.id.ivUpdate_Sach);
        }
    }

}
