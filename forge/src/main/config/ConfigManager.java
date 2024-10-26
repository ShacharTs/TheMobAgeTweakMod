package net.MobAgeTweak.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/AppData/Roaming/.minecraft/config/";
    private static final String CONFIG_FILE = CONFIG_DIR + "mobagetweak_config.properties";
    public static final int DEFAULT_COOLDOWN = 1200;

    private static Properties properties = new Properties();

    static {
        loadConfig();
    }

    public static boolean loadBooleanSetting(String mobName, String key, boolean defaultValue) {
        String fullKey = mobName.toLowerCase() + "_" + key;
        String value = properties.getProperty(fullKey);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static void saveBooleanSetting(String mobName, String key, boolean value) {
        String fullKey = mobName.toLowerCase() + "_" + key;
        properties.setProperty(fullKey, String.valueOf(value));
        saveProperties();
    }

    public static void loadConfig() {
        // Create the config directory if it doesn't exist
        new File(CONFIG_DIR).mkdirs();

        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadCooldown(String mobName) {
        String key = mobName.toLowerCase() + "_cooldown";
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : DEFAULT_COOLDOWN;
    }

    public static void saveCooldown(String mobName, int cooldown) {
        String key = mobName.toLowerCase() + "_cooldown";
        properties.setProperty(key, String.valueOf(cooldown));
        saveProperties();
    }

    // Load hostile-specific settings (onlyBaby, disableBaby)
    public static boolean loadHostileSetting(String mobName, String setting) {
        String key = mobName.toLowerCase() + "_" + setting;
        return Boolean.parseBoolean(properties.getProperty(key, "false"));
    }

    // Save hostile-specific settings (onlyBaby, disableBaby)
    public static void saveHostileSetting(String mobName, String setting, boolean value) {
        saveBooleanSetting(mobName, setting, value);
    }

    private static void saveProperties() {
        // Ensure the config directory exists before saving properties
        new File(CONFIG_DIR).mkdirs();

        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MobSettings getMobSettings(String mobName) {
        MobSettings settings = new MobSettings();
        settings.getCooldowns().put(mobName, loadCooldown(mobName));
        settings.setOnlyBaby(loadHostileSetting(mobName, "onlyBaby"));
        settings.setDisableBaby(loadHostileSetting(mobName, "disableBaby"));
        return settings;
    }

    public static void setMobSettings(String mobName, MobSettings mobSettings) {
        int cooldown = mobSettings.getCooldowns().getOrDefault(mobName, DEFAULT_COOLDOWN);
        saveCooldown(mobName, cooldown);
        saveHostileSetting(mobName, "onlyBaby", mobSettings.isOnlyBaby());
        saveHostileSetting(mobName, "disableBaby", mobSettings.isDisableBaby());
    }

    public static class MobSettings {
        private Map<String, Integer> cooldowns = new HashMap<>();
        private boolean onlyBaby = false;
        private boolean disableBaby = false;

        public Map<String, Integer> getCooldowns() {
            return cooldowns;
        }

        public boolean isOnlyBaby() {
            return onlyBaby;
        }

        public void setOnlyBaby(boolean onlyBaby) {
            this.onlyBaby = onlyBaby;
        }

        public boolean isDisableBaby() {
            return disableBaby;
        }

        public void setDisableBaby(boolean disableBaby) {
            this.disableBaby = disableBaby;
        }
    }
}
