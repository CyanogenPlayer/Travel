package dev.cyan.travel.converter;

import dev.cyan.travel.DTO.CountryDTO;
import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.DTO.RoomDTO;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;

public class DTOConverter {
    public static CountryDTO convertCountryToDTO(Country country) {
        return new CountryDTO(country.getId(), country.getName());
    }

    public static HotelDTO convertHotelToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getCountryId());
    }

    public static RoomDTO convertRoomToDTO(Room room) {
        return new RoomDTO(room.getId(), room.getRoomNumber(), room.getCapacity(), room.getHotel().getId());
    }
}
