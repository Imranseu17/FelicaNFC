package com.example.root.officeapp.nfcfelica;

import android.content.Context;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.root.officeapp.R;
import com.example.root.officeapp.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

public final class POS2Printer implements ReceiveListener {
    public BigDecimal AccountCredit;
    public String CardNo;
    public BigDecimal CurBalance;
    public String Customer;
    public String CustomerCode;
    public String Date;
    public BigDecimal DepositBalance;
    public String Device;
    public BigDecimal GasChrage;
    public BigDecimal GasVolume;
    public BigDecimal ItemAmount;
    public String ItemName;
    public String MeterNo;
    public BigDecimal MeterRent;
    public PrintMode Mode;
    public String Operator;
    public BigDecimal OtherCharges;
    public String PrepaidNo;
    public BigDecimal PrevBalance;
    public String RecipeID;
    public BigDecimal RefundPrice;
    public String Support;
    public String Title;
    HttpResponsAsync WebApi;
    private Context context;
    private Printer mPrinter = null;
    public String strAccountCredit;
    public String strCurBalance;
    public String strDepositBalance;
    public String strGasChrage;
    public String strGasVolume;
    public String strItemAmount;
    public String strMeterRent;
    public String strOtherCharges;
    public String strPrevBalance;
    public String strRefundPrice;

    public enum PrintMode {
        AddCharge,
        AddChargeCredit,
        AddGas,
        RefundGas
    }

    public POS2Printer() {
        String str = "";
        this.Support = str;
        this.ItemName = str;
        this.Operator = str;
        this.Device = str;
        this.CardNo = str;
        this.MeterNo = str;
        this.PrepaidNo = str;
        this.CustomerCode = str;
        this.Customer = str;
        this.RecipeID = str;
        this.Date = str;
        this.Title = str;
        BigDecimal bigDecimal = new BigDecimal(0);
        this.RefundPrice = bigDecimal;
        this.GasVolume = bigDecimal;
        this.GasChrage = bigDecimal;
        this.OtherCharges = bigDecimal;
        this.MeterRent = bigDecimal;
        this.AccountCredit = bigDecimal;
        this.ItemAmount = bigDecimal;
        this.CurBalance = bigDecimal;
        this.PrevBalance = bigDecimal;
        this.DepositBalance = bigDecimal;
    }

