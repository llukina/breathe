package com.example.android.breath.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);

    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply();

    }

    public int getSessions() {
        return preferences.getInt("sessions", 0);

    }

    public void setSessions(int sessions) {
        preferences.edit().putInt("sessions", sessions).apply();

    }

    public String getDate() {
        long miliDate = preferences.getLong("seconds", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliDate);

        String time = "Last: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        return time;

    }

    public void setDate(long miliseconds) {
        preferences.edit().putLong("seconds", miliseconds).apply();

    }
}
