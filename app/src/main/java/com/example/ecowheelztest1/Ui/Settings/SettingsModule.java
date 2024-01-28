package com.example.ecowheelztest1.Ui.Settings;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Repository.Repository;

public class SettingsModule {

    private Repository repository;
    private Context context;

    public SettingsModule(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    public void CreateContactUsDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.contact_us_dialog);
        ImageView close = dialog.findViewById(R.id.close);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void CreateLogOutDialog(LinearLayout logOutLayout) {
        if (repository.getIsRegistered() || repository.getIsLoggedIn()) {
            Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.log_out_dialog);
            Button yes = dialog.findViewById(R.id.btnYes);
            Button no = dialog.findViewById(R.id.btnNo);


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    repository.LogOut();
                    Toast.makeText(context, "התנתקת בהצלחה", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

    }
}
