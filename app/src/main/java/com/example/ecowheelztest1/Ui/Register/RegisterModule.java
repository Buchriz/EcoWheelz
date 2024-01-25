package com.example.ecowheelztest1.Ui.Register;

public class RegisterModule {

    private String userName, email, fullName;
    private int phoneNumber;

    public RegisterModule(String userName, String email, String fullName, int phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



//    boolean checkErrors()
//    {
//
//        if (this.userName.length()==0)
//        {
//            etuserName.setError("Enter UserName");
//            return false;
//        }
//
//        if (email.length() == 0)
//        {
//            etemail.setError("Enter Email");
//            return false;
//        }
//
//        if (fullName.length() == 0)
//        {
//            etfullName.setError("Enter Full Name");
//            return false;
//        }
//
//        if (String.valueOf(phoneNumber).length() == 0)
//        {
//            etphoneNumber.setError("Enter Phone");
//            return false;
//        }
//
//        return true;
//    }
}
