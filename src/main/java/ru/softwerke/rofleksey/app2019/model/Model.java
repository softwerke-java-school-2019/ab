package ru.softwerke.rofleksey.app2019.model;

/**
 * Model common interface
 */
public interface Model {
    /**
     * @return model id
     */
    long getId();

    /**
     * Sets model's id
     *
     * @param id id
     */
    void setId(long id);


    /**
     * precalculates fields after object deserialization and validation
     */
    void init();
}
