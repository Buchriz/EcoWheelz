package com.example.ecowheelztest1.Ui.Settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Repository.Repository;

import java.io.IOException;
import java.util.List;

public class SettingsModule {

    private Repository repository;
    private Context context;


    public SettingsModule(Context context) {
        repository = new Repository(context);
        this.context = context;
    }

    public boolean getIsLoggedIn()
    {
        return repository.getIsLoggedIn();
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

    public void CreateLogOutDialog() {
        if (repository.getIsLoggedIn()) {
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

    public LinearLayout ChangeHomeLocationNewLayout(LinearLayout parent, RelativeLayout midParent)
    {
        parent.removeView(midParent);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));

        EditText etUpdate = new EditText(context);
        etUpdate.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2));
        etUpdate.setHint("Change Location");
        etUpdate.setHintTextColor(Color.WHITE);
        etUpdate.setTextColor(Color.WHITE);

        Button btnUpdate = new Button(context);
        btnUpdate.setLayoutParams(new LinearLayout.LayoutParams(-2,-2,1));
        btnUpdate.setText("עדכן");
        btnUpdate.setTextColor(Color.WHITE);
        btnUpdate.setGravity(Gravity.CENTER);
        btnUpdate.setTextSize(17);
        btnUpdate.setTypeface(null, Typeface.BOLD);
        btnUpdate.setBackground(context.getDrawable(R.drawable.btn_backround));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView(linearLayout);
                parent.addView(midParent);

                String strH = etUpdate.getText().toString().trim();

                if (checkIsTrueLocation(strH)){
                    if (!strH.equals(repository.getSharedPreferences().getWorkLocation()))
                        repository.updateHomeLocation(strH);
                    else
                        Toast.makeText(context, "בית ועבודה אינם יכולים להיות לאותו מיקום", Toast.LENGTH_SHORT).show();
                }

            }
        });

        linearLayout.addView(etUpdate);
        linearLayout.addView(btnUpdate);

        return linearLayout;
    }
    public LinearLayout ChangeWorkLocationNewLayout(LinearLayout parent, RelativeLayout midParent)
    {
        parent.removeView(midParent);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));

        EditText etUpdate = new EditText(context);
        etUpdate.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2));
        etUpdate.setHint("Change Location");
        etUpdate.setHintTextColor(Color.WHITE);
        etUpdate.setTextColor(Color.WHITE);

        Button btnUpdate = new Button(context);
        btnUpdate.setLayoutParams(new LinearLayout.LayoutParams(-2,-2,1));
        btnUpdate.setText("עדכן");
        btnUpdate.setTextColor(Color.WHITE);
        btnUpdate.setGravity(Gravity.CENTER);
        btnUpdate.setTextSize(17);
        btnUpdate.setTypeface(null, Typeface.BOLD);
        btnUpdate.setBackground(context.getDrawable(R.drawable.btn_backround));
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView(linearLayout);
                parent.addView(midParent);

                String strW = etUpdate.getText().toString().trim();

                if (checkIsTrueLocation(strW)){
                    if (!strW.equals(repository.getSharedPreferences().getHomeLocation()))
                        repository.updateWorkLocation(strW);
                    else
                        Toast.makeText(context, "בית ועבודה אינם יכולים להיות לאותו מיקום", Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayout.addView(etUpdate);
        linearLayout.addView(btnUpdate);

        return linearLayout;
    }


    /////////////////////////////////////////////////////////////////////////////
    //              לנסות לשים דיאלוג לפני שינוי מיקומים
    ////////////////////////////////////////////////////////////////////////////
    public boolean HomeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String title = "מיקום ביתך הוא: " + repository.getSharedPreferences().getHomeLocation();
        builder.setTitle(title);
        builder.setMessage("האם בטוח לשנות מיקום זה?");
        builder.setCancelable(false);

        final boolean[] flag = {false};

        builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                flag[0] = true;
            }
        });

        builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                flag[0] = false;
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(-1).setTextColor(Color.GREEN);
        dialog.getButton(-2).setTextColor(Color.RED);

        return flag[0];
    }

    private boolean checkIsTrueLocation(String str) {
        if (str.length() == 0){
            Toast.makeText(context, "לא הוכנס מיקום", Toast.LENGTH_SHORT).show();
        }
        else {
            Address address = null;
            Geocoder geocoder = new Geocoder(context);

            try {
                List<Address> addresses = geocoder.getFromLocationName(str, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    address = addresses.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error finding location. Please try again.", Toast.LENGTH_SHORT).show();
            }

            if (address != null) {
                return true;
            } else {
                Toast.makeText(context, "מיקום לא נמצא", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

}
