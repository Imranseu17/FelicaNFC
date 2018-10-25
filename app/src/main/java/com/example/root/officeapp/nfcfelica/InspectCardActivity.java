package com.example.root.officeapp.nfcfelica;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.root.officeapp.R;

import java.util.ArrayList;

public class InspectCardActivity extends AppCompatActivity {
    private AlertDialog InitReadDiaog;
    HttpResponsAsync.ReadCardArgument ReadCardData;
    private AlertDialog WaitNFC;
    private HttpResponsAsync WebApi;
    private boolean bFelicaAccess;
    private IntentFilter[] intentFiltersArray;
    ErrorListAdapter listErrorAdapter;
    ListView listErrorView;
    HistoryListAdapter listHistoryAdapter;
    ListView listHistoryView;
    PropetiesListAdapter listPropetiesAdapter;
    ListView listPropetiesView;
    private NfcAdapter mAdapter;
    private FelicaAccess mFelica;
    private InspectCardActivity m_ref;
    private boolean nfcAccess;
    private OnClickListener onClickListenerFinish;
    private PendingIntent pendingIntent;
    private String[][] techListsArray;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_inspect_card);
        this.onClickListenerFinish = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        };
        this.nfcAccess = false;

        this.listPropetiesAdapter = new PropetiesListAdapter(this);
        this.listHistoryAdapter = new HistoryListAdapter(this);
        this.listErrorAdapter = new ErrorListAdapter(this);
        this.bFelicaAccess = false;
        this.m_ref = this;
        this.mFelica = new FelicaAccess();
        this.pendingIntent = Common.GetPendingIntent(this, getClass());
        this.intentFiltersArray = Common.GetIntentFilterArray();
        this.techListsArray = Common.GetTechListsArray();
        this.mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        NewWebApi();
        InitReadCard();
    }

    protected void onResume() {
        super.onResume();
        try {
            if (this.bFelicaAccess) {
                this.mAdapter.enableForegroundDispatch(this, this.pendingIntent, this.intentFiltersArray, this.techListsArray);
            }
        } catch (Exception e) {
        }
    }

    protected void onNewIntent(Intent intent) {
        if (this.bFelicaAccess && !this.nfcAccess) {
            final Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            if (tag != null) {
                this.nfcAccess = true;
                final AlertDialog tempProcessing = Common.showAlertDialogWaitCardReading(this);
                new Thread(new Runnable() {
                    public void run() {
                        InspectCardActivity.this.mFelica.setCardId("");
                        if (InspectCardActivity.this.mFelica.readTag(tag)) {
                            InspectCardActivity.this.ReadCard(tag);
                            Common.CloseAlertDialog(this, InspectCardActivity.this.InitReadDiaog);
                        }
                        Common.CloseAlertDialog(this, tempProcessing);
                        InspectCardActivity.this.nfcAccess = false;
                    }
                }).start();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        try {
            if (this.bFelicaAccess) {
                this.mAdapter.disableForegroundDispatch(this);
            }
        } catch (Exception e) {
        }
    }

    private void InitReadCard() {
        Common.showAlertDialog(this, getString(R.string.CardRead_Alert_Title), getString(R.string.CardRead_Alert_Msg), getString(R.string.CardRead_Alert_Button2), getString(R.string.CardRead_Alert_Button1), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                InspectCardActivity.this.m_ref.thisFinish();
            }
        }, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                InspectCardActivity.this.mAdapter.enableForegroundDispatch(InspectCardActivity.this.m_ref, InspectCardActivity.this.pendingIntent, InspectCardActivity.this.intentFiltersArray, InspectCardActivity.this.techListsArray);
                InspectCardActivity.this.bFelicaAccess = true;
                InspectCardActivity.this.InitReadDiaog = Common.showAlertDialogWaitNFC(InspectCardActivity.this.m_ref, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InspectCardActivity.this.m_ref.thisFinish();
                    }
                });
            }
        });
    }

    public void ReadCard(Tag tag) {
        HttpResponsAsync httpResponsAsync = this.WebApi;
        httpResponsAsync.getClass();
        this.ReadCardData = new HttpResponsAsync.ReadCardArgument();
        if (this.mFelica.SetReadCardData(tag, this.WebApi, this.ReadCardData)) {
            final ArrayList<PropetiesListData> propetiesListData = new ArrayList();
            PropetiesListData propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_Version));
            propetiesTemp.setValue(this.ReadCardData.VersionNo);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_CardId));
            propetiesTemp.setValue(this.ReadCardData.CardIdm);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_CustomerId));
            propetiesTemp.setValue(this.ReadCardData.CustomerId);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_CardGroup));
            Object[] objArr = new Object[1];
            int i = 0;
            objArr[0] = Integer.valueOf(Integer.valueOf(this.ReadCardData.CardGroup).intValue() & 255);
            propetiesTemp.setValue(String.format("%02X", objArr));
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_CardStatus));
            propetiesTemp.setValue(String.format("%02X", new Object[]{Integer.valueOf(Integer.valueOf(this.ReadCardData.CardStatus).intValue() & 255)}));
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_Credit));
            propetiesTemp.setValue(this.ReadCardData.Credit);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_Unit));
            propetiesTemp.setValue(this.ReadCardData.Unit);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_BasicFee));
            propetiesTemp.setValue(this.ReadCardData.BasicFee);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_Refund1));
            propetiesTemp.setValue(this.ReadCardData.Refund1);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_Refund2));
            propetiesTemp.setValue(this.ReadCardData.Refund2);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_UntreatedFee));
            propetiesTemp.setValue(this.ReadCardData.UntreatedFee);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_CardHistorynumber));
            propetiesTemp.setValue(this.ReadCardData.CardHistoryNo);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_ErrorNumber));
            propetiesTemp.setValue(this.ReadCardData.ErrorNo);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_OpenCount));
            propetiesTemp.setValue(this.ReadCardData.OpenCount);
            propetiesListData.add(propetiesTemp);
            propetiesTemp = new PropetiesListData();
            propetiesTemp.setName(getString(R.string.InspectCard_Properties_LidTime));
            propetiesTemp.setValue(Common.getFormatDate(this.ReadCardData.LidTime));
            propetiesListData.add(propetiesTemp);
            final ArrayList<HistoryListData> historyListData = new ArrayList();
            for (int i2 = 0; i2 < this.ReadCardData.CardHistory.size(); i2++) {
                HistoryListData dataTemp = new HistoryListData();
                HttpResponsAsync.ReadCardArgumentCardHistory cardHistory = (HttpResponsAsync.ReadCardArgumentCardHistory) this.ReadCardData.CardHistory.get(i2);
                dataTemp.setTime(Common.getFormatDate(cardHistory.HistoryTime));
                dataTemp.setType(cardHistory.HistoryType);
                historyListData.add(dataTemp);
            }
            final ArrayList<ErrorListData> errorListData = new ArrayList();
            while (true) {
                int i3 = i;
                if (i3 < this.ReadCardData.ErrorHistory.size()) {
                    ErrorListData dataTemp2 = new ErrorListData();
                    HttpResponsAsync.ReadCardArgumentErrorHistory errorHistory = (HttpResponsAsync.ReadCardArgumentErrorHistory) this.ReadCardData.ErrorHistory.get(i3);
                    dataTemp2.setGroup(errorHistory.ErrorGroup);
                    dataTemp2.setTime(Common.getFormatDate(errorHistory.ErrorTime));
                    dataTemp2.setType(errorHistory.ErrorType);
                    errorListData.add(dataTemp2);
                    i = i3 + 1;
                } else {
                    runOnUiThread(new Runnable() {
                        public synchronized void run() {
                            InspectCardActivity.this.listPropetiesAdapter.setPropetiesList(propetiesListData);
                            InspectCardActivity.this.listPropetiesView.setAdapter(InspectCardActivity.this.listPropetiesAdapter);
                            InspectCardActivity.this.listHistoryAdapter.setHistoryList(historyListData);
                            InspectCardActivity.this.listHistoryView.setAdapter(InspectCardActivity.this.listHistoryAdapter);
                            InspectCardActivity.this.listErrorAdapter.setErrorList(errorListData);
                            InspectCardActivity.this.listErrorView.setAdapter(InspectCardActivity.this.listErrorAdapter);
                            InspectCardActivity.this.listErrorView.setOnItemClickListener(new OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    InspectCardActivity.this.listErrorView_onItemClick(parent, view, position, id);
                                }
                            });
                        }
                    });
                    return;
                }
            }
        }
        Common.showReadCardFailedAlertFinish(this, this.onClickListenerFinish);
    }

    public void listErrorView_onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String message = Common.GetInspectCardErrorMessage(((HttpResponsAsync.ReadCardArgumentErrorHistory) this.ReadCardData.ErrorHistory.get(position)).ErrorType);
        if (!message.equals("")) {
            Common.showAlertDialog(this, "", message, getString(R.string.required_alert_button));
        }
    }

    private void NewWebApi() {
        this.WebApi = new HttpResponsAsync();
    }

    private void thisFinish() {
        finish();
    }
}
