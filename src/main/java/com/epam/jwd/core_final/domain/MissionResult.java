package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

public enum MissionResult implements BaseEntity {
    CANCELLED(1L),
    FAILED(2L),
    PLANNED(3L),
    IN_PROGRESS(4L),
    COMPLETED(5L);

    private final Long id;

    MissionResult(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return name();
    }

    /**
     * todo via java.lang.enum methods!
     *
     * @throws UnknownEntityException if such id does not exist
     */
    public static MissionResult resolveMissionResultById(int id) throws UnknownEntityException {
        for (MissionResult missionResult : MissionResult.values()) {
            if (missionResult.getId() == id) {
                return missionResult;
            }
        }
        throw new UnknownEntityException(null, new Object[]{id});
    }
}
