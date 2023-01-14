// Decompiled by ImCzf233

package io.fishermen.fpsdisplay.settings;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Covek extends GuiSettings
{
    public float a;
    
    public Covek() {
        super(GuiSettings.a(new char[] { 'F', 'u', 'l', 'l', 'B', 'r', 'i', 'g', 'h', 't' }), "", c4.render, 0, -1);
    }
    
    @SubscribeEvent
    public void a(final TickEvent.PlayerTickEvent a) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        this.a = Covek.mc.gameSettings.gammaSetting;
        Covek.mc.gameSettings.gammaSetting = 10.0f;
    }
    
    @Override
    public void dd() {
        Covek.mc.gameSettings.gammaSetting = this.a;
    }
}
