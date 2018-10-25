package com.example.root.officeapp.nfcfelica;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class SettingData {
    public static CCommission Commission = null;
    public static CCompanyName CompanyName = null;
    public static CLog Log = null;
    public static CPrinterData PrinterData;
    public static String SettingLang = "";
    public static CSmtpSetting SmtpSetting = null;
    public static CWebApi WebApi = null;
    public static CWordCount WordCount = null;
    private static SharedPreferences m_sharedPreferences = null;

    public static class CCommission {
        private String CommissionRate = "0.00";

        public String getCommission() {
            return this.CommissionRate;
        }

        public void setCommission(String commissionRate) {
            this.CommissionRate = commissionRate;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putString(Common.C_SPRE_TXT_COMMISSION_RATE, this.CommissionRate);
            editor.commit();
            return true;
        }
    }

    public static class CCompanyName {
        private String CompanyName = "";
        private String DEFAULT_COMPANY_NAME = "KarnaphuliGasCo.Ltd.";

        public String getCompanyName() {
            if (this.CompanyName.isEmpty()) {
                this.CompanyName = this.DEFAULT_COMPANY_NAME;
            }
            return this.CompanyName;
        }

        public void setCompanyName(String companyName) {
            this.CompanyName = companyName;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putString(Common.C_SPRE_TXT_COMPANY_NAME, this.CompanyName);
            editor.commit();
            return true;
        }
    }

    public static class CLog {
        private boolean outputLog = false;

        public boolean getOutputLog() {
            return this.outputLog;
        }

        public void setOutputLog(boolean outputLog) {
            this.outputLog = outputLog;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putBoolean(Common.C_SPRE_LOG_OUTPUT, this.outputLog);
            LogUtil.setShowLog(this.outputLog);
            editor.commit();
            return true;
        }
    }

    public static class CPrinterData {
        private String customer = "";
        private String deviceName = "";
        private String deviceTarget = "";
        private String operator = "";

        public String getOperator() {
            return this.operator;
        }

        public void setOperator(String value) {
            this.operator = value;
        }

        public String getCustomer() {
            return this.customer;
        }

        public void setCustomer(String value) {
            this.customer = value;
        }

        public String getDeviceTarget() {
            return this.deviceTarget;
        }

        public void setDeviceTarget(String value) {
            this.deviceTarget = value;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String value) {
            this.deviceName = value;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putString(Common.C_SPRE_PRINTER_OPERATOR, this.operator);
            editor.putString(Common.C_SPRE_PRINTER_CUSTOMER, this.customer);
            editor.putString(Common.C_SPRE_PRINTER_NAME, this.deviceName);
            editor.putString(Common.C_SPRE_PRINTER_DEVICEID, this.deviceTarget);
            editor.commit();
            return true;
        }
    }

    public static class CSmtpSetting {
        private String authMethod = "";
        private String hostName = "";
        private boolean mailAddCharge = false;
        private boolean mailAddGas = false;
        private String mailAddress = "";
        private boolean mailCardActivate = false;
        private boolean mailCardAdd = false;
        private boolean mailCardLost = false;
        private boolean mailCardReturn = false;
        private boolean mailRefundGas = false;
        private String password = "";
        private String port = "";
        private String protectionLevel = "";
        private String timeout = "";
        private boolean useSMS = false;
        private String username = "";

        public boolean getUseSMS() {
            return this.useSMS;
        }

        public void setUseSMS(boolean useSMS) {
            this.useSMS = useSMS;
        }

        public boolean getMailAddCharge() {
            return this.mailAddCharge;
        }

        public void setMailAddCharge(boolean mailAddCharge) {
            this.mailAddCharge = mailAddCharge;
        }

        public boolean getMailAddGas() {
            return this.mailAddGas;
        }

        public void setMailAddGas(boolean mailAddGas) {
            this.mailAddGas = mailAddGas;
        }

        public boolean getMailRefundGas() {
            return this.mailRefundGas;
        }

        public void setMailRefundGas(boolean mailRefundGas) {
            this.mailRefundGas = mailRefundGas;
        }

        public boolean getMailCardAdd() {
            return this.mailCardAdd;
        }

        public void setMailCardAdd(boolean mailCardAdd) {
            this.mailCardAdd = mailCardAdd;
        }

        public boolean getMailCardReturn() {
            return this.mailCardReturn;
        }

        public void setMailCardReturn(boolean mailCardReturn) {
            this.mailCardReturn = mailCardReturn;
        }

        public boolean getMailCardActivate() {
            return this.mailCardActivate;
        }

        public void setMailCardActivate(boolean mailCardActivate) {
            this.mailCardActivate = mailCardActivate;
        }

        public boolean getMailCardLost() {
            return this.mailCardLost;
        }

        public void setMailCardLost(boolean mailCardLost) {
            this.mailCardLost = mailCardLost;
        }

        public String getHostName() {
            return this.hostName;
        }

        public void setHostName(String value) {
            this.hostName = value;
        }

        public String getPort() {
            return this.port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getProtectionLevel() {
            return this.protectionLevel;
        }

        public void setProtectionLevel(String protectionLevel) {
            this.protectionLevel = protectionLevel;
        }

        public String getAuthMethod() {
            return this.authMethod;
        }

        public void setAuthMethod(String authMethod) {
            this.authMethod = authMethod;
        }

        public String getMailAddress() {
            return this.mailAddress;
        }

        public void setMailAddress(String mailAddress) {
            this.mailAddress = mailAddress;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTimeout() {
            return this.timeout;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putString(Common.C_SPRE_SMTP_HOST, this.hostName);
            editor.putString(Common.C_SPRE_SMTP_PORT, this.port);
            editor.putString(Common.C_SPRE_SMTP_SSLTLS, this.protectionLevel);
            editor.putString(Common.C_SPRE_SMTP_AUTH, this.authMethod);
            editor.putString(Common.C_SPRE_SMTP_MAIL, this.mailAddress);
            editor.putString(Common.C_SPRE_SMTP_USERNAME, this.username);
            editor.putString(Common.C_SPRE_SMTP_PASSWORD, this.password);
            editor.putString(Common.C_SPRE_SMTP_TIMEOUT, this.timeout);
            editor.putBoolean(Common.C_SPRE_SMTP_USESMS, this.useSMS);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILADDCHARGE, this.mailAddCharge);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILADDGAS, this.mailAddGas);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILREFUNDGAS, this.mailRefundGas);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILCARDADD, this.mailCardAdd);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILCARDRETURN, this.mailCardReturn);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILCARDACTIVATE, this.mailCardActivate);
            editor.putBoolean(Common.C_SPRE_SMTP_MAILCARDLOST, this.mailCardLost);
            editor.commit();
            return true;
        }
    }

    public static class CWebApi {
        private String apiKey = "";
        private String posKey = "";
        private String webApiUrl = "";
        private String webApiUrl2 = "";
        private String webApiUrl3 = "";

        public String getWebApiUrl() {
            return this.webApiUrl;
        }

        public void setWebApiUrl(String webApiUrl) {
            this.webApiUrl = webApiUrl;
        }

        public String getWebApiUrl2() {
            return this.webApiUrl2;
        }

        public void setWebApiUrl2(String webApiUrl) {
            this.webApiUrl2 = webApiUrl;
        }

        public String getWebApiUrl3() {
            return this.webApiUrl3;
        }

        public void setWebApiUrl3(String webApiUrl) {
            this.webApiUrl3 = webApiUrl;
        }

        public String getApiKey() {
            return this.apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getPosKey() {
            return this.posKey;
        }

        public void setPosKey(String posKey) {
            this.posKey = posKey;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putString(Common.C_SPRE_WEBAPI_URL, this.webApiUrl);
            editor.putString(Common.C_SPRE_WEBAPI_URL2, this.webApiUrl2);
            editor.putString(Common.C_SPRE_WEBAPI_URL3, this.webApiUrl3);
            editor.putString(Common.C_SPRE_WEBAPI_APIKEY, this.apiKey);
            editor.putString(Common.C_SPRE_WEBAPI_POSKEY, this.posKey);
            editor.commit();
            return true;
        }
    }

    public static class CWordCount {
        private String CustomText = "";
        private boolean Switch = false;

        public boolean getSwitch() {
            return this.Switch;
        }

        public void setSwitch(boolean checked) {
            this.Switch = checked;
        }

        public String getCustomText() {
            return this.CustomText;
        }

        public void setCustomText(String customText) {
            this.CustomText = customText;
        }

        public boolean SaveData() {
            if (SettingData.m_sharedPreferences == null) {
                return false;
            }
            Editor editor = SettingData.m_sharedPreferences.edit();
            editor.putBoolean(Common.C_SPRE_SW_WORD_COUNT_OUTPUT, this.Switch);
            editor.putString(Common.C_SPRE_TXT_WORD_COUNT_OUTPUT, this.CustomText);
            LogUtil.setShowLog(this.Switch);
            editor.commit();
            return true;
        }
    }

    public static void InitSettingData(SharedPreferences sharedPreferences) {
        if (PrinterData == null) {
            PrinterData = new CPrinterData();
        }
        if (SmtpSetting == null) {
            SmtpSetting = new CSmtpSetting();
        }
        if (WebApi == null) {
            WebApi = new CWebApi();
        }
        if (Log == null) {
            Log = new CLog();
        }
        if (WordCount == null) {
            WordCount = new CWordCount();
        }
        if (CompanyName == null) {
            CompanyName = new CCompanyName();
        }
        if (Commission == null) {
            Commission = new CCommission();
        }
        m_sharedPreferences = sharedPreferences;
        if (m_sharedPreferences != null) {
            PrinterData.setOperator(m_sharedPreferences.getString(Common.C_SPRE_PRINTER_OPERATOR, ""));
            PrinterData.setCustomer(m_sharedPreferences.getString(Common.C_SPRE_PRINTER_CUSTOMER, ""));
            PrinterData.setDeviceName(m_sharedPreferences.getString(Common.C_SPRE_PRINTER_NAME, ""));
            PrinterData.setDeviceTarget(m_sharedPreferences.getString(Common.C_SPRE_PRINTER_DEVICEID, ""));
            WebApi.setWebApiUrl(m_sharedPreferences.getString(Common.C_SPRE_WEBAPI_URL, ""));
            WebApi.setWebApiUrl2(m_sharedPreferences.getString(Common.C_SPRE_WEBAPI_URL2, ""));
            WebApi.setWebApiUrl3(m_sharedPreferences.getString(Common.C_SPRE_WEBAPI_URL3, ""));
            WebApi.setApiKey(m_sharedPreferences.getString(Common.C_SPRE_WEBAPI_APIKEY, ""));
            WebApi.setPosKey(m_sharedPreferences.getString(Common.C_SPRE_WEBAPI_POSKEY, ""));
            SmtpSetting.setHostName(m_sharedPreferences.getString(Common.C_SPRE_SMTP_HOST, ""));
            SmtpSetting.setPort(m_sharedPreferences.getString(Common.C_SPRE_SMTP_PORT, ""));
            SmtpSetting.setProtectionLevel(m_sharedPreferences.getString(Common.C_SPRE_SMTP_SSLTLS, ""));
            SmtpSetting.setAuthMethod(m_sharedPreferences.getString(Common.C_SPRE_SMTP_AUTH, ""));
            SmtpSetting.setUsername(m_sharedPreferences.getString(Common.C_SPRE_SMTP_USERNAME, ""));
            SmtpSetting.setPassword(m_sharedPreferences.getString(Common.C_SPRE_SMTP_PASSWORD, ""));
            SmtpSetting.setTimeout(m_sharedPreferences.getString(Common.C_SPRE_SMTP_TIMEOUT, ""));
            SmtpSetting.setUseSMS(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_USESMS, false));
            SmtpSetting.setMailAddCharge(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILADDCHARGE, false));
            SmtpSetting.setMailAddGas(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILADDGAS, false));
            SmtpSetting.setMailRefundGas(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILREFUNDGAS, false));
            SmtpSetting.setMailCardAdd(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILCARDADD, false));
            SmtpSetting.setMailCardReturn(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILCARDRETURN, false));
            SmtpSetting.setMailCardActivate(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILCARDACTIVATE, false));
            SmtpSetting.setMailCardLost(m_sharedPreferences.getBoolean(Common.C_SPRE_SMTP_MAILCARDLOST, false));
            Log.setOutputLog(m_sharedPreferences.getBoolean(Common.C_SPRE_LOG_OUTPUT, true));
            LogUtil.setShowLog(Log.getOutputLog());
            WordCount.setSwitch(m_sharedPreferences.getBoolean(Common.C_SPRE_SW_WORD_COUNT_OUTPUT, false));
            WordCount.setCustomText(m_sharedPreferences.getString(Common.C_SPRE_TXT_WORD_COUNT_OUTPUT, "20"));
            CompanyName.setCompanyName(m_sharedPreferences.getString(Common.C_SPRE_TXT_COMPANY_NAME, ""));
            Commission.setCommission(m_sharedPreferences.getString(Common.C_SPRE_TXT_COMMISSION_RATE, "0.0"));
        }
    }
}
