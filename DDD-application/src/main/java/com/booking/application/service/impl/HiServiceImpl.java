package com.booking.application.service.impl;

import com.booking.application.service.HiService;
import com.booking.domain.service.HiServiceDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiServiceImpl implements HiService {
    @Autowired
    private HiServiceDomain hiServiceDomain;
    @Override
    public String hi() {
        return this.hiServiceDomain.hi();
    }
}
