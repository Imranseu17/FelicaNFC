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
import com.example.root.officeapp.golobal.MainApplication;
import com.example.root.officeapp.nfcfelica.FelicaAccess;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;

import java.io.IOException;
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
    ArrayList<ReadCard.ReadBlockData> dataList = new ArrayList();









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
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
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
















        try {

            BlockDataList blockDataList = new BlockDataList();
//            NfcF nfc = NfcF.get(tag);
//
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 0)))[0], 0, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 1)))[0], 1, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 2)))[0], 2, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 3)))[0], 3, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 4)))[0], 4, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 5)))[0], 5, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 6)))[0], 6, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 7)))[0], 7, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 8)))[0], 8, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 11)))[0], 11, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 12)))[0], 12, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 13)))[0], 13, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 14)))[0], 14, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 15)))[0], 15, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 16)))[0], 16, true);
//            blockDataList.AddReadBlockData(felicaAccess.parse(nfc.transceive(felicaAccess.readWithoutEncryption(felicaAccess.targetIDm, felicaAccess.size, felicaAccess.targetServiceCode, 17)))[0], 17, true);





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




    class BlockDataList {
        ArrayList<ReadBlockData> dataList = new ArrayList();

        BlockDataList() {
        }

        void AddReadBlockData(byte[] Data, int Block, boolean Overwrite) {
            boolean found = false;
            Iterator it = this.dataList.iterator();
            while (it.hasNext()) {
                FelicaAccess.ReadBlockData ReadData = (FelicaAccess.ReadBlockData) it.next();
                if (ReadData.ReadBlock == Block) {
                    if (Overwrite) {
                        ReadData.ReadData = Data;
                    }
                    found = true;
                    if (found) {
                        this.dataList.add(new ReadBlockData(Data, Block));
                        return;
                    }
                    return;
                }
            }
            if (found) {
            }
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
