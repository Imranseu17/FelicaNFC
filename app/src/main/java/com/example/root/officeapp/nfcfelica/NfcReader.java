package com.example.root.officeapp.nfcfelica;

import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static com.example.root.officeapp.lang.NumberUtils.TAG;


public class NfcReader {

    public  byte [] []  ReadTag ( Tag tag )  {
        NfcF nfc  =  NfcF . get ( tag );
         try  {
            nfc .  connect ();
            // System 1 System Code -> 0xFE00
            byte []   TargetSystemCode  =   new  byte [] { ( byte )  0xfe , ( byte )  0x00 };

            // create polling command
            byte []  polling  =  polling ( TargetSystemCode );

            // get the result by sending a command
            byte []  PollingRes  =  nfc . transceive ( polling );

            // Get the IDm of System 0 (1 byte data size, 2 byte response code, the size of the IDm is 8 bytes)
            byte []  TargetIDm  =  Arrays. copyOfRange ( PollingRes ,  2 ,  10 );

            // the size of the data contained in the service (this time it was 4)
            int  size  =  4 ;

            // target service code -> 0x1A8B
            byte []  targetServiceCode  =  new  byte [] {( byte )  0x1A ,  ( byte )  0x8B };

            // Create Read Without Encryption command
            byte []  req  =  readWithoutEncryption ( TargetIDm ,  size ,  targetServiceCode );

            // get the result by sending a command
            byte []  res  =  nfc . transceive ( req );

            nfc . close ();

            // parse the results data only get
            return  parse ( res );
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
}
