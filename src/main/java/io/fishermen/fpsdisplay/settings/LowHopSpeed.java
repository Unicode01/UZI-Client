package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;

public class LowHopSpeed extends GuiSettings{
    public static CommandSettings speed;
    public LowHopSpeed() {
        super(GuiSettings.a(new char[]{'L','o','w','H','o','p','S','p','e','e','d'}), "", c4.movement, 0, -1);
        LowHopSpeed.speed = new CommandSettings(GuiSettings.a(new char[] { 'S', 'p', 'e', 'e', 'd' }), 0.34, 0.05, 1.5, 0.01);
        this.avav(speed);
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent a){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(!mc.thePlayer.onGround){
            return;
        }
        if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()){
            s(speed.g3tV4l4u3());
            mc.thePlayer.motionY = 0.1;
        }
    }
    public static float gg() {
        float v = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            v += 180.0f;
        }
        float f = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            f = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
            f = 0.5f;
        }
        else {
            f = 1.0f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            v -= 90.0f * f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            v += 90.0f * f;
        }
        v *= 0.017453292f;
        return v;
    }
    public static void s(final double s) {
        Minecraft.getMinecraft().thePlayer.motionX = -(Math.sin(gg()) * s);
        Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(gg()) * s;
    }
}
