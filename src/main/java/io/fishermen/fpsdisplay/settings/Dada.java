package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;

import java.util.ArrayList;

public class Dada extends GuiSettings
{
    private static CommandSettings M;
    private static boolean isChoosing;
    private static boolean ena;
    private static ArrayList<Object> Packets = new ArrayList<Object>();
    /*
    protected Random r;
    */

    public Dada() {
        super(GuiSettings.a(new char[] { 'I', 'n', 'v', 'm', 'o', 'v', 'e' }), "", c4.Player, 0, -1);
        this.M = new CommandSettings("Mode",1,1,2,1);
        this.avav(M);
        /*
        this.r = new Random();
         */
    }
    @Override
    public void en(){
        ena=true;
    }
    @Override
    public void dd(){
        ena=false;
    }
    public static boolean onPacket(Object object, Connection.Side connection_side) {
        if(!ena || M.g3tV4l4u3() != 2) return true;
        if(connection_side == Connection.Side.OUT && object instanceof C0EPacketClickWindow && !isChoosing){
            isChoosing = true;
            return true;
        }
        if(connection_side == Connection.Side.OUT && object instanceof C0DPacketCloseWindow && isChoosing){
            Packets.add(object);
            isChoosing = false;
            return false;
        }
        return true;
    }

    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
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
            ///KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
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
