package com.example.root.officeapp.nfcfelica;

import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;


import static com.example.root.officeapp.lang.NumberUtils.TAG;


public class NfcReader {
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
            byte []  TargetIDm  =  Arrays. copyOfRange ( PollingRes ,  2 ,  10 );

            // the size of the data contained in the service (this time it was 4)
            int  size  =  1 ;

            // target service code -> 0x1A8B
            byte []  targetServiceCode  =  new byte[]{(byte) 0, (byte) 9};

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



            BlockDataList datalist = new BlockDataList();
            datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 1)))[0], 1, true);
             datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 2)))[0], 2, true);
             datalist.AddReadBlockData(parse(nfc.transceive(readWithoutEncryption(TargetIDm, size, targetServiceCode, 3)))[0], 3, true);

             byte byCardGroup;
             byte byCardStatus;



             String strCustomerId;




             try {
                 byCardGroup = (byte) 0;
                 byCardStatus = (byte) 0;
                 strCustomerId = "";
                 byCardStatus = GetCardStatus(datalist.GetReadBlockData(3));
//                 String CardStatus = String.valueOf(GetCardStatus(datalist.GetReadBlockData(3)));
                 System.out.print(String.valueOf(byCardStatus));
    //             byCardGroup = GetCardGroup(datalist.GetReadBlockData(2));
                strCustomerId = GetCustomerId(datalist.GetReadBlockData(1), datalist.GetReadBlockData(2));
                 System.out.print(strCustomerId);
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
                        this.dataList.add(new ReadBlockData(Data, Block));
                        return;
                    }
                    return;
                }
            }
            if (found) {
            }
            this.dataList.add(new ReadBlockData(Data, Block));
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
            ReadBlockData addData = new ReadBlockData(null, Block);
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
        String cardStatus = GetByteToHexString(getData, IsEncryption.Encrypt, 0, 1);
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
                            RuntimeException runtimeException = new RuntimeException("InvalidProgramException CardStatus");
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

    private String GetByteToHexString(byte[] getData, IsEncryption enc, int start, int length) {
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

    private byte Decryption(IsEncryption enc, byte input) {
        if (enc == IsEncryption.NotEncrypt) {
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
                _b[i] = Decryption(IsEncryption.Encrypt, getData1[i]);
            }
            for (i = 0; i < 4; i++) {
                _b[16 + i] = Decryption(IsEncryption.Encrypt, getData2[i]);
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

}
