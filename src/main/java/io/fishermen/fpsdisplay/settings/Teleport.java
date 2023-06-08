package io.fishermen.fpsdisplay.settings;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.event.Connection;

import java.util.ArrayList;

public class Teleport extends GuiSettings{
    private ArrayList<EntityPlayer> Players = new ArrayList<EntityPlayer>();
    private static boolean TPpacket;
    private static boolean ifen;
    private static boolean CancelPacket;
    private static boolean Follow;
    private static EntityPlayer FollowPlayer;
    public Teleport(){
        super("Teleport","",c4.Tests,0,-1);
    }
    @Override
    public void en(){
        ifen=true;
        Follow = false;
    }
    @Override
    public void dd(){
        ifen=false;
        Follow = false;
    }
    @SubscribeEvent
    public void a(TickEvent.PlayerTickEvent e){
        if(!Follow || FollowPlayer == null) return;
        mc.thePlayer.setPositionAndRotation(FollowPlayer.posX,FollowPlayer.posY,FollowPlayer.posZ,FollowPlayer.rotationYaw,FollowPlayer.rotationPitch);
    }

    public static boolean onPacket(Object object, Connection.Side connection_side) {
        CancelPacket = false;
        if(ifen && connection_side == Connection.Side.OUT && object instanceof C01PacketChatMessage){
            C01PacketChatMessage packet= (C01PacketChatMessage) object;
            String Message = packet.getMessage();
            String[] temp1;
            EntityPlayer To = null;
            temp1 = Message.split(" ");
            if(Message.toLowerCase().startsWith(".tp")){
                TPpacket = true;
                CancelPacket = true;
                for(int i =0; i < temp1.length ; i++){
                    if(i != 0 && TPpacket){
                        for (EntityPlayer p : mc.theWorld.playerEntities){
                            if(p.getDisplayNameString().equalsIgnoreCase(temp1[i])){
                                To = p;
                                break;
                            }
                        }
                        if(temp1[i].equalsIgnoreCase("f")){
                            Follow = true;
                            continue;
                        }
                        if(To == null && !temp1[i].equalsIgnoreCase("f")){
                            mc.thePlayer.addChatMessage(new ChatComponentText("NoSuchPlayer:" + temp1[i]));
                            continue;
                        }
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(To.posX,To.posY,To.posZ,mc.thePlayer.onGround));
                        mc.thePlayer.setPosition(To.posX,To.posY,To.posZ);
                        if(Follow){
                            FollowPlayer = To;
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

}
