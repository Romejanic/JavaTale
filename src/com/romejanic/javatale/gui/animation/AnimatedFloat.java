package com.romejanic.javatale.gui.animation;

import com.romejanic.javatale.gl.Window;
import com.romejanic.javatale.math.Mathf;

public class AnimatedFloat {

	private float min;
	private float max;
	private float val;
	
	private float progress = 0f;
	private float duration = 0f;
	
	private boolean isAnimating = false;
	
	public AnimatedFloat(float min, float max, float duration) {
		this.min = min;
		this.max = max;
		this.val = min;
		
		this.duration = duration;
	}
	
	public float getMinimum() {
		return min;
	}
	
	public float getMaximum() {
		return max;
	}
	
	public float getValue() {
		return val;
	}
	
	public void setMinimum(float min) {
		this.min = min;
	}
	
	public void setMaximum(float max) {
		this.max = max;
	}
	
	public void setRange(float min, float max) {
		this.min = min;
		this.max = max;
	}
	
	public float animate() {
		if(this.isAnimating) {
			progress += Window.getDelta();
			val = Mathf.lerp(min, max, getAnimationProgress());
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
		isAnimating = true;
		reset();
	}
	
	public void stopAnimating() {
		isAnimating = false;
	}
	
	public void reset() {
		val = min;
		progress = 0f;
		isAnimating = false;
	}
	
}