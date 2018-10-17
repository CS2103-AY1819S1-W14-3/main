package seedu.address.model.appointment;

/**
 * Enumeration of the types of medical procedures
 */
public enum Type {
    PROPAEDEUTIC("PROP"), DIAGNOSTIC("DIAG"), THERAPEUTIC("THP"), SURGICAL("SRG");

    private String abbreviation;

    Type(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
