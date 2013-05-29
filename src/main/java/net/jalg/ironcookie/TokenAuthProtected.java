package net.jalg.ironcookie;

import java.lang.annotation.*;

import javax.ws.rs.NameBinding;

/**
 * Annotation to bind filter to resource method.
 * 
 * @author Jan Algermissen, http://jalg.net
 * 
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
@NameBinding
public @interface TokenAuthProtected {
}