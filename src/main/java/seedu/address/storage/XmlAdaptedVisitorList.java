package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB-friendly adapted version of the VisitorList.
 * @author GAO JIAXIN666
 */
@XmlRootElement
public class XmlAdaptedVisitorList implements Iterable<XmlAdaptedVisitor> {
    @XmlElement(required = true)
    private List<XmlAdaptedVisitor> visitorList;

    public XmlAdaptedVisitorList() {
        this.visitorList = new ArrayList<>();
    }

    public XmlAdaptedVisitorList(List<XmlAdaptedVisitor> vl) {
        this.visitorList = new ArrayList<>(vl);
    }

    /** Defensive copy constructor. */
    public XmlAdaptedVisitorList(XmlAdaptedVisitorList vl) {
        this(vl.visitorList);
    }

    /** Setter method to hot swap the internal list. */
    public void setVisitorList(List<XmlAdaptedVisitor> vl) {
        this.visitorList = new ArrayList<>(vl);
    }

    @Override
    public Iterator<XmlAdaptedVisitor> iterator() {
        return visitorList.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedVisitorList)) {
            return false;
        }

        XmlAdaptedVisitorList xavl = (XmlAdaptedVisitorList) o;
        return visitorList.equals(xavl.visitorList);
    }
}
