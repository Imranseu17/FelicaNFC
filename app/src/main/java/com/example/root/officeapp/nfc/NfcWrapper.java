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
package com.example.root.officeapp.nfc;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Nfcの隠し(@hide)機能を使うためのリフレクション機能をラップするラッパークラスを提供します
 * 
 * @author Kazzz
 * @date 2011/02/19
 * @since Android API Level 9
 *
 */

public class NfcWrapper {
    static final String TAG = "NfcWrapper";
    private NfcWrapper() {}
    
    /**
     * タグがNDEFフォーマッか否かを検査します
     * @param tag インテントから取得したタグ(ndroid.nfc.Tag)をセット
     * @return boolean タグがNDefフォーマットの場合はtrueが戻ります
     * @throws NfcException
     */
    public static final boolean isNdef(Parcelable tag) throws NfcException {
        return (Boolean) invokeNfcTagMethod("isNdef", tag);
    }
    /**
     * NDEFメッセージを取得します
     * @param tag インテントから取得したタグ(ndroid.nfc.Tag)をセット
     * @return NdefMessage 取得したNdefメッセージが戻ります
     * @throws NfcException
     */
    public static final NdefMessage read(Parcelable tag) throws NfcException {
        Object result = invokeNfcTagMethod("read", tag);
        return result != null ? (NdefMessage) result : null;
    }
    /**
     * NDEFメッセージを書き込みます
     * @param tag インテントから取得したタグ(ndroid.nfc.Tag)をセット
     * @param msg 書き込みするNdefMessageをセット
     * @return int 書き込んだ長さが戻ります (書きこみに失敗した場合は-1が戻ります
     * @throws NfcException
     */
    public static final int write(Parcelable tag, NdefMessage msg) throws NfcException {
        return (Integer) invokeNfcTagMethod("write", tag, msg);
    }

    public static final byte[] transceive(Parcelable tag, byte[] data) throws NfcException {
        return (byte[]) invokeNfcTagMethod("transceive", tag, data);
    }

    public static final Object invokeNfcTagMethod(String methodName, Parcelable tag, Object... params) throws NfcException {
        try {
            Context context = new Context() {
                @Override
                public AssetManager getAssets() {
                    return null;
                }

                @Override
                public Resources getResources() {
                    return null;
                }

                @Override
                public PackageManager getPackageManager() {
                    return null;
                }

                @Override
                public ContentResolver getContentResolver() {
                    return null;
                }

                @Override
                public Looper getMainLooper() {
                    return null;
                }

                @Override
                public Context getApplicationContext() {
                    return null;
                }

                @Override
                public void setTheme(int resid) {

                }

                @Override
                public Resources.Theme getTheme() {
                    return null;
                }

                @Override
                public ClassLoader getClassLoader() {
                    return null;
                }

                @Override
                public String getPackageName() {
                    return null;
                }

                @Override
                public ApplicationInfo getApplicationInfo() {
                    return null;
                }

                @Override
                public String getPackageResourcePath() {
                    return null;
                }

                @Override
                public String getPackageCodePath() {
                    return null;
                }

                @Override
                public SharedPreferences getSharedPreferences(String name, int mode) {
                    return null;
                }

                @Override
                public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
                    return false;
                }

                @Override
                public boolean deleteSharedPreferences(String name) {
                    return false;
                }

                @Override
                public FileInputStream openFileInput(String name) throws FileNotFoundException {
                    return null;
                }

                @Override
                public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
                    return null;
                }

                @Override
                public boolean deleteFile(String name) {
                    return false;
                }

                @Override
                public File getFileStreamPath(String name) {
                    return null;
                }

                @Override
                public File getDataDir() {
                    return null;
                }

                @Override
                public File getFilesDir() {
                    return null;
                }

                @Override
                public File getNoBackupFilesDir() {
                    return null;
                }

                @Nullable
                @Override
                public File getExternalFilesDir(@Nullable String type) {
                    return null;
                }

                @Override
                public File[] getExternalFilesDirs(String type) {
                    return new File[0];
                }

                @Override
                public File getObbDir() {
                    return null;
                }

                @Override
                public File[] getObbDirs() {
                    return new File[0];
                }

                @Override
                public File getCacheDir() {
                    return null;
                }

                @Override
                public File getCodeCacheDir() {
                    return null;
                }

                @Nullable
                @Override
                public File getExternalCacheDir() {
                    return null;
                }

                @Override
                public File[] getExternalCacheDirs() {
                    return new File[0];
                }

                @Override
                public File[] getExternalMediaDirs() {
                    return new File[0];
                }

                @Override
                public String[] fileList() {
                    return new String[0];
                }

                @Override
                public File getDir(String name, int mode) {
                    return null;
                }

                @Override
                public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
                    return null;
                }

