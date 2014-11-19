package pl.gda.pg.eti.kask.javaee.jsf.entities.validators;

import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.logging.Level;

/**
 * @author maciek
 */
@Log
public class GoodNameValidator implements ConstraintValidator<GoodName, String> {

  private String regex;
  private int minLength;
  private int maxLength;

  @Override
  public void initialize(GoodName constraintAnnotation) {
    regex = constraintAnnotation.regex();
    minLength = constraintAnnotation.minLength();
    maxLength = constraintAnnotation.maxLength();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value.length() < minLength) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
        "{pl.gda.pg.eti.kask.javaee.jsf.entities.validators.GoodName.TOO_SHORT}"
      ).addConstraintViolation();
      return false;
    }
    if (value.length() > maxLength) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
          "{pl.gda.pg.eti.kask.javaee.jsf.entities.validators.GoodName.TOO_LONG}"
      ).addConstraintViolation();
      return false;
    }

    if (!regex.equals("") && !value.matches(regex)) {
      return false;
    }

    return true;
  }
}
