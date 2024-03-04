package org.acme.villagers;

import org.acme.SoldierAttackResult;
import org.acme.interfaces.Damageable;
import org.acme.interfaces.Soldier;
import org.acme.villagers.characteristics.Characteristic;

import java.util.List;

public class Villager extends Soldier {
    private String name;
    private String surname;
    private int age;
    private Job job;
    private List<Characteristic> characteristic; // Change this to an enum or a class
    private int stomachSize;
    private int baseHealth;
    private int magic;

    private int workingForce;


    private Villager(Builder builder) {
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
        this.workingForce = builder.workingForce;
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

    public Job getJob() {
        return job;
    }

    public List<Characteristic> getCharacteristic() {
        return characteristic;
    }

    public int getStomachSize() {
        return stomachSize;
    }


    public int getBaseHealth() {
        return baseHealth;
    }


    public int getMagic() {
        return magic;
    }

    public int getWorkingForce() {
        return workingForce;
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

    public void setJob(Job job) {
        this.job = job;
    }

    public void setCharacteristic(List<Characteristic> characteristic) {
        this.characteristic = characteristic;
    }

    public void setStomachSize(int stomacSize) {
        this.stomachSize = stomacSize;
    }


    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }


    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setWorkingForce(int workingForce) {
        this.workingForce = workingForce;
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




    public int heal(int healthPoints) {
        if (healthPoints <= 0) {
            return 0;
        }
        int healthPointsLeft = this.baseHealth - this.health;
        this.health += healthPoints;

        if (this.health > this.baseHealth) {
            this.health = this.baseHealth;
        }

        return Math.max(healthPoints - healthPointsLeft, 0);
    }

    public void applyCharacteristics() {
        for (Characteristic c : characteristic) {
            c.apply(this);
        }
    }

    public int produceFood() {
        if(!this.job.equals(Job.FARMER)) {
            throw new IllegalStateException("Only farmers can produce food");
        }
        return this.workingForce;
    }

    public void heal(List<Villager> villagers) {
        if(!this.job.equals(Job.HEALER)) {
            throw new IllegalStateException("Only healers can heal");
        }
        int healthPoints = this.magic;
        for (Villager villager : villagers) {
            if (healthPoints <= 0) {
                break;
            }
            healthPoints = villager.heal(healthPoints);
        }
    }

    @Override
    public SoldierAttackResult attack(List<? extends Damageable> damageable) {
        if (!this.job.equals(Job.WARRIOR)) {
            throw new IllegalStateException("Only soldiers can attack");
        }
        return super.attack(damageable);
    }




    public static class Builder {
        private String name;
        private String surname;
        private Integer age;
        private Job job;
        private List<Characteristic> characteristic; // Change this to an enum or a class
        private Integer stomachSize;
        private Integer health;
        private Integer baseHealth;
        private Integer damage;
        private Integer magic;
        private Integer workingForce;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder job(Job job) {
            this.job = job;
            return this;
        }

        public Builder characteristic(List<Characteristic> characteristic) {
            this.characteristic = characteristic;
            return this;
        }

        public Builder stomachSize(Integer stomachSize) {
            this.stomachSize = stomachSize;
            return this;
        }

        public Builder health(Integer health) {
            this.health = health;
            return this;
        }

        public Builder baseHealth(Integer baseHealth) {
            this.baseHealth = baseHealth;
            return this;
        }

        public Builder damage(Integer damage) {
            this.damage = damage;
            return this;
        }

        public Builder magic(Integer magic) {
            this.magic = magic;
            return this;
        }

        public Builder workingForce(Integer workingForce) {
            this.workingForce = workingForce;
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
                    magic == null ||
                    workingForce == null
            ) {
                throw new IllegalStateException("All fields must be set");
            }
            return new Villager(this);
        }
    }


}
