package com.mdc.quester.player;

import com.mdc.quester.capability.ICapability;
import com.mdc.quester.capability.Storage;
import com.mdc.quester.capability.quest.CapabilityQuest;
import com.mdc.quester.capability.quest.ICapQuests;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class QuesterCapability {
    @CapabilityInject(ICapQuests.class)
    public static Capability<ICapQuests> QUESTS = null;

    private static ArrayList<Capability<? extends ICapability>> CAPABILITIES = new ArrayList<>();

    public static ArrayList<Capability<? extends ICapability>> getCapabilties(){
        if(CAPABILITIES.isEmpty()){
            CAPABILITIES.add(QUESTS);
        }
        return CAPABILITIES;
    }

    public static int getCapabilityID(Capability<? extends ICapability> capability){
        return getCapabilties().indexOf(capability);
    }

    public static Capability<? extends ICapability> getCapabilityFromID(int id){
        ArrayList<Capability<? extends ICapability>> capList = getCapabilties();
        if(id < 0) id = 0;
        else if(id >= capList.size()) id = capList.size() - 1;
        return capList.get(id);
    }

    private static <T extends ICapability> void regCapability(Class<T> capInterface, Callable<? extends T> capFactory){
        CapabilityManager.INSTANCE.register(capInterface, new Storage<>(), capFactory);
    }

    public static void init(){
        regCapability(ICapQuests.class, CapabilityQuest::new);
    }
}
