package com.dawn.foundation.dao;

import com.dawn.foundation.model.SequencePO;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;

public interface SequenceDAO extends CrudRepository<SequencePO, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SequencePO findByCode(String code);

    @Modifying(clearAutomatically = true)
    @Query("update SequencePO p set p.nextValue = p.nextValue + 1 where p.code = ?1")
    void next(String code);
}
