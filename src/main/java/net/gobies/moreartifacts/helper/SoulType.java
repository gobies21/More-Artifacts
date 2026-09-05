package net.gobies.moreartifacts.helper;

public enum SoulType {
    SHADOW("Shadow"),
    BLOOD("Blood"),
    MATRIX("Matrix"),
    UNKNOWN("Unknown");

    SoulType(String key) {
        this.key = key;
    }

    public final String key;
}
