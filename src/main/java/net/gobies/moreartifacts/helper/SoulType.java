package net.gobies.moreartifacts.helper;

public enum SoulType {
    ENVY("Envy"),
    WRATH("Wrath"),
    UNKNOWN("Unknown");

    SoulType(String key) {
        this.key = key;
    }

    public final String key;
}
