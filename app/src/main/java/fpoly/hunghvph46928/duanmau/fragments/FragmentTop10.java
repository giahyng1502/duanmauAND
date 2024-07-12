package fpoly.hunghvph46928.duanmau.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fpoly.hunghvph46928.duanmau.MainActivity;
import fpoly.hunghvph46928.duanmau.R;
import fpoly.hunghvph46928.duanmau.adapters.AdapterSach;
import fpoly.hunghvph46928.duanmau.adapters.AdapterTop10;
import fpoly.hunghvph46928.duanmau.models.Sach;
import fpoly.hunghvph46928.duanmau.models.Top;


public class FragmentTop10 extends Fragment {
    private RecyclerView recyclerView;
    List<Top> list;
    AdapterTop10 adapterTop10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ánh xạ
        recyclerView = view.findViewById(R.id.recyclerViewTop10);
        // lấy data từ sqlive
        list = MainActivity.daoPhieuMuon.getTop10();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        adapterTop10 = new AdapterTop10(requireContext(),list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterTop10);
    }
}