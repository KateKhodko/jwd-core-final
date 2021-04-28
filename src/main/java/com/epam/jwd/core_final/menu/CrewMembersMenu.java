package com.epam.jwd.core_final.menu;

import com.epam.jwd.core_final.context.DataLoader;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.CrewMemberUpdateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class CrewMembersMenu {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private final MainMenu mainMenu;

    public CrewMembersMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    void printCrewMembers() {
        List<CrewMember> crewMembers = CrewServiceImpl.getInstance().findAllCrewMembers();
        crewMembers.forEach(System.out::println);
    }

    void updateCrewMembers() {
        CrewMember crewMember = findCrewMemberById();
        if (crewMember != null) {
            System.out.println(crewMember);
            Role role = inputRole();
            Rank rank = inputRank();
            CrewMember newCrewMember = new CrewMemberFactory().create(
                    crewMember.getId(),
                    crewMember.getName(),
                    role,
                    rank);
            try {
                CrewServiceImpl.getInstance().updateCrewMemberDetails(newCrewMember);
                String updateMemberInfo = "Crew member updated : \n" + newCrewMember;
                System.out.println(updateMemberInfo);
                LOGGER.info(updateMemberInfo);
            } catch (CrewMemberUpdateException e) {
                String updateMemberError = "Error of crew member update : " + e.getMessage();
                LOGGER.error(updateMemberError);
                System.out.println(updateMemberError);
            }
        }
    }

    private Role inputRole() {
        System.out.println("\nRoles");
        for (Role role : Role.values()) {
            System.out.println(role.getId() + ". " + role.getName());
        }
        do {
            System.out.println("Input id of a role : ");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                try {
                    return Role.resolveRoleById(i);
                } catch (UnknownEntityException e) {
                    String roleError = "No role with such id";
                    LOGGER.error(roleError);
                    System.out.println(roleError);
                }
            }
        } while (true);
    }

    private Rank inputRank() {
        System.out.println("\nRanks");
        for (Rank rank : Rank.values()) {
            System.out.println(rank.getId() + ". " + rank.getName());
        }
        do {
            System.out.println("Input id of a rank : ");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                try {
                    return Rank.resolveRankById(i);
                } catch (UnknownEntityException e) {
                    String rankError = "No rank with such id";
                    LOGGER.error(rankError);
                    System.out.println(rankError);
                }
            }
        } while (true);
    }

    private void printSubmenu() {
        String subMenu = "Input (e - for exit or crew member id): ";
        System.out.println(subMenu);
    }

    private CrewMember findCrewMemberById() {
        printSubmenu();
        do {
            Scanner scan = new Scanner(System.in);
            if (mainMenu.exitToMenu(scan)) {
                return null;
            }
            if (scan.hasNextInt()) {
                int i = scan.nextInt();
                CrewMemberCriteria criteria = new CrewMemberCriteria.CrewMemberBuilder().withId(i).build();
                Optional<CrewMember> optionalCrewMember = CrewServiceImpl.getInstance().findCrewMemberByCriteria(criteria);
                if (optionalCrewMember.isPresent()) {
                    return optionalCrewMember.get();
                }
            }
            String noMemberFoundMessage = "No member with such id";
            LOGGER.error(noMemberFoundMessage);
            System.out.println(noMemberFoundMessage);
        } while (true);
    }
}
