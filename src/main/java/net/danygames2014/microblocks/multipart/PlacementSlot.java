package net.danygames2014.microblocks.multipart;

public enum PlacementSlot {
    FACE_NEG_Y,
    FACE_POS_Y,
    FACE_NEG_Z,
    FACE_POS_Z,
    FACE_NEG_X,
    FACE_POS_X,

    CORNER_BOT_NEG_X_NEG_Z,
    CORNER_BOT_NEG_X_POS_Z,
    CORNER_BOT_POS_X_NEG_Z,
    CORNER_BOT_POS_X_POS_Z,
    CORNER_TOP_NEG_X_NEG_Z,
    CORNER_TOP_NEG_X_POS_Z,
    CORNER_TOP_POS_X_NEG_Z,
    CORNER_TOP_POS_X_POS_Z,
    
    EDGE_BOT_POS_X,
    EDGE_BOT_POS_Z,
    EDGE_BOT_NEG_X,
    EDGE_BOT_NEG_Z,
    EDGE_MID_POS_X,
    EDGE_MID_POS_Z,
    EDGE_MID_NEG_X,
    EDGE_MID_NEG_Z,
    EDGE_TOP_POS_X,
    EDGE_TOP_POS_Z,
    EDGE_TOP_NEG_X,
    EDGE_TOP_NEG_Z;
}
