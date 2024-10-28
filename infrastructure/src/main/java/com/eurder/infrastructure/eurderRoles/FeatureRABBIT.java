package com.eurder.infrastructure.eurderRoles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum FeatureRABBIT {
    ADMIN_ONLY(List.of(EurderRole.ADMIN));


    private List<EurderRole> roles;

    FeatureRABBIT(List<EurderRole> roleList) {
        this.roles = roleList;
    }

    public List<EurderRole> getRoles() {
        return roles;
    }

    public static List<FeatureRABBIT> getFeaturesForRoles(List<EurderRole> rolesOfUser) {

        List<FeatureRABBIT> listOfAllFeatureRABBITS = List.of(FeatureRABBIT.values());
        List<FeatureRABBIT> allowedFeatureRABBITS = new ArrayList<>();
        for (FeatureRABBIT featureRABBIT : listOfAllFeatureRABBITS) {
            if (!Collections.disjoint(featureRABBIT.getRoles(), rolesOfUser)) {
                allowedFeatureRABBITS.add(featureRABBIT);
            }
        }
        return allowedFeatureRABBITS;
    }


}