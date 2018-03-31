package com.example.hp.probattletask;

/**
 * Created by hp on 3/31/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;

public class Utilities {

    public static String _UserInfo = "UserInfo";
    public static String _TempUserEmail = "TempUserInfo";

    private ProgressDialog pd = null;

    public void ShowLoader(Context context, String message) {
        pd = new ProgressDialog(context);

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
        pd.setMessage(message);

        // Set the progress dialog background color
        //pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(true);

        // Finally, show the progress dialog
        pd.show();
    }

    public void HideLoader() {
        if(pd != null)
            pd.dismiss();
    }


}

