package org.acme.generators;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class VillagerOptionTest {

    @Test
    void  testVillagerOption(){
        VillagerOption villagerOption = new VillagerOption().setAge();
        assertTrue(villagerOption.isAge());
        assertFalse(villagerOption.isDamage());
        assertFalse(villagerOption.isMagic());
        assertFalse(villagerOption.isName());
    }

    @Test
    void  testVillagerOption2(){
        VillagerOption villagerOption = new VillagerOption().setDamage();
        assertFalse(villagerOption.isAge());
        assertTrue(villagerOption.isDamage());
        assertFalse(villagerOption.isMagic());
        assertFalse(villagerOption.isName());
        assertFalse(villagerOption.isAll());
    }
}
