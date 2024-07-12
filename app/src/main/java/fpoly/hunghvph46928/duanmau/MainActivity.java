package fpoly.hunghvph46928.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import fpoly.hunghvph46928.duanmau.dao.DAOLoaiSach;
import fpoly.hunghvph46928.duanmau.dao.DAOPhieuMuon;
import fpoly.hunghvph46928.duanmau.dao.DAOSach;
import fpoly.hunghvph46928.duanmau.dao.DAOThanhVien;
import fpoly.hunghvph46928.duanmau.dao.DAOThuThu;
import fpoly.hunghvph46928.duanmau.fragments.Doi_MatKhau;
import fpoly.hunghvph46928.duanmau.fragments.FragmentDoanhThu;
import fpoly.hunghvph46928.duanmau.fragments.FragmentDoiMatKhau;
import fpoly.hunghvph46928.duanmau.fragments.FragmentPhieuMuon;
import fpoly.hunghvph46928.duanmau.fragments.FragmentQLSach;
import fpoly.hunghvph46928.duanmau.fragments.FragmentQuanLyThanhVien;
import fpoly.hunghvph46928.duanmau.fragments.FragmentTop10;
import fpoly.hunghvph46928.duanmau.fragments.Fragment_QLLoaiSach;
import fpoly.hunghvph46928.duanmau.fragments.QLUser;
import fpoly.hunghvph46928.duanmau.models.ThuThu;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fragment = null;
    public static DAOSach daoSach;
    public static DAOThuThu daoThuthu;
    public static DAOLoaiSach daoLoaiSach;
    public static DAOPhieuMuon daoPhieuMuon;
    public static DAOThanhVien daoThanhVien;
    public static ThuThu thuThu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        setSupportActionBar(toolbar);
        daoLoaiSach = new DAOLoaiSach(this);
        daoThanhVien = new DAOThanhVien(this);
        daoSach = new DAOSach(this);
        daoPhieuMuon = new DAOPhieuMuon(this);
        daoThuthu = new DAOThuThu(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FragmentQLSach()).commit();

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (R.id.nav_PhieuMuon == item.getItemId()) {
                    fragment = new FragmentPhieuMuon();
                } else if (R.id.nav_ThanhVien == item.getItemId()) {
                    fragment = new FragmentQuanLyThanhVien();
                } else if (R.id.nav_LoaiSach == item.getItemId()) {
                    fragment = new Fragment_QLLoaiSach();
                } else if (R.id.nav_Sach == item.getItemId()) {
                    fragment = new FragmentQLSach();
                } else if (R.id.sub_DoanhThu == item.getItemId()) {
                    fragment = new FragmentDoanhThu();
                } else if (R.id.sub_Pass == item.getItemId()) {
                    fragment = new FragmentDoiMatKhau();
                } else if (R.id.sub_Top == item.getItemId()) {
                    fragment = new FragmentTop10();
                } else if (R.id.qlUser == item.getItemId()) {
                    fragment = new QLUser();
                } else if (R.id.sub_Logout == item.getItemId()) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Đăng Xuất Thành Công", Toast.LENGTH_SHORT).show();
                }
                    if (fragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
                        drawerLayout.close();
                    }
                return true;
            }
                    });
        setNameUser();
            if (navigationView != null) {
                if (!checkAdmin(thuThu)) {
                    navigationView.getMenu().findItem(R.id.qlUser).setVisible(false);
                }}
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.fragment);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navLayout);
    }
    // Phương thức này được gọi để hiển thị tên người dùng lên thanh tiêu đề của NavigationView
    private void setNameUser() {
        // Lấy Intent từ Activity hiện tại
        Intent intent = getIntent();
        // Lấy chuỗi "user" từ Intent, giả định rằng đây là tên người dùng
        String user = intent.getStringExtra("user");

        // Lấy thông tin chi tiết của người dùng từ cơ sở dữ liệu
        thuThu = daoThuthu.getID(user);

        // Lấy View của header trong NavigationView để hiển thị thông tin người dùng
        View view = navigationView.getHeaderView(0);
        // Tìm TextView có id là tvUser trong header View
        TextView tvTen = view.findViewById(R.id.tvUser);
        // Thiết lập văn bản của TextView thành tên của người dùng
        tvTen.setText(thuThu.getHoTen());
    }

    private boolean checkAdmin (ThuThu thuThu1) {
        if (thuThu1.getMaTT().equalsIgnoreCase("admin")) {
            return true;
        } return false;
    }
}