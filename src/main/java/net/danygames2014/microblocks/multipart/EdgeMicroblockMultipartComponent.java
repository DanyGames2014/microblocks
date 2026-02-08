package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.EdgeMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class EdgeMicroblockMultipartComponent extends MicroblockMultipartComponent{
    public static final EdgeMicroblockModel MODEL = new EdgeMicroblockModel();

    public EdgeMicroblockMultipartComponent(){}

    public EdgeMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 8;
    }

    @Override
    public ObjectArrayList<ItemStack> getDropList() {
        ObjectArrayList<ItemStack> drops = new ObjectArrayList<>();

        int sizeToDrop = this.getSize();
        while (sizeToDrop > 1) {
            if (sizeToDrop >= 8) {
                drops.add(createStack(MicroblockItemType.SLAB_STRIP));
                sizeToDrop -= 8;
            } else if (sizeToDrop >= 4) {
                drops.add(createStack(MicroblockItemType.PANEL_STRIP));
                sizeToDrop -= 4;
            } else {
                drops.add(createStack(MicroblockItemType.STRIP));
                sizeToDrop -= 2;
            }
        }

        return drops;
    }

    @Override
    public MicroblockItemType getClosestItemType() {
        return switch (this.getSize()) {
            case 1, 2, 3 -> MicroblockItemType.STRIP;
            case 4, 5, 6, 7 -> MicroblockItemType.PANEL_STRIP;
            default -> MicroblockItemType.SLAB_STRIP;
        };
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
