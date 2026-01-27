package net.danygames2014.microblocks.item;

import net.danygames2014.nyalib.item.EnhancedPlacementContextItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class MicroblockItem extends TemplateItem implements EnhancedPlacementContextItem {
    public MicroblockItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean useOnBlock(ItemStack itemStack, PlayerEntity playerEntity, World world, int i, int i1, int i2, int i3, Vec3d vec3d) {
        return false;
    }
}
