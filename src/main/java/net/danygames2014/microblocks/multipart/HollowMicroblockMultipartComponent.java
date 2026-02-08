package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.HollowMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class HollowMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final HollowMicroblockModel MODEL = new HollowMicroblockModel();

    public HollowMicroblockMultipartComponent(){}

    public HollowMicroblockMultipartComponent(Block block, int meta, PlacementSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 16;
    }

    @Override
    public ObjectArrayList<ItemStack> getDropList() {
        ObjectArrayList<ItemStack> drops = new ObjectArrayList<>();

        int sizeToDrop = this.getSize();
        while (sizeToDrop > 1) {
            if (sizeToDrop >= 8) {
                drops.add(createStack(MicroblockItemType.HOLLOW_SLAB));
                sizeToDrop -= 8;
            } else if (sizeToDrop >= 4) {
                drops.add(createStack(MicroblockItemType.HOLLOW_PANEL));
                sizeToDrop -= 4;
            } else {
                drops.add(createStack(MicroblockItemType.HOLLOW_COVER));
                sizeToDrop -= 2;
            }
        }

        return drops;
    }

    @Override
    public MicroblockItemType getClosestItemType() {
        return switch (this.getSize()) {
            case 1, 2, 3 -> MicroblockItemType.HOLLOW_COVER;
            case 4, 5, 6, 7 -> MicroblockItemType.HOLLOW_PANEL;
            default -> MicroblockItemType.HOLLOW_SLAB;
        };
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }
}
