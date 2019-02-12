package com.bateman.rich.rmblibrary.gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bateman.rich.rmblibrary.R;

/**
 * A class for showing an About Dialog for your app.
 *
 * Sample usage:
 *         AboutAppDialog aboutDialog = new AboutAppDialog(this); // I had a major bug here where I was mistakenly using "getApplicationContext()"
 *         aboutDialog.setIconId(R.mipmap.ic_launcher)
 *                 .setTitleId(R.string.app_name)
 *                 .setVersionName(BuildConfig.VERSION_NAME)
 *                 .setCopyrightYear(2019)
 *                 .show();
 */
@SuppressWarnings("unused")
public class AboutAppDialog extends AlertDialog {

    private AlertDialog m_dialog = null;
    private int m_titleId;
    private int m_iconId;
    private int m_copyrightYear;
    private String m_versionName;

    private final DialogInterface.OnClickListener m_onClickDialogOkListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(m_dialog != null && m_dialog.isShowing()) {
                m_dialog.dismiss();
            }
        }
    };

    private final View.OnClickListener m_onClickUrlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String s = ((TextView) v).getText().toString();
            intent.setData(Uri.parse(s));
            try {
                getContext().startActivity(intent);
            } catch(ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No browser application found, cannot visit world-wide web", Toast.LENGTH_LONG).show();
            }
        }
    };


    public AboutAppDialog(Context context) {
        super(context);
    }

    public AboutAppDialog setTitleId(int titleId) {
        m_titleId=titleId;
        return this;
    }

    public AboutAppDialog setIconId(int iconId) {
        m_iconId=iconId;
        return this;
    }

    public AboutAppDialog setCopyrightYear(int year) {
        m_copyrightYear=year;
        return this;
    }

    public AboutAppDialog setVersionName(String versionName) {
        m_versionName=versionName;
        return this;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if(m_titleId > 0) builder.setTitle(m_titleId);
        if(m_iconId > 0) builder.setIcon(m_iconId);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams") View mainView = inflater.inflate(R.layout.dialog_about_app, null, false);
        builder.setView(mainView);

        builder.setPositiveButton("Ok", m_onClickDialogOkListener);
        m_dialog = builder.create();
        m_dialog.setCanceledOnTouchOutside(true);

        if(m_versionName != null) {
            TextView textViewVersionName = mainView.findViewById(R.id.dialog_about_version);
            String versionNameText = getContext().getResources().getString(R.string.dialog_about_version_name, m_versionName);
            textViewVersionName.setText(versionNameText);
        }

        TextView textViewCopyright = mainView.findViewById(R.id.dialog_about_copyright);
        String copyrightText = getContext().getResources().getString(R.string.dialog_about_app_copyright, m_copyrightYear);
        textViewCopyright.setText(copyrightText);

        TextView textViewAboutUrl = mainView.findViewById(R.id.dialog_about_app_url);
        if(textViewAboutUrl != null) {
            textViewAboutUrl.setOnClickListener(m_onClickUrlListener);
        }

        m_dialog.show();
    }
}
