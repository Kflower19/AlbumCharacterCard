package com.example.albumcardnhanvat;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1Fragment newInstance(String param1, String param2) {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        mListTab1 = new ArrayList<>(); // Khởi tạo danh sách
    }

    private RecyclerView rcvTab1FragmentHome;
    private Tab1FragmentAdapter mTab1FragmentAdapter;
    private Button addBtn;
    private List<Tab1FragmentHome> mListTab1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        TextView tvGreeting = view.findViewById(R.id.tv_greeting);

        Intent intent = requireActivity().getIntent();

        Bundle mybundle = intent.getBundleExtra("package");

        // Lấy tên tài khoản từ Bundle
        //Bundle bundle = getArguments();
        if (intent.getStringExtra("USERNAME") != null) {
            //String username = mybundle.getString("USERNAME");
            String username = intent.getStringExtra("USERNAME");
            tvGreeting.setText("Xin chào: " + username);
        } else {
            tvGreeting.setText("Xin chào! Hãy đăng nhập tài khoản" );
        }


        addBtn = view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> showAddDialog());

        rcvTab1FragmentHome = view.findViewById(R.id.rcv_user);
        mTab1FragmentAdapter = new Tab1FragmentAdapter(getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvTab1FragmentHome.setLayoutManager(gridLayoutManager);

        mListTab1.addAll(getListUser()); // Thêm dữ liệu ban đầu
        mTab1FragmentAdapter.setData(mListTab1);
        rcvTab1FragmentHome.setAdapter(mTab1FragmentAdapter);

        return view;
    }


    //su kien them anh
    private void showAddDialog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        ImageView imgPreview = dialogView.findViewById(R.id.img_preview);
        TextView edtName = dialogView.findViewById(R.id.edt_name);
        RecyclerView rcvImageList = dialogView.findViewById(R.id.rcv_image_list);

        List<Integer> imageList = getDrawableImages();
        ImageListAdapter adapter = new ImageListAdapter(getContext(), imageList, selectedImage -> {
            imgPreview.setImageResource(selectedImage);
            imgPreview.setTag(selectedImage); // Gán tag
        });
        rcvImageList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcvImageList.setAdapter(adapter);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString();
            if (!name.isEmpty() && imgPreview.getDrawable() != null) {
                int imageRes = (int) imgPreview.getTag();
                mListTab1.add(new Tab1FragmentHome(imageRes, name));
                mTab1FragmentAdapter.notifyItemInserted(mListTab1.size() - 1);
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private List<Integer> getDrawableImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img9);
        images.add(R.drawable.img8);
        images.add(R.drawable.img7);
        images.add(R.drawable.img6);
        images.add(R.drawable.img5);
        images.add(R.drawable.img4);
        images.add(R.drawable.img3);
        images.add(R.drawable.img2);
        images.add(R.drawable.img1);
        return images;
    }

    private List<Tab1FragmentHome> getListUser(){
        List<Tab1FragmentHome> list = new ArrayList<>();
        list.add(new Tab1FragmentHome(R.drawable.img1,"User name 1"));
        list.add(new Tab1FragmentHome(R.drawable.img2,"User name 2"));
        list.add(new Tab1FragmentHome(R.drawable.img3,"User name 3"));
        list.add(new Tab1FragmentHome(R.drawable.img4,"User name 4"));
        list.add(new Tab1FragmentHome(R.drawable.img5,"User name 5"));

        list.add(new Tab1FragmentHome(R.drawable.img1,"User name 6"));
        list.add(new Tab1FragmentHome(R.drawable.img2,"User name 7"));
        list.add(new Tab1FragmentHome(R.drawable.img3,"User name 8"));
        list.add(new Tab1FragmentHome(R.drawable.img4,"User name 9"));
        list.add(new Tab1FragmentHome(R.drawable.img5,"User name 10"));


        return list;
    }
}