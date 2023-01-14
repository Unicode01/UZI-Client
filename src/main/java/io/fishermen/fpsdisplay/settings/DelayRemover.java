package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class DelayRemover extends GuiSettings{
    private final Field leftClickCounterField;
    public DelayRemover(){
        super("DelayRemover","",c4.Player,0,-1);
        this.leftClickCounterField = ReflectionHelper.findField(Minecraft.class, "field_71429_W", "leftClickCounter");
        if (this.leftClickCounterField != null) this.leftClickCounterField.setAccessible(true);
    }
    @SubscribeEvent
    public void playerTickEvent(PlayerTickEvent event) {
        if (GuiSettings.isPlayerInGame() && this.leftClickCounterField != null) {
            if (!mc.inGameHasFocus || mc.thePlayer.capabilities.isCreativeMode) {
                return;
            }

            try {
                this.leftClickCounterField.set(mc, 0);
            } catch (IllegalAccessException | IndexOutOfBoundsException ex) {
                ex.printStackTrace();
                this.dd();
            }
        }
    }
}
