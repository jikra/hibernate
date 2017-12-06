package jpa.identifier;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Embeddable
public class PersonKey implements Serializable {

    private String key;
    private String lang;

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public String getLang() {

        return lang;
    }

    public void setLang(String lang) {

        this.lang = lang;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonKey personKey = (PersonKey) o;

        if (key != null ? !key.equals(personKey.key) : personKey.key != null) return false;
        return lang != null ? lang.equals(personKey.lang) : personKey.lang == null;
    }

    @Override
    public int hashCode() {

        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }
}
