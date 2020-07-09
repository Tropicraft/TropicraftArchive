package net.tropicraft.core.common.item;

public enum AshenMasks {
    SQUARE_ZORD("Square Zord"),
    HORN_MONKEY("Horn Monkey"),
    OBLONGATRON("Oblongatron"),
    HEADINATOR("Headinator"),
    SQUARE_HORN("Square Horn"),
    SCREW_ATTACK("Screw Attack"),
    THE_BRAIN("The Brain"),
    BAT_BOY("Bat Boy"),
    INVADER("Invader"),
    MOJO("Mojo"),
    WARTHOG("Warthog"),
    THE_HEART("The Heart"),
    ENIGMA("Enigma")
    ;

    private String name;

    public static AshenMasks[] VALUES = values();

    AshenMasks(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
