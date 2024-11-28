package crud.validation;

import crud.entity.EntityUser;

import java.util.regex.Pattern;

public class ValidationFormat {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,255}\\.[A-Za-z]{2,6}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ' -]{1,50}$");


    public boolean isValidEmail(String email) {
        if(email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidFirstName(String firstName) {
        if(firstName == null) return false;
        return NAME_PATTERN.matcher(firstName).matches();
    }

    public boolean isValidLastName(String lastName) {
        if(lastName == null) return false;
        return NAME_PATTERN.matcher(lastName).matches();
    }

    public boolean isValidId(int id) {
        return id > 0;
    }

    public boolean isValidEntityUser(EntityUser entityUser) {
        if(entityUser == null) {
            return false;
        }
        return true;
    }

}
