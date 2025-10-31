package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;

import com.debuggeando_ideas.best_travel_2025.api.models.responses.HotelResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.HotelEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IHotelService;
import com.debuggeando_ideas.best_travel_2025.util.SortType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class HotelService implements IHotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {

        PageRequest pageRequest = null;

        switch (sortType) {
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            case NONE -> pageRequest = PageRequest.of(page, size);
        }

        return this.hotelRepository.findAll(pageRequest).map(this::toHotelResponse);
    }

    @Override
    public Set<HotelResponse> readLessPrice(BigDecimal price) {
        return this.hotelRepository.findByPriceLessThan(price).stream().map(this::toHotelResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.hotelRepository.findByPriceBetween(min, max).stream().map(this::toHotelResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<HotelResponse> readByRatingGreaterThan(Integer rating) {
        return this.hotelRepository.findByRatingGreaterThan(rating).stream().map(this::toHotelResponse).collect(Collectors.toSet());
    }

    private HotelResponse toHotelResponse(HotelEntity hotelEntity) {
        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotelEntity, hotelResponse);
        return hotelResponse;
    }
}
