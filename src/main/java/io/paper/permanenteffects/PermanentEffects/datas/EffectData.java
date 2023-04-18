package io.paper.permanenteffects.PermanentEffects.datas;

import lombok.Data;

import java.util.LinkedList;

@Data
public class EffectData {

    private final int slot;
    private final String displayName;
    private final int maxLevel;
    private final LinkedList<Double> levelCosts;

}
