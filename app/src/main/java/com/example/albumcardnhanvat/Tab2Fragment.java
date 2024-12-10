package com.example.albumcardnhanvat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2Fragment newInstance(String param1, String param2) {
        Tab2Fragment fragment = new Tab2Fragment();
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
    }

    private EditText usernameField, passwordField;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        // Initialize views and database helper
        usernameField = view.findViewById(R.id.usernameField);
        passwordField = view.findViewById(R.id.passwordField);
        MaterialButton loginButton = view.findViewById(R.id.loginButton);
        MaterialButton dkyButton = view.findViewById(R.id.dkyButton);
        dbHelper = new DatabaseHelper(getContext());

        // dang nhap
        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();


            } else {
                // Kiểm tra thông tin người dùng
                boolean isValid = dbHelper.checkUser(username, password);
                if (isValid) {
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();


                    // Chuyển sang một Activity khác chứa Tab1Fragment
                    Intent intent = new Intent(getActivity(), MainActivity.class); // MainActivity là Activity của bạn


                    Bundle mybundle = new Bundle();
                    mybundle.putString("package", username);

                    intent.putExtra("USERNAME", username); // Truyền dữ liệu qua Intent
                    startActivity(intent);


                } else {
                    Toast.makeText(getContext(), "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //dang ky tai khoan
        dkyButton.setOnClickListener(v -> {
            // Hiển thị Dialog để đăng ký tài khoản
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_register, null);
            EditText newUsernameField = dialogView.findViewById(R.id.newUsernameField);
            EditText newPasswordField = dialogView.findViewById(R.id.newPasswordField);
            EditText confirmPasswordField = dialogView.findViewById(R.id.confirmPasswordField);

            // Tạo Dialog
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            builder.setView(dialogView);
            builder.setTitle("Đăng ký tài khoản");
            builder.setPositiveButton("Đăng ký", (dialog, which) -> {
                String newUsername = newUsernameField.getText().toString().trim();
                String newPassword = newPasswordField.getText().toString().trim();
                String confirmPassword = confirmPasswordField.getText().toString().trim();

                // Kiểm tra nhập đầy đủ thông tin
                if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu xác nhận
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra trùng tên tài khoản
                if (dbHelper.checkUser(newUsername)) {
                    Toast.makeText(getContext(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm tài khoản mới vào cơ sở dữ liệu
                boolean isInserted = dbHelper.insertUser(newUsername, newPassword);
                if (isInserted) {
                    Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        return view;
    }
}