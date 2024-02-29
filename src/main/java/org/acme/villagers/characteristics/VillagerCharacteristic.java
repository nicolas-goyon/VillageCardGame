package org.acme.villagers.characteristics;

import org.acme.villagers.Villager;

public enum VillagerCharacteristic implements Characteristic {
    SMALL_STOMACH {
        @Override
        public void apply(Villager villager) {
            villager.setStomachSize(Math.round(villager.getStomachSize() * 0.8f));
        }

        @Override
        public String getName() {
            return "Small stomach";
        }
    },

    BIG_STOMACH {
        @Override
        public void apply(Villager villager) {
            villager.setStomachSize(Math.round(villager.getStomachSize() * 1.2f));
        }

        @Override
        public String getName() {
            return "Big stomach";
        }
    },

    THIN_SKIN {
        @Override
        public void apply(Villager villager) {
            villager.setBaseHealth(Math.round(villager.getBaseHealth() * 0.8f));
            villager.setHealth(villager.getBaseHealth());
        }

        @Override
        public String getName() {
            return "Thin skin";
        }
    },

    HEALTHY {
        @Override
        public void apply(Villager villager) {
            villager.setBaseHealth(Math.round(villager.getBaseHealth() * 1.2f));
            villager.setHealth(villager.getBaseHealth());
        }

        @Override
        public String getName() {
            return "Healthy";
        }
    },

    WEAK {
        @Override
        public void apply(Villager villager) {
            villager.setDamage(Math.round(villager.getDamage() * 0.8f));
        }

        @Override
        public String getName() {
            return "Weak";
        }
    },

    STRONG {
        @Override
        public void apply(Villager villager) {
            villager.setDamage(Math.round(villager.getDamage() * 1.2f));
        }

        @Override
        public String getName() {
            return "Strong";
        }
    },

    SMART {
        @Override
        public void apply(Villager villager) {
            villager.setMagic(Math.round(villager.getMagic() * 1.2f));
        }

        @Override
        public String getName() {
            return "Smart";
        }
    },

    DUMB {
        @Override
        public void apply(Villager villager) {
            villager.setMagic(Math.round(villager.getMagic() * 0.8f));
        }

        @Override
        public String getName() {
            return "Dumb";
        }
    },

    HARDWORKING {
        @Override
        public void apply(Villager villager) {
            villager.setWorkingForce(Math.round(villager.getWorkingForce() * 1.2f));
        }

        @Override
        public String getName() {
            return "Hardworking";
        }
    },

    LAZYBONES {
        @Override
        public void apply(Villager villager) {
            villager.setWorkingForce(Math.round(villager.getWorkingForce() * 0.8f));
        }

        @Override
        public String getName() {
            return "Lazybones";
        }
    }
}
