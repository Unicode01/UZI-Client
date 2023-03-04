package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import pw.cinque.keystrokes.Client;

import java.io.File;

public class SaveConfigs extends GuiSettings{
    public SaveConfigs(){
        super("SaveConfigs","",c4.Tests,0,-1);
    }
    @Override
    public void en(){
        pw.cinque.keystrokes.Client.saveHacks();
        mc.thePlayer.addChatMessage(new ChatComponentText("ConfigSaved: "+ pw.cinque.keystrokes.Client.HACKS.getPath()));
        this.t();
    }
}
