package io.fishermen.fpsdisplay.settings;

import io.fishermen.fpsdisplay.FPSRenderer;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import io.fishermen.fpsdisplay.settings.Tm;
import org.lwjgl.input.Keyboard;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.keystrokes.render.Entity;

public class Spd2 extends GuiSettings {
    public static CommandSettings TmS;
    public Spd2()
    {
        super(GuiSettings.a(new char[] { 'T','i','m','e','r','S','p','e','e','d' }), "", c4.movement, 0, -1);
        Spd2.TmS = new CommandSettings(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), 1.00, 0.50, 2.00, 0.01);
        this.avav(Spd2.TmS);
    }
    private static net.minecraft.util.Timer getTimer() {
        return ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "timer", "field_71428_T");
    }

    private static void resetTimer() {
        try {
            getTimer().timerSpeed = 1.0F;
        } catch (NullPointerException ignored) {}
    }

    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            getTimer().timerSpeed = (float)Spd2.TmS.g3tV4l4u3();
            if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
                mc.thePlayer.jump();
                mc.thePlayer.setSprinting(false);
            }
        }
    }
    /*
    @Override
    public void en() {getTimer().timerSpeed = (float)Spd2.TmS.g3tV4l4u3();}
     */
    @Override
    public void dd() {resetTimer();}
}
