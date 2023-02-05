package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.entity.EntityPlayerSP;
import java.util.Random;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;

public class Da extends GuiSettings {
    public static CommandSettings x;
    public static CommandSettings y;
    public Random random;
    public static boolean en;

    public Da() {
        super(GuiSettings.a(new char[]{'V', 'e', 'l', 'o', 'c', 'i', 't', 'y'}), "", c4.combat1, 0, 0);
        this.random = new Random();
        Da.x = new CommandSettings(GuiSettings.a(new char[]{'X'}), 90.0, 0.0, 100.0, 1.0);
        Da.y = new CommandSettings(GuiSettings.a(new char[]{'Y'}), 90.0, 0.0, 100.0, 1.0);
        this.avav(Da.x);
        this.avav(Da.y);
    }
    @Override
    public void en(){
        en = true;
    }
    @Override
    public void dd(){
        en = false;
    }
    public static boolean onPacket(Object Packet, Connection.Side side){
        if(side == Connection.Side.IN && Packet instanceof S12PacketEntityVelocity && x.g3tV4l4u3() == 0 && y.g3tV4l4u3() == 0 && en){
            return false;
        }
        return true;
    }
    @SubscribeEvent
    public void onTick(LivingEvent.LivingUpdateEvent event) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (Da.mc.thePlayer.hurtTime == Da.mc.thePlayer.maxHurtTime && Da.mc.thePlayer.maxHurtTime > 0) {
            if (Da.x.g3tV4l4u3() == 0 && Da.y.g3tV4l4u3() == 0) {
                return;
            }
            if (Da.y.g3tV4l4u3() == 0 && Da.x.g3tV4l4u3() != 0) {
                final EntityPlayerSP thePlayer = Da.mc.thePlayer;
                thePlayer.motionX *= Da.x.g3tV4l4u3() / 100.0;
                final EntityPlayerSP thePlayer2 = Da.mc.thePlayer;
                thePlayer2.motionY = 0;
                final EntityPlayerSP thePlayer3 = Da.mc.thePlayer;
                thePlayer3.motionZ *= Da.x.g3tV4l4u3() / 100.0;
            }
            if (Da.y.g3tV4l4u3() != 0 && Da.x.g3tV4l4u3() == 0) {
                final EntityPlayerSP thePlayer = Da.mc.thePlayer;
                thePlayer.motionX = 0;
                final EntityPlayerSP thePlayer2 = Da.mc.thePlayer;
                thePlayer2.motionY *= Da.y.g3tV4l4u3() / 100.0;
                final EntityPlayerSP thePlayer3 = Da.mc.thePlayer;
                thePlayer3.motionZ = 0;
            }
            if (Da.x.g3tV4l4u3() != 0 && Da.x.g3tV4l4u3() != 0) {
                final EntityPlayerSP thePlayer = Da.mc.thePlayer;
                thePlayer.motionX *= Da.x.g3tV4l4u3() / 100.0;
                final EntityPlayerSP thePlayer2 = Da.mc.thePlayer;
                thePlayer2.motionY *= Da.y.g3tV4l4u3() / 100.0;
                final EntityPlayerSP thePlayer3 = Da.mc.thePlayer;
                thePlayer3.motionZ *= Da.x.g3tV4l4u3() / 100.0;
            }
        }
    }
}
