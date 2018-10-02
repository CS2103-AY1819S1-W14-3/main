package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple
 * commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /** Ordered prefixes for the addmh command. */
    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_MED_HISTORY = new Prefix("mh/");
    // todo may want to rename this to better suit other named conventions
}
