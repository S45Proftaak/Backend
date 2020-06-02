package com.foodplanner.rest_service.dtos.secretary.response;

import com.foodplanner.rest_service.dtos.UserByDateDTO;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class GetUserByDateDTO extends RepresentationModel<GetUserByDateDTO> {

    private List<UserByDateDTO> dto;

    public GetUserByDateDTO(List<UserByDateDTO> dto) {
        this.dto = dto;
    }

    public GetUserByDateDTO() {
    }

    public List<UserByDateDTO> getDto() {
        return dto;
    }

    public void setDto(List<UserByDateDTO> dto) {
        this.dto = dto;
    }
}
