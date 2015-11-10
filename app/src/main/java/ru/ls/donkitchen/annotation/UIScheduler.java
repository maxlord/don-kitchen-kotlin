package ru.ls.donkitchen.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 01.04.2015
 */
@Qualifier
@Documented
@Retention (RetentionPolicy.RUNTIME)
public @interface UIScheduler {
}
