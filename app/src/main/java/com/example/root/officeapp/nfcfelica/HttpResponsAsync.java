package com.example.root.officeapp.nfcfelica;

import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.example.root.officeapp.R;
import com.google.common.net.HttpHeaders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpResponsAsync extends AsyncTask<Void, Void, String> {
    public static final String C_ADDTRANSACTION_COMMISSION_NAME = "Vender Commission";
    public static final String C_ADDTRANSACTION_GAS_NAME = "Gas";
    private static final String C_STATUS_CODE_APIKeyInvalid = "104002";
    private static final String C_STATUS_CODE_AccountNumberInvalid = "104005";
    private static final String C_STATUS_CODE_AmountoutofRange = "104100";
    private static final String C_STATUS_CODE_AuthenticationFailure = "104004";
    private static final String C_STATUS_CODE_ClientError = "104000";
    private static final String C_STATUS_CODE_ContactInformationInvalid = "104008";
    private static final String C_STATUS_CODE_MalformedData = "104001";
    private static final String C_STATUS_CODE_MeterSerialNumberInvalid = "104006";
    private static final String C_STATUS_CODE_NoPermissions = "104003";
    private static final String C_STATUS_CODE_POSKeyInvalid = "104007";
    private static final String C_STATUS_CODE_ServerError = "105000";
    private static final String C_STATUS_CODE_Success = "102000";
    private static String C_WEB_API_KEY = "";
    private static ReadCardArgument m_ReadCardArgument = null;
    private static activateCardArgument m_activateCardArgument = null;
    private static activateCardResult m_activateCardResult = null;
    private static addAccountBalanceArgument m_addAccountBalanceArgument = null;
    private static addAccountBalanceResult m_addAccountBalanceResult = null;
    private static addTransactionArgument m_addTransactionArgument = null;
    private static addTransactionResult m_addTransactionResult = null;
    private static editCustomerInformationArgument m_editCustomerInformationArgument = null;
    private static getAccountSearchResultsArgument m_getAccountSearchResultsArgument = null;
    private static getAccountSearchResultsResult m_getAccountSearchResultsResult = null;
    private static getAddressInformationResult m_getAddressInformationResult = null;
    private static getChargeAmountsResult m_getChargeAmountsResult = null;
    private static getCustomerConsumptionArgument m_getCustomerConsumptionArgument = null;
    private static getCustomerConsumptionResult m_getCustomerConsumptionResult = null;
    private static getDetailedCustomerInformationArgument m_getDetailedCustomerInformationArgument = null;
    private static getDetailedCustomerInformationResult m_getDetailedCustomerInformationResult = null;
    private static getMeterEmergencyValueResult m_getMeterEmergencyValueResult = null;
    private static getPosSalesArgument m_getPosSalesArgument = null;
    private static getPosSalesResult m_getPosSalesResult = null;
    private static getRefundPriceArgument m_getRefundPriceArgument = null;
    private static getRefundPriceResult m_getRefundPriceResult = null;
    private static getSubscriptionsResult m_getSubscriptionsResult = null;
    private static getTariffInformationArgument m_getTariffInformationArgument = null;
    private static getTariffInformationResult m_getTariffInformationResult = null;
    private static ArrayList<getTariffInformationResult> m_getTariffInformationResults = null;
    private static getTaxRateResult m_getTaxRateResult = null;
    private static issueCardArgument m_issueCardArgument = null;
    private static issueCardResult m_issueCardResult = null;
    private static lostCardArgument m_lostCardArgument = null;
    private static lostCardResult m_lostCardResult = null;
    private static readCardResult m_readCardResult = null;
    private static refundAmountArgument m_refundAmountArgument = null;
    private static returnCardArgument m_returnCardArgument = null;
    private static returnCardResult m_returnCardResult = null;
    public String ErrorString = "";
    public WebApiType RequestWebApi = WebApiType.NONE;
    public boolean Success;
    public WebApiResponsListener mListener;

    public static class ReadCardArgument {
        public String BasicFee;
        public String CardGroup;
        public ArrayList<ReadCardArgumentCardHistory> CardHistory = new ArrayList();
        public String CardHistoryNo;
        public String CardIdm;
        public String CardStatus;
        public ReadCardArgumentConfigData ConfigData;
        public String Credit;
        public String CustomerId;
        public  ArrayList<ReadCardArgumentErrorHistory> ErrorHistory = new ArrayList();
        public String ErrorNo;
        public    String LidTime;
        public  ArrayList<ReadCardArgumentLogDay> LogDay = new ArrayList();
        public  ArrayList<ReadCardArgumentLogHour> LogHour = new ArrayList();
        public String OpenCount;
        public String Refund1;
        public String Refund2;
        public String Unit;
       public String UntreatedFee;
        public String VersionNo;

        public ReadCardArgument() {
            this.ConfigData = new ReadCardArgumentConfigData();
        }
    }

    public static class ReadCardArgumentCardHistory {
        public String HistoryTime;
        public String HistoryType;
    }

    public static class ReadCardArgumentConfigData {
        public String ClockTime;
        public String ClockTimeFlg;
        public String ContinueCon1;
        public String ContinueCon2;
        public String ContinueFlg1;
        public String ContinueFlg2;
        public String ContinueTime1;
        public String ContinueTime2;
        public String ContinueValue1;
        public String ContinueValue2;
        public String EmergencyCon;
        public String EmergencyConFlg;
        public String EmergencyValue;
        public String EmergencyValueFlg;
        public String FlowDetection;
        public String FlowDetectionFlg;
        public String IndexValue;
        public String IndexValueFlg;
        public String LogCount;
        public String LogCountFlg;
        public String LogDays;
        public String LogDaysFlg;
        public String LogInterval;
        public String LogIntervalFlg;
        public String MaxFlowCon;
        public String MaxFlowFlg;
        public String MaxFlowValue;
        public String OpenCockCon;
        public String OpenCockFlg;
        public String OpenCoverCon;
        public String OpenCoverConFlg;
        public String QuakeCon;
        public String QuakeConFlg;
        public String ReductionCon;
        public String ReductionConFlg;
        public String RemoteValveCon;
        public String SleepModeFlg;
        public String WeekControl;
        public String WeekControlFlg;
        public String WeekStart;
        public String WeekStartFlg;
    }

    public static class ReadCardArgumentErrorHistory {
        public String ErrorGroup;
        public String ErrorTime;
        public String ErrorType;
    }

    public static class ReadCardArgumentLogDay {
        public String GasTime;
        public String GasValue;
    }

    public static class ReadCardArgumentLogHour {
        public String GasTime;
        public String GasValue;
    }

    public interface WebApiResponsListener {
        void onRespons();
    }

    public enum WebApiType {
        NONE,
        addAccountBalance,
        addTransaction,
        getRefundPrice,
        refundAmount,
        getTariffInformation,
        getPosSales,
        getCustomerConsumption,
        getAccountSearchResults,
        getDetailedCustomerInformation,
        editCustomerInformation,
        getAddressInformation,
        getTaxRate,
        getSubscriptions,
        getMeterEmergencyValue,
        getChargeAmounts,
        readCard,
        lostCard,
        activateCard,
        returnCard,
        issueCard
    }

    public class activateCardArgument {
        String cardIdm;
    }

    public class activateCardResult {
        String timestamp;
    }

    public class addAccountBalanceArgument {
        String accountNumber;
        String amount;
    }

    public class addAccountBalanceResult {
        String newBalance;
        String receiptId;
        String timestamp;
    }

    public class addTransactionArgument {
        String accountNumber;
        ArrayList<addTransactionArgumentItemInfo> items = new ArrayList();
        String posKey;
    }

    public class addTransactionArgumentItemInfo {
        String itemName = "";
        String price = "";
        String quantity = "";
    }

    public class addTransactionResult {
        String newBalance;
        String receiptId;
        String timestamp;
    }

    public class editCustomerInformationArgument {
        String accountNumber = "";
        String address = "";
        String apartmentName = "";
        String area = "";
        String billingDate = "";
        String city = "";
        String postalCode = "";
        String subArea = "";
    }

    public class getAccountSearchResultsArgument {
        String accountNumber = "";
        String accountStatus = "";
        String address = "";
        String area = "";
        String city = "";
        String email = "";
        String firstName = "";
        String lastName = "";
        String meterSerialNumber = "";
        String phone = "";
        String postalCode = "";
        String subArea = "";
    }

    public class getAccountSearchResultsResult {
        ArrayList<getAccountSearchResultsResultInformation> accountInformation = new ArrayList();
        String timestamp;
    }

    public class getAccountSearchResultsResultInformation {
        String accountId;
        String accountNumber;
        String accountStatus;
        String address;
        String apartment;
        String balance;
        String city;
        String district;
        String division;
        String email;
        String firstName;
        String lastName;
        String meterSerialNumber;
        String phone;
        String postalCode;
    }

    public class getAddressInformationResult {
        ArrayList<getAddressInformationResultCity> city = new ArrayList();
    }

    public class getAddressInformationResultArea {
        String area;
        ArrayList<String> subArea = new ArrayList();
    }

    public class getAddressInformationResultCity {
        ArrayList<getAddressInformationResultArea> area = new ArrayList();
        String city;
    }

    public class getChargeAmountsInformation {
        String amount;
        String chargeId;
    }

    public class getChargeAmountsResult {
        ArrayList<getChargeAmountsInformation> getChargeAmountsInformations = new ArrayList();
    }

    public class getCustomerConsumptionArgument {
        String accountNumber;
        String end;
        String start;
    }

    public class getCustomerConsumptionResult {
        ArrayList<getCustomerConsumptionResultReadings> readings = new ArrayList();
        String timestamp;
        String totalConsumption;
    }

    public class getCustomerConsumptionResultReadings {
        String meterDelta;
        String meterSerialNumber;
        String readingDate;
    }

    public static class getDetailedCustomerInformationArgument {
        String accountNumber;
    }

    public class getDetailedCustomerInformationResult {
        String accountId;
        String accountNumber;
        String address;
        String apartment;
        String balance;
        String billingDay;
        String buetCode;
        ArrayList<getDetailedCustomerInformationResultCardInformation> cardInformations = new ArrayList();
        String city;
        ArrayList<getDetailedCustomerInformationResultContactInformation> contactInformations = new ArrayList();
        String district;
        String division;
        ArrayList<getDetailedCustomerInformationResultMeterInformation> meterInformations = new ArrayList();
        String postalCode;
        int selectCardInformation = -1;
        String status;
        ArrayList<getDetailedCustomerInformationResultSubscriptionInformation> subscriptionInformations = new ArrayList();
        ArrayList<getDetailedCustomerInformationResultTransactionHistory> transactionHistorys = new ArrayList();
    }

    public class getDetailedCustomerInformationResultCardInformation {
        String cardIdm;
        String cardNumber;
        String firstName;
        String issueDate;
        String lastName;
        String status;
    }

    public class getDetailedCustomerInformationResultContactInformation {
        String contactId;
        String email;
        String firstName;
        String lastName;
        String phoneNumber;
    }

    public class getDetailedCustomerInformationResultMeterInformation {
        String address;
        String area;
        String billingMode;
        String city;
        String installationDate;
        String meterId;
        String meterSerialNumber;
        String postalCode;
        String status;
        String subArea;
        String type;
    }

    public class getDetailedCustomerInformationResultSubscriptionInformation {
        String amount;
        String description;
        String name;
    }

    public class getDetailedCustomerInformationResultTransactionHistory {
        String amount;
        String date;
        String transactionName;
    }

    public class getMeterEmergencyValueResult {
        String emergencyValue;
    }

    public class getPosSalesArgument {
        String end;
        String posKey;
        String start;
    }

    public class getPosSalesResult {
        ArrayList<getPosSalesResultReceipts> receiptsArrayList = new ArrayList();
        String timestamp;
        String totalSales;
    }

    public class getPosSalesResultReceipt {
        String itemName;
        String price;
        String quantity;
        String receiptItemId;
    }

    public class getPosSalesResultReceipts {
        String accountNumber;
        String receiptDate;
        String receiptId;
        String receiptItems;
        ArrayList<getPosSalesResultReceipt> receipts = new ArrayList();
    }

    public class getRefundPriceArgument {
        String accountNumber;
        String volume;
    }

    public class getRefundPriceResult {
        String refundAmount;
    }

    public class getSubscriptionsResult {
        ArrayList<getSubscriptionsResultSubscriptionInformation> subscriptionInformations = new ArrayList();
    }

    public class getSubscriptionsResultSubscriptionInformation {
        String amount;
        String description;
        String subscriptionId;
        String subscriptionName;
    }

    public class getTariffInformationArgument {
        String meterSerialNumber;
    }

    public class getTariffInformationResult {
        String meterSerialNumber;
        String tariffBasePrice;
        String tariffPrice;
    }

    public class getTaxRateResult {
        String taxRate;
    }

    public class issueCardArgument {
        String cardIdm;
        String contactId;
        String meterSerialNumber;
    }

    public class issueCardResult {
        String accountNumber;
        String timestamp;
    }

    public class lostCardArgument {
        String cardIdm;
    }

    public class lostCardResult {
        String timestamp;
    }

    public class readCardResult {
        String accountNumber;
        String meterSerialNumber;
    }

    public class refundAmountArgument {
        String accountNumber;
        String volume;
    }

    public class returnCardArgument {
        String cardIdm;
    }

    public class returnCardResult {
        String timestamp;
    }

    public void setAddAccountBalanceArgument(addAccountBalanceArgument value) {
        m_addAccountBalanceArgument = value;
    }

    public void setAddTransactionArgument(addTransactionArgument value) {
        m_addTransactionArgument = value;
    }

    public void setGetRefundPriceArgument(getRefundPriceArgument value) {
        m_getRefundPriceArgument = value;
    }

    public void setRefundAmountArgument(refundAmountArgument value) {
        m_refundAmountArgument = value;
    }

    public void setGetTariffInformationArgument(getTariffInformationArgument value) {
        m_getTariffInformationArgument = value;
    }

    public void setGetPosSalesArgument(getPosSalesArgument value) {
        m_getPosSalesArgument = value;
    }

    public void setGetCustomerConsumptionArgument(getCustomerConsumptionArgument value) {
        m_getCustomerConsumptionArgument = value;
    }

    public void setGetAccountSearchResultsArgument(getAccountSearchResultsArgument value) {
        m_getAccountSearchResultsArgument = value;
    }

    public void setGetDetailedCustomerInformationArgument(getDetailedCustomerInformationArgument value) {
        m_getDetailedCustomerInformationArgument = value;
    }

    public void setEditCustomerInformationArgument(editCustomerInformationArgument value) {
        m_editCustomerInformationArgument = value;
    }

    public void setReadCardArgument(ReadCardArgument value) {
        m_ReadCardArgument = value;
    }

    public void setLostCardArgument(lostCardArgument value) {
        m_lostCardArgument = value;
    }

    public void setActivateCardArgument(activateCardArgument value) {
        m_activateCardArgument = value;
    }

    public void setReturnCardArgument(returnCardArgument value) {
        m_returnCardArgument = value;
    }

    public void setIssueCardArgument(issueCardArgument value) {
        m_issueCardArgument = value;
    }

    public addAccountBalanceResult getAddAccountBalanceResult() {
        return m_addAccountBalanceResult;
    }

    public addTransactionResult getAddTransactionResult() {
        return m_addTransactionResult;
    }

    public getRefundPriceResult getGetRefundPriceResult() {
        return m_getRefundPriceResult;
    }

    public getTariffInformationResult getGetTariffInformationResult() {
        return m_getTariffInformationResult;
    }

    public ArrayList<getTariffInformationResult> getGetTariffInformationResults() {
        return m_getTariffInformationResults;
    }

    public void clearGetTariffInformationResults() {
        if (m_getTariffInformationResults != null) {
            m_getTariffInformationResults.clear();
        }
    }

    public getPosSalesResult getGetPosSalesResult() {
        return m_getPosSalesResult;
    }

    public getCustomerConsumptionResult getGetCustomerConsumptionResult() {
        return m_getCustomerConsumptionResult;
    }

    public getAccountSearchResultsResult getGetAccountSearchResultsResult() {
        return m_getAccountSearchResultsResult;
    }

    public getDetailedCustomerInformationResult getGetDetailedCustomerInformationResult() {
        return m_getDetailedCustomerInformationResult;
    }

    public getAddressInformationResult getGetAddressInformationResult() {
        return m_getAddressInformationResult;
    }

    public getTaxRateResult getGetTaxRateResult() {
        return m_getTaxRateResult;
    }

    public getSubscriptionsResult getGetSubscriptionsResult() {
        return m_getSubscriptionsResult;
    }

    public getMeterEmergencyValueResult getGetMeterEmergencyValueResult() {
        return m_getMeterEmergencyValueResult;
    }

    public getChargeAmountsResult getGetChargeAmountsResult() {
        return m_getChargeAmountsResult;
    }

    public readCardResult getReadCardResult() {
        return m_readCardResult;
    }

    public lostCardResult getLostCardResult() {
        return m_lostCardResult;
    }

    public activateCardResult getActivateCardResult() {
        return m_activateCardResult;
    }

    public returnCardResult getReturnCardResult() {
        return m_returnCardResult;
    }

    public issueCardResult getIssueCardResult() {
        return m_issueCardResult;
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(Void... params) {
        StringBuilder stringBuilder;
        Iterator it = Common.getWebApiUrlList().iterator();
        while (it.hasNext()) {
            String urlSt = (String) it.next();
            String sendjson = "";
            try {
                StringBuilder stringBuilder2;
                this.Success = false;
                C_WEB_API_KEY = SettingData.WebApi.getApiKey();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(urlSt);
                stringBuilder3.append(getWebApiUrl());
                urlSt = stringBuilder3.toString();
                HttpURLConnection con = getHttpsConnection(urlSt);
                JSONObject jsonObject = getSnedJSON();
                OutputStream outputStream = con.getOutputStream();
                con.connect();
                sendjson = jsonObject.toString();
                outputStream.write(sendjson.getBytes("US-ASCII"));
                outputStream.flush();
                outputStream.close();
                if (this.RequestWebApi == WebApiType.readCard) {
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(urlSt);
                    stringBuilder4.append("\n");
                    stringBuilder4.append(sendjson);
//                    LogUtil.i(stringBuilder4.toString());
                }
                String readInputStream = readInputStream(con.getInputStream());
                getResponsJSON(readInputStream);
                if (this.RequestWebApi == WebApiType.addTransaction) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(urlSt);
                    stringBuilder2.append("\n");
                    stringBuilder2.append(sendjson);
                    stringBuilder2.append("\n");
                    stringBuilder2.append(readInputStream);
//                    LogUtil.i(stringBuilder2.toString());
                }
                if (!this.Success) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(urlSt);
                    stringBuilder2.append("\n");
                    stringBuilder2.append(sendjson);
                    stringBuilder2.append("\n");
                    stringBuilder2.append(readInputStream);
//                    LogUtil.d(stringBuilder2.toString());
                }
                return null;
            } catch (MalformedURLException e) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(urlSt);
                stringBuilder.append("\n");
                stringBuilder.append(sendjson);
//                LogUtil.e(stringBuilder.toString(), e);
                this.ErrorString = "timeout";
            } catch (IOException e2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(urlSt);
                stringBuilder.append("\n");
                stringBuilder.append(sendjson);
//                LogUtil.e(stringBuilder.toString(), e2);
                this.ErrorString = "";
            } catch (JSONException e3) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(urlSt);
                stringBuilder.append("\n");
                stringBuilder.append(sendjson);
//                LogUtil.e(stringBuilder.toString(), e3);
                this.ErrorString = "";
            } catch (Exception e4) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(urlSt);
                stringBuilder.append("\n");
                stringBuilder.append(sendjson);
