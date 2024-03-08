package org.acme.villagers;

public enum Job {
    UNEMPLOYED("Unemployed"),
    FARMER("Farmer"),
    WARRIOR("Soldier"),
    HEALER("Doctor");

    private final String name;

    private Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Job fromString(String name) {
        name = name.toLowerCase();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        for (Job job : Job.values()) {
            if (job.name.equals(name)) {
                return job;
            }
        }
        return UNEMPLOYED;
    }


}
