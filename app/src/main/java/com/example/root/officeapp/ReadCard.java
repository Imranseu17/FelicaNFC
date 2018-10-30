package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.keyboard.Keyboard;
import com.example.root.officeapp.golobal.MainApplication;
import com.example.root.officeapp.lang.StringUtils;
import com.example.root.officeapp.nfcfelica.Cng2Model;
import com.example.root.officeapp.nfcfelica.CngModel;
import com.example.root.officeapp.nfcfelica.Common;
import com.example.root.officeapp.nfcfelica.ContinueModel;
import com.example.root.officeapp.nfcfelica.FelicaAccess;
import com.example.root.officeapp.nfcfelica.HistoryListData;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;
import com.example.root.officeapp.nfcfelica.ParModel;
import com.example.root.officeapp.nfcfelica.SettingData;
import com.google.common.base.Ascii;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

import static com.example.root.officeapp.lang.NumberUtils.TAG;


public class ReadCard extends AppCompatActivity {

    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    TextView textdata;
    ImageView imageView;
    TextView textView;
    Tag tag;
    byte byCardGroup,byCardStatus;
    byte [] TargetIDm,targetServiceCode;
    String strCustomerId, cardGroup, cardStatus,versionNO,cardIDm,credit,unit,
            basicFee,refund1,refund2,untreatedFee,openCount,lidTime,indexValue,LidTime;
    int size,historyNO,errorNO;
    private boolean isChargeCheckFailed = false;
    private static Resources resources = Resources.getSystem();
    HttpResponsAsync.ReadCardArgument ReadCardData;


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

       ReadTag(tag);

       boolean response = GasChargeCard(tag,10.99,0,0,9, "10003419");

