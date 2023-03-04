package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;
import pw.cinque.timechanger.ClickListener;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Dada extends GuiSettings
{
    private static CommandSettings M;
    private static ClickListener noMoveClicksValue;
    private static CommandSettings noSprintValue;
    private static boolean isChoosing;
    private static boolean ena;
    private static ArrayList<Object> Packets = new ArrayList<Object>();
    private static boolean lastInvOpen = false;
    private static boolean invOpen = false;
    private static ArrayList<C03PacketPlayer> blinkPacketList = new ArrayList<C03PacketPlayer>();
    private static ArrayList<C0EPacketClickWindow> packetListYes = new ArrayList<C0EPacketClickWindow>();
    /*
    protected Random r;
    */

    public Dada() {
        super(GuiSettings.a(new char[] { 'I', 'n', 'v', 'm', 'o', 'v', 'e' }), "", c4.Player, 0, -1);
        this.M = new CommandSettings("Mode",1,1,4,1);
        this.avav(M);
        this.avav(noMoveClicksValue = new ClickListener("NoMoveClicks",false));
        this.avav(noSprintValue = new CommandSettings("NoSprint",3,1,3,1));
        /*
        this.r = new Random();
         */
    }
    @Override
    public void en(){
        ena=true;
        blinkPacketList.clear();
        lastInvOpen = false;
        invOpen = false;
    }
    @Override
    public void dd(){
        ena=false;
        blinkPacketList.clear();
        lastInvOpen = false;
        invOpen = false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame() || !ena){
            return true;
        }
        boolean SetCancelPacket = false;

        Object packet = object;

        lastInvOpen = invOpen;
        if (packet instanceof S2DPacketOpenWindow || (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)object).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)) {
            invOpen = true;
            if (noSprintValue.g3tV4l4u3() == 2) {
                if (mc.thePlayer.isSprinting()) {
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (mc.thePlayer.isSneaking()) {
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
            }
        }
        if (packet instanceof S2EPacketCloseWindow || packet instanceof C0DPacketCloseWindow) {
            invOpen = false;
            if (noSprintValue.g3tV4l4u3() == 2) {
                if (mc.thePlayer.isSprinting()) {
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                }
                if (mc.thePlayer.isSneaking()) {
                    mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                }
            }
        }

        if (M.g3tV4l4u3() == 3) {
                if (packet instanceof C16PacketClientStatus && ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                    SetCancelPacket = true;
                }
                if (packet instanceof C0DPacketCloseWindow) {
                    SetCancelPacket = true;
                }

                if (packet instanceof C0EPacketClickWindow) {
                    packetListYes.clear();
                    packetListYes.add((C0EPacketClickWindow) packet);

                    SetCancelPacket = true;

                    mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    for (C0EPacketClickWindow packetYes : packetListYes){
                        mc.getNetHandler().addToSendQueue(packetYes);
                    }
                    packetListYes.clear();
                    mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));

                }
            }
        if (M.g3tV4l4u3() == 1) {
                if (packet instanceof C16PacketClientStatus &&
                ((C16PacketClientStatus)packet).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT){
                    SetCancelPacket=true;
                }
        }
        if(M.g3tV4l4u3() == 2) {
            if (packet instanceof C03PacketPlayer) {
                if (lastInvOpen) {
                    blinkPacketList.add((C03PacketPlayer) packet);
                    SetCancelPacket=true;
                } else if (blinkPacketList.size() != 0) {
                    blinkPacketList.add((C03PacketPlayer) packet);
                    SetCancelPacket=true;
                    for (C03PacketPlayer packetYes : blinkPacketList){
                        mc.getNetHandler().addToSendQueue(packetYes);
                    }
                    blinkPacketList.clear();
                }
            }
        }
        return !SetCancelPacket;
    }

    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            Packets.clear();
            return;
        }
        if(!isChoosing && Packets != null){
            for (Object P : Packets){
                mc.getNetHandler().addToSendQueue((Packet) P);
            }
            Packets.clear();
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null) {
            if (mc.currentScreen instanceof GuiChat) {
                return;
            }
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
            EntityPlayerSP var1;
            if (Keyboard.isKeyDown(208) && mc.thePlayer.rotationPitch < 90.0F) {
                var1 = mc.thePlayer;
                var1.rotationPitch += 6.0F;
            }

            if (Keyboard.isKeyDown(200) && mc.thePlayer.rotationPitch > -90.0F) {
                var1 = mc.thePlayer;
                var1.rotationPitch -= 6.0F;
            }

            if (Keyboard.isKeyDown(205)) {
                var1 = mc.thePlayer;
                var1.rotationYaw += 6.0F;
            }

            if (Keyboard.isKeyDown(203)) {
                var1 = mc.thePlayer;
                var1.rotationYaw -= 6.0F;
            }
        }
    }
}