//                LogUtil.e(stringBuilder.toString(), e4);
                this.ErrorString = "";
            }
        }
        return null;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (this.mListener != null) {
            this.mListener.onRespons();
        }
    }

    private String getWebApiUrl() {
        String Result = "";
        switch (this.RequestWebApi) {
            case addAccountBalance:
                return "/addTransaction";
            case addTransaction:
                return "/addTransaction";
            case getRefundPrice:
                return "/getRefundPrice";
            case refundAmount:
                return "/refundAmount";
            case getTariffInformation:
                return "/getTariffInformation";
            case getPosSales:
                return "/getPosSales";
            case getCustomerConsumption:
                return "/getCustomerConsumption";
            case getAccountSearchResults:
                return "/getAccountSearchResults";
            case getDetailedCustomerInformation:
                return "/getDetailedCustomerInformation";
            case editCustomerInformation:
                return "/editCustomerInformation";
            case getAddressInformation:
                return "/getAddressInformation";
            case getTaxRate:
                return "/getTaxRate";
            case getSubscriptions:
                return "/getSubscriptions";
            case getMeterEmergencyValue:
                return "/getMeterEmergencyValue";
            case getChargeAmounts:
                return "/getChargeAmounts";
            case readCard:
                return "/readCard";
            case lostCard:
                return "/lostCard";
            case activateCard:
                return "/activateCard";
            case returnCard:
                return "/returnCard";
            case issueCard:
                return "/issueCard";
            default:
                return Result;
        }
    }

    private JSONObject getSnedJSON() {
        switch (this.RequestWebApi) {
            case addAccountBalance:
                return addAccountBalance(m_addAccountBalanceArgument);
            case addTransaction:
                return addTransaction(m_addTransactionArgument);
            case getRefundPrice:
                return getRefundPrice(m_getRefundPriceArgument);
            case refundAmount:
                return refundAmount(m_refundAmountArgument);
            case getTariffInformation:
                return getTariffInformation(m_getTariffInformationArgument);
            case getPosSales:
                return getPosSales(m_getPosSalesArgument);
            case getCustomerConsumption:
                return getCustomerConsumption(m_getCustomerConsumptionArgument);
            case getAccountSearchResults:
                return getAccountSearchResults(m_getAccountSearchResultsArgument);
            case getDetailedCustomerInformation:
                return getDetailedCustomerInformation(m_getDetailedCustomerInformationArgument);
            case editCustomerInformation:
                return editCustomerInformation(m_editCustomerInformationArgument);
            case getAddressInformation:
                return getAddressInformation();
            case getTaxRate:
                return getTaxRate();
            case getSubscriptions:
                return getSubscriptions();
            case getMeterEmergencyValue:
                return getMeterEmergencyValue();
            case getChargeAmounts:
                return getChargeAmounts();
            case readCard:
                return readCard(m_ReadCardArgument);
            case lostCard:
                return lostCard(m_lostCardArgument);
            case activateCard:
                return activateCard(m_activateCardArgument);
            case returnCard:
                return returnCard(m_returnCardArgument);
            case issueCard:
                return issueCard(m_issueCardArgument);
            default:
                return null;
        }
    }

    private void getResponsJSON(String readString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(readString);
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        switch (this.RequestWebApi) {
            case addAccountBalance:
                m_addAccountBalanceResult = new addAccountBalanceResult();
                addAccountBalanceResult(jsonObject, m_addAccountBalanceResult);
                return;
            case addTransaction:
                m_addTransactionResult = new addTransactionResult();
                addTransactionResult(jsonObject, m_addTransactionResult);
                return;
            case getRefundPrice:
                m_getRefundPriceResult = new getRefundPriceResult();
                getRefundPriceResult(jsonObject, m_getRefundPriceResult);
                return;
            case refundAmount:
                getRefundAmountResult(jsonObject);
                return;
            case getTariffInformation:
                m_getTariffInformationResult = new getTariffInformationResult();
                getTariffInformationResult(jsonObject, m_getTariffInformationResult);
                return;
            case getPosSales:
                m_getPosSalesResult = new getPosSalesResult();
                getPosSalesResult(jsonObject, m_getPosSalesResult);
                return;
            case getCustomerConsumption:
                m_getCustomerConsumptionResult = new getCustomerConsumptionResult();
                getCustomerConsumptionResult(jsonObject, m_getCustomerConsumptionResult);
                return;
            case getAccountSearchResults:
                m_getAccountSearchResultsResult = new getAccountSearchResultsResult();
                getAccountSearchResultsResult(jsonObject, m_getAccountSearchResultsResult);
                return;
            case getDetailedCustomerInformation:
                m_getDetailedCustomerInformationResult = new getDetailedCustomerInformationResult();
                getDetailedCustomerInformationResult(jsonObject, m_getDetailedCustomerInformationResult);
                return;
            case editCustomerInformation:
                editCustomerInformationResult(jsonObject);
                return;
            case getAddressInformation:
                m_getAddressInformationResult = new getAddressInformationResult();
                getAddressInformationResult(jsonObject, m_getAddressInformationResult);
                return;
            case getTaxRate:
                m_getTaxRateResult = new getTaxRateResult();
                getTaxRateResult(jsonObject, m_getTaxRateResult);
                return;
            case getSubscriptions:
                m_getSubscriptionsResult = new getSubscriptionsResult();
                getSubscriptionsResult(jsonObject, m_getSubscriptionsResult);
                return;
            case getMeterEmergencyValue:
                m_getMeterEmergencyValueResult = new getMeterEmergencyValueResult();
                getMeterEmergencyValueResult(jsonObject, m_getMeterEmergencyValueResult);
                return;
            case getChargeAmounts:
                m_getChargeAmountsResult = new getChargeAmountsResult();
                getChargeAmountsResult(jsonObject, m_getChargeAmountsResult);
                return;
            case readCard:
                m_readCardResult = new readCardResult();
                readCardResult(jsonObject, m_readCardResult);
                return;
            case lostCard:
                m_lostCardResult = new lostCardResult();
                lostCardResult(jsonObject, m_lostCardResult);
                return;
            case activateCard:
                m_activateCardResult = new activateCardResult();
                activateCardResult(jsonObject, m_activateCardResult);
                return;
            case returnCard:
                m_returnCardResult = new returnCardResult();
                returnCardResult(jsonObject, m_returnCardResult);
                return;
            case issueCard:
                m_issueCardResult = new issueCardResult();
                issueCardResult(jsonObject, m_issueCardResult);
                return;
            default:
                return;
        }
    }

    private JSONObject addAccountBalance(addAccountBalanceArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("posKey", SettingData.WebApi.getPosKey());
            JSONArray itemsJson = new JSONArray();
            for (int i = 0; i < 1; i++) {
                JSONObject itemJson = new JSONObject();
                itemJson.put("price", Common.parseDouble(param.amount) * -1.0d);
                itemJson.put("itemName", "Account Credit");
                itemJson.put("quantity", Common.parseDouble("1"));
                itemsJson.put(itemJson);
            }
            jsonObject.put("itemInfo", itemsJson);
            jsonObject.put("tax", false);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject addTransaction(addTransactionArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("posKey", param.posKey);
            JSONArray itemsJson = new JSONArray();
            for (int i = 0; i < param.items.size(); i++) {
                JSONObject itemJson = new JSONObject();
                addTransactionArgumentItemInfo item = (addTransactionArgumentItemInfo) param.items.get(i);
                itemJson.put("price", Common.parseDouble(item.price));
                itemJson.put("itemName", item.itemName);
                itemJson.put("quantity", Common.parseDouble(item.quantity));
                itemsJson.put(itemJson);
            }
            jsonObject.put("itemInfo", itemsJson);
            jsonObject.put("tax", true);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getRefundPrice(getRefundPriceArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("volume", Common.parseDouble(param.volume));
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject refundAmount(refundAmountArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("volume", Common.parseDouble(param.volume));
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getTariffInformation(getTariffInformationArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("meterSerialNumber", param.meterSerialNumber);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getPosSales(getPosSalesArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("posKey", param.posKey);
            jsonObject.put("start", param.start);
            jsonObject.put("end", param.end);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getCustomerConsumption(getCustomerConsumptionArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("start", param.start);
            jsonObject.put("end", param.end);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getAccountSearchResults(getAccountSearchResultsArgument search) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            if (!search.accountNumber.isEmpty()) {
                jsonObject.put("accountNumber", search.accountNumber);
            }
            if (!search.meterSerialNumber.isEmpty()) {
                jsonObject.put("meterSerialNumber", search.meterSerialNumber);
            }
            if (!search.postalCode.isEmpty()) {
                jsonObject.put("postalCode", search.postalCode);
            }
            if (!search.address.isEmpty()) {
                jsonObject.put("address", search.address);
            }
            if (!search.phone.isEmpty()) {
                jsonObject.put("phone", search.phone);
            }
            if (!search.email.isEmpty()) {
                jsonObject.put(NotificationCompat.CATEGORY_EMAIL, search.email);
            }
            if (!search.firstName.isEmpty()) {
                jsonObject.put("firstName", search.firstName);
            }
            if (!search.lastName.isEmpty()) {
                jsonObject.put("lastName", search.lastName);
            }
            if (!search.city.isEmpty()) {
                jsonObject.put("city", Common.parseInt(search.city));
            }
            if (!search.area.isEmpty()) {
                jsonObject.put("area", Common.parseInt(search.area));
            }
            if (!search.subArea.isEmpty()) {
                jsonObject.put("subArea", Common.parseInt(search.subArea));
            }
            if (!search.accountStatus.isEmpty()) {
                jsonObject.put("accountStatus", search.accountStatus);
            }
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
            LogUtil.e(e);
        } catch (Exception e2) {
            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getDetailedCustomerInformation(getDetailedCustomerInformationArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", param.accountNumber);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject editCustomerInformation(editCustomerInformationArgument editCustomer) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("accountNumber", editCustomer.accountNumber);
            if (!editCustomer.address.isEmpty()) {
                jsonObject.put("address", editCustomer.address);
            }
            if (!editCustomer.apartmentName.isEmpty()) {
                jsonObject.put("apartmentName", editCustomer.apartmentName);
            }
            if (!editCustomer.city.isEmpty()) {
                jsonObject.put("city", editCustomer.city);
            }
            if (!editCustomer.area.isEmpty()) {
                jsonObject.put("area", editCustomer.area);
            }
            if (!editCustomer.subArea.isEmpty()) {
                jsonObject.put("subarea", editCustomer.subArea);
            }
            if (!editCustomer.postalCode.isEmpty()) {
                jsonObject.put("postalCode", editCustomer.postalCode);
            }
            if (!editCustomer.billingDate.isEmpty()) {
                jsonObject.put("billingDate", editCustomer.billingDate);
            }
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getAddressInformation() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getTaxRate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getSubscriptions() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getMeterEmergencyValue() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject getChargeAmounts() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject readCard(ReadCardArgument readCardData) {
        JSONObject jsonObject = new JSONObject();
        JSONObject cardDatajson = new JSONObject();
        try {
            JSONObject itemJson;
            jsonObject.put("apiKey", C_WEB_API_KEY);
            cardDatajson.put("VersionNo", Common.parseInt(readCardData.VersionNo));
            cardDatajson.put("CardIdm", readCardData.CardIdm);
            cardDatajson.put("CustomerId", readCardData.CustomerId);
            cardDatajson.put("CardGroup", Common.parseInt(readCardData.CardGroup));
            cardDatajson.put("CardStatus", Common.parseInt(readCardData.CardStatus));
            cardDatajson.put("Credit", Common.parseDouble(readCardData.Credit));
            cardDatajson.put("Unit", Common.parseDouble(readCardData.Unit));
            cardDatajson.put("BasicFee", Common.parseInt(readCardData.BasicFee));
            cardDatajson.put("Refund1", Common.parseDouble(readCardData.Refund1));
            cardDatajson.put("Refund2", Common.parseDouble(readCardData.Refund2));
            cardDatajson.put("UntreatedFee", Common.parseInt(readCardData.UntreatedFee));
            cardDatajson.put("CardHistoryNo", Common.parseInt(readCardData.CardHistoryNo));
            cardDatajson.put("ErrorNo", Common.parseInt(readCardData.ErrorNo));
            cardDatajson.put("OpenCount", Common.parseInt(readCardData.OpenCount));
            cardDatajson.put("LidTime", readCardData.LidTime);
            JSONObject configDataJSON = new JSONObject();
            configDataJSON.put("LogDays", Common.parseInt(readCardData.ConfigData.LogDays));
            configDataJSON.put("LogDaysFlg", Common.parseInt(readCardData.ConfigData.LogDaysFlg));
            configDataJSON.put("IndexValue", Common.parseDouble(readCardData.ConfigData.IndexValue));
            configDataJSON.put("IndexValueFlg", Common.parseInt(readCardData.ConfigData.IndexValueFlg));
            configDataJSON.put("ContinueTime1", Common.parseInt(readCardData.ConfigData.ContinueTime1));
            configDataJSON.put("ContinueValue1", Common.parseInt(readCardData.ConfigData.ContinueValue1));
            configDataJSON.put("ContinueCon1", Common.parseInt(readCardData.ConfigData.ContinueCon1));
            configDataJSON.put("ContinueFlg1", Common.parseInt(readCardData.ConfigData.ContinueFlg1));
            configDataJSON.put("ContinueTime2", Common.parseInt(readCardData.ConfigData.ContinueTime2));
            configDataJSON.put("ContinueValue2", Common.parseInt(readCardData.ConfigData.ContinueValue2));
            configDataJSON.put("ContinueCon2", Common.parseInt(readCardData.ConfigData.ContinueCon2));
            configDataJSON.put("ContinueFlg2", Common.parseInt(readCardData.ConfigData.ContinueFlg2));
            configDataJSON.put("MaxFlowValue", Common.parseInt(readCardData.ConfigData.MaxFlowValue));
            configDataJSON.put("MaxFlowCon", Common.parseInt(readCardData.ConfigData.MaxFlowCon));
            configDataJSON.put("MaxFlowFlg", Common.parseInt(readCardData.ConfigData.MaxFlowFlg));
            configDataJSON.put("OpenCockCon", Common.parseInt(readCardData.ConfigData.OpenCockCon));
            configDataJSON.put("OpenCockFlg", Common.parseInt(readCardData.ConfigData.OpenCockFlg));
            configDataJSON.put("LogInterval", Common.parseInt(readCardData.ConfigData.LogInterval));
            configDataJSON.put("LogIntervalFlg", Common.parseInt(readCardData.ConfigData.LogIntervalFlg));
            configDataJSON.put("LogCount", Common.parseInt(readCardData.ConfigData.LogCount));
            configDataJSON.put("LogCountFlg", Common.parseInt(readCardData.ConfigData.LogCountFlg));
            configDataJSON.put("ClockTime", readCardData.ConfigData.ClockTime);
            configDataJSON.put("ClockTimeFlg", Common.parseInt(readCardData.ConfigData.ClockTimeFlg));
            configDataJSON.put("WeekStart", Common.parseInt(readCardData.ConfigData.WeekStart));
            configDataJSON.put("WeekStartFlg", Common.parseInt(readCardData.ConfigData.WeekStartFlg));
            configDataJSON.put("WeekControl", Common.parseInt(readCardData.ConfigData.WeekControl));
            configDataJSON.put("WeekControlFlg", Common.parseInt(readCardData.ConfigData.WeekControlFlg));
            configDataJSON.put("QuakeCon", Common.parseInt(readCardData.ConfigData.QuakeCon));
            configDataJSON.put("QuakeConFlg", Common.parseInt(readCardData.ConfigData.QuakeConFlg));
            configDataJSON.put("OpenCoverCon", Common.parseInt(readCardData.ConfigData.OpenCoverCon));
            configDataJSON.put("OpenCoverConFlg", Common.parseInt(readCardData.ConfigData.OpenCoverConFlg));
            configDataJSON.put("FlowDetection", Common.parseInt(readCardData.ConfigData.FlowDetection));
            configDataJSON.put("FlowDetectionFlg", Common.parseInt(readCardData.ConfigData.FlowDetectionFlg));
            configDataJSON.put("EmergencyCon", Common.parseInt(readCardData.ConfigData.EmergencyCon));
            configDataJSON.put("EmergencyConFlg", Common.parseInt(readCardData.ConfigData.EmergencyConFlg));
            configDataJSON.put("ReductionCon", Common.parseInt(readCardData.ConfigData.ReductionCon));
            configDataJSON.put("ReductionConFlg", Common.parseInt(readCardData.ConfigData.ReductionConFlg));
            configDataJSON.put("RemoteValveCon", Common.parseInt(readCardData.ConfigData.RemoteValveCon));
            configDataJSON.put("SleepModeFlg", Common.parseInt(readCardData.ConfigData.SleepModeFlg));
            configDataJSON.put("EmergencyValue", Common.parseDouble(readCardData.ConfigData.EmergencyValue));
            configDataJSON.put("EmergencyValueFlg", Common.parseInt(readCardData.ConfigData.EmergencyValueFlg));
            cardDatajson.put("ConfigData", configDataJSON);
            JSONArray cardHistoryJson = new JSONArray();
            int i = 0;
            for (int i2 = 0; i2 < readCardData.CardHistory.size(); i2++) {
                JSONObject itemJson2 = new JSONObject();
                ReadCardArgumentCardHistory data = (ReadCardArgumentCardHistory) readCardData.CardHistory.get(i2);
                itemJson2.put("HistoryType", Common.parseInt(data.HistoryType));
                itemJson2.put("HistoryTime", data.HistoryTime);
                cardHistoryJson.put(itemJson2);
            }
            cardDatajson.put("CardHistory", cardHistoryJson);
            JSONArray errorHistoryJson = new JSONArray();
            for (int i3 = 0; i3 < readCardData.ErrorHistory.size(); i3++) {
                JSONObject itemJson3 = new JSONObject();
                ReadCardArgumentErrorHistory data2 = (ReadCardArgumentErrorHistory) readCardData.ErrorHistory.get(i3);
                itemJson3.put("ErrorGroup", Common.parseInt(data2.ErrorGroup));
                itemJson3.put("ErrorType", data2.ErrorType);
                itemJson3.put("ErrorTime", data2.ErrorTime);
                errorHistoryJson.put(itemJson3);
            }
            cardDatajson.put("ErrorHistory", errorHistoryJson);
            JSONArray logHourJson = new JSONArray();
            for (int i4 = 0; i4 < readCardData.LogHour.size(); i4++) {
                itemJson = new JSONObject();
                ReadCardArgumentLogHour data3 = (ReadCardArgumentLogHour) readCardData.LogHour.get(i4);
                itemJson.put("GasValue", Common.parseDecimalJSON(data3.GasValue));
                itemJson.put("GasTime", data3.GasTime);
                logHourJson.put(itemJson);
            }
            cardDatajson.put("LogHour", logHourJson);
            JSONArray logDayJson = new JSONArray();
            while (i < readCardData.LogDay.size()) {
                itemJson = new JSONObject();
                ReadCardArgumentLogDay data4 = (ReadCardArgumentLogDay) readCardData.LogDay.get(i);
                itemJson.put("GasValue", Common.parseDecimalJSON(data4.GasValue));
                itemJson.put("GasTime", data4.GasTime);
                logDayJson.put(itemJson);
                i++;
            }
            cardDatajson.put("LogDay", logDayJson);
            jsonObject.put("cardData", cardDatajson);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
            LogUtil.e(e);
        } catch (Exception e2) {
            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject lostCard(lostCardArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("cardIdm", param.cardIdm);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject activateCard(activateCardArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("cardIdm", param.cardIdm);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
            LogUtil.e(e);
        } catch (Exception e2) {
            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject returnCard(returnCardArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("cardIdm", param.cardIdm);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private JSONObject issueCard(issueCardArgument param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("apiKey", C_WEB_API_KEY);
            jsonObject.put("cardIdm", param.cardIdm);
            jsonObject.put("contactId", param.contactId);
            jsonObject.put("meterSerialNumber", param.meterSerialNumber);
            jsonObject.put("username", Common.getWebApiUserName());
            jsonObject.put("password", Common.getWebApiPassWord());
        } catch (JSONException e) {
//            LogUtil.e(e);
        } catch (Exception e2) {
//            LogUtil.e(e2);
        }
        return jsonObject;
    }

    private boolean addAccountBalanceResult(JSONObject outBound, addAccountBalanceResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.receiptId = outBound.getString("receiptId");
            result.timestamp = outBound.getString("timestamp");
            result.newBalance = outBound.getString("newBalance");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean addTransactionResult(JSONObject outBound, addTransactionResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.receiptId = outBound.getString("receiptId");
            result.timestamp = outBound.getString("timestamp");
            result.newBalance = outBound.getString("newBalance");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getRefundPriceResult(JSONObject outBound, getRefundPriceResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.refundAmount = outBound.getString("refundAmount");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getRefundAmountResult(JSONObject outBound) {
        if (checkStatusCode(outBound)) {
            return true;
        }
        return false;
    }

    private boolean getTariffInformationResult(JSONObject outBound, getTariffInformationResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.tariffPrice = outBound.getString("tariffPrice");
            result.tariffBasePrice = outBound.getString("tariffBasePrice");
            result.meterSerialNumber = m_getTariffInformationArgument.meterSerialNumber;
            if (m_getTariffInformationResults == null) {
                m_getTariffInformationResults = new ArrayList();
            }
            boolean found = false;
            for (int i = 0; i < m_getTariffInformationResults.size(); i++) {
                if (m_getTariffInformationArgument.meterSerialNumber.equals(((getTariffInformationResult) m_getTariffInformationResults.get(i)).meterSerialNumber)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                m_getTariffInformationResults.add(result);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getPosSalesResult(JSONObject outBound, getPosSalesResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            result.totalSales = outBound.getString("totalSales");
            JSONArray receipts = outBound.getJSONArray("receipts");
            for (int i = 0; i < receipts.length(); i++) {
                getPosSalesResultReceipts addReceipts = new getPosSalesResultReceipts();
                JSONObject receiptsJSON = receipts.getJSONObject(i);
                addReceipts.receiptId = receiptsJSON.getString("receiptId");
                addReceipts.receiptDate = receiptsJSON.getString("receiptDate");
                addReceipts.accountNumber = receiptsJSON.getString("accountNumber");
                JSONArray receipt = receiptsJSON.getJSONArray("receiptItems");
                for (int n = 0; n < receipt.length(); n++) {
                    getPosSalesResultReceipt addReceipt = new getPosSalesResultReceipt();
                    JSONObject receiptJSON = receipt.getJSONObject(n);
                    addReceipt.receiptItemId = receiptJSON.getString("receiptItemId");
                    addReceipt.itemName = receiptJSON.getString("itemName");
                    addReceipt.quantity = receiptJSON.getString("quantity");
                    addReceipt.price = receiptJSON.getString("price");
                    addReceipts.receipts.add(addReceipt);
                }
                result.receiptsArrayList.add(addReceipts);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getCustomerConsumptionResult(JSONObject outBound, getCustomerConsumptionResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            result.totalConsumption = outBound.getString("totalConsumption");
            JSONArray readings = outBound.getJSONArray("readings");
            for (int i = 0; i < readings.length(); i++) {
                getCustomerConsumptionResultReadings addReading = new getCustomerConsumptionResultReadings();
                JSONObject receiptsJSON = readings.getJSONObject(i);
                addReading.meterSerialNumber = receiptsJSON.getString("meterSerialNumber");
                addReading.readingDate = receiptsJSON.getString("readingDate");
                addReading.meterDelta = receiptsJSON.getString("meterDelta");
                result.readings.add(addReading);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getAccountSearchResultsResult(JSONObject outBound, getAccountSearchResultsResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            JSONArray informations = outBound.getJSONArray("accountInformation");
            for (int i = 0; i < informations.length(); i++) {
                getAccountSearchResultsResultInformation information = new getAccountSearchResultsResultInformation();
                JSONObject receiptsJSON = informations.getJSONObject(i);
                information.accountId = receiptsJSON.getString("accountId");
                information.accountNumber = receiptsJSON.getString("accountNumber");
                information.meterSerialNumber = receiptsJSON.getString("meterSerialNumber");
                information.postalCode = receiptsJSON.getString("postalCode");
                information.address = receiptsJSON.getString("address");
                information.phone = receiptsJSON.getString("phone");
                information.email = receiptsJSON.getString(NotificationCompat.CATEGORY_EMAIL);
                information.apartment = receiptsJSON.getString("apartment");
                information.firstName = receiptsJSON.getString("firstName");
                information.lastName = receiptsJSON.getString("lastName");
                information.division = receiptsJSON.getString("area");
                information.district = receiptsJSON.getString("subArea");
                information.city = receiptsJSON.getString("city");
                information.balance = receiptsJSON.getString("balance");
                information.accountStatus = receiptsJSON.getString("accountStatus");
                result.accountInformation.add(information);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getDetailedCustomerInformationResult(JSONObject outBound, getDetailedCustomerInformationResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            int i;
            JSONObject receiptsJSON;
            result.accountId = outBound.getString("accountId");
            result.accountNumber = outBound.getString("accountNumber");
            result.address = outBound.getString("address");
            result.apartment = outBound.getString("apartment");
            result.division = outBound.getString("division");
            result.district = outBound.getString("district");
            result.city = outBound.getString("city");
            result.postalCode = outBound.getString("postalCode");
            result.balance = outBound.getString("balance");
            result.status = outBound.getString(NotificationCompat.CATEGORY_STATUS);
            result.billingDay = outBound.getString("billingDay");
            result.buetCode = outBound.getString("buetCode");
            JSONArray tempJSONArray = outBound.getJSONArray("subscriptionInformation");
            int i2 = 0;
            for (i = 0; i < tempJSONArray.length(); i++) {
                getDetailedCustomerInformationResultSubscriptionInformation subscriptionInformation = new getDetailedCustomerInformationResultSubscriptionInformation();
                receiptsJSON = tempJSONArray.getJSONObject(i);
                subscriptionInformation.name = receiptsJSON.getString("name");
                subscriptionInformation.description = receiptsJSON.getString("description");
                subscriptionInformation.amount = receiptsJSON.getString("amount");
                result.subscriptionInformations.add(subscriptionInformation);
            }
            tempJSONArray = outBound.getJSONArray("transactionHistory");
            for (i = 0; i < tempJSONArray.length(); i++) {
                getDetailedCustomerInformationResultTransactionHistory transactionHistory = new getDetailedCustomerInformationResultTransactionHistory();
                receiptsJSON = tempJSONArray.getJSONObject(i);
                transactionHistory.date = receiptsJSON.getString("date");
                transactionHistory.transactionName = receiptsJSON.getString("transactionName");
                transactionHistory.amount = receiptsJSON.getString("amount");
                result.transactionHistorys.add(transactionHistory);
            }
            tempJSONArray = outBound.getJSONArray("contactInformation");
            for (i = 0; i < tempJSONArray.length(); i++) {
                getDetailedCustomerInformationResultContactInformation contactInformation = new getDetailedCustomerInformationResultContactInformation();
                receiptsJSON = tempJSONArray.getJSONObject(i);
                contactInformation.contactId = receiptsJSON.getString("contactId");
                contactInformation.firstName = receiptsJSON.getString("firstName");
                contactInformation.lastName = receiptsJSON.getString("lastName");
                contactInformation.phoneNumber = receiptsJSON.getString("phoneNumber");
                contactInformation.email = receiptsJSON.getString(NotificationCompat.CATEGORY_EMAIL);
                result.contactInformations.add(contactInformation);
            }
            tempJSONArray = outBound.getJSONArray("cardInformation");
            for (i = 0; i < tempJSONArray.length(); i++) {
                getDetailedCustomerInformationResultCardInformation cardInformation = new getDetailedCustomerInformationResultCardInformation();
                receiptsJSON = tempJSONArray.getJSONObject(i);
                cardInformation.status = receiptsJSON.getString(NotificationCompat.CATEGORY_STATUS);
                cardInformation.cardNumber = receiptsJSON.getString("cardNumber");
                cardInformation.cardIdm = receiptsJSON.getString("cardIdm");
                cardInformation.issueDate = receiptsJSON.getString("issueDate");
                cardInformation.firstName = receiptsJSON.getString("firstName");
                cardInformation.lastName = receiptsJSON.getString("lastName");
                result.cardInformations.add(cardInformation);
            }
            tempJSONArray = outBound.getJSONArray("meterInformation");
            while (i2 < tempJSONArray.length()) {
                getDetailedCustomerInformationResultMeterInformation meterInformation = new getDetailedCustomerInformationResultMeterInformation();
                JSONObject receiptsJSON2 = tempJSONArray.getJSONObject(i2);
                meterInformation.meterId = receiptsJSON2.getString("meterId");
                meterInformation.meterSerialNumber = receiptsJSON2.getString("meterSerialNumber");
                meterInformation.installationDate = receiptsJSON2.getString("installationDate");
                meterInformation.type = receiptsJSON2.getString("type");
                meterInformation.status = receiptsJSON2.getString(NotificationCompat.CATEGORY_STATUS);
                meterInformation.billingMode = receiptsJSON2.getString("billingMode");
                meterInformation.city = receiptsJSON2.getString("city");
                meterInformation.area = receiptsJSON2.getString("area");
                meterInformation.subArea = receiptsJSON2.getString("subArea");
                meterInformation.address = receiptsJSON2.getString("address");
                meterInformation.postalCode = receiptsJSON2.getString("postalCode");
                result.meterInformations.add(meterInformation);
                i2++;
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean editCustomerInformationResult(JSONObject outBound) {
        if (checkStatusCode(outBound)) {
            return true;
        }
        return false;
    }

    private boolean getAddressInformationResult(JSONObject outBound, getAddressInformationResult result) {
        JSONException e;
        Exception e2;
        JSONObject jSONObject;
        getAddressInformationResult getaddressinformationresult;
        if (checkStatusCode(outBound)) {
            try {
                try {
                    JSONObject cityJSON = outBound.getJSONObject("addressInformation");
                    JSONArray cityArray = cityJSON.names();
                    int i = 0;
                    while (i < cityArray.length()) {
                        getAddressInformationResultCity city = new getAddressInformationResultCity();
                        city.city = cityArray.getString(i);
                        JSONObject areaJSON = cityJSON.getJSONObject(city.city);
                        JSONArray areaArray = areaJSON.names();
                        for (int n = 0; n < areaArray.length(); n++) {
                            getAddressInformationResultArea area = new getAddressInformationResultArea();
                            area.area = areaArray.getString(n);
                            JSONArray subAreaArray = areaJSON.getJSONArray(area.area);
                            for (int m = 0; m < subAreaArray.length(); m++) {
                                area.subArea.add(subAreaArray.getString(m));
                            }
                            city.area.add(area);
                        }
                        try {
                            result.city.add(city);
                            i++;
                        } catch (Exception e4) {
                            e2 = e4;
                        }
                    }
                    getaddressinformationresult = result;
                    return true;
                } catch (JSONException e5) {
                    e = e5;
                    getaddressinformationresult = result;
//                    LogUtil.e(e);
                    return false;
                } catch (Exception e6) {
                    e2 = e6;
                    getaddressinformationresult = result;
//                    LogUtil.e(e2);
                    return false;
                }
            } catch (Exception e8) {
                e2 = e8;
                jSONObject = outBound;
                getaddressinformationresult = result;
//                LogUtil.e(e2);
                return false;
            }
        }
        jSONObject = outBound;
        getaddressinformationresult = result;
        return false;
    }

    private boolean getTaxRateResult(JSONObject outBound, getTaxRateResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.taxRate = outBound.getString("taxRate");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getSubscriptionsResult(JSONObject outBound, getSubscriptionsResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            JSONArray tempJSONArray = outBound.getJSONArray("subscriptionInformation");
            for (int i = 0; i < tempJSONArray.length(); i++) {
                getSubscriptionsResultSubscriptionInformation subscriptionInformation = new getSubscriptionsResultSubscriptionInformation();
                JSONObject receiptsJSON = tempJSONArray.getJSONObject(i);
                subscriptionInformation.amount = receiptsJSON.getString("amount");
                subscriptionInformation.subscriptionName = receiptsJSON.getString("subscriptionName");
                subscriptionInformation.description = receiptsJSON.getString("description");
                subscriptionInformation.subscriptionId = receiptsJSON.getString("subscriptionId");
                result.subscriptionInformations.add(subscriptionInformation);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getMeterEmergencyValueResult(JSONObject outBound, getMeterEmergencyValueResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.emergencyValue = outBound.getString("emergencyValue");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean getChargeAmountsResult(JSONObject outBound, getChargeAmountsResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            JSONArray tempJSONArray = outBound.getJSONArray("chargeAmounts");
            for (int i = 0; i < tempJSONArray.length(); i++) {
                getChargeAmountsInformation chargeAmountsInformation = new getChargeAmountsInformation();
                JSONObject receiptsJSON = tempJSONArray.getJSONObject(i);
                chargeAmountsInformation.amount = receiptsJSON.getString("amount");
                chargeAmountsInformation.chargeId = receiptsJSON.getString("chargeId");
                result.getChargeAmountsInformations.add(chargeAmountsInformation);
            }
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean readCardResult(JSONObject outBound, readCardResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.accountNumber = outBound.getString("accountNumber");
            result.meterSerialNumber = outBound.getString("meterSerialNumber");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean lostCardResult(JSONObject outBound, lostCardResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean activateCardResult(JSONObject outBound, activateCardResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean returnCardResult(JSONObject outBound, returnCardResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean issueCardResult(JSONObject outBound, issueCardResult result) {
        if (!checkStatusCode(outBound)) {
            return false;
        }
        try {
            result.timestamp = outBound.getString("timestamp");
            result.accountNumber = outBound.getString("accountNumber");
            return true;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    private boolean checkStatusCode(JSONObject outBound) {
        try {
            this.Success = outBound.getString("statusCode").equals(C_STATUS_CODE_Success);
            if (!this.Success) {
                String string = outBound.getString("statusCode");
                boolean z = true;
                if (string.hashCode() == 1448754210 && string.equals(C_STATUS_CODE_POSKeyInvalid)) {
                    z = false;
                }
                if (z) {
                    this.ErrorString = "";
                } else {
                    this.ErrorString = Common.getResources().getString(R.string.webapi_error_addTransaction);
                }
            }
            return this.Success;
        } catch (JSONException e) {
//            LogUtil.e(e);
            return false;
        } catch (Exception e2) {
//            LogUtil.e(e2);
            return false;
        }
    }

    public String readInputStream(InputStream in) throws IOException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String st = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "US-ASCII"));
        while (true) {
            String readLine = br.readLine();
            st = readLine;
            if (readLine != null) {
                sb.append(st);
            } else {
                try {
                    break;
                } catch (Exception e) {
//                    LogUtil.e(e);
                }
            }
        }
        in.close();
        return sb.toString();
    }

    private static HttpURLConnection getHttpsConnection(String url) throws Exception {
        HttpURLConnection urlconn;
        URL connectURL = new URL(url);
        if ("https".equals(connectURL.getProtocol())) {
            TrustManager[] tm = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            }};
            SSLContext sslcontext = SSLContext.getInstance(Common.C_SMTP_PROTECTION_LEVEL_SSL);
            sslcontext.init(null, tm, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            urlconn = (HttpsURLConnection) connectURL.openConnection();
            ((HttpsURLConnection) urlconn).setSSLSocketFactory(sslcontext.getSocketFactory());
        } else {
            urlconn = (HttpURLConnection) connectURL.openConnection();
        }
        urlconn.setRequestMethod("POST");
        urlconn.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        urlconn.setRequestProperty(HttpHeaders.ACCEPT, "application/json");
        urlconn.setDoInput(true);
        urlconn.setDoOutput(true);
        urlconn.setConnectTimeout(30000);
        urlconn.setReadTimeout(30000);
        return urlconn;
    }
}
