package com.moez.QKSMS.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;

public class QRCodeContent {

    public String publicKey;
    public String mobileNumber;

    public QRCodeContent(String publicKey, String mobileNumber) {
        this.publicKey = publicKey;
        this.mobileNumber = mobileNumber;
    }

}
