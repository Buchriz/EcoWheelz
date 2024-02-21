package com.example.ecowheelztest1.Ui.Profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ecowheelztest1.R;
import com.example.ecowheelztest1.Repository.Repository;
import com.example.ecowheelztest1.Repository.User;
import com.example.ecowheelztest1.Ui.Maps.MapsActivity;

public class ProfileModule {

    private Repository repository;
    private Context context;

    public ProfileModule(Context context) {
        this.repository = new Repository(context);
        this.context = context;
    }

    public User getUserLogIn(){
        return this.repository.getSharedPreferences();
    }
    public boolean getIsLoggedIn()
    {
        return this.repository.getIsLoggedIn();
    }



    public void NewLayout(LinearLayout pA,LinearLayout regiButtonLayout,TextView tvName,String userName,String fullName, String email, String phoneNumber) {
        /////////////////////////////////////////////////////////////////////////
        //                   השמה של הפרטים האישיים
        ////////////////////////////////////////////////////////////////////////
        pA.removeView(regiButtonLayout);
        tvName.setText(userName);

        LinearLayout infoLinearLayout = new LinearLayout(context);
        infoLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1)); // Weighted height
        infoLinearLayout.setOrientation(LinearLayout.VERTICAL);
        infoLinearLayout.setGravity(Gravity.CENTER);

        TextView tvfullname = new TextView(context);
        tvfullname.setTextSize(20);
        tvfullname.setTextColor(Color.WHITE);
        tvfullname.setTypeface(null, Typeface.BOLD);
        tvfullname.setGravity(Gravity.CENTER);
        tvfullname.setBackground(context.getDrawable(R.drawable.round_background_settings));
        SpannableString spannableString1 = new SpannableString("Full Name: " + fullName);
        spannableString1.setSpan(new UnderlineSpan(), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvfullname.setText(spannableString1);
        //tvfullname.setText("Full Name: " + fullName);


        TextView tvemail = new TextView(context);
        tvemail.setTextSize(20);
        tvemail.setTextColor(Color.WHITE);
        tvemail.setTypeface(null, Typeface.BOLD);
        tvemail.setGravity(Gravity.CENTER);
        tvemail.setBackground(context.getDrawable(R.drawable.round_background_settings));
        SpannableString spannableString2 = new SpannableString("Email: " + email);
        spannableString2.setSpan(new UnderlineSpan(), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvemail.setText(spannableString2);
        //tvemail.setText("Email: " + email);


        TextView tvphonenumber = new TextView(context);
        tvphonenumber.setTextSize(20);
        tvphonenumber.setTextColor(Color.WHITE);
        tvphonenumber.setTypeface(null, Typeface.BOLD);
        tvphonenumber.setGravity(Gravity.CENTER);
        tvphonenumber.setBackground(context.getDrawable(R.drawable.round_background_settings));
        SpannableString spannableString3 = new SpannableString("Phone Number: " + phoneNumber);
        spannableString3.setSpan(new UnderlineSpan(), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvphonenumber.setText(spannableString3);
        //tvphonenumber.setText("Phone Number: " + phoneNumber);


        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(-1,200);
        textParams.setMargins(5,30,5,0);
        tvfullname.setLayoutParams(textParams);
        tvemail.setLayoutParams(textParams);
        tvphonenumber.setLayoutParams(textParams);


        infoLinearLayout.addView(tvfullname);
        infoLinearLayout.addView(tvemail);
        infoLinearLayout.addView(tvphonenumber);

        pA.addView(infoLinearLayout);


        /////////////////////////////////////////////////////////////////////////
        //                  השמה של הכפתור שינוי פרטים אישיים
        ////////////////////////////////////////////////////////////////////////

        RelativeLayout infoRelativeLayout = new RelativeLayout(context);
        infoRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1,-2));

        Button btnChange = new Button(context);
        btnChange.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        btnChange.setText("שנה פרטים אישיים");
        btnChange.setTextColor(Color.WHITE);
        btnChange.setTextSize(16);
        btnChange.setTypeface(null, Typeface.BOLD);
        btnChange.setBackground(context.getDrawable(R.drawable.btn_backround));

        // Set the position of the Button in the RelativeLayout
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(400,-2);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        buttonParams.setMargins(0, 0, 10, 10);
        btnChange.setLayoutParams(buttonParams);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataDialog();
            }
        });

        infoRelativeLayout.addView(btnChange);

        pA.addView(infoRelativeLayout);
    }


    public void updateDataDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_dialog);
        ImageView closeUpdate = dialog.findViewById(R.id.closeUpdate);
        EditText etuserNameUpdate = dialog.findViewById(R.id.userNameUpdate);
        EditText etemailUpdate = dialog.findViewById(R.id.emailUpdate);
        EditText etfullNameUpdate = dialog.findViewById(R.id.fullNameUpdate);
        EditText etphoneUpdate = dialog.findViewById(R.id.phoneUpdate);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        closeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        User user = repository.getSharedPreferences();
        etuserNameUpdate.setHint(user.getUserName());
        etemailUpdate.setHint(user.getEmail());
        etfullNameUpdate.setHint(user.getFullName());
        etphoneUpdate.setHint(user.getPhoneNumber());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userNameUpdate = etuserNameUpdate.getText().toString().trim();
                if (userNameUpdate.length() == 0)
                    userNameUpdate = user.getUserName();


                String emailUpdate = etemailUpdate.getText().toString().trim();
                if (emailUpdate.length() == 0)
                    emailUpdate = user.getEmail();


                String fullNameUpdate = etfullNameUpdate.getText().toString().trim();
                if (fullNameUpdate.length() == 0)
                    fullNameUpdate = user.getFullName();


                String phoneUpdate = etphoneUpdate.getText().toString().trim();
                if (phoneUpdate.length() == 0)
                    phoneUpdate = user.getPhoneNumber();


                repository.updateData(user.getRow_id(), userNameUpdate, emailUpdate, fullNameUpdate, phoneUpdate);
                dialog.dismiss();
                Intent intent = new Intent(context, MapsActivity.class);
                context.startActivity(intent);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
