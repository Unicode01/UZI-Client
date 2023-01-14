package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.server.*;
import net.minecraft.util.ChatComponentText;
import pw.cinque.event.Connection;
import pw.cinque.timechanger.ClickListener;

public class PacketSniffer extends GuiSettings{
    private static boolean en;
    public static ClickListener Active_in;
    public static ClickListener Active_out;
    public PacketSniffer(){
        super("PacketSniffer","",c4.Tests,0,-1);
        this.avav(Active_in = new ClickListener("Sniff In Packets",true));
        this.avav(Active_out = new ClickListener("Sniff Out Packets",true));
    }
    @Override
    public void dd(){
        en=false;
    }
    @Override
    public void en(){
        en=true;
    }
    public static void onPacket(Object object, Connection.Side connection_side) {
        if (en) {
            if(connection_side == Connection.Side.IN && Active_in.i()){
                mc.thePlayer.addChatMessage(new ChatComponentText("[In]"+object.toString()));
            }
            if(connection_side == Connection.Side.OUT && Active_out.i()){
                mc.thePlayer.addChatMessage(new ChatComponentText("[Out]"+object.toString()));
            }
        }
    }
}
