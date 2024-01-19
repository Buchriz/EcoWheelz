package com.example.ecowheelztest1.Ui.Settings;

public class NightModeSwitch
{
    private static boolean flag;

    public NightModeSwitch()
    {}

    public static boolean GetNightModSwitch()
    {
        return flag;
    }
    public static void SetNightModSwitch(boolean f)
    {
        flag = f;
    }

}
