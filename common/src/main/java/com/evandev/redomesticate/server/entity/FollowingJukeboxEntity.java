package com.evandev.redomesticate.server.entity;

import com.evandev.redomesticate.registry.ModEnchantments;
import com.evandev.redomesticate.registry.ModEntities;
import com.evandev.redomesticate.util.TameableUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class FollowingJukeboxEntity extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> FOLLOWING_UUID = SynchedEntityData.defineId(FollowingJukeboxEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<ItemStack> JUKEBOX_ITEM = SynchedEntityData.defineId(FollowingJukeboxEntity.class, EntityDataSerializers.ITEM_STACK);

    public FollowingJukeboxEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public FollowingJukeboxEntity(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(ModEntities.FOLLOWING_JUKEBOX.get(), world);
    }

    public void tick() {
        super.tick();
        Entity following = getFollowing();
        this.setPos(this.position().add(this.getDeltaMovement()));
        this.setYRot(this.getYRot() + 2F);
        if (!level().isClientSide) {
            if (following != null) {
                float width = following.getBbWidth() + 0.6F;
                this.setRecordItem(this.getDiscFromOwner());
                float speed = 0.05F;
                if (this.distanceTo(following) > 2 + width) {
                    this.copyPosition(following);
                } else {
                    double targetX = following.getX() + Math.sin(tickCount * speed) * width;
                    double targetY = following.getY();
                    double targetZ = following.getZ() - Math.cos(tickCount * speed) * width;
                    Vec3 vec3 = new Vec3(targetX - this.getX(), targetY - this.getY() + Math.sin(tickCount * 0.3F + 2F) * 0.01F, targetZ - this.getZ());
                    this.setDeltaMovement(vec3);
                }
                if (!this.isSilent() && !this.getRecordItem().isEmpty()) {
                    this.level().broadcastEntityEvent(this, (byte) 66);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 67);
                }
                if (following instanceof LivingEntity && !TameableUtils.hasEnchant((LivingEntity) following, ModEnchantments.DISK_JOCKEY)) {
                    this.setFollowingUUID(null);
                }
            } else {
                this.setRecordItem(ItemStack.EMPTY);
                this.discard();
            }
        }
    }

    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        this.entityData.set(FOLLOWING_UUID, Optional.empty());
        this.entityData.set(JUKEBOX_ITEM, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.hasUUID("FollowerUUID")) {
            this.setFollowingUUID(tag.getUUID("FollowerUUID"));
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
        if (this.getFollowerUUID() != null) {
            tag.putUUID("FollowerUUID", this.getFollowerUUID());
        }
    }

    @Nullable
    public UUID getFollowerUUID() {
        return this.entityData.get(FOLLOWING_UUID).orElse(null);
    }

    public void setFollowingUUID(@Nullable UUID uniqueId) {
        this.entityData.set(FOLLOWING_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getFollowing() {
        UUID id = getFollowerUUID();
        if (id != null && !level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    public ItemStack getRecordItem() {
        return this.entityData.get(JUKEBOX_ITEM);
    }

    public void setRecordItem(ItemStack item) {
        this.entityData.set(JUKEBOX_ITEM, item);
    }

    public void addDiscToOwner(ItemStack stack) {
        if (getFollowing() instanceof LivingEntity living) {
            TameableUtils.setPetJukeboxDisc(living, stack);
            if (stack.isEmpty()) {
                this.level().broadcastEntityEvent(this, (byte) 67);
            }
        }
    }

    public ItemStack getDiscFromOwner() {
        if (getFollowing() instanceof LivingEntity living) {
            return TameableUtils.getPetJukeboxDisc(living);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
    }

    public boolean shouldRiderSit() {
        return false;
    }

    public @NotNull InteractionResult interactAt(@NotNull Player player, @NotNull Vec3 vec3, @NotNull InteractionHand hand) {
        return interact(player, hand);
    }

    public boolean isPickable() {
        return true;
    }

    public @NotNull InteractionResult interact(Player player, @NotNull InteractionHand hand) {
        boolean flag = false;
        ItemStack held = player.getItemInHand(hand);
        if (!this.getRecordItem().isEmpty()) {
            ItemStack copy = this.getRecordItem().copy();
            addDiscToOwner(ItemStack.EMPTY);
            this.spawnAtLocation(copy);
            flag = true;
        }
        if (held.getItem() instanceof RecordItem) {
            addDiscToOwner(held);
            if (!player.isCreative()) {
                held.shrink(1);
            }
            flag = true;
        }
        return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }


    public void handleEntityEvent(byte id) {
        if (id == 66 || id == 67) {
            if (this.getRecordSound() != null && random.nextFloat() < 0.1F) {
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                this.level().addParticle(ParticleTypes.NOTE, this.getX(), this.getY(1), this.getZ(), f, f1, f2);
            }
            DomesticationMod.PROXY.updateEntityStatus(this, id);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Nullable
    public SoundEvent getRecordSound() {
        if (getRecordItem().getItem() instanceof RecordItem record) {
            return record.getSound();
        }
        return null;
    }
}
