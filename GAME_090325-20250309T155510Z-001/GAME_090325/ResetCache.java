public class ResetCache {
    public static void main(String[] args) {
        GamePreferences.saveCoin(0);
        
        GamePreferences.saveMP5Owned(false);
        GamePreferences.saveAKOwned(false);
        GamePreferences.saveSGOwned(false);
        GamePreferences.saveM4Owned(false);

        GamePreferences.saveSelectedGun("Pistol");

        System.out.println("Game preferences have been reset!");
    }
}
