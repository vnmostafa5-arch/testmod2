package com.example.client.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

// No mixin injection needed for Session Timer - we use Fabric events instead
@Mixin(Minecraft.class)
public class ExampleClientMixin {
}
