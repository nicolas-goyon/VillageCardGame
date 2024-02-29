package org.acme;

import java.util.List;

public class Villager {
    private String name;
    private String surname;
    private int age;
    private String job; // Change this to an enum or a class
    private List<String> characteristic; // Change this to an enum or a class
    private int stomachSize;
    private int health;
    private int baseHealth;
    private int damage;
    private int magic;


    private Villager(VillagerBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.age = builder.age;
        this.job = builder.job;
        this.characteristic = builder.characteristic;
        this.stomachSize = builder.stomachSize;
        this.health = builder.health;
        this.baseHealth = builder.baseHealth;
        this.damage = builder.damage;
        this.magic = builder.magic;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public String getJob() {
        return job;
    }

    public List<String> getCharacteristic() {
        return characteristic;
    }

    public int getStomachSize() {
        return stomachSize;
    }

    public int getHealth() {
        return health;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getDamage() {
        return damage;
    }

    public int getMagic() {
        return magic;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setCharacteristic(List<String> characteristic) {
        this.characteristic = characteristic;
    }

    public void setStomachSize(int stomacSize) {
        this.stomachSize = stomacSize;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }


    // Methods
    
    public int eat(int disponibleFood) {
        if (disponibleFood <= 0) {
            return 0;
        }

        if (disponibleFood >= this.stomachSize) {
            this.health = this.baseHealth;
            return disponibleFood - this.stomachSize;
        }

        return disponibleFood;
    }

    public int takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage must be a positive number");
        this.health -= damage;
        if (this.health < 0) this.health = 0;
        return this.health;
    }



    public static class VillagerBuilder {
        private String name;
        private String surname;
        private Integer age;
        private String job; // Change this to an enum or a class
        private List<String> characteristic; // Change this to an enum or a class
        private Integer stomachSize;
        private Integer health;
        private Integer baseHealth;
        private Integer damage;
        private Integer magic;


        public VillagerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public VillagerBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public VillagerBuilder age(Integer age) {
            this.age = age;
            return this;
        }

        public VillagerBuilder job(String job) {
            this.job = job;
            return this;
        }

        public VillagerBuilder characteristic(List<String> characteristic) {
            this.characteristic = characteristic;
            return this;
        }

        public VillagerBuilder stomachSize(Integer stomachSize) {
            this.stomachSize = stomachSize;
            return this;
        }

        public VillagerBuilder health(Integer health) {
            this.health = health;
            return this;
        }

        public VillagerBuilder baseHealth(Integer baseHealth) {
            this.baseHealth = baseHealth;
            return this;
        }

        public VillagerBuilder damage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public VillagerBuilder magic(Integer magic) {
            this.magic = magic;
            return this;
        }

        public Villager build() {
            if (name == null ||
                    surname == null ||
                    age == null ||
                    job == null ||
                    characteristic == null ||
                    stomachSize == null ||
                    health == null ||
                    baseHealth == null ||
                    damage == null ||
                    magic == null
            ) {
                throw new IllegalStateException("All fields must be set");
            }
            return new Villager(this);
        }
    }


}
