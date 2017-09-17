//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.17 at 08:54:41 PM EEST 
//


package org.sonar.plugins.tsql.rules.custom;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ruleMatchType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ruleMatchType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Full"/>
 *     &lt;enumeration value="TextOnly"/>
 *     &lt;enumeration value="TextAndClass"/>
 *     &lt;enumeration value="ClassOnly"/>
 *     &lt;enumeration value="Strict"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ruleMatchType")
@XmlEnum
public enum RuleMatchType {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Full")
    FULL("Full"),
    @XmlEnumValue("TextOnly")
    TEXT_ONLY("TextOnly"),
    @XmlEnumValue("TextAndClass")
    TEXT_AND_CLASS("TextAndClass"),
    @XmlEnumValue("ClassOnly")
    CLASS_ONLY("ClassOnly"),
    @XmlEnumValue("Strict")
    STRICT("Strict");
    private final String value;

    RuleMatchType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RuleMatchType fromValue(String v) {
        for (RuleMatchType c: RuleMatchType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
