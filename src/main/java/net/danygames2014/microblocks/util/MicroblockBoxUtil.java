package net.danygames2014.microblocks.util;

import net.danygames2014.microblocks.multipart.PlacementSlot;
import net.danygames2014.nyalib.util.BoxUtil;
import net.minecraft.util.math.Box;

public class MicroblockBoxUtil {
    public static Box transformEdgeMicroblock(Box box, PlacementSlot slot) {
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
}
