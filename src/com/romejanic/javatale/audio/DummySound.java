package com.romejanic.javatale.audio;

public class DummySound extends Sound {

	protected DummySound() {
		super(null);
	}
	
	@Override
	public String getName() {
		return "dummy";
	}

	public void play(boolean looping) {}
	protected void delete() {}
	
}