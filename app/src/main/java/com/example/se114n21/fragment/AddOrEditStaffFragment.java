package com.example.se114n21.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.se114n21.Models.Account;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import com.example.se114n21.databinding.FragmentAddStaffBinding;
import com.example.se114n21.utils.GlideUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddOrEditStaffFragment extends BaseFragment {
    FragmentAddStaffBinding addOrEditStaffFragment;
    Uri uri;
    NhanVien nhanVien;
    private String[] arr = new String[3];

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        addOrEditStaffFragment.avatar.setImageURI(uri);
                    } else {
                        Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get data from StaffFragment
        Bundle bundle = getArguments();

        if (bundle != null) {
            nhanVien = (NhanVien) bundle.getSerializable("staff");
        }

        arr[0]= getResources().getString(R.string.sales_agent);
        arr[1]= getResources().getString(R.string.supervisory_staff);
        arr[2]= getResources().getString(R.string.Counselor);

        initEvents();
        initSpinner();
        setData(nhanVien);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr);
        addOrEditStaffFragment.spnTypeStaff.setAdapter(adapter);
    }

    private void setData(NhanVien nhanVien) {
        if (nhanVien != null) {
            addOrEditStaffFragment.txtHoTen.setText(nhanVien.getHoTen());
            addOrEditStaffFragment.txtPhone.setText(nhanVien.getSDT());
            addOrEditStaffFragment.txtEmail.setText(nhanVien.getEmail());
            addOrEditStaffFragment.tvAddStaffAddress.setText(nhanVien.getDiaChi());
            addOrEditStaffFragment.titleAddOrUpdateStaff.setText(getResources().getText(R.string.update_staff));
            GlideUtils.loadUrl(nhanVien.getLinkAvt(), addOrEditStaffFragment.avatar);
        }
    }

    //oke. nếu bạn mệt r thì để mai cũngdđược. ti tui sợ bạn có việc khác á, ok 3 phút
    private void initEvents() {
        // add staff
        addOrEditStaffFragment.imgAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrUpdateStaff();
            }
        });

        //select image staff
        addOrEditStaffFragment.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        //back to staff fragment
        addOrEditStaffFragment.butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStaffFragment();
            }
        });
    }

    private void openGallery() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    private void addOrUpdateStaff() {

        String regex = "\\d+";
        if (!addOrEditStaffFragment.txtPhone.getText().toString().matches(regex)){
            showMessage("so dien thoai khong hop le");
            return;
        }

        showProgressDialog(true);

        if (nhanVien != null) {
            updateStaff();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Staff")
                .child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();

                NhanVien nhanVien = getDataStaff();
                nhanVien.setLinkAvt(urlImage.toString());

                FirebaseDatabase.getInstance().getReference("Staff").child(nhanVien.getMaNV())
                        .setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showProgressDialog(false);
                                    showMessage("Successful");
                                    goToStaffFragment();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showProgressDialog(false);
                                showMessage(e.getMessage());
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showProgressDialog(false);
                showMessage(e.getMessage());
            }
        });
    }

    private void updateStaff() {
        NhanVien nhanVien = getDataStaff();
        nhanVien.setLinkAvt(this.nhanVien.getLinkAvt());

        FirebaseDatabase.getInstance().getReference("Staff").child(nhanVien.getMaNV())
                .setValue(nhanVien).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showProgressDialog(false);
                            showMessage("Successful");
                            goToStaffFragment();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showProgressDialog(false);
                        showMessage(e.getMessage());
                    }
                });
    }

    private void goToStaffFragment() {
        getParentFragmentManager().popBackStack();
    }


    private NhanVien getDataStaff() {
        NhanVien nhanVien = new NhanVien();
        if (this.nhanVien != null) {
            nhanVien.setMaNV(this.nhanVien.getMaNV());
        } else {
            nhanVien.setMaNV(UUID.randomUUID().toString());
        }
        nhanVien.setHoTen(addOrEditStaffFragment.txtHoTen.getText().toString());
        nhanVien.setEmail(addOrEditStaffFragment.txtEmail.getText().toString());
        nhanVien.setDiaChi(addOrEditStaffFragment.tvAddStaffAddress.getText().toString());
        nhanVien.setSDT(addOrEditStaffFragment.txtPhone.getText().toString());

        int type = 1;
        if (addOrEditStaffFragment.spnTypeStaff.getSelectedItem().toString().equals(getResources().getString(R.string.sales_agent))) {
            type = 1;
        } else if (addOrEditStaffFragment.spnTypeStaff.getSelectedItem().toString().equals(getResources().getString(R.string.supervisory_staff))) {
            type = 2;
        }  else {
            type = 3;
        }
        nhanVien.setLoaiNhanVien(type);
        return nhanVien;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addOrEditStaffFragment = FragmentAddStaffBinding.inflate(inflater, container, false);
        return addOrEditStaffFragment.getRoot();
    }
}