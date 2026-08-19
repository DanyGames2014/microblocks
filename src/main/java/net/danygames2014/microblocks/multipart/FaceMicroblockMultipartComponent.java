package net.danygames2014.microblocks.multipart;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.item.MicroblockItemType;
import net.danygames2014.microblocks.multipart.model.FaceMicroblockModel;
import net.danygames2014.microblocks.multipart.model.MicroblockModel;
import net.danygames2014.microblocks.util.DirectionUtil;
import net.danygames2014.nyalib.block.voxelshape.VoxelShape;
import net.danygames2014.nyalib.multipart.MultipartComponent;
import net.danygames2014.nyalib.multipart.MultipartSlot;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.math.Direction;

public class FaceMicroblockMultipartComponent extends MicroblockMultipartComponent{

    public static final FaceMicroblockModel MODEL = new FaceMicroblockModel();

    public FaceMicroblockMultipartComponent(){}

    public FaceMicroblockMultipartComponent(Block block, int meta, MultipartSlot slot, int size) {
        super(block, meta, slot, size);
    }

    @Override
    public int getMaxSize() {
        return 14;
    }

    @Override
    public ObjectArrayList<ItemStack> getDropList() {
        ObjectArrayList<ItemStack> drops = new ObjectArrayList<>();

        int sizeToDrop = this.getSize();
        while (sizeToDrop > 1) {
            if (sizeToDrop >= 8) {
                drops.add(createStack(MicroblockItemType.SLAB));
                sizeToDrop -= 8;
            } else if (sizeToDrop >= 4) {
                drops.add(createStack(MicroblockItemType.PANEL));
                sizeToDrop -= 4;
            } else {
                drops.add(createStack(MicroblockItemType.COVER));
                sizeToDrop -= 2;
            }
        }

        return drops;
    }

    @Override
    public MicroblockItemType getClosestItemType() {
        return switch (this.getSize()) {
            case 1, 2, 3 -> MicroblockItemType.COVER;
            case 4, 5, 6, 7 -> MicroblockItemType.PANEL;
            default -> MicroblockItemType.SLAB;
        };
    }

    @Override
    public MicroblockModel getMicroblockModel() {
        return MODEL;
    }

    @Override
    public void onPlaced() {
        super.onPlaced();

        System.out.println(slot);
        System.out.println(DirectionUtil.faceSlotToDirection(slot));
        System.out.println(slot.slotIndex);
    }

    @Override
    public boolean occlusionTest(MultipartComponent component) {
        if(component instanceof FaceMicroblockMultipartComponent face) {
            if(Direction.byId(face.slot.slotIndex).getAxis() == Direction.byId(slot.slotIndex).getAxis()) {
                if(face.size + size > 16) {
                    return false;
                }
            }
        }
        return super.occlusionTest(component);
    }
}
