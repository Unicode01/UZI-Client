package io.fishermen.fpsdisplay.settings;


import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.event.Connection;
import pw.cinque.CommandSettings.CommandSettings;

import java.util.Objects;

public class Disabler extends GuiSettings {
    public static boolean CancelPacket;
    public static boolean enA;
    public static long LastPacketTime;
    public static CommandSettings DisPackTime;

    public Disabler() {
        super("Disabler", "", c4.Player, 0, -1);
        enA = false;
        //this.avav(DisPackTime = new CommandSettings("TimerDisTime", 30, 0, 100, 10));
    }

    @Override
    public void en() {
        enA = true;
    }

    @Override
    public void dd() {
        enA = false;
    }

    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if (connection_side == Connection.Side.OUT && enA) {
            /*
            if (object instanceof C03PacketPlayer) {
                if (System.currentTimeMillis() - LastPacketTime <= DisPackTime.g3tV4l4u3()) {
                    return false;
                }
            }
            LastPacketTime = System.currentTimeMillis();
        }
        if (connection_side == Connection.Side.IN && enA) {
            if (object instanceof S08PacketPlayerPosLook) {
                return false;
            }
        */
        }
        return true;
    }
}
