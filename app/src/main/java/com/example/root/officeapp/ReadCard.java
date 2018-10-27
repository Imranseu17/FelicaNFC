package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.officeapp.felica.lib.FeliCaLib;
import com.example.root.officeapp.felicatag.FelicaTag;
import com.example.root.officeapp.felicatag.NfcException;
import com.example.root.officeapp.felicatag.ReadResponse;
import com.example.root.officeapp.golobal.MainApplication;
import com.example.root.officeapp.nfcfelica.FelicaAccess;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;
import com.example.root.officeapp.nfcfelica.NfcReader;
import com.example.root.officeapp.nfcfelica.SettingData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class ReadCard extends AppCompatActivity {

    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    TextView textdata;
    FelicaTag felicaTag;
    ImageView imageView;
    TextView textView;
    FelicaAccess felicaAccess = new FelicaAccess();
    ArrayList<ReadBlockData> dataList = new ArrayList();
    Tag tag;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readcard);
        textdata = findViewById(R.id.textdata);
        imageView = findViewById(R.id.nfcImage);
        textView = findViewById(R.id.nfcText);
        setTitle(" Read Card ");
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        imageView.startAnimation(animation);
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType("text/plain");
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef};


        techListsArray = new String[][] {
                new String[] { NfcF.class.getName() }
        };


        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

        if(MainApplication.message == "Japanese"){
            setTitle(" カードを読む ");
            textView.setText("NFCカードをタップしてください");
        }

        else if(MainApplication.message == "Bangla"){
            setTitle(" কার্ড পড়ুন ");
            textView.setText("এনএফসি কার্ড ট্যাব দয়া করে");
        }

        else if(MainApplication.message == "English"){
            setTitle(" Read card ");
            textView.setText("Please tab the NFC card");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        imageView.clearAnimation();
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
         tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }

         felicaTag  = new FelicaTag(tag);
        FelicaTag.CommandPacket commandPacket = new FelicaTag.CommandPacket(tag.getId());
        FelicaTag.ServiceCode serviceCode = new FelicaTag.ServiceCode(tag.getId());
        FelicaTag.PMm pMm = new FelicaTag.PMm(tag.getId());
        FelicaTag.SystemCode systemCode = new FelicaTag.SystemCode(tag.getId());
        FeliCaLib.Block block = new FeliCaLib.Block();
        FeliCaLib.MemoryConfigurationBlock memoryConfigurationBlock =
                new FeliCaLib.MemoryConfigurationBlock(tag.getId());






        felicaAccess.readTag(tag);



        try {



            String versionNO =
                    String.valueOf
                            (felicaAccess.GetVersionNo
                                    (GetReadBlockData(0)));
            String cardStatus =
                    String.valueOf(felicaAccess.GetCardStatus
                            (GetReadBlockData(3)));
            String customerID =
                    String.valueOf(
                    felicaAccess. GetCustomerId
                            (tag.getId(),
                                    GetReadBlockData(2)));







            textdata.setText("Type: "+felicaTag.getType()+"\n"+
                    "IDm: "+"\n"+felicaTag.getIdm().toString()+
                    "Service Code List: "+"\n"+felicaTag.getServiceCodeList()+"\n"
                    +"Service Code: "+ serviceCode.getBytes()+"\n"+
                    "ID: "+felicaTag.getId()+
                    "\n"+"CommandPacket: "+"\n"+commandPacket.toString()
                    +"CommandPacket Bytes: "+"\n"+commandPacket.getBytes()
                    +"\n"+"PMm: "+"\n"+pMm.toString()+
                    "PM Bytes: "+pMm.getBytes()
                    +"\n"+"IDm Bytes: "+felicaTag.getIdm().getBytes()+
                    "\n"+""+systemCode
                    +"\n"+"System Code Bytes: "+systemCode.getBytes()+
                    "Block: "+block.getBytes()+
                    "\n"+"Memory Configuration: "+memoryConfigurationBlock.toString()
                    +"\n"+"VersionNO: "+versionNO
                    +"\n"+"CustomerID: "+customerID
                    +"\n"+"CradStatus: "+cardStatus







               );
        } catch (NfcException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }





    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }







           byte[] GetReadBlockData(int Block) {
            byte[] result = null;
            Iterator it = this.dataList.iterator();
            while (it.hasNext()) {
                ReadBlockData readBlockData = (ReadBlockData) it.next();
                if (readBlockData.ReadBlock == Block) {
                    result = readBlockData.ReadData;
                    break;
                }
            }
            if (result != null) {
                return result;
            }
            ReadBlockData addData = new ReadBlockData(null, Block);
            dataList.add(addData);
            return addData.ReadData;
        }





     class ReadBlockData {
        public int ReadBlock;
        public byte[] ReadData;

        public ReadBlockData(byte[] Data, int Block) {
            this.ReadData = Data;
            this.ReadBlock = Block;
            if (this.ReadData != null) {
                return;
            }
            if (Block >= 11) {
                this.ReadData = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
                return;
            }
            byte[] initData = new byte[16];
            new Random().nextBytes(initData);
            this.ReadData = initData;
        }
    }


















}
