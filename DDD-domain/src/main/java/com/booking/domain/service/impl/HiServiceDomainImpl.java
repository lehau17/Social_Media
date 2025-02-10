package com.booking.domain.service.impl;

import com.booking.domain.repository.HiRepositoryDomain;
import com.booking.domain.service.HiServiceDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiServiceDomainImpl implements HiServiceDomain {


    @Autowired
    private HiRepositoryDomain hiRepositoryDomain;
    @Override
    public String hi() {
        return this.hiRepositoryDomain.hi();
    }
}
