package luckyowlstudios.mods.luckysshowcase.block.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;

public interface DisplayBlock {

    boolean canHoldItem(Item item);

    SoundEvent placeItemSound();
}

