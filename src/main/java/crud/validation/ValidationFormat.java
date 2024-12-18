package crud.validation;

import crud.dto.DtoUser;
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

    public boolean isValidId(Integer id) {
        return id != null && id > 0;
    }

    public boolean isValidResultMapper(EntityUser entityUser) {
        if(entityUser == null) return false;
        int idUser = entityUser.getId();
        String firstName = entityUser.getFirstName();
        String lastName = entityUser.getLastName();
        String email = entityUser.getEmail();
        int MIN_ID_USER_VALID = 1;

        if(idUser >= MIN_ID_USER_VALID) return false;
        if(firstName == null || firstName.isEmpty()) return false;
        if(lastName == null || lastName.isEmpty()) return false;
        if(email == null || email.isEmpty()) return false;

        return true;
    }

    public boolean isValidDtoUser(DtoUser dtoUser) {
        return dtoUser != null;
    }

}
