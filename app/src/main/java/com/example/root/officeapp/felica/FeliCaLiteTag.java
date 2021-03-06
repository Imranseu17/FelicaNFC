/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.root.officeapp.felica;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.root.officeapp.felica.command.PollingResponse;
import com.example.root.officeapp.felica.command.ReadResponse;
import com.example.root.officeapp.felica.command.WriteResponse;
import com.example.root.officeapp.felica.lib.FeliCaLib;
import com.example.root.officeapp.nfc.NfcTag;

import java.nio.ByteBuffer;

import static com.example.root.officeapp.felica.lib.FeliCaLib.COMMAND_POLLING;
import static com.example.root.officeapp.felica.lib.FeliCaLib.COMMAND_READ_WO_ENCRYPTION;
import static com.example.root.officeapp.felica.lib.FeliCaLib.COMMAND_WRITE_WO_ENCRYPTION;
import static com.example.root.officeapp.felica.lib.FeliCaLib.SERVICE_FELICA_LITE_READONLY;
import static com.example.root.officeapp.felica.lib.FeliCaLib.SERVICE_FELICA_LITE_READWRITE;
import static com.example.root.officeapp.felica.lib.FeliCaLib.SYSTEMCODE_FELICA_LITE;

/**
 * FeliCa Lite仕様に準拠したFeliCa Liteタグクラスを提供します
 * 
 * @author Kazzz
 * @date 2011/01/23
 * @since Android API Level 9
 *
 */

public class FeliCaLiteTag extends NfcTag {
    /** Parcelable need CREATOR field **/ 
    public static final Parcelable.Creator<FeliCaLiteTag> CREATOR = 
        new Parcelable.Creator<FeliCaLiteTag>() {
            public FeliCaLiteTag createFromParcel(Parcel in) {
                return new FeliCaLiteTag(in);
            }
            
            public FeliCaLiteTag[] newArray(int size) {
                return new FeliCaLiteTag[size];
            }
        };

    protected Parcelable nfcTag;
    protected FeliCaLib.IDm idm;
    protected FeliCaLib.PMm pmm;
    /**
     * コンストラクタ
     * @param in 入力するパーセル化オブジェクトをセット
     */
    public FeliCaLiteTag(Parcel in) {
        this.readFromParcel(in);
    }

    public FeliCaLiteTag(Parcelable nfcTag) {
        this.nfcTag =  nfcTag;
    }

    public FeliCaLiteTag(Parcelable nfcTag, FeliCaLib.IDm idm, FeliCaLib.PMm pmm) {
        this.nfcTag =  nfcTag;
        this.idm = idm;
        this.pmm = pmm;
    }
    
    
    /* (non-Javadoc)
     * @see net.kazzz.nfc.NfcTag#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.nfcTag, 0);
        dest.writeParcelable(this.idm, 0);
        dest.writeParcelable(this.pmm, 0);
    }
    /* (non-Javadoc)
     * @see net.kazzz.nfc.NfcTag#readFromParcel(android.os.Parcel)
     */
    @Override
    public void readFromParcel(Parcel source) {
        ClassLoader cl = this.getClass().getClassLoader();
        this.nfcTag = source.readParcelable(cl);
        this.idm = source.readParcelable(cl);
        this.pmm = source.readParcelable(cl);
    }
    /**
     * カードデータをポーリングします
     * 
     * @return　byte[] システムコードの配列が戻ります
     * @throws FeliCaException
     */
    public byte[] polling() throws FeliCaException {
        if ( this.nfcTag == null ) {
            throw new FeliCaException("tagService is null. no polling execution");
        }
        FeliCaLib.CommandPacket polling =
            new FeliCaLib.CommandPacket(COMMAND_POLLING, new byte[] {
                      (byte) (SYSTEMCODE_FELICA_LITE >> 8)  // システムコード
                    , (byte) (SYSTEMCODE_FELICA_LITE & 0xff)
                    , (byte) 0x01                           //　システムコードリクエスト
                    , (byte) 0x00});                        // タイムスロット}; 
        FeliCaLib.CommandResponse r = FeliCaLib.execute(this.nfcTag, polling);
        PollingResponse pr = new PollingResponse(r);
        this.idm = pr.getIDm();
        this.pmm = pr.getPMm();
        return pr.getBytes();
    }

