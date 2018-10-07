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
package com.example.root.officeapp.felica.command;

import com.example.root.officeapp.felica.lib.FeliCaLib;
import com.example.root.officeapp.felica.lib.Util;

import java.util.Arrays;

/**
 * Pollingコマンドのレスポンスを抽象化したクラスを提供します
 * 
 * @author Kazzz
 * @date 2011/01/22
 * @since Android API Level 9
 *
 */

public class PollingResponse extends FeliCaLib.CommandResponse {
    final FeliCaLib.PMm pmm;
    final byte[] requestData; 

    public PollingResponse(FeliCaLib.CommandResponse response) {
        super(response);
        if ( this.data != null && this.data.length >= 8 ) {
            this.pmm = new FeliCaLib.PMm(Arrays.copyOfRange(this.data, 0, 8));
            this.requestData = Arrays.copyOfRange(this.data, 8, data.length);
        } else {
            this.pmm = null;
            this.requestData = null;
        }
    }
    /**
     * PMmを取得します
     * 
     * @return PMm pmmが戻ります
     */
    public FeliCaLib.PMm getPMm() {
        return this.pmm;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FeliCa レスポンス　パケット \n");
        sb.append(" コマンド名 : " + FeliCaLib.commandMap.get(this.responseCode)  +  "\n");
        sb.append(" データ長 : " + this.length + "\n");
        sb.append(" コマンドコード : " + Util.getHexString(this.responseCode) +  "\n");
        if ( this.idm != null )
            sb.append(" " + this.idm.toString() + "\n");
        if ( this.pmm != null )
            sb.append(" " + this.pmm.toString() + "\n");
        sb.append(" データ: " + Util.getHexString(this.data) + "\n");
        return sb.toString();
    }
}
