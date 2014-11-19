package pl.gda.pg.eti.kask.javaee.jsf.entities.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author maciek
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodNameValidator.class)
public @interface GoodName {

  String message() default "{pl.gda.pg.eti.kask.javaee.jsf.entities.validators.GoodName.BAD_NAME}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String regex() default "";

  int minLength() default 3;

  int maxLength() default 12;

}
