package com.example.root.officeapp.nfcfelica;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.nfc.tech.NfcF;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.epson.eposprint.Print;
import com.example.root.officeapp.R;
import com.example.root.officeapp.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Common {
    public static final String C_PRICE_SETTING_MODE = "PriceSettingMode";
    public static final String C_READ_CARD_MODE = "ReadCardMode";
    public static final String C_SMTP_AUTH_METHOD_AUTH = "AUTH";
    public static final String C_SMTP_AUTH_METHOD_NONE = "NONE";
    public static final String C_SMTP_PROTECTION_LEVEL_NONE = "NONE";
    public static final String C_SMTP_PROTECTION_LEVEL_SSL = "SSL";
    public static final String C_SMTP_PROTECTION_LEVEL_TLS = "TLS";
    public static final String C_SPRE_LANGUAGE = "LANGUAGE";
    public static final String C_SPRE_LANGUAGE_BEN = "Bengali";
    public static final String C_SPRE_LANGUAGE_DEFAULT = "English";
    public static final String C_SPRE_LANGUAGE_JAP = "Japanese";
    public static final String C_SPRE_LOG_OUTPUT = "LOG_OUTPUT";
    public static final String C_SPRE_PRINTER_CUSTOMER = "PRINTER_CUSTOMER";
    public static final String C_SPRE_PRINTER_DEVICEID = "PRINTER_DEVICEID";
    public static final String C_SPRE_PRINTER_NAME = "PRINTER_NAME";
    public static final String C_SPRE_PRINTER_OPERATOR = "PRINTER_OPERATOR";
    public static final String C_SPRE_SMTP_AUTH = "SMTP_AUTH";
    public static final String C_SPRE_SMTP_HOST = "SMTP_HOST";
    public static final String C_SPRE_SMTP_MAIL = "SMTP_MAIL";
    public static final String C_SPRE_SMTP_MAILADDCHARGE = "SMTP_MAILADDCHARGE";
    public static final String C_SPRE_SMTP_MAILADDGAS = "SMTP_MAILADDGAS";
    public static final String C_SPRE_SMTP_MAILCARDACTIVATE = "SMTP_MAILCARDACTIVATE";
    public static final String C_SPRE_SMTP_MAILCARDADD = "SMTP_MAILCARDADD";
    public static final String C_SPRE_SMTP_MAILCARDLOST = "SMTP_MAILCARDLOST";
    public static final String C_SPRE_SMTP_MAILCARDRETURN = "SMTP_MAILCARDRETURN";
    public static final String C_SPRE_SMTP_MAILREFUNDGAS = "SMTP_MAILREFUNDGAS";
    public static final String C_SPRE_SMTP_PASSWORD = "SMTP_PASSWORD";
    public static final String C_SPRE_SMTP_PORT = "SMTP_PORT";
    public static final String C_SPRE_SMTP_SSLTLS = "SMTP_SSLTLS";
    public static final String C_SPRE_SMTP_TIMEOUT = "SMTP_TIMEOUT";
    public static final String C_SPRE_SMTP_USERNAME = "SMTP_USERNAME";
    public static final String C_SPRE_SMTP_USESMS = "SMTP_USESMS";
    public static final String C_SPRE_SW_WORD_COUNT_OUTPUT = "SW_WORD_COUNT";
    public static final String C_SPRE_TXT_COMMISSION_RATE = "TXT_COMMISSION_RATE";
    public static final String C_SPRE_TXT_COMPANY_NAME = "TXT_COMPANY_NAME";
    public static final String C_SPRE_TXT_WORD_COUNT_OUTPUT = "TXT_WORD_COUNT";
    public static final String C_SPRE_WEBAPI_APIKEY = "WEBAPI_APIKEY";
    public static final String C_SPRE_WEBAPI_POSKEY = "WEBAPI_POSKEY";
    public static final String C_SPRE_WEBAPI_URL = "WEBAPI_URL";
    public static final String C_SPRE_WEBAPI_URL2 = "WEBAPI_URL2";
    public static final String C_SPRE_WEBAPI_URL3 = "WEBAPI_URL3";
    private static String lastSearchAccountNumber = null;
    private static Resources resources = null;
    private static int selectCustomerDetailesPage = 0;
    private static String webApiPassWord = null;
    private static String webApiUserName = null;

    public enum PriceSettingMode {
        Add_Price,
        Update_Price
    }

    public enum ReadCardMode {
        Login_Read,
        Menu_Read
    }

    static abstract class RunnableAlertDialog implements Runnable {
        public AlertDialog alertDialog;
        public boolean create = false;

        RunnableAlertDialog() {
        }
    }

    static abstract class RunnableAlertDialogClose implements Runnable {
        public boolean close = false;

        RunnableAlertDialogClose() {
        }
    }

    public static ArrayList<String> getWebApiUrlList() {
        ArrayList<String> result = new ArrayList();
        String temp = SettingData.WebApi.getWebApiUrl();
        if (!temp.equals("")) {
            result.add(temp);
        }
        temp = SettingData.WebApi.getWebApiUrl2();
        if (!temp.equals("")) {
            result.add(temp);
        }
        temp = SettingData.WebApi.getWebApiUrl3();
        if (!temp.equals("")) {
            result.add(temp);
        }
        return result;
    }

    public static void setWebApiPassWord(String webApiPassWord) {
        webApiPassWord = webApiPassWord;
    }

    public static void setWebApiUserName(String webApiUserName) {
        webApiUserName = webApiUserName;
    }

    public static String getWebApiPassWord() {
        return webApiPassWord;
    }

    public static String getWebApiUserName() {
        return webApiUserName;
    }

    public static void setLastSearchAccountNumber(String lastSearchAccountNumber) {
        lastSearchAccountNumber = lastSearchAccountNumber;
    }

    public static int getSelectCustomerDetailesPage() {
        return selectCustomerDetailesPage;
    }

    public static void setSelectCustomerDetailesPage(int selectCustomerDetailesPage) {
        selectCustomerDetailesPage = selectCustomerDetailesPage;
    }

    public static DecimalFormat getDecimalFormat() {
        return new DecimalFormat("#,##0.00");
    }

    public static DecimalFormat getDecimalFormatEditText() {
        return new DecimalFormat("0.00");
    }

    private static String getNumString(String txt) {
        return txt.replaceAll(",", "");
    }

    public static double parseDouble(String txt) throws ParseException {
        return NumberFormat.getNumberInstance().parse(getNumString(txt)).doubleValue();
    }

    public static BigDecimal parseDecimalJSON(String txt) {
        try {
            return new BigDecimal(NumberFormat.getNumberInstance().parse(getNumString(txt)).doubleValue()).setScale(3, 4);
        } catch (ParseException e) {
            return new BigDecimal(0);
        }
    }

    public static int parseInt(String txt) throws ParseException {
        return NumberFormat.getNumberInstance().parse(getNumString(txt)).intValue();
    }

    public static BigDecimal parseDecimal(String txt) {
        try {
            return new BigDecimal(NumberFormat.getNumberInstance().parse(getNumString(txt)).doubleValue());
        } catch (ParseException e) {
            return new BigDecimal(0);
        }
    }

    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
//            LogUtil.e(e);
            return versionCode;
        }
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
//            LogUtil.e(e);
            return versionName;
        }
    }

    public static void setResources(Resources resources) {
        resources = resources;
    }

    public static Resources getResources() {
        return resources;
    }

    public static boolean isNumInt(String number, int[] Num) {
        try {
            Num[0] = 0;
            Num[0] = parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (ParseException e2) {
            return false;
        }
    }

    public static boolean isNumDouble(String number, double[] Num) {
        try {
            Num[0] = 0.0d;
            Num[0] = parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        } catch (ParseException e2) {
            return false;
        }
    }

    public static View isRequiredEditText(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            return view;
        }
        return null;
    }

    public static View isRequiredSpinner(Spinner view) {
        if (resources.getString(R.string.spinner_noselected) == view.getSelectedItem().toString()) {
            return view;
        }
        return null;
    }

    public static void showRequiredAllFieldAlert(final FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.required_alert_title), Common.resources.getString(R.string.required_alert_allfield), Common.resources.getString(R.string.required_alert_button));
            }
        });
    }

    public static void showRequiredNameAlert(final FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.required_alert_title), Common.resources.getString(R.string.required_alert_name), Common.resources.getString(R.string.required_alert_button));
            }
        });
    }

    public static void showValidPriceAlert(final FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.required_alert_title), Common.resources.getString(R.string.required_alert_price2), Common.resources.getString(R.string.required_alert_button));
            }
        });
    }

    public static void showRequiredPriceAlert(final FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.required_alert_title), Common.resources.getString(R.string.required_alert_price), Common.resources.getString(R.string.required_alert_button));
            }
        });
    }

    public static AlertDialog showReacCardContinueAlert(final FragmentActivity context) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                if (this.alertDialog == null) {
                    this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.CardRead_Alert_Title)).setMessage(Common.resources.getString(R.string.CardRead_Alert_Msg)).setCancelable(false).show();
                }
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static void showWebApiFailedAlert(final FragmentActivity context) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.processing_failed_alert_title), Common.resources.getString(R.string.processing_failed_alert_Msg), Common.resources.getString(R.string.processing_failed_alert_Button));
            }
        });
    }

    public static void showWebApiFailedAlertFinish(final FragmentActivity context, final OnClickListener listener) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.processing_failed_alert_title), Common.resources.getString(R.string.processing_failed_alert_Msg), Common.resources.getString(R.string.processing_failed_alert_Button), listener);
            }
        });
    }

    public static void showReadCardFailedAlertFinish(final FragmentActivity context, final OnClickListener listener) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.readcard_failed_alert_title), Common.resources.getString(R.string.readcard_failed_alert_Msg), Common.resources.getString(R.string.readcard_failed_alert_Button), listener);
            }
        });
    }

    public static void showPrintAlert(final FragmentActivity context, final OnClickListener retry, final OnClickListener close) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.receipt_alert_title), Common.resources.getString(R.string.receipt_alert_msg), Common.resources.getString(R.string.receipt_alert_button1), Common.resources.getString(R.string.receipt_alert_button2), retry, close);
            }
        });
    }

    public static AlertDialog showReprintAlert(final FragmentActivity context, final OnClickListener onYes, final OnClickListener onNo) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                if (this.alertDialog == null) {
                    this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.receipt_RePrint_ConfirmTitle)).setMessage(Common.resources.getString(R.string.receipt_RePrint_ConfirmMsg)).setPositiveButton((CharSequence) "Yes", onYes).setNegativeButton((CharSequence) "No", onNo).setCancelable(false).show();
                }
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static void showSendMailFailedAlert(final FragmentActivity context, final OnClickListener retry, final OnClickListener close) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                Common.showAlertDialog(context, Common.resources.getString(R.string.sendmail_alert_title), Common.resources.getString(R.string.sendmail_alert_msg), Common.resources.getString(R.string.sendmail_alert_button1), Common.resources.getString(R.string.sendmail_alert_button2), retry, close);
            }
        });
    }

    public static void showAlertDialog(final FragmentActivity context, final String title, final String message, final String button) {
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                new Builder(context).setTitle(title).setMessage(message).setPositiveButton(button, null).setCancelable(false).show();
            }
        });
    }

    public static void showAlertDialog(FragmentActivity context, String title, String message, String button, OnClickListener listener) {
        final FragmentActivity fragmentActivity = context;
        final String str = title;
        final String str2 = message;
        final String str3 = button;
        final OnClickListener onClickListener = listener;
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                new Builder(fragmentActivity).setTitle(str).setMessage(str2).setPositiveButton(str3, onClickListener).setCancelable(false).show();
            }
        });
    }

    public static void showAlertDialog(FragmentActivity context, String title, String message, String button1, String button2) {
        final FragmentActivity fragmentActivity = context;
        final String str = title;
        final String str2 = message;
        final String str3 = button1;
        final String str4 = button2;
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                new Builder(fragmentActivity).setTitle(str).setMessage(str2).setPositiveButton(str3, null).setNegativeButton(str4, null).setCancelable(false).show();
            }
        });
    }

    public static AlertDialog showAlertDialogWaitProcessing(final FragmentActivity context) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                if (this.alertDialog == null) {
                    this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.processing_alert_title)).setMessage(Common.resources.getString(R.string.processing_alert_Msg)).setCancelable(false).show();
                }
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static AlertDialog showAlertDialogWaitPrintProcessing(final FragmentActivity context) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.processing_alert_title)).setMessage(Common.resources.getString(R.string.processing_alert_MsgPrint)).setCancelable(false).show();
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static AlertDialog showAlertDialogWaitCardReading(final FragmentActivity context) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                if (this.alertDialog == null) {
                    this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.cardreading_alert_title)).setMessage(Common.resources.getString(R.string.cardreading_alert_Msg)).setCancelable(false).show();
                }
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static void showAlertDialog(FragmentActivity context, String title, String message, String button1, String button2, OnClickListener listener1, OnClickListener listener2) {
        final FragmentActivity fragmentActivity = context;
        final String str = title;
        final String str2 = message;
        final String str3 = button1;
        final OnClickListener onClickListener = listener1;
        final String str4 = button2;
        final OnClickListener onClickListener2 = listener2;
        context.runOnUiThread(new Runnable() {
            public synchronized void run() {
                new Builder(fragmentActivity).setTitle(str).setMessage(str2).setPositiveButton(str3, onClickListener).setNegativeButton(str4, onClickListener2).setCancelable(false).show();
            }
        });
    }

    public static AlertDialog showAlertDialogWaitNFC(final FragmentActivity context, final OnClickListener listener) {
        RunnableAlertDialog result = new RunnableAlertDialog() {
            public synchronized void run() {
                if (this.alertDialog == null) {
                    this.alertDialog = new Builder(context).setTitle(Common.resources.getString(R.string.CardWait_Alert_Title)).setMessage(Common.resources.getString(R.string.CardWait_Alert_Msg)).setPositiveButton(Common.resources.getString(R.string.CardWait_Alert_Button), listener).setCancelable(false).show();
                }
                this.create = true;
            }
        };
        context.runOnUiThread(result);
        while (!result.create) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return result.alertDialog;
    }

    public static void CloseAlertDialog(Runnable context, final AlertDialog dialog) {
        RunnableAlertDialogClose runnableAlertDialog = new RunnableAlertDialogClose() {
            public synchronized void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                this.close = true;
            }
        };
