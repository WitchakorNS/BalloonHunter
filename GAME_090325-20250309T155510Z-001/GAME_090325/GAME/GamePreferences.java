import java.util.prefs.Preferences;

public class GamePreferences {
    private static final Preferences prefs = Preferences.userNodeForPackage(GamePreferences.class);
    
    // Coin preferences
    private static final String COIN_KEY = "coin";
    
    // Gun ownership preferences
    private static final String MP5_OWNED_KEY = "mp5_owned";
    private static final String AK_OWNED_KEY = "ak_owned";
    private static final String SG_OWNED_KEY = "sg_owned";
    private static final String M4_OWNED_KEY = "m4_owned";
    
    // Selected gun preference
    private static final String SELECTED_GUN_KEY = "selected_gun";
    
    // Coin methods
    public static void saveCoin(int coin) {
        prefs.putInt(COIN_KEY, coin);
    }
    
    public static int loadCoin() {
        return prefs.getInt(COIN_KEY, 0);
    }
    
    // Gun ownership methods
    public static void saveMP5Owned(boolean owned) {
        prefs.putBoolean(MP5_OWNED_KEY, owned);
    }
    
    public static boolean isMP5Owned() {
        return prefs.getBoolean(MP5_OWNED_KEY, false);
    }
    
    public static void saveAKOwned(boolean owned) {
        prefs.putBoolean(AK_OWNED_KEY, owned);
    }
    
    public static boolean isAKOwned() {
        return prefs.getBoolean(AK_OWNED_KEY, false);
    }
    
    public static void saveSGOwned(boolean owned) {
        prefs.putBoolean(SG_OWNED_KEY, owned);
    }
    
    public static boolean isSGOwned() {
        return prefs.getBoolean(SG_OWNED_KEY, false);
    }
    
    public static void saveM4Owned(boolean owned) {
        prefs.putBoolean(M4_OWNED_KEY, owned);
    }
    
    public static boolean isM4Owned() {
        return prefs.getBoolean(M4_OWNED_KEY, false);
    }
    
    // Selected gun methods
    public static void saveSelectedGun(String gunType) {
        prefs.put(SELECTED_GUN_KEY, gunType);
    }
    
    public static String getSelectedGun() {
        return prefs.get(SELECTED_GUN_KEY, "Pistol"); // Default to Pistol
    }
}
