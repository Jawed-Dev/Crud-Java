package crud.mapper;

import crud.dto.DtoUser;
import crud.entity.EntityUser;

public class MapperUser {
    public static int MIN_ID_USER_VALID = 1;

    public EntityUser dtoToEntityUser(DtoUser dtoUser) {
        EntityUser entityUser = new EntityUser();
        if(dtoUser.getId() >= MIN_ID_USER_VALID) entityUser.setId(dtoUser.getId());
        if(dtoUser.getFirstName() != null) entityUser.setFirstName(dtoUser.getFirstName());
        if(dtoUser.getLastName() != null) entityUser.setLastName(dtoUser.getLastName());
        if(dtoUser.getCurrentEmail() != null) entityUser.setEmail(dtoUser.getCurrentEmail());
        if(dtoUser.getCreateAt() != null) entityUser.setCreateAt(dtoUser.getCreateAt());
        return entityUser;
    }
}
