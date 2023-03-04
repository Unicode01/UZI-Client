package io.fishermen.fpsdisplay.settings;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.world.ChunkEvent;
import pw.cinque.keystrokes.Client;

import java.io.File;

public class LoadConfigs extends GuiSettings{
    public LoadConfigs(){
        super("LoadConfigs","",c4.Tests,0,-1);
    }
    @Override
    public void en(){
        pw.cinque.keystrokes.Client.loadHacks();
        mc.thePlayer.addChatMessage(new ChatComponentText("ConfigLoaded:" + pw.cinque.keystrokes.Client.HACKS.getPath()));
        this.t();
    }
}
