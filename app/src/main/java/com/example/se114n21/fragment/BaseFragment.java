package com.example.se114n21.fragment;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.se114n21.R;

public abstract class BaseFragment extends Fragment {
    protected MaterialDialog progressDialog;

    @Override
    public void onResume() {
        super.onResume();
        createProgressDialog();
    }

    public void createProgressDialog() {
        progressDialog = new MaterialDialog.Builder(getContext())
                .content(R.string.waiting_message)
                .progress(true, 0)
                .build();
    }

    public void showProgressDialog(boolean value) {
        if (value) {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
                progressDialog.setCancelable(false);
            }
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected void showMessage(String message){
        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
