package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {

    private Role role;
    private Rank rank;
    private Boolean isReadyForNextMissions;

    public boolean isRole(CrewMember crewMember) {
        return role == null || role.equals(crewMember.getRole());
    }

    public boolean isRank(CrewMember crewMember) {
        return rank == null || rank.equals(crewMember.getRank());
    }

    public Boolean readyForNextMissions(CrewMember crewMember) {
        return isReadyForNextMissions == null || isReadyForNextMissions.equals(crewMember.isReadyForNextMissions());
    }

    public static class CrewMemberBuilder extends BaseBuilder<CrewMember, CrewMemberBuilder> {

        protected final CrewMemberCriteria crewMemberCriteria;

        public CrewMemberBuilder() {
            super(new CrewMemberCriteria());
            crewMemberCriteria = (CrewMemberCriteria) criteria;
        }

        public CrewMemberBuilder withRole(Role role) {
            crewMemberCriteria.role = role;
            return this;
        }

        public CrewMemberBuilder withRank(Rank rank) {
            crewMemberCriteria.rank = rank;
            return this;
        }

        public CrewMemberBuilder withReadyForNextMission(boolean readyForNextMission) {
            crewMemberCriteria.isReadyForNextMissions = readyForNextMission;
            return this;
        }

        public CrewMemberCriteria build() {
            return crewMemberCriteria;
        }

        @Override
        protected CrewMemberBuilder getThis() {
            return this;
        }
    }
}
