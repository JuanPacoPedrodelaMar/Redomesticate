# Redomesticate

<a href='https://fabricmc.net'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://neoforged.net/'><img alt="neoforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/neoforge_vector.svg"></a>

Redomesticate was created with one goal in mind: Making tamable mobs in Minecraft not only useful and
engaging, but to promote the taming of mobs as a playstyle by giving massive improvements to how tame mobs behave and
interact with the world. Not only have we expanded the amount of mobs in vanilla Minecraft that can be tamed, but we
have also fixed several problems with these mobs, added new items and blocks to improve the tamed mob experience, and
have introduced a new Pet Enchanting system. Asides from the new command system, most of these changes should be
reflected in both mobs from vanilla and new tamed mobs introduced in other mods, like Alex's Mobs.

With Redomesticate installed, no more will your pets have to sit out battles, exploration, and more, at home.
Now they can join the fun without worry of permanently losing them, or them being completely outclassed by other mobs
and mods.

# Feature Breakdown

### Wander, Stay, Follow

Wolves, Cats, Parrots, Axolotls, Rabbits, and Foxes now have 3 possible states when tamed: Wander, Stay and Follow.
These can be cycled through by right-clicking on the mob. Now it's finally possible to have your cats and dogs wander
around your house instead of being frozen in place or randomly teleport to you. This feature is configurable.

### Swing Through Pets

Unless sneaking, the swing of your sword, fist, or any attack will pass right through any tamed pets of yours. They will
hit any possible mob behind the pet as well. This means you are free to get in the battle with your pets without fear of
hurting them! This change applies to arrows and other projectiles as well. If you do want to hurt the tamed mob, you can
hold sneak during the attack. This feature is configurable.

### Sweeping Edge Changes

Sweeping edge can now no longer hurt any tamed pets of yours.

### Tameable Axolotls

Gone are the days of weirdly holding a tropical fish bucket if you want to use an Axolotl as a companion: These
amphibians are now fully tameable! Simply feed a wild axolotl a few tropical fish to tame them. They can be made to sit,
wander and follow like all the other tameable mobs. Note that axolotls are by default very slow on land, dry out and
have low health - you have been warned. This feature is configurable.

### Tameable Foxes

Trustable mobs have always been somewhat scuffed in implementation. Thankfully, we've made it so that tamed foxes are
actually tamed - and can be made to sit, wander and follow like the other pets. To get a tame fox, you need to breed two
wild foxes with sweet berries and wait for it to grow up. Note that no matter how tame foxes are, they'll still want to
kill any chickens and (untamed) rabbits they can see. This feature is configurable.

### Tameable Rabbits

When they were initially added to the game, rabbits were fully tameable just like cats and wolves. However, this was
eventually removed. We've undone this change and made them like the other tamable mobs above. To tame a rabbit, feed it
several Hay Bales. Rabbits only have 3 health by default and usually (see: Sinister Carrot) cannot attack. However, to
make them useful we have reintroduced another removed feature: rabbits scare off ravagers. Both them being tameable and
the scaring of ravagers are configurable.

### Tameable Frogs

Lots of people have frogs as pets in the real world... right? Now you can tame them in Minecraft with a few spider eyes.
Frogs can be made to stay, follow or wander like the other pets, and will come to their owner's defense. They may have
low attack and health, but they sure make up for it in cuteness.

### Pet Beds

Pet Beds are new craftable blocks that are made like so:

Note that they come in all 16 colors.

By moving one of your tamed mobs onto a pet bed, you enable it to respawn there the next morning if it somehow dies. The
visual indication for this is if you see a bunch of ZZZ particles appear above your pet's head: this means it has set
its respawn point. To remove the respawn point, simply break the bed block.

Mobs can only respawn at dawn, so if your pet dies some other time you will have to wait until then to see it again. Pet
enchants are preserved with respawning, but more on that later. The respawning of mobs in beds is configurable.

### Wayward Lantern

The Wayward Lantern is a decorative block that can be crafted from iron ingots and a lantern. If you are ever so far
away from your pets in 'follow' mode that they are unloaded from the world, they will eventually find their way back to
the closest Wayward Lantern to you. This makes it perfectly fine to die far from home and not loose both your pets and
your loot.

