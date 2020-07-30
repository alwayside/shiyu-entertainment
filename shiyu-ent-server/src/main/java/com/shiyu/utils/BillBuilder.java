package com.shiyu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BillBuilder {

    public static Long purchaseBillCreate() {
        Long purchaseBill = billCreate();
        return purchaseBill;
    }

    public static Long sellBillCreate() {
        Long purchaseBill = billCreate();
        return purchaseBill;
    }

    private static Long billCreate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = new Date();
        String billDate = format.format(today);
        while (billDate.length() < 16) {
            billDate = billDate + "0";
        }
       return  Long.parseLong(billDate);
    }
}
