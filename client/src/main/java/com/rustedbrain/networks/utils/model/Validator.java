package com.rustedbrain.networks.utils.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RustedBrain on 21.01.2016.
 */
public enum Validator {

    NAME("^([A-Z]{1}[a-z]+)|([А-Я]{1}[а-я]+)$"),
    LOGIN("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$"),
    EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    PASSWORD("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    private Pattern pattern;
    private Matcher matcher;

    Validator(String regex) {
        pattern = Pattern.compile(regex);
    }

    public String validate(final String word) {
        matcher = pattern.matcher(word);
        if (!matcher.matches())
            throw new IllegalArgumentException();
        return word;
    }

}
