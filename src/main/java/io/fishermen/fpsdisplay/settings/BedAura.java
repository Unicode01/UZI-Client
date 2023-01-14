package io.fishermen.fpsdisplay.settings;

import pw.cinque.CommandSettings.CommandSettings;
import java.util.TimerTask;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedAura extends GuiSettings{
    private java.util.Timer t;
    private BlockPos m = null;
    private final long per = 600L;
    public static CommandSettings Range;
    public BedAura(){
        super(GuiSettings.a(new char[]{'B','e','d','A','u','r','a'}),"",c4.Player,0,-1);
        BedAura.Range = new CommandSettings(GuiSettings.a(new char[]{'R','a','n','g','e'}),5.0D, 2.0D, 10.0D, 1.0D);
        this.avav(Range);
    }
    @Override
    public void en(){
        (this.t = new java.util.Timer()).scheduleAtFixedRate(this.ta(), 0L, 600L);
    }
    @Override
    public void dd(){
        if (this.t != null) {
            this.t.cancel();
            this.t.purge();
            this.t = null;
        }

        this.m = null;
    }
    public TimerTask ta() {
        return new TimerTask() {
            public void run() {
                int ra = (int)BedAura.Range.g3tV4l4u3();
                for(int y = ra; y >= -ra; --y) {
                    for(int x = -ra; x <= ra; ++x) {
                        for(int z = -ra; z <= ra; ++z) {
                            if (GuiSettings.isPlayerInGame()) {
                                BlockPos p = new BlockPos(mc.thePlayer.posX + (double) x, mc.thePlayer.posY + (double) y, mc.thePlayer.posZ + (double) z);
                                boolean bed = mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                                if (BedAura.this.m == p) {
                                    if (!bed) {
                                        BedAura.this.m = null;
                                    }
                                } else if (bed) {
                                    BedAura.this.mi(p);
                                    BedAura.this.m = p;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
    }
    private void mi(BlockPos p) {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
    }
}