### Feather on a Stick

Feather on a Stick is a new simple tool crafted like so:

When used, this will send out a feather on a string in front of you. The closest pet of yours will then try to move onto
the feather to play with it. This tool is useful for getting your mob out of places it shouldn't be, like if it's stuck
in a doorway or on top of a chest. It's also useful for moving a pet onto its bed so you can set its respawn point.

### Rotten Apple & Sinister Carrot

Rotten Apples have a chance to form if an apple item despawns on the ground (configurable). These green, wormy apples
can be fed to a horse in order to turn it into a zombie horse. This zombification process will also preserve the speed
and other aspects of the horse. Zombie horses cannot wear armor.

Sinister Carrots are rarely found as loot in Woodland Mansions (configurable). They can be fed to a zombie horse in
order to turn it into a skeleton horse. They can also be fed to a rabbit to transform it into a Killer Rabbit,
which can attack and is much stronger than a normal rabbit.

### Animal Tamer Villager

Animal Tamers can be found in villages in their own unique houses (spawn rate configurable) or can be created with a
unemployed villager using a pet bed as a workstation. They trade a variety of items related to animals from both vanilla
minecraft and this mod, including unique pet enchants(charisma). Their houses also contain some untamed mobs and
sometimes fish tanks.

The Animal Tamer villager can be disabled via the config.

### Deed of Ownership

The Deed of Ownership is an item that can be purchased from the Animal Tamer villager. By right clicking on a tamed pet
that you are the owner of, you bind it to the deed. If you give the deed to any other player, and they use it on the
bounded animal, then the ownership of the pet will pass onto them, making it effectively as if they were the ones who
tamed the animal.

### Command Drum

The Command Drum block has been added to automate commanding groups of mobs via redstone. Craft it like so:

When interacted with, the drum will beat and change command modes, whilst ordering all tamed mobs of the user to follow,
stay or wander depending on the beat. If given a redstone signal, it will command the mobs of the player that originally
placed the block. The block also makes a funny sound if you jump on it.

### Collar Tags

Collar Tags are the conduit for pet enchantments. They can be crafted like so:

Like a nametag, these collar tags can be renamed in an anvil and used on a mob to rename it (unlike a nametag, this is
only for animals that are your pets). However, their real use is that they can be enchanted (configurable).

### Enchanting Your Pets

Pets can be enchanted by applying enchants to a collar tag in either an anvil or an enchanting table.

These collars can then be placed on a tamed pet of yours by interacting with it. To get the enchanted collar back, you
can simply swap it with any other collar, including an unenchanted one. You can view a pet's enchants by sneaking and
looking at them.

## All Pet Enchantments

35 Pet Enchantments are added, including 4 curses and 5 loot/trade exclusive enchantments. These are:

