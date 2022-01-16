package me.pixeldev.ecosmetics.v1_8_R3.track;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;

public class CraftEmptyEntity extends CraftEntity {

    public CraftEmptyEntity(CraftServer server, Entity entity) {
        super(server, entity);
    }

    @Override
    public EntityType getType() {
        return EntityType.ARMOR_STAND;
    }

}
