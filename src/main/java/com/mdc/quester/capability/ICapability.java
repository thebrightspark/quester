package com.mdc.quester.capability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by Alex on 6/12/2017.
 */
public interface ICapability extends INBTSerializable<NBTTagCompound>{
    ResourceLocation getKey();

    ICapabilityProvider getProvider();

    void dataChanged(EntityPlayerMP player);
}
