package com.example.albumcardnhanvat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Tab1FragmentAdapter extends RecyclerView.Adapter<Tab1FragmentAdapter.Tab1FragmentViewHolder>{

    private Context context;
    private List<Tab1FragmentHome> mListTab1;

    public Tab1FragmentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Tab1FragmentHome> list){
        this.mListTab1 = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Tab1FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new Tab1FragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab1FragmentViewHolder holder, int position) {
        Tab1FragmentHome tab1FragmentHome = mListTab1.get(position);
        if(tab1FragmentHome == null){
            return;
        }

        holder.imgUser.setImageResource(tab1FragmentHome.getResourceImage());
        holder.tvName.setText(tab1FragmentHome.getName());

        // Hiển thị hoặc ẩn dấu tích xanh
        if (tab1FragmentHome.isSelected()) {
            holder.imgCheck.setVisibility(View.VISIBLE);
        } else {
            holder.imgCheck.setVisibility(View.GONE);
        }


        holder.itemView.setOnLongClickListener(v->{
            showContextMenu(v, position);
            return true;
        });


        holder.itemView.setOnClickListener(v -> showFullScreenDialog(position));


    }

    //cham vao de hien full man hinh
    private void showFullScreenDialog(int position) {
        Tab1FragmentHome item = mListTab1.get(position);
        if (item == null) return;

        // Tạo dialog toàn màn hình
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_full_image, null);
        builder.setView(dialogView);

        ImageView imgFullScreen = dialogView.findViewById(R.id.img_full_screen);
        Button btnEdit = dialogView.findViewById(R.id.btn_edit);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnClose = dialogView.findViewById(R.id.btn_close);

        // Hiển thị hình ảnh
        imgFullScreen.setImageResource(item.getResourceImage());

        android.app.AlertDialog dialog = builder.create();

        // Xử lý nút "Chỉnh sửa"
        btnEdit.setOnClickListener(v -> {
            dialog.dismiss();
            handleEdit(position);
        });

        // Xử lý nút "Xóa"
        btnDelete.setOnClickListener(v -> {
            dialog.dismiss();
            handleDelete(position);
        });

        // Đóng dialog
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    //menu khi an lau hinh anh
    private void showContextMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_context);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_edit) {
                // Xử lý chỉnh sửa
                handleEdit(position);
                return true;
            } else if (item.getItemId() == R.id.menu_delete) {
                // Xử lý xóa
                handleDelete(position);
                return true;
            } else if (item.getItemId() == R.id.menu_select) {
                // Xử lý chọn
                handleSelect(position);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    // Xử lý các hành dong chinh sua hinh anh
    private void handleEdit(int position) {
        // Lấy item cần chỉnh sửa
        Tab1FragmentHome item = mListTab1.get(position);
        if (item == null) return;

        // Tạo Dialog để chỉnh sửa
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_item, null);
        builder.setView(dialogView);

        // Ánh xạ các thành phần trong dialog
        TextView tvDialogTitle = dialogView.findViewById(R.id.tv_dialog_title);
        ImageView imgPreview = dialogView.findViewById(R.id.img_preview);
        TextView edtName = dialogView.findViewById(R.id.edt_name);
        RecyclerView rcvImageList = dialogView.findViewById(R.id.rcv_image_list);

        // Hiển thị thông tin hiện tại
        imgPreview.setImageResource(item.getResourceImage());
        edtName.setText(item.getName());

        // Danh sách ảnh trong drawable
        List<Integer> imageList = getDrawableImages();
        ImageListAdapter adapter = new ImageListAdapter(context, imageList, selectedImage -> {
            // Cập nhật ảnh xem trước khi chọn
            imgPreview.setImageResource(selectedImage);
            item.setResourceImage(selectedImage);
        });
        rcvImageList.setLayoutManager(new GridLayoutManager(context, 3));
        rcvImageList.setAdapter(adapter);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            // Lưu thay đổi
            item.setName(edtName.getText().toString());
            notifyItemChanged(position);
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    //xoa phan tu trong mang
    private void handleDelete(int position) {
        mListTab1.remove(position);
        notifyItemRemoved(position);
    }

    //thay doi hien dau tich xanh
    private void handleSelect(int position) {
        Tab1FragmentHome item = mListTab1.get(position);
        if (item != null) {
            item.setSelected(!item.isSelected()); // Đổi trạng thái
            notifyItemChanged(position); // Cập nhật giao diện item
        }
    }

    @Override
    public int getItemCount() {
        if(mListTab1 != null){
            return mListTab1.size();
        }
        return 0;
    }

    public class Tab1FragmentViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private ImageView imgCheck;
        private TextView tvName;

        public Tab1FragmentViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.img_user);
            imgCheck = itemView.findViewById(R.id.img_check);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    private List<Integer> getDrawableImages() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.img1);
        images.add(R.drawable.img2);
        images.add(R.drawable.img9);
        images.add(R.drawable.img4);
        images.add(R.drawable.img5);
        images.add(R.drawable.img6);
        images.add(R.drawable.img7);
        images.add(R.drawable.img8);
        images.add(R.drawable.img3);
        return images;
    }
}
