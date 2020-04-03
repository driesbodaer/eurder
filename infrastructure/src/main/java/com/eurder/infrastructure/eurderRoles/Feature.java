package com.eurder.infrastructure.eurderRoles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Feature {
    ADMIN_ONLY(List.of(EurderRole.ADMIN));


    private List<EurderRole> roles;

    Feature(List<EurderRole> roleList) {
        this.roles = roleList;
    }

    public List<EurderRole> getRoles() {
        return roles;
    }

    public static List<Feature> getFeaturesForRoles(List<EurderRole> rolesOfUser) {

        List<Feature> listOfAllFeatures = List.of(Feature.values());
        List<Feature> allowedFeatures = new ArrayList<>();
        for (Feature feature : listOfAllFeatures) {
            if (!Collections.disjoint(feature.getRoles(), rolesOfUser)) {
                allowedFeatures.add(feature);
            }
        }
        return allowedFeatures;
    }


}