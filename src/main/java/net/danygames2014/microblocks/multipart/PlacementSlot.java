package net.danygames2014.microblocks.multipart;

public enum PlacementSlot {
    FACE_POS_Y,
    FACE_NEG_Y,
    FACE_POS_Z,
    FACE_NEG_Z,
    FACE_POS_X,
    FACE_NEG_X,

    // 6
    CORNER_BOT_NEG_X_NEG_Z,
    CORNER_TOP_NEG_X_NEG_Z,
    CORNER_BOT_NEG_X_POS_Z,
    CORNER_TOP_NEG_X_POS_Z,
    CORNER_BOT_POS_X_NEG_Z,
    CORNER_TOP_POS_X_NEG_Z,
    CORNER_BOT_POS_X_POS_Z,
    CORNER_TOP_POS_X_POS_Z,

    // 15 - 1
    EDGE_MID_NEG_X_NEG_Z,
    EDGE_MID_NEG_X_POS_Z,
    EDGE_MID_POS_X_NEG_Z,
    EDGE_MID_POS_X_POS_Z,
    EDGE_BOT_NEG_X,
    EDGE_BOT_POS_X,
    EDGE_TOP_NEG_X,
    EDGE_TOP_POS_X,
    EDGE_BOT_NEG_Z,
    EDGE_TOP_NEG_Z,
    EDGE_BOT_POS_Z,
    EDGE_TOP_POS_Z,

    POST_X,
    POST_Y,
    POST_Z,

    INVALID;

    private static final PlacementSlot[] VALUES = values();

    public static PlacementSlot fromOrdinal(int ordinal) {
        if (ordinal < 0) {
            ordinal = 0;
        }
        if(ordinal >= VALUES.length) {
            ordinal = VALUES.length - 1;
        }
        return VALUES[ordinal];
    }

    public int getPriority() {
        if(ordinal() < 6) {
            return  2;
        } else if(ordinal() < 15) {
            return 1;
        }
        return 0;
    }

    public static int edgeAxisMask(int e)
    {
        return switch (e >> 2) {
            case 0 -> 6;
            case 1 -> 5;
            case 2 -> 3;
            default -> 0;
        };
    }

    public static int unpackEdgeBits(int e)
    {
        return switch (e >> 2) {
            case 0 -> (e & 3) << 1;
            case 1 -> (e & 2) >> 1 | (e & 1) << 2;
            case 2 -> (e & 3);
            default -> 0;
        };
    }

    public static int packEdgeBits(int e, int bits)
    {
        return switch (e >> 2) {
            case 0 -> e & 0xC | bits >> 1;
            case 1 -> e & 0xC | (bits & 4) >> 2 | (bits & 1) << 1;
            case 2 -> e & 0xC | bits & 3;
            default -> 0;
        };
    }
}
