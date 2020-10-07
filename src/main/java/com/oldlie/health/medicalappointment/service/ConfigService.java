package com.oldlie.health.medicalappointment.service;

import com.oldlie.health.medicalappointment.model.db.Config;
import com.oldlie.health.medicalappointment.model.db.repository.ConfigRepository;
import com.oldlie.health.medicalappointment.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    public String getValue(String key) {
        return this.configRepository.findOne(
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(Config.CONF_KEY), key)
        ).map(Config::getConfValue).orElse(null);
    }

    public BaseResponse update(String key, String value) {
        this.configRepository.findOne(
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(Config.CONF_KEY), key)
        ).ifPresent(x -> {
            x.setConfValue(value);
            this.configRepository.save(x);
        });
        return new BaseResponse();
    }
}