//        context.runOnUiThread(runnableAlertDialog);
        while (!runnableAlertDialog.close) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static Locale getLocal(String lang) {
        Integer obj = null;
        int hashCode = lang.hashCode();
        if (hashCode == -688086063) {
            if (lang.equals(C_SPRE_LANGUAGE_JAP)) {
//                switch (obj) {
//                    case null:
//                        break;
//                    case 1:
//                        break;
//                    default:
//                        break;
//                }
            }
        } else if (hashCode == 60895824) {
            if (lang.equals(C_SPRE_LANGUAGE_DEFAULT)) {
                obj = 2;
//                switch (obj) {
//                    case null:
//                        break;
//                    case 1:
//                        break;
//                    default:
//                        break;
//                }
            }
        } else if (hashCode == 1441997506 && lang.equals(C_SPRE_LANGUAGE_BEN)) {
            obj = 1;
            switch (obj) {
//                case null:
//                    return Locale.JAPAN;
                case 1:
                    return new Locale("bn", "BD");
                default:
                    return Locale.ENGLISH;
            }
        }
        obj = -1;
        switch (obj) {
//            case null:
//                break;
            case 1:
                break;
            default:
                break;
        }
        return null;
    }

    public static String getCustomerPrepaidNo(HttpResponsAsync WebApi) {
        return WebApi.getGetDetailedCustomerInformationResult().accountNumber;
    }

    public static String getCustomerName(HttpResponsAsync WebApi) {
        String Result = "";
        if (WebApi.getGetDetailedCustomerInformationResult().contactInformations.size() <= 0) {
            return Result;
        }
        HttpResponsAsync.getDetailedCustomerInformationResultContactInformation contactInformation = (HttpResponsAsync.getDetailedCustomerInformationResultContactInformation) WebApi.getGetDetailedCustomerInformationResult().contactInformations.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contactInformation.firstName);
        stringBuilder.append(contactInformation.lastName);
        return stringBuilder.toString();
    }

    public static String getCustomerCode(HttpResponsAsync WebApi) {
        return WebApi.getGetDetailedCustomerInformationResult().buetCode;
    }

    public static String getCustomerCardIdm(HttpResponsAsync WebApi) {
        String Result = "";
        ArrayList<HttpResponsAsync.getDetailedCustomerInformationResultCardInformation> cardInformations = WebApi.getGetDetailedCustomerInformationResult().cardInformations;
        for (int i = 0; i < cardInformations.size(); i++) {
            HttpResponsAsync.getDetailedCustomerInformationResultCardInformation cardInformation = (HttpResponsAsync.getDetailedCustomerInformationResultCardInformation) cardInformations.get(i);
            if (Boolean.valueOf(cardInformation.status).booleanValue()) {
                return cardInformation.cardIdm;
            }
        }
        return Result;
    }

    public static String getCustomerMeterNo(HttpResponsAsync WebApi) {
        String Result = "";
        ArrayList<HttpResponsAsync.getDetailedCustomerInformationResultMeterInformation> meterInformations = WebApi.getGetDetailedCustomerInformationResult().meterInformations;
        for (int i = 0; i < meterInformations.size(); i++) {
            HttpResponsAsync.getDetailedCustomerInformationResultMeterInformation cardInformation = (HttpResponsAsync.getDetailedCustomerInformationResultMeterInformation) meterInformations.get(i);
            if (cardInformation.status.equals("meter.status.active")) {
                return cardInformation.meterSerialNumber;
            }
        }
        return Result;
    }

    public static String getCustomerSupport() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(resources.getString(R.string.receipt_print_Support));
        stringBuilder.append(" <");
        stringBuilder.append(SettingData.PrinterData.getCustomer());
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public static String getSendMailAddress(HttpResponsAsync WebApi) {
        String Result = "";
        try {
            if (WebApi.getGetDetailedCustomerInformationResult().contactInformations.size() == 0) {
                return Result;
            }
            if (SettingData.SmtpSetting.getUseSMS()) {
                return ((HttpResponsAsync.getDetailedCustomerInformationResultContactInformation) WebApi.getGetDetailedCustomerInformationResult().contactInformations.get(0)).phoneNumber;
            }
            return ((HttpResponsAsync.getDetailedCustomerInformationResultContactInformation) WebApi.getGetDetailedCustomerInformationResult().contactInformations.get(0)).email;
        } catch (Exception e) {
            return Result;
        }
    }

    public static AndroidSendmail getSettingDataSendMail() {
        if (SettingData.SmtpSetting == null) {
            return null;
        }
        boolean auth = C_SMTP_AUTH_METHOD_AUTH.equals(SettingData.SmtpSetting.getAuthMethod());
        boolean ssl = C_SMTP_PROTECTION_LEVEL_SSL.equals(SettingData.SmtpSetting.getProtectionLevel());
        boolean tls = C_SMTP_PROTECTION_LEVEL_TLS.equals(SettingData.SmtpSetting.getProtectionLevel());
        int[] timeout = new int[1];
        isNumInt(SettingData.SmtpSetting.getTimeout(), timeout);
        return new AndroidSendmail(SettingData.SmtpSetting.getHostName(), SettingData.SmtpSetting.getPort(), SettingData.SmtpSetting.getUsername(), SettingData.SmtpSetting.getPassword(), resources.getString(R.string.app_name), auth, ssl, tls, timeout[0]);
    }

    public static String GetSendMailBodyRecipt(POS2Printer printer, String Title, String Text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Title);
        stringBuilder.append("\r\n\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Date));
        stringBuilder.append(printer.Date);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Receipt));
        stringBuilder.append(printer.RecipeID);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Customer));
        stringBuilder.append(printer.Customer);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_CustomerCode));
        stringBuilder.append(printer.CustomerCode);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_ID));
        stringBuilder.append(printer.PrepaidNo);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Meter));
        stringBuilder.append(printer.MeterNo);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Card));
        stringBuilder.append(printer.CardNo);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Device));
        stringBuilder.append(printer.Device);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Operator));
        stringBuilder.append(printer.Operator);
        stringBuilder.append("\r\n\r\n");
        stringBuilder.append(Text);
        stringBuilder.append("\r\n\r\n");
        stringBuilder.append(printer.Support);
        stringBuilder.append("\r\n");
        stringBuilder.append(SettingData.CompanyName.getCompanyName());
        return stringBuilder.toString();
    }

    public static String GetSendMailBodyReciptNew(POS2Printer printer, String Title, String Text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Title);
        stringBuilder.append("\r\n");
        stringBuilder.append(Text);
        return stringBuilder.toString();
    }

    public static String GetSendMailBody(HttpResponsAsync WebApi, String Text) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Text);
        stringBuilder.append("\r\n\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_ID));
        stringBuilder.append(WebApi.getGetDetailedCustomerInformationResult().accountId);
        stringBuilder.append("\r\n");
        stringBuilder.append(resources.getString(R.string.sendmail_header_Device));
        stringBuilder.append(SettingData.WebApi.getPosKey());
        stringBuilder.append("\r\n\r\n");
        stringBuilder.append(SettingData.CompanyName.getCompanyName());
        return stringBuilder.toString();
    }

    public static void SetUpdateDetailedCustomerInformation(HttpResponsAsync WebApi) {
        WebApi.getClass();
        HttpResponsAsync.getDetailedCustomerInformationArgument param = new HttpResponsAsync.getDetailedCustomerInformationArgument();
        param.accountNumber = lastSearchAccountNumber;
        WebApi.RequestWebApi = HttpResponsAsync.WebApiType.getDetailedCustomerInformation;
        WebApi.setGetDetailedCustomerInformationArgument(param);
        WebApi.execute(new Void[0]);
    }

    @SuppressLint("WrongConstant")
    public static PendingIntent GetPendingIntent(Context packageContext, Class<?> cls) {
        return PendingIntent.getActivity(packageContext, 0, new Intent(packageContext, cls).addFlags(Print.ST_MOTOR_OVERHEAT), 0);
    }

    public static IntentFilter[] GetIntentFilterArray() {
        try {
            new IntentFilter("android.nfc.action.NDEF_DISCOVERED").addDataType("text/plain");
            return new IntentFilter[]{new IntentFilter("android.nfc.action.NDEF_DISCOVERED")};
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
    }

    public static String[][] GetTechListsArray() {
        String[][] strArr = new String[1][];
        strArr[0] = new String[]{NfcF.class.getName()};
        return strArr;
    }

    public static String getDateReceipt() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }

    public static String getFormatDate(String strDate) {
        Date date = null;
        try {
            date = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate.replace("0001", "1970").replace("T", StringUtils.SPACE)).getTime());
        } catch (ParseException e) {
        }
        SimpleDateFormat dateformat = new SimpleDateFormat(getLocalDateFormat(resources.getConfiguration().locale), resources.getConfiguration().locale);
        Calendar cal_created = Calendar.getInstance();
        cal_created.setTime(date);
        return dateformat.format(cal_created.getTime()).replace("1970", "0001");
    }

    public static String getFormatDateReports(String strDate) {
        Date date = null;
        try {
            date = new Date(new SimpleDateFormat("yyyyMMddHHmmss").parse(strDate.replace("0001", "1970").replace("T", StringUtils.SPACE)).getTime());
        } catch (ParseException e) {
        }
        SimpleDateFormat dateformat = new SimpleDateFormat(getLocalDateFormat(resources.getConfiguration().locale), resources.getConfiguration().locale);
        Calendar cal_created = Calendar.getInstance();
        cal_created.setTime(date);
        return dateformat.format(cal_created.getTime()).replace("1970", "0001");
    }

    private static String getLocalDateFormat(Locale locale) {
        String result = "";
        String language = locale.getLanguage();
        if (language.hashCode() == 3241 && language.equals("en")) {
        }
        return "MMM d, yyyy HH:mm a";
    }

    public static void checkPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestLocationPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
        }
    }

    public static void checkPermission(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
            requestLocationPermission(activity, permission);
        }
    }

    private static void requestLocationPermission(Activity activity, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 1000);
            return;
        }
        Toast.makeText(activity, "Permission required for application execution", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(activity, new String[]{permission}, 1000);
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0092  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String GetInspectCardErrorMessage(String ErrorCode) {
        Integer obj;
        int hashCode = ErrorCode.hashCode();
        if (hashCode == 1024) {
            if (ErrorCode.equals("  ")) {
                obj = 9;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode == 2156) {
            if (ErrorCode.equals("CO")) {
                obj = 7;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode == 2159) {
            if (ErrorCode.equals("CR")) {
                obj = 8;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode == 2208) {
            if (ErrorCode.equals("EE")) {
                obj = 4;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode == 2237) {
            if (ErrorCode.equals("Eb")) {
                obj = 5;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode == 2529) {
            if (ErrorCode.equals("OP")) {
                obj = 3;
                switch (obj) {
//                    case null:
//                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode != 2639) {
            switch (hashCode) {
                case 1536:
                    if (ErrorCode.equals("00")) {
                        obj = null;
                        break;
                    }
                case 1537:
                    if (ErrorCode.equals("01")) {
                        obj = 1;
                        break;
                    }
                case 1538:
                    if (ErrorCode.equals("02")) {
                        obj = 2;
                        break;
                    }
            }
        } else if (ErrorCode.equals("SB")) {
            obj = 6;
            switch (obj) {
//                case null:
//                    return resources.getString(R.string.InspectCardErrorMessage1);
                case 1:
                    return resources.getString(R.string.InspectCardErrorMessage2);
                case 2:
                    return resources.getString(R.string.InspectCardErrorMessage3);
                case 3:
                    return resources.getString(R.string.InspectCardErrorMessage4);
                case 4:
                    return resources.getString(R.string.InspectCardErrorMessage5);
                case 5:
                    return resources.getString(R.string.InspectCardErrorMessage6);
                case 6:
                    return resources.getString(R.string.InspectCardErrorMessage7);
                case 7:
                    return resources.getString(R.string.InspectCardErrorMessage8);
                case 8:
                    return resources.getString(R.string.InspectCardErrorMessage9);
                case 9:
                    return resources.getString(R.string.InspectCardErrorMessage10);
                default:
                    return "";
            }
        }
        obj = -1;
        switch (obj) {
//            case null:
//                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            default:
                break;
        }
        return ErrorCode;
    }
}