       if(response){
           WriteStatus(tag,historyNO+1);
       }





//        felicaTag = new FelicaTag(tag);
//        FelicaTag.CommandPacket commandPacket = new FelicaTag.CommandPacket(tag.getId());
//        FelicaTag.ServiceCode serviceCode = new FelicaTag.ServiceCode(tag.getId());
//        FelicaTag.PMm pMm = new FelicaTag.PMm(tag.getId());
//        FelicaTag.SystemCode systemCode = new FelicaTag.SystemCode(tag.getId());
//        FeliCaLib.Block block = new FeliCaLib.Block();
//        FeliCaLib.MemoryConfigurationBlock memoryConfigurationBlock =
//                new FeliCaLib.MemoryConfigurationBlock(tag.getId());
//
//
//        readTag(tag);
//
//        NfcReader nfcReader = new NfcReader();
//        byte[][] readdata = nfcReader.ReadTag(tag);




//        try {
//
//
//
//
//
//
//
//
//
//
//
//
//
//            textdata.setText("Type: "+felicaTag.getType()+"\n"+
//                    "IDm: "+"\n"+felicaTag.getIdm().toString()+
//                    "Service Code List: "+"\n"+felicaTag.getServiceCodeList()+"\n"
//                    +"Service Code: "+ serviceCode.getBytes()+"\n"+
//                    "ID: "+felicaTag.getId()+
//                    "\n"+"CommandPacket: "+"\n"+commandPacket.toString()
//                    +"CommandPacket Bytes: "+"\n"+commandPacket.getBytes()
//                    +"\n"+"PMm: "+"\n"+pMm.toString()+
//                    "PM Bytes: "+pMm.getBytes()
//                    +"\n"+"IDm Bytes: "+felicaTag.getIdm().getBytes()+
//                    "\n"+""+systemCode
//                    +"\n"+"System Code Bytes: "+systemCode.getBytes()+
//                    "Block: "+block.getBytes()+
//                    "\n"+"Memory Configuration: "+memoryConfigurationBlock.toString()
//                    +"\n"+"CradGroup: "+String.valueOf(nfcReader.byCardGroup)
//                    +"\n"+"CustomerID: "+nfcReader.strCustomerId
//                    +"\n"+"CradStatus: "+String.valueOf(nfcReader.byCardStatus)
//
//
//
//
//
//
//
//               );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//


    }


    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }


    String strCardId;

    private enum IsEncryption {
        NotEncrypt,
        Encrypt
    }

    public  byte [] []  ReadTag ( Tag tag )  {
        NfcF nfc  =  NfcF . get ( tag );
        try  {
            nfc .  connect ();
            // System 1 System Code -> 0xFE00
            byte []   TargetSystemCode  =   new  byte [] { ( byte )  0xfe , ( byte )  0x00 };

            // create polling command
            byte []  polling  =  polling ( nfc.getSystemCode() );

            // get the result by sending a command
            byte []  PollingRes  =  nfc . transceive ( polling );

            // Get the IDm of System 0 (1 byte data size, 2 byte response code, the size of the IDm is 8 bytes)
            TargetIDm  =  Arrays. copyOfRange ( PollingRes ,  2 ,  10 );

            // the size of the data contained in the service (this time it was 4)
            size  =  1 ;

            // target service code -> 0x1A8B
            targetServiceCode  =  new byte[]{(byte) 0, (byte) 9};

            // Create Read Without Encryption command
            //byte []  req  =  readWithoutEncryption ( TargetIDm ,  size ,  targetServiceCode,1 );

            // get the result by sending a command
            //byte []  res  =  nfc . transceive ( req );

//             nfc.connect();
//             byte [] TargetIDm = Arrays.copyOfRange(nfc.transceive(polling(TargetSystemCode)), 2, 10);
//             if ("" == this.strCardId) {
//                 this.strCardId = GetCardIdm(tag.getId());
//             }

            strCardId = GetCardIdm(tag.getId());



            ReadCard.BlockDataList datalist = new ReadCard.BlockDataList();
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 0)))[0], 0, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 1)))[0], 1, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 2)))[0], 2, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 3)))[0], 3, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 4)))[0], 4, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 5)))[0], 5, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 6)))[0], 6, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 7)))[0], 7, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 8)))[0], 8, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 11)))[0], 11, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 12)))[0], 12, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 13)))[0], 13, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 14)))[0], 14, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 15)))[0], 15, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 16)))[0], 16, true);
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 17)))[0], 17, true);






            try {
                byCardGroup = (byte) 0;
                byCardStatus = (byte) 0;
                strCustomerId = "";
                byCardStatus = GetCardStatus(datalist.GetReadBlockData(3));
                cardStatus = String.format("%02X", new Object[]{Integer.valueOf(Integer.valueOf(byCardStatus).intValue() & 255)});
                byCardGroup = GetCardGroup(datalist.GetReadBlockData(2));
                Object[] objArr = new Object[1];
                objArr[0] = Integer.valueOf(Integer.valueOf(byCardGroup).intValue() & 255);
                cardGroup = String.format("%02X", objArr);
                strCustomerId = GetCustomerId(datalist.GetReadBlockData(1), datalist.GetReadBlockData(2));
                versionNO = String.valueOf(GetVersionNo(datalist.GetReadBlockData(0)));
                String _cardIdm = GetCardIdm(TargetIDm);
                cardIDm = _cardIdm;
                credit = String.valueOf(GetCredit(datalist.GetReadBlockData(3)));
                unit = String.valueOf(GetUnit(datalist.GetReadBlockData(3)));
                basicFee = String.valueOf(GetBasicFee(datalist.GetReadBlockData(3)));
                refund1 = String.valueOf(GetRefund1(datalist.GetReadBlockData(4)));
                refund2 = String.valueOf(GetRefund2(datalist.GetReadBlockData(4)));
                untreatedFee = String.valueOf(GetUntreatedFee(datalist.GetReadBlockData(4)));
                historyNO = Integer.parseInt(String.valueOf(GetCardHistoryNo(datalist.GetReadBlockData(5)))) ;
                errorNO = Integer.parseInt(String.valueOf(GetErrorNo(datalist.GetReadBlockData(5))));
                openCount = String.valueOf(GetOpenCount(datalist.GetReadBlockData(7)));
                lidTime = GetWebApiDate(GetLidTime(datalist.GetReadBlockData(7))) ;
                indexValue = String.valueOf(GetIndexValue(datalist.GetReadBlockData(5)));
                LidTime = getFormatDate(lidTime);

                ReadCardData = new HttpResponsAsync.ReadCardArgument();

                ArrayList<HistoryListData> historyListData = new ArrayList();
                for (int i2 = 0; i2 < this.ReadCardData.CardHistory.size(); i2++) {
                    HistoryListData dataTemp = new HistoryListData();
                    HttpResponsAsync.ReadCardArgumentCardHistory cardHistory = (HttpResponsAsync.ReadCardArgumentCardHistory) this.ReadCardData.CardHistory.get(i2);
                    dataTemp.setTime(Common.getFormatDate(cardHistory.HistoryTime));
                    dataTemp.setType(cardHistory.HistoryType);
                    historyListData.add(dataTemp);
                }



                textdata.setText("*************  Card Properties  *************"+"\n"+"\n"+
                        "Version NO:"+versionNO+"\n"+
                        "Card Status: "+ cardStatus+"\n"
                        +"Card ID: "+cardIDm
                +"\n"+"Customer ID: "+strCustomerId
                +"\n"+"Card Group: "+ cardGroup
                +"\n"+"Credit: "+credit
                +"\n"+"Unit: "+unit
                +"\n"+"Basic Fee: "+basicFee
                +"\n"+"Refund1: "+refund1
                +"\n"+"Refund2: "+refund2
                +"\n"+"Untreated Fee: "+untreatedFee
                +"\n"+"Card History NO: "+historyNO
                +"\n"+"Card Error NO: "+errorNO
                +"\n"+"Open Count: "+openCount
                +"\n"+"Lid Time: "+LidTime
                +"\n"+"IndexValue: "+indexValue
                +"\n"+"\n"+"*************  Card History  *************"+"\n"+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            }




            nfc . close ();

            // parse the results data only get
            //return  parse ( res );
            return  null;
        }  catch  ( Exception  e )  {
            Log. e ( TAG ,  e .getMessage()  ,  e );
        }

        return  null ;
    }


    private  byte []  polling ( byte []  systemCode )  {
        ByteArrayOutputStream bout  =  new  ByteArrayOutputStream ( 100 );

        bout . write ( 0x00 );            // data length byte dummy
        bout . write ( 0x00 );            // command code
        bout . write ( systemCode [ 0 ]);   // SystemCode
        bout . write ( systemCode [ 1 ]);   // systemCode
        bout . write ( 0x01 );            // request code
        bout . write ( 0x0f );            // time slot

        byte []  msg  =  bout . toByteArray ();
        msg [ 0 ]  =  ( byte )  msg . length ;  // first byte is the data length
        return  msg ;
    }


    private  byte []  readWithoutEncryption ( byte []  idm ,  int  size ,  byte []  serviceCode )  throws IOException {
        ByteArrayOutputStream  bout  =  new  ByteArrayOutputStream ( 100 );

        bout . write ( 0 );               // data length byte dummy
        bout . write ( 0x06 );            // command code
        bout . write ( idm );             // IDm 8byte
        bout . write ( 1 );               // Length Number of Services (2 bytes will repeat this number for the following)

        // Because the specification of the service code is little endian, it specifies from the low order byte.
        bout . write ( serviceCode [ 1 ]);  // service code lower byte
        bout . write ( serviceCode [ 0 ]);  // service code high byte
        bout . write ( size );            // number of blocks

        // specify the block number
        for  ( int  I  =  0 ;  I  <  size ;  I++)  {
            bout . write ( 0X80 );        // see Section 4.3 of the block element upper byte "Felica user manual excerpt"
            bout . write ( I );           // block number
        }

        byte []  msg  =  bout . toByteArray ();
        msg [ 0 ]  =  ( byte )  msg . length ;  // first byte is the data length
        return  msg ;
    }

    public byte[] readWithoutEncryption(byte[] idm, int size, byte[] serviceCode, int startBlock) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(100);
        bout.write(0);
        bout.write(6);
        bout.write(idm);
        bout.write(1);
        bout.write(serviceCode[1]);
        bout.write(serviceCode[0]);
        bout.write(size);
        for (int i = 0; i < size; i++) {
            bout.write(128);
            bout.write(startBlock + i);
        }
        byte[] msg = bout.toByteArray();
        msg[0] = (byte) msg.length;
        return msg;
    }


    private  byte [] []  parse ( byte []  res )  throws  Exception  {
        // res [10] Error code. 0x00 is normal
        if  ( res [ 10 ]  !=  0x00 )
            throw  new  RuntimeException ( "Read Without Encryption Command Error" );

        // res [12] Reply block number
        // res [13 + n * 16] Repeat real data 16 (byte / block)
        int  size  =  res [ 12 ];
        byte [] []  data  =  new  byte [ size][16]  ;
        String  str  =  "" ;
        for  ( int  i  =  0 ;  i  <  size ;  i ++)  {
            byte []  tmp  =   new  byte [ 16 ];
            int  offset  =  13 +  i  *  16 ;
            for  ( int  j  =  0 ;  j  <  16 ;  j ++)  {
                tmp [ j ]  =  res [ offset  +  j ];
            }

            data [ i ]  =  tmp ;
        }
        return  data ;
    }

    public  class BlockDataList {
        ArrayList<ReadBlockData> dataList = new ArrayList();

        BlockDataList() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x0020  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        void AddReadBlockData(byte[] Data, int Block, boolean Overwrite) {
            boolean found = false;
            Iterator it = this.dataList.iterator();
            while (it.hasNext()) {
                ReadBlockData ReadData = (ReadBlockData) it.next();
                if (ReadData.ReadBlock == Block) {
                    if (Overwrite) {
                        ReadData.ReadData = Data;
                    }
                    found = true;
                    if (found) {
                        this.dataList.add(new ReadCard.ReadBlockData(Data, Block));
                        return;
                    }
                    return;
                }
            }
            if (found) {
            }
            this.dataList.add(new ReadCard.ReadBlockData(Data, Block));
        }

        public   byte[] GetReadBlockData(int Block) {
            byte[] result = null;
            Iterator it = this.dataList.iterator();
            while (it.hasNext()) {
                ReadBlockData ReadData = (ReadBlockData) it.next();
                if (ReadData.ReadBlock == Block) {
                    result = ReadData.ReadData;
                    break;
                }
            }
            if (result != null) {
                return result;
            }
            ReadCard.ReadBlockData addData = new ReadCard.ReadBlockData(null, Block);
            this.dataList.add(addData);
            return addData.ReadData;
        }
    }

    public class ReadBlockData {
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

    public byte GetCardStatus(byte[] getData) {
        int i = 1;
        //String cardStatus = DatatypeConverter.printHexBinary(getData);
        String cardStatus = GetByteToHexString(getData, ReadCard.IsEncryption.Encrypt, 0, 1);
        int hashCode = cardStatus.hashCode();
        if (hashCode != 1536) {
            if (hashCode != 1572) {
                if (hashCode != 1629) {
                    switch (hashCode) {
                        case 1541:
                            if (cardStatus.equals("05")) {
                                i = 3;
                                break;
                            }
                        case 1542:
                            if (cardStatus.equals("06")) {
                                i = 2;
                                break;
                            }
                    }
                } else if (cardStatus.equals("30")) {
                    i = 0;
                    switch (i) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            break;
                        default:
                            RuntimeException runtimeException = new RuntimeException("InvalidProgramException cardStatus");
                            break;
                    }
                    return (byte) hex2int(cardStatus);
                }
            }
        } else if (cardStatus.equals("00")) {
            i = 4;
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    break;
                default:
                    break;
            }
            return (byte) hex2int(cardStatus);
        }
        i = -1;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                break;
            default:
                break;
        }
        return (byte) hex2int(cardStatus);
    }

    private String GetByteToHexString(byte[] getData, ReadCard.IsEncryption enc, int start, int length) {
        byte[] _data = getData;
        if (_data.length != 16) {
            throw new RuntimeException("Failed to read.");
        }
        int end = (start + length) - 1;
        String _val = "";
        for (int i = start; i <= end; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(_val);
            stringBuilder.append(PadLeft(Integer.toHexString(Decryption(enc, _data[i]) & 255), 2, '0'));
            _val = stringBuilder.toString();
        }
        return _val.toUpperCase();
    }

    private int hex2int(String s) {
        try {
            return Integer.parseInt(s, 16);
        } catch (Exception e) {
            return 0;
        }
    }

    private String PadLeft(String strOrg, int length, char setStr) {
        String strRet = strOrg;
        StringBuilder sb = new StringBuilder(strRet);
        while (strRet.length() < length) {
            strRet = sb.insert(0, setStr).toString();
        }
        return strRet;
    }

    private byte Decryption(ReadCard.IsEncryption enc, byte input) {
        if (enc == ReadCard.IsEncryption.NotEncrypt) {
            return input;
        }
        return (byte) (255 - ((256 + (input & 255)) - hex2int(strCardId.substring(14, 16))));
    }

    private String GetCardIdm(byte[] Idm) {
        StringBuilder sb = new StringBuilder();
        int length = Idm.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X", new Object[]{Byte.valueOf(Idm[i])}));
        }
        return sb.toString();
    }

    public String GetCustomerId(byte[] getData1, byte[] getData2) {
        String result = "";
        byte[] _b = new byte[20];
        if (getData1.length != 16) {
            throw new RuntimeException("GetCustomerId");
        } else if (getData2.length != 16) {
            throw new RuntimeException("GetCustomerId");
        } else {
            int i;
            for (i = 0; i < 16; i++) {
                _b[i] = Decryption(ReadCard.IsEncryption.Encrypt, getData1[i]);
            }
            for (i = 0; i < 4; i++) {
                _b[16 + i] = Decryption(ReadCard.IsEncryption.Encrypt, getData2[i]);
            }
            try {
                result = new String(_b, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                //LogUtil.e(e);
            }
            if (result.charAt(0) == '0') {
//                if (SettingData.WordCount.getSwitch()) {
                if (true) {
                    int resultLength = result.length();
                    try {
//                        int settingWordCount = Integer.parseInt(SettingData.WordCount.getCustomText());
                        int settingWordCount = 16;
                        if (settingWordCount <= 0 || settingWordCount > 20) {
                            throw new Exception();
                        }
                        result = result.substring(resultLength - settingWordCount, resultLength);
                    } catch (Exception e2) {
                        result = result.replaceFirst("^0+", "");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Exception! at GetCustomerId.\nWordCount : ");
                        stringBuilder.append(SettingData.WordCount.getCustomText());
                        stringBuilder.append("\n");
//                        stringBuilder.append(//LogUtil.Output(e2.getStackTrace()));
                        //LogUtil.i(stringBuilder.toString());
                        return result;
                    }
                }
                result = result.replaceFirst("^0+", "");
            }
            return result;
        }
    }

    private byte GetCardGroup(byte[] getData) {
        int i = 1;
        String _s = GetByteToHexString(getData, IsEncryption.Encrypt, 15, 1);
        int hashCode = _s.hashCode();
        if (hashCode == 1536) {
            if (_s.equals("00")) {
                i = 0;
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        } else if (hashCode != 1760) {
            if (hashCode == 1792 && _s.equals("88")) {
                i = 2;
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        return (byte) hex2int(_s);
                    default:
                        throw new RuntimeException("cardGroup");
                }
            }
        }
        i = -1;
        switch (i) {
            case 0:
            case 1:
            case 2:
                break;
            default:
                break;
        }
        return (byte) hex2int(_s);
    }

    public int GetVersionNo(byte[] getData) {
        return BCDTo(GetByteToBitString(getData, IsEncryption.NotEncrypt, 0, 1));
    }

    private int BCDTo(String bcd) {
        if (bcd.length() % 4 > 0) {
            throw new RuntimeException("BCDTo Out Of Range");
        }
        String _s = "";
        int _len = bcd.length() / 4;
        for (int i = 0; i < _len; i++) {
            int start = i * 4;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(_s);
            stringBuilder.append(String.valueOf(bin2int(bcd.substring(start, start + 4))));
            _s = stringBuilder.toString();
        }
        return Integer.parseInt(_s);
    }

    private int bin2int(String s) {
        try {
            return Integer.parseInt(s, 2);
        } catch (Exception e) {
            return 0;
        }
    }


    private String GetByteToBitString(byte[] getData, IsEncryption enc, int start, int length) {
        byte[] _data = getData;
        if (_data.length != 16) {
            throw new RuntimeException("Failed to read.");
        }
        int end = (start + length) - 1;
        String _val = "";
        for (int i = start; i <= end; i++) {
            byte _byte = Decryption(enc, _data[i]);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(_val);
            stringBuilder.append(PadLeft(Integer.toBinaryString(_byte & 255), 8, '0'));
            _val = stringBuilder.toString();
        }
        return _val;
    }

    private double GetCredit(byte[] getData) {
        return GetByteToDouble(getData, IsEncryption.Encrypt, 1, 3, 3);
    }

    private double GetByteToDouble(byte[] getData, IsEncryption enc, int start, int inte, int dec) {
        int _len = inte + dec;
        byte[] _data = getData;
        if (_data.length != 16) {
            throw new RuntimeException("Failed to read.");
        }
        int end = (start + _len) - 1;
        byte[] _byte = new byte[16];
        for (int i = start; i <= end; i++) {
            _byte[i] = Decryption(enc, _data[i]);
        }
        StringBuilder sb = new StringBuilder(new String(_byte, start, inte + dec, StandardCharsets.US_ASCII));
        sb.insert(inte, ".");
        double[] _cnt = new double[1];
        if (Common.isNumDouble(new String(sb), _cnt)) {
            return _cnt[0];
        }
        throw new RuntimeException("Failed to read.");
    }

    private double GetUnit(byte[] getData) {
        return GetByteToDouble(getData, IsEncryption.Encrypt, 7, 3, 2);
    }

    private int GetBasicFee(byte[] getData) {
        return GetByteToInt(getData, IsEncryption.Encrypt, 12, 4);
    }

    private int GetByteToInt(byte[] getData, IsEncryption enc, int start, int length) {
        byte[] _data = getData;
        if (_data.length != 16) {
            throw new RuntimeException("Failed to read.");
        }
        int end = (start + length) - 1;
        byte[] _byte = new byte[16];
        for (int i = start; i <= end; i++) {
            _byte[i] = Decryption(enc, _data[i]);
        }
        int[] _cnt = new int[1];
        if (Common.isNumInt(new String(_byte, start, length, StandardCharsets.US_ASCII), _cnt)) {
            return _cnt[0];
        }
        throw new RuntimeException("Failed to read.");
    }

    private double GetRefund1(byte[] getData) {
        return GetByteToDouble(getData, IsEncryption.Encrypt, 0, 3, 3);
    }

    private double GetRefund2(byte[] getData) {
        return GetByteToDouble(getData, IsEncryption.Encrypt, 6, 3, 3);
    }

    private int GetUntreatedFee(byte[] getData) {
        return GetByteToInt(getData, IsEncryption.Encrypt, 7, 4);
    }

    public boolean GasChargeCard(Tag tag, double Credit, double Unit, int BasicFee, double emergencyValue, String meter) {
        String str = meter;
        NfcF nfc = NfcF.get(tag);
        try {
            nfc.connect();
            String _cardIdm = GetCardIdm(TargetIDm);
            if (this.strCardId.equals(_cardIdm)) {
                byte[][] data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, this.targetServiceCode, 2)));
                CheckDataLength(data);
                byte _cardGroup = GetCardGroup(data[0]);
                BlockDataList datalist = new BlockDataList();
                datalist.AddReadBlockData(data[0], 2, false);
                data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 3)));
                CheckDataLength(data);
                byte _cardStatus = GetCardStatus(data[0]);
                datalist.AddReadBlockData(data[0], 3, false);
                if (this.isChargeCheckFailed || isGasChargeCard(_cardStatus, _cardGroup)) {
                    byte[] req = readWithoutEncryption(TargetIDm, size, targetServiceCode, 5);
                    byte[] res = nfc.transceive(req);
                    CheckDataLength(parse(res));
                    SetCredit(datalist.GetReadBlockData(3), Credit);
                    SetUnit(datalist.GetReadBlockData(3), Unit);
                    SetBasicFee(datalist.GetReadBlockData(3), BasicFee);
                    CngModel tempCngModel = new CngModel();
                    tempCngModel.ContinueFlg1 = 1;
                    tempCngModel.ContinueFlg2 = 1;
                    req = readWithoutEncryption(TargetIDm, size, targetServiceCode, 5);
                    res = nfc.transceive(req);
                    data = parse(res);
                    CheckDataLength(data);
                    datalist.AddReadBlockData(data[0], 5, false);
                    SetCng(datalist.GetReadBlockData(5), tempCngModel);
                    ContinueModel tempContinueModel = new ContinueModel();
                    tempContinueModel.ContinueTime = 10;
                    tempContinueModel.ContinueValue = 10;
                    tempContinueModel.ContinueCon = 1;
                    tempContinueModel.ContinueFlg = 1;
                    req = readWithoutEncryption(TargetIDm, size, targetServiceCode, 6);
                    res = nfc.transceive(req);
                    byte[][] data2 = parse(res);
                    CheckDataLength(data2);
                    datalist.AddReadBlockData(data2[0], 6, false);
                    SetContinue1(datalist.GetReadBlockData(6), tempContinueModel);
                    ContinueModel tempContinueModel2 = new ContinueModel();
                    tempContinueModel2.ContinueTime = 24;
                    tempContinueModel2.ContinueValue = 5;
                    tempContinueModel2.ContinueCon = 2;
                    tempContinueModel2.ContinueFlg = 1;
                    SetContinue2(datalist.GetReadBlockData(6), tempContinueModel2);
                    Cng2Model tempCng2Model = new Cng2Model();
                    tempCng2Model.QuakeConFlg = 1;
                    tempCng2Model.EmergencyConFlg = 1;
                    tempCng2Model.EmergencyValueFlg = 1;
                    ContinueModel tempContinueModel22 = tempContinueModel2;
                    CngModel tempCngModel2 = tempCngModel;
                    byte[] req2 = readWithoutEncryption(TargetIDm, size, targetServiceCode, 8);
                    byte[] res2 = nfc.transceive(req2);
                    data2 = parse(res2);
                    CheckDataLength(data2);
                    datalist.AddReadBlockData(data2[0], 8, false);
                    SetCng2(datalist.GetReadBlockData(8), tempCng2Model);
                    ParModel tempParModel = new ParModel();
                    tempParModel.QuakeCon = 2;
                    tempParModel.EmergencyCon = 1;
                    SetPar(datalist.GetReadBlockData(8), tempParModel);
                    byte _cardGroup2 = _cardGroup;
                    SetEmergencyValue(datalist.GetReadBlockData(8), emergencyValue);
                    writeWithoutEncryption(nfc, datalist);
                    //LogUtil.i("Success to write a card.");
                    if (CheckWroteData(datalist, nfc, tempCngModel2, tempContinueModel, tempContinueModel22, tempCng2Model, tempParModel, emergencyValue)) {
                        //LogUtil.i("Success to check a written card.");
                        this.isChargeCheckFailed = false;
                        if (nfc != null) {
                            try {
                                nfc.close();
                            } catch (IOException e) {
                                //LogUtil.i(e.toString());
                            }
                        }
                        ReturnLocale();
                        return true;
                    }
                    this.isChargeCheckFailed = true;
                    if (nfc != null) {
                        try {
                            nfc.close();
                        } catch (IOException e2) {
                            //LogUtil.i(e2.toString());
                        }
                    }
                    ReturnLocale();
                    return false;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("At isGasChargeCard.\nisChargeCheckFailed : ");
                stringBuilder.append(this.isChargeCheckFailed);
                stringBuilder.append("\nCardId : ");
                stringBuilder.append(this.strCardId);
                stringBuilder.append("\nMeter : ");
                stringBuilder.append(str);
                stringBuilder.append("\nCardStatus : ");
                stringBuilder.append(_cardStatus);
                stringBuilder.append("\nCardGroup : ");
                stringBuilder.append(_cardGroup);
                //LogUtil.i(stringBuilder.toString());
                if (nfc != null) {
                    try {
                        nfc.close();
                    } catch (IOException e22) {
                        //LogUtil.i(e22.toString());
                    }
                }
                ReturnLocale();
                return false;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("At CardId checking.\n");
            stringBuilder2.append(this.strCardId);
            stringBuilder2.append("\n");
            stringBuilder2.append(str);
            stringBuilder2.append("\n");
            //LogUtil.i(stringBuilder2.toString());
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException e222) {
                    //LogUtil.i(e222.toString());
                }
            }
            ReturnLocale();
            return false;
        } catch (Exception e3) {
            Exception e4 = e3;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("Exeption at GasChargeCard.\n");
            stringBuilder3.append(this.strCardId);
            stringBuilder3.append("\n");
            stringBuilder3.append(str);
            stringBuilder3.append("\n");
//            stringBuilder3.append(//LogUtil.Output(e4.getStackTrace()));
            //LogUtil.i(stringBuilder3.toString());
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException e2222) {
                    //LogUtil.i(e2222.toString());
                }
            }
            ReturnLocale();
            return false;
        } catch (Throwable th) {
            Throwable th2 = th;
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException e22222) {
                    //LogUtil.i(e22222.toString());
                }
            }
            ReturnLocale();
            return false;
        }
    }

    private void CheckDataLength(byte[][] data) {
        if (this.size != data.length) {
            RuntimeException runtimeException = new RuntimeException("CheckDataLength");
        }
    }

    private boolean isGasChargeCard(byte CardStatus, byte CardGroup) {
        if (CardGroup != Keyboard.VK_F8) {
            return false;
        }
        if (CardStatus == (byte) 6 || CardStatus == Keyboard.VK_0) {
            return true;
        }
        return false;
    }

    private void SetCredit(byte[] setData, double credit) {
        if (ValidDouble(credit, 3, 3)) {
            SetDoubleToByte(setData, IsEncryption.Encrypt, credit, 1, 3, 3);
            return;
        }
        throw new RuntimeException("Credit Out Of Range");
    }

    private boolean ValidDouble(double num, int inte, int dec) {
        if (num < 0.0d) {
            return false;
        }
        if (num == 0.0d) {
            return true;
        }
        String[] _s = String.valueOf(num).split("\\.");
        if (_s[0].length() > inte) {
            return false;
        }
        if (_s.length <= 1 || _s[1].length() <= dec) {
            return true;
        }
        return false;
    }

    private void SetDoubleToByte(byte[] setData, IsEncryption enc, double val, int start, int inte, int dec) {
        int i;
        StringBuilder stringBuilder;
        int _len = inte + dec;
        int i2 = 0;
        String strFormat = "{0,number,";
        for (i = 0; i < inte; i++) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(strFormat);
            stringBuilder.append("0");
            strFormat = stringBuilder.toString();
        }
        StringBuilder strFormat2 = new StringBuilder();
        strFormat2.append(strFormat);
        strFormat2.append(".");
        strFormat = strFormat2.toString();
        for (i = 0; i < dec; i++) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(strFormat);
            stringBuilder.append("0");
            strFormat = stringBuilder.toString();
        }
        strFormat2 = new StringBuilder();
        strFormat2.append(strFormat);
        strFormat2.append("}");
        strFormat = MessageFormat.format(strFormat2.toString(), new Object[]{Double.valueOf(val)});
        if (strFormat.length() != _len + 1) {
            throw new RuntimeException("Failed to Write.");
        }
        byte[] _b = strFormat.replace(".", "").getBytes(StandardCharsets.US_ASCII);
        while (i2 < _len) {
            setData[start + i2] = Encryption(enc, _b[i2]);
            i2++;
        }
    }

    private byte Encryption(IsEncryption enc, byte input) {
        if (enc == IsEncryption.NotEncrypt) {
            return input;
        }
        int _y = hex2int(this.strCardId.substring(14, 16)) + (255 - input);
        String _z = Integer.toHexString(_y);
        if (_z.length() > 2) {
            _y = hex2int(_z.substring(_z.length() - 2, _z.length()));
        }
        return (byte) _y;
    }

    private void SetUnit(byte[] setData, double unit) {
        if (ValidDouble(unit, 3, 2)) {
            SetDoubleToByte(setData, IsEncryption.Encrypt, unit, 7, 3, 2);
            return;
        }
        throw new RuntimeException("Unit Out Of Range");
    }

    private void SetBasicFee(byte[] setData, int basicFee) {
        if (ValidInt(basicFee, 4)) {
            SetIntToByte(setData, IsEncryption.Encrypt, basicFee, 12, 4);
            return;
        }
        throw new RuntimeException("BasicFee Out Of Range");
    }

    private boolean ValidInt(int num, int len) {
        if (num < 0) {
            return false;
        }
        if (num != 0 && String.valueOf(num).length() > len) {
            return false;
        }
        return true;
    }

    private void SetIntToByte(byte[] setData, IsEncryption enc, int val, int start, int length) {
        int i = 0;
        String strFormat = "{0,number,";
        for (int i2 = 0; i2 < length; i2++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(strFormat);
            stringBuilder.append("0");
            strFormat = stringBuilder.toString();
        }
        StringBuilder strFormat2 = new StringBuilder();
        strFormat2.append(strFormat);
        strFormat2.append("}");
        strFormat = MessageFormat.format(strFormat2.toString(), new Object[]{Integer.valueOf(val)});
        if (strFormat.length() != length) {
            throw new RuntimeException("Failed to Write.");
        }
        byte[] _b = new byte[0];
        try {
            _b = strFormat.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            //LogUtil.e(e);
        }
        while (i < length) {
            setData[start + i] = Encryption(enc, _b[i]);
            i++;
        }
    }

    private void SetCng(byte[] setData, CngModel cng) {
        if (cng == null) {
            throw new RuntimeException("Cng");
        }
        String _s = "";
        switch (cng.LogDaysFlg) {
            case 0:
            case 1:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(_s);
                stringBuilder.append(String.valueOf(cng.LogDaysFlg));
                _s = stringBuilder.toString();
                switch (cng.IndexValueFlg) {
                    case 0:
                    case 1:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(_s);
                        stringBuilder.append(String.valueOf(cng.IndexValueFlg));
                        _s = stringBuilder.toString();
                        switch (cng.WeekControlFlg) {
                            case 0:
                            case 1:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(_s);
                                stringBuilder.append(String.valueOf(cng.WeekControlFlg));
                                _s = stringBuilder.toString();
                                switch (cng.WeekStartFlg) {
                                    case 0:
                                    case 1:
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append(String.valueOf(cng.WeekStartFlg));
                                        _s = stringBuilder.toString();
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append("0");
                                        _s = stringBuilder.toString();
                                        switch (cng.ClockTimeFlg) {
                                            case 0:
                                            case 1:
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append(String.valueOf(cng.ClockTimeFlg));
                                                _s = stringBuilder.toString();
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append("0");
                                                _s = stringBuilder.toString();
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append("0");
                                                _s = stringBuilder.toString();
                                                switch (cng.LogCountFlg) {
                                                    case 0:
                                                    case 1:
                                                        stringBuilder = new StringBuilder();
                                                        stringBuilder.append(_s);
                                                        stringBuilder.append(String.valueOf(cng.LogCountFlg));
                                                        _s = stringBuilder.toString();
                                                        switch (cng.LogIntervalFlg) {
                                                            case 0:
                                                            case 1:
                                                                stringBuilder = new StringBuilder();
                                                                stringBuilder.append(_s);
                                                                stringBuilder.append(String.valueOf(cng.LogIntervalFlg));
                                                                _s = stringBuilder.toString();
                                                                switch (cng.OpenCockFlg) {
                                                                    case 0:
                                                                    case 1:
                                                                        stringBuilder = new StringBuilder();
                                                                        stringBuilder.append(_s);
                                                                        stringBuilder.append(String.valueOf(cng.OpenCockFlg));
                                                                        _s = stringBuilder.toString();
                                                                        switch (cng.MaxFlowFlg) {
                                                                            case 0:
                                                                            case 1:
                                                                                stringBuilder = new StringBuilder();
                                                                                stringBuilder.append(_s);
                                                                                stringBuilder.append(String.valueOf(cng.MaxFlowFlg));
                                                                                _s = stringBuilder.toString();
                                                                                switch (cng.ContinueFlg2) {
                                                                                    case 0:
                                                                                    case 1:
                                                                                        stringBuilder = new StringBuilder();
                                                                                        stringBuilder.append(_s);
                                                                                        stringBuilder.append(String.valueOf(cng.ContinueFlg2));
                                                                                        _s = stringBuilder.toString();
                                                                                        switch (cng.ContinueFlg1) {
                                                                                            case 0:
                                                                                            case 1:
                                                                                                stringBuilder = new StringBuilder();
                                                                                                stringBuilder.append(_s);
                                                                                                stringBuilder.append(String.valueOf(cng.ContinueFlg1));
                                                                                                _s = stringBuilder.toString();
                                                                                                stringBuilder = new StringBuilder();
                                                                                                stringBuilder.append(_s);
                                                                                                stringBuilder.append("0");
                                                                                                _s = stringBuilder.toString();
                                                                                                stringBuilder = new StringBuilder();
                                                                                                stringBuilder.append(_s);
                                                                                                stringBuilder.append("0");
                                                                                                byte[] bArr = setData;
                                                                                                SetBitStringToByte(bArr, IsEncryption.Encrypt, stringBuilder.toString(), 14, 2);
                                                                                                return;
                                                                                            default:
                                                                                                throw new RuntimeException("ContinueFlg1");
                                                                                        }
                                                                                    default:
                                                                                        throw new RuntimeException("ContinueFlg2");
                                                                                }
                                                                            default:
                                                                                throw new RuntimeException("MaxFlowFlg");
                                                                        }
                                                                    default:
                                                                        throw new RuntimeException("OpenCockFlg");
                                                                }
                                                            default:
                                                                throw new RuntimeException("LogIntervalFlg");
                                                        }
                                                    default:
                                                        throw new RuntimeException("LogCountFlg");
                                                }
                                            default:
                                                throw new RuntimeException("ClockTimeFlg");
                                        }
                                    default:
                                        throw new RuntimeException("WeekStartFlg");
                                }
                            default:
                                throw new RuntimeException("WeekControlFlg");
                        }
                    default:
                        throw new RuntimeException("IndexValueFlg");
                }
            default:
                throw new RuntimeException("LogDaysFlg");
        }
    }

    private void SetContinue1(byte[] setData, ContinueModel continue1) {
        String _s = "";
        if (continue1.ContinueValue < 0 || 255 < continue1.ContinueValue) {
            throw new RuntimeException("ContinueValue1");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append(PadLeft(Integer.toBinaryString(continue1.ContinueValue), 8, '0'));
        _s = stringBuilder.toString();
        if (continue1.ContinueTime < 0 || 255 < continue1.ContinueTime) {
            throw new RuntimeException("ContinueTime1");
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append(PadLeft(Integer.toBinaryString(continue1.ContinueTime), 8, '0'));
        _s = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append("00000");
        _s = stringBuilder.toString();
        switch (continue1.ContinueFlg) {
            case 0:
            case 1:
                stringBuilder = new StringBuilder();
                stringBuilder.append(_s);
                stringBuilder.append(String.valueOf(continue1.ContinueFlg));
                _s = stringBuilder.toString();
                switch (continue1.ContinueCon) {
                    case 0:
                    case 1:
                    case 2:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(_s);
                        stringBuilder.append(PadLeft(Integer.toBinaryString(continue1.ContinueCon), 2, '0'));
                        byte[] bArr = setData;
                        SetBitStringToByte(bArr, IsEncryption.Encrypt, stringBuilder.toString(), 4, 3);
                        return;
                    default:
                        throw new RuntimeException("ContinueCon1");
                }
            default:
                throw new RuntimeException("ContinueFlg1");
        }
    }

    private void SetBitStringToByte(byte[] setData, IsEncryption enc, String val, int start, int length) {
        if (val.length() != 8 * length) {
            val = PadLeft(val, 8 * length, '0');
        }
        int j = 0;
        for (int i = 0; i < length; i++) {
            setData[start + i] = Encryption(enc, (byte) bin2int(val.substring(j, j + 8)));
            j += 8;
        }
    }

    private void SetContinue2(byte[] setData, ContinueModel continue2) {
        String _s = "";
        if (continue2.ContinueValue < 0 || 255 < continue2.ContinueValue) {
            throw new RuntimeException("ContinueValue2");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append(PadLeft(Integer.toBinaryString(continue2.ContinueValue), 8, '0'));
        _s = stringBuilder.toString();
        if (continue2.ContinueTime < 0 || 255 < continue2.ContinueTime) {
            throw new RuntimeException("ContinueTime2");
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append(PadLeft(Integer.toBinaryString(continue2.ContinueTime), 8, '0'));
        _s = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(_s);
        stringBuilder.append("00000");
        _s = stringBuilder.toString();
        switch (continue2.ContinueFlg) {
            case 0:
            case 1:
                stringBuilder = new StringBuilder();
                stringBuilder.append(_s);
                stringBuilder.append(String.valueOf(continue2.ContinueFlg));
                _s = stringBuilder.toString();
                switch (continue2.ContinueCon) {
                    case 0:
                    case 1:
                    case 2:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(_s);
                        stringBuilder.append(PadLeft(Integer.toBinaryString(continue2.ContinueCon), 2, '0'));
                        byte[] bArr = setData;
                        SetBitStringToByte(bArr, IsEncryption.Encrypt, stringBuilder.toString(), 7, 3);
                        return;
                    default:
                        throw new RuntimeException("ContinueCon2");
                }
            default:
                throw new RuntimeException("ContinueFlg2");
        }
    }

    private void SetCng2(byte[] setData, Cng2Model cng2) {
        if (cng2 == null) {
            throw new RuntimeException("Cng2");
        }
        String _s = "";
        switch (cng2.FlowDetectionFlg) {
            case 0:
            case 1:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(_s);
                stringBuilder.append(String.valueOf(cng2.FlowDetectionFlg));
                _s = stringBuilder.toString();
                switch (cng2.QuakeConFlg) {
                    case 0:
                    case 1:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(_s);
                        stringBuilder.append(String.valueOf(cng2.QuakeConFlg));
                        _s = stringBuilder.toString();
                        switch (cng2.ReductionConFlg) {
                            case 0:
                            case 1:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(_s);
                                stringBuilder.append(String.valueOf(cng2.ReductionConFlg));
                                _s = stringBuilder.toString();
                                switch (cng2.OpenCoverConFlg) {
                                    case 0:
                                    case 1:
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append(String.valueOf(cng2.OpenCoverConFlg));
                                        _s = stringBuilder.toString();
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append("0");
                                        _s = stringBuilder.toString();
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append("0");
                                        _s = stringBuilder.toString();
                                        switch (cng2.EmergencyValueFlg) {
                                            case 0:
                                            case 1:
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append(String.valueOf(cng2.EmergencyValueFlg));
                                                _s = stringBuilder.toString();
                                                switch (cng2.EmergencyConFlg) {
                                                    case 0:
                                                    case 1:
                                                        stringBuilder = new StringBuilder();
                                                        stringBuilder.append(_s);
                                                        stringBuilder.append(String.valueOf(cng2.EmergencyConFlg));
                                                        byte[] bArr = setData;
                                                        SetBitStringToByte(bArr, IsEncryption.Encrypt, stringBuilder.toString(), 0, 1);
                                                        return;
                                                    default:
                                                        throw new RuntimeException("EmergencyConFlg");
                                                }
                                            default:
                                                throw new RuntimeException("EmergencyValueFlg");
                                        }
                                    default:
                                        throw new RuntimeException("OpenCoverConFlg");
                                }
                            default:
                                throw new RuntimeException("ReductionConFlg");
                        }
                    default:
                        throw new RuntimeException("QuakeConFlg");
                }
            default:
                throw new RuntimeException("FlowDetectionFlg");
        }
    }

    private void SetPar(byte[] setData, ParModel par) {
        String _s = "";
        switch (par.QuakeCon) {
            case 0:
            case 1:
            case 2:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(_s);
                stringBuilder.append(PadLeft(Integer.toBinaryString(par.QuakeCon), 2, '0'));
                _s = stringBuilder.toString();
                switch (par.OpenCoverCon) {
                    case 0:
                    case 1:
                    case 2:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(_s);
                        stringBuilder.append(PadLeft(Integer.toBinaryString(par.OpenCoverCon), 2, '0'));
                        _s = stringBuilder.toString();
                        switch (par.FlowDetection) {
                            case 0:
                            case 1:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(_s);
                                stringBuilder.append(String.valueOf(par.FlowDetection));
                                _s = stringBuilder.toString();
                                switch (par.EmergencyCon) {
                                    case 0:
                                    case 1:
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append(_s);
                                        stringBuilder.append(String.valueOf(par.EmergencyCon));
                                        _s = stringBuilder.toString();
                                        switch (par.ReductionCon) {
                                            case 0:
                                            case 1:
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append(String.valueOf(par.ReductionCon));
                                                _s = stringBuilder.toString();
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append(_s);
                                                stringBuilder.append("0");
                                                byte[] bArr = setData;
                                                SetBitStringToByte(bArr, IsEncryption.Encrypt, stringBuilder.toString(), 1, 1);
                                                return;
                                            default:
                                                throw new RuntimeException("ReductionCon");
                                        }
                                    default:
                                        throw new RuntimeException("EmergencyCon");
                                }
                            default:
                                throw new RuntimeException("FlowDetection");
                        }
                    default:
                        throw new RuntimeException("OpenCoverCon");
                }
            default:
                throw new RuntimeException("QuakeCon");
        }
    }


    private void SetEmergencyValue(byte[] setData, double emergencyValue) {
        if (ValidDouble(emergencyValue, 3, 3)) {
            byte[] bArr = setData;
            SetBitStringToByte(bArr, IsEncryption.Encrypt, ToBCD(Long.valueOf(Math.round(1000.0d * emergencyValue)), 6), 4, 3);
            return;
        }
        throw new RuntimeException("EmergencyValue");
    }

    private void writeWithoutEncryption(NfcF nfc, BlockDataList writeData) throws IOException {
        int j = 0;
        Iterator it = writeData.dataList.iterator();
        while (it.hasNext()) {
            ReadBlockData Data = (ReadBlockData) it.next();
            try {
                CheckWriteResult(nfc.transceive(writeWithoutEncryption(TargetIDm, targetServiceCode, Data.ReadBlock, Data.ReadData)));
                j++;
            } catch (Exception e) {
                if (j >= 1) {
                    this.isChargeCheckFailed = true;
                }
                byte[] _byte = new byte[Data.ReadData.length];
                if (Data.ReadBlock <= 10) {
                    int i = 0;
                    if (Data.ReadBlock == 0) {
                        i = 2;
                        _byte[0] = Data.ReadData[0];
                        _byte[1] = Data.ReadData[1];
                    }
                    while (i < Data.ReadData.length) {
                        _byte[i] = Decryption(IsEncryption.Encrypt, Data.ReadData[i]);
                        i++;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception at writeWithoutEncryption.\n");
                stringBuilder.append(byteArraytoHexString(TargetIDm));
                stringBuilder.append("\n");
                stringBuilder.append(byteArraytoHexString(this.targetServiceCode));
                stringBuilder.append("\n");
                stringBuilder.append(Data.ReadBlock);
                stringBuilder.append("\n");
                stringBuilder.append(byteArraytoHexString(Data.ReadData));
                stringBuilder.append("\n");
                stringBuilder.append(byteArraytoHexString(_byte));
                stringBuilder.append("\ndataList length : ");
                stringBuilder.append(j);
                stringBuilder.append("\nStackTrace : ");
//                stringBuilder.append(LogUtil.Output(e.getStackTrace()));
//                LogUtil.i(stringBuilder.toString());
                throw e;
            }
        }
    }


    private void CheckWriteResult(byte[] res) {
        if (res[10] != (byte) 0) {
            RuntimeException runtimeException = new RuntimeException("Write Without Encryption Command Error");
        }
    }

    private boolean CheckWroteData(@NonNull BlockDataList dataList, @NonNull NfcF nfc, @NonNull CngModel tempCngModel, @NonNull ContinueModel tempContinueModel, @NonNull ContinueModel tempContinueModel2, @NonNull Cng2Model tempCng2Model, @NonNull ParModel tempParModel, double emergencyValue) {
        Exception e;

        BlockDataList type = dataList;
        CngModel cngModel = tempCngModel;
        ContinueModel continueModel = tempContinueModel;
        ContinueModel continueModel2 = tempContinueModel2;
        Cng2Model cng2Model = tempCng2Model;
        ParModel parModel = tempParModel;
        double d = emergencyValue;
        String type2 = "";
        boolean flag = false;
        int i = 0;
        while (i < type.dataList.size()) {
            String type3;
            try {
                type3 = type2;
                StringBuilder stringBuilder;
                try {
                    byte[] res = nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, ((ReadBlockData) type.dataList.get(i)).ReadBlock));
                    byte[][] data2 = parse(res);
                    CheckDataLength(data2);
                    String type4;
                    boolean flag2;
                    switch (i) {
                        case 0:
                            break;
                        case 1:
                            flag = true;
                            break;
                        case 2:
                            if (!GetCng(data2[0]).Equals(cngModel)) {
                                flag = false;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(tempCngModel.getClass().toString());
                                stringBuilder.append(" : ");
                                stringBuilder.append(cngModel);
                                type4 = stringBuilder.toString();
                                break;
                            }
                        case 3:
                            if (!GetContinue1(data2[0]).Equals(continueModel)) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(tempContinueModel.getClass().toString());
                                stringBuilder.append(" : ");
                                stringBuilder.append(continueModel);
                                flag = false;
                                type3 = stringBuilder.toString();
                            }
                            if (!GetContinue2(data2[0]).Equals(continueModel2)) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(tempContinueModel2.getClass().toString());
                                stringBuilder.append(" : ");
                                stringBuilder.append(continueModel2);
                                type4 = stringBuilder.toString();
                                flag2 = false;
                                break;
                            }
                            type4 = type3;
                            break;
                        case 4:
                            if (!GetCng2(data2[0]).Equals(cng2Model)) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(tempCng2Model.getClass().toString());
                                stringBuilder.append(" : ");
                                stringBuilder.append(cng2Model);
                                flag = false;
                                type3 = stringBuilder.toString();
                            }
                            if (!GetPar(data2[0]).Equals(parModel)) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(tempParModel.getClass().toString());
                                stringBuilder.append(" : ");
                                stringBuilder.append(parModel);
                                type3 = stringBuilder.toString();
                                flag = false;
                            }
                            if (GetEmergencyValue(data2[0]) != d) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("emergencyValue : ");
                                stringBuilder.append(d);
                                type4 = stringBuilder.toString();
                                flag2 = false;
                                break;
                            }
                            type4 = type3;
                            break;
                        default:
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("default : ");
                            stringBuilder.append(i);
                            type4 = stringBuilder.toString();
                            flag2 = false;
                            break;
                    }
                    i++;
                } catch (Exception e2) {
                    e = e2;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Exception at CheckWroteData!\nStackTrace : ");
//                    stringBuilder.append(//LogUtil.Output(e.getStackTrace()));
                    //LogUtil.i(stringBuilder.toString());
                    return false;
                }
            } catch (Exception e22) {
                type3 = type2;
                e = e22;
            }
        }
        return true;
    }

    private static void ReturnLocale() {
        try {
            Locale.setDefault(Common.getLocal(SettingData.SettingLang));
        } catch (Exception e) {
//            LogUtil.i("Occured in ReturnLocale()");
        }
    }

    private <T> String ToBCD(T num, int len) {
        long _val = new Long(num.toString()).longValue();
        String _str = "";
        for (int i = 1; i <= len; i++) {
            long _mod = _val % 10;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PadLeft(Long.toBinaryString(_mod), 4, '0'));
            stringBuilder.append(_str);
            _str = stringBuilder.toString();
            _val = (_val - _mod) / 10;
        }
        return _str;
    }

    private String byteArraytoHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for (byte b : byteArray) {
            sb.append(Integer.toHexString(255 & b));
        }
        return sb.toString();
    }

    private byte[] writeWithoutEncryption(byte[] idm, byte[] serviceCode, int blockNumber, byte[] data) throws TagLostException, IOException {
        if (idm == null || idm.length == 0) {
            throw new IllegalArgumentException();
        }
        ByteArrayOutputStream bout = new ByteArrayOutputStream(100);
        bout.write(0);
        bout.write(8);
        bout.write(idm);
        bout.write(1);
        bout.write(serviceCode[1]);
        bout.write(serviceCode[0]);
        bout.write(1);
        for (int i = 0; i < 1; i++) {
            bout.write(128);
            bout.write(blockNumber + i);
        }
        for (int i2 = 0; i2 < 16; i2++) {
            bout.write(data[i2]);
        }
        byte[] msg = bout.toByteArray();
        msg[0] = (byte) msg.length;
        return msg;
    }

    private CngModel GetCng(byte[] getData) {
        CngModel Result = new CngModel();
        String _s = GetByteToBitString(getData, IsEncryption.Encrypt, 14, 2);
        Result.LogDaysFlg = Integer.parseInt(_s.substring(0, 1));
        Result.IndexValueFlg = Integer.parseInt(_s.substring(1, 2));
        Result.WeekControlFlg = Integer.parseInt(_s.substring(2, 3));
        Result.WeekStartFlg = Integer.parseInt(_s.substring(3, 4));
        Result.ClockTimeFlg = Integer.parseInt(_s.substring(5, 6));
        Result.LogCountFlg = Integer.parseInt(_s.substring(8, 9));
        Result.LogIntervalFlg = Integer.parseInt(_s.substring(9, 10));
        Result.OpenCockFlg = Integer.parseInt(_s.substring(10, 11));
        Result.MaxFlowFlg = Integer.parseInt(_s.substring(11, 12));
        Result.ContinueFlg2 = Integer.parseInt(_s.substring(12, 13));
        Result.ContinueFlg1 = Integer.parseInt(_s.substring(13, 14));
        return Result;
    }

    private ContinueModel GetContinue1(byte[] getData) {
        ContinueModel Result = new ContinueModel();
        String _s = GetByteToBitString(getData, IsEncryption.Encrypt, 4, 3);
        Result.ContinueValue = bin2int(_s.substring(0, 8));
        Result.ContinueTime = bin2int(_s.substring(8, 16));
        Result.ContinueFlg = Integer.parseInt(_s.substring(21, 22));
        Result.ContinueCon = bin2int(_s.substring(22, 24));
        return Result;
    }

    private Cng2Model GetCng2(byte[] getData) {
        Cng2Model Result = new Cng2Model();
        String _s = GetByteToBitString(getData, IsEncryption.Encrypt, 0, 1);
        Result.FlowDetectionFlg = Integer.parseInt(_s.substring(0, 1));
        Result.QuakeConFlg = Integer.parseInt(_s.substring(1, 2));
        Result.ReductionConFlg = Integer.parseInt(_s.substring(2, 3));
        Result.OpenCoverConFlg = Integer.parseInt(_s.substring(3, 4));
        Result.EmergencyValueFlg = Integer.parseInt(_s.substring(6, 7));
        Result.EmergencyConFlg = Integer.parseInt(_s.substring(7, 8));
        return Result;
    }

    private ContinueModel GetContinue2(byte[] getData) {
        ContinueModel Result = new ContinueModel();
        String _s = GetByteToBitString(getData, IsEncryption.Encrypt, 7, 3);
        Result.ContinueValue = bin2int(_s.substring(0, 8));
        Result.ContinueTime = bin2int(_s.substring(8, 16));
        Result.ContinueFlg = Integer.parseInt(_s.substring(21, 22));
        Result.ContinueCon = bin2int(_s.substring(22, 24));
        return Result;
    }

    private ParModel GetPar(byte[] getData) {
        ParModel Result = new ParModel();
        String _s = GetByteToBitString(getData, IsEncryption.Encrypt, 1, 1);
        Result.QuakeCon = bin2int(_s.substring(0, 2));
        switch (Result.QuakeCon) {
            case 0:
            case 1:
            case 2:
                Result.OpenCoverCon = bin2int(_s.substring(2, 4));
                switch (Result.QuakeCon) {
                    case 0:
                    case 1:
                    case 2:
                        Result.FlowDetection = Integer.parseInt(_s.substring(4, 5));
                        Result.EmergencyCon = Integer.parseInt(_s.substring(5, 6));
                        Result.ReductionCon = Integer.parseInt(_s.substring(6, 7));
                        return Result;
                    default:
                        throw new RuntimeException("ReductionCon");
                }
            default:
                throw new RuntimeException("ReductionCon");
        }
    }

    private double GetEmergencyValue(byte[] getData) {
        return ((double) BCDTo(GetByteToBitString(getData, IsEncryption.Encrypt, 4, 3))) / 1000.0d;
    }

    public boolean WriteStatus(Tag tag, int CardHistoryNo) {
        NfcF nfc = NfcF.get(tag);
        try {
            nfc.connect();
            byte[][] data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, this.size, this.targetServiceCode, 3)));
            CheckDataLength(data);
            BlockDataList dataList = new BlockDataList();
            dataList.AddReadBlockData(data[0], 3, false);
            SetCardStatus(data[0], 21);
            data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, this.size, this.targetServiceCode, 5)));
            CheckDataLength(data);
            dataList.AddReadBlockData(data[0], 5, false);
            SetCardHistoryNo(data[0], CardHistoryNo);
            writeWithoutEncryption(nfc, dataList);
            data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, this.size, this.targetServiceCode, 3)));
            CheckDataLength(data);
            StringBuilder stringBuilder;
            if (GetCardStatus(data[0]) != Ascii.NAK) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("At CardStatus checking.\nCardId : ");
                stringBuilder.append(this.strCardId);
                stringBuilder.append("\nStatus : ");
                stringBuilder.append(data[0]);
                stringBuilder.append("\n");
                //LogUtil.i(stringBuilder.toString());
                if (nfc != null) {
                    try {
                        nfc.close();
                    } catch (IOException ex) {
                        //LogUtil.i(ex.toString());
                    }
                }
                ReturnLocale();
                return false;
            }
            data = parse(nfc.transceive(readWithoutEncryption(TargetIDm, this.size, this.targetServiceCode, 5)));
            CheckDataLength(data);
            if (GetCardHistoryNo(data[0]) != CardHistoryNo) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("At CardHistoryNo checking.\nCardId : ");
                stringBuilder.append(this.strCardId);
                stringBuilder.append("\nHistoryNo : ");
                stringBuilder.append(CardHistoryNo);
                stringBuilder.append("\n");
                //LogUtil.i(stringBuilder.toString());
                if (nfc != null) {
                    try {
                        nfc.close();
                    } catch (IOException ex2) {
                        //LogUtil.i(ex2.toString());
                    }
                }
                ReturnLocale();
                return false;
            }
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex22) {
                    //LogUtil.i(ex22.toString());
                }
            }
            ReturnLocale();
            return true;
        } catch (Exception e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Exception at WriteStatus.\n");
            stringBuilder2.append(this.strCardId);
            stringBuilder2.append("\n");
//            stringBuilder2.append(//LogUtil.Output(e.getStackTrace()));
            //LogUtil.i(stringBuilder2.toString());
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex3) {
                    //LogUtil.i(ex3.toString());
                }
            }
            ReturnLocale();
            return false;
        } catch (Throwable th) {
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex4) {
                    //LogUtil.i(ex4.toString());
                }
            }
            ReturnLocale();
            return false;
        }
    }

    private void SetCardStatus(byte[] setData, int cardStatus) {
        if (!(cardStatus == 0 || cardStatus == 21 || cardStatus == 48)) {
            switch (cardStatus) {
                case 5:
                case 6:
                    break;
                default:
                    throw new RuntimeException("SetCardStatus Out Of Range");
            }
        }
        byte[] bArr = setData;
        SetHexStringToByte(bArr, IsEncryption.Encrypt, PadLeft(Integer.toHexString(cardStatus), 2, '0'), 0, 1);
    }

    private void SetCardHistoryNo(byte[] setData, int cardHistoryNo) {
        if (cardHistoryNo < 0 || SupportMenu.USER_MASK < cardHistoryNo) {
            throw new RuntimeException("CardHistoryNo");
        }
        byte[] bArr = setData;
        SetBitStringToByte(bArr, IsEncryption.Encrypt, Integer.toBinaryString(cardHistoryNo), 4, 2);
    }


    public String GetHistoryNo(Tag tag) {
        NfcF nfc = NfcF.get(tag);
        try {

            nfc.connect();
            BlockDataList datalist = new BlockDataList();
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, this.size, this.targetServiceCode, 5)))[0], 5, true);
            String valueOf = String.valueOf(GetCardHistoryNo(datalist.GetReadBlockData(5)));
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex) {
//                    LogUtil.i(ex.toString());
                }
            }
            ReturnLocale();
            return valueOf;
        } catch (Exception e) {
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex2) {
//                    LogUtil.i(ex2.toString());
                }
            }
            ReturnLocale();
            return null;
        } catch (Throwable th) {
            if (nfc != null) {
                try {
                    nfc.close();
                } catch (IOException ex3) {
//                    LogUtil.i(ex3.toString());
                }
            }
            ReturnLocale();
            return null;
        }
    }

    private int GetCardHistoryNo(byte[] getData) {
        return Integer.parseInt(GetByteToBitString(getData, IsEncryption.Encrypt, 4, 2), 2);
    }

    private void SetHexStringToByte(byte[] setData, IsEncryption enc, String val, int start, int length) {
        val = PadLeft(val, length * 2, '0');
        int j = 0;
        for (int i = 0; i < length; i++) {
            setData[start + i] = Encryption(enc, (byte) hex2int(val.substring(j, j + 2)));
            j += 2;
        }
    }

    private int GetErrorNo(byte[] getData) {
        return GetByteToInt(getData, IsEncryption.Encrypt, 6, 2);
    }

    private int GetOpenCount(byte[] getData) {
        return bin2int(GetByteToBitString(getData, IsEncryption.Encrypt, 1, 2));
    }

    private String GetWebApiDate(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(calendar.getTime()).replace("1970", "0001");
    }

    private Calendar GetLidTime(byte[] getData) {
        Calendar Result = Calendar.getInstance();
        String _ymd = String.format("%010d", new Object[]{BCDToLong(GetByteToBitString(getData, IsEncryption.Encrypt, 9, 5).substring(0, 40))});
        if (_ymd.equals("0000000000")) {
            Result.clear();
        } else {
            try {
                Result.setTime(new SimpleDateFormat("yyMMddHHmm").parse(_ymd));
            } catch (ParseException e) {
                throw new RuntimeException("ClockTimeFlg");
            }
        }
        return Result;
    }

    private Long BCDToLong(String bcd) {
        if (bcd.length() % 4 > 0) {
            throw new RuntimeException("BCDTo Out Of Range");
        }
        String _s = "";
        int _len = bcd.length() / 4;
        for (int i = 0; i < _len; i++) {
            int start = i * 4;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(_s);
            stringBuilder.append(String.valueOf(bin2int(bcd.substring(start, start + 4))));
            _s = stringBuilder.toString();
        }
        return Long.valueOf(Long.parseLong(_s));
    }

    private double GetIndexValue(byte[] getData) {
        return ((double) BCDTo(GetByteToBitString(getData, IsEncryption.Encrypt, 0, 4))) / 1000.0d;
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

    private static String getLocalDateFormat(Locale locale) {
        String result = "";
        String language = locale.getLanguage();
        if (language.hashCode() == 3241 && language.equals("en")) {
        }
        return "MMM d, yyyy HH:mm a";
    }



}
