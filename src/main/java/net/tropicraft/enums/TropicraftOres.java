package net.tropicraft.enums;

import net.minecraft.util.IStringSerializable;

public enum TropicraftOres implements IStringSerializable {
    AZURITE, EUDIALYTE, ZIRCON;

    @Override
    public String getName() {
    	if (this == AZURITE) return "blockOreAzurite";
    	else if (this == EUDIALYTE) return "blockOreEudialyte";
    	else return "blockOreZircon";
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
};
