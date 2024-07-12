package fpoly.hunghvph46928.duanmau.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.models.Top;

public class AdapterTop10 extends RecyclerView.Adapter<AdapterTop10.ViewHolder> {
    Context context;
    List<Top> list;

    public AdapterTop10(Context context, List<Top> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_sach_top10,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Top top10 = list.get(position);
        holder.tvTensach.setText(top10.getTenSach());
        holder.tvSoluong.setText(top10.getSoLuong()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTensach,tvSoluong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTensach = itemView.findViewById(R.id.tvTop_Tensach);
            tvSoluong = itemView.findViewById(R.id.tvTop_Soluong);
        }
    }
}
