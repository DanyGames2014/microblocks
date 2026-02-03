package net.danygames2014.microblocks.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class MicroblockBoxUtil {
    public static Box transformCornerMicroblock(Box box, PlacementSlot slot) {
        Box origin = box.copy();
        switch (slot) {
            default -> {
                return origin;
            }
            case CORNER_TOP_NEG_X_NEG_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
            }
            case CORNER_BOT_NEG_X_POS_Z -> {
                origin = BoxUtil.rotateYCounterClockwise(origin, true);
            }
            case CORNER_TOP_NEG_X_POS_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYCounterClockwise(origin, true);
            }
            case CORNER_BOT_POS_X_NEG_Z -> {
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case CORNER_TOP_POS_X_NEG_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case CORNER_BOT_POS_X_POS_Z -> {
                origin = BoxUtil.rotateYClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case CORNER_TOP_POS_X_POS_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
        }
        return origin;
    }

    public static Box transformEdgeMicroblock(Box box, PlacementSlot slot) {
        Box origin = box.copy();
        switch (slot) {
            case EDGE_MID_NEG_X_NEG_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
            }
            case EDGE_MID_NEG_X_POS_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYCounterClockwise(origin, true);
            }
            case EDGE_MID_POS_X_NEG_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case EDGE_MID_POS_X_POS_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            default -> { //EDGE_BOT_NEG_X
                return origin;
            }
            case EDGE_BOT_POS_X -> {
                origin = BoxUtil.rotateYClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);

            }
            case EDGE_TOP_NEG_X -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
            }
            case EDGE_TOP_POS_X -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case EDGE_BOT_NEG_Z -> {
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case EDGE_TOP_NEG_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYClockwise(origin, true);
            }
            case EDGE_BOT_POS_Z -> {
                origin = BoxUtil.rotateYCounterClockwise(origin, true);
            }
            case EDGE_TOP_POS_Z -> {
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateXCounterClockwise(origin, true);
                origin = BoxUtil.rotateYCounterClockwise(origin, true);
            }
        }
        return origin;
    }

    public static Box transformPostMicroblock(Box box, PlacementSlot slot) {
        Box origin = box.copy();
        switch (slot) {
            default -> {
                return origin;
            }
            case POST_X -> {
                origin = BoxUtil.rotateYClockwise(box, true);
            }
            case POST_Y -> {
                origin = BoxUtil.rotateXClockwise(box, true);
            }
        }
        return origin;
    }

    public static Box copy(Box box){
        return Box.create(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public static Box offset(Box box, double x, double y, double z) {
        return Box.create(box.minX + x, box.minY + y, box.minZ + z, box.maxX + x, box.maxY + y, box.maxZ + z);
    }

    public static ObjectArrayList<Box> getCenteredBoxes(ObjectArrayList<Box> boxes, Box renderBounds) {
        double centerX = (renderBounds.minX + renderBounds.maxX) / 2.0;
        double centerY = (renderBounds.minY + renderBounds.maxY) / 2.0;
        double centerZ = (renderBounds.minZ + renderBounds.maxZ) / 2.0;

        double shiftX = 0.5 - centerX;
        double shiftY = 0.5 - centerY;
        double shiftZ = 0.5 - centerZ;

        ObjectArrayList<Box> centeredBoxes = new ObjectArrayList<>();
        for (Box box : boxes) {
            centeredBoxes.add(MicroblockBoxUtil.offset(box, shiftX, shiftY, shiftZ));
        }

        return centeredBoxes;
    }
}