    public void setWebApi(HttpResponsAsync WebApi) {
        this.WebApi = WebApi;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private static void SetEnLocale() {
        Locale.setDefault(Locale.ENGLISH);
    }

    private static void ReturnLocale() {
        Locale.setDefault(Common.getLocal(SettingData.SettingLang));
    }

    public void setPrintStr() {
        try {
            SetEnLocale();
            if (this.Device == null || this.Device.equals("")) {
                this.Device = SettingData.WebApi.getPosKey();
            }
            if (this.Date == null || this.Date.equals("")) {
                this.Date = Common.getDateReceipt();
            }
            if (this.Customer == null || this.Customer.equals("")) {
                this.Customer = Common.getCustomerName(this.WebApi);
            }
            if (this.CustomerCode == null || this.CustomerCode.equals("")) {
                this.CustomerCode = Common.getCustomerCode(this.WebApi);
            }
            if (this.PrepaidNo == null || this.PrepaidNo.equals("")) {
                this.PrepaidNo = Common.getCustomerPrepaidNo(this.WebApi);
            }
            if (this.MeterNo == null || this.MeterNo.equals("")) {
                this.MeterNo = Common.getCustomerMeterNo(this.WebApi);
            }
            if (this.CardNo == null || this.CardNo.equals("")) {
                this.CardNo = Common.getCustomerCardIdm(this.WebApi);
            }
            if (this.Operator == null || this.Operator.equals("")) {
                this.Operator = SettingData.PrinterData.getOperator();
            }
            if (this.Support == null || this.Support.equals("")) {
                this.Support = Common.getCustomerSupport();
            }
            DecimalFormat df = Common.getDecimalFormat();
            this.strItemAmount = df.format(this.ItemAmount);
            this.strAccountCredit = df.format(this.AccountCredit);
            this.strMeterRent = df.format(this.MeterRent);
            this.strGasChrage = df.format(this.GasChrage);
            this.strOtherCharges = df.format(this.OtherCharges);
            this.strGasVolume = df.format(this.GasVolume);
            this.strRefundPrice = df.format(this.RefundPrice);
            this.strDepositBalance = df.format(this.DepositBalance);
            this.strPrevBalance = df.format(this.PrevBalance);
            this.strCurBalance = df.format(this.CurBalance);
        } finally {
            ReturnLocale();
        }
    }

    public boolean runPrintReceiptSequence() {
        try {
            SetEnLocale();
            if (!initializeObject()) {
                ReturnLocale();
                return false;
            } else if (!createReceiptData()) {
                finalizeObject();
                ReturnLocale();
                return false;
            } else if (printData()) {
                while (this.mPrinter != null) {
                    Thread.sleep(500);
                }
                ReturnLocale();
                return true;
            } else {
                finalizeObject();
                ReturnLocale();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            finalizeObject();
            return false;
        } catch (Throwable th) {
            ReturnLocale();
            return false;
        }
    }

    private boolean initializeObject() {
        try {
            if (this.Device == null || this.Device.equals("")) {
                this.Device = SettingData.WebApi.getPosKey();
            }
            if (this.Date == null || this.Date.equals("")) {
                this.Date = Common.getDateReceipt();
            }
            if (this.Customer == null || this.Customer.equals("")) {
                this.Customer = Common.getCustomerName(this.WebApi);
            }
            if (this.CustomerCode == null || this.CustomerCode.equals("")) {
                this.CustomerCode = Common.getCustomerCode(this.WebApi);
            }
            if (this.PrepaidNo == null || this.PrepaidNo.equals("")) {
                this.PrepaidNo = Common.getCustomerPrepaidNo(this.WebApi);
            }
            if (this.MeterNo == null || this.MeterNo.equals("")) {
                this.MeterNo = Common.getCustomerMeterNo(this.WebApi);
            }
            if (this.CardNo == null || this.CardNo.equals("")) {
                this.CardNo = Common.getCustomerCardIdm(this.WebApi);
            }
            if (this.Operator == null || this.Operator.equals("")) {
                this.Operator = SettingData.PrinterData.getOperator();
            }
            if (this.Support == null || this.Support.equals("")) {
                this.Support = Common.getCustomerSupport();
            }
            DecimalFormat df = Common.getDecimalFormat();
            this.strItemAmount = df.format(this.ItemAmount);
            this.strAccountCredit = df.format(this.AccountCredit);
            this.strMeterRent = df.format(this.MeterRent);
            this.strGasChrage = df.format(this.GasChrage);
            this.strOtherCharges = df.format(this.OtherCharges);
            this.strGasVolume = df.format(this.GasVolume);
            this.strRefundPrice = df.format(this.RefundPrice);
            this.strDepositBalance = df.format(this.DepositBalance);
            this.strPrevBalance = df.format(this.PrevBalance);
            this.strCurBalance = df.format(this.CurBalance);
            int lang = 0;
            String str = SettingData.SettingLang;
            boolean z = true;
            int hashCode = str.hashCode();
            if (hashCode != -688086063) {
                if (hashCode == 60895824 && str.equals(Common.C_SPRE_LANGUAGE_DEFAULT)) {
                    z = true;
                }
            } else if (str.equals(Common.C_SPRE_LANGUAGE_JAP)) {
                z = false;
            }
            if (!z) {
                lang = 1;
            }
            this.mPrinter = new Printer(2, lang, this.context);
            this.mPrinter.setReceiveEventListener(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void finalizeObject() {
        if (this.mPrinter != null) {
            this.mPrinter.clearCommandBuffer();
            this.mPrinter.setReceiveEventListener(null);
            this.mPrinter = null;
        }
    }

    private boolean connectPrinter() {
        if (this.mPrinter == null) {
            return false;
        }
        boolean isBeginTransaction = false;
        try {
            Printer printer = this.mPrinter;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BT:");
            stringBuilder.append(SettingData.PrinterData.getDeviceTarget());
            printer.connect(stringBuilder.toString(), -2);
            try {
                this.mPrinter.beginTransaction();
                isBeginTransaction = true;
            } catch (Exception e) {
            }
            if (!isBeginTransaction) {
                try {
                    this.mPrinter.disconnect();
                } catch (Epos2Exception e2) {
                    return false;
                }
            }
            return true;
        } catch (Exception e3) {
            return false;
        }
    }

    private void disconnectPrinter() {
        if (this.mPrinter != null) {
            try {
                this.mPrinter.endTransaction();
            } catch (Exception e) {
            }
            try {
                this.mPrinter.disconnect();
            } catch (Exception e2) {
            }
            finalizeObject();
        }
    }

    private void setPrintData(StringBuilder textData, String Title, String Text) {
        textData.append(Title);
        int num = 40 - (Title.length() + Text.length());
        for (int i = 0; i < num; i++) {
            textData.append(StringUtils.SPACE);
        }
        textData.append(Text);
        textData.append("\n");
    }

    private boolean createReceiptData() {
        StringBuilder textData = new StringBuilder();
        if (this.mPrinter == null) {
            return false;
        }
        try {
            String str = SettingData.SettingLang;
            boolean z = true;
            int hashCode = str.hashCode();
            if (hashCode != -688086063) {
                if (hashCode == 60895824 && str.equals(Common.C_SPRE_LANGUAGE_DEFAULT)) {
                    z = true;
                }
            } else if (str.equals(Common.C_SPRE_LANGUAGE_JAP)) {
                z = false;
            }
            if (z) {
                this.mPrinter.addTextLang(-2);
            } else {
                this.mPrinter.addTextLang(1);
            }
            this.mPrinter.addTextAlign(1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.Title);
            stringBuilder.append("\n");
            textData.append(stringBuilder.toString());
            textData.append("----------------------------------------\n");
            this.mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Date), this.Date);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_ReceiptID), this.RecipeID);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Customer), this.Customer);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_CustomerCode), this.CustomerCode);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_AccountID), this.PrepaidNo);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Meter), this.MeterNo);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Card), this.CardNo);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Device), this.Device);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_Operator), this.Operator);
            textData.append("----------------------------------------\n");
            this.mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_DepositAmount), this.strDepositBalance);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PreviousBalance), this.strPrevBalance);
            setPrintData(textData, Common.getResources().getString(R.string.receipt_print_CurrentBalance), this.strCurBalance);
            textData.append("----------------------------------------\n");
            this.mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            switch (this.Mode) {
                case AddCharge:
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.ItemName);
                    stringBuilder.append(Common.getResources().getString(R.string.receipt_print_PrintTK));
                    setPrintData(textData, stringBuilder.toString(), this.strItemAmount);
                    break;
                case AddChargeCredit:
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintCredit), this.strAccountCredit);
                    break;
                case AddGas:
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintMeterRent), this.strMeterRent);
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintOtherCharges), this.strOtherCharges);
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintGasChrage), this.strGasChrage);
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintGasVolume), this.strGasVolume);
                    break;
                case RefundGas:
                    setPrintData(textData, Common.getResources().getString(R.string.receipt_print_PrintGasVolume), this.strGasVolume);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(Common.getResources().getString(R.string.receipt_print_PrintPrice));
                    stringBuilder.append(Common.getResources().getString(R.string.receipt_print_PrintTK));
                    setPrintData(textData, stringBuilder.toString(), this.strRefundPrice);
                    break;
            }
            this.mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            textData.append("----------------------------------------\n");
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.Support);
            stringBuilder.append("\n");
            textData.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(SettingData.CompanyName.getCompanyName());
            stringBuilder.append("\n");
            textData.append(stringBuilder.toString());
            this.mPrinter.addText(textData.toString());
            this.mPrinter.addFeedLine(1);
            this.mPrinter.addCut(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean printData() {
        if (this.mPrinter == null) {
            return false;
        }
        if (!isPrintable(this.mPrinter.getStatus()) && !connectPrinter()) {
            return false;
        }
        if (isPrintable(this.mPrinter.getStatus())) {
            try {
                this.mPrinter.sendData(-2);
                return true;
            } catch (Exception e) {
                try {
                    this.mPrinter.disconnect();
                } catch (Exception e2) {
                }
                return false;
            }
        }
        try {
            this.mPrinter.disconnect();
        } catch (Exception e3) {
        }
        return false;
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null || status.getConnection() == 0 || status.getOnline() == 0) {
            return false;
        }
        return true;
    }

    public void onPtrReceive(Printer printerObj, int code, PrinterStatusInfo status, String printJobId) {
        new Thread(new Runnable() {
            public void run() {
                POS2Printer.this.disconnectPrinter();
            }
        }).start();
    }
}
