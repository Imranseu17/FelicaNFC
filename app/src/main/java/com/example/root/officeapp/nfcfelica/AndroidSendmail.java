package com.example.root.officeapp.nfcfelica;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public final class AndroidSendmail {
    private final boolean auth;
    private final String password;
    private final String port;
    private final String server;
    private final boolean ssl;
    private final int timeout;
    private final boolean tls;
    private final String userid;
    private final String username;

    public class SMSSentBroadcastReceiver extends BroadcastReceiver {
        public boolean first = false;
        public SendMailed sm;

        public void onReceive(Context context, Intent intent) {
            boolean result = false;
            if (getResultCode() == -1) {
                result = true;
            }
            if (!this.first) {
                this.sm.onMailResult(result);
                this.first = true;
            }
        }
    }

    public interface SendMailed {
        void onMailResult(boolean z);
    }

    private class SimpleAuthenticator extends Authenticator {
        private String pass_string = null;
        private String user_string = null;

        public SimpleAuthenticator(String user_s, String pass_s) {
            this.user_string = user_s;
            this.pass_string = pass_s;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(this.user_string, this.pass_string);
        }
    }

    public AndroidSendmail(String server, String port, String userid, String password, String username, boolean auth, boolean ssl, boolean tls, int timeout) {
        this.server = server;
        this.port = port;
        this.userid = userid;
        this.password = password;
        this.username = username;
        this.auth = auth;
        this.ssl = ssl;
        this.tls = tls;
        this.timeout = timeout;
    }

    public void SendMail(Context context, String to, String from, String subject, String body, final SendMailed sm) {
        if (SettingData.SmtpSetting.getUseSMS()) {
            SendSMSMail(context, to, subject, body, sm);
            return;
        }
        new AsyncTask<String, Void, Boolean>() {
            protected Boolean doInBackground(String... params) {
                Boolean result_string = Boolean.valueOf(false);
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", AndroidSendmail.this.server);
                    props.put("mail.smtp.port", AndroidSendmail.this.port);
                    if (AndroidSendmail.this.timeout > 0) {
                        props.put("mail.smtp.connectiontimeout", Integer.valueOf(AndroidSendmail.this.timeout));
                    }
                    SimpleAuthenticator sa = null;
                    if (AndroidSendmail.this.auth) {
                        props.put("mail.smtp.auth", "true");
                        sa = new SimpleAuthenticator(AndroidSendmail.this.userid, AndroidSendmail.this.password);
                    }
                    if (AndroidSendmail.this.ssl) {
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        props.put("mail.smtp.socketFactory.fallback", "false");
                        props.put("mail.smtp.socketFactory.port", AndroidSendmail.this.port);
                    }
                    if (AndroidSendmail.this.tls) {
                        props.put("mail.smtp.starttls.enable", "true");
                    }
                    MimeMessage msg = new MimeMessage(Session.getInstance(props, sa));
                    msg.setRecipients(RecipientType.TO, params[0]);
                    msg.setFrom(new InternetAddress(params[1], AndroidSendmail.this.username, "utf-8"));
                    msg.setSubject(MimeUtility.encodeText(params[2], "utf-8", "B"));
                    msg.setContent(params[3], "text/plain; charset=\"utf-8\"");
                    msg.setSentDate(new Date());
                    Transport.send(msg);
                    return Boolean.valueOf(true);
                } catch (Exception e) {
                    LogUtil.e(e);
                    return result_string;
                }
            }

            protected void onPostExecute(Boolean result) {
                sm.onMailResult(result.booleanValue());
            }
        }.execute(new String[]{to, from, subject, body});
    }

    private void SendSMSMail(Context context, String Address, String subject, String Text, SendMailed sm) {
        Context context2 = context;
        SendMailed sendMailed = sm;
        String ACTION_SENT = "com.example.sms2.ACTION_SENT";
        Activity activity = (Activity) context2;
        SMSSentBroadcastReceiver smsSentBroadcastReceiver = new SMSSentBroadcastReceiver();
        smsSentBroadcastReceiver.sm = sendMailed;
        activity.registerReceiver(smsSentBroadcastReceiver, new IntentFilter("com.example.sms2.ACTION_SENT"));
        String sendText = Text;
        SmsManager smsManager = SmsManager.getDefault();
        try {
            if (sendText.length() > 160) {
                ArrayList<String> messagelist = smsManager.divideMessage(sendText);
                ArrayList<PendingIntent> arrayList = new ArrayList();
                for (int i = 0; i < messagelist.size(); i++) {
                    arrayList.add(PendingIntent.getBroadcast(context2, 0, new Intent("com.example.sms2.ACTION_SENT"), 0));
                }
                ArrayList<PendingIntent> sentIntents = arrayList;
                smsManager.sendMultipartTextMessage(Address, null, messagelist, arrayList, null);
                return;
            }
            smsManager.sendTextMessage(Address, null, sendText, PendingIntent.getBroadcast(context2, 0, new Intent("com.example.sms2.ACTION_SENT"), 0), null);
        } catch (Exception e) {
            sendMailed.onMailResult(false);
        }
    }
}