                @Override
                public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
                    return null;
                }

                @Override
                public boolean moveDatabaseFrom(Context sourceContext, String name) {
                    return false;
                }

                @Override
                public boolean deleteDatabase(String name) {
                    return false;
                }

                @Override
                public File getDatabasePath(String name) {
                    return null;
                }

                @Override
                public String[] databaseList() {
                    return new String[0];
                }

                @Override
                public Drawable getWallpaper() {
                    return null;
                }

                @Override
                public Drawable peekWallpaper() {
                    return null;
                }

                @Override
                public int getWallpaperDesiredMinimumWidth() {
                    return 0;
                }

                @Override
                public int getWallpaperDesiredMinimumHeight() {
                    return 0;
                }

                @Override
                public void setWallpaper(Bitmap bitmap) throws IOException {

                }

                @Override
                public void setWallpaper(InputStream data) throws IOException {

                }

                @Override
                public void clearWallpaper() throws IOException {

                }

                @Override
                public void startActivity(Intent intent) {

                }

                @Override
                public void startActivity(Intent intent, @Nullable Bundle options) {

                }

                @Override
                public void startActivities(Intent[] intents) {

                }

                @Override
                public void startActivities(Intent[] intents, Bundle options) {

                }

                @Override
                public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {

                }

                @Override
                public void startIntentSender(IntentSender intent, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws IntentSender.SendIntentException {

                }

                @Override
                public void sendBroadcast(Intent intent) {

                }

                @Override
                public void sendBroadcast(Intent intent, @Nullable String receiverPermission) {

                }

                @Override
                public void sendOrderedBroadcast(Intent intent, @Nullable String receiverPermission) {

                }

                @Override
                public void sendOrderedBroadcast(@NonNull Intent intent, @Nullable String receiverPermission, @Nullable BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                }

                @Override
                public void sendBroadcastAsUser(Intent intent, UserHandle user) {

                }

                @Override
                public void sendBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission) {

                }

                @Override
                public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, @Nullable String receiverPermission, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                }

                @Override
                public void sendStickyBroadcast(Intent intent) {

                }

                @Override
                public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                }

                @Override
                public void removeStickyBroadcast(Intent intent) {

                }

                @Override
                public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {

                }

                @Override
                public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, @Nullable Handler scheduler, int initialCode, @Nullable String initialData, @Nullable Bundle initialExtras) {

                }

                @Override
                public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {

                }

                @Nullable
                @Override
                public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
                    return null;
                }

                @Nullable
                @Override
                public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter, int flags) {
                    return null;
                }

                @Nullable
                @Override
                public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler) {
                    return null;
                }

                @Nullable
                @Override
                public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, @Nullable String broadcastPermission, @Nullable Handler scheduler, int flags) {
                    return null;
                }

                @Override
                public void unregisterReceiver(BroadcastReceiver receiver) {

                }

                @Nullable
                @Override
                public ComponentName startService(Intent service) {
                    return null;
                }

                @Nullable
                @Override
                public ComponentName startForegroundService(Intent service) {
                    return null;
                }

                @Override
                public boolean stopService(Intent service) {
                    return false;
                }

                @Override
                public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
                    return false;
                }

                @Override
                public void unbindService(@NonNull ServiceConnection conn) {

                }

                @Override
                public boolean startInstrumentation(@NonNull ComponentName className, @Nullable String profileFile, @Nullable Bundle arguments) {
                    return false;
                }

                @Nullable
                @Override
                public Object getSystemService(@NonNull String name) {
                    return null;
                }

                @Nullable
                @Override
                public String getSystemServiceName(@NonNull Class<?> serviceClass) {
                    return null;
                }

                @Override
                public int checkPermission(@NonNull String permission, int pid, int uid) {
                    return pid;
                }

                @Override
                public int checkCallingPermission(@NonNull String permission) {
                    return Integer.parseInt(permission);
                }


                @Override
                public int checkCallingOrSelfPermission(@NonNull String permission) {
                    return Integer.parseInt(permission);
                }

                @Override
                public int checkSelfPermission(@NonNull String permission) {
                    return Integer.parseInt(permission);
                }

                @Override
                public void enforcePermission(@NonNull String permission, int pid, int uid, @Nullable String message) {

                }

                @Override
                public void enforceCallingPermission(@NonNull String permission, @Nullable String message) {

                }

                @Override
                public void enforceCallingOrSelfPermission(@NonNull String permission, @Nullable String message) {

                }

                @Override
                public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {

                }

                @Override
                public void revokeUriPermission(Uri uri, int modeFlags) {

                }

                @Override
                public void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {

                }

                @Override
                public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
                    return modeFlags;
                }

                @Override
                public int checkCallingUriPermission(Uri uri, int modeFlags) {
                    return modeFlags;
                }

                @Override
                public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
                    return modeFlags;
                }

                @Override
                public int checkUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags) {
                    return pid;
                }

                @Override
                public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {

                }

                @Override
                public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {

                }

                @Override
                public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {

                }

                @Override
                public void enforceUriPermission(@Nullable Uri uri, @Nullable String readPermission, @Nullable String writePermission, int pid, int uid, int modeFlags, @Nullable String message) {

                }

                @Override
                public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
                    return null;
                }

                @Override
                public Context createContextForSplit(String splitName) throws PackageManager.NameNotFoundException {
                    return null;
                }

                @Override
                public Context createConfigurationContext(@NonNull Configuration overrideConfiguration) {
                    return null;
                }

                @Override
                public Context createDisplayContext(@NonNull Display display) {
                    return null;
                }

                @Override
                public Context createDeviceProtectedStorageContext() {
                    return null;
                }

                @Override
                public boolean isDeviceProtectedStorage() {
                    return false;
                }
            };

            NfcAdapter adapter = NfcAdapter.getDefaultAdapter(context);
            Class<?> tagClass = Class.forName("android.nfc.Tag");
            
            // android.nfc.RawTagConnectionを生成
            Method createRawTagConnection = 
                adapter.getClass().getMethod("createRawTagConnection", tagClass);

            // android.nfc.RawTagConnection#mTagServiceフィールドを取得 (NfcService.INfcTagへの参照が入っている)
            Object rawTagConnection = createRawTagConnection.invoke(adapter, tag);
            Field f = rawTagConnection.getClass().getDeclaredField("mTagService");
            f.setAccessible(true);
            Object tagService = f.get(rawTagConnection);

            //データの型とデータを配列にまとめる
            Class<?>[] typeArray = new Class[params.length+1];
            typeArray[0] = Integer.TYPE;
            Object[] paramArray = new Object[params.length+1];
            paramArray[0] = getServiceHandle(adapter, tag);
            
            for ( int i = 0; i < params.length ; i++ ) {
                typeArray[i+1] = params[i].getClass();
                paramArray[i+1] = params[i];
            }
            
            Method method = tagService.getClass().getMethod(methodName, typeArray);
            Object o = method.invoke(tagService, paramArray);
            return o;
        } catch (ClassNotFoundException e){
            throw new NfcException(e);
        } catch (NoSuchMethodException e){
            throw new NfcException(e);
        } catch (SecurityException e){
            throw new NfcException(e);
        } catch (NoSuchFieldException e){
            throw new NfcException(e);
        } catch (IllegalAccessException e){
            throw new NfcException(e);
        } catch (IllegalArgumentException e){
            throw new NfcException(e);
        } catch (InvocationTargetException e){
            throw new NfcException(e);
        }            
    }
    /**
     * NFCデバイスへアクセスするためのサービスハンドルを取得します
     * 
     * @param adapter NfcAdapterへの参照をセット
     * @param tag  デバイスから取得したタグ( "android.nfc.extra.TAG" )をセットします
     * @return int 取得したサービスハンドルが戻ります
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static final int getServiceHandle(NfcAdapter adapter, Parcelable tag) throws 
        ClassNotFoundException , SecurityException
        , NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        //ServiceHandleをmServiceHandleフィールドから取得
        Class<?> tagClass = Class.forName("android.nfc.Tag");
        Field f = tagClass.getDeclaredField("mServiceHandle");
        f.setAccessible(true);
        return (Integer) f.get(tag);  
    }
}
