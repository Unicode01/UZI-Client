package io.fishermen.fpsdisplay;

import io.fishermen.fpsdisplay.settings.BetterSword;
import io.fishermen.fpsdisplay.settings.InvCleaner;
import io.fishermen.fpsdisplay.settings.das;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvEvent {
    public static boolean BetterSword;
    public static boolean AutoArmor;
    public static boolean InvCleaner;
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent a){
        if(BetterSword){
            io.fishermen.fpsdisplay.settings.BetterSword.Sword();
        }
        if(AutoArmor){
            das.Armor();
        }
        if(InvCleaner){
            io.fishermen.fpsdisplay.settings.InvCleaner.CleanInv();
        }
    }
}
