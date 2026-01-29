package net.danygames2014.microblocks.util;

import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.minecraft.util.math.Box;
import net.modificationstation.stationapi.api.util.math.Direction;

public class ShrinkHelper {
    public static boolean shouldShrink(MicroblockMultipartComponent component, MicroblockMultipartComponent other){
//        if(component.getPriority() != other.getPriority()) {
//            return component.getPriority() < other.getPriority();
//        }

        int componentPriority = component.getPriority();
        int otherPriority = other.getPriority();
        if(componentPriority != otherPriority) {
            return componentPriority < otherPriority;
        }
        if(component.slot.ordinal() < 6) {
            if(component.isTransparent() != other.isTransparent()) {
                return component.isTransparent();
            }
            if(component.getSize() != other.getSize()) {
                return component.getSize() < other.getSize();
            }
        }
        else {
            if(component.getSize() != other.getSize()) {
                return component.getSize() < other.getSize();
            }
            if(component.isTransparent() != other.isTransparent()) {
                return component.isTransparent();
            }
        }
        return component.slot.ordinal() < other.slot.ordinal();

    }

    public static Box shrink(Box target, Box other, Direction face){
        switch (face) {
            case DOWN -> {
                if(target.minY < other.maxY) target.minY = other.maxY;
            }
            case UP -> {
                if (target.maxY > other.minY) target.maxY = other.minY;
            }
            case EAST -> {
                if (target.minZ < other.maxZ) target.minZ = other.maxZ;
            }
            case WEST -> {
                if (target.maxZ > other.minZ) target.maxZ = other.minZ;
            }
            case NORTH -> {
                if (target.minX < other.maxX) target.minX = other.maxX;
            }
            case SOUTH -> {
                if (target.maxX > other.minX) target.maxX = other.minX;
            }
        }
        return target;
    }


}
