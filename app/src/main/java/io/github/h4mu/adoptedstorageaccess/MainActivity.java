package io.github.h4mu.adoptedstorageaccess;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final File dir = getFilesDir();
        dir.setReadable(true, false);
        dir.setExecutable(true, false);
        dir.setWritable(true, false);

        TextView folderPathText = (TextView) findViewById(R.id.folderPathText);
        folderPathText.setText(dir.getPath());
        if (!dir.getAbsolutePath().startsWith("/mnt/expand/")) {
            findViewById(R.id.warningLayout).setVisibility(View.VISIBLE);
        }
    }

    public void onOpenAppSettingsButtonClick(View view) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    public void onFolderButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(getFilesDir()));
        if (!((Checkable) findViewById(R.id.enableActivityAttachmentSwitch)).isChecked()) {
            intent.setType("resource/folder");
        }

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(intent);
        }
    }

    public void onClipboardButtonClick(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        Uri uri = Uri.fromFile(getFilesDir());
        ClipData clip = ClipData.newUri(getContentResolver(), "URI", uri);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, getString(R.string.copiedToClipboard, uri), Toast.LENGTH_LONG).show();
    }

    public void onSettingsButtonClick(View view) {
//        startActivity(new Intent(getBaseContext(), SettingsActivity.class));
    }

    public void onLicenseButtonClick(View view) {
        startActivity(new Intent(getBaseContext(), LicenseActivity.class));
    }
}
