package com.jobboard.library.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private GenericMapper() {
    }

    public static <T, U> U map(T source, Class<U> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}
