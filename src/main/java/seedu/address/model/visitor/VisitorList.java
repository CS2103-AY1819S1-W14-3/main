package seedu.address.model.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@@auth0r GAO JIAXIN666
/**
 * The visitor list to store all the visitors for patient.
 */
public class VisitorList {
    private List<Visitor> visitorList;


    public VisitorList() {
        visitorList = new ArrayList<Visitor>();
    }

    public VisitorList(VisitorList visitorList) {
        Objects.requireNonNull(visitorList);
        this.visitorList = new ArrayList<>(Objects.requireNonNull(visitorList.visitorList));
    }

    public VisitorList(List<Visitor> visitors) {
        Objects.requireNonNull(visitors);
        this.visitorList = new ArrayList<>(visitors);
    }

    public int getSize() {
        return visitorList.size();
    }

    /**
     * Checks if a given visitor name exist.
     * @param visitor The visitor to check.
     * @return true if visitor is contained in the visitor list, false if not exist.
     */
    public boolean contains(Visitor visitor) {
        for (Visitor v : visitorList) {
            if (v.equals(visitor)) {
                return true;
            }
        }
        return false;
    }

    public void add (Visitor visitor) {
        visitorList.add(visitor);
    }

    public void remove(Visitor visitor) {
        this.visitorList.remove(visitor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Visitor m : visitorList) {
            sb.append(m.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof VisitorList) {
            VisitorList ml = (VisitorList) o;
            return visitorList.size() == ml.visitorList.size()
                    && visitorList.containsAll(ml.visitorList);
        }

        return false;
    }
}
