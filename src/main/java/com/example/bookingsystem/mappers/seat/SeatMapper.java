package com.example.bookingsystem.mappers.seat;

import com.example.bookingsystem.dto.seat.SeatDtoIn;
import com.example.bookingsystem.dto.seat.SeatDtoOut;
import com.example.bookingsystem.entities.seat.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapper {
    SeatDtoOut toDto(SeatEntity seatEntity);
    SeatEntity toEntity(SeatDtoIn seatDtoIn);
    List<SeatDtoOut> toDtoList(List<SeatEntity> seatEntities);
}