```
    Health Boost I-III : Pet gains 10 additional maximum health per level.
    Fireproof : Pet cannot be damaged by fire or lava.
    Immunity Frame I-III : Pet gains immunity frames (1 second for each level) after attacked, donated by an energy barrier overlay. While active, pet will not be damaged further during this time.
    Deflecting : Pet is protected by a ghostly shield which deflects any projectile attacks.
    Poison Resistance : Pet cannot be inflicted with poison effect.
    Chain Lightning I-II : When attacking, Pet shoots lightning that arcs from the target to nearby mobs. Perfect for dealing with swarms of mobs like zombies. Level determines amount of mobs the lightning arcs between.
    Speedster I-III : Increases pets movement speed.
    Frost Fang : Inflicts attack targets with frozen effect (ie. powdered snow effect) that slows them and damages them over time
    Magnetic : Pet is aided by a ghostly magnet which pulls attack targets closer to it.
    Linked Inventory : Pet can pick up items and teleport them to its owners inventory.
    Total Recall : When below 2 health, pet enters a recall orb and is protected from any further damage until released by its owner.
    Health Siphon : Transfers any incoming damage from the pet to its owner, if nearby.
    Bubbling I-II : Pet attacks trap target inside a giant bubble which floats upwards and pops, sending the target plummeting to the ground. Level determines duration of the bubble. Only found as loot in Buried Treasure.
    Herding I-II : Pet can be followed by untamed animals. Level determines maximum amount of followers.
    Amphibious : Pet cannot dry out (if aquatic like axolotls) or drown (if a land animal) in water or on land. Improves movement underwater and allows land animals to properly swim instead of splashing at the top of the water.
    Vampire's Familiar I-II : Pet regains a portion of health for each successful attack. Level I is 50% of the attack, level II is 100%. Only found as loot in Woodland Mansions.
    Void Cloud : If pet falls off a cliff or into the void, a cloud will be summoned to lift it back up and towards its owner, if nearby. Only found as loot in End Cities.
    Charisma I-III : Lowers trade prices if near owner. Level determines how great of a deal is given. Only given as a trade by villagers.
    Shadow Hands I-IV : Pet gains dark magical abilities in the form of shadow hands that emerge from the creature. These hands deal melee damage at a decent range. Level determines count and speed of hands when attacking.
    Disc Jockey : Pet is followed by a magical jukebox that can play any record on loop.
    Defusal I-III : Pet will nullify any nearby explosion, such as from creepers or TNT. Level determines range of effect.
    Warping Bite : Pet will teleport target away from itself when attacking.
    Ore Scenting I-III : Pet will occasionally sniff out local ores and highlight them to nearby players. Level determines frequency, range and amount of ores highlighted. Only found as loot in Abandoned Mineshafts.
    Gluttonous : This pet can eat any food if low on health to heal, ignoring dietary restrictions and negative effects of the food.
    Psychic Wall I-III : This pet can summon a psychic wall of energy between its owner and any close monsters during combat. This wall will stop any unfriendly entity from walking through it. Level determines size of wall, length and frequency of the effect.
    Intimidation I-II : This pet can occasionally scare all nearby monsters away from it. Level determines frequency and range.
    Tethered Teleport : This pet will automatically teleport with you whenever you do when following. That includes through portals, via commands, ender pearls and more.
    Muffled : This pet will not emit sound or vibrations. Only found as loot in Ancient Cities.
    Blazing Protection I-III : This pet is surrounded by multiple (2 per level) blaze bars, which absorb incoming damage and ignite attackers. Only found as loot in Nether Fortresses.
    Healing Aura I-II : When this pet or its allies are injured, it will create a circle of regeneration around itself, healing nearby mobs.
    Rejuvenation : This pet can absorb experience orbs when injured to convert to health points.
    Curse of Afterlife : If pet is slain, a hostile, zombifed version of it appears, with all the enchants and abilities of its past life.
    Curse of Infamy : Pet attracts attention from nearby monsters, which will begin targeting it.
    Curse of Blight : Random flowers, crops, grass blocks, etc. will wither and die occasionally when this pet is near.
    Curse of Immaturity : Pet appears as a baby animal version of itself, and deals less damage when attacking.
```

Most of these enchants are not compatible with each other and have varying levels of occurrence. See the chart below:
enchantment chart

## A Word on Mod Compatibility

The Wander, Stay and Follow command system is implemented for vanilla mobs only. It is up to other mods to develop a
similar system for their mobs.

However, all other features of the mod like the pet beds and enchants should work with any tameable mob in other mods.
Enchants from this mod are compatible with Enchantment Descriptions. Mobs from Alex's Mobs should spawn by default
inside some of the Animal Tamer's cages in villages. If you are a mod dev and want to make sure Redomesticate
works out of the box for your mod, remember to make sure your tameable mob's class extends TameableAnimal.

## Special Thanks & Credits:

- AlexModGuy, without whom this mod wouldn't be possible.
- Kobber for the incredible new Wayward Lantern textures.

## License

[![Code license (GPL3.0)](https://img.shields.io/badge/code%20license-GPL3.0-green.svg?style=flat-square)](https://github.com/evanbones/Redomesticate/blob/1.21.1/LICENSE)

---

[![discord-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/social/discord-plural_vector.svg)](https://discord.com/invite/JcGRdT6Pbx) [![github-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/social/github-plural_vector.svg)](https://github.com/evanbones/Redomesticate)
