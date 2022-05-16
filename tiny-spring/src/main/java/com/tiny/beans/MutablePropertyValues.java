package com.tiny.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MutablePropertyValues implements PropertyValues, Serializable {

    private final List<PropertyValue> propertyValueList;

    /**
     * Creates a new empty MutablePropertyValues object.
     * <p>Property values can be added with the {@code add} method.
     * @see #add(String, Object)
     */
    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    /**
     * Add a PropertyValue object, replacing any existing one for the
     * corresponding property or getting merged with it (if applicable).
     * @param propertyName name of the property
     * @param propertyValue value of the property
     * @return this in order to allow for adding multiple property values in a chain
     */
    public MutablePropertyValues add(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
        return this;
    }

    /**
     * Add a PropertyValue object, replacing any existing one for the
     * corresponding property or getting merged with it (if applicable).
     * @param pv the PropertyValue object to add
     * @return this in order to allow for adding multiple property values in a chain
     */
    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        for (int i = 0; i < this.propertyValueList.size(); i++) {
            PropertyValue currentPv = this.propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())) {
                pv = mergeIfRequired(pv, currentPv);
                setPropertyValueAt(pv, i);
                return this;
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }

    /**
     * Modify a PropertyValue object held in this object.
     * Indexed from 0.
     */
    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.set(i, pv);
    }

    @Override
    public boolean contains(String propertyName) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return Collections.unmodifiableList(this.propertyValueList).iterator();
    }

    /**
     * Merges the value of the supplied 'new' {@link PropertyValue} with that of
     * the current {@link PropertyValue} if merging is supported and enabled.
     * @see Mergeable
     */
    private PropertyValue mergeIfRequired(PropertyValue newPv, PropertyValue currentPv) {
        Object value = newPv.getValue();
        if (value instanceof Mergeable) {
            Mergeable mergeable = (Mergeable) value;
            if (mergeable.isMergeEnabled()) {
                Object merged = mergeable.merge(currentPv.getValue());
                return new PropertyValue(newPv.getName(), merged);
            }
        }
        return newPv;
    }
}
