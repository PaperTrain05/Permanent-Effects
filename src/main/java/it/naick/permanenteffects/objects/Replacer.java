package it.naick.permanenteffects.objects;

import lombok.Getter;

public class Replacer {
    @Getter
    private final String placeholder;
    @Getter
    private final String replacement;

    public Replacer(String placeholder, String replacement) {
        this.placeholder = placeholder;
        this.replacement = replacement;
    }
}
