package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pw.cinque.CommandSettings.CommandSettings;

import java.lang.reflect.Field;

public class Fp extends GuiSettings
{
    public static CommandSettings Dl;
    public final static Field rightClickDelayTimerField;

    static {
        rightClickDelayTimerField = ReflectionHelper.findField(Minecraft.class, "field_71467_ac", "rightClickDelayTimer");

        if (rightClickDelayTimerField != null) {
            rightClickDelayTimerField.setAccessible(true);
        }
    }
    public Fp()
    {
        super(GuiSettings.a(new char[] { 'F','a','s','t','P','l','a','c','e' }), "", c4.combat2, 0, -1);
        this.avav(Fp.Dl = new CommandSettings(GuiSettings.a(new char[] { 'D', 'e', 'l', 'a', 'y' }), 0, 0, 4, 1));
    }

    @SubscribeEvent
    public void a(final PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if (a.phase == Phase.END) {
            if (mc.inGameHasFocus && rightClickDelayTimerField != null) {
                ItemStack item = mc.thePlayer.getHeldItem();
                if (item == null || !(item.getItem() instanceof ItemBlock)) {
                    return;
                }

                try {
                    int c = (int) Dl.g3tV4l4u3();
                    if (c == 0) {
                        rightClickDelayTimerField.set(mc, 0);
                    } else {
                        if (c == 4) {
                            return;
                        }

                        int d = rightClickDelayTimerField.getInt(mc);
                        if (d == 4) {
                            rightClickDelayTimerField.set(mc, c);
                        }
                    }
                } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {}
            }
        }
    }
}
