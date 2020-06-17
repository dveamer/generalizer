package com.dveamer.generalizer;

import com.dveamer.generalizer.url.UrlGeneralizerFactory;

import java.util.List;

public class GeneralizerFactory {

    public Generalizer<String, String> createUrlGeneralizer(List<String> paths) {
        return new UrlGeneralizerFactory().create(paths);
    }

}
