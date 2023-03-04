package pw.cinque.keystrokes;

import com.google.gson.*;
import io.fishermen.fpsdisplay.settings.GuiSettings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.keystrokes.KeystrokesMod;
import pw.cinque.keystrokes.render.Gui;
import pw.cinque.timechanger.ClickListener;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Client {
    public static File HACKS = null;
    public static File UZI_DIR = null;
    public static ArrayList<GuiSettings> Hacks = new ArrayList<GuiSettings>();
    private static JsonParser jsonParser = new JsonParser();
    private static Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    public Client(){
        UZI_DIR = getDirectory();
        if(UZI_DIR == null) return;
        HACKS = new File(UZI_DIR, "UZIHacks.json");
        if (!UZI_DIR.exists()) UZI_DIR.mkdir();
    }
    public static void saveHacks() {
        try {
            JsonObject json = new JsonObject();

            for (GuiSettings hack : Hacks) {
                if(hack == null || hack == getHack("LoadConfigs") || hack == getHack("SaveConfigs")) continue;
                JsonObject jsonHack = new JsonObject();
                jsonHack.addProperty("toggled", hack.getStat());
                jsonHack.addProperty("key", hack.getKey());

                if (!hack.getValue().isEmpty()) {
                    for (pw.cinque.CommandSettings.GuiSettings value : hack.getValue()) {
                        if (value instanceof ClickListener)
                            jsonHack.addProperty(value.getName(), (Boolean) value.getValue());
                        if (value instanceof CommandSettings)
                            jsonHack.addProperty(value.getName(), ((CommandSettings) value).g3tV4l4u3());
                    }
                }
                json.add(hack.getName(), jsonHack);
            }

            PrintWriter saveJson = new PrintWriter(new FileWriter(HACKS));
            saveJson.println(gsonPretty.toJson(json));
            saveJson.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static void loadHacks() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(HACKS));
            JsonObject jsonObject = (JsonObject) jsonParser.parse(bufferedReader);
            bufferedReader.close();

            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                GuiSettings hack = getHack(entry.getKey());

                if(hack == null || hack == getHack("LoadConfigs") || hack == getHack("SaveConfigs")) continue;

                JsonObject jsonObjectHack = (JsonObject) entry.getValue();

                hack.setKey(jsonObjectHack.get("key").getAsInt());
                hack.setToggled(jsonObjectHack.get("toggled").getAsBoolean());

                if(hack.getValue().isEmpty()) continue;

                for (pw.cinque.CommandSettings.GuiSettings value : hack.getValue()) {
                    if (value instanceof ClickListener)
                        value.setValue(jsonObjectHack.get(value.getName()).getAsBoolean());
                    if (value instanceof CommandSettings)
                        value.setValue(jsonObjectHack.get(value.getName()).getAsDouble());
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    private static GuiSettings getHack(String Name){
        for(GuiSettings m : Hacks){
            if(m.getName().equals(Name)){
                return m;
            }
        }
        return null;
    }
    public static File getDirectory() {
        File dir = Minecraft.getMinecraft().mcDataDir;
        return new File(String.format("%s%s%s-%s%s", dir, File.separator, "UZI", "1.8.9", File.separator));
    }
}
