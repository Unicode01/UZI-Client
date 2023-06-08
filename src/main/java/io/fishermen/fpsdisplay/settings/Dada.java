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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class Dada extends GuiSettings
{
    private static CommandSettings M;
    private static ClickListener noMoveClicksValue;
    private static ClickListener noSprintValue;
    private static boolean isChoosing;
    private static boolean ena;
    private static ArrayList<Object> Packets = new ArrayList<Object>();
    private static boolean lastInvOpen = false;
    private static boolean invOpen = false;
    private static ArrayList<C0EPacketClickWindow> packetListYes = new ArrayList<C0EPacketClickWindow>();
    private static boolean  passPacket;
    private static int windowID;
    /*
    protected Random r;
    */

    public Dada() {
        super(GuiSettings.a(new char[] { 'I', 'n', 'v', 'm', 'o', 'v', 'e' }), "", c4.Player, 0, -1);
        this.M = new CommandSettings("Mode",1,1,2,1);
        this.avav(M);
        this.avav(noMoveClicksValue = new ClickListener("NoMoveClicks",false));
        this.avav(noSprintValue = new ClickListener("NoSprint",false));
        /*
        this.r = new Random();
         */
    }
    @Override
    public void en(){
        ena=true;
        lastInvOpen = false;
        invOpen = false;
    }
    @Override
    public void dd(){
        ena=false;
        lastInvOpen = false;
        invOpen = false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame() || !ena){
            return true;
        }
        boolean SetCancelPacket = false;
        if(connection_side == Connection.Side.OUT){
            if(M.g3tV4l4u3() == 2 && passPacket){
                if(object instanceof C0DPacketCloseWindow){
                    passPacket = false;
                }
                return true;
            }
            if(object instanceof C0BPacketEntityAction && noSprintValue.i()){
                if(((C0BPacketEntityAction)object).getAction() == C0BPacketEntityAction.Action.START_SPRINTING){
                    SetCancelPacket = true;
                }
            }
            if(M.g3tV4l4u3() == 1){
                return true;
            }
            if(M.g3tV4l4u3() == 2){
                if(object instanceof C0EPacketClickWindow){
                    packetListYes.add((C0EPacketClickWindow) object);
                    windowID = ((C0EPacketClickWindow) object).getWindowId();
                    SetCancelPacket = true;
                }
                if(object instanceof C16PacketClientStatus){
                    if(((C16PacketClientStatus)object).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT){
                        SetCancelPacket = true;
                    }
                }
                if(object instanceof C0DPacketCloseWindow){
                    passPacket = true;
                    mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                    for (Packet P : packetListYes){
                        mc.getNetHandler().addToSendQueue(P);
                    }
                    mc.getNetHandler().addToSendQueue(new C0DPacketCloseWindow(windowID));
                    SetCancelPacket = true;
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
