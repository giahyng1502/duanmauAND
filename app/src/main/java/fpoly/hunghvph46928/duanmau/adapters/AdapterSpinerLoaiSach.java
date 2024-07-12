package fpoly.hunghvph46928.duanmau.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.models.LoaiSach;

public class AdapterSpinerLoaiSach extends ArrayAdapter<LoaiSach> {
    private Context context;
    private List<LoaiSach> list;

    public AdapterSpinerLoaiSach(@NonNull Context context, @NonNull List<LoaiSach> list) {
        super(context, R.layout.custom_spn_loaisach, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position,convertView,parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spn_loaisach, parent, false);
        }

        LoaiSach loaiSach = list.get(position);
        TextView maloai = convertView.findViewById(R.id.maLoai_spn);
        maloai.setText(loaiSach.getMaLoai()+"");
        TextView tenLoai = convertView.findViewById(R.id.tvTenloai_spn);
        tenLoai.setText(loaiSach.getTenLoai());
        return convertView;
    }
}
