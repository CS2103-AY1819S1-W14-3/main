package seedu.address.model.appointment;

import java.util.ArrayList;

/**
 * TypeFields for testing Type
 */
public class TypeFields {
    /**
     * Produces an ArrayList of types from the enum values of Type
     * @return ArrayList of types in String
     */
    public ArrayList<String> typeFields() {
        Type[] types = Type.values();
        ArrayList<String> typesList = new ArrayList<>();
        for (Type type : types) {
            typesList.add(type.getAbbreviation());
        }
        return typesList;
    }
}
