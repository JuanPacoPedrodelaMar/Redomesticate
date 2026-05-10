package com.evandev.redomesticate.content;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import java.util.UUID;

public record TeleportData(Entity entity, ServerLevel level, UUID ownerUuid) {
}