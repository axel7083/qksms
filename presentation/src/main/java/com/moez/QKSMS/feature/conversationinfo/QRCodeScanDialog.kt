package com.moez.QKSMS.feature.conversationinfo

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.moez.QKSMS.R
import com.moez.QKSMS.common.models.QRCodeContent

class QRCodeScanDialog(val activity: Activity?, val listener: (String) -> Unit) : Dialog(activity!!), View.OnClickListener {

    private val TAG = "QRCodeScanDialog"
    private lateinit var btn_cancel: TextView

    private lateinit var  mCodeScanner: CodeScanner
    private lateinit var  scannerView: CodeScannerView
    private lateinit var content: QRCodeContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.scan_qr_code_dialog)
        setupViews()

        checkCameraPermission()

        mCodeScanner.decodeCallback = DecodeCallback { result ->
            val message = result.text
            content = Gson().fromJson(message, QRCodeContent::class.java)
            Log.d(TAG, "Content scanned: ${content.publicKey}")

            //Update the public key
            activity!!.runOnUiThread {
                listener(content.publicKey)
                dismiss()
            }

           // Toast.makeText(activity!!.applicationContext,"Public key saved for ${content.mobileNumber}", Toast.LENGTH_LONG).show()
           // dismiss()
        }
    }

    private fun setupViews() {
        btn_cancel = findViewById(R.id.dialog_btn_cancel)
        scannerView = findViewById(R.id.scannerView)

        mCodeScanner = CodeScanner(context, scannerView)
        btn_cancel.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.dialog_btn_cancel) {
            dismiss()
        }
    }

    private fun checkCameraPermission() {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        mCodeScanner!!.startPreview()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {}
                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest,
                            token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                })
                .check()
    }
}