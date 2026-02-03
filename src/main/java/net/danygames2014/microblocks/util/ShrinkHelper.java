package net.danygames2014.microblocks.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.danygames2014.microblocks.multipart.MicroblockMultipartComponent;
import net.danygames2014.microblocks.multipart.PlacementSlot;
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

    public static int calculateCulling(MicroblockMultipartComponent other, Box bounds) {
        if(switch(other.slot.ordinal()) {
            case 0 -> bounds.minY <= 0;
            case 1 -> bounds.maxY >= 1;
            case 2 -> bounds.minZ <= 0;
            case 3 -> bounds.maxZ >= 1;
            case 4 -> bounds.minX <= 0;
            case 5 -> bounds.maxX >= 1;
            default -> false;
        }){
        return 1<<other.slot.ordinal();
        }
        return 0;
    }

    public static int shrinkSide(PlacementSlot slot, PlacementSlot other){
        if(other.ordinal() < 6) {
            return other.ordinal();
        }
        if(slot.ordinal() < 14) {
            int c1 = slot.ordinal()-6;
            int c2 = other.ordinal()-6;
            return switch (c1 ^ c2) {
                case 1 -> c2 & 1;
                case 2 -> 2 | (c2 & 2) >> 1;
                case 4 -> 4 | (c2 & 4) >> 2;
                default -> -1;
            };
        }
        if(other.ordinal() < 14){
            int e1 = slot.ordinal() - 14;
            int c2 = other.ordinal() - 6;
            int ebits = PlacementSlot.unpackEdgeBits(e1);
            if((c2&PlacementSlot.edgeAxisMask(e1)) != ebits){
                return -1;
            }
            return (e1&0xC)>>1|(c2&(~ebits))>>(e1>>2);
        }
        int e1 = slot.ordinal()-14;
        int e2 = other.ordinal()-14;
        int e1bits = PlacementSlot.unpackEdgeBits(e1);
        int e2bits = PlacementSlot.unpackEdgeBits(e2);
        if((e1&0xC) == (e2&0xC))//same axis
        {
            return switch (e1bits^e2bits)
            {
                case 1 -> (e2bits&1) == 0 ?  0 : 1;
                case 2 -> (e2bits&2) == 0 ? 2 : 3;
                case 4 -> (e2bits&4) == 0 ? 4 : 5;
                default -> -1;
            };
        }
        else
        {
            int mask = PlacementSlot.edgeAxisMask(e1)&PlacementSlot.edgeAxisMask(e2);
            if((e1bits&mask) != (e2bits&mask)){
                return -1;
            }

            return switch(e1>>2)
            {
                case 0 -> (e2bits&1) == 0 ? 0 : 1;
                case 1 -> (e2bits&2) == 0 ? 2 : 3;
                case 2 -> (e2bits&4) == 0 ? 4 : 5;
                default -> -1;
            };
        }
    }
}
