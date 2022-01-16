package me.pixeldev.ecosmetics.v1_8_R3.track;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class EmptyEntity extends Entity {

    public EmptyEntity() {
        super(null);
    }

    @Override
    public CraftEntity getBukkitEntity() {
        return new CraftEmptyEntity(
                (CraftServer) Bukkit.getServer(),
                this
        );
    }

    @Override
    protected void h() {

    }

    @Override
    protected void a(NBTTagCompound nbtTagCompound) {

    }

    @Override
    protected void b(NBTTagCompound nbtTagCompound) {

    }

}
