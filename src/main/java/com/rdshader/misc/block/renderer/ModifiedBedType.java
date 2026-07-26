package com.rdshader.misc.block.renderer;

import com.rdshader.misc.RDSMisc;
import net.minecraft.resources.Identifier;

public enum ModifiedBedType {
    DOUBLE("double", 2),
    QUADRUPLE("quadruple", 4),
    TRIPLE_DEPRESSED("triple_depressed", 8),
    FOURFOLD_DEPRESSED("fourfold_depressed", 16),
    FIVEFOLD_DEPRESSED("fivefold_depressed", 32),
    SIXFOLD_DEPRESSED("sixfold_depressed", 64),
    SEVENFOLD_DEPRESSED("sevenfold_depressed", 128),
    EIGHTFOLD_DEPRESSED("eightfold_depressed", 256);

    final String id;
    final int intensity;

    ModifiedBedType(String id, int times) {
        this.id = id;
        this.intensity = times;
    }

    public String getId() {
        return id;
    }

    public Identifier getIdentifier() {
        return Identifier.fromNamespaceAndPath(RDSMisc.MODID, "entity/bed/" + this.id);
    }

    public int getIntensity() {
        return this.intensity;
    }
}
