package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.*;
import pw.cinque.CommandSettings.CommandSettings;

public class Tm extends GuiSettings {
    public static CommandSettings TmS;
    public Tm()
    {
        super(GuiSettings.a(new char[] { 'T','i','m','e','r' }), "", c4.movement, 0, -1);
        Tm.TmS = new CommandSettings(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), 1.00, 0.01, 2.00, 0.01);
        this.avav(Tm.TmS);
    }
    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
            if (!(mc.currentScreen instanceof GuiMainMenu)) {
            getTimer().timerSpeed = (float)Tm.TmS.g3tV4l4u3();
        } else {
            resetTimer();
        }
    }
    public static net.minecraft.util.Timer getTimer() {
        return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
    }

    static void resetTimer() {
        try {
            getTimer().timerSpeed = 1.0F;
        } catch (NullPointerException ignored) {}
    }
    @Override
    public void dd() {
        resetTimer();
    }
}
