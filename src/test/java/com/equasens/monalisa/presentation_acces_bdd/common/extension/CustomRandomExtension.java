package com.equasens.monalisa.presentation_acces_bdd.common.extension;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import io.github.glytching.junit.extension.random.RandomBeansExtension;

public class CustomRandomExtension extends RandomBeansExtension {

    public CustomRandomExtension() {
        super(EnhancedRandomBuilder
                .aNewEnhancedRandomBuilder()
                .randomize(String.class, StringRandomizer.aNewStringRandomizer(5))
                .collectionSizeRange(5, 5)
                .excludeField(f -> "id".equals(f.getName()))
                .build());
    }
}
