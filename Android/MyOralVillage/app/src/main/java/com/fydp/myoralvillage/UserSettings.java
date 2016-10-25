package com.fydp.myoralvillage;

/**
 * Created by kamil on 2016-02-29.
 */
public class UserSettings {
    /*
     * We support 3 countries right now
     *   Tanzania - Shilling, TZ-
     *   Vanuatu - Vatu VT
     *   Tonga - Pa'Anga T$
     */
    public enum Country {TANZANIA, VANUATU, TONGA}
    public String userName;
    public int userId;
    public boolean[] demosViewed = {false,false,false,false,false,false,false,false,false};
    public boolean[] availableLevels = {true,false,false};
    public boolean[] activityProgress = {false,false,false,false,false,false,false,false,false};
    Country actual_country = Country.VANUATU;
}
