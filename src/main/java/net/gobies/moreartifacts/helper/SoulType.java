package net.gobies.moreartifacts.helper;

public enum SoulType {
    ENVY("Envy"),
    WRATH("Wrath"),
    PRIDE("Pride"),
    GREED("Greed"),
    GLUTTONY("Gluttony"),
    SLOTH("Sloth"),
    LUST("Lust"),
    UNKNOWN("Unknown");

    SoulType(String key) {
        this.key = key;
    }

    public final String key;
}
