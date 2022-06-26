package dev.xkmc.l2rpgcommon.content.magic.products.info;

public enum ProductState {
	LOCKED, UNLOCKED, CRAFTED;

	public int getIndex() {
		return ordinal();
	}

	public String toString() {
		return name().toLowerCase();
	}
}