    public FeliCaLib.IDm pollingAndGetIDm() throws FeliCaException {
        this.polling();
        return this.idm;
    }
    /**
     * FeliCa IDmを取得します
     * @return IDm IDmが戻ります
     * @throws FeliCaException
     */
    public FeliCaLib.IDm getIDm() throws FeliCaException {
        return this.idm;
    }
    /**
     * FeliCa PMmを取得します
     * @return PMm PMmが戻ります
     * @throws FeliCaException
     */
    public FeliCaLib.PMm getPMm() throws FeliCaException {
        return this.pmm;
    }
    /**
     * メモリコンフィグィグレーションブロック(ブロック番号:0x88h)を取得します
     * 
     * @return MemoryConfigurationBlock 取得したメモリコンフィグィグレーションブロックが戻ります
     * @throws FeliCaException
     */
    public FeliCaLib.MemoryConfigurationBlock getMemoryConfigBlock() throws FeliCaException {
        ReadResponse r = this.readWithoutEncryption((byte)0x88); //ブロック88hはMC領域
        return ( r != null ) 
            ? new FeliCaLib.MemoryConfigurationBlock(r.getBlockData()) : null;
    }
    
    /**
     * 認証不要領域のデータを読み込みます
     * 
     * @param addr 読み込むブロックのアドレス (0オリジン)をセット
     * @return ReadResponse デバイスからの読み込んだレスポンスが戻ります
     * @throws FeliCaException
     */
    public ReadResponse readWithoutEncryption(byte addr) throws FeliCaException {
        if ( this.nfcTag == null ) {
            throw new FeliCaException("tagService is null. no read execution");
        }
        // read without encryption
        FeliCaLib.CommandPacket readWoEncrypt =
            new FeliCaLib.CommandPacket(COMMAND_READ_WO_ENCRYPTION, idm, new byte[]{
                      (byte) 0x01                                 // サービス数
                    , (byte) (SERVICE_FELICA_LITE_READONLY >> 8)  //サービスコード : リードオンリー
                    , (byte) (SERVICE_FELICA_LITE_READONLY & 0xff)
                    , (byte) 0x01                 // 同時読み込みブロック数
                    , (byte) 0x80, addr });       // ブロックリスト
        FeliCaLib.CommandResponse r = FeliCaLib.execute(this.nfcTag, readWoEncrypt);
        return new ReadResponse(r); 
    }
    /**
     * 認証不要領域のデータを書き込みます
     * 
     * @param addr データをセットするブロックのアドレス(0オリジン)をセット
     * @param buff 書きこむデータをセット (16バイト)
     * @return WriteResponse 書き込んだ結果のレスポンスオブジェクトが戻ります (書き込みに失敗した場合-1が戻ります)
     * @throws FeliCaException
     */
    public WriteResponse writeWithoutEncryption(byte addr, byte[] buff) throws FeliCaException {
        if ( this.nfcTag == null ) {
            throw new FeliCaException("tagService is null. no write execution");
        }
        // write without encryption
        ByteBuffer b =  ByteBuffer.allocate(22); // コマンド 6バイト + 書きだすデータ 16バイト
        b.put(new byte[]{
                  (byte) 0x01                                  // Number of Service
                , (byte) (SERVICE_FELICA_LITE_READWRITE >> 8)  //サービスコード: リード/ライト
                , (byte) (SERVICE_FELICA_LITE_READWRITE & 0xff)
                , (byte) 0x01                                  // 同時書き込みブロック数
                , (byte) 0x80, addr                            // ブロックリスト 0x80は (2バイトブロックエレメント+ランダムサービス)
                });
        b.put(buff, 0, buff.length > 16 ? 16 : buff.length); //書き出すデータ  (一度につき16バイト)
        
        FeliCaLib.CommandPacket writeWoEncrypt =
            new FeliCaLib.CommandPacket(COMMAND_WRITE_WO_ENCRYPTION, idm, b.array());
        FeliCaLib.CommandResponse r = FeliCaLib.execute(this.nfcTag, writeWoEncrypt);
        return new WriteResponse(r);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("FeliCaLiteTag \n");
       if ( this.idm != null ) 
           sb.append(this.idm.toString()).append("\n");
       if ( this.pmm != null ) 
           sb.append(this.pmm.toString()).append("\n");
       return sb.toString();
    }
}
