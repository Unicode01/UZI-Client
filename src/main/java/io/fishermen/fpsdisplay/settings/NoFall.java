package io.fishermen.fpsdisplay.settings;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.CommandSettings.CommandSettings;
import pw.cinque.event.Connection;

public class NoFall extends GuiSettings{
    public static CommandSettings Mode;
    private static boolean En;
    private static double GbY;
    public NoFall(){
        super(GuiSettings.a(new char[]{'N','o','F','a','l','l'}),"",c4.movement,0,-1);
        this.avav(Mode = new CommandSettings("Mode",2,1,2,1));
    }
    @Override
    public void en(){
        En=true;
    }
    @Override
    public void dd(){
        En=false;
    }
    public static void onPacket(Object object, Connection.Side connection_side) {
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
        if(Mode.g3tV4l4u3() == 1 && En){
            if(object instanceof  C03PacketPlayer){
                boolean onGroundT = ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"onGround","field_149474_g");
                if(!onGroundT){
                    if((double)ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"y","field_149477_b") <= GbY-2D){
                        ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer)object,true,"onGround","field_149474_g");
                        GbY = ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"y","field_149477_b");
                    } else if ((double)ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"y","field_149477_b") > GbY) {
                        GbY = ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"y","field_149477_b");
                    }
                }else {
                    GbY = ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"y","field_149477_b");
                }
            }
        }
        if(Mode.g3tV4l4u3() == 2 && En){
            if(object instanceof  C03PacketPlayer){
                boolean onGroundT = ObfuscationReflectionHelper.getPrivateValue(C03PacketPlayer.class,(C03PacketPlayer) object,"onGround","field_149474_g");
                if(onGroundT){
                    ObfuscationReflectionHelper.setPrivateValue(C03PacketPlayer.class,(C03PacketPlayer)object,false,"onGround","field_149474_g");
                }
            }
        }
    }

    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent a){
        if(!this.getStat()){
            return;
        }
        if(!GuiSettings.isPlayerInGame()){
            return;
        }
    }
}
