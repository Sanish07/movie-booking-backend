package com.sanish.movie_service.mappers;

import com.sanish.movie_service.dtos.Screen.ScreenDto;
import com.sanish.movie_service.entities.Screen;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreenMapper {

    public static ScreenDto mapToScreenDto(Screen screen){
        return new ScreenDto(screen.getScreenNumber(), screen.getCapacity());
    }

    public static Screen mapToScreen(ScreenDto screenDto){
        return Screen.builder()
                .screenNumber(screenDto.getScreenNumber())
                .capacity(screenDto.getCapacity())
                .build();
    }

}
