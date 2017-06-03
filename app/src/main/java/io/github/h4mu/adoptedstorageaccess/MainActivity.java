package io.github.h4mu.adoptedstorageaccess;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.text.DecimalFormat;

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
        folderPathText.setText(dir.getPath());  // /mnt/expand/

        Button folderButton = (Button) findViewById(R.id.folderButton);
        folderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(dir), "resource/folder"); // ES
//                intent.setData(Uri.fromFile(dir)); // OI

                if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
                {
                    startActivity(intent);
                }
            }
        });

        Button clipboardButton = (Button) findViewById(R.id.clipboardButton);
        clipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newUri(getContentResolver(), "URI", Uri.fromFile(dir));
                clipboard.setPrimaryClip(clip);
            }
        });
    }
}
