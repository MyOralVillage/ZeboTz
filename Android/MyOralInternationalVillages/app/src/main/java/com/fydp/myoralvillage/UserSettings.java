package com.fydp.myoralvillage;

/**
 * Created by paulj on 2016-10-16.
 *
 * This just keeps track of some user inputted data
 */

public class UserSettings {
        /*
         * We support 3 countries right now
         *   Tanzania - Shilling, TZ-
         *   Vanuatu - Vatu VT
         *   Tonga - Pa'Anga T$
         */
        public enum Country {TANZANIA, VANUATU, TONGA}
        public String userName = "admin"; // Make this the default for now. CHANGE BEFORE RELEASE
        public int userId;
        public boolean[] demosViewed = {false,false,false,false,false,false,false,false,false};
        public boolean[] availableLevels = {true,false,false};
        public boolean[] activityProgress = {false,false,false,false,false,false,false,false,false};
        Country actual_country = Country.TONGA;
}
