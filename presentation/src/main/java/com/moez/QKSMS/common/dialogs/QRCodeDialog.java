package com.moez.QKSMS.common.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.moez.QKSMS.R;


public class QRCodeDialog extends Dialog implements View.OnClickListener {

    private ImageView dialog_QRCode;
    private TextView btn_cancel;

    private Bitmap QRCode;


    public QRCodeDialog(Activity activity, Bitmap QRCode) {
        super(activity);

        this.QRCode = QRCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qr_code_dialog);

        setupViews();

        dialog_QRCode.setImageBitmap(QRCode);
    }

    private void setupViews()
    {
        dialog_QRCode = findViewById(R.id.dialog_QRCode);
        btn_cancel = findViewById(R.id.dialog_btn_cancel);

        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.dialog_btn_cancel)
        {
            dismiss();
        }
    }
}