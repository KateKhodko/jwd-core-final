package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {

    private final Role role;
    private final Rank rank;
    private boolean isReadyForNextMissions = true;

    public CrewMember(Long id, String name, Role role, Rank rank) {
        super(id, name);
        this.role = role;
        this.rank = rank;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public void setNotReadyForNextMissions() {
        isReadyForNextMissions = false;
    }

    public boolean isReadyForNextMissions() {
        return isReadyForNextMissions;
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", rank=" + rank +
                ", isReadyForNextMissions=" + isReadyForNextMissions +
                '}';
    }
}
