package com.booking.infrastructure.persistence.repository;

import com.booking.domain.repository.HiRepositoryDomain;
import org.springframework.stereotype.Service;

@Service
public class HiRepoInfrImpl implements HiRepositoryDomain {
    @Override
    public String hi() {
        return "Hello Infrastructure";
    }
}
