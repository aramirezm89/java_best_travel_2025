package com.debuggeando_ideas.best_travel_2025.infrastructure.Services;


import com.debuggeando_ideas.best_travel_2025.api.models.responses.FlyResponse;
import com.debuggeando_ideas.best_travel_2025.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel_2025.domain.repositories.FlyRepository;
import com.debuggeando_ideas.best_travel_2025.infrastructure.abstract_services.IFlyService;
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

@Transactional(readOnly = true)
@Service
@Slf4j
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;

    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch (sortType) {
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
            case NONE -> pageRequest = PageRequest.of(page, size);
        }

        return this.flyRepository.findAll(pageRequest).map(this::toFlyResponse);
    }

    @Override
    public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
        return this.flyRepository.findByOriginNameIgnoreCaseAndDestinyNameIgnoreCase(origin, destiny)
                .stream()
                .map(this::toFlyResponse)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return this.flyRepository.findByPriceLessThan(price).stream().map(this::toFlyResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
        return this.flyRepository.findByPriceBetween(min, max).stream().map(this::toFlyResponse).collect(Collectors.toSet());
    }

    private FlyResponse toFlyResponse(FlyEntity flyEntity) {
        var FlyResponse = new FlyResponse();
        BeanUtils.copyProperties(flyEntity, FlyResponse);

        return FlyResponse;
    }


}
