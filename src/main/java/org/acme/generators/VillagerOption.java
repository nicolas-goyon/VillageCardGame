package org.acme.generators;

public class VillagerOption {
    private boolean name;
    private boolean surname;
    private boolean age;
    private boolean job;
    private boolean characteristic; // Change this to an enum or a class
    private boolean stomachSize;
    private boolean health;
    private boolean baseHealth;
    private boolean magic;
    private boolean damage;
    private boolean workingForce;

    public VillagerOption(){
        this.name = false;
        this.surname = false;
        this.age = false;
        this.job = false;
        this.characteristic = false;
        this.stomachSize = false;
        this.baseHealth = false;
        this.magic = false;
        this.workingForce = false;
    }

    public VillagerOption setName(){
        this.name = true;
        return this;
    }

    public VillagerOption setSurname(){
        this.surname = true;
        return this;
    }

    public VillagerOption setAge(){
        this.age = true;
        return this;
    }

    public VillagerOption setJob(){
        this.job = true;
        return this;
    }

    public VillagerOption setCharacteristic(){
        this.characteristic = true;
        return this;
    }

    public VillagerOption setStomachSize(){
        this.stomachSize = true;
        return this;
    }

    public VillagerOption setHealth(){
        this.health = true;
        return this;
    }

    public VillagerOption setBaseHealth(){
        this.baseHealth = true;
        return this;
    }

    public VillagerOption setMagic(){
        this.magic = true;
        return this;
    }

    public VillagerOption setDamage(){
        this.damage = true;
        return this;
    }

    public VillagerOption setWorkingForce(){
        this.workingForce = true;
        return this;
    }

    public boolean isName() {
        return name;
    }

    public boolean isSurname() {
        return surname;
    }

    public boolean isAge() {
        return age;
    }

    public boolean isJob() {
        return job;
    }

    public boolean isCharacteristic() {
        return characteristic;
    }

    public boolean isStomachSize() {
        return stomachSize;
    }

    public boolean isHealth() {
        return health;
    }

    public boolean isBaseHealth() {
        return baseHealth;
    }

    public boolean isMagic() {
        return magic;
    }


    public boolean isDamage() {
        return damage;
    }

    public boolean isWorkingForce() {
        return workingForce;
    }

    public boolean isAll(){
        return name && surname && age && job && characteristic && stomachSize && health && baseHealth && magic && damage && workingForce;
    }

    public VillagerOption setAll(){
        this.name = true;
        this.surname = true;
        this.age = true;
        this.job = true;
        this.characteristic = true;
        this.stomachSize = true;
        this.health = true;
        this.baseHealth = true;
        this.magic = true;
        this.damage = true;
        this.workingForce = true;
        return this;
    }

}
