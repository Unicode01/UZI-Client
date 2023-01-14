// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import pw.cinque.timechanger.ClickListener;

import java.util.Random;

public class Jok extends GuiSettings
{
    private static ClickListener K;

    public Jok() {
        super(GuiSettings.a(new char[] { 'S', 'p', 'r', 'i', 'n', 't' }), "", c4.movement, 0, -1);
        Jok.K = new ClickListener(GuiSettings.a(new char[] { 'S', 'u', 'p', 'e', 'r' }), false);
        this.avav(K);
    }
    /*
    @Override
    void onUpdate () {
    final Minecraft mc = Minecraft.getMinecraft();
    final EntityPlayerSP thePlayer = Jok.mc.thePlayer;
    if(mc.gameSettings.keyBindForward.isPressed()
    ){
        thePlayer.setSprinting(true);
	}
     */

	@SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(K.i()){
            if(mc.thePlayer.isSprinting()) return;
            if(mc.gameSettings.keyBindForward.isKeyDown()){
                mc.thePlayer.setSprinting(true);
            }
            if(mc.gameSettings.keyBindBack.isKeyDown()){
                mc.thePlayer.setSprinting(true);
            }
            if(mc.gameSettings.keyBindLeft.isKeyDown()){
                mc.thePlayer.setSprinting(true);
            }
            if(mc.gameSettings.keyBindRight.isKeyDown()){
                mc.thePlayer.setSprinting(true);
            }
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        if(mc.inGameHasFocus) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }

		/*
        try {
            final Field field = Minecraft.class.getDeclaredField("rightClickDelayTimer");
            field.setAccessible(true);
            final Field field2 = field;
            final Minecraft mc = Jok.mc;
            field2.set(Minecraft.getMinecraft(), 0);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
		*/
    }
    @Override
    public void dd(){
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        mc.thePlayer.setSprinting(false);
    }
}
