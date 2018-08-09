package com.romejanic.javatale.gui.animation;

import com.romejanic.javatale.gl.Window;
import com.romejanic.javatale.math.Mathf;

public class AnimatedFloat {

	private float start;
	private float end;
	private float val;
	
	private float progress = 0f;
	private float duration = 0f;
	
	private boolean isAnimating = false;
	
	public AnimatedFloat(float start, float end, float duration) {
		this.start = start;
		this.end = end;
		this.val = start;
		
		this.duration = duration;
	}
	
	public float getStartValue() {
		return start;
	}
	
	public float getEndValue() {
		return end;
	}
	
	public float getValue() {
		return Mathf.clamp(this.val, this.start, this.end);
	}
	
	public void setStartValue(float start) {
		this.start = start;
	}
	
	public void setEndValue(float end) {
		this.end = end;
	}
	
	public void setRange(float start, float end) {
		if(this.isAnimating) {
			this.stopAnimating();
		}
		this.start = start;
		this.end = end;
	}
	
	public float animate() {
		if(this.isAnimating) {
			progress += Window.getDelta();
			val = Mathf.lerp(start, end, getAnimationProgress());
			if(progress >= duration) {
				this.isAnimating = false;
			}
		}
		return getValue();
	}
	
	public float getAnimationProgress() {
		return Mathf.clamp01(progress / duration);
	}
	
	public boolean isAnimating() {
		return isAnimating;
	}
	
	public void startAnimating() {
		reset();
		isAnimating = true;
	}
	
	public void stopAnimating() {
		isAnimating = false;
	}
	
	public void reset() {
		val = start;
		progress = 0f;
		isAnimating = false;
	}
	
	public void animateTo(float value) {
		this.setRange(this.val, value);
		this.startAnimating();
	}
	
}