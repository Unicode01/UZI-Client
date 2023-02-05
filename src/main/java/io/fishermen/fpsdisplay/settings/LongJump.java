package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class LongJump extends GuiSettings {
    public LongJump() {
        super(GuiSettings.a(new char[]{'L', 'o', 'n', 'g', 'J', 'u', 'm', 'p'}), "", c4.movement, 0, -1);

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
            if (mc.thePlayer.onGround && !mc.thePlayer.capabilities.isFlying) {
                mc.thePlayer.jump();
            }
        }
    }
}
