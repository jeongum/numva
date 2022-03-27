package com.egongil.numva.core.queryrepository;

import com.egongil.numva.api.dto.response.FindSafetyInfoResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egongil.numva.core.entity.SafetyInfo.QSafetyInfo.safetyInfo;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SafetyInfoQueryRepositoryImpl implements SafetyInfoQueryRepository{

    private final JPAQueryFactory query;

    @Override
    public List<FindSafetyInfoResDto> findAllWithUserId(Long userId) {
        return query
                .select(Projections.constructor(FindSafetyInfoResDto.class,
                        safetyInfo.id,
                        safetyInfo.name,
                        safetyInfo.memo.content,
                        safetyInfo.safetyNumber.safetyNumber))
                .from(safetyInfo)
                .where(safetyInfo.user.id.eq(userId))
                .fetch();
    }
}
